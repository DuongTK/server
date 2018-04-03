package vn.edu.topica.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.edu.topica.authentication.model.BaseObject;
import vn.edu.topica.constant.CategoryType;

@AllArgsConstructor
@Data
@Entity
@NoArgsConstructor
@Table(name = "report_query")//, indexes = {@Index(name = "idx_key", columnList = "key")})
public class Query extends BaseObject {

  @Id
  @JsonIgnore
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private CategoryType category;

  @Column(nullable = false)
  private String key;

  @Column(nullable = false)
  @JsonIgnore
  private String query;

  @Column(nullable = false)
  private String name;

  @Column(name = "has_date",nullable = false)
  private boolean hasDate = true;

}