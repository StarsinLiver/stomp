package com.example.backend.model.dto;

/**
 * packageName : com.example.backend.model.dto
 * fileName : ChatMessageDto
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

public interface ChatMessageDto {
    Integer getCid();
    Integer getSender();
    String getContent();
    String getType();
    String getFileRoot();
    String getTimestamp();
    String getRoomName();
    String getName();
}
