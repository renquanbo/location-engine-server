package com.breadcrumbdata.locationengineserver.model.vo;

public class AnchorVO {
    private Integer id;

    private String name;

    private Integer x;

    private Integer y;

    private Integer height;

    private Integer anchorGroup;

    private Integer layer;

    private Integer batteryPercentage;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getX() {
        return x;
    }

    public void setX(Integer x) {
        this.x = x;
    }

    public Integer getY() {
        return y;
    }

    public void setY(Integer y) {
        this.y = y;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Integer getAnchorGroup() {
        return anchorGroup;
    }

    public void setAnchorGroup(Integer anchorGroup) {
        this.anchorGroup = anchorGroup;
    }

    public Integer getLayer() {
        return layer;
    }

    public void setLayer(Integer layer) {
        this.layer = layer;
    }

    public Integer getBatteryPercentage() {
        return batteryPercentage;
    }

    public void setBatteryPercentage(Integer batteryPercentage) {
        this.batteryPercentage = batteryPercentage;
    }
}
