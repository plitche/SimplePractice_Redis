package com.practice.redis.entity;

import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.time.LocalDateTime;

/**
 * author         : yskwon
 * date           : 2023-06-30
 * title          :
 * Level          :
 * isSolved       :
 * url            :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023-06-30        yskwon       최초 생성
 */

@Getter
@RedisHash(value = "people", timeToLive = -1L)
// value: Redis의 keyspace값, timeToLive: 만료시간 seconds단위, 기본값은 만료시간이 없는 -1L
public class Person {

    // @Id: Redis Key값이 되며 null로 세팅하면 랜덤값이 설정됨
    // keyspace와 합쳐져서 Redis에 저장된 최종 키 값은 keyspace:id가 된다.
    @Id
    private String id;
    private String name;
    private Integer age;
    private LocalDateTime regDate;

    public Person(String id, String name, Integer age) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.regDate = LocalDateTime.now();
    }
}
