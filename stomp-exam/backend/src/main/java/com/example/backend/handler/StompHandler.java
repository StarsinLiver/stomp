package com.example.backend.handler;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptorAdapter;
import org.springframework.stereotype.Component;

/**
 * packageName : com.example.backend.handler
 * fileName : StompHandler
 * author : san26
 * date : 2023-12-31
 * description :
 * 요약 :
 * <p>
 * ===========================================================
 * DATE            AUTHOR             NOTE
 * —————————————————————————————
 * 2023-12-31         san26          최초 생성
 */

@Component
public class StompHandler extends ChannelInterceptorAdapter {
    @Override
    public void postSend(Message message, MessageChannel channel, boolean sent) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
        String sessionId = accessor.getSessionId();

        switch (accessor.getCommand()) {
            case CONNECT:
                System.out.println("세션 들어옴 => " + sessionId);
                break;
            case DISCONNECT:
                System.out.println("세션 끊음 => " + sessionId);
                break;
            default:
                break;
        }
    }
}
