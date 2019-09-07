import request from '@/utils/request'

export function getList(query, data) {
  return request({
    url: '/admin/paymentResult/list?pageCode=' + query.page + '&pageSize=' + query.limit,
    method: 'post',
    data
  })
}
export function findById(orderId) {
  return request({
    url: '/admin/paymentResult/findById/' + orderId,
    method: 'get'
  })
}

export function updateById(paymentSystemId) {
  return request({
    url: '/admin/paymentResult/updateById/' + paymentSystemId,
    method: 'get'
  })
}

/*
* 结算
* */
export function settlement(paymentSystemId,value) {
  return request({
    url: '/admin/paymentResult/settlement?paymentSystemId=' + paymentSystemId+"&value="+value,
    method: 'get'
  })
}
