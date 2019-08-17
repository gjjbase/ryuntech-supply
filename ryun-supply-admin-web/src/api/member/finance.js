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
export function save(data) {
  return request({
    url: '/admin/financeUserInfo/addOrder',
    method: 'post',
    data
  })
}
