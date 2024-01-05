package com.example.backend.service;

import com.example.backend.model.ChatMessage;
import com.example.backend.model.ChatRoom;
import com.example.backend.model.ChatUser;
import com.example.backend.model.MessageType;
import com.example.backend.model.dto.ChatMessageDto;
import com.example.backend.repository.ChatMessageRepository;
import com.example.backend.repository.ChatRoomRepository;
import com.example.backend.repository.ChatUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.*;

/**
 * packageName : com.example.backend.service
 * fileName : ChatMessageService
 * author : san26
 * date : 2024-01-05
 * description :
 * 요약 :
 * <p>
 * ===========================================================
 * DATE            AUTHOR             NOTE
 * —————————————————————————————
 * 2024-01-05         san26          최초 생성
 */
@Service
@RequiredArgsConstructor
public class ChatMessageService {
    private final SimpMessageSendingOperations messagingTemplate;
    private final ChatMessageRepository chatMessageRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final ChatUserRepository chatUserRepository;

    public void handleChatMessage(int roomId , ChatMessage message) {
//        메시지를 세이브 합니다.
        ChatMessage chatMessage = null;
        if (message.getType().equals(MessageType.CHAT)) {
//             저장 실행
            chatMessageRepository.save(message);
        } else if (message.getType().equals(MessageType.DATA)) {
            // 저장 실행
            // 1) DB에 이미지 저장
            // 2) DB에 이미지를 다운로드 할 수 있는 url 저장 (다운로드 URL 만들기 필요)
            // 3) 파일명(중복이 안되는) : uuid(기본키) 사용(유일값)

            // TODO : 1) uuid 만들기
            String tmpUuid = UUID.randomUUID()              // UUID 랜덤 생성함수
                    .toString()                             // 문자열 변환
                    .replace("-", "");      // -를 빈문자열로 변환(편의상)

            // TODO : 2) 다운로드 url 만들기
            String fileDownloadUri = ServletUriComponentsBuilder
                    .fromCurrentContextPath()               // 기본 경로 : localhost:8000
                    .path("/api/chat-message/")          // 추가 경로 : 기본경로 + "/api/chat-message/"
                    .path(tmpUuid)                          // 기본경로 + 추가경로 + uuid 붙임
                    .toUriString();                         // 문자열 변환

            // TODO : 3) 위의 정보를 ProjectImages 객체에 저장 후 DB save 함수 실행
            chatMessage = ChatMessage.builder()
                    .sender(message.getSender())
                    .roomId(message.getRoomId())
                    .content(message.getContent())
                    .type(message.getType())
                    .uuid(tmpUuid)
                    .fileName(message.getFileName())
                    .fileRoot(fileDownloadUri)
                    .fileData(message.getFileData())
                    .timestamp(message.getTimestamp())
                    .build();

            chatMessageRepository.save(chatMessage);
        }

        List<ChatMessageDto> list = chatMessageRepository.selectChatMessageAllByRoomId(roomId);
        messagingTemplate.convertAndSend("/sub/chat/" + roomId , list);
    }

//    이미지 , 동영상을 보내기 위해 사용됩니다.
    public Optional<ChatMessage> findByUuid(String uuid) {
        return chatMessageRepository.findByUuid(uuid);
    }

//    채팅방 전체 목록을 가져옵니다.
    public List<ChatRoom> findAllChatRoom() {
        return chatRoomRepository.findAll();
    }

//    채팅방 만들기
    public ChatRoom addChatRoom(ChatRoom chatRoom) {
        return chatRoomRepository.save(chatRoom);
    }

//    유저 만들기
    public ChatUser addChatUser(ChatUser user) {
        return chatUserRepository.save(user);
    }

}
