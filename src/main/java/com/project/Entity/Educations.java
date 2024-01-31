package com.project.Entity;
import java.time.LocalDate;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Educations {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String name;
    @Column
    private String school;
    @Column
    private LocalDate startDate;
    @Column
    private LocalDate endDate;
    @Column
    private String diploma;

    public Educations(Long id, String name, String school, LocalDate startDate, LocalDate endDate, String diploma) {
        this.id = id;
        this.name = name;
        this.school = school;
        this.startDate = startDate;
        this.endDate = endDate;
        this.diploma = diploma;
    }

    public Educations() {
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSchool() {
        return school;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public String getDiploma() {
        return diploma;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public void setDiploma(String diploma) {
        this.diploma = diploma;
    }
}
