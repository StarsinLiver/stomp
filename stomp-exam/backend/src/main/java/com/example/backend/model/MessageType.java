package com.example.backend.model;

/**
 * packageName : com.example.backend.model
 * fileName : MessageType
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
public enum MessageType {
    CHAT, // 챗이냐 (String)
    DATA, // IMAGE 형식이냐
    JOIN,
    LEAVE
}