/**
 * @file 封装支持 promise 的 http 请求工具
 * @author Zhang Peng
 * @see https://github.com/mzabriskie/axios
 * @see http://www.jianshu.com/p/df464b26ae58
 */
import axios from 'axios'
import router from '@/router'
import store from '@/store'
import { Message, MessageBox, Notification } from 'element-ui'
import { getToken } from '@/utils/auth'
import settings from '@/settings'

axios.defaults.timeout = settings.HTTP_TIMEOUT // timeout
axios.defaults.baseURL = process.env.VUE_APP_BASE_API // url = base url + request url
axios.defaults.withCredentials = true
// Content-Type 一般无需设置
// @see https://juejin.im/post/5d64f919f265da0390053da4
// axios.defaults.headers['Content-Type'] = 'application/json'

// request interceptor
axios.interceptors.request.use(
  config => {
    // do something before request is sent
    console.group('%c%s', 'color:blue', '[Http Request]')
    console.debug('[request info]', config)
    if (store.getters.token) {
      // let each request carry token
      // ['Authorization'] is a custom headers token
      // please modify it according to the actual situation
      config.headers['Authorization'] = getToken()
    }
    return config
  },
  error => {
    // do something with request error
    console.error(error) // for debug
    Promise.reject(error)
  }
)

// response interceptor
axios.interceptors.response.use(
  config => {
    console.debug('[response info]', config)
    const res = config.data
    console.groupEnd()
    // if the custom code is not 20000, it is judged as an error.
    if (res.code === 0) {
      return res
    } else {
      Message({
        message: res.message || 'Error',
        showClose: true,
        type: 'error',
        duration: 5 * 1000
      })

      // 50008: Illegal token; 50012: Other clients logged in; 50014: Token expired;
      if (res.code === 400 || res.code === 401 || res.code === 403) {
        // to re-login
        MessageBox.confirm(
          'You have been logged out, you can cancel to stay on this page, or log in again',
          'Confirm logout',
          {
            confirmButtonText: 'Re-Login',
            cancelButtonText: 'Cancel',
            type: 'warning'
          }
        ).then(() => {
          store.dispatch('user/resetToken').then(() => {
            location.reload()
          })
        })
      }
      return Promise.reject(new Error(res.message || 'Error'))
    }
  },
  error => {
    let code = 0
    console.log('error', error)
    try {
      code = error.response.data.code
    } catch (e) {
      if (e.toString().indexOf('Error: timeout') !== -1 || e.toString().indexOf('Error: Network Error')) {
        Notification.error({ title: '网络请求超时', duration: 5000 })
        return Promise.reject(e)
      }
    }
    if (code) {
      if (code === 401) {
        MessageBox.confirm('登录状态已过期，您可以继续留在该页面，或者重新登录', '系统提示', {
          confirmButtonText: '重新登录',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          store.dispatch('LogOut').then(() => {
            location.reload() // 为了重新实例化vue-router对象 避免bug
          })
        })
      } else if (code === 403) {
        router.push({ path: '/401' })
      } else {
        const errorMsg = error.response.data.message
        if (errorMsg !== undefined) {
          Notification.error({ title: errorMsg, duration: 5000 })
        }
      }
    } else {
      Notification.error({ title: '接口请求失败', duration: 5000 })
    }
    return Promise.reject(error)
  }
)

export default axios
