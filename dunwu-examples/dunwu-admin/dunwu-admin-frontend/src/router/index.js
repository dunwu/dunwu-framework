/* Layout */
import Layout from '@/layout'
import Vue from 'vue'
import Router from 'vue-router'
/* Router Modules */
import demosRouter from './modules/demos'
import showcaseRouter from './modules/showcase'
import systemRouter from './modules/system'

Vue.use(Router)

/**
 * constantRoutes
 * a base page that does not have permission requirements
 * all roles can be accessed
 */
export const constantRoutes = [
  {
    path: '/login',
    meta: { title: '登录', noCache: true },
    component: () => import('@/views/auth/login'),
    hidden: true
  },
  {
    path: '/register',
    component: () => import('@/views/auth/register'),
    hidden: true
  },
  {
    path: '/auth-redirect',
    component: () => import('@/views/auth/auth-redirect'),
    hidden: true
  },
  {
    path: '/401',
    component: () => import('@/views/common/401'),
    hidden: true
  },
  {
    path: '/404',
    component: () => import('@/views/common/404'),
    hidden: true
  },
  {
    path: '/redirect',
    component: Layout,
    hidden: true,
    children: [
      {
        path: '/redirect/:path*',
        component: () => import('@/views/common/redirect')
      }
    ]
  },
  {
    path: '/',
    component: Layout,
    redirect: '/profile/index'
  },
  {
    path: '/profile',
    component: Layout,
    redirect: '/profile/index',
    hidden: true,
    children: [
      {
        path: 'index',
        component: () => import('@/views/profile/index'),
        name: 'Profile',
        meta: { title: 'Profile', icon: 'user', noCache: true, affix: true }
      }
    ]
  }
]

/**
 * asyncRoutes
 * the routes that need to be dynamically loaded based on user roles
 */
export const asyncRoutes = [
  /** when your routing map is too long, you can split it into small modules **/
  demosRouter,
  showcaseRouter,
  systemRouter,

  // 404 page must be placed at the end !!!
  { path: '*', redirect: '/404', hidden: true }
]

const createRouter = () =>
  new Router({
    // mode: 'history', // require service support
    scrollBehavior: () => ({ y: 0 }),
    routes: constantRoutes
  })

const router = createRouter()

// Detail see: https://github.com/vuejs/vue-router/issues/1234#issuecomment-357941465
export function resetRouter() {
  const newRouter = createRouter()
  router.matcher = newRouter.matcher // reset router
}

export default router
