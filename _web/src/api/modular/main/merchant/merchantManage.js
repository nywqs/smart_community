import { axios } from '@/utils/request'

/**
 * 查询商户
 *
 * @author 程永磊
 * @date 2022-06-17 20:38:52
 */
export function merchantPage (parameter) {
  return axios({
    url: '/merchant/page',
    method: 'get',
    params: parameter
  })
}

/**
 * 商户列表
 *
 * @author 程永磊
 * @date 2022-06-17 20:38:52
 */
export function merchantList (parameter) {
  return axios({
    url: '/merchant/list',
    method: 'get',
    params: parameter
  })
}

/**
 * 添加商户
 *
 * @author 程永磊
 * @date 2022-06-17 20:38:52
 */
export function merchantAdd (parameter) {
  return axios({
    url: '/merchant/add',
    method: 'post',
    data: parameter
  })
}

/**
 * 编辑商户
 *
 * @author 程永磊
 * @date 2022-06-17 20:38:52
 */
export function merchantEdit (parameter) {
  return axios({
    url: '/merchant/edit',
    method: 'post',
    data: parameter
  })
}

/**
 * 删除商户
 *
 * @author 程永磊
 * @date 2022-06-17 20:38:52
 */
export function merchantDelete (parameter) {
  return axios({
    url: '/merchant/delete',
    method: 'post',
    data: parameter
  })
}

/**
 * 导出商户
 *
 * @author 程永磊
 * @date 2022-06-17 20:38:52
 */
export function merchantExport (parameter) {
  return axios({
    url: '/merchant/export',
    method: 'get',
    params: parameter,
    responseType: 'blob'
  })
}
