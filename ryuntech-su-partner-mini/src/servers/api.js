"use strict";

let urlPath = "https://wx.ryuntech.com:9999/api/admin/";

module.exports = {
  AuthLogin: urlPath+"/partner/outAuth", //微信认证
  AuthUserInfo: urlPath+"/user/userInfo", //微信登录
  SendCode: urlPath+"/sms/", //发送验证码
  UpdateUser: urlPath+"/partner/outUpdateUser", //更新用户信息
  RegisterUpdateUser: urlPath+"/partner/outRegisterUpdateUser", //更新用户信息
  UpdateOrder: urlPath+"/partner/outUpdateOrder", //更新订单信息
  Settlement: urlPath+"/order/outSettlement", //结算
  ImgPath : "https://wx.ryuntech.com/upload/",//图片路径
  OrderList : urlPath+"order/outList",//订单路径
  FindByOpenId : urlPath+"partner/outFindByOpenId",//获取用户信息
};
