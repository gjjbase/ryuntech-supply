import request from '@/utils/request'

export function getList(query, data) {
  return request({
    url: '/admin/financeUserInfo/list?pageCode=' + query.page + '&pageSize=' + query.limit,
    method: 'post',
    data
  })
}
