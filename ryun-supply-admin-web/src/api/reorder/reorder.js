import request from '@/utils/request'

export function getList(query, data) {
  return request({
    url: '/admin/order/list?pageCode=' + query.page + '&pageSize=' + query.limit,
    method: 'post',
    data
  })
}
export function findById(orderId) {
  return request({
    url: '/admin/order/' + orderId,
    method: 'get'
  })
}
