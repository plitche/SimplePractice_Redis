package com.practice.redis;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * author         : yskwon
 * date           : 2023-07-24
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023-07-24        yskwon       최초 생성
 */
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class RedisTemplateTest {

    private static final Logger logger = LoggerFactory.getLogger(RedisTemplateTest.class);

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    // 차례대로 Strings, Set, Hash 자료구조에 대한 Operations
    // redisTemplate 을 주입받은 후에 원하는 Key, Value 타입에 맞게 Operations 을 선언해서 사용
    // 가장 흔하게 사용되는 RedisTemplate<String, String> 을 지원하는 StringRedisTemplate 타입도 따로 존재

    @Test
    @Order(1)
    void testStrings() {
        //given
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        String key = "stringKey";

        //when
        valueOperations.set(key, "hello");

        //then
        String value = valueOperations.get(key);
        Assertions.assertThat(value).isEqualTo("hello");
    }

    @Test
    @Order(2)
    void testSet() {
        // given
        SetOperations<String, String> setOperations = redisTemplate.opsForSet();
        String key = "setKey";

        // when
        /*
        setOperations.add(key, "h");
        setOperations.add(key, "e");
        setOperations.add(key, "l");
        setOperations.add(key, "l");
        setOperations.add(key, "o");
        */
        setOperations.add(key, "h", "e", "l", "l", "o");

        // then
        Set<String> members = setOperations.members(key);
        logger.debug("members = {}", Arrays.toString(members.toArray()));
        Long size = setOperations.size(key);

        Assertions.assertThat(members).containsOnly("h", "e", "l", "o");
        Assertions.assertThat(size).isEqualTo(4);
    }

    @Test
    @Order(3)
    void testHash() {
        // given
        HashOperations<String, Object, Object> hashOperations = redisTemplate.opsForHash();
        String key = "hashKey";

        // when
        hashOperations.put(key, "hello", "world");

        // then
        Object value = hashOperations.get(key, "hello");
        Assertions.assertThat(value).isEqualTo("world");

        Map<Object, Object> entries = hashOperations.entries(key);
        Assertions.assertThat(entries.keySet()).containsExactly("hello");
        Assertions.assertThat(entries.values()).containsExactly("world");

        Long size = hashOperations.size(key);
        Assertions.assertThat(size).isEqualTo(entries.size());
    }
}
