import defaultSettings from '@/settings'

const title = defaultSettings.title || 'dunwu-quickstart-front'

export default function getPageTitle(pageTitle) {
  if (pageTitle) {
    return `${pageTitle} - ${title}`
  }
  return `${title}`
}
