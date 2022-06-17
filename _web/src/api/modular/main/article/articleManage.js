import { axios } from '@/utils/request'

/**
 * 查询文章
 *
 * @author 程永磊
 * @date 2022-06-17 12:25:39
 */
export function articlePage (parameter) {
  return axios({
    url: '/article/page',
    method: 'get',
    params: parameter
  })
}

/**
 * 文章列表
 *
 * @author 程永磊
 * @date 2022-06-17 12:25:39
 */
export function articleList (parameter) {
  return axios({
    url: '/article/list',
    method: 'get',
    params: parameter
  })
}

/**
 * 添加文章
 *
 * @author 程永磊
 * @date 2022-06-17 12:25:39
 */
export function articleAdd (parameter) {
  return axios({
    url: '/article/add',
    method: 'post',
    data: parameter
  })
}

/**
 * 编辑文章
 *
 * @author 程永磊
 * @date 2022-06-17 12:25:39
 */
export function articleEdit (parameter) {
  return axios({
    url: '/article/edit',
    method: 'post',
    data: parameter
  })
}

/**
 * 删除文章
 *
 * @author 程永磊
 * @date 2022-06-17 12:25:39
 */
export function articleDelete (parameter) {
  return axios({
    url: '/article/delete',
    method: 'post',
    data: parameter
  })
}

/**
 * 导出文章
 *
 * @author 程永磊
 * @date 2022-06-17 12:25:39
 */
export function articleExport (parameter) {
  return axios({
    url: '/article/export',
    method: 'get',
    params: parameter,
    responseType: 'blob'
  })
}
