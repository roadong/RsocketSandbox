package com.gl.rsocketsandbox.chat.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Mono;

@Controller
public class EchoController {
    @MessageMapping("echo.message")
    public Mono<String> echo(RSocketRequester requester, @Payload String data) {
        return Mono.just(data);
    }
}

