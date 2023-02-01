package com.masai.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.masai.model.Entry;

public interface EntryRepository extends JpaRepository<Entry, Integer>{

}
