<script setup lang="ts">
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import { StarIcon } from "lucide-vue-next";
import { ref } from "vue";

const reviews = ref([
  {
    id: 1,
    jobTitle: "前端开发实习生",
    employer: "科技创新中心",
    date: "2025-04-08",
    rating: 5,
    comment: "张三是个出色的开发者，展现了扎实的技术基础和快速学习能力。他不仅按时完成了分配的任务，还提出了一些改进网站性能的创新想法。他与团队合作愉快，沟通清晰。非常期待未来能再次合作！",
    skills: ["前端开发", "React", "团队合作", "问题解决"]
  },
  {
    id: 2,
    jobTitle: "图书馆助理",
    employer: "大学图书馆",
    date: "2025-03-15",
    rating: 4,
    comment: "张三工作认真负责，能够按时完成分配的任务。他在整理书籍和帮助读者方面表现出色，展现了良好的服务意识。有时可能需要更加主动一些，但总体表现很好。",
    skills: ["责任心", "服务意识", "工作效率"]
  }
]);
</script>

<template>
  <div class="grid gap-6 p-4">
    <h1 class="text-2xl font-bold">我的评价</h1>

    <div class="grid gap-4">
      <!-- Overall Rating Card -->
      <Card>
        <CardHeader>
          <CardTitle>总体评分</CardTitle>
          <p class="text-sm text-muted-foreground">基于您完成的所有工作</p>
        </CardHeader>
        <CardContent>
          <div class="flex items-center gap-4">
            <div class="text-5xl font-bold">4.8</div>
            <div class="flex">
              <StarIcon 
                v-for="star in 5" 
                :key="star" 
                :class="['h-6 w-6', star <= 4 ? 'text-yellow-400 fill-yellow-400' : 'text-yellow-400']" 
              />
            </div>
            <div class="text-sm text-muted-foreground">
              基于{{ reviews.length }}次评价
            </div>
          </div>
        </CardContent>
      </Card>

      <!-- Reviews List -->
      <template v-if="reviews.length > 0">
        <Card v-for="review in reviews" :key="review.id" class="mb-6">
          <CardHeader>
            <div class="flex justify-between items-start">
              <div>
                <h3 class="font-semibold">{{ review.jobTitle }}</h3>
                <p class="text-sm text-muted-foreground">{{ review.employer }} · {{ review.date }}</p>
              </div>
              <div class="flex">
                <StarIcon
                  v-for="star in 5"
                  :key="star"
                  :class="['h-4 w-4', star <= review.rating ? 'text-yellow-400 fill-yellow-400' : 'text-muted-foreground']"
                />
              </div>
            </div>
          </CardHeader>
          <CardContent>
            <p class="mb-4">{{ review.comment }}</p>
            <div v-if="review.skills && review.skills.length > 0" class="mt-4">
              <p class="text-sm font-medium mb-2">雇主认可的技能:</p>
              <div class="flex flex-wrap gap-2">
                <span
                  v-for="(skill, index) in review.skills"
                  :key="index"
                  class="inline-flex items-center rounded-full bg-blue-100 px-3 py-1 text-xs font-medium text-blue-800"
                >
                  {{ skill }}
                </span>
              </div>
            </div>
          </CardContent>
        </Card>
      </template>

      <!-- Empty State -->
      <Card v-else>
        <CardContent class="flex flex-col items-center justify-center py-10">
          <StarIcon class="h-12 w-12 text-muted-foreground mb-4" />
          <p class="text-center text-muted-foreground">您还没有收到任何评价</p>
          <p class="text-center text-muted-foreground text-sm mt-1">完成工作后，雇主会对您进行评价</p>
        </CardContent>
      </Card>
    </div>
  </div>
</template>
