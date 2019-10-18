const tokens = {
  admin: {
    token: 'admin-token'
  },
  user: {
    token: 'user-token'
  }
}

const users = {
  'admin-token': {
    roles: ['admin'],
    introduction: 'I am a super administrator',
    avatar: 'http://dunwu.test.upcdn.net/common/logo/zp.png',
    name: 'Super Admin'
  },
  'user-token': {
    roles: ['user'],
    introduction: 'I am an user',
    avatar: 'http://dunwu.test.upcdn.net/common/logo/zp.png',
    name: 'Normal User'
  }
}

export default [
  // user login
  {
    url: '/user/login',
    type: 'post',
    response: config => {
      const { username } = config.body
      const token = tokens[username]

      // mock error
      if (!token) {
        return {
          success: false,
          code: '60204',
          message: 'Account and password are incorrect.'
        }
      }

      return {
        success: true,
        code: '0',
        message: 'success',
        data: token
      }
    }
  },

  // get user info
  {
    url: '/user/getInfo',
    type: 'get',
    response: config => {
      const { token } = config.query
      const info = users[token]

      // mock error
      if (!info) {
        return {
          success: false,
          code: '50008',
          message: 'Login failed, unable to get user details.'
        }
      }

      return {
        success: true,
        code: '0',
        message: 'success',
        data: info
      }
    }
  },

  // user logout
  {
    url: '/user/logout',
    type: 'post',
    response: _ => {
      return {
        success: true,
        code: '0',
        message: 'success',
        data: 'success'
      }
    }
  }
]
