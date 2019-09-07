package com.ryuntech.admin.biz.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ryuntech.admin.api.entity.FinanceUserInfo;
import com.ryuntech.admin.api.vo.FinanceOrder;
import com.ryuntech.admin.biz.service.IFinanceUserInfoService;
import com.ryuntech.admin.biz.service.IOrderService;
import com.ryuntech.common.utils.QueryPage;
import com.ryuntech.common.utils.Result;
import com.ryuntech.common.utils.ValidateUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import static com.ryuntech.common.constant.enums.CommonEnums.PARAM_PARSE_ERROR;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author antu
 * @since 2019-08-14
 */
@Slf4j
@RestController
@RequestMapping("/financeUserInfo")
@Api(value = "FinanceUserInfoController", tags = {"融资客户信息管理接口"})
public class FinanceUserInfoController extends BaseController {
    @Autowired
    private IFinanceUserInfoService iFinanceUserInfoService;

    @Autowired
    private IOrderService iOrderService;
    @Autowired
    RedisTemplate redisTemplate;

    /**
     * 分页查询列表数据，条件查询
     *
     * @param financeUserInfo
     * @return
     */
    @PostMapping("/list")
    @ApiOperation(value = "分页、条件查询用户列表信息")
    @ApiImplicitParam(name = "financeUserInfo", value = "查询条件", required = true, dataType = "SysUser", paramType = "body")
    public Result<IPage<FinanceUserInfo>> list(FinanceUserInfo financeUserInfo, QueryPage queryPage) {
        return iFinanceUserInfoService.pageList(financeUserInfo,queryPage);
    }

    /**
     * 根据ID查询用户信息
     *
     * @param userId
     * @return
     */
    @GetMapping("/{userId}")
    @ApiOperation(value = "查询详细融资客户信息", notes = "userId存在")
    @ApiImplicitParam(name = "userId", value = "用户编号", required = true, dataType = "String")
    public Result<FinanceUserInfo> findById(@PathVariable String userId) {
        if (StringUtils.isBlank(userId)) {
            return new Result<>();
        } else {
            return new Result<>(iFinanceUserInfoService.getById(userId));
        }
    }

    /**
     * 更新用户信息
     *
     * @param financeUserInfo
     * @return
     */
    @PostMapping("/edit")
    @ApiOperation(value = "更新融资用户")
    @ApiImplicitParam(name = "financeUserInfo", value = "用户实体信息", required = true, dataType = "FinanceUserInfo", paramType = "body")
    public Result edit(@RequestBody FinanceUserInfo financeUserInfo) {
        return iFinanceUserInfoService.updateFinacneOrder(financeUserInfo);
    }


    /**
     * 删除用户信息
     *
     * @param userId
     * @return
     */
    @DeleteMapping("/{userId}")
    @ApiOperation(value = "删除用户")
    @ApiImplicitParam(name = "userId", value = "用户编号", required = true, dataType = "String")
    public Result delete(@PathVariable String userId) {
        iFinanceUserInfoService.removeById(userId);
        return new Result();
    }
    /**
     * 添加融资用户信息 ，并更新订单数据FindByOpenId
     *
     * @param financeOrder
     * @return
     */
    @PostMapping("/outAddOrder")
    @ApiOperation(value = "添加融资用户信息")
    @ApiImplicitParam(name = "financeOrder", value = "添加融资用户信息", required = true, dataType = "FinanceOrder", paramType = "body")
    public Result addOrder(@RequestBody FinanceOrder financeOrder) {
        //验证手机
        boolean mobileNumber = ValidateUtil.isMobileNumber(financeOrder.getMobile());
        if (mobileNumber){
            //检查该用户的订单是否有未完结状态，有则不能申请
          /*  FinanceUserInfo financeUserInfo = iFinanceUserInfoService.selectOne(financeOrder);
            if (financeUserInfo!=null){
                //查询是否有订单未审核的状态
                iOrderService.
            }*/
            //核对验证码是否正确
//            Object attribute = getSession().getAttribute(financeOrder.getMobile() + "ryun_code");
            Object value =   redisTemplate.opsForValue().get(financeOrder.getMobile() + "ryun_code");
            if (value!=null&&value.toString().equals(financeOrder.getCode())){
                getSession().setAttribute(financeOrder.getMobile() + "ryun_code",null);
                return iFinanceUserInfoService.addFinacneOrder(financeOrder);
            }else {
                return  new Result(PARAM_PARSE_ERROR);
            }
        }else {
            return  new Result(PARAM_PARSE_ERROR);
        }
    }


}
