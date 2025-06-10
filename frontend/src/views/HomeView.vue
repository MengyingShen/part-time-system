<script setup lang="ts">
import { Button } from '@/components/ui/button'
import {
  Card,
  CardContent,
  CardDescription,
  CardFooter,
  CardHeader,
  CardTitle,
} from '@/components/ui/card'
import { RouterLink } from 'vue-router'
import { Badge } from '@/components/ui/badge'
import { ref } from 'vue'
const data = ref([
  {
    id: 1,
    title: '前端开发实习生',
    company: '科技创新有限公司',
    location: '北京市海淀区',
    salary: '¥35/小时',
    description: '负责开发和维护公司的网站和移动应用程序界面',
    postedDays: 2,
    imageUrl:
      'https://images.unsplash.com/photo-1498050108023-c5249f4df085?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=600&q=80',
  },
  {
    id: 2,
    title: '市场营销助理',
    company: '领航市场营销',
    location: '远程',
    salary: '¥30/小时',
    description: '协助社交媒体营销和内容创作，策划线上活动',
    postedDays: 3,
    imageUrl:
      'https://images.unsplash.com/photo-1460925895917-afdab827c52f?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8MTZ8fG1hcmtldGluZ3xlbnwwfHwwfHx8MA%3D%3D&auto=format&fit=crop&w=600&q=80',
  },
  {
    id: 3,
    title: '数据分析实习生',
    company: '数据智能科技',
    location: '上海市浦东新区',
    salary: '¥40/小时',
    description: '协助收集、处理和分析业务数据，制作分析报告',
    postedDays: 1,
    imageUrl:
      'https://images.unsplash.com/photo-1551288049-bebda4e38f71?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=600&q=80',
  },
  {
    id: 4,
    title: '咖啡师助理',
    company: '市中心咖啡馆',
    location: '北京市朝阳区',
    salary: '¥30/小时',
    description: '学习制作各类咖啡饮品，提供优质的客户服务',
    postedDays: 5,
    imageUrl:
      'https://images.unsplash.com/photo-1513530534585-c7b1394c6d51?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxzZWFyY2h8Nnx8Y2FmZXxlbnwwfHwwfHw%3D&auto=format&fit=crop&w=600&q=80',
  },
  {
    id: 5,
    title: '平面设计实习生',
    company: '创意设计工作室',
    location: '杭州市西湖区',
    salary: '¥35/小时',
    description: '参与品牌视觉设计，制作宣传材料和社交媒体图像',
    postedDays: 4,
    imageUrl:
      'https://images.unsplash.com/photo-1626785774573-4b799315345d?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxzZWFyY2h8Mnx8Z3JhcGhpYyUyMGRlc2lnbnxlbnwwfHwwfHw%3D&auto=format&fit=crop&w=600&q=80',
  },
  {
    id: 6,
    title: '移动应用测试员',
    company: '科技创新有限公司',
    location: '远程',
    salary: '¥3000/项目',
    description: '进行移动应用测试，发现并记录问题，提供改进建议',
    postedDays: 2,
    imageUrl:
      'https://images.unsplash.com/photo-1526498460520-4c246339dccb?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxzZWFyY2h8NHx8YXBwJTIwdGVzdGluZ3xlbnwwfHwwfHw%3D&auto=format&fit=crop&w=600&q=80',
  },
])
</script>

