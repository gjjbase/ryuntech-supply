package com.ryuntech.admin.biz.controller;


import com.ryuntech.admin.biz.service.MessageSendService;
import com.ryuntech.common.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sms")
@Api(value = "SmsController", tags = {"短信发送接口"})
public class SmsController extends BaseController {

    @Autowired
    MessageSendService messageSendService;
    /**
     * 根据ID查询用户信息
     *
     * @param phone
     * @return
     */
    @GetMapping("/{phone}")
    @ApiOperation(value = "发送短信", notes = "phone存在")
    @ApiImplicitParam(name = "phone", value = "用户编号", required = true, dataType = "String")
    public Result  findById(@PathVariable String phone) {
        if (StringUtils.isBlank(phone)) {
            return new Result();
        } else {
            //生成随机的验证码，组装成短信发送
            //将验证码放在session中，对比用户传递的参数，一直则通过，否则不通过
//            messageSendService.sendSms();
            return new Result();
        }
    }
}
