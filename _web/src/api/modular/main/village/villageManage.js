import { axios } from '@/utils/request'

/**
 * 查询小区
 *
 * @author 程永磊
 * @date 2022-06-17 19:56:45
 */
export function villagePage (parameter) {
  return axios({
    url: '/village/page',
    method: 'get',
    params: parameter
  })
}

/**
 * 小区列表
 *
 * @author 程永磊
 * @date 2022-06-17 19:56:45
 */
export function villageList (parameter) {
  return axios({
    url: '/village/list',
    method: 'get',
    params: parameter
  })
}

/**
 * 添加小区
 *
 * @author 程永磊
 * @date 2022-06-17 19:56:45
 */
export function villageAdd (parameter) {
  return axios({
    url: '/village/add',
    method: 'post',
    data: parameter
  })
}

/**
 * 编辑小区
 *
 * @author 程永磊
 * @date 2022-06-17 19:56:45
 */
export function villageEdit (parameter) {
  return axios({
    url: '/village/edit',
    method: 'post',
    data: parameter
  })
}

/**
 * 删除小区
 *
 * @author 程永磊
 * @date 2022-06-17 19:56:45
 */
export function villageDelete (parameter) {
  return axios({
    url: '/village/delete',
    method: 'post',
    data: parameter
  })
}

/**
 * 导出小区
 *
 * @author 程永磊
 * @date 2022-06-17 19:56:45
 */
export function villageExport (parameter) {
  return axios({
    url: '/village/export',
    method: 'get',
    params: parameter,
    responseType: 'blob'
  })
}
