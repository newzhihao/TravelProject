//组件介绍：
//这是一个Vue组件，用于创建一个轮播图，显示一组图片和对应的文字内容。

<template>
<!--  indicator-position="inside"轮播指示器（如小圆点）的位置在轮播容器内部。-->
  <el-carousel
      :autoplay="true"
      indicator-position="inside"
      height="400px"
      class="carousel"
      trigger="click"
  >
    <el-carousel-item
        v-for="(item, index) in carouselList"
        :key="index"
    >
      <div class="carousel-item">
        <img
            :src="item.image"
            alt="轮播图片"
            class="carousel-img"
        >
        <div class="carousel-info">
          <h3 class="carousel-title">{{ item.title }}</h3>
          <p class="carousel-desc">{{ item.desc || '精彩内容不容错过' }}</p>
        </div>
      </div>
    </el-carousel-item>
  </el-carousel>
</template>

<script setup>
const carouselList = [
  {
    image: 'src/asset/casuel/01.png',
    title: '独行侠总冠军！！！',
    desc: '精彩内容尽在旅记'
  },
  {
    image: 'src/asset/casuel/02.png',
    title: '千子树下，莫离莫弃',
    desc: '探索未知的旅程'
  },
  {
    image: 'src/asset/casuel/03.png',
    title: '山水之间的宁静',
    desc: '寻找内心的平静'
  },
  {
    image: 'src/asset/casuel/04.png',
    title: '城市与自然的融合',
    desc: '现代与传统的碰撞'
  }
];
</script>

<style scoped>
/* 轮播容器样式 */
.carousel {
  border-radius: 8px; /* 容器圆角 */
  overflow: hidden; /* 隐藏超出容器的内容，配合圆角使用 */
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1); /* 容器阴影效果，增强层次感 */
}

/* 轮播项样式 */
.carousel-item {
  position: relative; /* 相对定位，作为子元素绝对定位的参考 */
  width: 100%; /* 宽度占满容器 */
  height: 100%; /* 高度占满容器 */
}

/* 轮播图片样式 */
.carousel-img {
  width: 100%; /* 图片宽度占满父元素 */
  height: 100%; /* 图片高度占满父元素 */
  object-fit: cover; /* 保持图片比例，裁剪多余部分填满容器 */
}

/* 轮播信息区域样式（标题和描述） */
.carousel-info {
  position: absolute; /* 绝对定位，相对于.carousel-item定位 */
  bottom: 0; /* 定位到底部 */
  left: 0; /* 定位到左侧 */
  right: 0; /* 定位到右侧 */
  padding: 20px; /* 内边距 */
  background: linear-gradient(transparent, rgba(0, 0, 0, 0.7)); /* 渐变背景，从透明到黑色半透明 */
  color: #fff; /* 文字颜色为白色 */
}

/* 轮播标题样式 */
.carousel-title {
  font-size: 20px; /* 字体大小 */
  font-weight: 600; /* 字体粗细 */
  margin: 0 0 8px 0; /* 外边距，底部8px，其他方向0 */
}

/* 轮播描述样式 */
.carousel-desc {
  font-size: 14px; /* 字体大小 */
  margin: 0; /* 清除默认外边距 */
  opacity: 0.9; /* 透明度稍低，与标题区分层次 */
}

/* 穿透样式：修改element-ui轮播指示器位置（内部放置时） */
::v-deep .el-carousel__indicators--inside {
  bottom: 20px; /* 指示器距离底部20px，与信息区域保持一致 */
}

/* 穿透样式：修改element-ui轮播激活状态指示器的样式 */
::v-deep .el-carousel__indicator.is-active button {
  background-color: #FB7299; /* 激活状态的指示器颜色 */
}
</style>