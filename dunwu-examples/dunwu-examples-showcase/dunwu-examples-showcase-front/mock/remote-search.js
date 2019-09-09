import Mock from 'mockjs'

const NameList = []
const count = 100

for (let i = 0; i < count; i++) {
  NameList.push(
    Mock.mock({
      name: '@first'
    })
  )
}
NameList.push({ name: 'mock-Pan' })

export default [
  // nickname search
  {
    url: '/search/user',
    type: 'get',
    response: config => {
      const { name } = config.query
      const mockNameList = NameList.filter(item => {
        const lowerCaseName = item.name.toLowerCase()
        return !(name && lowerCaseName.indexOf(name.toLowerCase()) < 0)
      })
      return {
        success: true,
        code: '0',
        message: 'success',
        data: { items: mockNameList }
      }
    }
  },

  // transaction list
  {
    url: '/transaction/list',
    type: 'get',
    response: _ => {
      return {
        success: true,
        code: '0',
        message: 'success',
        data: {
          total: 20,
          'items|20': [
            {
              order_no: '@guid()',
              timestamp: +Mock.Random.date('T'),
              nickname: '@name()',
              price: '@float(1000, 15000, 0, 2)',
              'status|1': ['success', 'pending']
            }
          ]
        }
      }
    }
  }
]
