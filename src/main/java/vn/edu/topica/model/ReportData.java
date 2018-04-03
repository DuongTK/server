package vn.edu.topica.model;

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
import vn.edu.topica.constant.ReportType;

@AllArgsConstructor
@Data
@Entity
@NoArgsConstructor
@Table(name = "report_data")//, indexes = {@Index(name = "idx_user_Id", columnList = "user_id")})
public class ReportData extends BaseObject{

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Column(name = "query_key",nullable = false)
  private String queryKey;

  @Column(nullable = false)
  private String name;

  @Column(name = "report_type",nullable = false)
 // @Enumerated(EnumType.STRING)
  private String reportType;

  @Column(name = "file_name", nullable = false, unique = true)
  private String fileName;

  private short status = 0;


}
