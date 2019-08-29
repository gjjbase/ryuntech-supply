package com.ryuntech.admin.biz.mapper;

import com.ryuntech.admin.api.entity.Partner;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author antu
 * @since 2019-08-19
 */
@Mapper
@Repository
public interface PartnerMapper extends IBaseMapper<Partner> {

}
