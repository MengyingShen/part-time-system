<script setup lang="ts">
import { ref } from 'vue'
import { Button } from "@/components/ui/button"
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card"
import { Input } from "@/components/ui/input"
import { Label } from "@/components/ui/label"
import { Textarea } from "@/components/ui/textarea"
import { 
  Camera, 
  FileText, 
  Save, 
  User 
} from 'lucide-vue-next'

const profileImage = ref('/placeholder-avatar.jpg')
const formData = ref({
  fullName: '张三',
  studentId: '202500123',
  gender: 'male',
  birthdate: '2005-06-15',
  email: 'zhangsan@example.com',
  phone: '13812345678',
  school: '某某大学',
  college: '计算机学院',
  major: '计算机科学与技术',
  grade: '3',
  eduBegin: '2022-09-01',
  eduEnd: '2026-07-01',
  workDays: 'weekends',
  workHours: '15',
  intro: '计算机专业在读学生，拥有良好的编程能力和团队协作精神。熟悉Java、Python、前端开发技术，曾参与多个实践项目。希望通过兼职积累实战经验，为未来职业发展打下基础。'
})

const jobCategories = [
  '程序开发', '产品运营', '市场营销', '客户服务', 
  '内容创作', '数据分析', '设计', '教育培训', '餐饮服务'
]
const selectedCategories = ref<string[]>([])

const handleSubmit = () => {
  // Handle form submission
  console.log('Form submitted:', formData.value, selectedCategories.value)
}
</script>

