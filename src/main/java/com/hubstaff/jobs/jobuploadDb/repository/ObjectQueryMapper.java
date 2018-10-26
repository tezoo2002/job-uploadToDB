package com.hubstaff.jobs.jobuploadDb.repository;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.SortedMap;
import java.util.stream.Collectors;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.From;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

@Component
@SuppressWarnings("squid:S1452")
public class ObjectQueryMapper {
	private static final Logger logger = LoggerFactory.getLogger(ObjectQueryMapper.class);
    private static final String DEFAULT_PATH_NAME = "root";

    /**
     * Extracts the sort fields from the pageableSearchRequest for all validSortFields.
     *
     * @param pageableSearchRequest The search request object from which to extract sort fields.
     * @param validSortFields       The sort fields for which we have metadata such as the field name in the index.
     * @return The list of {@link SortField} objects extracted from the search request.
     */
    public <T extends PageableSortableSearchRequest> List<SortField> extractSortFields(T pageableSearchRequest, SortedMap<String, SortField> validSortFields) {
        List<String> sortByFields = pageableSearchRequest.getSortBy();

        if ((sortByFields == null || sortByFields.isEmpty()) || validSortFields == null || validSortFields.isEmpty()) {
            return Collections.emptyList();
        }
        return sortByFields.stream()
                           .map(validSortFields::get)
                           .filter(Objects::nonNull)
                           .collect(Collectors.toList());
    }

