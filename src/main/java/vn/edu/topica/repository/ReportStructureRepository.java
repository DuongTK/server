package vn.edu.topica.repository;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.edu.topica.model.ReportStructure;

@Repository
public interface ReportStructureRepository extends CrudRepository<ReportStructure, Long> {
  @Query(value = "select * from report_structure WHERE query_key = :queryKey ORDER BY created_date DESC",nativeQuery = true)
  List<ReportStructure> findByQueryKeyOrderByCreatedDateDesc(@Param("queryKey") String queryKey);
}
