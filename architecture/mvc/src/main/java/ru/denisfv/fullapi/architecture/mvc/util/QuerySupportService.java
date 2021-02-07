package ru.denisfv.fullapi.architecture.mvc.util;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Hibernate;
import ru.denisfv.fullapi.architecture.mvc.entity.abstr.AbstractEntity;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import javax.persistence.metamodel.Metamodel;
import javax.persistence.metamodel.PluralAttribute;
import java.util.*;

import static ru.denisfv.fullapi.architecture.mvc.util.Constants.MAX_SIZE_RECORDS;

@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
public class QuerySupportService<T extends AbstractEntity> {

    @PersistenceContext
    @NonNull EntityManager entityManager;

    /**
     * Returns a list of items in the specified sort
     *
     * @param entityClass Entity class
     * @param sortName    Sorting
     * @return Returns a list of elements of the Entity class
     */
    public List<T> findAll(final Class<T> entityClass, final String sortName) {
        if (log.isDebugEnabled()) {
            log.debug(entityClass + " started findAll");
        }

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(entityClass);
        Root<T> variableRoot = criteriaQuery.from(entityClass);
        Set<PluralAttribute<T, ?, ?>> pluralAttributes = getDeclaredPluralAttributes(entityManager, entityClass);

        pluralAttributes.forEach(pluralAttribute -> variableRoot.fetch(pluralAttribute.getName(), JoinType.LEFT));
        criteriaQuery.select(variableRoot);

        if (Objects.nonNull(sortName)) {
            addSort(criteriaQuery, variableRoot, criteriaBuilder, sortName.split(","));
        }

        return entityManager.createQuery(criteriaQuery).setMaxResults(MAX_SIZE_RECORDS).getResultList();
    }

    /**
     * Adds ascending sorting to the query by default (e.g. 'typeName' ascending and 'typeName-' descending)
     *
     * @param criteriaQuery
     * @param variableRoot
     * @param criteriaBuilder
     * @param fieldParam
     */
    public static <T extends From> void addSort(final CriteriaQuery criteriaQuery, final T variableRoot, final CriteriaBuilder criteriaBuilder, final String[] fieldParam) {
        if (fieldParam.length > 0) {
            List<Order> list = new ArrayList<>();

            for (String fieldName : fieldParam) {
                if (fieldName.charAt(fieldName.length() - 1) != '-') {
                    Order order = criteriaBuilder.asc(variableRoot.get(fieldName));
                    list.add(order);
                } else {
                    Order order = criteriaBuilder.desc(variableRoot.get(fieldName.substring(0, fieldName.length() - 1)));
                    list.add(order);
                }
            }
            //multi columns
            criteriaQuery.orderBy(list);
        }
    }

    /**
     * Returns a list of fields with multiple values
     *
     * @return many fields
     */
    public static <T> Set<PluralAttribute<T, ?, ?>> getDeclaredPluralAttributes(final EntityManager entityManager, final Class<T> persistentClass) {
        Metamodel metamodel = entityManager.getMetamodel();
        return metamodel.entity(persistentClass).getDeclaredPluralAttributes();
    }

    /**
     * Initializes the associated object if it has not yet been initialized with a hibernate
     *
     * @param object the object to be initialized
     */
    public static void initRelatedObject(Object object) {
        if (!Hibernate.isInitialized(object)) {
            Hibernate.initialize(object);
        }
    }

    /**
     * Returns a list of search parameters
     *
     * @param start
     * @param limit
     * @param sort
     * @param search
     * @param isActiveFilter
     * @return
     */
    public static Map<String, Object> getQueryParam(final String start, final String limit, final String sort, final String search, final String isActiveFilter) {
        var param = new HashMap<String, Object>();

        Optional.ofNullable(limit).ifPresent(limitElem -> param.put(Param.LIMIT_NAME.getParamName(), Integer.valueOf(limitElem) - 1));
        Optional.ofNullable(start).ifPresent(startElem -> param.put(Param.START_NAME.getParamName(), Integer.valueOf(startElem) - 1));
        Optional.ofNullable(sort).ifPresent(sortElem -> param.put(Param.SORT_NAME.getParamName(), sortElem));
        Optional.ofNullable(search).ifPresent(searchElem -> param.put(Param.SEARCH_NAME.getParamName(), searchElem));

        Optional.ofNullable(isActiveFilter).ifPresent(isActiveFilterElem -> {
            if (!"all".equalsIgnoreCase(isActiveFilterElem)) {
                param.put("isActive", isActiveFilterElem);
            }
        });

        return param;
    }

    @FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
    public enum Param {
        GRAPH_NAME("graph"),
        SEARCH_NAME("search"),
        SORT_NAME("sort"),
        START_NAME("start"),
        LIMIT_NAME("limit"),
        RELATED_NAME("related"),
        IS_VISIBLE_FILTER_NAME("isVisibleFilter"),
        IS_ALL_ROWS("isAllRows"),
        IS_JOIN_SPECIAL_RELATED_NAME("joinSpecialRelated"),
        IS_JOIN_PLURAL_ATTRIBUTES("joinPluralAttributes"),
        IS_DISTINCT("distinct");

        String paramName;

        Param(final String paramName) {
            this.paramName = paramName;
        }

        String getParamName() {
            return this.paramName;
        }
    }
}
