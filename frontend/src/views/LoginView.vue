<script setup lang="ts">
import { ref, computed, onMounted } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import { Button } from "@/components/ui/button";
import { Card, CardContent, CardDescription, CardFooter, CardHeader, CardTitle } from "@/components/ui/card";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import authService from "@/lib/authService";

const route = useRoute();
const router = useRouter();

const username = ref('');
const password = ref('');
const error = ref('');
const isLoading = ref(false);
const role = computed(() => {
  const roleParam = Array.isArray(route.query.role) ? route.query.role[0] : route.query.role;
  return roleParam || 'student';
});

const handleSubmit = async (e: Event) => {
  e.preventDefault();
  error.value = "";
  isLoading.value = true;
  
  try {
    console.log('Attempting login with:', { username: username.value });
    const response = await authService.login({ 
      username: username.value, 
      password: password.value 
    });
    
    console.log('Login response:', response);
    console.log('Token in localStorage after login:', localStorage.getItem('token'));
    console.log('User data in localStorage after login:', localStorage.getItem('user'));
    
    if (response.role.toLowerCase() !== role.value.toLowerCase() && role.value !== "admin") {
      const roleNames: Record<string, string> = {
        student: '学生',
        employer: '雇主',
        admin: '管理员'
      };
      error.value = `您尝试以${roleNames[role.value as keyof typeof roleNames] || role.value}身份登录，但您的账户类型是${roleNames[response.role as keyof typeof roleNames] || response.role}`;
    } else {
      // Redirect based on user role
      const redirectPath = response.role.toLowerCase() === "admin" 
        ? "/admin" 
        : response.role.toLowerCase() === "employer"
          ? "/employer"
          : "/student";
      await router.push(redirectPath);
    }
  } catch (err) {
    console.error("Login error:", err);
    error.value = "登录失败：用户名或密码错误";
  } finally {
    isLoading.value = false;
  }
};
</script>

<template>
<div class="min-h-screen bg-gradient-to-b from-blue-50 to-white">
  <div class="container mx-auto flex min-h-screen flex-col items-center justify-center py-12">
    <RouterLink to="/" class="mb-8 text-xl font-bold text-blue-900">
      大学兼职门户
    </RouterLink>

    <Card class="w-full max-w-md">
      <CardHeader class="text-center">
        <CardTitle>登录您的账户</CardTitle>
        <CardDescription>
          输入您的凭证以访问您的{{ role === "admin" ? "管理员" : role === "student" ? "学生" : "雇主" }}账户
        </CardDescription>
      </CardHeader>
      <CardContent>
        <div v-if="error" class="mb-4 rounded-md bg-red-50 p-3 text-sm text-red-600">{{ error }}</div>
        <form @submit="handleSubmit">
          <div class="grid gap-4">
            <div class="grid gap-2">
              <Label for="username">用户名</Label>
              <Input 
                id="username" 
                v-model="username"
                placeholder="请输入用户名" 
                required
              />
            </div>
            <div class="grid gap-2">
              <div class="flex items-center justify-between">
                <Label for="password">密码</Label>
                <RouterLink to="/forgot-password" class="text-sm text-blue-600 hover:underline">
                  忘记密码？
                </RouterLink>
              </div>
              <Input 
                id="password" 
                type="password"
                v-model="password"
                required
              />
            </div>
            <Button type="submit" class="w-full" :disabled="isLoading">
              {{ isLoading ? "登录中..." : "登录" }}
            </Button>
          </div>
        </form>
      </CardContent>
      <CardFooter class="flex flex-col gap-4 text-center">
        <div class="text-sm text-gray-600">
          还没有账户？
          <RouterLink :to="`/register${role ? `?role=${role}` : ''}`" class="text-blue-600 hover:underline">
            在此注册
          </RouterLink>
        </div>
        <div v-if="role !== 'student'" class="text-sm">
          <RouterLink to="/login?role=student" class="text-blue-600 hover:underline">
            以学生身份登录
          </RouterLink>
        </div>
        <div v-if="role !== 'employer'" class="text-sm">
          <RouterLink to="/login?role=employer" class="text-blue-600 hover:underline">
            以雇主身份登录
          </RouterLink>
        </div>
        <div v-if="role !== 'admin'" class="text-sm">
          <RouterLink to="/login?role=admin" class="text-blue-600 hover:underline">
            以管理员身份登录
          </RouterLink>
        </div>
      </CardFooter>
    </Card>
  </div>
</div> 
</template>