<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { Input } from '@/components/ui/input'
import { Button } from '@/components/ui/button'
import {
  Card,
  CardContent,
  CardDescription,
  CardFooter,
  CardHeader,
  CardTitle,
} from '@/components/ui/card'
import { Bookmark, Search, Loader2 } from 'lucide-vue-next'
import jobService, { type Job } from '@/lib/jobService'
import { Badge } from '@/components/ui/badge'
import { toast } from 'vue-sonner'

// Reactive state
const keyword = ref('')
const location = ref('')
const jobType = ref('')
const jobListings = ref<Job[]>([])
const isLoading = ref(false)
const isApplying = ref<number | null>(null)
const isTogglingBookmark = ref<number | null>(null)
const savedJobIds = ref<number[]>([])

// Load initial jobs
const loadJobs = async () => {
  try {
    console.log('Loading jobs...')
    isLoading.value = true
    const params = {
      keyword: keyword.value || undefined,
      location: location.value || undefined,
      jobType: jobType.value || undefined,
      status: 'OPEN',
    }

    console.log('Fetching job listings...')
    try {
      const { data } = await jobService.searchJobs(params)
      console.log('Successfully fetched job listings:', data.length)
      jobListings.value = data
    } catch (error) {
      console.error('Failed to load job listings:', error)
      throw error // Re-throw to be caught by the outer catch
    }

    // Load saved jobs
    console.log('Fetching saved jobs...')
    try {
      const savedJobs = await jobService.getSavedJobs()
      console.log('Successfully fetched saved jobs:', savedJobs.length)
      savedJobIds.value = savedJobs.map((job) => job.id)
    } catch (error) {
      console.error('Failed to load saved jobs:', error)
      // Don't re-throw here, as we still want to show the job listings
    }
  } catch (error) {
    console.error('Failed to load jobs:', error)
    toast.error('无法加载职位列表，请稍后重试。')
  } finally {
    isLoading.value = false
  }
}

// Search jobs with filters
const searchJobs = async () => {
  try {
    isLoading.value = true
    const params = {
      keyword: keyword.value?.trim() || undefined,
      location: location.value?.trim() || undefined,
      jobType: jobType.value || undefined,
      status: 'OPEN', // Only show open jobs by default
    }

    console.log('Searching with params:', params)
    const { data } = await jobService.searchJobs(params)
    jobListings.value = data

    // Update URL with search parameters
    const query = {} as any
    if (keyword.value) query.keyword = keyword.value
    if (location.value) query.location = location.value
    if (jobType.value) query.jobType = jobType.value

    // Update URL without page reload
    const queryString = new URLSearchParams(query).toString()
    window.history.pushState({}, '', queryString ? `?${queryString}` : window.location.pathname)
  } catch (error) {
    console.error('Search failed:', error)
    toast.error('搜索失败，请稍后重试。')
  } finally {
    isLoading.value = false
  }
}

// Load initial search params from URL
const loadInitialSearchParams = () => {
  const params = new URLSearchParams(window.location.search)
  if (params.has('keyword')) keyword.value = params.get('keyword') || ''
  if (params.has('location')) location.value = params.get('location') || ''
  if (params.has('jobType')) jobType.value = params.get('jobType') || ''
}

// Apply for a job
const applyForJob = async (jobId: number) => {
  try {
    isApplying.value = jobId
    await jobService.applyForJob(jobId, {
      coverLetter: '我对这个职位非常感兴趣，希望有机会加入贵公司。',
      resumeId: null, // You might want to get this from user's profile
    })

    toast.success('您的职位申请已成功提交。')
  } catch (error: any) {
    console.error('Failed to apply for job:', error)
    toast.error(error.response?.data?.message || '提交申请时出错，请稍后重试。')
  } finally {
    isApplying.value = null
  }
}

// Toggle job bookmark
const toggleBookmark = async (jobId: number) => {
  try {
    isTogglingBookmark.value = jobId
    const { saved } = await jobService.toggleSaveJob(jobId)

    if (saved) {
      savedJobIds.value = [...savedJobIds.value, jobId]
      toast.success('职位已保存到您的收藏夹。')
    } else {
      savedJobIds.value = savedJobIds.value.filter((id) => id !== jobId)
      toast.success('职位已从您的收藏夹中移除。')
    }
  } catch (error) {
    console.error('Failed to toggle bookmark:', error)
    toast.error('保存职位时出错，请稍后重试。')
  } finally {
    isTogglingBookmark.value = null
  }
}

// Format date to relative time
const formatRelativeTime = (dateString: string) => {
  const date = new Date(dateString)
  const now = new Date()
  const diffInSeconds = Math.floor((now.getTime() - date.getTime()) / 1000)

  if (diffInSeconds < 60) return '刚刚'
  if (diffInSeconds < 3600) return `${Math.floor(diffInSeconds / 60)}分钟前`
  if (diffInSeconds < 86400) return `${Math.floor(diffInSeconds / 3600)}小时前`
  if (diffInSeconds < 604800) return `${Math.floor(diffInSeconds / 86400)}天前`

  return date.toLocaleDateString('zh-CN')
}

