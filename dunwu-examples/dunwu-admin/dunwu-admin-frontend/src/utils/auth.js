import Cookies from 'js-cookie'
import settings from '@/settings'

const TOKEN_KEY = settings.TOKEN_KEY

export function getToken() {
  return Cookies.get(TOKEN_KEY)
}

export function setToken(token, rememberMe) {
  if (rememberMe) {
    return Cookies.set(TOKEN_KEY, token, { expires: settings.TOKEN_EXPIRE_TIME })
  } else return Cookies.set(TOKEN_KEY, token)
}

export function removeToken() {
  return Cookies.remove(TOKEN_KEY)
}
