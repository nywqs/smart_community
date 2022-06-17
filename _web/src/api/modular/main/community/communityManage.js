import { axios } from '@/utils/request'

/**
 * 查询社区管理
 *
 * @author 程永磊
 * @date 2022-06-17 18:54:52
 */
export function communityPage (parameter) {
  return axios({
    url: '/community/page',
    method: 'get',
    params: parameter
  })
}

/**
 * 社区管理列表
 *
 * @author 程永磊
 * @date 2022-06-17 18:54:52
 */
export function communityList (parameter) {
  return axios({
    url: '/community/list',
    method: 'get',
    params: parameter
  })
}

/**
 * 添加社区管理
 *
 * @author 程永磊
 * @date 2022-06-17 18:54:52
 */
export function communityAdd (parameter) {
  return axios({
    url: '/community/add',
    method: 'post',
    data: parameter
  })
}

/**
 * 编辑社区管理
 *
 * @author 程永磊
 * @date 2022-06-17 18:54:52
 */
export function communityEdit (parameter) {
  return axios({
    url: '/community/edit',
    method: 'post',
    data: parameter
  })
}

/**
 * 删除社区管理
 *
 * @author 程永磊
 * @date 2022-06-17 18:54:52
 */
export function communityDelete (parameter) {
  return axios({
    url: '/community/delete',
    method: 'post',
    data: parameter
  })
}

/**
 * 导出社区管理
 *
 * @author 程永磊
 * @date 2022-06-17 18:54:52
 */
export function communityExport (parameter) {
  return axios({
    url: '/community/export',
    method: 'get',
    params: parameter,
    responseType: 'blob'
  })
}
