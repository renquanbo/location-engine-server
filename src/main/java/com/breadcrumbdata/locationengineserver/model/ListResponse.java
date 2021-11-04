package com.breadcrumbdata.locationengineserver.model;

public class ListResponse {
    private Long total;
    private Paginator paginator;

    public ListResponse(Long total, Paginator paginator) {
        this.total = total;
        this.paginator = paginator;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public Paginator getPaginator() {
        return paginator;
    }

    public void setPaginator(Paginator paginator) {
        this.paginator = paginator;
    }
}
