package com.example.thymeleaf.skierowanie.dao;

import com.example.thymeleaf.skierowanie.model.Miejsce;
import org.springframework.data.repository.CrudRepository;

public interface MiejsceDao extends CrudRepository<Miejsce, Integer> {              //clasa, id
}
