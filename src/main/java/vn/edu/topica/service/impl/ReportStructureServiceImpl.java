package vn.edu.topica.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.topica.model.ReportStructure;
import vn.edu.topica.repository.ReportStructureRepository;
import vn.edu.topica.service.ReportStructureService;
@Service
public class ReportStructureServiceImpl implements ReportStructureService {

  @Autowired
  private ReportStructureRepository reportStructureRepository;
  @Override
  public ReportStructure findById(Long id){
    return reportStructureRepository.findOne(id);
  }

  @Override
  public  void save(ReportStructure reportStructure){
    reportStructureRepository.save(reportStructure);
  }

  @Override
  public List<ReportStructure>  findByQueryKeyOrderByCreatedDateDesc(String queryKey) {
    return reportStructureRepository.findByQueryKeyOrderByCreatedDateDesc(queryKey);
  }


}
