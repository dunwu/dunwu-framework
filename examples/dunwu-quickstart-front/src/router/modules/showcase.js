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
      meta: { title: '文件列表', icon: 'list' }
    },
    {
      path: 'upload',
      component: () => import('@/views/showcase/filesystem/upload'),
      name: 'upload',
      meta: { title: '文件上传', icon: 'upload' }
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
      meta: { title: '任务列表', icon: 'list' }
    },
    {
      path: 'detail',
      component: () => import('@/views/showcase/scheduler/detail'),
      name: 'detail',
      meta: { title: '任务详情', icon: 'eye' }
    },
    {
      path: 'edit',
      component: () => import('@/views/showcase/scheduler/detail'),
      hidden: true
    }
  ]
}

const showcaseRouter = {
  path: '/showcase',
  component: Layout,
  name: 'showcase',
  meta: { title: '功能示例', icon: 'experiment' },
  children: [filesystemRouter, schedulerRouter, mailRouter]
}

export default showcaseRouter
