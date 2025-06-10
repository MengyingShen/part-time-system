import api from './api'

export interface Application {
  id: number
  jobTitle: string
  company: string
  location: string
  status: string
  appliedDate: string
  interviewDate?: string
  feedback?: string
}

const applicationService = {
  // Get all applications for the current user
  getMyApplications: async (): Promise<Application[]> => {
    try {
      const response = await api.get('/me/applications')
      return response.data.map((app: any) => ({
        id: app.id,
        jobTitle: app.jobTitle,
        company: app.company,
        location: app.location,
        status: app.statusDisplay || app.status,
        appliedDate: app.appliedDate,
        interviewDate: app.interviewDate,
        feedback: app.feedback,
      }))
    } catch (error) {
      console.error('Failed to fetch applications:', error)
      throw error
    }
  },
}

export default applicationService
