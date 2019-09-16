/**
 * “选项”相关接口
 */
import request from '@/utils/request'

export function listRoleOptions() {
  return request({
    url: '/saas/option/role',
    method: 'get'
  })
}

