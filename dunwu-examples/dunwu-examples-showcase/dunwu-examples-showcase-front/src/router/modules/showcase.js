import Layout from '@/layout'

const mailRouter = {
  path: 'email',
  component: () => import('@/views/showcase/email'),
  name: 'email',
  meta: { title: '发送邮件' }
}

const filesystemRouter = {
  path: 'filesystem',
  component: () => import('@/views/showcase/filesystem'),
  name: 'filesystem',
  meta: { title: '文件服务' },
  children: [
    {
      path: 'list',
      component: () => import('@/views/showcase/filesystem/list'),
      name: 'list',
      meta: { title: '文件列表' }
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
  meta: { title: '调度服务' },
  children: [
    {
      path: 'list',
      component: () => import('@/views/showcase/scheduler/list'),
      name: 'SchedulerList',
      meta: { title: '任务列表' }
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
  component: () => import('@/views/showcase/template'),
  name: 'template',
  meta: { title: '模板服务' },
  children: [
    {
      path: 'list',
      component: () => import('@/views/showcase/template/list'),
      name: 'TemplateList',
      meta: { title: '模板列表' }
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

const generateIdRouter = {
  path: 'id',
  component: () => import('@/views/showcase/generate-id'),
  name: 'GenerateId',
  meta: { title: '分布式ID' }
}

const ipRegionRouter = {
  path: 'ip',
  component: () => import('@/views/showcase/ip-region'),
  name: 'IpRegion',
  meta: { title: 'IP所在地' }
}

const showcaseRouter = {
  path: '/showcase',
  component: Layout,
  name: 'showcase',
  meta: { title: '功能示例', icon: 'experiment' },
  children: [
    filesystemRouter,
    schedulerRouter,
    templateRouter,
    mailRouter,
    generateIdRouter,
    ipRegionRouter
  ]
}

export default showcaseRouter
