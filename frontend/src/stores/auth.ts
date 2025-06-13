import { defineStore } from 'pinia'
import { ref } from 'vue'
import api from '@/lib/api'

export const useAuthStore = defineStore('auth', () => {
  const user = ref(null)
  const isAuthenticated = ref(false)
  const isLoading = ref(false)
  const error = ref<string | null>(null)

  // Check if user is authenticated by validating the token
  const checkAuth = async () => {
    const token = getToken()
    if (!token) {
      isAuthenticated.value = false
      user.value = null
      return false
    }
    
    // Since we don't have a /me endpoint, we'll just check if we have a valid token
    // and assume the user is authenticated if the token exists and isn't expired
    // The token will be validated by the backend on the next API call
    isAuthenticated.value = true
    return true
  }

  // Login user
  const login = async (credentials: { username: string; password: string }) => {
    try {
      isLoading.value = true
      error.value = null
      const response = await api.post('/api/auth/signin', {
        username: credentials.username,
        password: credentials.password
      })
      
      const { token, id, username, email, fullName, roles } = response.data
      
      // Store the token
      setToken(token)
      
      // Set user data
      user.value = {
        id,
        username,
        email,
        fullName,
        roles
      }
      
      isAuthenticated.value = true
      return true
    } catch (err: any) {
      error.value = err.response?.data?.message || '登录失败，请重试'
      clearToken()
      isAuthenticated.value = false
      user.value = null
      throw err
    } finally {
      isLoading.value = false
    }
  }

  // Token management
  const getToken = (): string | null => {
    return localStorage.getItem('token')
  }

  const setToken = (token: string) => {
    // Store the token in localStorage
    localStorage.setItem('token', token)
    
    // Update axios default headers
    if (token) {
      api.defaults.headers.common['Authorization'] = `Bearer ${token}`
    } else {
      delete api.defaults.headers.common['Authorization']
    }
  }

  const clearToken = () => {
    localStorage.removeItem('token')
    delete api.defaults.headers.common['Authorization']
  }

  // Initialize auth state from token
  const initAuth = async () => {
    const token = getToken()
    if (!token) {
      isAuthenticated.value = false
      user.value = null
      return false
    }
    
    // Since we don't have a /me endpoint, we'll just check if the token exists
    // The actual user data will be populated on successful login
    isAuthenticated.value = true
    return true
  }

  // Logout user
  const logout = () => {
    clearToken()
    isAuthenticated.value = false
    user.value = null
  }

  return {
    user,
    isAuthenticated,
    isLoading,
    error,
    checkAuth,
    login,
    logout,
    initAuth,
    getToken,
    hasToken: () => !!getToken()
  }
})