<template>
    <div class="grid gap-6">
      <div class="flex justify-between items-center">
        <h1 class="text-2xl font-bold">个人中心</h1>
        <Button>保存更改</Button>
      </div>
      
      <div class="grid gap-6 md:grid-cols-[300px_1fr]">
        <Card>
          <CardHeader class="pb-2">
            <CardTitle>个人资料</CardTitle>
          </CardHeader>
          <CardContent class="space-y-6">
            <div class="flex flex-col items-center gap-4">
              <div class="relative">
                <img 
                  :src="profileImage" 
                  alt="个人头像" 
                  class="w-32 h-32 rounded-full object-cover border-4 border-blue-100"
                />
                <Button 
                  variant="secondary" 
                  size="icon" 
                  class="absolute bottom-0 right-0 rounded-full"
                >
                  <Camera class="h-4 w-4" />
                  <span class="sr-only">更换头像</span>
                </Button>
              </div>
              <div class="text-center">
                <h3 class="font-medium">张三</h3>
                <p class="text-sm text-muted-foreground">计算机科学与技术</p>
              </div>
            </div>
            
            <div class="space-y-2">
              <div class="flex items-center gap-2">
                <User class="h-4 w-4 text-muted-foreground" />
                <span class="text-sm">个人信息</span>
              </div>
              <div class="pl-6 grid gap-3">
                <div class="grid grid-cols-2 text-sm">
                  <span class="text-muted-foreground">学号</span>
                  <span>202500123</span>
                </div>
                <div class="grid grid-cols-2 text-sm">
                  <span class="text-muted-foreground">院系</span>
                  <span>计算机学院</span>
                </div>
                <div class="grid grid-cols-2 text-sm">
                  <span class="text-muted-foreground">年级</span>
                  <span>2025级</span>
                </div>
              </div>
            </div>
            
            <div class="space-y-2">
              <div class="flex items-center gap-2">
                <FileText class="h-4 w-4 text-muted-foreground" />
                <span class="text-sm">求职概况</span>
              </div>
              <div class="pl-6 grid gap-3">
                <div class="grid grid-cols-2 text-sm">
                  <span class="text-muted-foreground">已投递简历</span>
                  <span>12份</span>
                </div>
                <div class="grid grid-cols-2 text-sm">
                  <span class="text-muted-foreground">面试邀请</span>
                  <span>5次</span>
                </div>
                <div class="grid grid-cols-2 text-sm">
                  <span class="text-muted-foreground">收到offer</span>
                  <span>2个</span>
                </div>
              </div>
            </div>
            
            <Button class="w-full gap-2">
              <FileText class="h-4 w-4" />
              查看我的简历
            </Button>
          </CardContent>
        </Card>
        
        <div class="space-y-6">
          <Card>
            <CardHeader class="pb-2">
              <CardTitle>基本信息</CardTitle>
            </CardHeader>
            <CardContent class="space-y-4">
              <div class="grid gap-4 md:grid-cols-2">
                <div class="space-y-2">
                  <Label for="fullName">姓名</Label>
                  <Input id="fullName" v-model="formData.fullName" />
                </div>
                <div class="space-y-2">
                  <Label for="studentId">学号</Label>
                  <Input id="studentId" v-model="formData.studentId" readonly class="bg-muted" />
                </div>
              </div>
              
              <div class="grid gap-4 md:grid-cols-2">
                <div class="space-y-2">
                  <Label for="gender">性别</Label>
                  <select 
                    id="gender"
                    v-model="formData.gender"
                    class="w-full h-10 rounded-md border border-input bg-background px-3 py-1"
                  >
                    <option value="male">男</option>
                    <option value="female">女</option>
                    <option value="other">其他</option>
                  </select>
                </div>
                <div class="space-y-2">
                  <Label for="birthdate">出生日期</Label>
                  <Input id="birthdate" type="date" v-model="formData.birthdate" />
                </div>
              </div>
              
              <div class="grid gap-4 md:grid-cols-2">
                <div class="space-y-2">
                  <Label for="email">邮箱</Label>
                  <Input id="email" type="email" v-model="formData.email" />
                </div>
                <div class="space-y-2">
                  <Label for="phone">手机号码</Label>
                  <Input id="phone" v-model="formData.phone" />
                </div>
              </div>
            </CardContent>
          </Card>
          
          <Card>
            <CardHeader class="pb-2">
              <CardTitle>学业信息</CardTitle>
            </CardHeader>
            <CardContent class="space-y-4">
              <div class="grid gap-4 md:grid-cols-2">
                <div class="space-y-2">
                  <Label for="school">学校</Label>
                  <Input id="school" v-model="formData.school" />
                </div>
                <div class="space-y-2">
                  <Label for="college">院系</Label>
                  <Input id="college" v-model="formData.college" />
                </div>
              </div>
              
              <div class="grid gap-4 md:grid-cols-2">
                <div class="space-y-2">
                  <Label for="major">专业</Label>
                  <Input id="major" v-model="formData.major" />
                </div>
                <div class="space-y-2">
                  <Label for="grade">年级</Label>
                  <select 
                    id="grade"
                    v-model="formData.grade"
                    class="w-full h-10 rounded-md border border-input bg-background px-3 py-1"
                  >
                    <option value="1">大一</option>
                    <option value="2">大二</option>
                    <option value="3">大三</option>
                    <option value="4">大四</option>
                    <option value="5">硕士研究生</option>
                    <option value="6">博士研究生</option>
                  </select>
                </div>
              </div>
              
              <div class="grid gap-4 md:grid-cols-2">
                <div class="space-y-2">
                  <Label for="eduBegin">入学时间</Label>
                  <Input id="eduBegin" type="date" v-model="formData.eduBegin" />
                </div>
                <div class="space-y-2">
                  <Label for="eduEnd">毕业时间</Label>
                  <Input id="eduEnd" type="date" v-model="formData.eduEnd" />
                </div>
              </div>
            </CardContent>
          </Card>
          
          <Card>
            <CardHeader class="pb-2">
              <CardTitle>求职意向</CardTitle>
            </CardHeader>
            <CardContent class="space-y-4">
              <div class="grid gap-4 md:grid-cols-2">
                <div class="space-y-2">
                  <Label htmlFor="jobType">期望工作类型</Label>
                  <select 
                    id="jobType"
                    class="w-full h-10 rounded-md border border-input bg-background px-3 py-1"
                    defaultValue="part-time"
                  >
                    <option value="part-time">兼职</option>
                    <option value="intern">实习</option>
                    <option value="full-time">全职</option>
                    <option value="temp">临时工</option>
                  </select>
                </div>
                <div class="space-y-2">
                  <Label htmlFor="salary">期望薪资（元/月）</Label>
                  <Input id="salary" defaultValue="3000-5000" />
                </div>
              </div>
              
              <div class="grid gap-4 md:grid-cols-2">
                <div class="space-y-2">
                  <Label for="workDays">可工作时间</Label>
                  <select 
                    id="workDays"
                    v-model="formData.workDays"
                    class="w-full h-10 rounded-md border border-input bg-background px-3 py-1"
                  >
                    <option value="weekdays">工作日</option>
                    <option value="weekends">周末</option>
                    <option value="both">均可</option>
                  </select>
                </div>
                <div class="space-y-2">
                  <Label for="workHours">工作时长（小时/周）</Label>
                  <Input id="workHours" type="number" v-model.number="formData.workHours" min="1" max="40" />
                </div>
              </div>
              
              <div class="space-y-2">
                <Label for="jobCategories">意向岗位类别（多选）</Label>
                <div class="grid gap-2 sm:grid-cols-2 md:grid-cols-3">
                  <div v-for="category in jobCategories" :key="category" class="flex items-center gap-2">
                    <input 
                      type="checkbox" 
                      :id="'category-' + category" 
                      :value="category"
                      v-model="selectedCategories"
                      class="h-4 w-4" 
                    />
                    <Label :for="'category-' + category" class="text-sm font-normal">{{ category }}</Label>
                  </div>
                </div>
              </div>
              
              <div class="space-y-2">
                <Label for="intro">个人简介</Label>
                <Textarea 
                  id="intro" 
                  v-model="formData.intro"
                  placeholder="简要介绍自己的背景、能力和求职意向"
                  :rows="4"
                />
              </div>
            </CardContent>
          </Card>
          
          <div class="flex justify-end">
            <Button size="lg" class="gap-2" @click="handleSubmit">
              <Save class="h-4 w-4" />
              保存所有更改
            </Button>
          </div>
        </div>
      </div>
    </div>
</template>
