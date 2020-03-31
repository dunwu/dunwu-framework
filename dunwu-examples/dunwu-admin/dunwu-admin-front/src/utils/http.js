/**
 * @file 封装支持 promise 的 http 请求工具
 * @author Zhang Peng
 * @see https://github.com/mzabriskie/axios
 * @see http://www.jianshu.com/p/df464b26ae58
 */
import axios from 'axios'
import store from '@/store'
import { Message, MessageBox } from 'element-ui'
import { getToken } from '@/utils/auth'

axios.defaults.timeout = 5000 // timeout
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
      // ['Authorization'] is a custom headers key
      // please modify it according to the actual situation
      config.headers['X-Token'] = getToken()
      console.log('token', getToken())
    }
    return config
  },
  error => {
    // do something with request error
    console.error(error) // for debug
    return Promise.reject(error)
  }
)

// response interceptor
axios.interceptors.response.use(
  /**
   * If you want to get http information such as headers or status
   * Please return  response => response
   */

  /**
   * Determine the request status by custom code
   * Here is just an example
   * You can also judge the status by HTTP Status Code
   */
  response => {
    const res = response.data
    console.debug('[response info]', response.data)
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
    console.log('err' + error) // for debug
    console.groupEnd()
    Message({
      message: error.message,
      type: 'error',
      duration: 5 * 1000
    })
    return Promise.reject(error)
  }
)

export default axios
