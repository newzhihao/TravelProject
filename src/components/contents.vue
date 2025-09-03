<template>
  <!-- 保持原有模板内容不变 -->
  <div class="article-detail-page">
    <!-- 标题区 -->
    <div class="title-container">
      <h1 class="article-title">{{cardList.title}}</h1>
      <div class="author-info">
        <el-avatar size="default" :src="author.authorPhotoUrl"></el-avatar>
        <div class="author-details">
          <span class="author-name">{{author.authorName}}</span>
          <span class="publish-time">{{cardList.publishTime}}</span>
        </div>
      </div>
      <div class="tags-container">
        <el-tag v-for="(tag, index) in tags" :key="index" type="primary">{{tag}} </el-tag>

      </div>
      <el-rate
          v-model="value"
          disabled
          show-score
          text-color="#ff9900"
          class="card-rate"
      ></el-rate>
    </div>
    <!-- 主体内容区 -->
    <div class="content-container">
      <div class="main-content">
        <div class="article-content">
          <p>{{cardList.summary}}</p>
          <ul>
            <li v-for="image in imagesList" :key="image.imageId">
              <p>{{image.imageCaption}}</p>
              <img :src="image.imageUrl" alt="文章图片" class="image">
            </li>
          </ul>
          <p class="content">{{cardList.content}}</p>
        </div>
        <!-- 互动区 -->
        <div class="interaction-container">
          <el-button type="primary" class="interaction-btn" @click="collect">
            <span>收藏</span>
          </el-button>
        </div>
        <span>
          <el-rate v-model="value" clearable  size="large"/>
          </span>
      </div>
    </div>
    <!-- 评论区 -->
    <div class="comment-container">
      <h3 class="comment-title">评论区 ({{ comments.length}})</h3>
      <el-divider></el-divider>
      <!-- 新增评论输入框 -->
      <div class="comment-input-container">
        <el-input
            v-model="newCommentText"
            type="textarea"
            :rows="4"
            placeholder="写下你的评论..."
            class="comment-input">
        </el-input>
        <el-button type="primary" style="margin-top: 10px;" @click="publishComment">
          发表评论
        </el-button>
      </div>
      <el-divider></el-divider>
      <div class="comment-list">
        <div class="comment-item" v-for="(comment, index) in comments" :key="index">
          <el-avatar size="small" :src="comment.authorPhotoUrl" ></el-avatar>
          <div class="comment-content">
            <div class="comment-header">
              <span class="comment-author">{{ comment.authorName}}</span>
              <span class="comment-time">{{ comment.commentTime}}</span>
            </div>
            <p class="comment-text">{{ comment.commentText }}</p>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import {ref, computed, onMounted, reactive} from 'vue';
import {useRoute} from "vue-router";
import {queryContents,queryImageById,getUserById,getCategoriesById,getCommentsById} from "@/api/contents";
import router from "@/router";
import axios from "axios";
import {Edit, Star} from "@element-plus/icons-vue";
import {ElMessage} from "element-plus";
const cardList = ref([])
const imagesList = ref([])
const author = ref({})
const tags = ref([])//标签
const $route = useRoute();
const value = ref(3);
const query_contents_ById = async()=>{
  const result = await queryContents($route.params.contentId);
  cardList.value = result.data;
  await query_user_ById(result.data.authorAccount);
}
const query_images_ById = async()=>{
  const images = await queryImageById($route.params.contentId);
  imagesList.value = images.data;
}
const query_user_ById = async(account)=>{
  try{
    const userData = await getUserById(account);
    author.value = userData.data;
  }
  catch(error){
    console.log(error);
  }
}
const query_tags_ById = async()=>{
  const result = await getCategoriesById($route.params.contentId);
  tags.value = result.data;
}

// 评论数据
const comments = ref([]);
const getComments = async()=>{
  const result = await getCommentsById($route.params.contentId);
  comments.value = result.data;

}
const newCommentText = ref('');
const contentId = ref($route.params.contentId);
// 从localStorage获取用户账号
const userInfoStr = localStorage.getItem('userInfo');
const userAccount = userInfoStr ? JSON.parse(userInfoStr).authorAccount : null;

const publishComment = async () => {
  if (!newCommentText.value.trim()) {
    alert('评论内容不能为空');
    return;
  }
  if (!userAccount) {
    alert('请先登录');
    await router.push("/LoginRegister")
    return;
  }
  if (!contentId.value) {
    alert('无法获取内容ID，请刷新页面重试');
    return;
  }

  try {
    const commentData = {
      contentId: contentId.value,
      userAccount: userAccount,
      commentText: newCommentText.value
    };

    const response = await axios.post('/api/insertComment', commentData);

    if (response.data.code === 1) {
      newCommentText.value = '';
      await getComments();
    } else {
      alert('评论发布失败');
    }
  } catch (error) {
    console.error('评论提交失败', error);
    alert('网络异常，请稍后重试');
  }
};
// 收藏按钮
const collect = async () => {
  const collectData = reactive({
    authorAccount: '',
    contentId: '',
  })
  collectData.authorAccount = userAccount;
  collectData.contentId = $route.params.contentId;

  try {
    await axios.post('/api/collect', collectData);
    const successMsg = ElMessage.success('收藏成功，快去看看吧！');
    setTimeout(() => {
      successMsg.close();
    }, 1000);

  } catch (error) {
    ElMessage.error({
      message: '收藏失败，请稍后重试',
      duration: 3000
    });
    console.error('收藏错误:', error);
  }
}

