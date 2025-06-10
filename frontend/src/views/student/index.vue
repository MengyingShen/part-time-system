<script setup lang="ts">
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from "@/components/ui/card";
import { BriefcaseIcon, BookmarkIcon, FileTextIcon, StarIcon } from "lucide-vue-next";
import { ref } from "vue";

const recentJobs = ref([
  {
    id: 1,
    title: "校园咖啡馆服务员",
    company: "星巴克",
    location: "校内",
    description: "负责接待顾客、点单和制作饮品。灵活的工作时间，适合课余时间。",
    salary: "¥20-25"
  },
  {
    id: 2,
    title: "图书馆助理",
    company: "大学图书馆",
    location: "校内",
    description: "整理书籍、帮助读者查找资料，维护图书馆秩序。安静的工作环境。",
    salary: "¥18-20"
  },
  {
    id: 3,
    title: "前端开发实习生",
    company: "科技创新中心",
    location: "线上/线下",
    description: "参与网站和应用开发，要求有基本的HTML/CSS/JavaScript技能。",
    salary: "¥30-40"
  },
  {
    id: 4,
    title: "社团活动助理",
    company: "学生会",
    location: "校内",
    description: "协助组织校园活动，负责宣传和现场协调。获取活动组织经验。",
    salary: "¥15-18"
  }
]);

// Dashboard stats
const stats = [
  {
    title: "申请中的职位",
    value: "3",
    description: "正在审核的申请",
    icon: BriefcaseIcon
  },
  {
    title: "收藏的职位",
    value: "7",
    description: "保存以后查看",
    icon: BookmarkIcon
  },
  {
    title: "简历完整度",
    value: "80%",
    description: "提高竞争力",
    icon: FileTextIcon
  },
  {
    title: "我的评价",
    value: "4.8",
    description: "基于2次评价",
    icon: StarIcon
  }
];

</script>
    <template>
    <div class="grid gap-6">
      <h1 class="text-2xl font-bold">欢迎回来，同学</h1>
      
      <div class="grid gap-4 md:grid-cols-2 lg:grid-cols-4">
        <Card v-for="stat in stats" :key="stat.title">
          <CardHeader class="flex flex-row items-center justify-between pb-2">
            <CardTitle class="text-sm font-medium">{{ stat.title }}</CardTitle>
            <component :is="stat.icon" class="h-4 w-4 text-muted-foreground" />
          </CardHeader>
          <CardContent>
            <div class="text-2xl font-bold">{{ stat.value }}</div>
            <p class="text-xs text-muted-foreground">{{ stat.description }}</p>
          </CardContent>
        </Card>
      </div>

      <h2 class="mt-4 text-xl font-semibold">最近的职位机会</h2>
      <div class="grid gap-4 md:grid-cols-2">
        <Card v-for="job in recentJobs" :key="job.id">
          <CardHeader>
            <CardTitle>{{ job.title }}</CardTitle>
            <CardDescription>{{ job.company }} · {{ job.location }}</CardDescription>
          </CardHeader>
          <CardContent>
            <p class="text-sm text-muted-foreground mb-4">{{ job.description }}</p>
            <div class="flex justify-between items-center">
              <div class="text-sm font-medium">{{ job.salary }}/小时</div>
              <RouterLink 
                :to="`/student/search/${job.id}`" 
                class="text-sm font-medium text-blue-600 hover:underline"
              >
                查看详情
              </RouterLink>
            </div>
          </CardContent>
        </Card>
      </div>
    </div>
</template>
