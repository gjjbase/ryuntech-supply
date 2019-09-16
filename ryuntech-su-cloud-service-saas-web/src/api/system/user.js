import request from '@/utils/request'

export function login(data) {
  return request({
    url: '/auth/oauth/token',
    method: 'post',
    params: {
      username: data.username,
      password: data.password,
      grant_type: 'password'
    }
  })
}

export function getInfo(token) {
  return request({
    url: '/saas/user/info',
    method: 'get',
    params: { token }
  })
}

export function logout() {
  return request({
    url: '/auth/token/logout',
    method: 'delete'
  })
}

export function getList(query, data) {
  return request({
    url: '/saas/user/list?pageCode=' + query.page + '&pageSize=' + query.limit,
    method: 'post',
    data
  })
}

export function findById(id) {
  return request({
    url: '/saas/user/' + id,
    method: 'get'
  })
}

export function save(data) {
  return request({
    url: '/saas/user',
    method: 'post',
    data
  })
}

export function del(id) {
  return request({
    url: '/saas/user/' + id,
    method: 'delete'
  })
}

export function edit(data) {
  return request({
    url: '/saas/user/edit',
    method: 'put',
    data
  })
}

export function changePass(data) {
  return request({
    url: '/saas/user/changePass',
    method: 'post',
    data
  })
}

/**
 * 更新用户的角色
 * @param perm
 */
export function updateUserRoles(data) {
  return request({
    url: '/saas/user/role',
    method: 'patch',
    data
  })
}

export const upload = process.env.VUE_APP_BASE_API + '/saas/storage/local/upload'
