package com.projects.springboot.app.entity;

import java.util.Date;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "students")
public class Student {

  @Id
  private String studentId;

  @NotBlank(message = "{Student.gender.NotBlank}")
  private String gender;

  @NotBlank(message = "{Student.firstName.NotBlank}")
  private String firstName;

  @NotBlank(message = "{Student.middleName.NotBlank}")
  private String middleName;

  @NotBlank(message = "{Student.lastName.NotBlank}")
  private String lastName;

  @NotNull
  private Date dateOfBirth;

  private String otherStudentDetails;

  /**
   * This is the default constructor method without id.
   */
  public Student(@NotBlank String gender, @NotBlank String firstName, @NotBlank String middleName,
      @NotBlank String lastName, @NotNull Date dateOfBirth, String otherStudentDetails) {
    this.gender = gender;
    this.firstName = firstName;
    this.middleName = middleName;
    this.lastName = lastName;
    this.dateOfBirth = dateOfBirth;
    this.otherStudentDetails = otherStudentDetails;
  }

  public String getStudentId() {
    return studentId;
  }

  public void setStudentId(String studentId) {
    this.studentId = studentId;
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

  public Date getDateOfBirth() {
    return dateOfBirth;
  }

  public void setDateOfBirth(Date dateOfBirth) {
    this.dateOfBirth = dateOfBirth;
  }

  public String getOtherStudentDetails() {
    return otherStudentDetails;
  }

  public void setOtherStudentDetails(String otherStudentDetails) {
    this.otherStudentDetails = otherStudentDetails;
  }

}
