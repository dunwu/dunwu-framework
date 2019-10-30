import Layout from '@/layout'
import componentsRouter from './components'
import nestedRouter from './nested'

const articleRouter = {
  path: '/demos/article',
  component: () => import('@/views/demos/article/index'),
  name: 'Article',
  meta: {
    title: 'Article', icon: 'example'
  },
  redirect: '/demos/article/list',
  children: [{
    path: 'create',
    component: () => import('@/views/demos/article/create'),
    name: 'CreateArticle',
    meta: { title: 'Create Article', icon: 'edit' }
  }, {
    path: 'edit/:id(\\d+)',
    component: () => import('@/views/demos/article/edit'),
    name: 'EditArticle',
    meta: {
      title: 'Edit Article', noCache: true, activeMenu: '/demos/article/list'
    },
    hidden: true
  }, {
    path: 'list',
    component: () => import('@/views/demos/article/list'),
    name: 'ArticleList',
    meta: { title: 'Article List', icon: 'list' }
  }]
}

const chartsRouter = {
  path: '/demos/charts',
  component: () => import('@/views/demos/charts/index'),
  name: 'Charts',
  meta: {
    title: 'Charts', icon: 'chart'
  },
  redirect: '/demos/charts/keyboard',
  children: [{
    path: 'keyboard',
    component: () => import('@/views/demos/charts/keyboard'),
    name: 'KeyboardChart',
    meta: { title: 'Keyboard Chart', noCache: true }
  }, {
    path: 'line',
    component: () => import('@/views/demos/charts/line'),
    name: 'LineChart',
    meta: { title: 'Line Chart', noCache: true }
  }, {
    path: 'mix-chart',
    component: () => import('@/views/demos/charts/mix-chart'),
    name: 'MixChart',
    meta: { title: 'Mix Chart', noCache: true }
  }]
}

const dashboardRouter = {
  path: 'dashboard',
  component: () => import('@/views/demos/dashboard/index'),
  name: 'Dashboard',
  meta: { title: 'Dashboard', icon: 'dashboard' }
}

const excelRouter = {
  path: '/demos/excel',
  component: () => import('@/views/demos/excel/index'),
  redirect: '/excel/export-excel',
  name: 'Excel',
  meta: {
    title: 'Excel', icon: 'excel'
  },
  children: [{
    path: 'export-excel',
    component: () => import('@/views/demos/excel/export-excel'),
    name: 'ExportExcel',
    meta: { title: 'Export Excel' }
  }, {
    path: 'export-selected-excel',
    component: () => import('@/views/demos/excel/select-excel'),
    name: 'SelectExcel',
    meta: { title: 'Export Selected' }
  }, {
    path: 'export-merge-header',
    component: () => import('@/views/demos/excel/merge-header'),
    name: 'MergeHeader',
    meta: { title: 'Merge Header' }
  }, {
    path: 'upload-excel',
    component: () => import('@/views/demos/excel/upload-excel'),
    name: 'UploadExcel',
    meta: { title: 'Upload Excel' }
  }]
}

const clipboardRouter = {
  path: '/demos/clipboard',
  component: () => import('@/views/demos/clipboard/index'),
  name: 'ClipboardDemo',
  meta: { title: 'Clipboard', icon: 'clipboard' }
}

const guideRouter = {
  path: '/demos/guide',
  component: () => import('@/views/demos/guide/index'),
  name: 'Guide',
  meta: { title: 'Guide', icon: 'guide', noCache: true }
}

const iconRouter = {
  path: '/demos/icon',
  component: () => import('@/views/demos/icons/index'),
  name: 'Icons',
  meta: { title: 'Icons', icon: 'icon', noCache: true }
}

const pdfRouter = {
  path: '/demos/pdf', component: () => import('@/views/demos/pdf/index'), children: [{
    path: 'test',
    component: () => import('@/views/demos/pdf/test'),
    name: 'PDF',
    meta: { title: 'PDF', icon: 'pdf' }
  }, {
    path: '/download', component: () => import('@/views/demos/pdf/download'), hidden: true
  }]
}

const permissionRouter = {
  path: '/permission',
  component: () => import('@/views/demos/permission/index'),
  redirect: '/permission/page',
  alwaysShow: true, // will always show the root menu
  name: 'Permission',
  meta: {
    title: 'Permission', icon: 'lock', roles: ['admin', 'editor'] // you can set roles in root nav
  },
  children: [{
    path: 'page',
    component: () => import('@/views/demos/permission/page'),
    name: 'PagePermission',
    meta: {
      title: 'Page Permission', roles: ['admin'] // or you can only set roles in sub nav
    }
  }, {
    path: 'directive',
    component: () => import('@/views/demos/permission/directive'),
    name: 'DirectivePermission',
    meta: {
      title: 'Directive Permission'
      // if do not set roles, means: this page does not require permission
    }
  }, {
    path: 'role',
    component: () => import('@/views/demos/permission/role'),
    name: 'RolePermission',
    meta: {
      title: 'Role Permission', roles: ['admin']
    }
  }]
}

const tableRouter = {
  path: '/demos/table', component: () => import('@/views/demos/table/index'), name: 'Table', meta: {
    title: 'Table', icon: 'table'
  }, children: [{
    path: 'dynamic-table',
    component: () => import('@/views/demos/table/dynamic-table/index'),
    name: 'DynamicTable',
    meta: { title: 'Dynamic Table' }
  }, {
    path: 'drag-table',
    component: () => import('@/views/demos/table/drag-table'),
    name: 'DragTable',
    meta: { title: 'Drag Table' }
  }, {
    path: 'inline-edit-table',
    component: () => import('@/views/demos/table/inline-edit-table'),
    name: 'InlineEditTable',
    meta: { title: 'Inline Edit' }
  }, {
    path: 'complex-table',
    component: () => import('@/views/demos/table/complex-table'),
    name: 'ComplexTable',
    meta: { title: 'Complex Table' }
  }]
}

const tabRouter = {
  path: '/demos/tab',
  component: () => import('@/views/demos/tab/index'),
  name: 'Tab',
  meta: { title: 'Tab', icon: 'tab' }
}

const themeRouter = {
  path: '/demos/theme',
  component: () => import('@/views/demos/theme/index'),
  name: 'Theme',
  meta: { title: 'Theme', icon: 'theme' }
}

const zipRouter = {
  path: '/demos/zip',
  component: () => import('@/views/demos/zip/index'),
  name: 'ExportZip',
  meta: { title: 'Export Zip' }
}

const externalRouter = {
  path: 'external-link', component: Layout, children: [{
    path: 'https://github.com/dunwu/dunwu', meta: { title: 'External Link', icon: 'link' }
  }]
}

const demosRouter = {
  path: '/demos',
  component: Layout,
  redirect: '/demos/article/list',
  name: 'Demos',
  meta: { title: 'Demos', icon: 'example' },
  children: [componentsRouter, articleRouter, chartsRouter, clipboardRouter, dashboardRouter, excelRouter, guideRouter, iconRouter, pdfRouter, permissionRouter, nestedRouter, tableRouter, tabRouter, themeRouter, zipRouter, externalRouter]
}

export default demosRouter
