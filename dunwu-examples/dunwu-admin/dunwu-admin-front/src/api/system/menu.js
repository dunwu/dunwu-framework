import request from '@/utils/http'

export function getMenuPage(params) {
  return request({
    url: '/menu/page', method: 'get', params
  })
}

export function treeList(params) {
  return request({
    url: '/menu/treeList', method: 'get', params
  })
}

export function getMenuById(params) {
  return request({
    url: '/menu/getById', method: 'get', params
  })
}

export function checkMenu(data) {
  return request({
    url: '/menu/check', method: 'post', data
  })
}

export function insertMenu(data) {
  return request({
    url: '/menu/insert', method: 'post', data
  })
}

export function updateMenuById(data) {
  return request({
    url: '/menu/updateById', method: 'post', data
  })
}

export function deleteMenuById(data) {
  return request({
    url: '/menu/deleteById', method: 'post', data
  })
}

export function deleteBatchIds(data) {
  return request({
    url: '/menu/deleteBatchIds', method: 'post', data
  })
}

export function relatedDeleteById(data) {
  return request({
    url: '/menu/relatedDeleteById', method: 'post', data
  })
}
