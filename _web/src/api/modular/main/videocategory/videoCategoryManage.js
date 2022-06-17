import { axios } from '@/utils/request'

/**
 * 查询视频分类
 *
 * @author jetox
 * @date 2022-06-17 22:13:03
 */
export function videoCategoryPage (parameter) {
  return axios({
    url: '/videoCategory/page',
    method: 'get',
    params: parameter
  })
}

/**
 * 视频分类列表
 *
 * @author jetox
 * @date 2022-06-17 22:13:03
 */
export function videoCategoryList (parameter) {
  return axios({
    url: '/videoCategory/list',
    method: 'get',
    params: parameter
  })
}

/**
 * 添加视频分类
 *
 * @author jetox
 * @date 2022-06-17 22:13:03
 */
export function videoCategoryAdd (parameter) {
  return axios({
    url: '/videoCategory/add',
    method: 'post',
    data: parameter
  })
}

/**
 * 编辑视频分类
 *
 * @author jetox
 * @date 2022-06-17 22:13:03
 */
export function videoCategoryEdit (parameter) {
  return axios({
    url: '/videoCategory/edit',
    method: 'post',
    data: parameter
  })
}

/**
 * 删除视频分类
 *
 * @author jetox
 * @date 2022-06-17 22:13:03
 */
export function videoCategoryDelete (parameter) {
  return axios({
    url: '/videoCategory/delete',
    method: 'post',
    data: parameter
  })
}

/**
 * 导出视频分类
 *
 * @author jetox
 * @date 2022-06-17 22:13:03
 */
export function videoCategoryExport (parameter) {
  return axios({
    url: '/videoCategory/export',
    method: 'get',
    params: parameter,
    responseType: 'blob'
  })
}
