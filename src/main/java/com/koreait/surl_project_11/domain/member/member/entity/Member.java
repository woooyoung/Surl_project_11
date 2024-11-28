package com.koreait.surl_project_11.domain.member.member.entity;

import com.koreait.surl_project_11.global.jpa.entity.BaseTime;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.*;

import static lombok.AccessLevel.PROTECTED;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor(access = PROTECTED)
@NoArgsConstructor(access = PROTECTED)
public class Member extends BaseTime {
    @Column(unique = true)
    private String username;
    private String password;
    private String nickname;
    @Column(unique = true)
    private String refreshToken;

    public String getName() {
        return nickname;
    }
}
