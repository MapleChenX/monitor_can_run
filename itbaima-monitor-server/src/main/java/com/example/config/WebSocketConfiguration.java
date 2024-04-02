package com.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

@Configuration
public class WebSocketConfiguration {

    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        // 本Bean用于扫描和注册所有携带ServerEndpoint注解的实例用于WebSocket连接
        return new ServerEndpointExporter();
    }
}
