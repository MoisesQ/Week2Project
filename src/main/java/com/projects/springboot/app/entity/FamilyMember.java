package com.projects.springboot.app.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javax.validation.constraints.NotBlank;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "familymembers")
public class FamilyMember {

  @Id
  private String familyMemberId;

  @JsonIgnoreProperties("familyMembers")
  @DBRef
  private Family familyId;

  @NotBlank(message="{FamilyMember.parentOrStudentMember.NotBlank}")
  private String parentOrStudentMember;

  @DBRef
  private Parent parentId;

  @DBRef
  private Student  studentId;

  
  /**
   * This is the default constructor method without id.
   */
  public FamilyMember(Family familyId, @NotBlank String parentOrStudentMember, 
      Parent parentId, Student studentId) {
    super();
    this.familyId = familyId;
    this.parentOrStudentMember = parentOrStudentMember;
    this.parentId = parentId;
    this.studentId = studentId;
  }


  public String getFamilyMemberId() {
    return familyMemberId;
  }


  public void setFamilyMemberId(String familyMemberId) {
    this.familyMemberId = familyMemberId;
  }


  public Family getFamilyId() {
    return familyId;
  }


  public void setFamilyId(Family familyId) {
    this.familyId = familyId;
  }


  public String getParentOrStudentMember() {
    return parentOrStudentMember;
  }


  public void setParentOrStudentMember(String parentOrStudentMember) {
    this.parentOrStudentMember = parentOrStudentMember;
  }


  public Parent getParentId() {
    return parentId;
  }


  public void setParentId(Parent parentId) {
    this.parentId = parentId;
  }


  public Student getStudentId() {
    return studentId;
  }


  public void setStudentId(Student studentId) {
    this.studentId = studentId;
  }
 

}
