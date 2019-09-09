"use strict";

var urlPath = "https://wx.ryuntech.com:9999/api/admin/";

module.exports = {
  AuthLogin: "https://wx.ryuntech.com:9999/api/admin//partner/outAuth", //微信认证
  AuthUserInfo: "https://wx.ryuntech.com:9999/api/admin//user/userInfo", //微信登录
  SendCode: "https://wx.ryuntech.com:9999/api/admin//sms/", //发送验证码
  UpdateUser: "https://wx.ryuntech.com:9999/api/admin//partner/outUpdateUser", //更新用户信息
  RegisterUpdateUser: "https://wx.ryuntech.com:9999/api/admin//partner/outRegisterUpdateUser", //更新用户信息
  UpdateOrder: "https://wx.ryuntech.com:9999/api/admin//partner/outUpdateOrder", //更新订单信息
  Settlement: "https://wx.ryuntech.com:9999/api/admin//order/outSettlement", //结算
  ImgPath: "https://wx.ryuntech.com/upload/", //图片路径
  OrderList: "https://wx.ryuntech.com:9999/api/admin/order/outList", //订单路径
  FindByOpenId: "https://wx.ryuntech.com:9999/api/admin/partner/outFindByOpenId" //获取用户信息
};