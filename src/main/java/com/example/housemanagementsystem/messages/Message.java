package com.example.housemanagementsystem.messages;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Message {
    private Integer messageID;
    private String messageTitle;
    private String messageStatus;
    private Timestamp createdAt;
    private String messageComment;
    private Integer userID;
    private Integer apartmentNo;
}
