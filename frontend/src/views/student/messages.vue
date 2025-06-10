<script setup lang="ts">
import { ref, computed } from 'vue'
import { Button } from '@/components/ui/button'
import { Card } from '@/components/ui/card'
import { Input } from '@/components/ui/input'
import { Send, Paperclip, MoreHorizontal } from 'lucide-vue-next'

// --- TypeScript Interfaces (No changes needed) ---
interface Message {
  text: string
  time: string
  sentByMe: boolean
}

interface Conversation {
  id: number
  name: string
  company: string
  unread: boolean
  lastMessage: string
  lastMessageTime: string
  messages: Message[]
}

// --- Mock Data (No changes needed) ---
const conversations = ref<Conversation[]>([
  {
    id: 1,
    name: '李小红',
    company: '星巴克',
    unread: true,
    lastMessage: '您好，关于您申请的咖啡馆服务员职位...',
    lastMessageTime: '10:30',
    messages: [
      {
        text: '您好，我是李小红，星巴克校园咖啡馆的招聘负责人。',
        time: '昨天 10:30',
        sentByMe: false,
      },
      {
        text: '您好，很高兴认识您！',
        time: '昨天 10:35',
        sentByMe: true,
      },
      {
        text: '关于您申请的咖啡馆服务员职位，我们已经审核了您的申请，想邀请您来参加面试。',
        time: '昨天 10:40',
        sentByMe: false,
      },
      {
        text: '太好了，我很期待！请问面试时间是什么时候？',
        time: '昨天 10:45',
        sentByMe: true,
      },
      {
        text: '我们计划在本周五下午2点进行面试，您方便吗？',
        time: '昨天 10:50',
        sentByMe: false,
      },
      {
        text: '周五下午2点没问题，我会准时到的。请问需要带些什么材料吗？',
        time: '昨天 10:55',
        sentByMe: true,
      },
      {
        text: '只需要带您的学生证和简历即可。面试地点在校内星巴克咖啡馆二楼的会议室。',
        time: '昨天 11:00',
        sentByMe: false,
      },
      {
        text: '好的，我知道了，谢谢您的通知！',
        time: '昨天 11:05',
        sentByMe: true,
      },
      {
        text: '不客气，期待周五见到您！如果有任何问题，随时联系我。',
        time: '昨天 11:10',
        sentByMe: false,
      },
    ],
  },
  {
    id: 2,
    name: '王教授',
    company: '大学图书馆',
    unread: false,
    lastMessage: '您的图书馆助理申请已通过初审...',
    lastMessageTime: '昨天',
    messages: [
      {
        text: '您好，我是负责图书馆招聘的王教授。',
        time: '2天前 14:20',
        sentByMe: false,
      },
      {
        text: '您好，王教授！',
        time: '2天前 14:25',
        sentByMe: true,
      },
      {
        text: '您的图书馆助理申请已通过初审，我们想了解一下您的图书分类知识。',
        time: '2天前 14:30',
        sentByMe: false,
      },
      {
        text: '我在上学期选修了图书馆学导论课程，学习了杜威十进制分类法和国会图书馆分类系统。',
        time: '2天前 14:35',
        sentByMe: true,
      },
      {
        text: '很好，这对工作会有帮助。您什么时候可以来面试？',
        time: '2天前 14:40',
        sentByMe: false,
      },
      {
        text: '我周一到周四下午都有空，您看哪天合适？',
        time: '2天前 14:45',
        sentByMe: true,
      },
      {
        text: '那我们定在下周二下午3点，图书馆行政办公室见。',
        time: '2天前 14:50',
        sentByMe: false,
      },
      {
        text: '好的，我会准时到场。谢谢您！',
        time: '2天前 14:55',
        sentByMe: true,
      },
    ],
  },
  {
    id: 3,
    name: '张经理',
    company: '科技创新中心',
    unread: false,
    lastMessage: '恭喜您通过了我们的技术测试！',
    lastMessageTime: '3天前',
    messages: [
      {
        text: '您好，感谢您申请我们的前端开发实习生职位。',
        time: '上周五 09:15',
        sentByMe: false,
      },
      {
        text: '您好！很高兴有机会申请贵公司的职位。',
        time: '上周五 09:20',
        sentByMe: true,
      },
      {
        text: '我们看了您的简历，对您的技能很感兴趣。您能否完成一个小的技术测试？',
        time: '上周五 09:25',
        sentByMe: false,
      },
      {
        text: '当然可以，我很乐意参加技术测试。',
        time: '上周五 09:30',
        sentByMe: true,
      },
      {
        text: '太好了！我已经通过邮件发送了测试题，请在周一前完成。',
        time: '上周五 09:35',
        sentByMe: false,
      },
      {
        text: '收到了，我会认真完成并按时提交。',
        time: '上周五 09:40',
        sentByMe: true,
      },
      {
        text: '恭喜您通过了我们的技术测试！我们对您的代码质量和解决问题的能力印象深刻。',
        time: '周一 10:00',
        sentByMe: false,
      },
      {
        text: '谢谢！我很高兴能通过测试。',
        time: '周一 10:05',
        sentByMe: true,
      },
      {
        text: '我们想邀请您来参加最后的面试。您下周三上午有空吗？',
        time: '周一 10:10',
        sentByMe: false,
      },
      {
        text: '下周三上午我有空，非常期待面试机会！',
        time: '周一 10:15',
        sentByMe: true,
      },
    ],
  },
])

