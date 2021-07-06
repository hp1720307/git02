package com.xxxx.crm.query;

import com.xxxx.crm.base.BaseQuery;

public class CusDevPlanQuery extends BaseQuery {
    // 营销机会id
    private Integer sid;

    public CusDevPlanQuery() {
    }

    public Integer getSid() {
        return sid;
    }

    public void setSid(Integer sid) {
        this.sid = sid;
    }

    @Override
    public String toString() {
        return "CusDevPlanQuery{" +
                "sid=" + sid +
                '}';
    }
}
