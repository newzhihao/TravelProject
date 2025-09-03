//组件介绍：
//home组件是整个页面的根组件，它包含了Header、Carousel和CardList三个子组件。
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
      <!-- 轮播图和卡片混合布局 -->
      <div class="featured-section">
        <!-- 主轮播图 -->
        <div class="main-carousel">
          <Carousel />
        </div>
        <!-- 左侧小卡片区域 -->
        <div class="small-cards">
          <el-card
              class="featured-card"
              v-for="(item, index) in smallCardList"
              :key="index"
              @click="goToContent(item)"
          >
            <div class="card-img-container">
              <img :src="item.coverImageUrl" alt="内容封面" class="card-img">
            </div>
            <div class="card-info">
              <h3 class="card-title">{{ item.title }}</h3>
            </div>
          </el-card>
        </div>

      </div>

      <!-- 内容卡片列表 -->
      <div class="content-grid">
        <CardList />
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, onUnmounted } from 'vue';
import { useRouter } from 'vue-router';
import Header from '@/components/Header.vue';
import Carousel from '@/components/Carousel.vue';
import CardList from '@/components/CardList.vue';
import { getCardList } from '@/api/contents';

const router = useRouter();
const isSticky = ref(false);
const activeNav = ref(0);
const smallCardList = ref([]);

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

// 监听滚动事件实现吸顶效果
const handleScroll = () => {
  isSticky.value = window.scrollY > 64;
};

onMounted(() => {
  window.addEventListener('scroll', handleScroll);
  // 获取小卡片数据
  getCardList().then(res => {
    smallCardList.value = res.data.slice(0, 2); // 取前2个作为左侧小卡片
  });
});

onUnmounted(() => {
  window.removeEventListener('scroll', handleScroll);
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
  color: #3c5094; /* 品牌粉色 */
  background: transparent; /* 保持透明背景 */
}

/* 导航项激活状态 */
.nav-item.active {
  color: #3c5094; /* 品牌粉色 */
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
  background-color: #3c5094; /* 品牌粉色背景 */
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
   /*max-width: 1440px; 最大宽度1440px，限制内容宽度 */
  margin: 0 auto; /* 水平居中 */
  padding: 20px 40px; /* 内边距，与导航栏对齐 */
}

/* 精选内容区域样式 */
.featured-section {
  display: flex; /* flex布局，排列轮播和小卡片 */
  gap: 20px; /* 子元素之间间距20px */
  margin-bottom: 30px; /* 底部外边距30px，与下方内容分隔 */
}

/* 小卡片容器样式 */
.small-cards {
  flex: 1; /* 占据1份空间 */
  display: flex; /* flex布局 */
  flex-direction: column; /* 垂直排列 */
  gap: 20px; /* 卡片之间间距20px */

}

/* 主轮播容器样式 */
.main-carousel {
  flex: 2; /* 占据2份空间，比小卡片区域宽 */
}

/* 精选卡片样式 */
.featured-card {
  border-radius: 8px; /* 8px圆角 */
  overflow: hidden; /* 隐藏超出部分，配合圆角 */
  border: none; /* 去除边框 */
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.08); /* 轻微阴影 */
  transition: transform 0.2s, box-shadow 0.2s; /* 变换和阴影过渡动画 */
  cursor: pointer; /* 鼠标手型，提示可点击 */
}

/* 精选卡片hover状态 */
.featured-card:hover {
  transform: translateY(-3px); /* 向上微移3px */
  box-shadow: 0 4px 16px rgba(0, 0, 0, 0.12); /* 加深阴影 */
}

/* 卡片图片容器样式 */
.card-img-container {
  width: 100%; /* 宽度占满 */
  height: 145px; /* 固定高度120px */
  overflow: hidden; /* 隐藏图片缩放超出部分 */
}

/* 卡片图片样式 */
.card-img {
  width: 100%; /* 宽度占满容器 */
  height: 100%; /* 高度占满容器 */
  object-fit: cover; /* 保持比例，裁剪填充 */
  transition: transform 0.3s ease; /* 缩放过渡动画 */
}

/* 卡片hover时图片缩放效果 */
.featured-card:hover .card-img {
  transform: scale(1.03); /* 放大1.03倍 */
}

/* 卡片信息区域样式 */
.card-info {
  padding: 12px; /* 内边距12px */
}

/* 卡片标题样式 */
.card-title {
  font-size: 16px; /* 字体大小16px */
  font-weight: 500; /* 中等字重 */
  color: #333; /* 深灰色文字 */
  margin: 0; /* 去除默认外边距 */
  display: -webkit-box; /* WebKit盒模型，用于多行省略 */
  -webkit-line-clamp: 2; /* 最多显示2行 */
  -webkit-box-orient: vertical; /* 垂直排列 */
  overflow: hidden; /* 隐藏超出部分 */
  line-height: 1.4; /* 行高1.4倍 */
}

/* 内容网格布局样式 */
.content-grid {
  display: grid; /* 网格布局 */
  grid-template-columns: repeat(4, 1fr); /* 4列布局，每列宽度相等 */
  gap: 20px; /* 网格间距20px */
}

/* 响应式布局：屏幕宽度≤1200px时 */
@media (max-width: 1200px) {
  .content-grid {
    grid-template-columns: repeat(3, 1fr); /* 改为3列布局 */
  }
}

/* 穿透样式：修改element-ui卡片组件的内容区域样式 */
:deep(.el-card__body){
  padding: 0; /* 去除默认内边距 */
  display: flex; /* 使用flex布局 */
  flex-direction: column; /* 垂直方向排列子元素 */
}
</style>
