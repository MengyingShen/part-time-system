import api from './api'

interface LoginData {
  username: string
  password: string
}

interface RegisterData {
  username: string
  password: string
  email: string
  fullName: string
  phoneNumber: string
  role: 'STUDENT' | 'EMPLOYER'
}

interface BackendAuthResponse {
  accessToken: string
  id: number
  username: string
  email: string
  fullName: string
  roles: string[]
  tokenType: string
}

interface AuthResponse {
  token: string
  type: string
  id: number
  username: string
  email: string
  role: string
}

const authService = {
  login: async (data: LoginData): Promise<AuthResponse> => {
    try {
      console.log('AuthService: Starting login process')
      const response = await api.post<BackendAuthResponse>('/api/auth/signin', data)
      console.log('AuthService: Received response from server:', response)

      const { accessToken, tokenType, id, username, email, roles } = response.data
      console.log('AuthService: Extracted token and user data')

      // 转换角色格式: ROLE_STUDENT -> STUDENT
      const role = roles[0].replace('ROLE_', '')
      console.log('AuthService: User role:', role)

      // 创建用户对象以便于前端使用
      const userData = {
        id,
        username,
        email,
        role,
      }

      console.log('AuthService: Storing token and user data in localStorage')
      try {
        localStorage.setItem('token', accessToken)
        localStorage.setItem('user', JSON.stringify(userData))
        console.log('AuthService: Successfully stored token and user data')
        console.log('AuthService: Token in localStorage:', localStorage.getItem('token'))
        console.log('AuthService: User in localStorage:', localStorage.getItem('user'))
      } catch (storageError) {
        console.error('AuthService: Error storing data in localStorage:', storageError)
        throw new Error('无法保存登录信息，请检查浏览器设置')
      }

      return {
        token: accessToken,
        type: tokenType,
        id,
        username,
        email,
        role,
      }
    } catch (error: any) {
      console.error('Login error:', error.response?.data || error.message)
      throw error
    }
  },

  register: async (data: RegisterData): Promise<AuthResponse> => {
    try {
      const response = await api.post<BackendAuthResponse>('/api/auth/signup', data)
      const { accessToken, tokenType, id, username, email, roles } = response.data

      // 转换角色格式: ROLE_STUDENT -> STUDENT
      const role = roles[0].replace('ROLE_', '')

      // 创建用户对象以便于前端使用
      const userData = {
        id,
        username,
        email,
        role,
      }

      localStorage.setItem('token', accessToken)
      localStorage.setItem('user', JSON.stringify(userData))

      return {
        token: accessToken,
        type: tokenType,
        id,
        username,
        email,
        role,
      }
    } catch (error: any) {
      console.error('Registration error:', error.response?.data || error.message)
      throw error
    }
  },

  logout: () => {
    localStorage.removeItem('token')
    localStorage.removeItem('user')
  },

  getCurrentUser: (): any => {
    console.log('Getting current user from localStorage')
    const user = localStorage.getItem('user')
    console.log('User from localStorage:', user)
    return user ? JSON.parse(user) : null
    const userStr = localStorage.getItem('user')
    if (userStr) {
      return JSON.parse(userStr)
    }
    return null
  },
}

export default authService
