import { axios } from '@/utils/request'

/**
 * 查询答复记录
 *
 * @author jetox
 * @date 2022-06-17 22:29:45
 */
export function feedbackReplyPage (parameter) {
  return axios({
    url: '/feedbackReply/page',
    method: 'get',
    params: parameter
  })
}

/**
 * 答复记录列表
 *
 * @author jetox
 * @date 2022-06-17 22:29:45
 */
export function feedbackReplyList (parameter) {
  return axios({
    url: '/feedbackReply/list',
    method: 'get',
    params: parameter
  })
}

/**
 * 添加答复记录
 *
 * @author jetox
 * @date 2022-06-17 22:29:45
 */
export function feedbackReplyAdd (parameter) {
  return axios({
    url: '/feedbackReply/add',
    method: 'post',
    data: parameter
  })
}

/**
 * 编辑答复记录
 *
 * @author jetox
 * @date 2022-06-17 22:29:45
 */
export function feedbackReplyEdit (parameter) {
  return axios({
    url: '/feedbackReply/edit',
    method: 'post',
    data: parameter
  })
}

/**
 * 删除答复记录
 *
 * @author jetox
 * @date 2022-06-17 22:29:45
 */
export function feedbackReplyDelete (parameter) {
  return axios({
    url: '/feedbackReply/delete',
    method: 'post',
    data: parameter
  })
}

/**
 * 导出答复记录
 *
 * @author jetox
 * @date 2022-06-17 22:29:45
 */
export function feedbackReplyExport (parameter) {
  return axios({
    url: '/feedbackReply/export',
    method: 'get',
    params: parameter,
    responseType: 'blob'
  })
}
