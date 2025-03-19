package snghk.word_chain.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfiguration implements WebSocketMessageBrokerConfigurer {
    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/subscribe"); // 메시지 구독 경로
        config.setApplicationDestinationPrefixes("/publish"); // 클라이언트 메시지 발행 경로
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws") // 웹소켓 연결 엔드포인트
                .setAllowedOrigins("http://localhost:8080") // CORS 허용
                .withSockJS(); // SockJS 지원
    }
}
