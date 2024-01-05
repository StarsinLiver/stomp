package com.example.backend.repository;

import com.example.backend.model.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * packageName : com.example.backend.repository
 * fileName : ChatRoomRepository
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
public interface ChatRoomRepository extends JpaRepository<ChatRoom , Integer> {
}
