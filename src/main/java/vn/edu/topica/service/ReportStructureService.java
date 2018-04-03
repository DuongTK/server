package vn.edu.topica.service;

import java.util.List;
import vn.edu.topica.model.ReportStructure;

public interface ReportStructureService {
   ReportStructure findById(Long id);
   void save(ReportStructure reportStructure);
   List<ReportStructure> findByQueryKeyOrderByCreatedDateDesc(String queryKey);
}
