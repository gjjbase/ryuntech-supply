package com.ryuntech.saas.controller;


import com.ryuntech.common.utils.RandomUtil;
import com.ryuntech.common.utils.Result;
import com.ryuntech.saas.api.dto.Sms;
import com.ryuntech.saas.api.dto.SmsResponse;
import com.ryuntech.saas.api.service.MessageSendService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

@Slf4j
@RestController
@RequestMapping("/sms")
@Api(value = "SmsController", tags = {"短信发送接口"})
public class SmsController extends ModuleBaseController {

    @Autowired
    MessageSendService messageSendService;
    @Autowired
    RedisTemplate redisTemplate;


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
            String numberCode = RandomUtil.createNumberCode(4);
            log.info("生成验证码成功:"+numberCode);
            getSession().setAttribute(phone+"ryun_code",numberCode);
            redisTemplate.opsForValue().set(phone+"ryun_code",numberCode,60*10, TimeUnit.SECONDS);
            Sms sms = new Sms();
            sms.setPhoneNumbers(phone);
            sms.setContent("{\"code\":\"" + numberCode + "\"}");
            SmsResponse smsResponse = messageSendService.sendSms(sms);
            return new Result(smsResponse);
        }
    }
}
