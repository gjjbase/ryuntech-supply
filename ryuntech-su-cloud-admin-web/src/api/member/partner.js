import request from '@/utils/request'

export function getList(query, data) {
  return request({
    url: '/admin/partner/list?pageCode=' + query.page + '&pageSize=' + query.limit,
    method: 'post',
    data
  })
}
export function findById(partnerId) {
  return request({
    url: '/admin/partner/' + partnerId,
    method: 'get'
  })
}
export function updateById(partnerId) {
  return request({
    url: '/admin/partner/updateById/' + partnerId,
    method: 'get'
  })
}

export function edit(data) {
  return request({
    url: '/admin/partner/edit',
    method: 'post',
    data
  })
}

export function del(partnerId) {
  return request({
    url: '/admin/partner/' + partnerId,
    method: 'delete'
  })
}
