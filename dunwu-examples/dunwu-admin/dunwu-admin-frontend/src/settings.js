module.exports = {
  title: 'DUNWU-ADMIN',
  /**
   * @description Whether show the settings right-panel
   */
  showSettings: true,
  /**
   * @description Whether need tagsView
   */
  tagsView: true,
  /**
   * @description Whether fix the header
   */
  fixedHeader: false,
  /**
   * @description Whether show the logo in sidebar
   */
  sidebarLogo: true,
  /**
   * @description Whether show the logo in sidebar
   */
  HTTP_TIMEOUT: 5000,
  /**
   * @description Token
   */
  TOKEN_KEY: 'dunwu_admin_token',
  /**
   * @description Token expire time
   */
  TOKEN_EXPIRE_TIME: 24,
  /**
   * @description Need show err logs component.
   * The default is only used in the production env
   * If you want to also use it in dev, you can pass ['production', 'development']
   */
  errorLog: 'production'
}
