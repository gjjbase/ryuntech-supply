import request from '@/utils/request'

export function getList(query, data) {
  return request({
    url: '/admin/financeUserInfo/list?pageCode=' + query.page + '&pageSize=' + query.limit,
    method: 'post',
    data
  })
}
export function findById(userId) {
  return request({
    url: '/admin/financeUserInfo/' + userId,
    method: 'get'
  })
}

export function del(userId) {
  return request({
    url: '/admin/financeUserInfo/' + userId,
    method: 'delete'
  })
}

export function save(data) {
  return request({
    url: '/admin/financeUserInfo/outAddOrder',
    method: 'post',
    data
  })
}
export function edit(data) {
  return request({
    url: '/admin/financeUserInfo/edit',
    method: 'post',
    data
  })
}
export function sendCode(data) {
  return request({
    url: '/admin/sms/'+data,
    method: 'get'
  })
}
