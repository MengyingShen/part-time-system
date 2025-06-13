import api from './api'

export interface Job {
  id: number
  title: string
  company: string
  location: string
  type: 'PART_TIME' | 'INTERNSHIP' | 'TEMPORARY' | 'VOLUNTEER'
  description: string
  salary: string
  postedAt: string
  requirements?: string
  responsibilities?: string
  status?: 'OPEN' | 'CLOSED' | 'PAUSED'
  employerId?: number
  createdAt?: string
  updatedAt?: string
}

export interface SearchParams {
  keyword?: string
  location?: string
  jobType?: string
  page?: number
  limit?: number
}

const jobService = {
  // Search jobs with filters
  searchJobs: async (params: SearchParams): Promise<{ data: Job[]; total: number }> => {
    try {
      const response = await api.get('/jobs/search', { params })
      // The backend returns the array directly, not wrapped in content/totalElements
      return {
        data: Array.isArray(response.data) ? response.data : [],
        total: Array.isArray(response.data) ? response.data.length : 0,
      }
    } catch (error) {
      console.error('Error searching jobs:', error)
      throw error
    }
  },

  // Get job details by ID
  getJobById: async (id: number): Promise<Job> => {
    try {
      const response = await api.get(`/jobs/${id}`)
      return response.data
    } catch (error) {
      console.error(`Error fetching job ${id}:`, error)
      throw error
    }
  },

  // Apply for a job
  applyForJob: async (jobId: number, applicationData: any): Promise<any> => {
    try {
      const response = await api.post(`/api/applications`, {
        jobId,
        ...applicationData,
      })
      return response.data
    } catch (error) {
      console.error('Error applying for job:', error)
      throw error
    }
  },

  // Save/unsave job
  toggleSaveJob: async (jobId: number): Promise<{ saved: boolean; error?: string }> => {
    try {
      // Check if the job is already saved first
      const savedJobs = await api.get('/api/me/saved-jobs').catch(error => {
        if (error.response?.status === 401) {
          throw { response: { status: 401 } };
        }
        throw error;
      });
      
      const isSaved = savedJobs?.data?.some((savedJob: any) => savedJob.jobId === jobId);
      
      if (isSaved) {
        // If job is saved, unsave it
        await api.delete(`/api/me/saved-jobs/${jobId}`);
        return { saved: false };
      } else {
        // If job is not saved, save it
        await api.post(`/api/me/saved-jobs/${jobId}`);
        return { saved: true };
      }
    } catch (error: any) {
      console.error('Error toggling job save:', error);
      if (error.response?.status === 401) {
        return { 
          saved: false, 
          error: 'Please log in to save jobs' 
        };
      }
      if (error.response && error.response.data && error.response.data.message) {
        return { 
          saved: false, 
          error: error.response.data.message 
        };
      }
      return { 
        saved: false, 
        error: 'Failed to save job. Please try again later.' 
      };
    }
  },

  // Get saved jobs
  getSavedJobs: async (): Promise<Array<{id: number, jobId: number}>> => {
    try {
      console.log('Fetching saved jobs from /api/me/saved-jobs')
      const response = await api.get('/api/me/saved-jobs')
      console.log('Saved jobs response:', response.data)
      
      if (!Array.isArray(response.data)) {
        console.error('Expected array of saved jobs, got:', response.data)
        return []
      }
      
      // Transform the response to ensure we have jobId
      const savedJobs = response.data.map(item => ({
        id: item.id, // The saved job record ID
        jobId: item.jobId || item.job?.id // The actual job ID
      })).filter(Boolean)
      
      console.log('Processed saved jobs:', savedJobs)
      return savedJobs
    } catch (error) {
      if (error.response?.status === 401) {
        console.log('User not authenticated, returning empty saved jobs')
        return []
      }
      console.error('Error fetching saved jobs:', error)
      throw error
    }
  },
}

export default jobService
