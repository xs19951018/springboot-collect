package com.my.springbootshiro.domain;

import java.util.ArrayList;
import java.util.List;

public class StudentExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public StudentExample() {
        oredCriteria = new ArrayList<>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andStuUuidIsNull() {
            addCriterion("stu_uuid is null");
            return (Criteria) this;
        }

        public Criteria andStuUuidIsNotNull() {
            addCriterion("stu_uuid is not null");
            return (Criteria) this;
        }

        public Criteria andStuUuidEqualTo(String value) {
            addCriterion("stu_uuid =", value, "stuUuid");
            return (Criteria) this;
        }

        public Criteria andStuUuidNotEqualTo(String value) {
            addCriterion("stu_uuid <>", value, "stuUuid");
            return (Criteria) this;
        }

        public Criteria andStuUuidGreaterThan(String value) {
            addCriterion("stu_uuid >", value, "stuUuid");
            return (Criteria) this;
        }

        public Criteria andStuUuidGreaterThanOrEqualTo(String value) {
            addCriterion("stu_uuid >=", value, "stuUuid");
            return (Criteria) this;
        }

        public Criteria andStuUuidLessThan(String value) {
            addCriterion("stu_uuid <", value, "stuUuid");
            return (Criteria) this;
        }

        public Criteria andStuUuidLessThanOrEqualTo(String value) {
            addCriterion("stu_uuid <=", value, "stuUuid");
            return (Criteria) this;
        }

        public Criteria andStuUuidLike(String value) {
            addCriterion("stu_uuid like", value, "stuUuid");
            return (Criteria) this;
        }

        public Criteria andStuUuidNotLike(String value) {
            addCriterion("stu_uuid not like", value, "stuUuid");
            return (Criteria) this;
        }

        public Criteria andStuUuidIn(List<String> values) {
            addCriterion("stu_uuid in", values, "stuUuid");
            return (Criteria) this;
        }

        public Criteria andStuUuidNotIn(List<String> values) {
            addCriterion("stu_uuid not in", values, "stuUuid");
            return (Criteria) this;
        }

        public Criteria andStuUuidBetween(String value1, String value2) {
            addCriterion("stu_uuid between", value1, value2, "stuUuid");
            return (Criteria) this;
        }

        public Criteria andStuUuidNotBetween(String value1, String value2) {
            addCriterion("stu_uuid not between", value1, value2, "stuUuid");
            return (Criteria) this;
        }

        public Criteria andTeaUuidIsNull() {
            addCriterion("tea_uuid is null");
            return (Criteria) this;
        }

        public Criteria andTeaUuidIsNotNull() {
            addCriterion("tea_uuid is not null");
            return (Criteria) this;
        }

        public Criteria andTeaUuidEqualTo(String value) {
            addCriterion("tea_uuid =", value, "teaUuid");
            return (Criteria) this;
        }

        public Criteria andTeaUuidNotEqualTo(String value) {
            addCriterion("tea_uuid <>", value, "teaUuid");
            return (Criteria) this;
        }

        public Criteria andTeaUuidGreaterThan(String value) {
            addCriterion("tea_uuid >", value, "teaUuid");
            return (Criteria) this;
        }

        public Criteria andTeaUuidGreaterThanOrEqualTo(String value) {
            addCriterion("tea_uuid >=", value, "teaUuid");
            return (Criteria) this;
        }

        public Criteria andTeaUuidLessThan(String value) {
            addCriterion("tea_uuid <", value, "teaUuid");
            return (Criteria) this;
        }

        public Criteria andTeaUuidLessThanOrEqualTo(String value) {
            addCriterion("tea_uuid <=", value, "teaUuid");
            return (Criteria) this;
        }

        public Criteria andTeaUuidLike(String value) {
            addCriterion("tea_uuid like", value, "teaUuid");
            return (Criteria) this;
        }

        public Criteria andTeaUuidNotLike(String value) {
            addCriterion("tea_uuid not like", value, "teaUuid");
            return (Criteria) this;
        }

        public Criteria andTeaUuidIn(List<String> values) {
            addCriterion("tea_uuid in", values, "teaUuid");
            return (Criteria) this;
        }

        public Criteria andTeaUuidNotIn(List<String> values) {
            addCriterion("tea_uuid not in", values, "teaUuid");
            return (Criteria) this;
        }

        public Criteria andTeaUuidBetween(String value1, String value2) {
            addCriterion("tea_uuid between", value1, value2, "teaUuid");
            return (Criteria) this;
        }

        public Criteria andTeaUuidNotBetween(String value1, String value2) {
            addCriterion("tea_uuid not between", value1, value2, "teaUuid");
            return (Criteria) this;
        }

        public Criteria andStuNameIsNull() {
            addCriterion("stu_name is null");
            return (Criteria) this;
        }

        public Criteria andStuNameIsNotNull() {
            addCriterion("stu_name is not null");
            return (Criteria) this;
        }

        public Criteria andStuNameEqualTo(String value) {
            addCriterion("stu_name =", value, "stuName");
            return (Criteria) this;
        }

        public Criteria andStuNameNotEqualTo(String value) {
            addCriterion("stu_name <>", value, "stuName");
            return (Criteria) this;
        }

        public Criteria andStuNameGreaterThan(String value) {
            addCriterion("stu_name >", value, "stuName");
            return (Criteria) this;
        }

        public Criteria andStuNameGreaterThanOrEqualTo(String value) {
            addCriterion("stu_name >=", value, "stuName");
            return (Criteria) this;
        }

        public Criteria andStuNameLessThan(String value) {
            addCriterion("stu_name <", value, "stuName");
            return (Criteria) this;
        }

        public Criteria andStuNameLessThanOrEqualTo(String value) {
            addCriterion("stu_name <=", value, "stuName");
            return (Criteria) this;
        }

        public Criteria andStuNameLike(String value) {
            addCriterion("stu_name like", value, "stuName");
            return (Criteria) this;
        }

        public Criteria andStuNameNotLike(String value) {
            addCriterion("stu_name not like", value, "stuName");
            return (Criteria) this;
        }

        public Criteria andStuNameIn(List<String> values) {
            addCriterion("stu_name in", values, "stuName");
            return (Criteria) this;
        }

        public Criteria andStuNameNotIn(List<String> values) {
            addCriterion("stu_name not in", values, "stuName");
            return (Criteria) this;
        }

        public Criteria andStuNameBetween(String value1, String value2) {
            addCriterion("stu_name between", value1, value2, "stuName");
            return (Criteria) this;
        }

        public Criteria andStuNameNotBetween(String value1, String value2) {
            addCriterion("stu_name not between", value1, value2, "stuName");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {
        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}