package com.practice.redis.repository;

import com.practice.redis.entity.Person;
import org.springframework.data.repository.CrudRepository;

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
public interface PersonRedisRepository extends CrudRepository<Person, String> {

}
