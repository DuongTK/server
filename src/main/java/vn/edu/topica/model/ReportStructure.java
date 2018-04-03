package vn.edu.topica.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.edu.topica.authentication.model.BaseObject;

@AllArgsConstructor
@Data
@Entity
@NoArgsConstructor
@Table(name = "report_structure")//, indexes = {@Index(name = "idx_user_Id", columnList = "user_id")})
public class ReportStructure extends BaseObject {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Column(name = "query_key",nullable = false)
  private String queryKey;

  @Column(nullable = false)
  private String name;

  @Column(nullable = false)
  private String data;
}
