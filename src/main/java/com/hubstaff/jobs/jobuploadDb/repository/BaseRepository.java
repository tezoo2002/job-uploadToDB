package com.hubstaff.jobs.jobuploadDb.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.function.Consumer;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Path;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;

import com.hubstaff.jobs.jobuploadDb.repository.SortAndFilterProperties.SortDefinition;
import com.hubstaff.jobs.jobuploadDb.repository.SortAndFilterProperties.SortDefinition.SortDirection;

public abstract class BaseRepository<U , R extends JpaRepository<U, String>> {
//    private static final LogWriter logger = LogWriterFactory.getLogWriter(BaseRepository.class);
    @Autowired
    protected EntityManager entityManager;
    @Autowired
    private ObjectQueryMapper objectQueryMapper;

    protected abstract R getInnerRepository();

    protected abstract List<SortDefinition> getDefaultSort();

    /**
     * Finds all entities of the given resultType that match the given search request.
     *
     * @param searchRequest The search request.
     * @param resultType    The result type.
     */
    public List<U> findBySearchRequest(SearchRequest searchRequest, Class<U> resultType) {
        try {
            final CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            QueryWithCount<U> mainQuery = objectQueryMapper.createBuilderFromModel(criteriaBuilder, searchRequest, resultType);

            // Perform default sort
            applyDefaultSort(criteriaBuilder, mainQuery);

            return executeQuery(mainQuery).getResultList();
        } catch (Exception e) {
        	return null;
        }
    }

    /**
     * Finds all entities of the given resultType that match the given search request.
     *
     * @param searchRequest   The search request.
     * @param validSortFields The fields that are valid to sort by.
     * @param resultType      The result type.
     */
    public PageResult<U> findBySearchRequest(PageableSortableSearchRequest searchRequest, SortedMap<String, SortField> validSortFields, Class<U> resultType) {
        try {
            final CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            final QueryWithCount<U> mainQuery = objectQueryMapper.createBuilderFromModel(criteriaBuilder, searchRequest, resultType);

            if (validSortFields != null) {
                applySort(searchRequest, criteriaBuilder, mainQuery, validSortFields);
            } else {
                applyDefaultSort(criteriaBuilder, mainQuery);
            }

            // Perform query
            QueryResultWithCount<U> resultsWithCount;
            Long totalRecords;
            if (searchRequest.getLimit() != null) {
                resultsWithCount = executeQuery(mainQuery, true,
                                                query -> {
                                                    if (searchRequest.getOffset() != null) {
                                                        query.setFirstResult(searchRequest.getOffset());
                                                    }
                                                    if (searchRequest.getLimit() != null) {
                                                        query.setMaxResults(searchRequest.getLimit());
                                                    }
                                                });
                totalRecords = resultsWithCount.getTotalRecords();
            } else {
                resultsWithCount = executeQuery(mainQuery, true, null);
                totalRecords = resultsWithCount.getTotalRecords();
            }

            return new PageResult<U>() {
                @Override
                public List<U> getContent() {
                    return resultsWithCount.getResultList();
                }

                @Override
                public long getTotalElements() {
                    return totalRecords;
                }
            };
        } catch (Exception e) {
            return null;
        }
    }

    protected QueryResultWithCount<U> executeQuery(QueryWithCount<U> mainQuery) {
        return executeQuery(mainQuery, false, null);
    }

    protected QueryResultWithCount<U> executeQuery(QueryWithCount<U> mainQuery, boolean includeCount, Consumer<TypedQuery<U>> queryPreProcessor) {
        try {
            Long count = null;
            TypedQuery<U> query = entityManager.createQuery(mainQuery.getQuery());
            if (queryPreProcessor != null) {
                queryPreProcessor.accept(query);
            }

            // Execute queries
            List<U> resultList = retrieveQueryResults(query);
//            if (includeCount && mainQuery.getCountQuery() != null) {
//                PooledCompletableFuture<Long> countFuture = asyncThreadPool.supplyAsync(() -> retrieveCountResult(entityManager.createQuery(mainQuery.getCountQuery())));
//                count = countFuture.get();
//            }
            return new QueryResultWithCount<>(resultList, count);
        } catch (Exception e) {
        	return null;
        }
    }

