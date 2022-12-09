package com.gl.rsocketsandbox.chat.controller;

import com.gl.rsocketsandbox.chat.dto.EchoMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.messaging.rsocket.annotation.ConnectMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Mono;

@Controller
@Slf4j
public class EchoController {

    @ConnectMapping
    public void setupFrame(RSocketRequester requester) {
        // 연결 셋업
        log.info(requester.toString());
    }

    @MessageMapping("ping")
    public Mono<String> pong(@Payload String ping) {
        log.info(ping);
        return Mono.just("pong");
    }

    @MessageMapping("echo.message")
    public Mono<EchoMessage> echo(EchoMessage message) {
        return Mono.just(message);
    }
}

