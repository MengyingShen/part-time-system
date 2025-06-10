<script setup lang="ts">
import { ref } from 'vue';
import { Button } from "@/components/ui/button";
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from "@/components/ui/card";
import { Input } from "@/components/ui/input";
import { Tabs, TabsContent, TabsList, TabsTrigger } from "@/components/ui/tabs";
import { Switch } from "@/components/ui/switch";
import { Label } from "@/components/ui/label";
import { Textarea } from "@/components/ui/textarea";

interface ProfileData {
  username: string;
  fullName: string;
  email: string;
  phone: string;
  location: string;
  bio: string;
  [key: string]: string | boolean | undefined;
}

interface Notifications {
  email: boolean;
  app: boolean;
  newJobs: boolean;
  applicationUpdates: boolean;
  messages: boolean;
  reviews: boolean;
  [key: string]: boolean | undefined;
}

interface Security {
  currentPassword: string;
  newPassword: string;
  confirmPassword: string;
  [key: string]: string | undefined;
}

    const profileData = ref<ProfileData>({
      username: "zhangsan",
      fullName: "张三",
      email: "zhangsan@example.com",
      phone: "13812345678",
      location: "北京市",
      bio: "北京大学计算机科学专业学生，对前端开发和人工智能有浓厚兴趣。"
    });

    const notifications = ref<Notifications>({
      email: true,
      app: true,
      newJobs: true,
      applicationUpdates: true,
      messages: true,
      reviews: true
    });

    const security = ref<Security>({
      currentPassword: "",
      newPassword: "",
      confirmPassword: ""
    });

    

    const saveProfile = () => {
      console.log('Saving profile:', profileData.value);
    };

    const saveNotifications = () => {
      console.log('Saving notifications:', notifications.value);
    };

    const updatePassword = () => {
      console.log('Updating password');
    };

</script>

