package com.example.backend.model;

import lombok.*;

import javax.persistence.*;

/**
 * packageName : com.example.backend.model
 * fileName : ChatRoom
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
@Entity
@Getter
@Table(name = "chat_room")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Setter
@Builder
public class ChatRoom {
    @Id
    @GeneratedValue(strategy = GenerationType
            .IDENTITY)
    private Integer roomId;
    private String roomName;
}
