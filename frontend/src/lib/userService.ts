import api from './api';

interface UserProfile {
  id?: number;
  username: string;
  email: string;
  fullName?: string;
  phoneNumber?: string;
  role: string;
}

interface UpdateProfileData {
  fullName?: string;
  phoneNumber?: string;
  email?: string;
}

interface ChangePasswordData {
  currentPassword: string;
  newPassword: string;
}

const userService = {
  getProfile: async () => {
    const response = await api.get<UserProfile>('/api/users/me');
    return response.data;
  },
  
  updateProfile: async (data: UpdateProfileData) => {
    const response = await api.put<UserProfile>('/api/users/me', data);
    return response.data;
  },
  
  changePassword: async (data: ChangePasswordData) => {
    return api.post('/api/users/me/change-password', data);
  }
};

export default userService;