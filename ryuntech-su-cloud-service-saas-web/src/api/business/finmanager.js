import request from '@/utils/request'

export function getList(query, data) {
  return request({
    url: '/saas/paymentResult/list?pageCode=' + query.page + '&pageSize=' + query.limit,
    method: 'post',
    data
  })
}
export function findById(orderId) {
  return request({
    url: '/saas/paymentResult/findById/' + orderId,
    method: 'get'
  })
}

export function updateById(paymentSystemId) {
  return request({
    url: '/saas/paymentResult/updateById/' + paymentSystemId,
    method: 'get'
  })
}

/*
* 结算
* */
export function settlement(paymentSystemId, value) {
  return request({
    url: '/saas/paymentResult/settlement?paymentSystemId=' + paymentSystemId + '&value=' + value,
    method: 'get'
  })
}
