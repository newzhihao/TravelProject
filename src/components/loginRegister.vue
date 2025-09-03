<template>
  <div class="login-register">
    <div class="canvas-wrapper">
      <canvas id="cvs" width="360" height="360"></canvas>
      <canvas id="cvs2" width="360" height="360"></canvas>
    </div>
    <div class="login-register-container">
      <el-tabs stretch class="login-tabs">
        <el-tab-pane label="登录" lazy>
          <div class="login-box">
            <el-form ref="loginFormRef" :model="loginForm" label-position="top" class="login-form">
              <el-form-item label="账号">
                <el-input v-model="loginForm.username" placeholder="请输入账号" />
              </el-form-item>
              <el-form-item label="密码">
                <el-input v-model="loginForm.password" type="password" show-password placeholder="请输入密码" />
              </el-form-item>
            </el-form>
            <el-button :loading="loginLoading" type="primary" class="submit" @click="handleLogin">
              登录
            </el-button>

          </div>
        </el-tab-pane>
        <el-tab-pane label="注册" lazy>
          <div class="register-box">
            <el-form ref="registerFormRef" :model="registerForm" label-position="top" class="register-form">
              <el-form-item label="账号">
                <el-input v-model="registerForm.authorAccount" placeholder="请输入账号" maxlength="50" />
              </el-form-item>
              <el-form-item label="昵称">
                <el-input v-model="registerForm.authorName" placeholder="请输入昵称" maxlength="50" />
              </el-form-item>
              <el-form-item label="头像">
                <el-upload
                    class="upload-area"
                    action="#"
                    :auto-upload="false"
                    :on-change="handleFileChange"
                    list-type="picture"
                    accept="image/*"
                >
                  <div slot="tip" class="el-upload__tip">支持jpg、png、gif格式，单文件不超过5MB</div>
                </el-upload>
              </el-form-item>
              <el-form-item label="密码">
                <el-input v-model="registerForm.authorPassword" type="password" show-password placeholder="请输入密码" />
              </el-form-item>
              <el-form-item label="确认密码">
                <el-input v-model="registerForm.confirmPassword" type="password" show-password placeholder="再次确认密码" />
              </el-form-item>
            </el-form>
            <el-button :loading="registerLoading" type="primary" class="submit" @click="handleRegister">
              注册
            </el-button>
          </div>
        </el-tab-pane>
      </el-tabs>
    </div>
  </div>
</template>

<script setup>
import { ref, reactive } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import axios from 'axios'

const router = useRouter()

// 登录表单数据
const loginFormRef = ref(null)
const loginForm = reactive({
  username: '',
  password: ''
})

const loginLoading = ref(false)

// 登录方法
const handleLogin = async () => {
  loginLoading.value = true
  try {
    // 这里替换为实际的API请求
    const response = await fetch('/api/login', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify({
        authorAccount: loginForm.username,
        authorPassword: loginForm.password
      })
    })

    const result = await response.json()

    if (result.code === 1) {
      ElMessage.success('登录成功')

      // 存储用户信息到本地
      localStorage.setItem('userInfo', JSON.stringify(result.data))
      // 跳转到首页
      await router.push('/Home')
    } else {
      ElMessage.error(result.msg || '登录失败')
    }
  } catch (error) {
    ElMessage.error('网络错误，请稍后重试')
    console.error('登录错误:', error)
  } finally {
    loginLoading.value = false
  }
}

// 注册表单数据
const registerFormRef = ref(null)
const registerForm = reactive({
  authorAccount: '',
  authorPassword: '',
  authorName: '',
  confirmPassword: ''
})
const registerLoading = ref(false)
const selectedFile = ref(null)

// 处理文件选择变化
const handleFileChange = (file) => {
  selectedFile.value = file.raw
}