<template>
  <main>
    <div class="min-h-screen bg-gradient-to-b from-blue-50 to-white">
      <header class="container mx-auto py-6">
        <div class="flex items-center justify-between">
          <h1 class="text-2xl font-bold text-blue-900">大学兼职门户</h1>
          <div class="flex gap-4">
            <RouterLink to="/login">
              <Button class="bg-blue-600 hover:bg-blue-700">登录</Button>
            </RouterLink>
            <RouterLink to="/register">
              <Button class="bg-blue-600 hover:bg-blue-700">注册</Button>
            </RouterLink>
          </div>
        </div>
      </header>

      <main class="container mx-auto py-12">
        <section class="mb-16 text-center">
          <h2 class="mb-4 text-4xl font-bold text-blue-900">寻找您理想的兼职工作</h2>
          <p class="mx-auto mb-8 max-w-2xl text-lg text-gray-600">
            与雇主联系，发现机会，在学习的同时打造您的职业生涯。
          </p>
          <div class="flex justify-center gap-4">
            <Button size="lg" class="bg-blue-600 hover:bg-blue-700">
              <RouterLink to="/register?role=student"> 以学生身份加入 </RouterLink>
            </Button>
            <Button size="lg" class="bg-blue-600 hover:bg-blue-700">
              <RouterLink to="/register?role=employer"> 以雇主身份加入 </RouterLink>
            </Button>
          </div>
        </section>

        <section class="mb-16">
          <h3 class="mb-8 text-center text-2xl font-semibold text-blue-900">工作流程</h3>
          <div class="grid gap-8 md:grid-cols-3">
            <Card>
              <CardHeader class="text-center">
                <div
                  class="mx-auto mb-4 overflow-hidden h-24 w-24 rounded-full border-2 border-blue-100"
                >
                  <img src="/1.png" alt="学生用户" class="w-full h-full object-cover" />
                </div>
                <CardTitle>学生用户</CardTitle>
                <CardDescription>寻找并申请符合您时间安排的兼职工作</CardDescription>
              </CardHeader>
              <CardContent>
                <ul class="space-y-2 text-sm">
                  <li>• 注册并创建个人资料</li>
                  <li>• 搜索并申请兼职工作</li>
                  <li>• 管理您的申请</li>
                  <li>• 对工作体验进行评分和评价</li>
                </ul>
              </CardContent>
              <CardFooter>
                <RouterLink to="/register?role=student" class="w-full">
                  <Button class="w-full bg-blue-600 hover:bg-blue-700">注册为学生</Button>
                </RouterLink>
              </CardFooter>
            </Card>

            <Card>
              <CardHeader class="text-center">
                <div
                  class="mx-auto mb-4 overflow-hidden h-24 w-24 rounded-full border-2 border-blue-100"
                >
                  <img src="/2.png" alt="雇主用户" class="w-full h-full object-cover" />
                </div>
                <CardTitle>雇主用户</CardTitle>
                <CardDescription>为您的兼职岗位寻找合格的学生</CardDescription>
              </CardHeader>
              <CardContent>
                <ul class="space-y-2 text-sm">
                  <li>• 注册您的公司</li>
                  <li>• 发布工作机会</li>
                  <li>• 审核学生申请</li>
                  <li>• 管理职位列表和邀请</li>
                </ul>
              </CardContent>
              <CardFooter>
                <Link to="/register?role=employer" class="w-full">
                  <Button class="w-full bg-blue-600 hover:bg-blue-700"> 注册为雇主 </Button>
                </Link>
              </CardFooter>
            </Card>

            <Card>
              <CardHeader class="text-center">
                <div
                  class="mx-auto mb-4 overflow-hidden h-24 w-24 rounded-full border-2 border-blue-100"
                >
                  <img src="/3.png" alt="管理员" class="w-full h-full object-cover" />
                </div>
                <CardTitle>管理员</CardTitle>
                <CardDescription>系统监督与平台管理</CardDescription>
              </CardHeader>
              <CardContent>
                <ul class="space-y-2 text-sm">
                  <li>• 审核用户注册</li>
                  <li>• 管理系统设置</li>
                  <li>• 监控平台活动</li>
                  <li>• 确保数据安全和合规</li>
                </ul>
              </CardContent>
              <CardFooter>
                <Link to="/login?role=admin" class="w-full">
                  <Button class="w-full bg-blue-600 hover:bg-blue-700"> 管理员登录 </Button>
                </Link>
              </CardFooter>
            </Card>
          </div>
        </section>

        <section class="mb-16">
          <h3 class="mb-8 text-center text-2xl font-semibold text-blue-900">推荐机会</h3>
          <div class="grid gap-6 md:grid-cols-2 lg:grid-cols-3">
            <Card v-for="job in data" :key="job.id" class="overflow-hidden">
              <div class="relative h-48">
                <img :src="job.imageUrl" :alt="job.title" class="w-full h-full object-cover" />
                <Badge class="absolute top-2 right-2 bg-blue-600">{{ job.salary }}</Badge>
              </div>
              <div class="p-4">
                <h4 class="text-lg font-semibold text-gray-900">{{ job.title }}</h4>
                <p class="text-sm text-gray-600">{{ job.company }}</p>
                <p class="text-sm text-gray-500 mt-1">
                  <i class="fas fa-map-marker-alt mr-1"></i> {{ job.location }}
                </p>
                <p class="text-sm text-gray-700 mt-2">{{ job.description }}</p>
              </div>
              <div class="p-4 pt-0 flex justify-between items-center border-t border-gray-100 mt-2">
                <span class="text-xs text-gray-500">{{ job.postedDays }}天前发布</span>
                <Link to="/login">
                  <Button size="sm" class="bg-blue-600 hover:bg-blue-700"> 立即申请 </Button>
                </Link>
              </div>
            </Card>
          </div>
          <div class="mt-8 text-center">
            <Link to="/login">
              <Button class="bg-blue-600 hover:bg-blue-700">查看所有工作</Button>
            </Link>
          </div>
        </section>
      </main>

      <footer class="bg-gray-50 py-12">
        <div class="container mx-auto">
          <div class="grid gap-8 md:grid-cols-3">
            <div>
              <h4 class="mb-4 text-lg font-semibold">大学兼职门户</h4>
              <p class="text-sm text-gray-600">自2023年以来，为学生提供优质的兼职机会。</p>
            </div>
            <div>
              <h4 class="mb-4 text-lg font-semibold">快速链接</h4>
              <ul class="space-y-2 text-sm text-gray-600">
                <li>
                  <Link to="/about" class="hover:text-blue-600"> 关于我们 </Link>
                </li>
                <li>
                  <Link to="/contact" class="hover:text-blue-600"> Contact </Link>
                </li>
                <li>
                  <Link to="/privacy" class="hover:text-blue-600"> 隐私政策 </Link>
                </li>
                <li>
                  <Link to="/terms" class="hover:text-blue-600"> 服务条款 </Link>
                </li>
              </ul>
            </div>
            <div>
              <h4 class="mb-4 text-lg font-semibold">联系我们</h4>
              <p class="text-sm text-gray-600">
                Email: support@collegejobportal.com
                <br />
                Phone: (123) 456-7890
              </p>
            </div>
          </div>
          <div class="mt-8 border-t border-gray-200 pt-8 text-center text-sm text-gray-600">
            © {new Date().getFullYear()} 大学兼职门户。保留所有权利。
          </div>
        </div>
      </footer>
    </div>
  </main>
</template>
