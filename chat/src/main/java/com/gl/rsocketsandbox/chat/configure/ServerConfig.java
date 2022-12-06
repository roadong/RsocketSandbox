package com.gl.rsocketsandbox.chat.configure;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.messaging.rsocket.RSocketStrategies;
import org.springframework.messaging.rsocket.annotation.support.RSocketMessageHandler;
import org.springframework.web.util.pattern.PathPatternRouteMatcher;
import reactor.util.retry.Retry;

import java.time.Duration;

@Configuration
public class ServerConfig {

    @Bean
    public RSocketMessageHandler rSocketMessageHandler() {
        RSocketMessageHandler handler = new RSocketMessageHandler();
        handler.setRouteMatcher(new PathPatternRouteMatcher());
        handler.setRSocketStrategies(rSocketStrategies());
        return handler;
    }

    @Bean
    public RSocketStrategies rSocketStrategies() {
        return RSocketStrategies.builder()
                .routeMatcher(new PathPatternRouteMatcher())
                .build();
    }

    @Bean
    public RSocketRequester requester(RSocketRequester.Builder builder) {
        return builder
                .rsocketStrategies(rSocketStrategies())
                .rsocketConnector(connector -> connector.reconnect(Retry.fixedDelay(3, Duration.ofSeconds(3))))
                .tcp("0.0.0.0", 8090);
    }


//    @Bean
//    public CloseableChannel rsocketServer() {
//        return RSocketServer.create(rSocketMessageHandler().responder())
//                .bind(TcpServerTransport.create("localhost", 8090))
//                .block();
//    }
}
