package com.schedulepilot.core.service.imp;

import com.schedulepilot.core.service.QueryService;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.Tuple;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;

@Component
public class QueryServiceImp implements QueryService {

    @Autowired
    private EntityManager entityManager;

    @Override
    @Transactional
    public List<?> getQueryResultList(String query, Map<String, Object> params, Class<?> test) {
        Query queryResult = entityManager.createNativeQuery(query)
                .unwrap(org.hibernate.query.NativeQuery.class)
                .setResultTransformer(Transformers.aliasToBean(test));
        params.forEach((k, v) ->
                queryResult.setParameter(k, v)
        );
        return queryResult.getResultList();
    }

    @Override
    @Transactional
    public Object getQuerySingleResult(String query, Map<String, Object> params, Class<?> test) {
        Query queryResult = entityManager.createNativeQuery(query)
                .unwrap(org.hibernate.query.NativeQuery.class)
                .setResultTransformer(Transformers.aliasToBean(test));
        params.forEach((k, v) ->
                queryResult.setParameter(k, v)
        );
        return queryResult.getSingleResult();
    }

    @Override
    @Transactional
    public List<Tuple> getQueryResultListTuple(String query, Map<String, Object> params) {
        Query queryResult = entityManager.createNativeQuery(query, Tuple.class);
        params.forEach((k, v) ->
                queryResult.setParameter(k, v)
        );
        return queryResult.getResultList();
    }
}
