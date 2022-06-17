import { axios } from '@/utils/request'

/**
 * 查询报名记录
 *
 * @author jetox
 * @date 2022-06-17 22:52:54
 */
export function activityUsersPage (parameter) {
  return axios({
    url: '/activityUsers/page',
    method: 'get',
    params: parameter
  })
}

/**
 * 报名记录列表
 *
 * @author jetox
 * @date 2022-06-17 22:52:54
 */
export function activityUsersList (parameter) {
  return axios({
    url: '/activityUsers/list',
    method: 'get',
    params: parameter
  })
}

/**
 * 添加报名记录
 *
 * @author jetox
 * @date 2022-06-17 22:52:54
 */
export function activityUsersAdd (parameter) {
  return axios({
    url: '/activityUsers/add',
    method: 'post',
    data: parameter
  })
}

/**
 * 编辑报名记录
 *
 * @author jetox
 * @date 2022-06-17 22:52:54
 */
export function activityUsersEdit (parameter) {
  return axios({
    url: '/activityUsers/edit',
    method: 'post',
    data: parameter
  })
}

/**
 * 删除报名记录
 *
 * @author jetox
 * @date 2022-06-17 22:52:54
 */
export function activityUsersDelete (parameter) {
  return axios({
    url: '/activityUsers/delete',
    method: 'post',
    data: parameter
  })
}

/**
 * 导出报名记录
 *
 * @author jetox
 * @date 2022-06-17 22:52:54
 */
export function activityUsersExport (parameter) {
  return axios({
    url: '/activityUsers/export',
    method: 'get',
    params: parameter,
    responseType: 'blob'
  })
}
