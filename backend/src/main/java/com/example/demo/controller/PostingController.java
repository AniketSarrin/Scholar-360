package com.example.demo.controller;

import com.example.demo.model.Posting;
import com.example.demo.service.PostingService;
import com.example.demo.repository.PostingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import com.example.demo.service.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;


import java.util.List;

@RestController
@RequestMapping("/api/postings")
public class PostingController {

    @Autowired
    private PostingRepository postingRepository;
    @Autowired
    private PostingService postingService;
    @Autowired
    private JwtService jwtService;
    public PostingController(PostingService postingService, JwtService jwtService) {
        this.postingService = postingService;
        this.jwtService = jwtService;
    }

    @GetMapping
    public List<Posting> getAllPostings() {
        return postingService.getAllPostings();
    }
    @PostMapping("/addPosting")
    public ResponseEntity<Posting> addPosting(@RequestBody Posting posting) {
        Posting savedPosting = postingService.addPosting(posting);
        return new ResponseEntity<>(savedPosting, HttpStatus.CREATED);
    }

    @GetMapping("/type/{type}")
    public List<Posting> getPostingsByType(@PathVariable String type) {
        return postingRepository.findByPostingTypeIgnoreCase(type);
    }
    // New: filter by school (university)
    @GetMapping("/school/{school}")
    public List<Posting> getBySchool(@PathVariable String school) {
        return postingService.findBySchool(school);
    }

    // New: filter by department
    @GetMapping("/department/{department}")
    public List<Posting> getByDepartment(@PathVariable String department) {
        return postingService.findByDepartment(department);
    }

    // Optional: filter by multiple params (postingType + school + department)
    @GetMapping("/filter")
    public List<Posting> getFiltered(
            @RequestParam(required = false) String postingType,
            @RequestParam(required = false) String school,
            @RequestParam(required = false) String department) {
        return postingService.findByFilters(postingType, school, department);
    }
    @GetMapping("/schools")
    public List<String> getSchools() {
        return postingService.getAllDistinctSchools();
    }

    @GetMapping("/departments")
    public List<String> getDepartments() {
        return postingService.getAllDistinctDepartments();
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deletePosting(@RequestHeader("Authorization") String authHeader,
                                           @PathVariable Long id) {
        try {
            String token = authHeader.replace("Bearer ", "");
            Claims claims = jwtService.validateToken(token);
            String role = claims.get("role", String.class);

            if (!"ADMIN".equalsIgnoreCase(role)) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Only admin can delete postings.");
            }

            boolean deleted = postingService.deletePostingById(id);
            if (deleted) {
                return ResponseEntity.ok("Posting deleted successfully.");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Posting not found.");
            }
        } catch (JwtException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid or expired token.");
        }
    }
}