    /**
     * Extracts the search fields from the given modelClass by finding {@link SearchField} annotated fields.
     *
     * @param modelClass The {@link SearchRequest} implementing class.
     * @return The list of fields and related {@link SearchField} metadata.
     */
    public <T extends SearchRequest> List<ImmutablePair<Field, SearchField>> extractSearchFields(Class<T> modelClass) {
        if (modelClass != null) {
            List<Field> annotatedFields = new ArrayList<>();
            ReflectionUtils.doWithFields(modelClass, field -> {
                if (field.isAnnotationPresent(SearchField.class)) {
                    annotatedFields.add(field);
                }
            });

            return annotatedFields.stream().map(x -> ImmutablePair.of(x, AnnotatedElementUtils.findMergedAnnotation(x, SearchField.class))).collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    /**
     * Extracts the current values for all fields annotated with {@link SearchField}.
     * This allows us to provide the filters that will be applied to the search.
     *
     * @param model The model containing the values.
     * @return The {@link SearchField} metadata and associated search value.
     */
    @SuppressWarnings("unchecked")
    public <T extends SearchRequest> List<ImmutablePair<SearchField, Object>> extractFilterValuesFromModel(T model) {
        List<ImmutablePair<SearchField, Object>> fieldList = new ArrayList<>();
        if (model == null) {
            return fieldList;
        }
        extractSearchFields(model.getClass())
                .stream()
                .filter(pair -> StringUtils.isNotEmpty(pair.getRight().fieldName()))
                .forEach(pair -> {
                    try {
                        Field annotatedField = pair.getLeft();
                        SearchField fieldAnnotation = pair.getRight();
                        annotatedField.setAccessible(true);
                        final Object searchValue = annotatedField.get(model);
                        if (searchValue != null) {
                            fieldList.add(ImmutablePair.of(fieldAnnotation, searchValue));
                        }
                    } catch (IllegalAccessException e) {
                        logger.error("Error executing extractFilterValuesFromModel", e);
                    }
                });

        return fieldList;
    }

    public <T extends SearchRequest, R> QueryWithCount<R> createBuilderFromModel(CriteriaBuilder builder, T model, Class<R> resultClass) {
        return createBuilderFromModel(builder, model, resultClass, null);
    }

    public <T extends SearchRequest, R> QueryWithCount<R> createBuilderFromModel(CriteriaBuilder builder, T model, Class<R> resultClass, java.util.function.Predicate<SearchField> searchFieldFilter) {
        CriteriaQuery<R> query = builder.createQuery(resultClass);
        CriteriaQuery<Long> countQuery = builder.createQuery(Long.class);
        From<R, R> root = query.from(resultClass);
        From<R, R> countRoot = countQuery.from(resultClass);

        Map<String, Path<?>> pathMap = new HashMap<>();
        Map<String, Path<?>> countPathMap = new HashMap<>();
        pathMap.put(DEFAULT_PATH_NAME, root);
        countPathMap.put(DEFAULT_PATH_NAME, root);

        // Select
        query.select(root);
        countQuery.select(builder.countDistinct(root));
        if (model == null) {
            return new QueryWithCount<>(query, countQuery, pathMap);
        }

        // Add joins
        final List<ImmutablePair<SearchField, Object>> applicableSearchFields = extractFilterValuesFromModel(model).stream().filter(pair -> searchFieldFilter == null || searchFieldFilter.test(pair.left)).collect(Collectors.toList());
        Set<String> joinProperties = applicableSearchFields.stream()
                                                           .filter(x -> x.left.fieldName().contains("."))
                                                           .map(x -> StringUtils.split(x.left.fieldName(), ".")[0])
                                                           .collect(Collectors.toSet());
        
        for (String joinProperty : joinProperties) {
            pathMap.put(joinProperty, root.join(joinProperty));
            countPathMap.put(joinProperty, countRoot.join(joinProperty));
        }

        // Add search criteria
        ArrayList<javax.persistence.criteria.Predicate> wherePredicates = new ArrayList<>();
        for (ImmutablePair<SearchField, Object> pair : applicableSearchFields) {
            Object value = pair.getRight();
            if (value instanceof Collection<?>) {
                ArrayList<javax.persistence.criteria.Predicate> predicates = new ArrayList<>();
                for (Object item : (Collection<?>) value) {
                    Predicate currentPredicate = builder.equal(resolvePath(pathMap, pair.getLeft().fieldName()), item);
                    predicates.add(currentPredicate);
                }
                if(((Collection<?>) value).size() > 0)
                	wherePredicates.add(builder.or(predicates.toArray(new Predicate[0])));
            } else {
                Object searchValue = value;
                if (value instanceof Collection<?>) {
                    searchValue = ((Collection<?>) value).iterator().next();
                }
                if (pair.getLeft().fieldName().contains("keyword")) {
                	wherePredicates.add(builder.or(builder.like(root.get("title"), "%"+ value +"%")
                			, builder.like(root.get("availability"), "%"+ value +"%")
                			, builder.like(root.get("description"), "%"+ value +"%")
                			, builder.like(root.get("company"), "%"+ value +"%")
                			, builder.like(root.get("location"), "%"+ value +"%")
                			));
                }else if (pair.getLeft().fieldName().contains("after")) {
                	if((Long) value>0)
                		wherePredicates.add(builder.greaterThanOrEqualTo(root.get("createdDate"), (Comparable) value));
                }
                else if (pair.getLeft().fieldName().contains("before")) {
                	if((Long) value>0)
                		wherePredicates.add(builder.lessThan(root.get("createdDate"), (Comparable) value));
                }else {
                	wherePredicates.add(builder.equal(resolvePath(pathMap, pair.getLeft().fieldName()), searchValue));
                }
                
            }
        }
        query.where(builder.and(wherePredicates.toArray(new Predicate[0])));
        countQuery.where(builder.and(wherePredicates.toArray(new Predicate[0])));

        return new QueryWithCount<>(query, countQuery, pathMap);
    }

    public Path<?> resolvePath(Map<String, Path<?>> pathMap, String fieldName) {
        String[] pathParts = StringUtils.split(fieldName, ".");
        if (pathParts.length > 1) {
            Path path = pathMap.get(pathParts[0]);
            for (int i = 1; i < pathParts.length; i++) {
                path = path.get(pathParts[i]);
            }
            return path;
        }
        return pathMap.get(DEFAULT_PATH_NAME).get(fieldName);
    }
}