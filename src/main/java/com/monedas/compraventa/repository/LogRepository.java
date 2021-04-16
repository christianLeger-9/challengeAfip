package com.monedas.compraventa.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.monedas.compraventa.entity.Log;

@Repository
public interface LogRepository extends CrudRepository<Log, Long> {
}