package com.example.backend.controller;

/**
 * packageName : com.example.backend.controller
 * fileName : ChatController
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

import com.example.backend.model.ChatMessage;

import com.example.backend.model.ChatRoom;
import com.example.backend.model.ChatUser;
import com.example.backend.model.MessageType;
import com.example.backend.service.ChatMessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ChatController {

    private final ChatMessageService chatService;

    @MessageMapping("/chat/sendMessage")
    public void sendMessage(@Payload ChatMessage message) {
        System.out.println(message);
        chatService.handleChatMessage(message.getRoomId(), message);
    }

    //    Todo : 이미지는 이곳으로..
    @PostMapping("/api/data")
    public void sendMessage(@RequestParam("sender") int sender,
                            @RequestParam("roomId") int roomId,
                            @RequestParam("content") String content,
                            @RequestParam("type") String type,
                            @RequestParam("fileRoot") String fileRoot,
                            @RequestParam("file") MultipartFile file,
                            @RequestParam("timestamp") String timestamp) {
        try {
            ChatMessage chatMessage = ChatMessage.builder()
                    .sender(sender)
                    .roomId(roomId)
                    .content(content)
                    .type(MessageType.DATA)
                    .fileRoot(fileRoot)
                    .fileName(file.getOriginalFilename())
                    .fileData(file.getBytes())
                    .timestamp(timestamp)
                    .build();
            chatService.handleChatMessage(chatMessage.getRoomId(), chatMessage);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    //    Todo : 이미지 보기
    @GetMapping("/api/chat-message/{uuid}")
    public ResponseEntity<byte[]> findByIdDownloading(@PathVariable String uuid) {
        ChatMessage chatMessage = chatService.findByUuid(uuid).get();         // 상세조회

        return ResponseEntity.ok()
//           Todo : header() : 헤더 (1)첨부파일로 전송한다고 표시, (2) 첨부파일명 표시
//                  HttpHeaders.CONTENT_DISPOSITION : 첨부파일 표시
//                  "attachment; filename=\"" + fileDb.getFileName() + "\"" : 첨부파일명 표시
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + chatMessage.getFileName() + "\"")
//           TODO : body() : 바디 - 실제 이미지 전송(리액트)
                .body(chatMessage.getFileData());    // 첨부파일
    }

    //    채팅방 전체 보기
    @GetMapping("/api/chat-room")
    public ResponseEntity<?> findAllChatRoom() {
        try {
            return new ResponseEntity<>(chatService.findAllChatRoom(), HttpStatus.OK);
        } catch (Exception e) {
            log.info(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //    채팅방 생성
    @PostMapping("/api/chat-room")
    public ResponseEntity<?> addChatRoom(@RequestBody ChatRoom chatRoom) {
        try {
            chatService.addChatRoom(chatRoom);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            log.info(e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    //    유저 생성
//    채팅방 생성
    @PostMapping("/api/chat-user")
    public ResponseEntity<?> addChatRoom(@RequestBody ChatUser user) {
        try {
            ChatUser chatUser = chatService.addChatUser(user);
            return new ResponseEntity<>(chatUser, HttpStatus.OK);
        } catch (Exception e) {
            log.info(e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }


    //    현재 이부분이 안됨 딱히 상관은 없는데 짜증나네 ㅋㅋㅋㅋㅋㅋㅋ
    @SubscribeMapping("/sub/chat/{roomId}")
//    public ChatRoom joinRoom(@DestinationVariable String roomId) {
    public void joinRoom(@DestinationVariable String roomId) {
        System.out.println("구독 룸넘버  | ");
//        chatService.handleUserJoin(roomId , "USER");
//        return chatService.getChatRoom(roomId);
    }
}