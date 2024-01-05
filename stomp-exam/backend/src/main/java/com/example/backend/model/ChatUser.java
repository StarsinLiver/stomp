package com.example.backend.model;

import lombok.*;

import javax.persistence.*;

/**
 * packageName : com.example.backend.model
 * fileName : ChatUser
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
@Table(name = "chat_user")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Setter
@Builder
public class ChatUser {
    @Id
    @GeneratedValue(strategy = GenerationType
            .IDENTITY)
    private Integer uid;
    private String name;
    private String roomId;
}
