const componentsRouter = {
  path: '/components',
  component: () => import('@/views/demos/components-demo/index'),
  redirect: 'noRedirect',
  name: 'ComponentDemo',
  meta: {
    title: 'Components', icon: 'component'
  },
  children: [{
    path: 'tinymce',
    component: () => import('@/views/demos/components-demo/tinymce'),
    name: 'TinymceDemo',
    meta: { title: 'Tinymce' }
  }, {
    path: 'markdown',
    component: () => import('@/views/demos/components-demo/markdown'),
    name: 'MarkdownDemo',
    meta: { title: 'Markdown' }
  }, {
    path: 'json-editor',
    component: () => import('@/views/demos/components-demo/json-editor'),
    name: 'JsonEditorDemo',
    meta: { title: 'JSON Editor' }
  }, {
    path: 'split-pane',
    component: () => import('@/views/demos/components-demo/split-pane'),
    name: 'SplitpaneDemo',
    meta: { title: 'SplitPane' }
  }, {
    path: 'avatar-upload',
    component: () => import('@/views/demos/components-demo/avatar-upload'),
    name: 'AvatarUploadDemo',
    meta: { title: 'Upload' }
  }, {
    path: 'dropzone',
    component: () => import('@/views/demos/components-demo/dropzone'),
    name: 'DropzoneDemo',
    meta: { title: 'Dropzone' }
  }, {
    path: 'sticky',
    component: () => import('@/views/demos/components-demo/sticky'),
    name: 'StickyDemo',
    meta: { title: 'Sticky' }
  }, {
    path: 'count-to',
    component: () => import('@/views/demos/components-demo/count-to'),
    name: 'CountToDemo',
    meta: { title: 'Count To' }
  }, {
    path: 'mixin',
    component: () => import('@/views/demos/components-demo/mixin'),
    name: 'ComponentMixinDemo',
    meta: { title: 'Component Mixin' }
  }, {
    path: 'back-to-top',
    component: () => import('@/views/demos/components-demo/back-to-top'),
    name: 'BackToTopDemo',
    meta: { title: 'Back To Top' }
  }, {
    path: 'drag-dialog',
    component: () => import('@/views/demos/components-demo/drag-dialog'),
    name: 'DragDialogDemo',
    meta: { title: 'Drag Dialog' }
  }, {
    path: 'drag-select',
    component: () => import('@/views/demos/components-demo/drag-select'),
    name: 'DragSelectDemo',
    meta: { title: 'Drag Select' }
  }, {
    path: 'dnd-list',
    component: () => import('@/views/demos/components-demo/dnd-list'),
    name: 'DndListDemo',
    meta: { title: 'Dnd List' }
  }, {
    path: 'drag-kanban',
    component: () => import('@/views/demos/components-demo/drag-kanban'),
    name: 'DragKanbanDemo',
    meta: { title: 'Drag Kanban' }
  }]
}

export default componentsRouter
