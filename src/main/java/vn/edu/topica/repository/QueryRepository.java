package vn.edu.topica.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.edu.topica.model.Query;

public interface QueryRepository extends PagingAndSortingRepository<Query,Long> {

  @org.springframework.data.jpa.repository.Query(value = "select query from report_query where report_query.key = :key",nativeQuery = true)
  String findByKey(@Param("key") String key);

  @org.springframework.data.jpa.repository.Query(value = "select * from report_query where length(trim(query)) > 0 ",nativeQuery = true)
  List<Query> findAllQueryNotEmpty();
}
