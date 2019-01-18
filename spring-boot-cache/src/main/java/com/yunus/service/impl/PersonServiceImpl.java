package com.yunus.service.impl;

import com.google.common.base.Preconditions;
import com.yunus.entity.Person;
import com.yunus.service.PersonService;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;

/**
 * @Author: gaoyunfeng
 * @date: 2019/1/18
 */
@Service
public class PersonServiceImpl implements PersonService {

    private Map<Integer, Person> persons = new HashMap<>();
    private String[] names = {"李四", "王五", "赵六", "朱琪", "罗八", "张酒"};

    @PostConstruct
    public void init() {
        Random random = new Random(100);
        for (int i = 0; i < 15; i++) {
            Person person = new Person();
            person.setAge(String.valueOf(random.nextInt()));
            person.setId(i + 1);
            person.setName(names[i % 5] + i);
            persons.put(i, person);
        }
    }

    @Override
    @Cacheable(value = "persons", key = "#id")
    public Person getById(Integer id) {
        return persons.get(id);
    }

    @Override
    @Cacheable(value = "persons")
    public List<Person> list() {
        List<Person> list = new ArrayList<>();
        Set<Map.Entry<Integer, Person>> entries = persons.entrySet();
        for (Map.Entry<Integer, Person> entry : entries) {
            Person person = entry.getValue();
            list.add(person);
        }
        return list;
    }

    @Override
    @CachePut(value = "persons", key = "#form.id")
    public Person updateById(Person form) {
        Preconditions.checkNotNull(form.getId());
        persons.put(form.getId(), form);
        return form;
    }
}
