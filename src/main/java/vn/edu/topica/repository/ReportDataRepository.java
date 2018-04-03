package vn.edu.topica.repository;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.edu.topica.model.ReportData;

@Repository
public interface ReportDataRepository extends PagingAndSortingRepository<ReportData, Long> {

  @Query(value = "select * from report_data WHERE report_type = :reportType AND query_key = :queryKey AND status = 0 ORDER BY created_date DESC",nativeQuery = true)
  List<ReportData> findByReportTypeOrderByCreatedDateDesc(@Param("reportType") String reportType,@Param("queryKey") String queryKey);
}
