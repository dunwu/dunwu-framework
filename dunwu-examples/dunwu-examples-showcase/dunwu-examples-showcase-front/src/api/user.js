import request from '@/utils/request'

export function isUserExists(params) {
  return request({
    url: 'user/isUserExists',
    method: 'get',
    params
  })
}

export function register(data) {
  return request({
    url: 'user/register',
    method: 'post',
    data
  })
}

export function login(data) {
  return request({
    url: '/user/login',
    method: 'post',
    data
  })
}

export function getInfo() {
  return request({
    url: '/user/getInfo',
    method: 'get'
  })
}

export function logout() {
  return request({
    url: '/user/logout',
    method: 'post'
  })
}
