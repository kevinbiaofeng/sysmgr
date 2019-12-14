package com.snake.mcf.project.sysmgr;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.junit.Test;

import com.snake.mcf.common.utils.GeneratotUtils;
import com.snake.mcf.common.utils.JsonUtils;
import com.snake.mcf.common.utils.security.encrypt.EncryptionUtil;

/**
 * @ClassName SerTest
 * @Author 大帅
 * @Date 2019/6/23 17:50
 */
public class SerTest {

    @Test
    public void test1() throws  Exception{
        String s = GeneratotUtils.generateNumber();
        System.out.println(s);
        //96e79218965eb72c92a549dd5a330112
        //96E79218965EB72C92A549DD5A330112
        //String s = MD5Utils.md5Digest("111111");
       // System.out.println(s.toString());
       /* String s1 = IdUtil.fastSimpleUUID();
        System.out.println(s1);
        String s2 = IdUtil.fastSimpleUUID();
        System.out.println(s2);

        System.out.println(s1+s2);

        byte[] data = "aaaaaa".getBytes();
        Sign sign = SecureUtil.sign(SignAlgorithm.SHA1withRSA);
        //签名
        byte[] signed = sign.sign(data);
        String s = new String(signed,"UTF-8");
        System.out.println(s);
        //验证签名
        boolean verify = sign.verify(data, signed);
        System.out.println(verify);*/
    }

    @Test
    public void test(){
        String ser = GeneratotUtils.generateBlank2LowerCase();
        Map<String,Object> map = new HashMap<>();
        map.put("loginName","admin");
        map.put("loginPassword",GeneratotUtils.generateBlank2LowerCase());
        map.put("merchant",GeneratotUtils.generateBlank2LowerCase());
        map.put("timestamp",new Date().getTime());
        String s = EncryptionUtil.encryptContent(JsonUtils.toString(map), ser);
        System.out.println(s);
        System.out.println(s.length());
        
        System.out.println(UUID.randomUUID().toString().replaceAll("-",""));
    }
    @Test
    public void testSaveConfigInfo(){

    }

}
