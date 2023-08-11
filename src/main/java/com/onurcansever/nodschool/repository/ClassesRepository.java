package com.onurcansever.nodschool.repository;

import com.onurcansever.nodschool.model.SchoolClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClassesRepository extends JpaRepository<SchoolClass, Integer> {

}
