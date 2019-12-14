package com.snake.mcf.sysmgr.repertory.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import com.snake.mcf.sysmgr.repertory.entity.TbMerchantDomain;
import com.snake.mcf.sysmgr.repertory.tk.TkComponentMapper;

@Repository
@Mapper
public interface TbMerchantDomainMapper extends TkComponentMapper<TbMerchantDomain> {

}
