package com.example.housemanagementsystem.votings;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Voting {

    private Integer votingID;
    private String votingStatus;
    private String votingTitle;
    private Integer answerOnTopicID;
    private String votingAnswer;
    private Timestamp votingAt;
    private Integer userID;
    private Integer apartmentNo;

    public Voting(String votingTitle) {

    }

    public Voting(Integer votingID, String votingTitle) {
    }

    public Voting(Integer votingID,String status,  String votingTitle, String votingAnswer) {
    }
}
