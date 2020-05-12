package com.bzb.atjob.app.auth.core.repository;

public class DeptSpec extends DeptExample {
    public static class SpecCriteria extends Criteria {
        public SpecCriteria andQuery(String query) {
            if (query == null) {
                throw new RuntimeException("Value for query cannot be null");
            }

            addCriterion(
                    "(T.DEPT_ID like '" + query + "' OR T.NAME like '" + query + "' OR T.CODE like '" + query + "')");
            return (SpecCriteria) this;
        }

        protected SpecCriteria() {
            super();
        }
    }

    //
    // 以下是固定的样板代码
    //
    public SpecCriteria or() {
        SpecCriteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public SpecCriteria createCriteria() {
        SpecCriteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected SpecCriteria createCriteriaInternal() {
        SpecCriteria criteria = new SpecCriteria();
        return criteria;
    }
}