import { axios } from '@/utils/request'

/**
 * 查询楼宇
 *
 * @author 程永磊
 * @date 2022-06-17 20:03:07
 */
export function buildingPage (parameter) {
  return axios({
    url: '/building/page',
    method: 'get',
    params: parameter
  })
}

/**
 * 楼宇列表
 *
 * @author 程永磊
 * @date 2022-06-17 20:03:07
 */
export function buildingList (parameter) {
  return axios({
    url: '/building/list',
    method: 'get',
    params: parameter
  })
}

/**
 * 添加楼宇
 *
 * @author 程永磊
 * @date 2022-06-17 20:03:07
 */
export function buildingAdd (parameter) {
  return axios({
    url: '/building/add',
    method: 'post',
    data: parameter
  })
}

/**
 * 编辑楼宇
 *
 * @author 程永磊
 * @date 2022-06-17 20:03:07
 */
export function buildingEdit (parameter) {
  return axios({
    url: '/building/edit',
    method: 'post',
    data: parameter
  })
}

/**
 * 删除楼宇
 *
 * @author 程永磊
 * @date 2022-06-17 20:03:07
 */
export function buildingDelete (parameter) {
  return axios({
    url: '/building/delete',
    method: 'post',
    data: parameter
  })
}

/**
 * 导出楼宇
 *
 * @author 程永磊
 * @date 2022-06-17 20:03:07
 */
export function buildingExport (parameter) {
  return axios({
    url: '/building/export',
    method: 'get',
    params: parameter,
    responseType: 'blob'
  })
}