// --- NEW: Logic for handling chat functionality ---

// ID of the currently selected conversation, null if none is selected
const selectedConversationId = ref<number | null>(null)

// Text in the message input box
const messageText = ref('')

// A computed property to get the full conversation object based on the selected ID
const selectedConversation = computed(() => {
  if (selectedConversationId.value === null) {
    return null
  }
  return conversations.value.find((c) => c.id === selectedConversationId.value)
})

// Function to select a conversation from the list
const selectConversation = (id: number) => {
  selectedConversationId.value = id
}

// Function to send a message
const sendMessage = () => {
  if (!selectedConversation.value || !messageText.value.trim()) {
    return
  }

  // Add the new message to the conversation's message array
  selectedConversation.value.messages.push({
    text: messageText.value,
    time: new Date().toLocaleTimeString('zh-CN', { hour: '2-digit', minute: '2-digit' }),
    sentByMe: true,
  })

  // Clear the input field
  messageText.value = ''
}
</script>

<template>
  <div class="grid gap-6 p-6">
    <h1 class="text-2xl font-bold">消息</h1>

    <div class="grid grid-cols-1 md:grid-cols-[300px_1fr] gap-4">
      <Card class="md:h-[600px] overflow-hidden">
        <div class="p-4 border-b">
          <Input placeholder="搜索对话..." />
        </div>
        <div class="overflow-auto h-[calc(100%-60px)]">
          <div
            v-for="conversation in conversations"
            :key="conversation.id"
            class="flex items-center gap-3 p-3 cursor-pointer border-b"
            :class="{
              'bg-muted': selectedConversationId === conversation.id,
              'hover:bg-muted/50': selectedConversationId !== conversation.id,
            }"
            @click="selectConversation(conversation.id)"
          >
            <div class="relative">
              <div class="w-10 h-10 rounded-full bg-blue-100 flex items-center justify-center">
                <span class="text-blue-700 font-medium">
                  {{ conversation.name.charAt(0) }}
                </span>
              </div>
              <span
                v-if="conversation.unread"
                class="h-2 w-2 rounded-full bg-primary absolute -top-1 -right-1"
              ></span>
            </div>
            <div class="flex-1 min-w-0">
              <div class="flex items-center justify-between">
                <h3 class="font-medium">{{ conversation.name }}</h3>
                <span class="text-xs text-muted-foreground">{{
                  conversation.lastMessageTime
                }}</span>
              </div>
              <p class="text-sm text-muted-foreground truncate">
                {{ conversation.lastMessage }}
              </p>
            </div>
          </div>
        </div>
      </Card>

      <Card class="md:h-[600px] flex flex-col">
        <template v-if="selectedConversation">
          <div class="border-b p-4 flex items-center justify-between">
            <div>
              <h2 class="text-lg font-semibold">{{ selectedConversation.name }}</h2>
              <p class="text-sm text-muted-foreground">{{ selectedConversation.company }}</p>
            </div>
            <Button variant="ghost" size="icon">
              <MoreHorizontal class="h-4 w-4" />
            </Button>
          </div>

          <div class="flex-1 overflow-auto p-4 space-y-4">
            <div
              v-for="(message, index) in selectedConversation.messages"
              :key="index"
              class="flex"
              :class="{
                'justify-end': message.sentByMe,
                'justify-start': !message.sentByMe,
              }"
            >
              <div
                class="max-w-[80%] rounded-lg px-4 py-2"
                :class="{
                  'bg-primary text-primary-foreground': message.sentByMe,
                  'bg-muted': !message.sentByMe,
                }"
              >
                <p class="text-sm">{{ message.text }}</p>
                <p
                  class="text-xs mt-1"
                  :class="{
                    'text-primary-foreground/70': message.sentByMe,
                    'text-muted-foreground': !message.sentByMe,
                  }"
                >
                  {{ message.time }}
                </p>
              </div>
            </div>
          </div>

          <div class="border-t p-4">
            <div class="flex items-center gap-2">
              <Button variant="ghost" size="icon">
                <Paperclip class="h-4 w-4" />
              </Button>
              <Input
                v-model="messageText"
                placeholder="输入消息..."
                class="flex-1"
                @keydown.enter="sendMessage"
              />
              <Button type="submit" size="icon" @click="sendMessage">
                <Send class="h-4 w-4" />
              </Button>
            </div>
          </div>
        </template>
        <div v-else class="flex-1 flex items-center justify-center text-muted-foreground">
          请选择一个对话开始聊天
        </div>
      </Card>
    </div>
  </div>
</template>
