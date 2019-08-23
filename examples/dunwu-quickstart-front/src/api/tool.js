import request from '@/utils/request'

export function sendMail(data) {
  return request({
    url: '/mail/send',
    method: 'post',
    data
  })
}
