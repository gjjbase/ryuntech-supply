import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

/* Layout */
import Layout from '@/layout'

/**
 * Note: sub-menu only appear when route children.length >= 1
 * Detail see: https://panjiachen.github.io/vue-element-admin-site/guide/essentials/router-and-nav.html
 *
 * hidden: true                   if set true, item will not show in the sidebar(default is false)
 * alwaysShow: true               if set true, will always show the root menu
 *                                if not set alwaysShow, when item has more than one children route,
 *                                it will becomes nested mode, otherwise not show the root menu
 * redirect: noRedirect           if set noRedirect will no redirect in the breadcrumb
 * name:'router-name'             the name is used by <keep-alive> (must set!!!)
 * meta : {
    roles: ['admin','editor']    control the page roles (you can set multiple roles)
    title: 'title'               the name show in sidebar and breadcrumb (recommend set)
    icon: 'svg-name'             the icon show in the sidebar
    breadcrumb: false            if set false, the item will hidden in breadcrumb(default is true)
    activeMenu: '/example/list'  if set path, the sidebar will highlight the path you set
  }
 */

/**
 * constantRoutes
 * a base page that does not have permission requirements
 * all roles can be accessed
 */
export const constantRoutes = [
  {
    path: '/login',
    component: () => import('@/views/login/index'),
    hidden: true
  },
  {
    path: '/outfinance',
    name: '融资申请',
    component: () => import('@/views/outfinance/index'),
    hidden: true
  },
  {
    path: '/',
    component: Layout,
    redirect: '/dashboard',
    children: [{
      path: 'dashboard',
      name: 'Dashboard',
      component: () => import('@/views/dashboard/index'),
      meta: { title: 'Dashboard', icon: 'dashboard' }
    }]
  },

  {
    path: '公司官网',
    component: Layout,
    children: [
      {
        path: 'https://www.ryuntech.com',
        meta: { title: '公司官网', icon: 'github' }
      }
    ]
  },

  {
    path: '/admin',
    component: Layout,
    redirect: '/admin/user',
    name: '权限管理',
    meta: { title: '权限管理', icon: 'setting' },
    children: [
      {
        path: 'user',
        name: '操作员管理',
        component: () => import('@/views/user/index'),
        meta: { title: '操作员管理', icon: 'peoples' }
      },
    ]
  },
  {
    path: '/business',
    component: Layout,
    redirect: '/operation/business',
    name: '业务处理',
    meta: { title: '业务处理', icon: 'setting' },
    children: [
      {
        path: 'reorder',
        name: '推荐订单管理',
        component: () => import('@/views/business/reorder/index'),
        meta: { title: '推荐订单管理', icon: 'documentation' }
      },
      {
        path: 'finmanager',
        name: '财务管理',
        component: () => import('@/views/business/finmanager/index'),
        meta: { title: '财务管理', icon: 'documentation' }
      }
      /*,
      {
        path: 'operatelog',
        name: '操作日志管理',
        component: () => import('@/views/business/operatelog/index'),
        meta: { title: '操作日志管理', icon: 'peoples' }
      },*/
    ]
  },
  {
    path: '/member',
    component: Layout,
    redirect: '/operation/member',
    name: '会员管理',
    meta: { title: '会员管理', icon: 'setting' },
    children: [
      {
        path: 'finance',
        name: '融资用户管理',
        component: () => import('@/views/member/finance/index'),
        meta: { title: '融资用户管理', icon: 'peoples' }
      },
      {
        path: 'partner',
        name: '合伙人会员管理',
        component: () => import('@/views/member/partner/index'),
        meta: { title: '合伙人会员管理', icon: 'documentation' }
      }
    ]
  },
  {
    path: '/monitor',
    component: Layout,
    redirect: '/monitor/admin',
    name: '系统管理',
    meta: { title: '系统监控', icon: 'springcloud' },
    children: [
      {
        path: 'admin',
        name: '服务监控',
        component: () => import('@/views/monitor/admin'),
        meta: { title: '服务监控', icon: 'service-monitor' }
      },
      {
        path: 'doc',
        name: '接口文档',
        component: () => import('@/views/doc/swagger'),
        meta: { title: '接口文档', icon: 'documentation' }
      }
     /* ,
      {
        path: 'zipkin',
        name: '链路监控',
        component: () => import('@/views/monitor/zipkin'),
        meta: { title: '链路监控', icon: 'zipkin' }
      },
      {
        path: 'eureka',
        name: '注册中心',
        component: () => import('@/views/monitor/eureka'),
        meta: { title: '注册中心', icon: 'service-center' }
      },*/
    ]
  },

  // 404 page must be placed at the end !!!
  { path: '*', redirect: '/404', hidden: true }
]

const createRouter = () => new Router({
  mode: 'history', // require service support
  scrollBehavior: () => ({ y: 0 }),
  routes: constantRoutes
})

const router = createRouter()

// Detail see: https://github.com/vuejs/vue-router/issues/1234#issuecomment-357941465
export function resetRouter() {
  const newRouter = createRouter()
  router.matcher = newRouter.matcher // reset router
}

export default router
