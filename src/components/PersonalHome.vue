<template>
  <div class="personal-home">
    <!-- 用户信息区域 -->
    <div class="user-info">
      <el-avatar :src="userInfo.authorPhotoUrl" size="large" shape="circle"></el-avatar>
      <div class="user-details">
        <h2>{{ userInfo.authorName }}</h2>
        <p>账号: {{ userInfo.authorAccount }}</p>
      </div>
    </div>

    <!-- 导航标签页 -->
    <el-tabs v-model="activeTab" @tab-click="handleTabClick">
      <el-tab-pane label="作品" name="works">
        <div class="works-container">
          <el-row :gutter="20">
            <el-col
                v-for="(card) in CardList"
                :key="card.contentId"
                :span="6"
            >
              
              <el-card class="box-card">
                <div class="card-container">
                  <div class="content-area" :style="{ cursor: 'pointer' }" @click="CheckCard(card)">
                    <img :src="card.coverImageUrl" alt="封面图片">
                  </div>
                  <div class="title-area">
                    <h3>{{ card.title }}</h3>
                    <div class="delete-icon" @click="deleteWork(card.contentId)">
                      <el-icon><Delete /></el-icon>
                    </div>
                  </div>
                </div>
              </el-card>
            </el-col>
          </el-row>
        </div>
      </el-tab-pane>
      <el-tab-pane label="收藏" name="collections">
        <div class="collections-container">
          <el-row :gutter="20">
            <el-col v-for="collection in CollectionList" :key="collection.contentId" :span="6">
              <el-card class="box-card" >
                <div class="card-container">
                  <div class="content-area" :style="{ cursor: 'pointer' }" @click="checkCollection(collection)">
                    <img :src="collection.coverImageUrl" alt="封面图片">
                  </div>
                  <div class="title-area">
                    <h3>{{ collection.title }}</h3>
                    <div class="delete-icon" @click="deleteCollection(collection.contentId)">
                      <el-icon><Delete /></el-icon>
                    </div>
                  </div>
                </div>
              </el-card>
            </el-col>
          </el-row>
        </div>
      </el-tab-pane>
    </el-tabs>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { useRouter } from 'vue-router';
import { getPersonalContents, getUserById, getCollections } from '@/api/contents';
import { Delete } from "@element-plus/icons-vue";
import { ElMessage, ElMessageBox } from "element-plus"; // 引入MessageBox
import axios from "axios";

const router = useRouter();
const userInfoStr = localStorage.getItem('userInfo');
const userInfo = ref({});
const userAccount = userInfoStr ? JSON.parse(userInfoStr).authorAccount : null;
const CardList = ref([]);
const CollectionList = ref([]);
const userCollections = ref([]);
const activeTab = ref('works');

onMounted(() => {
  const data = JSON.parse(localStorage.getItem('userInfo'))
  if (data) {
    userInfo.value = data
  }
  fetchUserInfo();
  fetchUserWorks();
  fetchUserCollections();
});

// 获取用户信息
const fetchUserInfo = async () => {
  try {
    const result = await getUserById({ userAccount });
    userInfo.value = result.data;
  } catch (error) {
    console.error('获取用户信息失败:', error);
  }
};

// 获取用户作品
const fetchUserWorks = async () => {
  try {
    const result = await getPersonalContents(userInfo.value.authorAccount);
    CardList.value = result.data;
  } catch (error) {
    console.error('获取用户作品失败:', error);
  }
};

// 获取用户收藏
const fetchUserCollections = async () => {
  try {
    const result = await getCollections(userInfo.value.authorAccount);
    CollectionList.value = result.data;
  } catch (error) {
    console.error('获取用户收藏失败:', error);
  }
};

const CheckCard = (card) => {
  router.push(`/contents/${card.contentId}`);
};

const checkCollection = (collection) => {
  router.push(`/contents/${collection.contentId}`);
};

const handleTabClick = (tab) => {
  activeTab.value = tab.name;
};

// 删除作品（带确认弹窗）
const deleteWork = async (contentId) => {
  try {
    // 显示确认弹窗
    await ElMessageBox.confirm(
        '确定要删除该作品吗？删除后将无法恢复',
        '删除确认',
        {
          confirmButtonText: '确认删除',
          cancelButtonText: '取消',
          type: 'warning',
        }
    );

    // 用户确认后执行删除
    await axios.delete(`/api/deleteContent/${contentId}`);
    ElMessage.success('作品删除成功');
    await fetchUserWorks(); // 重新获取作品列表
  } catch (error) {
    // 如果是取消操作，不显示错误信息
    if (error.name !== 'CanceledError') {
      console.error('删除作品失败:', error);
      ElMessage.error('作品删除失败');
    }
  }
};

// 删除收藏（带确认弹窗）
const deleteCollection = async (contentId) => {
  try {
    // 显示确认弹窗
    await ElMessageBox.confirm(
        '确定要取消收藏该内容吗？',
        '取消收藏',
        {
          confirmButtonText: '确认',
          cancelButtonText: '取消',
          type: 'info',
        }
    );

    // 用户确认后执行删除
    await axios.delete(`/api/deleteCollection/${contentId}`);
    ElMessage.success('收藏删除成功');
    await fetchUserCollections(); // 重新获取收藏列表
  } catch (error) {
    // 如果是取消操作，不显示错误信息
    if (error.name !== 'CanceledError') {
      console.error('删除收藏失败:', error);
      ElMessage.error('收藏删除失败');
    }
  }
};
</script>

<style scoped>
/* 保持原有样式不变 */
.personal-home {
  padding: 20px;
  max-width: 1200px;
  margin: 0 auto;
}

.user-info {
  display: flex;
  align-items: center;
  margin-bottom: 20px;
}

.user-details {
  margin-left: 20px;
}

.user-details h2 {
  margin: 0;
  font-size: 24px;
}

.user-details p {
  margin: 5px 0 0;
  color: #666;
}

.works-container,
.collections-container {
  margin-top: 20px;
}

:deep(.el-card__body){
  padding: 0;
  height: 100%;
}

.card-container {
  display: flex;
  flex-direction: column;
  height: 100%;
}

.content-area {
  background-color: #3b5998;
  color: white;
  height: 80%;
  box-sizing: border-box;
  position: relative;
}

.content-area img {
  width: 100%;
  height: 100%;
  object-fit: cover;
  position: absolute;
  top: 0;
  left: 0;
}

.title-area {
  padding: 15px 20px;
  background-color: white;
  color: #333;
  height: 20%;
  box-sizing: border-box;
  display: flex;
  align-items: center;
  justify-content: space-between;
}

.delete-icon {
  cursor: pointer;
}

.title-area h3 {
  margin: 0;
  font-size: 18px;
  font-weight: 500;
  line-height: 1.3;
  overflow: hidden;
  text-overflow: ellipsis;
  display: -webkit-box;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
  line-clamp: 2;
}

.box-card {
  margin-bottom: 20px;
  height: 240px;
  overflow: hidden;
}
</style>