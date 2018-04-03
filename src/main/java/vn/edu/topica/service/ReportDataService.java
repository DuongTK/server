package vn.edu.topica.service;

import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;
import vn.edu.topica.model.ReportData;

public interface ReportDataService {

  void saveFile(MultipartFile file);

  Path load(String fileName);

  Resource loadFileAsResource(String fileName);

  void deleteFile(String fileName);

  ReportData findById(Long id);

  void save(ReportData reportData);

  void delete(Long id);

  List<ReportData> findByReportTypeOrderByCreatedDateDesc(String reportType,String queryKey);

}
