package com.example.backend.model;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDateTime;
/**
 * packageName : com.example.backend.model
 * fileName : ChatMessage
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
@Entity
@Getter
@Table(name = "chat_message")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Setter
@Builder
//@DynamicInsert
//@DynamicUpdate
// soft delete
//@Where(clause = "DELETE_YN = 'N'")
//@SQLDelete(sql = "UPDATE projects_images SET DELETE_YN = 'Y', DELETE_TIME=NOW() WHERE pid = ?")
public class ChatMessage {
    @Id
    @GeneratedValue(strategy = GenerationType
            .IDENTITY)
    private Integer cid;
    private Integer sender; // 유저 id
    private String content;
    private MessageType type;
    private Integer roomId; // 방 id
    private String uuid;
    private String fileName;
    private String fileRoot;
    @Lob
    private byte[] fileData;
    private String timestamp;
}
