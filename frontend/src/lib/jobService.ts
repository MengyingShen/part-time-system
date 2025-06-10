import api from './api';

export interface Job {
  id: number;
  title: string;
  company: string;
  location: string;
  type: 'PART_TIME' | 'INTERNSHIP' | 'TEMPORARY' | 'VOLUNTEER';
  description: string;
  salary: string;
  postedAt: string;
  requirements?: string;
  responsibilities?: string;
  status?: 'OPEN' | 'CLOSED' | 'PAUSED';
  employerId?: number;
  createdAt?: string;
  updatedAt?: string;
}

export interface SearchParams {
  keyword?: string;
  location?: string;
  jobType?: string;
  page?: number;
  limit?: number;
}

const jobService = {
  // Search jobs with filters
  searchJobs: async (params: SearchParams): Promise<{ data: Job[]; total: number }> => {
    try {
      const response = await api.get('/jobs/search', { params });
      // The backend returns the array directly, not wrapped in content/totalElements
      return {
        data: Array.isArray(response.data) ? response.data : [],
        total: Array.isArray(response.data) ? response.data.length : 0
      };
    } catch (error) {
      console.error('Error searching jobs:', error);
      throw error;
    }
  },

  // Get job details by ID
  getJobById: async (id: number): Promise<Job> => {
    try {
      const response = await api.get(`/jobs/${id}`);
      return response.data;
    } catch (error) {
      console.error(`Error fetching job ${id}:`, error);
      throw error;
    }
  },

  // Apply for a job
  applyForJob: async (jobId: number, applicationData: any): Promise<any> => {
    try {
      const response = await api.post(`/api/applications`, {
        jobId,
        ...applicationData
      });
      return response.data;
    } catch (error) {
      console.error('Error applying for job:', error);
      throw error;
    }
  },

  // Save/unsave job
  toggleSaveJob: async (jobId: number): Promise<{ saved: boolean }> => {
    try {
      const response = await api.post(`/api/jobs/${jobId}/toggle-save`);
      return response.data;
    } catch (error) {
      console.error('Error toggling job save:', error);
      throw error;
    }
  },

  // Get saved jobs
  getSavedJobs: async (): Promise<Job[]> => {
    try {
      const response = await api.get('/me/saved-jobs');
      return response.data;
    } catch (error) {
      console.error('Error fetching saved jobs:', error);
      throw error;
    }
  }
};

export default jobService;
