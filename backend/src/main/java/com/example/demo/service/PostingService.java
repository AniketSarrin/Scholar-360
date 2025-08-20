package com.example.demo.service;

import com.example.demo.model.Posting;
import com.example.demo.model.PostingType;
import com.example.demo.repository.PostingRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PostingService {

    private final PostingRepository repository;

    public PostingService(PostingRepository repository) {
        this.repository = repository;
    }

    public List<Posting> getAll() {
        return repository.findAll();
    }
    public Posting addPosting(Posting posting) {
        return repository.save(posting);
    }

    public List<Posting> getByType(String  type) {
        return repository.findByPostingTypeIgnoreCase(type);
    }

    public Posting save(Posting posting) {
        return repository.save(posting);
    }
    public List<Posting> findBySchool(String school) {
        return repository.findBySchoolIgnoreCase(school);
    }

    public List<Posting> findByDepartment(String department) {
        return repository.findByDepartmentIgnoreCase(department);
    }

    // For combined filtering - you implement based on which params are non-null
    public List<Posting> findByFilters(String postingType, String school, String department) {
        // Example: use JPA Criteria or custom query to support combined filtering

        // Here's a simple example using the repository method for each case:
        if (postingType != null && school != null && department != null) {
            return repository.findByPostingTypeAndSchoolIgnoreCaseAndDepartmentIgnoreCase(postingType, school, department);
        } else if (postingType != null && school != null) {
            return repository.findByPostingTypeAndSchoolIgnoreCase(postingType, school);
        } else if (postingType != null && department != null) {
            return repository.findByPostingTypeAndDepartmentIgnoreCase(postingType, department);
        } else if (school != null && department != null) {
            return repository.findBySchoolIgnoreCaseAndDepartmentIgnoreCase(school, department);
        } else if (postingType != null) {
            return repository.findByPostingTypeIgnoreCase(postingType);
        } else if (school != null) {
            return repository.findBySchoolIgnoreCase(school);
        } else if (department != null) {
            return repository.findByDepartmentIgnoreCase(department);
        } else {
            return repository.findAll();
        }
    }
    // For frontend dropdowns, get distinct schools and departments:
    public List<String> getAllDistinctSchools() {
        return repository.findDistinctSchools();
    }

    public List<String> getAllDistinctDepartments() {
        return repository.findDistinctDepartments();
    }

    public boolean deletePostingById(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }
    public List<Posting> getAllPostings() {
        return repository.findAllByOrderByIdDesc();
    }
}
