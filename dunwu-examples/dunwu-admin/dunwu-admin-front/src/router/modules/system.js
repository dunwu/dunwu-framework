import Layout from '@/layout'

/**
 * 用户管理页面路由
 */
const userRouter = {
  path: 'user',
  component: () => import('@/views/system/user'),
  name: 'user',
  meta: { title: '用户管理' },
  children: [
    {
      path: 'list',
      component: () => import('@/views/system/user/UserList'),
      name: 'UserList',
      meta: { title: '用户列表' }
    },
    {
      path: 'create',
      component: () => import('@/views/system/user/UserDetail'),
      hidden: true
    },
    {
      path: 'update',
      component: () => import('@/views/system/user/UserDetail'),
      hidden: true
    }
  ]
}
/**
 * 角色管理页面路由
 */
const roleRouter = {
  path: 'role',
  component: () => import('@/views/system/role'),
  name: 'role',
  meta: { title: '角色管理' },
  children: [
    {
      path: 'list',
      component: () => import('@/views/system/role/RoleList'),
      name: 'RoleList',
      meta: { title: '角色列表' }
    },
    {
      path: 'create',
      component: () => import('@/views/system/role/RoleDetail'),
      hidden: true
    },
    {
      path: 'update',
      component: () => import('@/views/system/role/RoleDetail'),
      hidden: true
    }
  ]
}
/**
 * 权限管理页面路由
 */
const permissionRouter = {
  path: 'permission',
  component: () => import('@/views/system/permission'),
  name: 'permission',
  meta: { title: '权限管理' },
  children: [
    {
      path: 'list',
      component: () => import('@/views/system/permission/PermissionList'),
      name: 'PermissionList',
      meta: { title: '权限列表' }
    },
    {
      path: 'create',
      component: () => import('@/views/system/permission/PermissionDetail'),
      hidden: true
    },
    {
      path: 'update',
      component: () => import('@/views/system/permission/PermissionDetail'),
      hidden: true
    }
  ]
}
/**
 * 菜单管理页面路由
 */
const menuRouter = {
  path: 'menu',
  component: () => import('@/views/system/menu'),
  name: 'menu',
  meta: { title: '菜单' },
  children: [
    {
      path: 'list',
      component: () => import('@/views/system/menu/MenuList'),
      name: 'MenuList',
      meta: { title: '菜单管理' }
    }
  ]
}

/**
 * 系统管理路由，只有角色为 admin 才能访问
 */
const systemRouter = {
  path: '/system',
  component: Layout,
  name: 'system',
  meta: {
    title: '系统管理',
    icon: 'setting',
    roles: ['admin']
  },
  children: [userRouter, roleRouter, permissionRouter, menuRouter]
}

export default systemRouter
