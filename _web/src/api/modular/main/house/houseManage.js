import { axios } from '@/utils/request'

/**
 * 查询房屋
 *
 * @author 程永磊
 * @date 2022-06-17 20:21:07
 */
export function housePage (parameter) {
  return axios({
    url: '/house/page',
    method: 'get',
    params: parameter
  })
}

/**
 * 房屋列表
 *
 * @author 程永磊
 * @date 2022-06-17 20:21:07
 */
export function houseList (parameter) {
  return axios({
    url: '/house/list',
    method: 'get',
    params: parameter
  })
}

/**
 * 添加房屋
 *
 * @author 程永磊
 * @date 2022-06-17 20:21:07
 */
export function houseAdd (parameter) {
  return axios({
    url: '/house/add',
    method: 'post',
    data: parameter
  })
}

/**
 * 编辑房屋
 *
 * @author 程永磊
 * @date 2022-06-17 20:21:07
 */
export function houseEdit (parameter) {
  return axios({
    url: '/house/edit',
    method: 'post',
    data: parameter
  })
}

/**
 * 删除房屋
 *
 * @author 程永磊
 * @date 2022-06-17 20:21:07
 */
export function houseDelete (parameter) {
  return axios({
    url: '/house/delete',
    method: 'post',
    data: parameter
  })
}

/**
 * 导出房屋
 *
 * @author 程永磊
 * @date 2022-06-17 20:21:07
 */
export function houseExport (parameter) {
  return axios({
    url: '/house/export',
    method: 'get',
    params: parameter,
    responseType: 'blob'
  })
}
