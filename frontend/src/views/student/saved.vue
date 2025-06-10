<script setup lang="ts">
import { ref } from 'vue';
import { Button } from "@/components/ui/button";
import { Card, CardContent, CardDescription, CardFooter, CardHeader, CardTitle } from "@/components/ui/card";
import { Bookmark, Trash2 } from "lucide-vue-next";
import { useRouter } from 'vue-router';

const router = useRouter();

// 模拟数据 - 实际应用中应该从API获取
const savedJobs = ref([
  {
    id: 1,
    title: "校园咖啡馆服务员",
    company: "星巴克",
    location: "校内",
    type: "兼职",
    description: "负责接待顾客、点单和制作饮品。灵活的工作时间，适合课余时间。要求热情友好，有良好的沟通能力。",
    salary: "¥20-25",
    savedAt: "2天前"
  },
  {
    id: 2,
    title: "图书馆助理",
    company: "大学图书馆",
    location: "校内",
    type: "兼职",
    description: "整理书籍、帮助读者查找资料，维护图书馆秩序。安静的工作环境。要求细心，有责任心。",
    salary: "¥18-20",
    savedAt: "3天前"
  },
  {
    id: 3,
    title: "前端开发实习生",
    company: "科技创新中心",
    location: "线上/线下",
    type: "实习",
    description: "参与网站和应用开发，要求有基本的HTML/CSS/JavaScript技能。可以获得行业经验和技术指导。",
    salary: "¥30-40",
    savedAt: "1周前"
  },
  {
    id: 4,
    title: "校园大使",
    company: "腾讯",
    location: "校内",
    type: "兼职",
    description: "负责在校园内推广腾讯产品，组织校园活动，收集用户反馈。提供产品体验和市场推广技能培训。",
    salary: "¥25-30",
    savedAt: "2周前"
  },
  {
    id: 5,
    title: "英语家教",
    company: "个人",
    location: "线上",
    type: "兼职",
    description: "为高中生提供英语口语和写作辅导，每周2-3次，每次1-2小时。要求英语专业或有相关教学经验。",
    salary: "¥80-100",
    savedAt: "5天前"
  }
]);

// 方法
const removeSavedJob = (jobId: number) => {
  savedJobs.value = savedJobs.value.filter(job => job.id !== jobId);
};

const applyForJob = (jobId: number) => {
  // 实现申请工作的逻辑
  console.log('Applying for job:', jobId);
};

const browseJobs = () => {
  // 导航到工作搜索页面
  router.push('/student/search');
};
</script>

<template>
  <div class="grid gap-6">
    <h1 class="text-2xl font-bold">保存的工作</h1>
    
    <Card v-if="savedJobs.length === 0">
      <CardContent class="flex flex-col items-center justify-center py-10">
        <Bookmark class="h-12 w-12 text-muted-foreground mb-4" />
        <p class="text-center text-muted-foreground">您还没有保存任何工作</p>
        <Button class="mt-4" @click="browseJobs">浏览可用工作</Button>
      </CardContent>
    </Card>
    
    <div v-else class="grid gap-4">
      <Card v-for="job in savedJobs" :key="job.id" class="mb-4">
        <CardHeader>
          <div class="flex justify-between">
            <div>
              <CardTitle>{{ job.title }}</CardTitle>
              <CardDescription class="mt-1">{{ job.company }} · {{ job.location }}</CardDescription>
            </div>
            <Button 
              variant="ghost" 
              size="icon"
              @click="removeSavedJob(job.id)"
            >
              <Trash2 class="h-5 w-5 text-red-500" />
            </Button>
          </div>
        </CardHeader>
        <CardContent>
          <div class="grid gap-2">
            <div class="flex gap-2 text-sm">
              <span class="rounded-full bg-blue-100 px-2.5 py-0.5 text-xs font-medium text-blue-800">
                {{ job.type }}
              </span>
              <span class="rounded-full bg-green-100 px-2.5 py-0.5 text-xs font-medium text-green-800">
                {{ job.salary }}/小时
              </span>
            </div>
            <p class="text-sm text-muted-foreground">{{ job.description }}</p>
          </div>
        </CardContent>
        <CardFooter class="flex justify-between">
          <div class="text-sm text-muted-foreground">保存于 {{ job.savedAt }}</div>
          <Button @click="applyForJob(job.id)">申请</Button>
        </CardFooter>
      </Card>
    </div>
  </div>
</template>
