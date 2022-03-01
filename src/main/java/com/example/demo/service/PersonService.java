package com.example.demo.service;

import com.example.demo.dao.PersonRepo;
import com.example.demo.exceptions.UserNotFoundException;
import com.example.demo.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PersonService {

    private final PersonRepo personRepo;

    @Autowired
    public PersonService(PersonRepo personRepo) {
        this.personRepo = personRepo;
    }

    public Person addPerson(Person person) {
        if (person.getUuId() == null) {
            person.setUuid(UUID.randomUUID());
        }
        return personRepo.save(person);
    }

    public List<Person> getAllPeople() {
        return personRepo.findAll();
    }

    public Person getPersonById(Integer id) {
        return personRepo.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Person with id " + id + " not found."));
    }

    public Person updatePerson(Integer id, Person newPerson) {
        Person person = personRepo.findById(id).get();
        person.setName(newPerson.getName());
        return personRepo.save(person);
    }

    public void deletePerson(Integer id) {
        personRepo.deleteById(id);
    }
}