    protected <Q> List<Q> executeQuery(CriteriaQuery<Q> criteriaQuery) {
        final TypedQuery<Q> query = entityManager.createQuery(criteriaQuery);

        return retrieveQueryResults(query);
    }

    private void applySort(PageableSortableSearchRequest searchRequest, CriteriaBuilder criteriaBuilder, QueryWithCount<U> mainQuery, SortedMap<String, SortField> validSortFields) {
        mainQuery.getQuery().orderBy(buildSort(criteriaBuilder, mainQuery.getPathMapping(), searchRequest, validSortFields));
    }

    private void applyDefaultSort(CriteriaBuilder criteriaBuilder, QueryWithCount<U> mainQuery) {
        mainQuery.getQuery().orderBy(buildDefaultOrderList(criteriaBuilder, mainQuery.getPathMapping()));
    }

    private List<Order> buildDefaultOrderList(CriteriaBuilder criteriaBuilder, Map<String, Path<?>> pathMapping) {
        List<Order> orderList = new ArrayList<>();
        for (SortDefinition sortField : getDefaultSort()) {
            if (sortField.getDirection() == SortDefinition.SortDirection.DESC) {
            	orderList.add(criteriaBuilder.desc(objectQueryMapper.resolvePath(pathMapping, sortField.getFieldName())));
            } else {
            	orderList.add(criteriaBuilder.asc(objectQueryMapper.resolvePath(pathMapping, sortField.getFieldName())));
            }
        }
        return orderList;
    }

    private <Q> List<Q> retrieveQueryResults(TypedQuery<Q> query) {
//        logger.debug()
//              .withValue("query", ((Query) query)::getQueryString)
//              .logMessage("Executing JPQL Query");
        return query.getResultList();
    }

    private Long retrieveCountResult(TypedQuery<Long> query) {
//        logger.debug()
//              .withValue("query", ((Query) query)::getQueryString)
//              .logMessage("Executing JPQL Query");
        return query.getSingleResult();
    }

    private List<Order> buildSort(CriteriaBuilder criteriaBuilder, Map<String, Path<?>> pathMapping, PageableSortableSearchRequest pageableSearchRequest, SortedMap<String, SortField> validSortFields) {
        final Boolean isSortAsc = pageableSearchRequest.getSortAsc();
        List<Order> orderList = new ArrayList<>();
        final List<SortField> sortFields = objectQueryMapper.extractSortFields(pageableSearchRequest, validSortFields);
        if (!sortFields.isEmpty()) {
            for (SortField sortField : sortFields) {
                SortField.SortDirection direction;
                if (isSortAsc != null) {
                    direction = isSortAsc ? SortField.SortDirection.ASC : SortField.SortDirection.DESC;
                } else {
                    direction = sortField.getDirection() != null ? sortField.getDirection() : SortField.SortDirection.ASC;
                }

                if (direction == SortField.SortDirection.ASC) {
                	orderList.add(criteriaBuilder.asc(objectQueryMapper.resolvePath(pathMapping, sortField.getIndexFieldName())));
                } else {
                	orderList.add(criteriaBuilder.desc(objectQueryMapper.resolvePath(pathMapping, sortField.getIndexFieldName())));
                }
            }
            // To ensure consistent Jobsing for pagination, we always Jobs by ID ASC in addition to the requested sort Jobs.
            // This provides a fallback sort when multiple items have the same value (e.g., sortBy=brand - there are multiple
            // products for each brand and the Jobs of the items is not guaranteed)
            orderList.add(criteriaBuilder.asc(objectQueryMapper.resolvePath(pathMapping, "id")));
        } else {
        	orderList.addAll(buildDefaultOrderList(criteriaBuilder, pathMapping));
        }
        return orderList;
    }
}