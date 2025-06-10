<script setup lang="ts">
import { RouterLink } from 'vue-router'
import { Button } from "@/components/ui/button"
import { 
  Activity,
  Bell, 
  Bookmark, 
  Briefcase, 
  Cog, 
  FileText, 
  GraduationCap, 
  Home, 
  LogOut, 
  MessageSquare, 
  Search, 
  Star,
  User
} from 'lucide-vue-next'
import { Separator } from "@/components/ui/separator"
import { Avatar, AvatarFallback, AvatarImage } from "@/components/ui/avatar"
import { Badge } from "@/components/ui/badge"

// Logout function
const handleLogout = () => {
  // Add logout logic here
  console.log('Logging out...')
  // router.push('/login')
}
</script>

    <template>
    <div class="grid min-h-screen grid-cols-[240px_1fr] bg-muted/5">
      <aside class="border-r border-border bg-gradient-to-b from-indigo-50 to-indigo-100 text-sidebar-foreground">
        <div class="flex h-16 items-center gap-2 border-b border-border px-6 bg-gradient-to-r from-indigo-600 to-indigo-800">
          <GraduationCap class="h-6 w-6 text-white" />
          <h1 class="text-lg font-semibold text-white">学生门户</h1>
        </div>
        
        <div class="p-4">
          <div class="flex items-center gap-3 mb-6 bg-white p-3 rounded-lg shadow-sm">
            <Avatar>
              <AvatarImage src="/student-avatar.png" alt="Student" />
              <AvatarFallback class="bg-indigo-600 text-white">学生</AvatarFallback>
            </Avatar>
            <div>
              <p class="font-medium">沈同学</p>
              <p class="text-xs text-gray-500">student@example.com</p>
            </div>
          </div>
        </div>
        <nav class="grid gap-4 px-2">
          <div class="mb-2">
            <p class="px-4 text-xs font-semibold text-gray-500 uppercase tracking-wider mb-1">主要功能</p>
          </div>
          <Button variant="ghost" class="justify-start gap-2 hover:bg-indigo-200" asChild>
            <RouterLink to="/student" class="relative group">
              <span class="absolute left-0 w-1 h-6 bg-indigo-600 rounded-r-full opacity-0 group-hover:opacity-100 transition-opacity"></span>
              <div class="flex h-8 w-8 items-center justify-center rounded-md bg-indigo-100 text-indigo-800 mr-2">
                <Home class="h-4 w-4" />
              </div>
              <span>仪表盘</span>
              <Badge variant="outline" class="ml-auto text-xs">新</Badge>
            </RouterLink>
          </Button>
          <Button variant="ghost" class="justify-start gap-2 hover:bg-indigo-200" asChild>
            <RouterLink to="/student/search" class="relative group">
              <span class="absolute left-0 w-1 h-6 bg-indigo-600 rounded-r-full opacity-0 group-hover:opacity-100 transition-opacity"></span>
              <div class="flex h-8 w-8 items-center justify-center rounded-md bg-indigo-100 text-indigo-800 mr-2">
                <Search class="h-4 w-4" />
              </div>
              <span>查找工作</span>
            </RouterLink>
          </Button>
          <p class="px-4 text-xs font-semibold text-gray-500 uppercase tracking-wider mb-1 mt-6">工作管理</p>
          <Button variant="ghost" class="justify-start gap-2 hover:bg-indigo-200" asChild>
            <RouterLink to="/student/applications" class="relative group">
              <span class="absolute left-0 w-1 h-6 bg-indigo-600 rounded-r-full opacity-0 group-hover:opacity-100 transition-opacity"></span>
              <div class="flex h-8 w-8 items-center justify-center rounded-md bg-blue-100 text-blue-800 mr-2">
                <Briefcase class="h-4 w-4" />
              </div>
              <span>我的申请</span>
              <Badge class="ml-auto bg-blue-600 text-xs">3</Badge>
            </RouterLink>
          </Button>
          <Button variant="ghost" class="justify-start gap-2 hover:bg-indigo-200" asChild>
            <RouterLink to="/student/saved" class="relative group">
              <span class="absolute left-0 w-1 h-6 bg-indigo-600 rounded-r-full opacity-0 group-hover:opacity-100 transition-opacity"></span>
              <div class="flex h-8 w-8 items-center justify-center rounded-md bg-blue-100 text-blue-800 mr-2">
                <Bookmark class="h-4 w-4" />
              </div>
              <span>保存的工作</span>
              <Badge class="ml-auto bg-blue-600 text-xs">12</Badge>
            </RouterLink>
          </Button>
          <p class="px-4 text-xs font-semibold text-gray-500 uppercase tracking-wider mb-1 mt-6">个人信息</p>
          <Button variant="ghost" class="justify-start gap-2 hover:bg-indigo-200" asChild>
            <RouterLink to="/student/resume" class="relative group">
              <span class="absolute left-0 w-1 h-6 bg-indigo-600 rounded-r-full opacity-0 group-hover:opacity-100 transition-opacity"></span>
              <div class="flex h-8 w-8 items-center justify-center rounded-md bg-amber-100 text-amber-800 mr-2">
                <FileText class="h-4 w-4" />
              </div>
              <span>我的简历</span>
            </RouterLink>
          </Button>
          <Button variant="ghost" class="justify-start gap-2 hover:bg-indigo-200" asChild>
            <RouterLink to="/student/reviews" class="relative group">
              <span class="absolute left-0 w-1 h-6 bg-indigo-600 rounded-r-full opacity-0 group-hover:opacity-100 transition-opacity"></span>
              <div class="flex h-8 w-8 items-center justify-center rounded-md bg-amber-100 text-amber-800 mr-2">
                <Star class="h-4 w-4" />
              </div>
              <span>我的评价</span>
            </RouterLink>
          </Button>
          <Button variant="ghost" class="justify-start gap-2 hover:bg-indigo-200" asChild>
            <RouterLink to="/student/messages" class="relative group">
              <span class="absolute left-0 w-1 h-6 bg-indigo-600 rounded-r-full opacity-0 group-hover:opacity-100 transition-opacity"></span>
              <div class="flex h-8 w-8 items-center justify-center rounded-md bg-purple-100 text-purple-800 mr-2">
                <MessageSquare class="h-4 w-4" />
              </div>
              <span>消息</span>
              <Badge class="ml-auto bg-red-600 text-xs">2</Badge>
            </RouterLink>
          </Button>
          
          <Separator class="my-2" />
          
          <Button variant="ghost" class="justify-start gap-2 hover:bg-indigo-200" asChild>
            <RouterLink to="/student/settings" class="relative group">
              <span class="absolute left-0 w-1 h-6 bg-indigo-600 rounded-r-full opacity-0 group-hover:opacity-100 transition-opacity"></span>
              <div class="flex h-8 w-8 items-center justify-center rounded-md bg-gray-200 text-gray-800 mr-2">
                <Cog class="h-4 w-4" />
              </div>
              <span>设置</span>
            </RouterLink>
          </Button>
        </nav>
      </aside>
      <main class="flex flex-col">
        <header class="flex h-16 items-center justify-between border-b border-border px-6 bg-white shadow-sm">
          <div class="flex items-center gap-3">
            <Activity class="h-5 w-5 text-indigo-600" />
            <h2 class="text-lg font-semibold">学生控制面板</h2>
            <Badge variant="outline" class="ml-2 bg-indigo-50 text-indigo-700 border-indigo-200">在线</Badge>
          </div>
          
          <div class="flex items-center gap-4">
            <Button variant="outline" size="sm" class="gap-1 text-xs">
              <User class="h-3 w-3" />
              我的资料
            </Button>
            
            <Button variant="ghost" size="icon" class="relative">
              <Bell class="h-5 w-5" />
              <span class="absolute top-0 right-0 h-2 w-2 rounded-full bg-red-500"></span>
              <span class="sr-only">通知</span>
            </Button>
            
            <Button variant="ghost" size="icon" asChild>
              <RouterLink to="/student/settings">
                <Cog class="h-5 w-5" />
                <span class="sr-only">设置</span>
              </RouterLink>
            </Button>
            
            <Button variant="ghost" size="icon" asChild class="text-red-500 hover:text-red-700 hover:bg-red-50">
              <RouterLink to="/login">
                <LogOut class="h-5 w-5" />
                <span class="sr-only">登出</span>
              </RouterLink>
            </Button>
          </div>
        </header>
        <div class="flex-1 overflow-auto p-6 bg-gray-50">
          <div class="mb-6 bg-gradient-to-r from-indigo-600 to-indigo-800 p-4 rounded-lg shadow-md text-white">
            <div class="flex justify-between items-center">
              <div>
                <h3 class="text-xl font-bold">您好，沈同学</h3>
                <p class="text-indigo-100">您有 <span class="font-semibold">3</span> 个未处理的应用和 <span class="font-semibold">2</span> 条新消息</p>
              </div>
              <div class="bg-white/20 p-3 rounded-lg text-center">
                <p class="text-3xl font-bold">12</p>
                <p class="text-xs text-indigo-100">可申请岗位</p>
              </div>
            </div>
          </div>
          <div class="bg-white p-6 rounded-lg shadow-sm">
            <RouterView />
          </div>
        </div>
      </main>
    </div>
    </template>