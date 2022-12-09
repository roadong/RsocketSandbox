package com.gl.rsocketsandbox.chat.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;


public class EchoMessage {
    @Getter
    private String sender;
    @Getter
    private String message;

    protected EchoMessage() {}

    public EchoMessage(String sender, String message) {
        this.sender = sender;
        this.message = message;
    }
}
