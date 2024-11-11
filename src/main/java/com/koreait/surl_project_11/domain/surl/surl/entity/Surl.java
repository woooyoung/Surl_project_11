package com.koreait.surl_project_11.domain.surl.surl.entity;

import com.koreait.surl_project_11.global.jpa.entity.BaseTime;
import jakarta.persistence.Entity;
import lombok.*;

import static lombok.AccessLevel.PROTECTED;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor(access = PROTECTED)
public class Surl extends BaseTime {
    private String body;
    private String url;
    @Setter(AccessLevel.NONE)
    private long count;

    public void increaseCount() {
        count++;
    }

}
