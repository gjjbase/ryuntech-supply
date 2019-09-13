import request from '@/utils/request'

export function getList(query, data) {
  return request({
    url: '/saas/financeUserInfo/list?pageCode=' + query.page + '&pageSize=' + query.limit,
    method: 'post',
    data
  })
}
export function findById(userId) {
  return request({
    url: '/saas/financeUserInfo/' + userId,
    method: 'get'
  })
}

export function del(userId) {
  return request({
    url: '/saas/financeUserInfo/' + userId,
    method: 'delete'
  })
}

export function save(data) {
  return request({
    url: '/saas/financeUserInfo/outAddOrder',
    method: 'post',
    data
  })
}
export function edit(data) {
  return request({
    url: '/saas/financeUserInfo/edit',
    method: 'post',
    data
  })
}
export function sendCode(data) {
  return request({
    url: '/saas/sms/' + data,
    method: 'get'
  })
}
