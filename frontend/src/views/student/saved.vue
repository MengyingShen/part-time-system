<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { Button } from '@/components/ui/button'
import {
  Card,
  CardContent,
  CardDescription,
  CardFooter,
  CardHeader,
  CardTitle,
} from '@/components/ui/card'
import { Bookmark, Trash2, Loader2 } from 'lucide-vue-next'
import { useRouter } from 'vue-router'
import { getSavedJobs, unsaveJob, type SavedJob } from '@/lib/savedJobService'
// import { useToast } from "@/components/ui/toast/use-toast"
import { toast } from 'vue-sonner'

const router = useRouter()
// const { toast } = useToast()

const savedJobs = ref<SavedJob[]>([])
const isLoading = ref(true)
const error = ref<string | null>(null)

// 加载保存的工作
const loadSavedJobs = async () => {
  try {
    isLoading.value = true
    error.value = null
    savedJobs.value = await getSavedJobs()
  } catch (err) {
    console.error('Failed to load saved jobs:', err)
    error.value = '加载保存的工作失败，请稍后重试'
    toast({
      title: '错误',
      description: error.value,
      variant: 'destructive',
    })
  } finally {
    isLoading.value = false
  }
}

// 取消保存工作
const removeSavedJob = async (jobId: number) => {
  try {
    await unsaveJob(jobId)
    savedJobs.value = savedJobs.value.filter((job) => job.jobId !== jobId)
    toast({
      title: '成功',
      description: '已从收藏中移除',
    })
  } catch (err) {
    console.error('Failed to unsave job:', err)
    toast({
      title: '错误',
      description: '移除收藏失败，请稍后重试',
      variant: 'destructive',
    })
  }
}

// 申请工作
const applyForJob = (jobId: number) => {
  // 实现申请工作的逻辑
  console.log('Applying for job:', jobId)
  router.push(`/student/apply/${jobId}`)
}

// 浏览工作
const browseJobs = () => {
  router.push('/student/search')
}

// 组件挂载时加载数据
onMounted(() => {
  loadSavedJobs()
})
</script>

<template>
  <div class="grid gap-6">
    <div class="flex items-center justify-between">
      <h1 class="text-2xl font-bold tracking-tight">保存的工作</h1>
    </div>

    <!-- 加载状态 -->
    <div v-if="isLoading" class="flex justify-center items-center py-12">
      <Loader2 class="h-8 w-8 animate-spin text-primary" />
      <span class="ml-2">加载中...</span>
    </div>

    <!-- 错误状态 -->
    <div v-else-if="error" class="text-center py-12">
      <p class="text-destructive mb-4">{{ error }}</p>
      <Button @click="loadSavedJobs">重试</Button>
    </div>

    <!-- 空状态 -->
    <Card v-else-if="savedJobs.length === 0">
      <CardContent class="flex flex-col items-center justify-center py-16">
        <Bookmark class="h-12 w-12 text-muted-foreground mb-4" />
        <p class="text-center text-muted-foreground mb-4">您还没有保存任何工作</p>
        <Button @click="browseJobs">
          <Bookmark class="mr-2 h-4 w-4" />
          浏览可用工作
        </Button>
      </CardContent>
    </Card>

    <!-- 工作列表 -->
    <div v-else class="grid gap-4">
      <Card v-for="job in savedJobs" :key="job.id" class="hover:shadow-md transition-shadow">
        <CardHeader class="pb-2">
          <div class="flex justify-between items-start">
            <div>
              <CardTitle class="text-lg">{{ job.title }}</CardTitle>
              <CardDescription class="mt-1">
                {{ job.company }} · {{ job.location }}
              </CardDescription>
            </div>
            <Button
              variant="ghost"
              size="sm"
              @click="removeSavedJob(job.jobId)"
              class="text-muted-foreground hover:text-destructive h-8 w-8 p-0"
              title="取消收藏"
            >
              <Trash2 class="h-4 w-4" />
            </Button>
          </div>
        </CardHeader>
        <CardContent class="pb-2">
          <div class="flex items-center text-sm text-muted-foreground mb-2">
            <span
              class="inline-flex items-center px-2.5 py-0.5 rounded-full text-xs font-medium bg-blue-100 text-blue-800 dark:bg-blue-900 dark:text-blue-100 mr-2"
            >
              {{ job.type }}
            </span>
            <span class="text-foreground font-medium">{{ job.salary }}</span>
            <span class="mx-2">·</span>
            <span>保存于 {{ job.savedAt }}</span>
          </div>
          <p class="text-sm text-muted-foreground line-clamp-2">{{ job.description }}</p>
        </CardContent>
        <CardFooter class="pt-0">
          <div class="flex w-full justify-between items-center">
            <Button variant="outline" size="sm" @click="applyForJob(job.jobId)"> 立即申请 </Button>
            <Button variant="ghost" size="sm" @click="$router.push(`/student/jobs/${job.jobId}`)">
              查看详情
            </Button>
          </div>
        </CardFooter>
      </Card>
    </div>
  </div>
</template>
