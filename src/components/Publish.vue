//组件介绍：
//这是投稿发布内容的页面，用户可以在这里填写标题、简介、正文内容、分类标签和图片上传。
<template>
  <!-- 投稿页面容器 -->
  <div class="publish-page">
    <!-- 页面标题区域 -->
    <div class="page-header">
      <h3 class="page-title">分享你的精彩内容，与大家一起交流</h3>
    </div>

    <!-- 主内容卡片 -->
    <div class="publish-card">
      <!-- Element Plus表单组件 -->
      <el-form ref="form" :model="formData" label-width="100px" class="form-container">

        <!-- 标题输入区域 -->
        <el-form-item label="标题">
          <el-input
              v-model="formData.title"
              placeholder="请输入吸引人的标题"
              class="form-input"
          />
        </el-form-item>

        <!-- 分类选择区域（多选） -->
        <el-form-item label="分类">
          <el-select
              v-model="formData.category"
              multiple
              placeholder="选择合适的分类"
              class="form-select"
          >
            <el-option label="旅游" value="旅游" />
            <el-option label="美食" value="美食" />
            <el-option label="攻略" value="攻略" />
            <el-option label="科技" value="科技" />
            <el-option label="人文" value="人文" />
            <el-option label="摄影" value="摄影" />
            <el-option label="县志地理" value="县志地理" />
          </el-select>
        </el-form-item>

        <!-- 摘要输入区域（多行文本） -->
        <el-form-item label="摘要">
          <el-input
              type="textarea"
              v-model="formData.summary"
              rows="2"
              placeholder="简要描述内容核心（将显示在列表页）"
              class="form-textarea"
          />
        </el-form-item>

        <!-- 内容输入区域（多行文本） -->
        <el-form-item label="内容">
          <el-input
              type="textarea"
              v-model="formData.content"
              rows="8"
              placeholder="请详细描述你的内容..."
              class="form-textarea"
          />
        </el-form-item>

        <!-- 图片上传区域 -->
        <el-form-item label="图片">
          <div class="upload-wrapper">
            <el-upload
                class="upload-area"
                action="#"
                :auto-upload="false"
                :on-change="handleFileChange"
                list-type="picture-card"
                accept="image/*"
                multiple
            >
              <div class="upload-icon-container">
                <el-icon class="upload-icon"><Plus /></el-icon>
                <div class="upload-text">点击or拖拽图片到此</div>
              </div>
            </el-upload>
          </div>
        </el-form-item>

        <!-- 提交按钮区域 -->
        <el-form-item class="submit-group">
          <el-button
              type="default"
              @click="resetForm"
              class="reset-btn"
          >
            重置
          </el-button>
          <el-button
              type="primary"
              @click="handleSubmit"
              :loading="isLoading"
              class="submit-btn"
          >
            <el-icon v-if="!isLoading"><Check /></el-icon>
            提交投稿
          </el-button>
        </el-form-item>
      </el-form>
    </div>

    <!-- 提交结果弹窗 -->
    <el-dialog
        title="提交结果"
        :visible.sync="showResult"
        :center="true"
        width="500px"
    >
      <pre>{{ resultInfo }}</pre>
      <template #footer>
        <el-button @click="showResult = false">关闭</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import {ref, reactive, onMounted} from 'vue';
import axios from 'axios';
import { Plus, Check } from '@element-plus/icons-vue';
import { ElMessage } from 'element-plus';

// 表单数据对象
const formData = reactive({
  title: '',  // 文章标题
  category: [],  // 文章分类数组
  summary: '',  // 文章摘要
  content: '',  // 文章内容
  images: [],  // 上传的图片文件数组
  authorAccount: '' // 作者账号
});

// 表单引用，用于重置表单
const form = ref(null);

// 状态管理
const isLoading = ref(false);  // 提交加载状态
const showResult = ref(false);  // 是否显示结果弹窗
const resultInfo = ref('');  // 结果信息

/**
 * 处理文件选择变化
 * @param {Object} file 当前选择的文件
 * @param {Array} fileList 所有已选择的文件列表
 */
const handleFileChange = (file, fileList) => {
  // 将文件列表中的原始文件保存到formData.images
  formData.images = fileList.map(item => item.raw);
};

// 重置表单
const resetForm = () => {
  formData.title = '';
  formData.category = [];
  formData.summary = '';
  formData.content = '';
  formData.images = [];
  if (form.value) {
    form.value.resetFields();
  }
};

// 组件挂载时执行
onMounted(() => {
  // 从localStorage获取用户信息
  const userInfo = JSON.parse(localStorage.getItem('userInfo'));
  if (userInfo) {
    // 设置作者账号
    formData.authorAccount = userInfo.authorAccount;
  }
});

/**
 * 提交表单处理函数
 */
