package com.briciasantana.myfinances.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.briciasantana.myfinances.model.entity.BookEntry;

public interface BookEntryRepository extends JpaRepository<BookEntry, Long>{

}
