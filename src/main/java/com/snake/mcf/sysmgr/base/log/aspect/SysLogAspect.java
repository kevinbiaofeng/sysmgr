package com.snake.mcf.sysmgr.base.log.aspect;

import com.snake.mcf.common.aop.annotation.AopLog;
import com.snake.mcf.common.utils.CusAccessObjectUtil;
import com.snake.mcf.common.utils.GeneratotUtils;
import com.snake.mcf.common.utils.JsonUtils;
import com.snake.mcf.sysmgr.authority.ShiroUtils;
import com.snake.mcf.sysmgr.base.log.SysLogService;
import com.snake.mcf.sysmgr.repertory.entity.dto.TbSysLogDTO;
import com.snake.mcf.sysmgr.repertory.entity.dto.TbSysRoleDTO;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.List;

/**
 * @ClassName SysLogAspect
 * @Author 大帅
 * @Date 2019/6/24 17:24
 */
@Slf4j
@Aspect
@Component
public class SysLogAspect {

    @Autowired
    private SysLogService sysLogService;

    //定义切点 @Pointcut
    //在注解的位置切入代码
    @Pointcut("@annotation(com.snake.mcf.common.aop.annotation.AopLog)")
    public void logPoinCut() {

    }

    //切面 配置通知
    @AfterReturning("logPoinCut()")
    public void saveSysLog(JoinPoint joinPoint) {
        log.info("切面......");
        log.info("sysLogService = {}",sysLogService);
        log.info("joinPoint = {}",joinPoint);

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        TbSysLogDTO logDTO = new TbSysLogDTO();
        logDTO.setId(GeneratotUtils.generateNumber());
        logDTO.setCreatedDate(new Date());
        //获取操作
        //从切面织入点处通过反射机制获取织入点处的方法

        MethodSignature signature = (MethodSignature)joinPoint.getSignature();
        //获取切入点所在的方法
        Method method = signature.getMethod();
        AopLog myLog = method.getAnnotation(AopLog.class);

        if (myLog != null) {
            String module = myLog.module();
            log.info("module : {}",module);
            logDTO.setModule(module);
            String description = myLog.description();
            log.info("description : {}",description);
            logDTO.setDescription(description);
        }

        logDTO.setMethod(request.getMethod());
        logDTO.setAccount(ShiroUtils.getSessionLoginName());
        logDTO.setIp(CusAccessObjectUtil.getIpAddress(request));
        //请求的参数
        Object[] args = joinPoint.getArgs();
        //将参数所在的数组转换成json
        String params = JsonUtils.toString(args);
        logDTO.setParams(params);

        List<TbSysRoleDTO> roles = ShiroUtils.getSessionRole();
        List<String> roleName = sysLogService.getRoleName(roles);
        logDTO.setRoleName(JsonUtils.toString(roleName));

        //商户号
        logDTO.setMerchant(ShiroUtils.getSessionMerchant());

        sysLogService.saveSysLog(logDTO);
    }




}
