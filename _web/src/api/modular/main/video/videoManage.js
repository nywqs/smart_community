import { axios } from '@/utils/request'

/**
 * 查询视频列表
 *
 * @author jetox
 * @date 2022-06-17 22:07:37
 */
export function videoPage (parameter) {
  return axios({
    url: '/video/page',
    method: 'get',
    params: parameter
  })
}

/**
 * 视频列表列表
 *
 * @author jetox
 * @date 2022-06-17 22:07:37
 */
export function videoList (parameter) {
  return axios({
    url: '/video/list',
    method: 'get',
    params: parameter
  })
}

/**
 * 添加视频列表
 *
 * @author jetox
 * @date 2022-06-17 22:07:37
 */
export function videoAdd (parameter) {
  return axios({
    url: '/video/add',
    method: 'post',
    data: parameter
  })
}

/**
 * 编辑视频列表
 *
 * @author jetox
 * @date 2022-06-17 22:07:37
 */
export function videoEdit (parameter) {
  return axios({
    url: '/video/edit',
    method: 'post',
    data: parameter
  })
}

/**
 * 删除视频列表
 *
 * @author jetox
 * @date 2022-06-17 22:07:37
 */
export function videoDelete (parameter) {
  return axios({
    url: '/video/delete',
    method: 'post',
    data: parameter
  })
}

/**
 * 导出视频列表
 *
 * @author jetox
 * @date 2022-06-17 22:07:37
 */
export function videoExport (parameter) {
  return axios({
    url: '/video/export',
    method: 'get',
    params: parameter,
    responseType: 'blob'
  })
}
