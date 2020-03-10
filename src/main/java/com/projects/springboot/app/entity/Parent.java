package com.projects.springboot.app.entity;

import java.util.ArrayList;
import java.util.List;
import javax.validation.constraints.NotBlank;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "parents")
public class Parent {

  @Id
  private String parentId;

  @DBRef
  private List<Student> students;
  
  @NotBlank(message = "{Parent.gender.NotBlank}")
  private String gender;

  @NotBlank(message = "{Parent.gender.NotBlank}")
  private String firstName;

  @NotBlank(message = "{Parent.gender.NotBlank}")
  private String middleName;

  @NotBlank(message = "{Parent.gender.NotBlank}")
  private String lastName;

  private String otherParentDetails;
  
  public Parent() {
    students = new ArrayList<>();
  }


  /**
   * This is the default constructor method without id.
   */
  public Parent(@NotBlank String gender, @NotBlank String firstName,
      @NotBlank String middleName, @NotBlank String lastName, String otherParentDetails) {
    this.gender = gender;
    this.firstName = firstName;
    this.middleName = middleName;
    this.lastName = lastName;
    this.otherParentDetails = otherParentDetails;
    this.students = new ArrayList<>();
  }


  public String getParentId() {
    return parentId;
  }


  public void setParentId(String parentId) {
    this.parentId = parentId;
  }


  public List<Student> getStudents() {
    return students;
  }


  public void setStudents(List<Student> students) {
    this.students = students;
  }


  public String getGender() {
    return gender;
  }


  public void setGender(String gender) {
    this.gender = gender;
  }


  public String getFirstName() {
    return firstName;
  }


  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }


  public String getMiddleName() {
    return middleName;
  }


  public void setMiddleName(String middleName) {
    this.middleName = middleName;
  }


  public String getLastName() {
    return lastName;
  }


  public void setLastName(String lastName) {
    this.lastName = lastName;
  }


  public String getOtherParentDetails() {
    return otherParentDetails;
  }


  public void setOtherParentDetails(String otherParentDetails) {
    this.otherParentDetails = otherParentDetails;
  }
  
 
}
