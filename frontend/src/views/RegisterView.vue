<script setup lang="ts">
import { ref, computed, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { Button } from '@/components/ui/button'
import {
  Card,
  CardContent,
  CardDescription,
  CardFooter,
  CardHeader,
  CardTitle,
} from '@/components/ui/card'
import { Input } from '@/components/ui/input'
import { Label } from '@/components/ui/label'
import { Tabs, TabsContent, TabsList, TabsTrigger } from '@/components/ui/tabs'
import authService from '@/lib/authService'

const route = useRoute()
const router = useRouter()

// Form state
const role = ref<string>('')
const username = ref('')
const password = ref('')
const confirmPassword = ref('')
const email = ref('')
const fullName = ref('')
const phoneNumber = ref('')

// UI state
const error = ref('')
const isLoading = ref(false)
const success = ref(false)

// Initialize role from route query
onMounted(() => {
  const roleParam = Array.isArray(route.query.role) ? route.query.role[0] : route.query.role
  role.value = roleParam || 'student'
})

const handleRoleChange = (newRole: string) => {
  role.value = newRole
}

const handleSubmit = async (e: Event) => {
  e.preventDefault()
  error.value = ''

  // Validate form
  if (password.value !== confirmPassword.value) {
    error.value = '两次输入的密码不匹配'
    return
  }

  if (password.value.length < 6) {
    error.value = '密码必须至少6个字符'
    return
  }

  isLoading.value = true

  try {
    await authService.register({
      username: username.value,
      password: password.value,
      email: email.value,
      fullName: fullName.value,
      phoneNumber: phoneNumber.value,
      role: role.value.toUpperCase() === 'STUDENT' ? 'STUDENT' : 'EMPLOYER',
    })

    success.value = true

    // Redirect to login after successful registration
    setTimeout(() => {
      router.push(`/login?role=${role.value}`)
    }, 2000)
  } catch (err: any) {
    console.error('Registration error:', err)
    if (err.response) {
      // Check for specific error messages from the API
      if (err.response.status === 400) {
        // Bad request - likely validation errors or duplicate
        if (err.response.data && err.response.data.message) {
          error.value = err.response.data.message
        } else if (err.response.data && !err.response.data.success) {
          // Handle the ApiResponse format from our backend
          error.value = err.response.data.message || '用户名或邮箱已被使用'
        } else {
          error.value = '请检查您的输入并重试'
        }
      } else if (err.response.status === 500) {
        // Server error
        error.value = '服务器错误，请稍后再试'
      } else {
        // Other HTTP errors
        error.value = `注册失败 (${err.response.status}): 请稍后再试`
      }
    } else if (err.request) {
      // Network error - no response
      error.value = '网络错误，请检查您的连接'
    } else {
      // Other errors
      error.value = '注册失败，请稍后再试'
    }
  } finally {
    isLoading.value = false
  }
}
</script>
<template>
  <div class="min-h-screen bg-gradient-to-b from-blue-50 to-white">
    <div class="container mx-auto flex min-h-screen flex-col items-center justify-center py-12">
      <RouterLink to="/" class="mb-8 text-xl font-bold text-blue-900"> 大学兼职门户 </RouterLink>

      <Card class="w-full max-w-md">
        <CardHeader class="text-center">
          <CardTitle>创建您的账户</CardTitle>
          <CardDescription>
            注册以{{ role === 'student' ? '寻找兼职工作' : '发布工作机会' }}
          </CardDescription>
        </CardHeader>
        <CardContent>
          <Tabs :model-value="role" @update:model-value="handleRoleChange" class="mb-6">
            <TabsList class="grid w-full grid-cols-2">
              <TabsTrigger value="student">学生</TabsTrigger>
              <TabsTrigger value="employer">雇主</TabsTrigger>
            </TabsList>
            <TabsContent value="student">
              <p class="mb-4 text-sm text-gray-600">
                创建学生账户以搜索并申请符合您时间安排的兼职工作。
              </p>
            </TabsContent>
            <TabsContent value="employer">
              <p class="mb-4 text-sm text-gray-600">创建雇主账户以发布工作机会并找到合格的学生。</p>
            </TabsContent>
          </Tabs>

          <div v-if="error" class="mb-4 rounded-md bg-red-50 p-3 text-sm text-red-600">
            {{ error }}
          </div>
          <div v-if="success" class="mb-4 rounded-md bg-green-50 p-3 text-sm text-green-600">
            注册成功！正在跳转到登录页面...
          </div>

          <form @submit.prevent="handleSubmit">
            <div class="grid gap-4">
              <div class="grid gap-2">
                <Label for="username">用户名</Label>
                <Input id="username" v-model="username" required />
              </div>
              <div class="grid gap-2">
                <Label for="fullName">全名</Label>
                <Input id="fullName" v-model="fullName" required />
              </div>
              <div class="grid gap-2">
                <Label for="email">电子邮箱</Label>
                <Input
                  id="email"
                  type="email"
                  placeholder="m@example.com"
                  v-model="email"
                  required
                />
              </div>
              <div class="grid gap-2">
                <Label for="phoneNumber">电话号码</Label>
                <Input
                  id="phoneNumber"
                  type="tel"
                  placeholder="+86 123 4567 8900"
                  v-model="phoneNumber"
                />
              </div>
              <div class="grid gap-2">
                <Label for="password">密码</Label>
                <Input id="password" type="password" v-model="password" required />
              </div>
              <div class="grid gap-2">
                <Label for="confirmPassword">确认密码</Label>
                <Input id="confirmPassword" type="password" v-model="confirmPassword" required />
              </div>
              <Button type="submit" class="w-full" :disabled="isLoading">
                {{ isLoading ? '注册中...' : '注册' }}
              </Button>
            </div>
          </form>
        </CardContent>
        <CardFooter class="flex flex-col text-center">
          <div class="text-sm text-gray-600">
            已经有账户了吗？
            <RouterLink
              :to="`/login${role ? `?role=${role}` : ''}`"
              class="text-blue-600 hover:underline"
            >
              在此登录
            </RouterLink>
          </div>
        </CardFooter>
      </Card>
    </div>
  </div>
</template>
