<script setup lang="ts">
import { Badge } from "@/components/ui/badge";
import { Button } from "@/components/ui/button";
import { Card, CardContent, CardDescription, CardFooter, CardHeader, CardTitle } from "@/components/ui/card";
import { Clock, MessageCircle } from "lucide-vue-next";
import { ref } from "vue";

const applications = ref([
  {
    id: 1,
    jobTitle: "校园咖啡馆服务员",
    company: "星巴克",
    location: "校内",
    status: "面试中",
    appliedDate: "2025-04-10",
    interviewDate: "2025-04-15 14:00"
  },
  {
    id: 2,
    jobTitle: "图书馆助理",
    company: "大学图书馆",
    location: "校内",
    status: "已申请",
    appliedDate: "2025-04-11"
  },
  {
    id: 3,
    jobTitle: "前端开发实习生",
    company: "科技创新中心",
    location: "线上/线下",
    status: "已通过",
    appliedDate: "2025-04-01",
    interviewDate: "2025-04-05 10:30",
    feedback: "面试表现很好，技术能力符合要求。欢迎加入我们的团队！请在下周一前完成入职手续。"
  },
  {
    id: 4,
    jobTitle: "社团活动助理",
    company: "学生会",
    location: "校内",
    status: "已拒绝",
    appliedDate: "2025-04-02",
    feedback: "感谢您的申请。虽然您的背景很出色，但我们需要有更多活动组织经验的人选。希望您未来申请其他职位时能够成功。"
  }
]);
</script>
    <template>
      <div class="grid gap-6">
        <h1 class="text-2xl font-bold">我的申请</h1>
        
        <div class="grid gap-4">
          <Card v-for="application in applications" :key="application.id" class="mb-6">
            <CardHeader>
              <div class="flex items-start justify-between">
                <div>
                  <CardTitle>{{ application.jobTitle }}</CardTitle>
                  <CardDescription class="mt-1">
                    {{ application.company }} · {{ application.location }}
                  </CardDescription>
                </div>
                <Badge 
                  :class="
                    application.status === '已申请' ? 'bg-blue-100 text-blue-800 hover:bg-blue-100' :
                    application.status === '面试中' ? 'bg-yellow-100 text-yellow-800 hover:bg-yellow-100' :
                    application.status === '已通过' ? 'bg-green-100 text-green-800 hover:bg-green-100' :
                    'bg-red-100 text-red-800 hover:bg-red-100'
                  "
                >
                  {{ application.status }}
                </Badge>
              </div>
            </CardHeader>
            <CardContent>
              <div class="grid gap-2">
                <div class="flex items-center text-sm text-muted-foreground">
                  <Clock class="mr-1 h-4 w-4" />
                  申请日期: {{ application.appliedDate }}
                </div>
                <div v-if="application.interviewDate" class="text-sm">
                  <span class="font-medium">面试日期:</span> {{ application.interviewDate }}
                </div>
                <div v-if="application.feedback" class="mt-2">
                  <p class="text-sm font-medium">反馈:</p>
                  <p class="text-sm text-muted-foreground">{{ application.feedback }}</p>
                </div>
              </div>
            </CardContent>
            <CardFooter class="flex justify-end gap-2">
              <Button 
                v-if="application.status !== '已拒绝' && application.status !== '已通过'" 
                variant="outline"
                @click="cancelApplication(application.id)"
              >
                取消申请
              </Button>
              <Button class="gap-1" @click="contactRecruiter(application)">
                <MessageCircle class="h-4 w-4" />
                联系招聘方
              </Button>
            </CardFooter>
          </Card>
        </div>
      </div>
    </template>
