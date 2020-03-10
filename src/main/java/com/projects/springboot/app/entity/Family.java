package com.projects.springboot.app.entity;

//import java.util.ArrayList;
//import java.util.List;
import javax.validation.constraints.NotBlank;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "families")
public class Family {

  @Id
  private String familyId;

  @DBRef
  private Parent headOfFamilyParentId;
  
//  private List<FamilyMember> familyMembers = new ArrayList<>();
  
  @NotBlank(message = "{Family.familyName.NotBlank}")
  private String familyName;

  /**
   * This is the default constructor method without id.
   */
  public Family(Parent headOfFamilyParentId, @NotBlank String familyName) {
    super();
    this.headOfFamilyParentId = headOfFamilyParentId;
    this.familyName = familyName;
  }
  
  public String getFamilyId() {
    return familyId;
  }

  public void setFamilyId(String familyId) {
    this.familyId = familyId;
  }

  public Parent getHeadOfFamilyParentId() {
    return headOfFamilyParentId;
  }

  public void setHeadOfFamilyParentId(Parent headOfFamilyParentId) {
    this.headOfFamilyParentId = headOfFamilyParentId;
  }

  public String getFamilyName() {
    return familyName;
  }

  public void setFamilyName(String familyName) {
    this.familyName = familyName;
  }
  
//  public List<FamilyMember> getFamilyMembers() {
//    return familyMembers;
//  }
//
//  public void setFamilyMembers(List<FamilyMember> familyMembers) {
//    this.familyMembers = familyMembers;
//  }

}
