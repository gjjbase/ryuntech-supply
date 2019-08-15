package com.ryuntech.admin.biz.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ryuntech.admin.api.entity.PartnerStaffUser;
import com.ryuntech.admin.biz.service.IPartnerStaffUserService;
import com.ryuntech.common.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author antu
 * @since 2019-08-12
 */
@RestController
@RequestMapping("/partner-staff-user")
public class PartnerStaffUserController {
    @Autowired
    IPartnerStaffUserService iPartnerStaffUserService;

    /**
     * 多表关联，分页查询(1对1)
     * @param page
     * @return
     */
    @RequestMapping("/findAll")
    public Result<IPage<PartnerStaffUser>> findAll(@RequestBody Page<PartnerStaffUser> page){

        return iPartnerStaffUserService.pages(page);

    }

    @RequestMapping("/selectAll")
    public Result<IPage<PartnerStaffUser>> selectAll(@RequestBody Page<PartnerStaffUser> page){

        return iPartnerStaffUserService.pageList(page);

    }
}
