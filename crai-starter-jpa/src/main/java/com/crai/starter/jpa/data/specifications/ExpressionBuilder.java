package com.crai.starter.jpa.data.specifications;

import com.crai.starter.jpa.exception.GeneralException;
import com.crai.starter.jpa.params.SearchParams;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.extern.slf4j.Slf4j;


import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.util.StringUtils;


import java.lang.reflect.InvocationTargetException;
import java.time.Instant;
import java.util.Date;
import java.util.List;

@Slf4j
public class ExpressionBuilder<T> {

  private static final String LIKE_WILDCARD = "%";
  private static final String SEARCH_MODE_OR = "OR";

  private Root<T> root;
  private SearchParams params;

  public ExpressionBuilder(SearchParams params, Root<T> root) {
    this.params = params;
    this.root = root;
  }

  public Predicate build(Predicate p, CriteriaBuilder cb, ParamDef paramDef) {

    Path<T> tt = root.get(paramDef.getDbField());
    Class<?> javaType = tt.getJavaType();

    if (!classCompatibleWithOperator(javaType, paramDef.getOperator())) {
      throw new IllegalArgumentException("Operator incompatible with field");
    }
    switch (paramDef.getOperator()) {
      case EQUALS:
        return this.buildEqualsExpression(p, cb, paramDef);
      case GTE:
        return this.buildGreaterEqualsExpression(p, cb, paramDef);
      case IN:
        return this.buildInExpression(p, cb, paramDef);
      case LTE:
        return this.buildLessEqualsExpression(p, cb, paramDef);
      case LT:
        return this.buildLessExpression(p, cb, paramDef);
      case GT:
        return this.buildGreaterExpression(p, cb, paramDef);
      case IS_NULL:
        return this.buildIsNullExpression(p, cb, paramDef);
      case IS_NOTNULL:
        return this.buildIsNotNullExpression(p, cb, paramDef);
      case NOT_EQUALS:
        return this.buildNotEqualsExpression(p, cb, paramDef);
      case NOT_LIKE:
        return this.buildNotLikeExpression(p, cb, paramDef);
      case LIKE:
      default:
        return this.buildLikeExpression(p, cb, paramDef);
    }
  }

  private boolean classCompatibleWithOperator(Class<?> clazz, ParamOperators operator) {
    if (operator == null) {
      return true;
    } else {
      switch (operator) {
        case NOT_EQUALS:
        case EQUALS:
        case IN:
        case IS_NULL:
        case IS_NOTNULL:
          return true;
        case GT:
        case GTE:
        case LT:
        case LTE:
          return (Date.class.isAssignableFrom(clazz)
            || Instant.class.isAssignableFrom(clazz)
            || (clazz.isPrimitive() && !clazz.equals(boolean.class))
            || Number.class.isAssignableFrom(clazz));
        case LIKE:
        case NOT_LIKE:
          return String.class.equals(clazz);
        default:
          return false;
      }
    }
  }

  private Predicate buildLikeExpression(Predicate p, CriteriaBuilder cb, ParamDef paramDef) {
    String value = getValue(paramDef.getName());

    if (StringUtils.isEmpty(value)) {
//      if (!StringUtils.isEmpty(value))
      return p;
//      p.getExpressions()
//        .add(cb.like(cb.lower(root.get(paramDef.getDbField())),
//          buildLikeValue(value)));

    }
    return this.buildPredicate(cb,p,cb.like(cb.lower(root.get(paramDef.getDbField())),
            buildLikeValue(value)));
  }

  private Predicate buildNotLikeExpression(Predicate p, CriteriaBuilder cb, ParamDef paramDef) {
    String value = getValue(paramDef.getName());

    if (StringUtils.isEmpty(value)) {
      return p;
    }

    Predicate notLike = cb.conjunction();
    notLike.getExpressions()
      .add(cb.notLike(cb.lower(root.get(paramDef.getDbField())),
        buildLikeValue(paramDef.getName())));

    return buildPredicate(cb, p, notLike);
  }

  private Predicate buildInExpression(Predicate p, CriteriaBuilder cb, ParamDef paramDef) {
    List<?> values = getValue(paramDef.getName());

    if (values != null && !values.isEmpty()) {
//      p.getExpressions()
//        .add(root.get(paramDef.getDbField()).in(values));
      return this.buildPredicate(cb,p,root.get(paramDef.getDbField()).in(values));
    }

    return p;
  }

