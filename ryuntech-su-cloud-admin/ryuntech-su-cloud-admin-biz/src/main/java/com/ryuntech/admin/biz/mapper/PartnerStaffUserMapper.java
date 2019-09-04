package com.ryuntech.admin.biz.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ryuntech.admin.api.entity.PartnerStaffUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface PartnerStaffUserMapper extends BaseMapper<PartnerStaffUser> {
    IPage<PartnerStaffUser> Pages(@Param("page") Page<PartnerStaffUser> page);

    IPage<PartnerStaffUser> pageList(@Param("page") Page<PartnerStaffUser> page);
}
