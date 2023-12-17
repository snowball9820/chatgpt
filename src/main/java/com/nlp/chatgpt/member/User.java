package com.nlp.chatgpt.member;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
@Getter
@Setter
@Document(collection = "users") // DB안에 users라는 콜렉션에 저장(table 개념)
public class User {


    @Id
    private String id; // 여기서 id는 mongoDB에서 객체를 식별하기 위한 id
    private String username; // 유저 id
    private String password; // 비밀번호
    private String fullName; // 이름
    private String phoneNumber; // 핸드폰 번호
}
