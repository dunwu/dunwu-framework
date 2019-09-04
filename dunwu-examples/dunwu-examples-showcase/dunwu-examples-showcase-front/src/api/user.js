import request from '@/utils/request'

export async function login(data) {
  return request({
    url: '/user/login',
    method: 'post',
    data
  })
}

export function getInfo(token) {
  return request({
    url: '/user/getInfo',
    method: 'get',
    params: { token }
  })
}

export async function logout() {
  return request({
    url: '/user/logout',
    method: 'post'
  })
}
