import { axios } from '@/utils/request'

/**
 * 查询活动总结
 *
 * @author jetox
 * @date 2022-06-17 22:55:11
 */
export function activitySummaryPage (parameter) {
  return axios({
    url: '/activitySummary/page',
    method: 'get',
    params: parameter
  })
}

/**
 * 活动总结列表
 *
 * @author jetox
 * @date 2022-06-17 22:55:11
 */
export function activitySummaryList (parameter) {
  return axios({
    url: '/activitySummary/list',
    method: 'get',
    params: parameter
  })
}

/**
 * 添加活动总结
 *
 * @author jetox
 * @date 2022-06-17 22:55:11
 */
export function activitySummaryAdd (parameter) {
  return axios({
    url: '/activitySummary/add',
    method: 'post',
    data: parameter
  })
}

/**
 * 编辑活动总结
 *
 * @author jetox
 * @date 2022-06-17 22:55:11
 */
export function activitySummaryEdit (parameter) {
  return axios({
    url: '/activitySummary/edit',
    method: 'post',
    data: parameter
  })
}

/**
 * 删除活动总结
 *
 * @author jetox
 * @date 2022-06-17 22:55:11
 */
export function activitySummaryDelete (parameter) {
  return axios({
    url: '/activitySummary/delete',
    method: 'post',
    data: parameter
  })
}

/**
 * 导出活动总结
 *
 * @author jetox
 * @date 2022-06-17 22:55:11
 */
export function activitySummaryExport (parameter) {
  return axios({
    url: '/activitySummary/export',
    method: 'get',
    params: parameter,
    responseType: 'blob'
  })
}
