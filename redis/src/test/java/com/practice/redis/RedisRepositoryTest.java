package com.practice.redis;

import com.practice.redis.entity.Person;
import com.practice.redis.repository.PersonRedisRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

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
@SpringBootTest
public class RedisRepositoryTest {
    private Person person;

    @Autowired
    private PersonRedisRepository repository;

    @BeforeEach
    void init() {
        person = new Person("test", "Plitche", 30);
    }

    @Test
    void save() {
        // 저장
        Person savePerson = repository.save(person);
        Assertions.assertEquals(person, savePerson);
    }

    @Test
    void find() {
        // 'keyspace:id' 값을 가져옴
        Optional<Person> id = repository.findById(person.getId());
        Assertions.assertEquals(person.getId(), id);
    }

    @Test
    void count() {
        Person person = new Person("test", "Plitche", 30);

        // Person Entity의 @RedisHash에 저의되어 있는 keyspace 에 속한 키의 개수를 구함
        long count = repository.count();
        Assertions.assertEquals(count, 1);
    }

    @Test
    void delete() {
        Person person = new Person("test", "Plitche", 30);
        repository.save(person);

        // 삭제
        repository.delete(person);
        long count = repository.count();
        Assertions.assertEquals(count, 0);
    }
}
