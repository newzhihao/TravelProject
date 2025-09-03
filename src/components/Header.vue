//组件介绍：
//这是头部巡航栏，包含了头像，搜索框，投稿按钮等功能。
<template>
  <el-header class="header">
    <!-- Logo区域 -->
    <div class="logo">
      <span class="logo-text">旅记</span>
    </div>

    <!-- 搜索框 -->
    <div class="search-container">
      <el-input
          v-model="searchText"
          placeholder="搜索内容..."
          class="search-input"
          @keyup.enter="handleSearch"
      >
        <template #prefix>
          <el-icon class="search-icon"><Search /></el-icon>
        </template>
      </el-input>
    </div>

    <!-- 右侧功能区 -->
    <div class="header-right">
      <el-button type="primary" class="upload-btn" @click="publish">
        <el-icon><Upload /></el-icon>
        投稿
      </el-button>
      <div class="avatar-container" @click="personHome">
        <el-avatar
            :src="user_data.authorPhotoUrl || defaultAvatar"
            shape="circle"
            class="user-avatar"
        />
      </div>
    </div>
  </el-header>
</template>
<script setup>
import { Message, Search, Star, Upload } from '@element-plus/icons-vue';
import { useRouter } from "vue-router";
import { onMounted, ref } from "vue";
// 导入搜索接口
import { searchContents } from "@/api/contents";
import { ElMessage } from 'element-plus';

const router = useRouter();
const user_data = ref({});
const searchText = ref('');
const defaultAvatar = 'https://cube.elemecdn.com/3/7c/3ea6beec643a9c936c2343e5797jpeg.jpeg';

onMounted(() => {
  const data = JSON.parse(localStorage.getItem('userInfo'));
  if (data) {
    user_data.value = data;
  }
});

const publish = () => {
  router.push('/Publish');
};

const personHome = () => {
  router.push('/personal-home');
};

// 实现搜索功能
const handleSearch = async () => {
  const words = searchText.value.trim();
  if (!words) {
    ElMessage.warning('请输入搜索内容');
    return;
  }

  try {
    // 调用搜索接口
    const result = await searchContents(words);
    // 假设搜索结果页路由为/search，将结果通过路由参数传递
    router.push({
      path: '/search',
      query: {
        words: words,
        results: JSON.stringify(result.data) // 将结果作为参数传递
      }
    });
    // 清空搜索框
    searchText.value = '';
  } catch (error) {
    console.error('搜索失败:', error);
    ElMessage.error('搜索失败，请稍后重试');
  }
};
</script>
<style scoped>
/* 头部导航容器样式 */
.header {
  display: flex; /* 使用flex布局 */
  align-items: center; /* 垂直居中对齐子元素 */
  padding: 0 40px; /* 左右内边距40px，上下0 */
  height: 64px; /* 固定高度64px */
  background-color: #fff; /* 白色背景 */
  border-bottom: 1px solid #eee; /* 底部灰色边框 */
  box-shadow: 0 1px 2px rgba(0, 0, 0, 0.05); /* 轻微阴影，增强层次感 */
}

/* 网站logo容器样式 */
.logo {
  display: flex; /* flex布局，用于对齐logo图标和文字 */
  align-items: center; /* 垂直居中 */
  cursor: pointer; /* 鼠标悬停显示手型，提示可点击 */
  margin-right: 40px; /* 右侧外边距40px，与搜索框保持距离 */
}

/* logo文字样式 */
.logo-text {
  font-size: 24px; /* 字体大小24px */
  font-weight: 700; /* 粗体 */
  color: #3c5094;
}

/* 搜索框容器样式 */
.search-container {
  flex: 1; /* 占据剩余空间 */
  max-width: 600px; /* 最大宽度600px，防止过宽 */
}

/* 搜索输入框样式 */
.search-input {
  height: 36px; /* 高度36px */
  padding-left: 15px; /* 左侧内边距，防止文字贴边 */
  padding-right: 15px; /* 右侧内边距 */
}

/* 搜索图标样式 */
.search-icon {
  color: #3c5094; /* 灰色图标 */
}

/* 头部右侧区域样式（图标按钮、上传按钮、头像） */
.header-right {
  display: flex; /* flex布局 */
  align-items: center; /* 垂直居中 */
  gap: 15px; /* 子元素之间间距15px */
}

/* 图标按钮样式（如消息、设置等） */
.icon-btn {
  color: #666; /* 深灰色图标 */
  transition: color 0.2s; /* 颜色变化过渡动画 */
}

/* 图标按钮hover状态 */
.icon-btn:hover {
  color: #3c5094; /* 变为品牌粉色 */
  background-color: rgba(251, 114, 153, 0.1); /* 浅粉色背景 */
}

/* 上传按钮样式 */
.upload-btn {
  background-color: #3c5094;
  border-color: #3c5094; /* 边框颜色与背景一致 */
  color: #fff; /* 白色文字 */
  border-radius: 20px; /* 圆角20px */
  padding: 6px 16px; /* 上下6px，左右16px内边距 */
  font-size: 14px; /* 字体大小14px */
  transition: all 0.2s; /* 所有属性变化过渡动画 */
}

/* 上传按钮hover状态 */
.upload-btn:hover {
  background-color: #3c5094;
  border-color: #3c5094; /* 边框颜色同步变化 */
  transform: translateY(-1px); /* 向上微移1px，增强交互感 */
}

/* 用户头像容器样式 */
.avatar-container {
  cursor: pointer; /* 鼠标悬停显示手型，提示可点击 */
}

/* 用户头像图片样式 */
.user-avatar {
  width: 36px; /* 宽度36px */
  height: 36px; /* 高度36px，保持正方形 */
  border: 1px solid #eee; /* 浅灰色边框 */
}
</style>