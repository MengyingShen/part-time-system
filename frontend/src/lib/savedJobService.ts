import api from './api'

export interface SavedJob {
  id: number
  jobId: number
  title: string
  company: string
  location: string
  type: string
  description: string
  salary: string
  savedAt: string
}

export const getSavedJobs = async (): Promise<SavedJob[]> => {
  try {
    const response = await api.get('/api/me/saved-jobs')
    return response.data.map((job: any) => ({
      ...job,
      id: job.id,
      savedAt: job.relativeSavedAt || job.savedAt,
    }))
  } catch (error) {
    console.error('Error fetching saved jobs:', error)
    throw error
  }
}

export const saveJob = async (jobId: number): Promise<void> => {
  try {
    await api.post(`/api/me/saved-jobs/${jobId}`)
  } catch (error) {
    console.error('Error saving job:', error)
    throw error
  }
}

export const unsaveJob = async (jobId: number): Promise<void> => {
  try {
    await api.delete(`/api/me/saved-jobs/${jobId}`)
  } catch (error) {
    console.error('Error unsaving job:', error)
    throw error
  }
}
