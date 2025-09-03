<template>
  <div class="search-result-page">
    <Header />
    <div class="result-container">
      <h2>搜索结果: "{{ $route.query.words }}"</h2>

      <!-- 使用网格容器包裹卡片 -->
      <div class="card-grid">
        <div v-for="(card, index) in resultList" :key="index" class="card-item" @click="goToContent(card)">
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
import { ref, onMounted } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import Header from '@/components/Header.vue';

const route = useRoute();
const router = useRouter();
const resultList = ref([]);

onMounted(() => {
  // 从路由参数中获取搜索结果
  if (route.query.results) {
    resultList.value = JSON.parse(route.query.results);
  }
});

const goToContent = (item) => {
  router.push(`/contents/${item.contentId}`);
};
</script>

<style scoped>
.result-container {
  max-width: 1200px;
  margin: 20px auto;
  padding: 0 20px;
}

/* 网格容器 - 实现每行两个卡片 */
.card-grid {
  display: grid;
  grid-template-columns: repeat(2, 1fr); /* 两列等宽布局 */
  gap: 20px; /* 卡片之间的间距 */
  margin-top: 20px;
}

/* 卡片项容器样式 */
.card-item {
  width: 100%;
}

/* 卡片主体样式 */
.card {
  border: none;
  border-radius: 8px;
  overflow: hidden;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.08);
  transition: all 0.2s ease;
  height: 100%;
}

.card:hover {
  transform: translateY(-5px);
  box-shadow: 0 8px 16px rgba(0, 0, 0, 0.12);
}

/* 穿透样式：修改element-ui卡片组件的内容区域样式 */
:deep(.el-card__body){
  padding: 0;
  height: 100%;
  display: flex;
  flex-direction: column;
}

/* 卡片图片容器样式 */
.card-img-container {
  width: 100%;
  padding-top: 56.25%; /* 16:9的比例 */
  position: relative;
  overflow: hidden;
}

/* 卡片图片样式 */
.card-img {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  object-fit: cover;
  transition: transform 0.3s ease;
}

.card:hover .card-img {
  transform: scale(1.05); /* 鼠标悬停时图片轻微放大 */
}

/* 卡片内容区域样式 */
.card-content {
  padding: 12px;
  flex: 1;
  display: flex;
  flex-direction: column;
}

/* 卡片标题样式 */
.card-title {
  font-size: 16px;
  font-weight: 500;
  color: #333;
  margin: 0;
  display: -webkit-box;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
  overflow: hidden;
  line-height: 1.4;
  flex: 1;
}

/* 响应式调整 - 在小屏幕上改为每行一个卡片 */
@media (max-width: 768px) {
  .card-grid {
    grid-template-columns: 1fr; /* 单列布局 */
  }
}
</style>
