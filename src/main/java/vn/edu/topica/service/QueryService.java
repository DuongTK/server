package vn.edu.topica.service;

import java.util.List;

import vn.edu.topica.model.Query;

public interface QueryService {
	String findByKey(String key);
	List<Query> findAllQueryNotEmpty();
}
