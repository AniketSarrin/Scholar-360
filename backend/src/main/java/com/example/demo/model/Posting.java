package com.example.demo.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "posting")
public class Posting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String school;
    private String department;

    @Column(name = "posting_type")
    private String postingType;

    private LocalDate date;
    private String link;

    @Column(columnDefinition = "TEXT")
    private String content;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getSchool() { return school; }
    public void setSchool(String school) { this.school = school; }

    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }

    public String getPostingType() { return postingType; }
    public void setPostingType(String postingType) { this.postingType = postingType; }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }

    public String getLink() { return link; }
    public void setLink(String link) { this.link = link; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
}
