import {
  createRouter,
  createWebHistory,
  type RouteLocationNormalized,
  type NavigationGuardNext,
} from 'vue-router'
import { toast } from 'vue-sonner'

// Helper function to check if user is authenticated
const isAuthenticated = (): boolean => {
  const token = localStorage.getItem('token')
  return !!token
}

// Authentication guard
const requireAuth = (
  to: RouteLocationNormalized,
  from: RouteLocationNormalized,
  next: NavigationGuardNext,
) => {
  if (!isAuthenticated()) {
    toast.warning('请先登录以继续')
    next({
      path: '/login',
      query: { redirect: to.fullPath !== '/' ? to.fullPath : undefined },
    })
  } else {
    next()
  }
}

// Public only guard (for login/register pages)
const requireGuest = (
  to: RouteLocationNormalized,
  from: RouteLocationNormalized,
  next: NavigationGuardNext,
) => {
  if (isAuthenticated()) {
    next({ path: '/student' })
  } else {
    next()
  }
}

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: () => import('@/views/HomeView.vue'),
    },
    {
      path: '/login',
      name: 'login',
      component: () => import('@/views/LoginView.vue'),
      beforeEnter: requireGuest,
    },
    {
      path: '/register',
      name: 'register',
      component: () => import('@/views/RegisterView.vue'),
      beforeEnter: requireGuest,
    },
    // Admin routes
    // {
    //   path: '/admin',
    //   component: AdminLayout,
    //   children: [
    //     { path: '', component: lazyLoad('admin/DashboardView') },
    //     { path: 'profile', component: lazyLoad('admin/ProfileView') },
    //     { path: 'students', component: lazyLoad('admin/StudentsView') },
    //     { path: 'resumes', component: lazyLoad('admin/ResumesView') },
    //     { path: 'companies', component: lazyLoad('admin/CompaniesView') },
    //     { path: 'jobs', component: lazyLoad('admin/JobsView') },
    //     { path: 'applications', component: lazyLoad('admin/ApplicationsView') },
    //     { path: 'reviews', component: lazyLoad('admin/ReviewsView') },
    //     { path: 'messages', component: lazyLoad('admin/MessagesView') },
    //     { path: 'system', component: lazyLoad('admin/SystemView') }
    //   ]
    // },
    // // Employer routes
    // {
    //   path: '/employer',
    //   component: EmployerLayout,
    //   children: [
    //     { path: '', component: lazyLoad('employer/DashboardView') },
    //     { path: 'profile', component: lazyLoad('employer/ProfileView') },
    //     { path: 'jobs', component: lazyLoad('employer/JobsView') },
    //     { path: 'post-job', component: lazyLoad('employer/PostJobView') },
    //     { path: 'applications', component: lazyLoad('employer/ApplicationsView') },
    //     { path: 'reviews', component: lazyLoad('employer/ReviewsView') },
    //     { path: 'favorites', component: lazyLoad('employer/FavoritesView') },
    //     { path: 'analytics', component: lazyLoad('employer/AnalyticsView') },
    //     { path: 'messages', component: lazyLoad('employer/MessagesView') },
    //     { path: 'settings', component: lazyLoad('employer/SettingsView') }
    //   ]
    // },
    // Student routes
    {
      path: '/student',
      component: () => import('@/views/student/layout.vue'),
      beforeEnter: requireAuth,
      children: [
        { path: '', component: () => import('@/views/student/index.vue') },
        { path: 'profile', component: () => import('@/views/student/profile.vue') },
        { path: 'search', component: () => import('@/views/student/search.vue') },
        { path: 'search/:id', component: () => import('@/views/student/search.vue') },
        { path: 'applications', component: () => import('@/views/student/applications.vue') },
        { path: 'saved', component: () => import('@/views/student/saved.vue') },
        { path: 'resume', component: () => import('@/views/student/resume.vue') },
        { path: 'reviews', component: () => import('@/views/student/reviews.vue') },
        { path: 'write-review', component: () => import('@/views/student/write-review.vue') },
        { path: 'messages', component: () => import('@/views/student/messages.vue') },
        { path: 'settings', component: () => import('@/views/student/settings.vue') },
        { path: 'llm', component: () => import('@/views/student/llm.vue') },
        { path: 'data-dashboard', component: () => import('@/views/student/dataDashboard.vue') },
      ],
    },
  ],
})

// Global navigation guard
router.beforeEach((to, from, next) => {
  // Add any global navigation logic here if needed
  console.log(`Navigating from ${from.path} to ${to.path}`)
  next()
})

export default router
