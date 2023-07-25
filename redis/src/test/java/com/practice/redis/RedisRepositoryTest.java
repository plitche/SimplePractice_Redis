package com.practice.redis;

import com.practice.redis.entity.Person;
import com.practice.redis.repository.PersonRedisRepository;
import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Optional;

/**
 * author         : yskwon
 * date           : 2023-06-30
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023-06-30        yskwon       최초 생성
 */
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class RedisRepositoryTest {
    private Person person;
    private static final Logger logger = LoggerFactory.getLogger(RedisRepositoryTest.class);


    @Autowired
    private PersonRedisRepository repository;

    @BeforeEach
    void init() {
        person = new Person("test_id_1", "Plitche", 30);
        logger.debug("init Person Object : {}", this.person);
    }

    @Test
    @Order(1)
    void save() {
        // 저장
        Person savePerson = repository.save(person);
        logger.debug("save Person Object : {}", savePerson);
        Assertions.assertEquals(person, savePerson);
    }

    @Test
    @Order(2)
    void find() {
        // 'keyspace:id' 값을 가져옴
        Optional<Person> findPerson = repository.findById(person.getId());

        Assertions.assertEquals(person.getId(), findPerson.get().getId());
    }

    @Test
    @Order(3)
    void count() {
        Person person = new Person("test_id_1", "Plitche", 30);

        // Person Entity의 @RedisHash에 저의되어 있는 keyspace 에 속한 키의 개수를 구함
        long count = repository.count();
        Assertions.assertEquals(count, 1);
    }

    @Test
    @Order(4)
    void delete() {
        Person person = new Person("test_id_1", "Plitche", 30);
        repository.save(person);

        // 삭제
        repository.delete(person);
        long count = repository.count();
        Assertions.assertEquals(count, 0);
    }
}
