package com.snake.mcf.sysmgr.base.server;

import org.springframework.boot.web.context.WebServerInitializedEvent;
import org.springframework.boot.web.server.WebServer;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @ClassName ApplicationServer
 * @Author 大帅
 * @Date 2019/6/21 10:12
 */
@Component
public class ApplicationServer implements ApplicationListener<WebServerInitializedEvent> {

    private int serverPort;

    @Override
    public void onApplicationEvent(WebServerInitializedEvent webServerInitializedEvent) {
        WebServer webServer = webServerInitializedEvent.getWebServer();
        int port = webServer.getPort();
        this.serverPort = port;
    }

    public String getIP(){
        try {
            InetAddress address = InetAddress.getLocalHost();
            return address.getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return null;
    }



}
