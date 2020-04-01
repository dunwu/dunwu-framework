import request from '@/utils/http'

export function getPermissionPage(params) {
  return request({
    url: '/permission/page', method: 'get', params
  })
}

export function getPermissionById(params) {
  return request({
    url: '/permission/getById', method: 'get', params
  })
}

export function insertPermission(data) {
  return request({
    url: '/permission/insert', method: 'post', data
  })
}

export function updatePermissionById(data) {
  return request({
    url: '/permission/updateById', method: 'post', data
  })
}

export function deletePermissionById(data) {
  return request({
    url: '/permission/deleteById', method: 'post', data
  })
}
