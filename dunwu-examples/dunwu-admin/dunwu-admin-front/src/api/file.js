import request from '@/utils/http'

export async function uploadFile(data) {
  return request({
    url: '/file/upload', method: 'post', data
  })
}

export function getFilePage(params) {
  return request({
    url: '/file/page', method: 'get', params
  })
}

export function deleteFile(data) {
  return request({
    url: '/file/delete', method: 'post', data
  })
}
