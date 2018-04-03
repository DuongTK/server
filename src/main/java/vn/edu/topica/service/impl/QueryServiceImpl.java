package vn.edu.topica.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.topica.model.Query;
import vn.edu.topica.repository.QueryRepository;
import vn.edu.topica.service.QueryService;

@Service
public class QueryServiceImpl implements QueryService {

  @Autowired
  private QueryRepository queryRepository;

  @Override
  public String findByKey(String key) {
    return queryRepository.findByKey(key);
  }

  @Override
  public List<Query> findAllQueryNotEmpty(){
    return queryRepository.findAllQueryNotEmpty();
  }
}
