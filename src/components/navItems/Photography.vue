<template>
  <div class="home-container">
    <!-- 顶部导航栏 -->
    <header class="main-header" :class="{ 'sticky': isSticky }">
      <Header />
    </header>

    <!-- 二级导航 -->
    <div class="secondary-nav">
      <el-scrollbar>
        <div class="nav-wrapper">
          <el-button
              v-for="(item, index) in navItems"
              :key="index"
              :class="{ 'active': activeNav === index }"
              class="nav-item"
              @click="handleNavClick(index, item.path)"
          >
            {{ item.name }}
          </el-button>
        </div>
      </el-scrollbar>
    </div>

    <!-- 主要内容区 -->
    <div class="main-content">
      <!-- 内容卡片列表 -->
      <div class="content-grid">
        <div v-for="(card, index) in cardList" :key="index" class="card-item" @click="goToContent(card)">
          <el-card class="card">
            <div class="card-img-container">
              <img :src="card.coverImageUrl" alt="封面图片" class="card-img">
            </div>
            <div class="card-content">
              <h3 class="card-title">{{ card.title }}</h3>
            </div>
          </el-card>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, onUnmounted, watch } from 'vue';
import { useRouter, useRoute } from 'vue-router';
import Header from '@/components/Header.vue';
import { getContentsByCategory} from '@/api/contents';

const router = useRouter();
const route = useRoute();
const isSticky = ref(false);
const activeNav = ref(0);
const cardList = ref([]);

// 二级导航项
const navItems = [
  { name: '推荐', path: '/Home' },
  { name: '旅游', path: '/travel' },
  { name: '美食', path: '/food' },
  { name: '攻略', path: '/strategy' },
  { name: '科技', path: '/technology' },
  { name: '人文', path: '/humanities' },
  { name: '摄影', path: '/photography' },
  { name: '县志地理', path: '/countyGeography' },
];

// 根据当前路由更新激活的导航项
const updateActiveNav = () => {
  const currentPath = route.path;
  const index = navItems.findIndex(item => item.path === currentPath);
  if (index !== -1) {
    activeNav.value = index;
  }
};

// 监听滚动事件实现吸顶效果
const handleScroll = () => {
  isSticky.value = window.scrollY > 64;
};

onMounted(() => {
  window.addEventListener('scroll', handleScroll);
  // 获取小卡片数据
  getContentsByCategory('摄影').then(res => {
    cardList.value = res.data; // 取前2个作为左侧小卡片
  });
  // 初始化激活导航项
  updateActiveNav();
});

onUnmounted(() => {
  window.removeEventListener('scroll', handleScroll);
});

// 监听路由变化
watch(() => route.path, () => {
  updateActiveNav();
});

const handleNavClick = (index, path) => {
  activeNav.value = index;
  router.push(path);
};

const goToContent = (item) => {
  router.push(`/contents/${item.contentId}`);
};
</script>
<style scoped>
/* 首页容器样式 */
.home-container {
  background-color: #f9f9f9; /* 浅灰色背景，区分内容区域与其他部分 */
  min-height: 100vh; /* 最小高度为视口高度，确保内容不足时也占满屏幕 */
}

/* 主头部容器样式 */
.main-header {
  position: relative; /* 相对定位，用于z-index层级控制 */
  z-index: 100; /* 较高层级，确保在其他内容上方 */
}

/* 头部固定定位样式（滚动时添加） */
.sticky {
  position: fixed; /* 固定定位，保持在视口顶部 */
  top: 0;
  left: 0;
  right: 0;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1); /* 增加阴影，增强悬浮感 */
  animation: slideDown 0.3s ease; /* 应用下滑动画 */
}

/* 头部下滑动画 */
@keyframes slideDown {
  from {
    transform: translateY(-100%); /* 初始位置在视口上方 */
  }
  to {
    transform: translateY(0); /* 结束位置在视口顶部 */
  }
}

/* 次要导航栏样式（分类导航） */
.secondary-nav {
  height: 50px; /* 固定高度50px */
  background-color: #fff; /* 白色背景 */
  border-bottom: 1px solid #eee; /* 底部边框，分隔内容 */
  position: sticky; /* 粘性定位，滚动时保持在顶部 */
  top: 64px; /* 距离视口顶部64px（主头部高度） */
  z-index: 90; /* 层级低于主头部，高于内容 */
}

/* 导航项容器样式 */
.nav-wrapper {
  display: flex; /* 使用flex布局排列导航项 */
  padding: 0 40px; /* 左右内边距40px */
  gap: 8px; /* 导航项之间间距8px */
}

/* 导航项样式 */
.nav-item {
  height: 50px; /* 高度与导航栏一致 */
  line-height: 50px; /* 行高等于高度，实现文字垂直居中 */
  padding: 0 16px; /* 左右内边距16px */
  font-size: 14px; /* 字体大小14px */
  color: #333; /* 深灰色文字 */
  background: transparent; /* 透明背景 */
  border: none; /* 去除边框 */
  position: relative; /* 相对定位，用于激活状态下划线定位 */
  transition: color 0.2s; /* 颜色过渡动画 */
}

