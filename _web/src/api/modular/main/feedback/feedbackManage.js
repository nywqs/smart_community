import { axios } from '@/utils/request'

/**
 * 查询建议反馈
 *
 * @author jetox
 * @date 2022-06-17 22:24:58
 */
export function feedbackPage (parameter) {
  return axios({
    url: '/feedback/page',
    method: 'get',
    params: parameter
  })
}

/**
 * 建议反馈列表
 *
 * @author jetox
 * @date 2022-06-17 22:24:58
 */
export function feedbackList (parameter) {
  return axios({
    url: '/feedback/list',
    method: 'get',
    params: parameter
  })
}

/**
 * 添加建议反馈
 *
 * @author jetox
 * @date 2022-06-17 22:24:58
 */
export function feedbackAdd (parameter) {
  return axios({
    url: '/feedback/add',
    method: 'post',
    data: parameter
  })
}

/**
 * 编辑建议反馈
 *
 * @author jetox
 * @date 2022-06-17 22:24:58
 */
export function feedbackEdit (parameter) {
  return axios({
    url: '/feedback/edit',
    method: 'post',
    data: parameter
  })
}

/**
 * 删除建议反馈
 *
 * @author jetox
 * @date 2022-06-17 22:24:58
 */
export function feedbackDelete (parameter) {
  return axios({
    url: '/feedback/delete',
    method: 'post',
    data: parameter
  })
}

/**
 * 导出建议反馈
 *
 * @author jetox
 * @date 2022-06-17 22:24:58
 */
export function feedbackExport (parameter) {
  return axios({
    url: '/feedback/export',
    method: 'get',
    params: parameter,
    responseType: 'blob'
  })
}