<template>
    <div class="grid gap-6">
      <h1 class="text-2xl font-bold">设置</h1>

      <Tabs defaultValue="profile" class="w-full">
        <TabsList class="grid w-full grid-cols-3">
          <TabsTrigger value="profile">个人资料</TabsTrigger>
          <TabsTrigger value="notifications">通知</TabsTrigger>
          <TabsTrigger value="security">安全</TabsTrigger>
        </TabsList>
        
        <TabsContent value="profile">
          <Card>
            <CardHeader>
              <CardTitle>个人资料</CardTitle>
              <CardDescription>
                管理您的个人信息，这些信息将显示给潜在雇主
              </CardDescription>
            </CardHeader>
            <CardContent class="space-y-4">
              <div class="grid grid-cols-1 md:grid-cols-2 gap-4">
                <div class="space-y-2">
                  <Label htmlFor="username">用户名</Label>
                  <Input 
                    id="username"
                    v-model="profileData.username"
                  />
                </div>
                <div class="space-y-2">
                  <Label htmlFor="fullName">姓名</Label>
                  <Input 
                    id="fullName"
                    v-model="profileData.fullName"
                  />
                </div>
                <div class="space-y-2">
                  <Label htmlFor="email">电子邮箱</Label>
                  <Input 
                    id="email"
                    type="email"
                    v-model="profileData.email"
                  />
                </div>
                <div class="space-y-2">
                  <Label htmlFor="phone">电话号码</Label>
                  <Input 
                    id="phone"
                    v-model="profileData.phone"
                  />
                </div>
                <div class="space-y-2">
                  <Label htmlFor="location">所在地</Label>
                  <Input 
                    id="location"
                    v-model="profileData.location"
                  />
                </div>
              </div>
              
              <div class="space-y-2">
                <Label htmlFor="bio">个人简介</Label>
                <Textarea 
                  id="bio"
                  v-model="profileData.bio"
                  :rows="4"
                />
              </div>
              
              <div class="flex justify-end">
                <Button @click="saveProfile">保存更改</Button>
              </div>
            </CardContent>
          </Card>
        </TabsContent>
        
        <TabsContent value="notifications">
          <Card>
            <CardHeader>
              <CardTitle>通知设置</CardTitle>
              <CardDescription>
                管理您希望接收哪些通知以及如何接收
              </CardDescription>
            </CardHeader>
            <CardContent class="space-y-6">
              <div class="space-y-4">
                <h3 class="text-sm font-medium">通知渠道</h3>
                <div class="grid gap-4">
                  <div class="flex items-center justify-between">
                    <div>
                      <Label htmlFor="emailNotifications">电子邮件通知</Label>
                      <p class="text-sm text-muted-foreground">通过邮件接收通知</p>
                    </div>
                    <Switch 
                      id="emailNotifications"
                      v-model:checked="notifications.email"
                    />
                  </div>
                  <div class="flex items-center justify-between">
                    <div>
                      <Label htmlFor="appNotifications">应用内通知</Label>
                      <p class="text-sm text-muted-foreground">在应用内显示通知</p>
                    </div>
                    <Switch 
                      id="appNotifications"
                      v-model:checked="notifications.app"
                    />
                  </div>
                </div>
              </div>
              
              <div class="space-y-4">
                <h3 class="text-sm font-medium">通知类型</h3>
                <div class="grid gap-4">
                  <div class="flex items-center justify-between">
                    <div>
                      <Label htmlFor="newJobs">新职位推荐</Label>
                      <p class="text-sm text-muted-foreground">当有符合您兴趣的新职位时通知您</p>
                    </div>
                    <Switch 
                      id="newJobs"
                      v-model:checked="notifications.newJobs"
                    />
                  </div>
                  <div class="flex items-center justify-between">
                    <div>
                      <Label htmlFor="applicationUpdates">申请状态更新</Label>
                      <p class="text-sm text-muted-foreground">当您的申请状态有变化时通知您</p>
                    </div>
                    <Switch 
                      id="applicationUpdates"
                      v-model:checked="notifications.applicationUpdates"
                    />
                  </div>
                  <div class="flex items-center justify-between">
                    <div>
                      <Label htmlFor="messages">新消息</Label>
                      <p class="text-sm text-muted-foreground">当收到新消息时通知您</p>
                    </div>
                    <Switch 
                      id="messages" 
                      v-model:checked="notifications.messages"
                    />
                  </div>
                  <div class="flex items-center justify-between">
                    <div>
                      <Label htmlFor="reviews">新评价</Label>
                      <p class="text-sm text-muted-foreground">当收到新评价时通知您</p>
                    </div>
                    <Switch 
                      id="reviews"
                      v-model:checked="notifications.reviews"
                    />
                  </div>
                </div>
              </div>
              
              <div class="flex justify-end">
                <Button @click="saveNotifications">保存设置</Button>
              </div>
            </CardContent>
          </Card>
        </TabsContent>
        
        <TabsContent value="security">
          <Card>
            <CardHeader>
              <CardTitle>安全设置</CardTitle>
              <CardDescription>
                管理您的账户安全和隐私设置
              </CardDescription>
            </CardHeader>
            <CardContent class="space-y-6">
              <div class="space-y-4">
                <h3 class="text-sm font-medium">修改密码</h3>
                <div class="grid gap-4">
                  <div class="space-y-2">
                    <Label htmlFor="currentPassword">当前密码</Label>
                    <Input 
                      id="currentPassword"
                      type="password"
                      v-model="security.currentPassword"
                    />
                  </div>
                  <div class="space-y-2">
                    <Label htmlFor="newPassword">新密码</Label>
                    <Input 
                      id="newPassword"
                      type="password"
                      v-model="security.newPassword"
                    />
                  </div>
                  <div class="space-y-2">
                    <Label htmlFor="confirmPassword">确认新密码</Label>
                    <Input 
                      id="confirmPassword"
                      type="password"
                      v-model="security.confirmPassword"
                    />
                  </div>
                </div>
                <div class="flex justify-end">
                  <Button @click="updatePassword">更新密码</Button>
                </div>
              </div>
              
              <div class="pt-4 border-t">
                <h3 class="text-sm font-medium mb-4">账户管理</h3>
                <Button variant="destructive">注销账户</Button>
              </div>
            </CardContent>
          </Card>
        </TabsContent>
      </Tabs>
    </div>
</template>