import request from '@/utils/request'

/**
 * 添加一条记录
 * @param data
 * @returns {*}
 */
export function add(data) {
  return request({
    url: '<#if package.ModuleName??>${package.ModuleName}/</#if>${table.apiBaseUrl}/add',
    method: 'post',
    data
  })
}

/**
 * 根据 ID 集合批量删除
 * @param ids
 * @returns {*}
 */
export function delBatch(ids) {
  return request({
    url: '<#if package.ModuleName??>${package.ModuleName}/</#if>${table.apiBaseUrl}/del/batch',
    method: 'post',
    data: ids
  })
}

/**
 * 修改一条记录
 * @param data
 * @returns {*}
 */
export function edit(data) {
  return request({
    url: '<#if package.ModuleName??>${package.ModuleName}/</#if>${table.apiBaseUrl}/edit',
    method: 'post',
    data
  })
}

/**
 * 根据 params 条件，查询匹配条件的列表
 * @param params
 * @returns {*}
 */
export function list(params) {
  return request({
    url: '<#if package.ModuleName??>${package.ModuleName}/</#if>${table.apiBaseUrl}/list',
    method: 'get',
    params
  })
}

/**
 * 根据 params 条件，查询匹配条件的分页列表
 * @param params
 * @returns {*}
 */
export function page(params) {
  return request({
    url: '<#if package.ModuleName??>${package.ModuleName}/</#if>${table.apiBaseUrl}/page',
    method: 'get',
    params
  })
}

/**
 * 根据 ID 查询记录
 * @param id
 * @returns {*}
 */
export function getById(id) {
  return request({
    url: '<#if package.ModuleName??>${package.ModuleName}/</#if>${table.apiBaseUrl}/' + id,
    method: 'get'
  })
}

/**
 * 根据指定 id 列表，导出相应数据
 * @param ids
 * @returns {*}
 */
export function exportList(ids) {
  return request({
    url: '<#if package.ModuleName??>${package.ModuleName}/</#if>${table.apiBaseUrl}/export/list',
    method: 'post',
    responseType: 'blob',
    data: ids
  })
}

/**
 * 根据 params 条件，导出符合查询条件的分页数据
 * @param params
 * @returns {*}
 */
export function exportPage(params) {
  return request({
    url: '<#if package.ModuleName??>${package.ModuleName}/</#if>${table.apiBaseUrl}/export/page',
    method: 'get',
    responseType: 'blob',
    params
  })
}

export default { add, edit, delBatch, list, page, getById, exportList, exportPage }
