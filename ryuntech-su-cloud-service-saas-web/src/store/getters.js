const getters = {
  sidebar: state => state.app.sidebar,
  device: state => state.app.device,
  visitedViews: state => state.tagsView.visitedViews,
  cachedViews: state => state.tagsView.cachedViews,
  token: state => state.user.token,
  avatar: state => state.user.avatar,
  name: state => state.user.name,
  perms: state => state.user.perms,
  roles: state => state.user.roles,
  addRouters: state => state.permission.addRouters,
  permission_routers: state => state.permission.routers,
  errorLogs: state => state.errorLog.logs
}
export default getters