/* 导航项hover状态 */
.nav-item:hover {
  color: #FB7299; /* 品牌粉色 */
  background: transparent; /* 保持透明背景 */
}

/* 导航项激活状态 */
.nav-item.active {
  color: #FB7299; /* 品牌粉色 */
  font-weight: 500; /* 中等字重，突出显示 */
}

/* 激活状态下划线 */
.nav-item.active::after {
  content: ''; /* 伪元素内容为空 */
  position: absolute; /* 绝对定位 */
  bottom: 0; /* 定位到导航项底部 */
  left: 0;
  width: 100%; /* 宽度占满导航项 */
  height: 3px; /* 高度3px */
  background-color: #FB7299; /* 品牌粉色背景 */
  border-radius: 3px 3px 0 0; /* 上圆角，下直角 */
  animation: expand 0.3s ease; /* 应用展开动画 */
}

/* 下划线展开动画 */
@keyframes expand {
  from {
    width: 0; /* 初始宽度0 */
    left: 50%; /* 初始位置居中 */
  }
  to {
    width: 100%; /* 结束宽度100% */
    left: 0; /* 结束位置靠左 */
  }
}

/* 主内容区域样式 */
.main-content {
  max-width: 1440px; /* 最大宽度1440px，限制内容宽度 */
  margin: 0 auto; /* 水平居中 */
  padding: 20px 40px; /* 内边距，与导航栏对齐 */
}

/* 网格容器 - 实现每行两个卡片 */
.content-grid {
  display: grid;
  grid-template-columns: repeat(3, 1fr); /* 3列等宽布局 */
  gap: 20px; /* 卡片之间的间距 */
  margin-top: 20px;
}
/* 卡片项容器样式 */
.card-item {
  width: 100%; /* 宽度占满父容器 */
}

/* 卡片主体样式 */
.card {
  border: none; /* 去除默认边框 */
  border-radius: 8px; /* 8px圆角边框 */
  overflow: hidden; /* 隐藏超出容器的内容，配合圆角使用 */
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.08); /* 轻微阴影效果，增强层次感 */
  transition: all 0.2s ease; /* 所有属性变化时的过渡动画，0.2秒完成，缓动效果 */
  height: 100%; /* 高度占满父容器 */
}

/* 卡片hover状态样式 */
.card:hover {
  transform: translateY(-5px); /* 向上移动5px，产生悬浮效果 */
  box-shadow: 0 8px 16px rgba(0, 0, 0, 0.12); /* 加深阴影，增强悬浮感 */
}

/* 穿透样式：修改element-ui卡片组件的内容区域样式 */
:deep(.el-card__body){
  padding: 0; /* 去除默认内边距 */
  height: 100%; /* 高度占满卡片 */
  display: flex; /* 使用flex布局 */
  flex-direction: column; /* 垂直方向排列子元素 */
}

/* 卡片图片容器样式 */
.card-img-container {
  width: 100%; /* 宽度占满父容器 */
  padding-top: 56.25%; /* 16:9的比例（9/16=0.5625），实现响应式图片容器 */
  position: relative; /* 相对定位，作为图片绝对定位的参考 */
  overflow: hidden; /* 隐藏图片缩放时超出容器的部分 */
}

/* 卡片图片样式 */
.card-img {
  position: absolute; /* 绝对定位，覆盖整个容器 */
  top: 0;
  left: 0;
  width: 100%; /* 宽度占满容器 */
  height: 100%; /* 高度占满容器 */
  object-fit: cover; /* 保持图片比例，裁剪多余部分填满容器 */
  transition: transform 0.3s ease; /* 图片缩放过渡动画，0.3秒完成 */
}


/* 卡片内容区域样式 */
.card-content {
  padding: 12px; /* 12px内边距 */
  flex: 1; /* 自动填充剩余空间 */
  display: flex; /* 使用flex布局 */
  flex-direction: column; /* 垂直方向排列子元素 */
}

/* 卡片标题样式 */
.card-title {
  font-size: 16px; /* 字体大小16px */
  font-weight: 500; /* 中等字重 */
  color: #333; /* 深灰色文字 */
  margin: 0; /* 去除默认外边距 */
  display: -webkit-box; /* WebKit内核浏览器的盒模型，用于多行文本省略 */
  -webkit-line-clamp: 2; /* 最多显示2行文字 */
  -webkit-box-orient: vertical; /* 垂直方向排列 */
  overflow: hidden; /* 隐藏超出部分 */
  line-height: 1.4; /* 行高1.4倍 */
  flex: 1; /* 自动填充剩余空间 */
}

</style>