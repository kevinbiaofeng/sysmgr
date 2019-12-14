package com.snake.mcf.sysmgr.task;

import com.snake.mcf.sysmgr.repertory.mapper.TbMerchantMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @ClassName SaticScheduleTask
 * @Author 大帅
 * @Date 2019/7/29 10:50
 */
@Component
@Configuration      //1.主要用于标记配置类，兼备Component的效果。
@EnableScheduling   // 2.开启定时任务
public class SaticScheduleTask {

    @Autowired
    private TbMerchantMapper tbMerchantMapper;

    //3.添加定时任务
    //@Scheduled(cron = "0/5 * * * * ?")
    @Scheduled(cron = "0 */1 * * * ?")
    //或直接指定时间间隔，例如：5秒
    //@Scheduled(fixedRate=5000)
    private void configureTasks() {
        //System.out.println("tbMerchantMapper == " + tbMerchantMapper);
        //System.out.println("执行定时任务时间：" + LocalDateTime.now());
        
    }


}
