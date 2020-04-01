import request from '@/utils/http'

export function getUserPage(params) {
  return request({
    url: '/user/page',
    method: 'get',
    params
  })
}

export function getUserById(params) {
  return request({
    url: '/user/getById',
    method: 'get',
    params
  })
}

export function insertUser(data) {
  return request({
    url: '/user/insert',
    method: 'post',
    data
  })
}

export function updateUserById(data) {
  return request({
    url: '/user/updateById',
    method: 'post',
    data
  })
}

export function deleteUserById(data) {
  return request({
    url: '/user/deleteById',
    method: 'post',
    data
  })
}

export function isUserExists(params) {
  return request({
    url: 'user/exists',
    method: 'get',
    params
  })
}

export function userRegister(data) {
  return request({
    url: 'user/register',
    method: 'post',
    data
  })
}

export function userLogin(data) {
  return request({
    url: '/user/login',
    method: 'post',
    params: {
      rememberme: data.rememberme
    },
    data: {
      username: data.username.trim(),
      password: data.password,
      checkCode: data.checkCode
    }
    // headers: { 'content-type': 'application/x-www-form-urlencoded' }
  })
}

export function getUserInfo() {
  return request({
    url: '/user/info',
    method: 'get'
  })
}

export function userLogout() {
  return request({
    url: '/user/logout',
    method: 'post'
  })
}
