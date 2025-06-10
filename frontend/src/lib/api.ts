import axios from 'axios';

// Create an Axios instance with default config
const api = axios.create({
  baseURL: '/', // Base URL is set to root since we're using Vite proxy
  timeout: 10000,
  headers: {
    'Content-Type': 'application/json',
    'Accept': 'application/json'
  },
  // Important for Spring Security CSRF handling
  withCredentials: true,
  withXSRFToken: true
});

// Request interceptor for adding auth token
api.interceptors.request.use(
  (config) => {
    const token = localStorage.getItem('token');
    if (token) {
      console.log('Adding Authorization header with token');
      config.headers.Authorization = `Bearer ${token}`;
    } else {
      console.warn('No token found in localStorage');
    }
    return config;
  },
  (error) => {
    console.error('Request interceptor error:', error);
    return Promise.reject(error);
  }
);

// Response interceptor for handling errors globally
api.interceptors.response.use(
  (response) => response,
  (error) => {
    // Handle different error response statuses
    if (error.response) {
      // The request was made and the server responded with a status code
      // that falls out of the range of 2xx
      const { status } = error.response;
      
      if (status === 401) {
        // Session expired or unauthorized
        console.warn('Authentication error - received 401 status');
        
        // Check if this is a request to the search or saved-jobs endpoint
        const isSearchRequest = error.config?.url?.includes('/jobs/search');
        const isSavedJobsRequest = error.config?.url?.includes('/saved-jobs');
        
        if (isSearchRequest || isSavedJobsRequest) {
          console.log(`${isSearchRequest ? 'Search' : 'Saved jobs'} request returned 401 - keeping token and showing empty results`);
          // Return empty array for results instead of redirecting
          return Promise.resolve({ data: isSearchRequest ? { content: [], totalElements: 0 } : [] });
        }
        
        // Only redirect if not already on login page to prevent infinite redirects
        if (!window.location.pathname.includes('/login')) {
          console.log('Redirecting to login page');
          localStorage.removeItem('token');
          window.location.href = `/login?redirect=${encodeURIComponent(window.location.pathname)}`;
        } else {
          console.log('Already on login page, not redirecting');
        }
      } else if (status === 403) {
        // Forbidden
        console.warn('Forbidden access to resource');
      } else if (status === 400) {
        // Bad request - validation errors, etc.
        console.warn('Bad request:', error.response.data);
      } else if (status === 404) {
        // Not found
        console.warn('Resource not found');
      } else if (status === 500) {
        // Server error
        console.error('Server error:', error.response.data);
      }
    } else if (error.request) {
      // The request was made but no response was received
      console.error('Network error - no response received');
    } else {
      // Something happened in setting up the request
      console.error('Error setting up request:', error.message);
    }
    
    return Promise.reject(error);
  }
);

export default api;
