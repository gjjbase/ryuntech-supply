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
    url: '/admin/order/findById/' + orderId,
    method: 'get'
  })
}
export function del(orderId) {
  return request({
    url: '/admin/order/' + orderId,
    method: 'delete'
  })
}

export function edit(data) {
  return request({
    url: '/admin/order/edit',
    method: 'put',
    data
  })
}

export function loan(orderId) {
  return request({
    url: '/admin/order/loan?orderid='+orderId,
    method: 'get'
  })
}

export function refuse(orderId) {
  return request({
    url: '/admin/order/refuse?orderid='+orderId,
    method: 'get'
  })
}
