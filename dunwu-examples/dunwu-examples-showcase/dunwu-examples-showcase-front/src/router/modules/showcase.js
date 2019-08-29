import Layout from '@/layout'

const mailRouter = {
  path: 'email',
  component: () => import('@/views/showcase/email'),
  name: 'email',
  meta: { title: '邮件', icon: 'email' }
}

const filesystemRouter = {
  path: 'filesystem',
  component: () => import('@/views/showcase/filesystem'),
  name: 'filesystem',
  meta: { title: '文件服务', icon: 'file-sync' },
  children: [
    {
      path: 'list',
      component: () => import('@/views/showcase/filesystem/list'),
      name: 'list',
      meta: { title: '文件列表', icon: 'file-sync' }
    },
    {
      path: 'upload',
      component: () => import('@/views/showcase/filesystem/upload'),
      hidden: true
    }
  ]
}

const schedulerRouter = {
  path: 'scheduler',
  component: () => import('@/views/showcase/scheduler'),
  name: 'scheduler',
  meta: { title: '调度服务', icon: 'timer' },
  children: [
    {
      path: 'list',
      component: () => import('@/views/showcase/scheduler/list'),
      name: 'list',
      meta: { title: '任务列表', icon: 'timer' }
    },
    {
      path: 'create',
      component: () => import('@/views/showcase/scheduler/detail'),
      hidden: true
    },
    {
      path: 'update',
      component: () => import('@/views/showcase/scheduler/detail'),
      hidden: true
    }
  ]
}

const templateRouter = {
  path: 'template',
  component: () => import('@/views/showcase/scheduler'),
  name: 'template',
  meta: { title: '模板服务', icon: 'template' },
  children: [
    {
      path: 'list',
      component: () => import('@/views/showcase/template/list'),
      name: 'list',
      meta: { title: '模板列表', icon: 'template' }
    },
    {
      path: 'create',
      component: () => import('@/views/showcase/template/detail'),
      hidden: true
    },
    {
      path: 'update',
      component: () => import('@/views/showcase/template/detail'),
      hidden: true
    }
  ]
}

const showcaseRouter = {
  path: '/showcase',
  component: Layout,
  name: 'showcase',
  meta: { title: '功能示例', icon: 'experiment' },
  children: [filesystemRouter, schedulerRouter, templateRouter, mailRouter]
}

export default showcaseRouter
