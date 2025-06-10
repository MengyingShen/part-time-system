<script setup lang="ts">
import { ref, onMounted } from 'vue';
import { Button } from "@/components/ui/button";
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from "@/components/ui/card";
import { Progress } from "@/components/ui/progress";
import { PlusIcon, Download, UploadIcon, EditIcon } from "lucide-vue-next";
import { resumeService, type ResumeDto } from '@/lib/resumeService';

const resume = ref<ResumeDto | null>(null);
const loading = ref(true);
const error = ref<string | null>(null);

// 加载简历数据
const loadResume = async () => {
  try {
    loading.value = true;
    resume.value = await resumeService.getResume();
  } catch (err) {
    console.error('加载简历失败:', err);
    error.value = '加载简历失败，请稍后重试';
  } finally {
    loading.value = false;
  }
};

// 下载简历
const downloadResume = () => {
  // 实现下载逻辑
  console.log('下载简历');
};

// 上传简历
const handleFileUpload = (event: Event) => {
  const input = event.target as HTMLInputElement;
  if (input.files && input.files[0]) {
    const file = input.files[0];
    console.log('上传文件:', file);
    // 实现上传逻辑
  }
};

// 触发文件上传
const triggerFileUpload = () => {
  const fileInput = document.createElement('input');
  fileInput.type = 'file';
  fileInput.accept = '.pdf,.doc,.docx';
  fileInput.onchange = handleFileUpload;
  fileInput.click();
};

// 组件挂载时加载数据
onMounted(() => {
  loadResume();
});
</script>
<template>
  <div class="grid gap-6">
    <h1 class="text-2xl font-bold">我的简历</h1>
    
    <Card>
      <CardHeader>
        <div class="flex justify-between items-center">
          <div>
            <CardTitle>个人简历</CardTitle>
            <CardDescription>完整的简历可以提高您被录用的机会</CardDescription>
          </div>
          <div class="flex gap-2">
            <Button 
              variant="outline" 
              size="sm" 
              class="gap-1"
              @click="triggerFileUpload"
              :disabled="loading"
            >
              <UploadIcon class="h-4 w-4" />
              上传
            </Button>
            <Button 
              variant="outline" 
              size="sm" 
              class="gap-1"
              @click="downloadResume"
              :disabled="!resume || loading"
            >
              <Download class="h-4 w-4" />
              下载
            </Button>
          </div>
        </div>
      </CardHeader>
      <CardContent>
        <div v-if="loading" class="text-center py-8">
          <div class="animate-spin rounded-full h-8 w-8 border-b-2 border-blue-500 mx-auto"></div>
          <p class="mt-2 text-sm text-gray-500">加载中...</p>
        </div>
        
        <div v-else-if="error" class="text-center py-8 text-red-500">
          {{ error }}
          <Button variant="outline" size="sm" class="mt-2" @click="loadResume">
            重试
          </Button>
        </div>
        
        <template v-else>
          <div class="mb-6">
            <div class="flex justify-between mb-1">
              <span class="text-sm font-medium">完整度</span>
              <span class="text-sm font-medium">{{ resume?.completionPercentage || 0 }}%</span>
            </div>
            <Progress :value="resume?.completionPercentage || 0" class="h-2" />
          </div>

          <div class="grid gap-6">
            <div class="border rounded-lg p-4">
              <div class="flex justify-between items-start mb-4">
                <div>
                  <h3 class="font-medium">个人信息</h3>
                  <p class="text-sm text-muted-foreground">基本联系方式和个人详情</p>
                </div>
                <Button variant="ghost" size="icon">
                  <EditIcon class="h-4 w-4" />
                </Button>
              </div>
              <div class="grid gap-1 text-sm">
                <div class="grid grid-cols-3">
                  <span class="text-muted-foreground">姓名</span>
                  <span class="col-span-2">{{ resume?.fullName || '未填写' }}</span>
                </div>
                <div class="grid grid-cols-3">
                  <span class="text-muted-foreground">邮箱</span>
                  <span class="col-span-2">{{ resume?.email || '未填写' }}</span>
                </div>
                <div class="grid grid-cols-3">
                  <span class="text-muted-foreground">电话</span>
                  <span class="col-span-2">{{ resume?.phone || '未填写' }}</span>
                </div>
                <div class="grid grid-cols-3">
                  <span class="text-muted-foreground">毕业院校</span>
                  <span class="col-span-2">{{ resume?.school || '未填写' }}</span>
                </div>
                <div class="grid grid-cols-3">
                  <span class="text-muted-foreground">专业</span>
                  <span class="col-span-2">{{ resume?.major || '未填写' }}</span>
                </div>
                <div class="grid grid-cols-3">
                  <span class="text-muted-foreground">毕业时间</span>
                  <span class="col-span-2">{{ resume?.graduationDate || '未填写' }}</span>
                </div>
              </div>
            </div>

            <div class="border rounded-lg p-4">
              <div class="flex justify-between items-start mb-4">
                <div>
                  <h3 class="font-medium">教育经历</h3>
                  <p class="text-sm text-muted-foreground">您的教育背景</p>
                </div>
                <Button variant="ghost" size="icon">
                  <EditIcon class="h-4 w-4" />
                </Button>
              </div>
              <div class="grid gap-4">
                <div>
                  <h4 class="font-medium">{{ resume?.school || '未填写学校' }}</h4>
                  <p class="text-sm">{{ resume?.major || '未填写专业' }}, {{ resume?.education || '未填写' }}</p>
                  <p class="text-sm text-muted-foreground">预计 {{ resume?.graduationDate }} 毕业</p>
                </div>
              </div>
            </div>

            <div class="border rounded-lg p-4">
              <div class="flex justify-between items-start mb-4">
                <div>
                  <h3 class="font-medium">技能</h3>
                  <p class="text-sm text-muted-foreground">您擅长的技能</p>
                </div>
                <Button variant="ghost" size="icon">
                  <EditIcon class="h-4 w-4" />
                </Button>
              </div>
              <div class="flex flex-wrap gap-2">
                <template v-if="resume?.skills">
                  <span 
                    v-for="(skill, index) in resume.skills.split(',').map(s => s.trim())" 
                    :key="index"
                    class="rounded-full bg-blue-100 px-2.5 py-0.5 text-xs font-medium text-blue-800"
                  >
                    {{ skill }}
                  </span>
                </template>
                <span v-else class="text-sm text-muted-foreground">未填写技能</span>
              </div>
            </div>

            <div class="border rounded-lg p-4">
              <div class="flex justify-between items-start mb-4">
                <div>
                  <h3 class="font-medium">工作经历</h3>
                  <p class="text-sm text-muted-foreground">您的工作经历</p>
                </div>
              </div>
              <p class="text-sm">{{ resume?.experience || '暂无工作经历' }}</p>
            </div>

            <div class="border rounded-lg p-4">
              <div class="flex justify-between items-start mb-4">
                <div>
                  <h3 class="font-medium">项目经验</h3>
                  <p class="text-sm text-muted-foreground">您的项目经验</p>
                </div>
              </div>
              <p class="text-sm">{{ resume?.projects || '暂无项目经验' }}</p>
            </div>

            <div class="border rounded-lg p-4">
              <div class="flex justify-between items-start mb-4">
                <div>
                  <h3 class="font-medium">自我介绍</h3>
                  <p class="text-sm text-muted-foreground">简单的自我介绍</p>
                </div>
              </div>
              <p class="text-sm">{{ resume?.selfIntroduction || '暂无自我介绍' }}</p>
            </div>
          </div>
        </template>
        </CardContent>
      </Card>
    </div>
    </template>