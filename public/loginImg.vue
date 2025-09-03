<template>
  <div class="upload-test-container">
    <h3>内容发布测试</h3>

    <el-form ref="form" :model="formData" label-width="100px" class="form-container">
      <!-- 新增作者账号字段 -->
      <el-form-item label="作者账号" required>
        <el-input v-model="formData.authorAccount" placeholder="请输入作者账号" />
      </el-form-item>

      <el-form-item label="标题" required>
        <el-input v-model="formData.title" placeholder="请输入标题" />
      </el-form-item>

      <el-form-item label="分类" required>
        <el-select v-model="formData.category" multiple placeholder="选择分类">
          <el-option label="旅游" value="旅游" />
          <el-option label="美食" value="美食" />
          <el-option label="科技" value="科技" />
        </el-select>
      </el-form-item>

      <el-form-item label="摘要" required>
        <el-input type="textarea" v-model="formData.summary" rows="2" />
      </el-form-item>

      <el-form-item label="内容" required>
        <el-input type="textarea" v-model="formData.content" rows="3" />
      </el-form-item>

      <!-- 图片上传 -->
      <el-form-item label="图片" required>
        <el-upload
            class="upload-area"
            action="#"
            :auto-upload="false"
            :on-change="handleFileChange"
            list-type="picture"
            accept="image/*"
            multiple
        >
          <el-button type="primary">选择图片</el-button>
          <div slot="tip" class="el-upload__tip">支持jpg、png、gif格式，单文件不超过5MB</div>
        </el-upload>
      </el-form-item>

      <!-- 操作按钮 -->
      <el-form-item>
        <el-button
            type="primary"
            @click="handleSubmit"
            :loading="isLoading"
        >
          提交
        </el-button>
      </el-form-item>
    </el-form>

    <!-- 结果提示 -->
    <el-dialog title="提交结果" :visible.sync="showResult">
      <pre>{{ resultInfo }}</pre>
      <template #footer>
        <el-button @click="showResult = false">关闭</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive } from 'vue';
import axios from 'axios';

// 表单数据 - 新增authorAccount字段
const formData = reactive({
  authorAccount: '', // 作者账号
  title: '',
  category: [],
  summary: '',
  content: '',
  images: [] // 存储选中的文件
});

// 状态管理
const isLoading = ref(false);
const showResult = ref(false);
const resultInfo = ref('');

// 处理文件选择
const handleFileChange = (file, fileList) => {
  formData.images = fileList.map(item => item.raw);
};

// 提交表单 - 新增authorAccount的校验和传递
const handleSubmit = async () => {
  // 完善校验 - 新增authorAccount的非空校验
  if (!formData.authorAccount || !formData.title || !formData.category.length || !formData.summary || !formData.content || !formData.images.length) {
    resultInfo.value = '请完善所有必填项（包括作者账号）';
    showResult.value = true;
    return;
  }

  isLoading.value = true;
  try {
    // 构建FormData
    const form = new FormData();

    // 添加文本字段 - 新增authorAccount的传递
    form.append('publish', new Blob([JSON.stringify({
      authorAccount: formData.authorAccount, // 传递作者账号
      title: formData.title,
      category: formData.category,
      summary: formData.summary,
      content: formData.content
    })], { type: 'application/json' }));

    // 添加图片文件
    formData.images.forEach((file, index) => {
      form.append('images', file, file.name);
    });

    // 发送请求
    const res = await axios.post('/api/uploads', form, {
      headers: { 'Content-Type': 'multipart/form-data' }
    });

    resultInfo.value = JSON.stringify(res.data, null, 2);
  } catch (err) {
    resultInfo.value = err.response?.data?.msg || '请求失败：' + err.message;
  } finally {
    isLoading.value = false;
    showResult.value = true;
  }
};
</script>

<style scoped>
.upload-test-container {
  max-width: 800px;
  margin: 20px auto;
  padding: 20px;
  border: 1px solid #eee;
  border-radius: 8px;
}

.form-container {
  margin-top: 20px;
}

.upload-area {
  margin-top: 10px;
}
</style>