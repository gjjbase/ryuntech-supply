import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

/* Layout */
import Layout from '@/layout'
const _import = require('./_import_' + process.env.NODE_ENV)
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
    path: '/saas',
    component: Layout,
    // redirect: '/saas/user',
    name: '系统管理',
    meta: { title: '系统管理', icon: 'setting' },
    children: [
      {
        path: 'user',
        name: '用户管理',
        component: () => import('@/views/system/user/index'),
        meta: { title: '用户管理', icon: 'peoples' }
      },
      {
        path: 'role_manage',
        name: '角色管理',
        component: () => import('@/views/system/role/index'),
        meta: { title: '角色管理', icon: 'peoples', noCache: true }
      },
      {
        hidden: true,
        path: 'role_manage/:roleId/assign_perm',
        name: '角色授权',
        component: () => import('@/views/system/role/assign_perm'),
        meta: { hiddenTag: true, title: '角色授权' }
      },
      {
        path: 'perm_manage',
        name: '权限管理',
        component: () => import('@/views/system/perm/index'),
        meta: { title: '权限管理', icon: 'chart', noCache: true }
      }
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
    redirect: '/monitor/saas',
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
  { path: '*', redirect: '/404', hidden: true },
  { path: '/401', redirect: '/404', hidden: true }
]

export const asyncRouterMap = [
  {
    path: '/menu1',
    component: Layout,
    children: [{
      path: 'index',
      name: 'menu1',
      component: _import('menu/menu1'),
      meta: { perm: 'm:menu1', title: '菜单1', icon: 'icon' }
    }]
  },

  {
    path: '/menu2',
    component: Layout,
    children: [{
      path: 'index',
      name: 'menu2',
      component: _import('menu/menu2'),
      meta: { perm: 'm:menu2', title: '菜单2', icon: 'icon' }
    }]
  },

  {
    path: '/menu3',
    component: Layout,
    meta: {
      perm: 'm:menu3',
      title: '菜单3',
      icon: 'chart'
    },
    children: [
      {
        path: 'menu3_1',
        component: _import('menu/menu3_1'),
        name: 'menu3_1',
        meta: { perm: 'm:menu3:1', title: '菜单3-1', icon: 'chart', noCache: true }
      },
      {
        path: 'menu3_2',
        component: _import('menu/menu3_2'),
        name: 'menu3_2',
        meta: { perm: 'm:menu3:2', title: '菜单3-2', icon: 'chart', noCache: true }
      },
      {
        path: 'menu3_3',
        component: _import('menu/menu3_3'),
        name: 'menu3_3',
        meta: { perm: 'm:menu3:3', title: '菜单3-3', icon: 'chart', noCache: true }
      }
    ]
  },
  {
    path: '/menu4',
    name: 'menu4',
    component: Layout,
    redirect: '/menu4/menu4_1/a',
    meta: {
      perm: 'm:menu4',
      title: '菜单4',
      icon: 'example'
    },
    children: [
      {
        path: '/menu4/menu4_1',
        name: 'menu4_1',
        component: _import('menu/menu4_1/index'),
        redirect: '/menu4/menu4_1/a',
        meta: {
          perm: 'm:menu4:1',
          title: '菜单4-1',
          icon: 'table'
        },
        children: [
          {
            path: 'a',
            name: 'menu4_1_a',
            component: _import('menu/menu4_1/a'),
            meta: { perm: 'm:menu4:1:a', title: '菜单4-1-a' }
          },
          {
            path: 'b',
            name: 'menu4_1_b',
            component: _import('menu/menu4_1/b'),
            meta: { perm: 'm:menu4:1:b', title: '菜单4-1-b' }
          },
          {
            path: 'c',
            name: 'menu4_1_c',
            component: _import('menu/menu4_1/c'),
            meta: { perm: 'm:menu4:1:c', title: '菜单4-1-c' }
          }
        ]
      },
      {
        path: 'menu4/menu4_2',
        name: 'menu4_2',
        icon: 'tab',
        component: _import('menu/menu4_2/index'),
        meta: { perm: 'm:menu4:2', title: '菜单4-2' }
      }
    ]
  }
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
