<script setup lang="ts">
import { ref, onMounted, nextTick } from 'vue';
import { Button } from '@/components/ui/button';
import { Card, CardContent, CardHeader, CardTitle } from '@/components/ui/card';
import { Input } from '@/components/ui/input';
import { Loader2, Send, User, Bot } from 'lucide-vue-next';
import { toast } from 'vue-sonner';
import { chatWithDeepSeek, type Message } from '@/lib/deepseekService';

interface ChatMessage extends Message {
  id: string;
  timestamp: Date;
  isError?: boolean;
}

const messages = ref<ChatMessage[]>([]);
const inputMessage = ref('');
const isLoading = ref(false);
const error = ref<string | null>(null);

// Generate a unique ID for each message
const generateId = () => Math.random().toString(36).substr(2, 9);

// Send message to DeepSeek API with streaming
const sendMessage = async () => {
  if (!inputMessage.value.trim() || isLoading.value) return;

  const userMessage: ChatMessage = {
    id: generateId(),
    role: 'user',
    content: inputMessage.value,
    timestamp: new Date(),
  };

  // Add user message to chat
  messages.value = [...messages.value, userMessage];
  
  // Create AI message with empty content that we'll update with streaming
  const aiMessageId = generateId();
  const aiMessage: ChatMessage = {
    id: aiMessageId,
    role: 'assistant',
    content: '',
    timestamp: new Date(),
  };
  
  messages.value = [...messages.value, aiMessage];
  inputMessage.value = '';
  isLoading.value = true;
  error.value = null;

  try {
    // Create conversation history for context (exclude the current AI message we just added)
    const conversationHistory: Message[] = [
      ...messages.value
        .filter(m => !m.isError && m.id !== aiMessageId)
        .map(({ role, content }) => ({ role, content })),
    ];

    // Get AI response with streaming
    await chatWithDeepSeek(conversationHistory, (chunk, done) => {
      if (done) {
        isLoading.value = false;
        return;
      }
      
      // Update the AI message content with the new chunk
      const messageIndex = messages.value.findIndex(m => m.id === aiMessageId);
      if (messageIndex !== -1) {
        const updatedMessages = [...messages.value];
        updatedMessages[messageIndex] = {
          ...updatedMessages[messageIndex],
          content: updatedMessages[messageIndex].content + chunk,
        };
        messages.value = updatedMessages;
        
        // Auto-scroll to bottom when new content arrives
        nextTick(() => {
          const container = document.querySelector('.chat-container');
          if (container) {
            container.scrollTop = container.scrollHeight;
          }
        });
      }
    });
  } catch (err) {
    console.error('Error in chat:', err);
    error.value = '无法获取AI回复，请稍后重试';
    toast({
      title: '错误',
      description: error.value,
      variant: 'destructive',
    });
    
    // Update the existing AI message with error state
    const messageIndex = messages.value.findIndex(m => m.id === aiMessageId);
    if (messageIndex !== -1) {
      const updatedMessages = [...messages.value];
      updatedMessages[messageIndex] = {
        ...updatedMessages[messageIndex],
        content: '抱歉，我暂时无法处理您的请求。请稍后再试。',
        isError: true,
      };
      messages.value = updatedMessages;
    }
  } finally {
    isLoading.value = false;
  }
};

// Handle Enter key press
const handleKeyDown = (e: KeyboardEvent) => {
  if (e.key === 'Enter' && !e.shiftKey) {
    e.preventDefault();
    sendMessage();
  }
};

// Initialize with a welcome message
onMounted(() => {
  messages.value = [{
    id: generateId(),
    role: 'assistant',
    content: '你好！我是你的AI助手，有什么我可以帮你的吗？',
    timestamp: new Date(),
  }];
});
</script>

<template>
  <div class="flex flex-col h-[calc(100vh-100px)] max-w-4xl mx-auto p-4">
    <Card class="flex-1 flex flex-col overflow-hidden">
      <CardHeader class="border-b">
        <CardTitle class="text-xl">AI 聊天助手</CardTitle>
      </CardHeader>
      
      <!-- Chat messages -->
      <CardContent class="flex-1 overflow-y-auto p-4 space-y-4 chat-container">
        <div v-for="message in messages" :key="message.id" 
             class="flex items-start gap-3"
             :class="{ 'justify-end': message.role === 'user' }">
          <!-- AI Avatar -->
          <div v-if="message.role === 'assistant'" 
               class="flex-shrink-0 h-8 w-8 rounded-full bg-primary/10 flex items-center justify-center">
            <Bot class="h-4 w-4 text-primary" />
          </div>
          
          <!-- Message Content -->
          <div :class="[
      'max-w-[80%] px-4 py-2 rounded-lg',
      message.role === 'user' ? 'bg-primary text-primary-foreground' : 'bg-muted',
      message.isError ? 'bg-red-50 text-red-800' : ''
    ]">
            <div class="whitespace-pre-wrap break-words">{{ message.content }}</div>
            <div class="text-xs mt-1 opacity-70" 
                 :class="message.role === 'user' ? 'text-primary-foreground/70' : 'text-muted-foreground'">
              {{ message.timestamp.toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' }) }}
            </div>
          </div>
          
          <!-- User Avatar -->
          <div v-if="message.role === 'user'" 
               class="flex-shrink-0 h-8 w-8 rounded-full bg-primary flex items-center justify-center">
            <User class="h-4 w-4 text-primary-foreground" />
          </div>
        </div>
        
        <!-- Loading indicator -->
        <div v-if="isLoading" class="flex items-center gap-2 p-2">
          <div class="h-2 w-2 rounded-full bg-primary/50 animate-bounce" style="animation-delay: 0s"></div>
          <div class="h-2 w-2 rounded-full bg-primary/50 animate-bounce" style="animation-delay: 0.2s"></div>
          <div class="h-2 w-2 rounded-full bg-primary/50 animate-bounce" style="animation-delay: 0.4s"></div>
        </div>
      </CardContent>
      
      <!-- Input area -->
      <div class="border-t p-4">
        <div class="flex gap-2">
          <Input
            v-model="inputMessage"
            @keydown="handleKeyDown"
            :disabled="isLoading"
            placeholder="输入消息..."
            class="flex-1"
          />
          <Button 
            @click="sendMessage" 
            :disabled="!inputMessage.trim() || isLoading"
            size="icon"
          >
            <Send v-if="!isLoading" class="h-4 w-4" />
            <Loader2 v-else class="h-4 w-4 animate-spin" />
          </Button>
        </div>
        <p v-if="error" class="text-sm text-destructive mt-2">{{ error }}</p>
      </div>
    </Card>
  </div>
</template>

<style scoped>
.chat-container {
  scroll-behavior: smooth;
}
</style>
