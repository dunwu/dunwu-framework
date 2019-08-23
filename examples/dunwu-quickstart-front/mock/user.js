const logininfo = {
  admin: {
    password: '123456',
    token: 'admin-token'
  },
  user: {
    password: '123456',
    token: 'user-token'
  }
}

const users = {
  'admin-token': {
    roles: ['admin'],
    introduction: 'I am a super administrator',
    avatar:
      'http://dunwu.test.upcdn.net/images/others/zp.png',
    name: 'Super Admin'
  },
  'user-token': {
    roles: ['user'],
    introduction: 'I am an user',
    avatar:
      'http://dunwu.test.upcdn.net/images/others/zp.png',
    name: 'Normal User'
  }
}

export default [
  // user login
  {
    url: '/user/login',
    type: 'post',
    response: config => {
      const { nickname, password } = config.body
      const info = logininfo[nickname]

      if (!info) {
        return {
          success: false,
          code: 60204,
          message: 'Account is not exists.',
          data: {}
        }
      }

      if (info.password !== password) {
        return {
          success: false,
          code: 60203,
          message: 'password is incorrect.',
          data: {}
        }
      }

      // mock error
      if (!info.token) {
        return {
          success: false,
          code: 60204,
          message: 'Account and password are incorrect.',
          data: {}
        }
      }

      return {
        success: true,
        code: '0',
        msg: 'success',
        data: {
          token: info.token
        }
      }
    }
  },

  // get user info
  {
    url: '/user/getInfo.*',
    type: 'get',
    response: config => {
      const { token } = config.query
      const info = users[token]

      // mock error
      if (!info) {
        return {
          success: false,
          code: 50008,
          message: 'Login failed, unable to get user details.',
          data: {}
        }
      }

      return {
        success: true,
        code: '0',
        msg: 'success',
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
        msg: 'success',
        data: {}
      }
    }
  }
]