onMounted(
    ()=>{
      query_contents_ById();
      query_images_ById();
      query_tags_ById();
      getComments();
    }
)
</script>

<style scoped>
.article-detail-page {
  padding: 30px 50px;
  max-width: 1200px;
  margin: 0 auto;
  background-color: #fff;
  min-height: 100vh;
  box-shadow: 0 0 20px rgba(0, 0, 0, 0.05);
}

/* 标题区域优化 */
.title-container {
  margin-bottom: 30px;
  padding-bottom: 20px;
  border-bottom: 1px solid #f0f0f0;
}

.article-title {
  font-size: 28px;
  font-weight: 700;
  margin-bottom: 20px;
  color: #1a1a1a;
  line-height: 1.4;
  letter-spacing: -0.5px;
}

.author-info {
  display: flex;
  align-items: center;
  margin-bottom: 15px;
}

.author-info .el-avatar {
  width: 40px;
  height: 40px;
  margin-right: 12px;
  border: 2px solid #f5f5f5;
}

.author-details {
  display: flex;
  flex-direction: column;
}

.author-name {
  font-weight: 500;
  color: #333;
  margin-bottom: 3px;
  font-size: 15px;
}

.publish-time {
  color: #8c8c8c;
  font-size: 13px;
}

.tags-container {
  margin-top: 15px;
  display: flex;
  gap: 10px;
  flex-wrap: wrap;
}

.tags-container .el-tag {
  background-color: #f5f7fa;
  color: #4e5969;
  border: none;
  padding: 4px 12px;
  font-size: 13px;
  border-radius: 20px;
}

/* 内容区域优化 */
.content-container {
  margin-bottom: 40px;
}

.main-content {
  width: 100%;
}

.article-content {
  margin-bottom: 40px;
  line-height: 1.8;
  color: #333;
  font-size: 16px;
}

.article-content p {
  margin-bottom: 1.5em;
  line-height: 1.8;
}

.image {
  width: 100%;
  max-width: 800px;
  height: auto;
  margin: 20px auto;
  border-radius: 8px;
  display: block;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.08);
  transition: transform 0.3s ease;
}

.image:hover {
  transform: translateY(-3px);
}

.content {
  width: 100%;
  white-space: pre-line;
  color: #333;
  font-size: 16px;
}

/* 互动区优化 */
.interaction-container {
  display: flex;
  gap: 20px;
  margin-top: 30px;
  padding: 15px 0;
  border-top: 1px solid #f0f0f0;
  border-bottom: 1px solid #f0f0f0;
}

.interaction-btn {
  font-size: 16px;
  color: #fcfdfd;
  padding: 8px 16px;
  border-radius: 4px;
  transition: all 0.2s ease;
}

.interaction-btn:hover {
  color: #409eff;
  background-color: #f0f7ff;
}

.interaction-btn .el-icon {
  margin-right: 5px;
}

/* 评论区优化 */
.comment-container {
  padding: 30px;
  background-color: #f9fafb;
  border-radius: 12px;
  margin-top: 40px;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.04);
}

.comment-title {
  font-size: 20px;
  font-weight: 600;
  margin-bottom: 20px;
  color: #1a1a1a;
  display: flex;
  align-items: center;
}


.comment-list {
  margin-bottom: 20px;
}

.comment-item {
  display: flex;
  margin-bottom: 25px;
  padding-bottom: 25px;
  border-bottom: 1px solid #f0f0f0;
  transition: transform 0.2s ease;
}

.comment-item:hover {
  transform: translateX(5px);
}

.comment-item:last-child {
  border-bottom: none;
  margin-bottom: 0;
  padding-bottom: 0;
}

.comment-item .el-avatar {
  margin-right: 12px;
  width: 40px;
  height: 40px;
}

.comment-content {
  flex: 1;
}

.comment-header {
  display: flex;
  justify-content: space-between;
  margin-bottom: 8px;
}

.comment-author {
  font-weight: 500;
  color: #333;
}

.comment-time {
  color: #999;
  font-size: 12px;
}

.comment-text {
  font-size: 15px;
  line-height: 1.6;
  color: #555;
  background-color: #fff;
  padding: 12px 15px;
  border-radius: 8px;
  box-shadow: 0 1px 3px rgba(0, 0, 0, 0.05);
}

/* 评论输入框优化 */
.comment-input-container {
  margin-bottom: 25px;
}

.comment-input .el-textarea__inner {
  border-radius: 8px;
  padding: 15px;
  font-size: 15px;
  border-color: #e5e7eb;
  resize: vertical;
  transition: all 0.2s ease;
}

.comment-input .el-textarea__inner:focus {
  border-color: #409eff;
  box-shadow: 0 0 0 3px rgba(64, 158, 255, 0.1);
}

.comment-input-container .el-button {
  padding: 10px 20px;
  font-size: 15px;
  border-radius: 6px;
  transition: all 0.2s ease;
}

.comment-input-container .el-button:hover {
  transform: translateY(-2px);
}

/* 响应式调整 */
@media (max-width: 768px) {
  .article-detail-page {
    padding: 20px 15px;
  }

  .article-title {
    font-size: 24px;
  }

  .comment-container {
    padding: 20px 15px;
  }
}
</style>

