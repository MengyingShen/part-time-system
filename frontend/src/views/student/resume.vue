<script setup lang="ts">
import { Button } from "@/components/ui/button";
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from "@/components/ui/card";
import { Progress } from "@/components/ui/progress";
import { PlusIcon, Download, UploadIcon, EditIcon } from "lucide-vue-next";
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
              <Button variant="outline" size="sm" class="gap-1">
                <UploadIcon class="h-4 w-4" />
                上传
              </Button>
              <Button variant="outline" size="sm" class="gap-1">
                <Download class="h-4 w-4" />
                下载
              </Button>
            </div>
          </div>
        </CardHeader>
        <CardContent>
          <div class="mb-6">
            <div class="flex justify-between mb-1">
              <span class="text-sm font-medium">完整度</span>
              <span class="text-sm font-medium">80%</span>
            </div>
            <Progress value={80} class="h-2"></Progress>
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
                  <span class="col-span-2">张三</span>
                </div>
                <div class="grid grid-cols-3">
                  <span class="text-muted-foreground">邮箱</span>
                  <span class="col-span-2">zhangsan@example.com</span>
                </div>
                <div class="grid grid-cols-3">
                  <span class="text-muted-foreground">电话</span>
                  <span class="col-span-2">13812345678</span>
                </div>
                <div class="grid grid-cols-3">
                  <span class="text-muted-foreground">位置</span>
                  <span class="col-span-2">北京市</span>
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
                  <h4 class="font-medium">北京大学</h4>
                  <p class="text-sm">计算机科学, 本科</p>
                  <p class="text-sm text-muted-foreground">2023 - 2027 (预计)</p>
                </div>
                <div>
                  <h4 class="font-medium">北京市第一中学</h4>
                  <p class="text-sm">高中学历</p>
                  <p class="text-sm text-muted-foreground">2020 - 2023</p>
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
                <span class="rounded-full bg-blue-100 px-2.5 py-0.5 text-xs font-medium text-blue-800">
                  Java
                </span>
                <span class="rounded-full bg-blue-100 px-2.5 py-0.5 text-xs font-medium text-blue-800">
                  Python
                </span>
                <span class="rounded-full bg-blue-100 px-2.5 py-0.5 text-xs font-medium text-blue-800">
                  JavaScript
                </span>
                <span class="rounded-full bg-blue-100 px-2.5 py-0.5 text-xs font-medium text-blue-800">
                  React
                </span>
                <span class="rounded-full bg-blue-100 px-2.5 py-0.5 text-xs font-medium text-blue-800">
                  英语 (流利)
                </span>
              </div>
            </div>

            <div class="border rounded-lg p-4 border-dashed flex flex-col items-center justify-center py-6">
              <Button variant="outline" class="gap-1">
                <PlusIcon class="h-4 w-4" />
                添加工作经历
              </Button>
            </div>
          </div>
        </CardContent>
      </Card>
    </div>
    </template>