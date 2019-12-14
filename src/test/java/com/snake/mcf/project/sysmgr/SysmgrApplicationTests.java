package com.snake.mcf.project.sysmgr;

import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.snake.mcf.sysmgr.service.api.QRCodeService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SysmgrApplicationTests {

    @Autowired
    private QRCodeService qrCodeService;

    @Test
    public void contextLoads() {
        String userID = "3001";
        String merchant = "d94fbb4840574007a94c64f7633b8093";
        String code = "103232";
//        String qrCodePath = "D:/img";
//        String privateKey = "xWJSdaf7g9Qdnj3h";
//        Map<String, Object> flag = qrCodeService.getQRCodeUrl(userID, merchant, code, 20191106);
//        System.out.println("flag---------"+ flag.toString());
    }

    /*@Test
    public void contextLoads() {
        int userID = 3008;
        String merchant = "d94fbb4840574007a94c64f7633b8093";
        String qrCodeUrl = qrCodeService.getQRCodeUrl(userID, merchant);
        System.out.println(qrCodeUrl);
    }*/


}
