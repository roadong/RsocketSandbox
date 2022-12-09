package com.gl.rsocketsandbox.chat.configure;

import io.netty.buffer.ByteBufAllocator;
import io.rsocket.frame.decoder.PayloadDecoder;
import org.springframework.boot.autoconfigure.rsocket.RSocketMessageHandlerCustomizer;
import org.springframework.boot.rsocket.server.RSocketServerCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.ReactiveAdapterRegistry;
import org.springframework.core.codec.Decoder;
import org.springframework.core.codec.Encoder;
import org.springframework.core.io.buffer.NettyDataBufferFactory;
import org.springframework.http.codec.cbor.Jackson2CborDecoder;
import org.springframework.http.codec.cbor.Jackson2CborEncoder;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.http.codec.json.Jackson2JsonEncoder;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.rsocket.DefaultMetadataExtractor;
import org.springframework.messaging.rsocket.RSocketStrategies;
import org.springframework.messaging.rsocket.annotation.support.RSocketMessageHandler;
import org.springframework.web.util.pattern.PathPatternRouteMatcher;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.function.Consumer;

@Configuration
public class ServerConfig {

    @Bean
    RSocketMessageHandlerCustomizer rSocketMessageHandler() {
        return messageHandler -> {
            messageHandler.setRSocketStrategies(rSocketStrategies());
            messageHandler.afterPropertiesSet();
        };
    }

    @Bean
    RSocketStrategies rSocketStrategies() {
        return RSocketStrategies.builder()
                .encoders(encoderList())
                .decoders(decoderList())
                .routeMatcher(new PathPatternRouteMatcher())
//                .metadataExtractor(new DefaultMetadataExtractor())
                .reactiveAdapterStrategy(new ReactiveAdapterRegistry())
                .dataBufferFactory(new NettyDataBufferFactory(ByteBufAllocator.DEFAULT))
                .build();
    }

    private Consumer<List<Encoder<?>>> encoderList() {
        return encoders -> {
            encoders.add(new Jackson2JsonEncoder());
            encoders.add(new Jackson2CborEncoder());
        };
    }

    private Consumer<List<Decoder<?>>> decoderList() {
        return decoders -> {
            decoders.add(new Jackson2JsonDecoder());
            decoders.add(new Jackson2CborDecoder());
        };
    }

//    @Bean
//    public RSocketRequester requester(RSocketRequester.Builder builder,
//                                      RSocketStrategies rSocketStrategies) {
//        RSocketMessageHandler
//        return builder
//                .rsocketStrategies(rSocketStrategies)
//                .rsocketConnector(connector -> connector
//                        .dataMimeType(MimeTypeUtils.APPLICATION_JSON_VALUE)
//                        .dataMimeType(MimeTypeUtils.TEXT_PLAIN_VALUE)
//                        .metadataMimeType(WellKnownMimeType.MESSAGE_RSOCKET_ROUTING.toString())
//                        .payloadDecoder(PayloadDecoder.ZERO_COPY)
//                        .reconnect(Retry.fixedDelay(3, Duration.ofSeconds(3))))
//
//                .tcp("0.0.0.0", 8090);
//    }

    @Bean
    RSocketServerCustomizer rSocketServerCustomizer(RSocketStrategies rSocketStrategies) {
        return rSocketServer -> {
            rSocketServer
                    .payloadDecoder(PayloadDecoder.ZERO_COPY);
        };
    }

//    @Bean
//    public CloseableChannel rsocketServer() {
//        return RSocketServer.create(rSocketMessageHandler().responder())
//                .bind(TcpServerTransport.create("localhost", 8090))
//                .block();
//    }
}