const handleSubmit = async () => {
  // 表单验证
  if (!formData.title || !formData.category.length || !formData.summary || !formData.content || !formData.images.length) {
    ElMessage.warning('请完善所有必填项');
    return;
  }

  isLoading.value = true;  // 开始加载

  try {
    // 创建FormData对象用于文件上传
    const form = new FormData();

    // 添加文本数据（转换为JSON Blob）
    form.append('publish', new Blob([JSON.stringify({
      authorAccount: formData.authorAccount,  // 作者账号
      title: formData.title,  // 标题
      category: formData.category,  // 分类
      summary: formData.summary,  // 摘要
      content: formData.content  // 内容
    })], { type: 'application/json' }));

    // 添加所有图片文件
    formData.images.forEach((file, index) => {
      form.append('images', file, file.name);
    });

    // 发送POST请求到服务器
    const res = await axios.post('/api/uploads', form, {
      headers: { 'Content-Type': 'multipart/form-data' }
    });

    // 保存结果信息
    resultInfo.value = JSON.stringify(res.data, null, 2);

    // 成功提示
    ElMessage.success('投稿成功！感谢您的分享');

    // 重置表单
    resetForm();

  } catch (err) {
    // 错误处理
    resultInfo.value = err.response?.data?.msg || '请求失败：' + err.message;
    ElMessage.error('提交失败，请稍后重试');
  } finally {
    // 结束加载并显示结果
    isLoading.value = false;
    showResult.value = true;
  }
};
</script>

<style scoped>
.publish-page {
  max-width: 1000px;
  margin: 0 auto;
  padding: 30px 20px;
  background-color: #f9fafb;
  min-height: 100vh;
}

/* 页面标题样式 */
.page-header {
  text-align: center;
  margin-bottom: 30px;
  padding: 20px 0;
}

.page-title {
  margin: 0 0 10px;
  color: #1a1a1a;
  font-size: 2rem;
  font-weight: 700;
  letter-spacing: -0.5px;
}

/* 主内容卡片 */
.publish-card {
  background-color: #fff;
  border-radius: 16px;
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.05);
  padding: 30px 40px;
  transition: box-shadow 0.3s ease;
}

.publish-card:hover {
  box-shadow: 0 10px 30px rgba(0, 0, 0, 0.08);
}

/* 表单容器 */
.form-container {
  margin-top: 20px;
}

/* 表单元素样式 */
.form-input, .form-select, .form-textarea {
  width: 100%;
  transition: all 0.2s ease;
}

.form-textarea {
  resize: vertical;
}

/* 上传区域样式 */
.upload-wrapper {
  margin-top: 10px;
}

.upload-area {
  border: 2px dashed #e2e8f0;
  border-radius: 12px;
  padding: 40px 20px;
  text-align: center;
  transition: all 0.3s ease;
  background-color: #f8fafc;
}

.upload-area:hover {
  border-color: #3c5094;
  background-color: rgba(251, 114, 153, 0.03);
}

.upload-icon-container {
  display: flex;
  flex-direction: column;
  align-items: center;
}

.upload-icon {
  font-size: 28px;
  color: #94a3b8;
  margin-bottom: 12px;
  transition: color 0.3s ease;
}

.upload-area:hover .upload-icon {
  color: #3c5094;
}

.upload-text {
  color: #64748b;
  font-size: 14px;
}

/* 提交按钮组 */
.submit-group {
  display: flex;
  justify-content: flex-end;
  gap: 15px;
  margin-top: 20px;
  padding-top: 15px;
  border-top: 1px solid #f1f5f9;
}

.reset-btn {
  padding: 8px 20px;
  border-radius: 6px;
  transition: all 0.2s ease;
}

.submit-btn {
  padding: 8px 30px;
  border-radius: 6px;
  background-color: #3c5094;
  border-color: #3c5094;
  transition: all 0.2s ease;
}

.submit-btn:hover {
  background-color: #3c5094;
  border-color: #3c5094;
  transform: translateY(-2px);
}

.submit-btn .el-icon {
  margin-right: 6px;
}

/* 图片预览样式 */
:deep(.el-upload-list--picture-card .el-upload-list__item) {
  width: 120px;
  height: 120px;
  border-radius: 8px;
  transition: all 0.2s ease;
}

:deep(.el-upload-list--picture-card .el-upload-list__item:hover) {
  transform: scale(1.05);
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
}

/* 错误提示样式 */
:deep(.el-form-item__error) {
  font-size: 12px;
  margin-top: 5px;
}

/* 响应式调整 */
@media (max-width: 768px) {
  .publish-card {
    padding: 20px 15px;
  }

  .page-title {
    font-size: 1.6rem;
  }

  .submit-group {
    flex-direction: column;
  }

  .reset-btn, .submit-btn {
    width: 100%;
  }
}
</style>