package com.example.backend.repository;

import com.example.backend.model.ChatMessage;
import com.example.backend.model.ChatRoom;
import com.example.backend.model.dto.ChatMessageDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * packageName : com.example.backend.repository
 * fileName : ChatMessageRepository
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
@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessage , Integer> {

//    일반적으로 보낼때
    @Query(value = "select m.cid as cid, m.sender as sender,  m.content as content, m.type as type, m.file_root as fileRoot, m.timestamp as timestamp, r.room_name as roomName, u.name as name " +
            "from chat_message m " +
            "join chat_user u " +
            "on m.sender = u.uid " +
            "join chat_room r " +
            "on m.room_id = r.room_id " +
            "where m.room_id = :roomId" , nativeQuery = true)
    List<ChatMessageDto> selectChatMessageAllByRoomId(int roomId);

//    이미지 , 비디오 데이터 보낼때
    Optional<ChatMessage> findByUuid(String uuid);
}
