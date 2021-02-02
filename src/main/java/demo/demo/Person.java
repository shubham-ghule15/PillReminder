package demo.demo;

import java.sql.Date;

public class Person {
    private String name, email, bloodGroup, password;
    private long contactNo;
    private Date DOB;
    private float weight, height;

    public Person(String name, String email, String bloodGroup, long contactNo, Date DOB, float weight, float height) {
        this.name = name;
        this.email = email;
        this.bloodGroup = bloodGroup;
        this.contactNo = contactNo;
        this.DOB = DOB;
        this.weight = weight;
        this.height = height;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBloodGroup() {
        return this.bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public long getContactNo() {
        return this.contactNo;
    }

    public void setContactNo(long contactNo) {
        this.contactNo = contactNo;
    }

    public Date getDOB() {
        return this.DOB;
    }

    public void setDOB(Date DOB) {
        this.DOB = DOB;
    }

    public float getWeight() {
        return this.weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public float getHeight() {
        return this.height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

}
