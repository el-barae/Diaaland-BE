package com.project.Entity;

import java.time.LocalDate;
import java.util.List;

import com.project.model.DegreesConverter;
import jakarta.persistence.*;

@Entity
public class Jobs {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String name;
    @Column
    private String description;
    @Column
    private double minSalary;
    @Column
    private double maxSalary;
    @Column
    private String type;
    @Column
    private LocalDate openDate;
    @Column
    private LocalDate closeDate;
    @Column
    private int numberOfPositions;
    @Column
    private String address;
    @Column
    private String remoteStatus;

    @Column
    @Convert(converter = DegreesConverter.class)
    private List<String> degrees;
    
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customers customer;
    

    public Jobs(Long id, String name, String description, double minSalary, double maxSalary, String type, LocalDate openDate, LocalDate closeDate, int numberOfPositions, String address, String remoteStatus, Customers customer, List<String> degrees) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.minSalary = minSalary;
        this.maxSalary = maxSalary;
        this.type = type;
        this.openDate = openDate;
        this.closeDate = closeDate;
        this.numberOfPositions = numberOfPositions;
        this.address = address;
        this.remoteStatus = remoteStatus;
        this.degrees = degrees;
        this.customer = customer;
    }

    public Jobs() {
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public double getMinSalary() {
        return minSalary;
    }

    public double getMaxSalary() {
        return maxSalary;
    }

    public String getType() {
        return type;
    }

    public LocalDate getOpenDate() {
        return openDate;
    }

    public LocalDate getCloseDate() {
        return closeDate;
    }

    public int getNumberOfPositions() {
        return numberOfPositions;
    }

    public String getAddress() {
        return address;
    }

    public String isRemoteStatus() {
        return remoteStatus;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setMinSalary(double minSalary) {
        this.minSalary = minSalary;
    }

    public void setMaxSalary(double maxSalary) {
        this.maxSalary = maxSalary;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setOpenDate(LocalDate openDate) {
        this.openDate = openDate;
    }

    public void setCloseDate(LocalDate closeDate) {
        this.closeDate = closeDate;
    }

    public void setNumberOfPositions(int numberOfPositions) {
        this.numberOfPositions = numberOfPositions;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setRemoteStatus(String remoteStatus) {
        this.remoteStatus = remoteStatus;
    }
    
    public Customers getCustomer() {
    	return this.customer;
    }
    
    public void setCustomer(Customers customer) {
    	this.customer = customer;
    }

    public List<String> getDegrees() {
        return degrees;
    }

    public void setDegrees(List<String> degrees) {
        this.degrees = degrees;
    }
    /*@OneToMany(mappedBy = "job")
    private List<CandidatesJobs> candidatesJobs;*/
    
}