// Map job type to display text
const getJobTypeText = (type: string) => {
  const types: Record<string, string> = {
    PART_TIME: '兼职',
    INTERNSHIP: '实习',
    TEMPORARY: '临时工',
    VOLUNTEER: '志愿者',
    FULL_TIME: '全职',
    STUDENT_WORK: '学生工作',
  }
  return types[type] || type
}

// Format salary display
const formatSalary = (salary: string) => {
  if (!salary) return '面议'
  // Remove duplicate '/小时' if it exists
  return salary.replace(/(\/小时)+/g, '/小时')
}

// Load initial search params and jobs on component mount
onMounted(async () => {
  loadInitialSearchParams()
  await searchJobs()
  
  // Load saved jobs
  try {
    console.log('Fetching saved jobs...')
    const savedJobs = await jobService.getSavedJobs()
    console.log('Saved jobs response:', savedJobs)
    
    // Ensure we're getting an array and extract job IDs
    if (Array.isArray(savedJobs)) {
      // Use jobId instead of id to match the actual job listings
      savedJobIds.value = savedJobs.map(job => job.jobId)
      console.log('Mapped saved job IDs:', savedJobIds.value)
      console.log('Current job listings:', jobListings.value.map(j => ({id: j.id, title: j.title})))
    } else {
      console.error('Saved jobs is not an array:', savedJobs)
      savedJobIds.value = []
    }
  } catch (error) {
    console.error('Failed to load saved jobs:', error)
  }
})
</script>

<template>
  <div class="grid gap-6">
    <h1 class="text-2xl font-bold">查找工作</h1>

    <div class="grid gap-4">
      <Card>
        <CardContent class="pt-6">
          <form @submit.prevent="searchJobs" class="flex flex-col md:flex-row gap-4">
            <div class="flex-1 relative">
              <Search class="absolute left-3 top-3 h-4 w-4 text-muted-foreground" />
              <Input placeholder="搜索职位关键词" class="pl-9" v-model="keyword" />
            </div>
            <div class="flex-1">
              <Input placeholder="地点" v-model="location" />
            </div>
            <div class="flex-1">
              <select
                class="w-full h-10 rounded-md border border-input bg-background px-3 py-2"
                v-model="jobType"
              >
                <option value="">工作类型</option>
                <option value="PART_TIME">兼职</option>
                <option value="INTERNSHIP">实习</option>
                <option value="TEMPORARY">临时工</option>
                <option value="VOLUNTEER">志愿者</option>
              </select>
            </div>
            <div>
              <Button type="submit" :disabled="isLoading">
                <Loader2 v-if="isLoading" class="mr-2 h-4 w-4 animate-spin" />
                {{ isLoading ? '搜索中...' : '搜索' }}
              </Button>
            </div>
          </form>
        </CardContent>
      </Card>
    </div>

    <div
      v-if="isLoading && jobListings.length === 0"
      class="flex justify-center items-center py-12"
    >
      <Loader2 class="h-8 w-8 animate-spin text-primary" />
      <span class="ml-2">加载中...</span>
    </div>

    <div v-else-if="jobListings.length === 0" class="text-center py-12 text-muted-foreground">
      没有找到匹配的职位。请尝试修改搜索条件。
    </div>

    <div v-else class="grid gap-4">
      <Card v-for="job in jobListings" :key="job.id" class="mb-4">
        <CardHeader>
          <div class="flex justify-between">
            <div>
              <div class="flex items-center gap-2">
                <CardTitle class="text-lg">{{ job.title }}</CardTitle>
                <Badge variant="outline">
                  {{ getJobTypeText(job.type) }}
                </Badge>
                <Badge
                  v-if="job.salary"
                  variant="outline"
                  class="ml-auto text-green-600 font-medium"
                >
                  {{ formatSalary(job.salary) }}
                </Badge>
              </div>
              <CardDescription class="mt-1">
                {{ job.company }} · {{ job.location }}
              </CardDescription>
            </div>
            <Button
              variant="ghost"
              size="icon"
              :disabled="isTogglingBookmark === job.id"
              @click="toggleBookmark(job.id)"
            >
              <Bookmark
                class="h-5 w-5"
                :class="{ 'fill-current text-yellow-400': savedJobIds.includes(job.id) }"
              />
            </Button>
          </div>
        </CardHeader>
        <CardContent>
          <p class="text-sm text-muted-foreground">{{ job.description }}</p>
        </CardContent>
        <CardFooter class="flex justify-between items-center">
          <span class="text-sm text-muted-foreground"> 发布于 {{ job.postedAt || '最近' }} </span>
          <Button @click="applyForJob(job.id)" :disabled="isApplying === job.id">
            <Loader2 v-if="isApplying === job.id" class="mr-2 h-4 w-4 animate-spin" />
            {{ isApplying === job.id ? '申请中...' : '立即申请' }}
          </Button>
        </CardFooter>
      </Card>
    </div>
  </div>
</template>
