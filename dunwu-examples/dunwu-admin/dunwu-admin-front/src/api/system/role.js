import request from '@/utils/request'

export function getRolePage(params) {
  return request({
    url: '/role/page', method: 'get', params
  })
}

export function getRoleById(params) {
  return request({
    url: '/role/getById', method: 'get', params
  })
}

export function insertRole(data) {
  return request({
    url: '/role/insert', method: 'post', data
  })
}

export function updateRoleById(data) {
  return request({
    url: '/role/updateById', method: 'post', data
  })
}

export function deleteRoleById(data) {
  return request({
    url: '/role/deleteById', method: 'post', data
  })
}
