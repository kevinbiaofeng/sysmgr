package com.snake.mcf.sysmgr.service.goldmgr.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.snake.mcf.sysmgr.base.BaseServiceImpl;
import com.snake.mcf.sysmgr.repertory.entity.Shareconfig;
import com.snake.mcf.sysmgr.repertory.mapper.ShareconfigMapper;
import com.snake.mcf.sysmgr.service.goldmgr.ShareConfigService;

import tk.mybatis.mapper.entity.Example;

@Service
public class ShareConfigServiceImpl extends BaseServiceImpl implements ShareConfigService {

    @Autowired
    private ShareconfigMapper shareconfigMapper;
    
    @Override
    public List<Shareconfig> getShareRewardList(){
    	//过滤
        Example example = new Example(Shareconfig.class);
        //查询
    	return shareconfigMapper.selectByExample(example);
    }
}
