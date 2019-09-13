import request from '@/utils/request'

export function getList(query, data) {
  return request({
    url: '/saas/order/list?pageCode=' + query.page + '&pageSize=' + query.limit,
    method: 'post',
    data
  })
}
export function findById(orderId) {
  return request({
    url: '/saas/order/findById/' + orderId,
    method: 'get'
  })
}
export function del(orderId) {
  return request({
    url: '/saas/order/' + orderId,
    method: 'delete'
  })
}

export function edit(data) {
  return request({
    url: '/saas/order/edit',
    method: 'put',
    data
  })
}

export function loan(orderId, amt) {
  return request({
    url: '/saas/order/loan?orderid=' + orderId + '&orderFactPayAmount=' + amt,
    method: 'get'
  })
}

export function refuse(orderId) {
  return request({
    url: '/saas/order/refuse?orderid=' + orderId,
    method: 'get'
  })
}
