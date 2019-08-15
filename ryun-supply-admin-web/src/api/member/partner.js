import request from '@/utils/request'

export function getList(query, data) {
  return request({
    url: '/admin/partner/list?pageCode=' + query.page + '&pageSize=' + query.limit,
    method: 'post',
    data
  })
}
export function findById(userId) {
  return request({
    url: '/admin/partner/' + userId,
    method: 'get'
  })
}
