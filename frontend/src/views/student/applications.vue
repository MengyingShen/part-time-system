<script setup lang="ts">
import { Badge } from '@/components/ui/badge'
import { Button } from '@/components/ui/button'
import {
  Card,
  CardContent,
  CardDescription,
  CardFooter,
  CardHeader,
  CardTitle,
} from '@/components/ui/card'
import { Clock, MessageCircle, Loader2 } from 'lucide-vue-next'
import { ref, onMounted } from 'vue'
import applicationService, { type Application } from '@/lib/applicationService'
import { toast } from 'vue-sonner'

const applications = ref<Application[]>([])
const isLoading = ref(true)

// Fetch applications from API
const fetchApplications = async () => {
  try {
    isLoading.value = true
    applications.value = await applicationService.getMyApplications()
  } catch (error) {
    console.error('Failed to fetch applications:', error)
    toast.error('获取申请记录失败，请稍后重试')
  } finally {
    isLoading.value = false
  }
}

// Fetch applications when component mounts
onMounted(() => {
  fetchApplications()
})
</script>
<template>
  <div class="grid gap-6">
    <div class="flex items-center justify-between">
      <h1 class="text-2xl font-bold tracking-tight">我的申请</h1>
    </div>

    <div v-if="isLoading" class="flex justify-center items-center py-12">
      <Loader2 class="h-8 w-8 animate-spin text-primary" />
      <span class="ml-2">加载中...</span>
    </div>

    <div v-else-if="applications.length === 0" class="text-center py-12">
      <p class="text-muted-foreground">暂无申请记录</p>
      <Button class="mt-4" @click="fetchApplications">刷新</Button>
    </div>

    <div v-else class="grid gap-4">
      <Card
        v-for="application in applications"
        :key="application.id"
        class="hover:shadow-md transition-shadow"
      >
        <CardHeader class="pb-2">
          <div class="flex items-start justify-between">
            <div>
              <CardTitle class="text-lg">{{ application.jobTitle }}</CardTitle>
              <CardDescription class="mt-1">
                {{ application.company }} · {{ application.location }}
              </CardDescription>
            </div>
            <Badge
              :variant="
                application.status === '已通过'
                  ? 'default'
                  : application.status === '已拒绝'
                    ? 'destructive'
                    : application.status === '面试中'
                      ? 'secondary'
                      : 'outline'
              "
              class="ml-4"
            >
              {{ application.status }}
            </Badge>
          </div>
        </CardHeader>
        <CardContent class="pb-2">
          <div class="flex items-center text-sm text-muted-foreground">
            <Clock class="mr-1 h-4 w-4" />
            <span>申请于 {{ application.appliedDate }}</span>
            <span v-if="application.interviewDate" class="ml-4">
              <MessageCircle class="mr-1 h-4 w-4 inline" />
              面试时间: {{ application.interviewDate }}
            </span>
          </div>
          <div v-if="application.feedback" class="mt-3 p-3 bg-muted/50 rounded-md">
            <p class="text-sm font-medium">反馈信息：</p>
            <p class="text-sm text-muted-foreground mt-1">{{ application.feedback }}</p>
          </div>
        </CardContent>
        <CardFooter class="pt-0">
          <div class="flex w-full justify-between items-center">
            <Button variant="outline" size="sm">查看详情</Button>
            <Button v-if="application.status === '已通过'" size="sm">接受邀请</Button>
          </div>
        </CardFooter>
      </Card>
    </div>
  </div>
</template>
