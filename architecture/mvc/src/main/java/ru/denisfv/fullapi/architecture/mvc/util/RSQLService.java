package ru.denisfv.fullapi.architecture.mvc.util;

import com.github.tennaito.rsql.jpa.JpaPredicateVisitor;
import com.github.tennaito.rsql.misc.ArgumentParser;
import cz.jirutka.rsql.parser.RSQLParser;
import cz.jirutka.rsql.parser.ast.Node;
import cz.jirutka.rsql.parser.ast.RSQLVisitor;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import ru.denisfv.fullapi.architecture.mvc.entity.abstr.AbstractEntity;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import javax.persistence.metamodel.PluralAttribute;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import static ru.denisfv.fullapi.architecture.mvc.util.Constants.MAX_SIZE_RECORDS;
import static ru.denisfv.fullapi.architecture.mvc.util.QuerySupportService.addSort;
import static ru.denisfv.fullapi.architecture.mvc.util.QuerySupportService.getDeclaredPluralAttributes;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Slf4j
public class RSQLService<T extends AbstractEntity> {

    /*
        | Basic Operator | Description         |
        |----------------|---------------------|
        | ==             | Equal To            |
        | !=             | Not Equal To        |
        | =gt=           | Greater Than        |
        | =ge=           | Greater Or Equal To |
        | =lt=           | Less Than           |
        | =le=           | Less Or Equal To    |
        | =in=           | In                  |
        | =out=          | Not in              |

        | Composite Operator | Description     |
        |--------------------|-----------------|
        | ;                  | Logical AND     |
        | ,                  | Logical OR      |
     */

    @PersistenceContext
    @NonNull EntityManager entityManager;

    /**
     * Returns a list of items according to parameters
     *
     * @param param       Parameter list (start, limit, sort)
     * @param entityClass Entity class
     * @return Returns a list of elements of the Entity class according to parameters
     */
    public List<T> find(final Map<String, Object> param, final Class<T> entityClass) {
        if (log.isDebugEnabled()) {
            log.debug("QueryUtil invoke find");
        }

        if (Objects.isNull(param)) {
            return List.of();
        }

        // Create criteria and from
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(entityClass);
        From<T, T> root = criteriaQuery.from(entityClass);

        // Load related objects if not equal to related = false
        if (!"false".equals(param.get("related"))) {
            Set<PluralAttribute<T, ?, ?>> pluralAttributes = getDeclaredPluralAttributes(entityManager, entityClass);
            pluralAttributes.forEach(pluralAttribute -> root.fetch(pluralAttribute.getName(), JoinType.LEFT));
        }

        if (param.containsKey("search")) {
            generateCriteriaQuery((String) param.get("search"), criteriaQuery, root, entityManager);
        }

        criteriaQuery.select(root);

        if (param.containsKey("sort")) {
            addSort(criteriaQuery, root, criteriaBuilder, ((String) param.get("sort")).split(","));
        }

        TypedQuery<T> query = entityManager.createQuery(criteriaQuery)
                .setFirstResult((Integer) param.getOrDefault("start", 0))
                .setMaxResults((Integer) param.getOrDefault("limit", MAX_SIZE_RECORDS));

        return query.getResultList();
    }

    /**
     * Returns the number of items
     *
     * @param param       Parameters
     * @param entityClass Entity class
     * @return Amount of elements
     */
    public Long count(final Map<String, Object> param, final Class<T> entityClass) {
        if (log.isDebugEnabled()) {
            log.debug("QueryUtil started count");
        }
        if (Objects.isNull(param)) {
            return 0L;
        }

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
        From root = criteriaQuery.from(entityClass);

        criteriaQuery.select(criteriaBuilder.count(root));

        if (param.containsKey("search")) {
            generateCriteriaQuery((String) param.get("search"), criteriaQuery, root, entityManager);
        }

        Query query = entityManager.createQuery(criteriaQuery);

        return (Long) query.getSingleResult();
    }

    /**
     * Add search terms to Criteria Query from string in rsql format - jpa
     *
     * @param search        search line
     * @param criteriaQuery request criterion
     * @param root
     * @param entityManager entity manager
     */
    @SuppressWarnings("unchecked")
    private static <T, R extends From> void generateCriteriaQuery(final String search, final CriteriaQuery<T> criteriaQuery, final R root, final EntityManager entityManager) {
        ArgumentParser argumentParser = new RsqlSupportArgumentParser();
        JpaPredicateVisitor<T> jpaPredicateVisitor = new JpaPredicateVisitor<>();
        jpaPredicateVisitor.getBuilderTools().setArgumentParser(argumentParser);
        // Create the JPA Predicate Visitor
        RSQLVisitor<Predicate, EntityManager> visitor = jpaPredicateVisitor.defineRoot(root);
        // Parse a RSQL into a Node
        Node rootNode = new RSQLParser().parse(search);
        // Visit the node to retrieve CriteriaQuery
        Predicate predicate = rootNode.accept(visitor, entityManager);
        // Use generated predicate as you like
        criteriaQuery.where(predicate);
    }
}
