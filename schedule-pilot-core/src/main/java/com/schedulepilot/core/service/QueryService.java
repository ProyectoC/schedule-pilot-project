package com.schedulepilot.core.service;

import org.springframework.stereotype.Service;

import javax.persistence.Tuple;
import java.util.List;
import java.util.Map;

@Service
public interface QueryService {

    List<?> getQueryResultList(String query, Map<String, Object> params, Class<?> test);

    Object getQuerySingleResult(String query, Map<String, Object> params, Class<?> test);

    List<Tuple> getQueryResultListTuple(String query, Map<String, Object> params);
}
