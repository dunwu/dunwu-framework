import { validNickname, isExternal } from '@/utils/validate.js'

describe('Utils:validate', () => {
  it('validNickname', () => {
    expect(validNickname('admin')).toBe(true)
    expect(validNickname('user')).toBe(true)
    expect(validNickname('xxxx')).toBe(false)
  })
  it('isExternal', () => {
    expect(isExternal('https://github.com/dunwu')).toBe(true)
    expect(isExternal('http://github.com/dunwu')).toBe(true)
    expect(isExternal('github.com/dunwu')).toBe(false)
    expect(isExternal('/dashboard')).toBe(false)
    expect(isExternal('./dashboard')).toBe(false)
    expect(isExternal('dashboard')).toBe(false)
  })
})
