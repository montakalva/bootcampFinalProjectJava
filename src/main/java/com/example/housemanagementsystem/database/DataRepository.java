package com.example.housemanagementsystem.database;

import com.example.housemanagementsystem.users.User;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DataRepository {
    private static DataRepository dataRepository_instance;

    private Integer loggedInUserID = null;
    private User loggedInUser = null;


    public static DataRepository getInstance() {
        if (dataRepository_instance == null) dataRepository_instance = new DataRepository();
        return dataRepository_instance;
    }
}