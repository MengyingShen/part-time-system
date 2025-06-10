import api from './api'
import type { Resume } from '@/types/resume'

export interface ResumeDto {
  id?: number
  fullName: string
  phone: string
  email: string
  education: string
  school: string
  major: string
  graduationDate: string
  skills: string
  experience: string
  projects: string
  selfIntroduction: string
  completionPercentage?: number
  createdAt?: string
  updatedAt?: string
}

export const resumeService = {
  // 获取当前用户的简历
  async getResume(): Promise<ResumeDto | null> {
    try {
      const response = await api.get<ResumeDto>('/api/me/resume')
      return response.data
    } catch (error) {
      if (error.response?.status === 404) {
        return null // 简历不存在
      }
      console.error('获取简历失败:', error)
      throw error
    }
  },

  // 保存或更新简历
  async saveResume(resumeData: Partial<ResumeDto>): Promise<ResumeDto> {
    try {
      const response = await api.post<ResumeDto>('/api/me/resume', resumeData)
      return response.data
    } catch (error) {
      console.error('保存简历失败:', error)
      throw error
    }
  },

  // 获取简历完成度
  async getResumeCompletion(): Promise<number> {
    try {
      const response = await api.get<number>('/api/me/resume/completion')
      return response.data
    } catch (error) {
      console.error('获取简历完成度失败:', error)
      return 0
    }
  },
}

export default resumeService
