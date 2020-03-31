import request from '@/utils/http'

export function getById(params) {
  return request({
    url: '/scheduler/getById', method: 'get', params
  })
}

export function getSchedulerPage(params) {
  return request({
    url: '/scheduler/page', method: 'get', params
  })
}

export function getJobBeans(params) {
  return request({
    url: '/scheduler/getJobBeans', method: 'get', params
  })
}

export function createJob(data) {
  return request({
    url: '/scheduler/createJob', method: 'post', data
  })
}

export function updateJob(data) {
  return request({
    url: '/scheduler/updateJob', method: 'post', data
  })
}

export function deleteJob(data) {
  return request({
    url: '/scheduler/deleteJob', method: 'post', data
  })
}

export function pauseJob(data) {
  return request({
    url: '/scheduler/pauseJob', method: 'post', data
  })
}

export function resumeJob(data) {
  return request({
    url: '/scheduler/resumeJob', method: 'post', data
  })
}

export function executeJob(data) {
  return request({
    url: '/scheduler/executeJob', method: 'post', data
  })
}
