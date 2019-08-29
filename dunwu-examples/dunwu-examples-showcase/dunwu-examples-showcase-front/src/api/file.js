import request from '@/utils/request'

export async function uploadFile(data) {
  return request({
    url: '/file/upload',
    method: 'post',
    data
  })
}

export function getFilePage(params) {
  return request({
    url: '/file/page',
    method: 'get',
    params
  })
}

export function removeFile(data) {
  return request({
    url: '/file/remove',
    method: 'post',
    data
  })
}