  private Predicate buildIsNullExpression(Predicate p, CriteriaBuilder cb, ParamDef paramDef) {

//    p.getExpressions()
//      .add(cb.isNull(root.get(paramDef.getDbField())));
//
//    return p;
    return this.buildPredicate(cb,p,cb.isNull(root.get(paramDef.getDbField())));
  }

  private Predicate buildIsNotNullExpression(Predicate p, CriteriaBuilder cb, ParamDef paramDef) {
//    p.getExpressions()
//      .add(cb.isNotNull(root.get(paramDef.getDbField())));
    return this.buildPredicate(cb,p,cb.isNotNull(root.get(paramDef.getDbField())));

//    return p;
  }

  private Predicate buildEqualsExpression(Predicate p, CriteriaBuilder cb, ParamDef paramDef) {
    Object value = getValue(paramDef.getName());

    if (value != null) {
//      p.getExpressions()
//        .add(cb.equal(root.get(paramDef.getDbField()), value));
      return this.buildPredicate(cb,p,cb.equal(root.get(paramDef.getDbField()), value));
    }
    return p;
  }

  private Predicate buildNotEqualsExpression(Predicate p, CriteriaBuilder cb, ParamDef paramDef) {
    Object value = getValue(paramDef.getName());

    if (value == null) {
      return p;
    }

    Predicate nep = buildPredicate(cb,p,cb.conjunction());
//    nep.getExpressions()
//      .add(cb.notEqual(root.get(paramDef.getDbField()), value));

    return buildPredicate(cb, nep, cb.notEqual(root.get(paramDef.getDbField()), value));
  }

  @SuppressWarnings("unchecked")
  private Predicate buildGreaterExpression(Predicate p, CriteriaBuilder cb, ParamDef paramDef) {
    Comparable value = getValue(paramDef.getName());
    if (value != null) {
//      p.getExpressions()
//        .add(cb.greaterThan(root.get(paramDef.getDbField()), value));
      return this.buildPredicate(cb,p,cb.greaterThan(root.get(paramDef.getDbField()), value));
    }
    return p;
  }

  @SuppressWarnings("unchecked")
  private Predicate buildLessExpression(Predicate p, CriteriaBuilder cb, ParamDef paramDef) {
    Comparable value = getValue(paramDef.getName());
    if (value != null) {
//      p.getExpressions()
//        .add(cb.lessThan(root.get(paramDef.getDbField()), value));
      return this.buildPredicate(cb,p,cb.lessThan(root.get(paramDef.getDbField()), value));
    }
    return p;
  }

  @SuppressWarnings("unchecked")
  private Predicate buildGreaterEqualsExpression(Predicate p, CriteriaBuilder cb, ParamDef paramDef) {
    Comparable value = getValue(paramDef.getName());
    if (value != null) {
//      p.getExpressions()
//        .add(cb.greaterThanOrEqualTo(root.get(paramDef.getDbField()), value));
      return this.buildPredicate(cb,p,cb.greaterThanOrEqualTo(root.get(paramDef.getDbField()), value));
    }
    return p;
  }

  @SuppressWarnings("unchecked")
  private Predicate buildLessEqualsExpression(Predicate p, CriteriaBuilder cb, ParamDef paramDef) {
    Comparable value = getValue(paramDef.getName());
    if (value != null) {
//      p.getExpressions()
//        .add(cb.lessThanOrEqualTo(root.get(paramDef.getDbField()), value));
      return this.buildPredicate(cb,p,cb.lessThanOrEqualTo(root.get(paramDef.getDbField()), value));
    }
    return p;

  }

  public Predicate buildPredicate(CriteriaBuilder cb) {

    // Por defecto, el modo de búsqueda es AND
    if (SEARCH_MODE_OR.equalsIgnoreCase(params.getSearchMode())) {
      return cb.disjunction();
    } else {
      return cb.conjunction();
    }
  }

  public Predicate buildPredicate(CriteriaBuilder cb, Predicate predicate1, Predicate predicate2) {

    // Por defecto, el modo de búsqueda es AND
    if (SEARCH_MODE_OR.equalsIgnoreCase(params.getSearchMode())) {
      return cb.or(predicate1, predicate2);
    } else {
      return cb.and(predicate1, predicate2);
    }
  }

  private String buildLikeValue(String value) {

    return new StringBuilder(LIKE_WILDCARD)
      .append(value)
      .append(LIKE_WILDCARD)
      .toString()
      .toLowerCase();
  }

  @SuppressWarnings("unchecked")
  private <Y> Y getValue(String name) {

    try {
      return (Y) PropertyUtils.getProperty(this.params, name);
    } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
      log.error("Error accessing param value: " + name, e);
      throw new GeneralException(e);
    }
  }
}