// 表单提交方法
const handleRegister = async () => {
  // 表单验证
  if (!registerForm.authorAccount || !registerForm.authorPassword || !registerForm.authorName || !registerForm.confirmPassword ||!selectedFile.value) {
    ElMessage.error('请完善所有必填项（包括头像）')
    return
  }

  if (registerForm.authorPassword!== registerForm.confirmPassword) {
    ElMessage.error('两次输入的密码不一致')
    return
  }

  registerLoading.value = true

  try {
    // 创建FormData对象
    const formData = new FormData()
    formData.append('user', new Blob ([JSON.stringify({
      authorAccount: registerForm.authorAccount,
      authorPassword: registerForm.authorPassword,
      authorName: registerForm.authorName
    })], { type: 'application/json' }))
    formData.append('file', selectedFile.value)

    // 发送POST请求到服务器
    const response = await axios.post('/api/register', formData, {
      headers: {
        'Content-Type': 'multipart/form-data'
      }
    })

    const result = response.data

    if (result.code === 1) {
      ElMessage.success('注册成功，请登录')
      // 跳转到登录页面
      await router.push('/')
    } else {
      ElMessage.error(result.msg || '注册失败')
    }
  } catch (error) {
    ElMessage.error('网络错误，请稍后重试')
    console.error('注册错误:', error)
  } finally {
    registerLoading.value = false
  }
}
</script>

<style scoped>
.upload-area {
  margin-top: 10px;
  padding: 20px;
  border: 2px dashed #d1d5db;
  border-radius: 8px;
  transition: all 0.2s ease;
}

.upload-area:hover {
  border-color: #60a5fa;
  background-color: rgba(96, 165, 250, 0.03);
}
.login-register {
  position: relative;
  display: flex;
  width: 100%;
  height: 100vh;
  justify-content: center;
  align-items: center;
  background: linear-gradient(135deg, #f5f7fa 0%, #e4e5e6 100%);
}


.canvas-wrapper {
  position: relative;
  width: 360px;
  height: 360px;
  margin-right: 30px;
  border-radius: 12px;
  overflow: hidden;
  box-shadow: 0 10px 30px rgba(0, 0, 0, 0.1);
  background-image: url('@/asset/casuel/dallas-mavericks-4.svg');
  background-size: cover;
  background-position: center;
}

#cvs {
  visibility: hidden;
  position: absolute;
}

#cvs2 {
  position: absolute;
  top: 0;
  left: 0;
  transition: transform 0.5s ease;
}
.canvas-wrapper:hover #cvs2 {
  transform: scale(1.05);
}



.login-register-container {
  width: 360px;
  padding: 30px 40px;
  background-color: white;
  border-radius: 12px;
  box-shadow: 0 10px 30px rgba(0, 0, 0, 0.1);
  transition: all 0.3s ease;
}

.login-register-container:hover {
  box-shadow: 0 15px 35px rgba(0, 0, 0, 0.15);
}

.login-tabs {
  width: 100%;
}

.login-box,
.register-box {
  display: flex;
  flex-direction: column;
  align-items: center;
}

.login-form,
.register-form {
  width: 100%;
}

.login-form .el-form-item,
.register-form .el-form-item {
  margin-bottom: 20px;
}

.login-form .el-form-item__label,
.register-form .el-form-item__label {
  font-weight: 500;
  color: #333;
  margin-bottom: 5px;
}

.submit {
  width: 100%;
  margin-top: 10px;
  font-size: 16px;
  height: 44px;
  background: linear-gradient(135deg, #f992af 0%, #f7759a 100%);
  border: none;
  transition: all 0.3s ease;
}

.submit:hover {
  background: linear-gradient(135deg, #f7759a 0%, #f992af 100%);
  transform: translateY(-2px);
  box-shadow: 0 5px 15px rgba(249, 146, 175, 0.4);
}

.submit:active {
  transform: translateY(0);
}

.tips {
  color: #999;
  font-size: 12px;
  text-align: center;
  margin-top: 15px;
}

.tips .agreement {
  color: #5376c8;
  margin-left: 4px;
  cursor: pointer;
  text-decoration: underline;
  transition: color 0.3s ease;
}

.tips .agreement:hover {
  color: #3b5998;
}


@keyframes fadeIn {
  from {
    opacity: 0;
    transform: translateY(10px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
}
</style>
