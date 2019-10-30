import request from '@/utils/request'

export function sendMail(data) {
  return request({
    url: '/mail/send', method: 'post', data
  })
}

export function createTemplate(data) {
  return request({
    url: '/template/save', method: 'post', data
  })
}

export function updateTemplate(data) {
  return request({
    url: '/template/updateById', method: 'post', data
  })
}

export function getTemplatePage(params) {
  return request({
    url: '/template/page', method: 'get', params
  })
}

export function deleteTemplate(params) {
  return request({
    url: `/template/removeById`, method: 'post', params
  })
}

export function getTemplateById(params) {
  return request({
    url: '/template/getById', method: 'get', params
  })
}

export function generateUuid(params) {
  return request({
    url: '/id/generateUuid', method: 'get', params
  })
}

export function generateSnowFlakeId(params) {
  return request({
    url: '/id/generateSnowFlakeId', method: 'get', params
  })
}
