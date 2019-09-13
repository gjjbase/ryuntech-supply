import router from './router'
import store from './store'
import { Message } from 'element-ui'
import NProgress from 'nprogress' // progress bar
import 'nprogress/nprogress.css' // progress bar style
import { getToken } from '@/utils/auth' // get token from cookie
import getPageTitle from '@/utils/get-page-title'

NProgress.configure({ showSpinner: false }) // NProgress Configuration

const loginRoute = '/login'
const whiteList = [loginRoute, '/outfinance'] // no redirect whitelist
/**
 * 匹配权限
 * @param userPerms 用户拥有的权限集合，后台返回来，存在vuex，数据类型是数组
 * @param routerPerm 定义的src/router/index.js的路由表asyncRouterMap中
 * @returns {*}
 */
function hasPermission(userPerms, routerPerm) {
  // 特殊值，*代表所有资源权限
  if (userPerms.some(p => p.val == '*')) return true
  // 如果菜单路由上没有声明perm属性，默认显示该菜单，代表所有人可以访问
  if (!routerPerm) return true
  // 判断用户的资源权限集合中是否包含该菜单路由声明的资源权限
  return userPerms.some(p => p.val == routerPerm)
}

router.beforeEach(async(to, from, next) => {
  // start progress bar
  NProgress.start()

  // set page title
  document.title = getPageTitle(to.meta.title)

  // determine whether the user has logged in
  const token = getToken()
  const hasToken = token != 'undefined' && token != undefined && token != null && token != ''

  if (hasToken) {
    if (to.path === loginRoute) {
      // if is logged in, redirect to the home page
      next({ path: '/' })
      NProgress.done()
    } else {
      const hasGetUserInfo = store.getters.name
      if (hasGetUserInfo) {
        next()
      } else if (!store.getters.perms || store.getters.perms.length === 0) {
        try {
          // get user info
          await store.dispatch('user/getInfo').then(res => {
            const perms = res.authorities // note: roles must be a array! such as: [{name:'菜单1',val:'menu:1'}]
            store.dispatch('GenerateRoutes', { perms }).then(() => { // 根据roles权限生成可访问的路由表
              router.addRoutes(store.getters.addRouters) // 动态添加可访问路由表
              next({ ...to, replace: true }) // hack方法 确保addRoutes已完成 ,set the replace: true so the navigation will not leave a history record
            })
          })
          next()
        } catch (error) {
          // remove token and go to login page to re-login
          await store.dispatch('user/resetToken')
          Message.error(error || 'Has Error')
          next(`/login?redirect=${to.path}`)
          NProgress.done()
        }
      }
    }
  } else {
    /* has no token*/

    if (whiteList.indexOf(to.path) !== -1) {
      // in the free login whitelist, go directly
      next()
    } else {
      // other pages that do not have permission to access are redirected to the login page.
      next(`/login?redirect=${to.path}`)
      NProgress.done()
    }
  }
})

router.afterEach(() => {
  // finish progress bar
  NProgress.done()
})
