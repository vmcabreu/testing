package com.crai.starter.jpa.data.specifications;

import com.crai.starter.jpa.params.SearchParams;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.extern.slf4j.Slf4j;


import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;


import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public abstract class BaseSpecification<T> implements Specification<T> {

  private static final long serialVersionUID = 4284822650712634342L;

  static private final char SORT_DESC_CUSTOM = '-';
  static private final String SORT_DESC = "desc";

  /**
   * Pattern to detect standard Spring pagination with [asc|desc]
   */
  static private final Pattern SORT_PATTERN = Pattern.compile("\\s*((asc)|(desc))\\s*$");

  private final Set<ParamDef> paramDefSet = new HashSet<>();
  private final SearchParams params;

  public BaseSpecification(SearchParams searchParams) {
    super();
    this.params = searchParams == null ? new SearchParams() : searchParams;
    this.paramDefSet.addAll(Arrays.asList(defineParamDefSet()));
  }

  protected abstract ParamDef[] defineParamDefSet();

  @Override
  public Predicate toPredicate(Root<T> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {

    ExpressionBuilder<T> exprBuilder = new ExpressionBuilder<>(params, root);
    Predicate p = exprBuilder.buildPredicate(cb);
    for (ParamDef paramDef : paramDefSet) {
      p = exprBuilder.build(p, cb, paramDef);
    }

    return p;
  }

  public PageRequest getUpdatable() {
    Sort sort = buildSort();
    return PageRequest.of(params.getPage(), params.getPageSize(), sort);
  }

  private Sort buildSort() {
    if (params.getSort().isEmpty()) {
      return Sort.unsorted();
    }
    List<String> sortFields = params.getSort();
    List<Order> orders = new ArrayList<>(sortFields.size());

    if (SORT_PATTERN.matcher(sortFields.get(sortFields.size() - 1)).matches()) {
      String orderField = String.join(",", sortFields);
      sortFields.clear();
      sortFields.add(orderField);
    }

    for (String sortField : sortFields) {
      orders.addAll(buildOrder(sortField));
    }

    log.info("Sort parameters {} => {}", sortFields, orders);
    return Sort.by(orders);
  }

  private List<Order> buildOrder(String sortField) {

    String[] fields = sortField.split(",");
    for (int i = 0; i < fields.length; i++) {
      fields[i] = StringUtils.trimAllWhitespace(fields[i]);
    }

    if (fields.length == 1) {
      return buildCustomOrder(fields);
    }

    Matcher matcher = SORT_PATTERN.matcher(fields[fields.length - 1]);
    if (!matcher.matches()) {
      return buildCustomOrder(fields);
    }

    List<Order> orders = new ArrayList<Order>(fields.length - 1);
    log.info(" Sort.Order: '{}','{}'", fields, matcher.group(1));

    for (int i = 0; i < (fields.length - 1); i++) {
      if (SORT_DESC.equals(matcher.group(1))) {
        orders.add(Order.desc(fields[i]));
      } else {
        orders.add(Order.asc(fields[i]));
      }
    }
    return orders;
  }

  /**
   * Support custom pagination with [+|-] instead of [asc|desc]
   */
  private List<Order> buildCustomOrder(String[] fields) {

    List<Order> orders = new ArrayList<>(fields.length);

    for (String field : fields) {
      String orderField = StringUtils.trimAllWhitespace(field.replaceFirst("\\+|\\-", ""));

      if (SORT_DESC_CUSTOM == field.charAt(0)) {
        orders.add(Order.desc(orderField));
      } else {
        orders.add(Order.asc(orderField));
      }
    }
    return orders;
  }
}
