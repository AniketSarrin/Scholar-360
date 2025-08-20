package com.example.demo.repository;

import com.example.demo.model.Posting;
import com.example.demo.model.PostingType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;


import java.util.List;

@Repository
public interface PostingRepository extends JpaRepository<Posting, Long> {
    List<Posting> findByPostingTypeIgnoreCase(String postingType);

    List<Posting> findBySchoolIgnoreCase(String school);

    List<Posting> findByDepartmentIgnoreCase(String department);

    List<Posting> findByPostingTypeAndSchoolIgnoreCaseAndDepartmentIgnoreCase(String postingType, String school, String department);

    List<Posting> findByPostingTypeAndSchoolIgnoreCase(String postingType, String school);

    List<Posting> findByPostingTypeAndDepartmentIgnoreCase(String postingType, String department);

    List<Posting> findBySchoolIgnoreCaseAndDepartmentIgnoreCase(String school, String department);
    @Query("SELECT DISTINCT p.school FROM Posting p")
    List<String> findDistinctSchools();

    @Query("SELECT DISTINCT p.department FROM Posting p")
    List<String> findDistinctDepartments();
    List<Posting> findAllByOrderByIdDesc();




}
