package com.crcc.common.model;

public class ExportConfig {
    private Long id;

    private String type;

    private String title;

    private String fieldName;

    private Byte sort;

    private Byte order;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName == null ? null : fieldName.trim();
    }

    public Byte getSort() {
        return sort;
    }

    public void setSort(Byte sort) {
        this.sort = sort;
    }

    public Byte getOrder() {
        return order;
    }

    public void setOrder(Byte order) {
        this.order = order;
    }
}