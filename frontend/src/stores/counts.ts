import { defineStore } from 'pinia'
import { ref, computed } from 'vue'
import api from '@/lib/api'

interface CountsState {
  applicationsCount: number
  savedJobsCount: number
  isLoading: boolean
  error: string | null
}

export const useCountsStore = defineStore('counts', () => {
  // State
  const state = ref<CountsState>({
    applicationsCount: 0,
    savedJobsCount: 0,
    isLoading: false,
    error: null
  })
  
  // Getters
  const applicationsCount = computed(() => state.value.applicationsCount)
  const savedJobsCount = computed(() => state.value.savedJobsCount)
  const isLoading = computed(() => state.value.isLoading)
  const error = computed(() => state.value.error)
  
  // Actions
  const setApplicationsCount = (count: number) => {
    state.value.applicationsCount = count
  }
  
  const setSavedJobsCount = (count: number) => {
    state.value.savedJobsCount = count
  }
  
  const incrementSavedJobsCount = () => {
    state.value.savedJobsCount++
  }
  
  const decrementSavedJobsCount = () => {
    if (state.value.savedJobsCount > 0) {
      state.value.savedJobsCount--
    }
  }
  
  const incrementApplicationsCount = () => {
    state.value.applicationsCount++
  }
  
  const decrementApplicationsCount = () => {
    if (state.value.applicationsCount > 0) {
      state.value.applicationsCount--
    }
  }
  
  const $reset = () => {
    state.value = {
      applicationsCount: 0,
      savedJobsCount: 0,
      isLoading: false,
      error: null
    }
  }
  
  const fetchCounts = async () => {
    console.log('Fetching counts...')
    state.value.isLoading = true
    state.value.error = null
    
    try {
      // Fetch saved jobs count
      const [savedJobsRes, applicationsRes] = await Promise.all([
        api.get('/api/me/saved-jobs'),
        api.get('/api/me/applications')
      ])
      
      const savedJobs = Array.isArray(savedJobsRes.data) ? savedJobsRes.data : []
      const applications = Array.isArray(applicationsRes.data) ? applicationsRes.data : []
      
      state.value.savedJobsCount = savedJobs.length
      state.value.applicationsCount = applications.length
      
      console.log('Counts updated:', {
        savedJobs: savedJobs.length,
        applications: applications.length
      })
      
      return {
        savedJobs: savedJobs.length,
        applications: applications.length
      }
    } catch (error) {
      console.error('Failed to fetch counts:', error)
      state.value.error = error instanceof Error ? error.message : 'Failed to fetch counts'
      throw error
    } finally {
      state.value.isLoading = false
    }
  }
  return {
    // State
    state,
    
    // Getters
    applicationsCount,
    savedJobsCount,
    isLoading,
    error,
    
    // Actions
    fetchCounts,
    setApplicationsCount,
    setSavedJobsCount,
    incrementApplicationsCount,
    decrementApplicationsCount,
    incrementSavedJobsCount,
    decrementSavedJobsCount,
    $reset
  }
})
