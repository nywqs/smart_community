import { axios } from '@/utils/request'

/**
 * 查询活动
 *
 * @author jetox
 * @date 2022-06-17 22:43:04
 */
export function activityPage (parameter) {
  return axios({
    url: '/activity/page',
    method: 'get',
    params: parameter
  })
}

/**
 * 活动列表
 *
 * @author jetox
 * @date 2022-06-17 22:43:04
 */
export function activityList (parameter) {
  return axios({
    url: '/activity/list',
    method: 'get',
    params: parameter
  })
}

/**
 * 添加活动
 *
 * @author jetox
 * @date 2022-06-17 22:43:04
 */
export function activityAdd (parameter) {
  return axios({
    url: '/activity/add',
    method: 'post',
    data: parameter
  })
}

/**
 * 编辑活动
 *
 * @author jetox
 * @date 2022-06-17 22:43:04
 */
export function activityEdit (parameter) {
  return axios({
    url: '/activity/edit',
    method: 'post',
    data: parameter
  })
}

/**
 * 删除活动
 *
 * @author jetox
 * @date 2022-06-17 22:43:04
 */
export function activityDelete (parameter) {
  return axios({
    url: '/activity/delete',
    method: 'post',
    data: parameter
  })
}

/**
 * 导出活动
 *
 * @author jetox
 * @date 2022-06-17 22:43:04
 */
export function activityExport (parameter) {
  return axios({
    url: '/activity/export',
    method: 'get',
    params: parameter,
    responseType: 'blob'
  })
}
