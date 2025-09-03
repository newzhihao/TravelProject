//内容卡片列表
//展示内容
<template>
  <div v-for="(card, index) in cardList" :key="index" class="card-item" @click="CheckCard(card)">
    <el-card class="card">
      <div class="card-img-container">
        <img :src="card.coverImageUrl" alt="封面图片" class="card-img">
        <el-rate
            v-model="value"
            disabled
            show-score
            text-color="#ff9900"
            class="card-rate"
            ></el-rate>
      </div>
      <div class="card-content">
        <h3 class="card-title">{{ card.title }} </h3>

      </div>
    </el-card>
  </div>
</template>

<script setup>
import { getCardList } from "@/api/contents";
import { useRouter } from "vue-router";
import { ref, onMounted } from 'vue';

const cardList = ref([]);
const router = useRouter();
const value = ref(3)
const query_contents = async () => {
  try {
    const result = await getCardList();
    cardList.value = result.data.slice(2);
  } catch (error) {
    console.error('获取卡片数据失败:', error);
  }
};

const CheckCard = (card) => {
  router.push(`/contents/${card.contentId}`);
};

onMounted(() => {
  query_contents();
});
</script>

<style scoped>
.card-rate{
  margin-left: 10px;
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