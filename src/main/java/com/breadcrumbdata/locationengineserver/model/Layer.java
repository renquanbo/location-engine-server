package com.breadcrumbdata.locationengineserver.model;

import javax.persistence.*;

@Entity
public class Layer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true, nullable = false)
    private String name;

    @Column(nullable = false)
    private Integer layerNumber;

    @Column(nullable = false)
    private Integer realLength;

    @Column(nullable = false)
    private Integer realWidth;

    @Column(nullable = false)
    private Integer X0;

    @Column(nullable = false)
    private Integer Y0;

    @Lob
    private String floorPlan;

    private String description;

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

    public Integer getLayerNumber() {
        return layerNumber;
    }

    public void setLayerNumber(Integer layerNumber) {
        this.layerNumber = layerNumber;
    }

    public Integer getRealLength() {
        return realLength;
    }

    public void setRealLength(Integer realLength) {
        this.realLength = realLength;
    }

    public Integer getRealWidth() {
        return realWidth;
    }

    public void setRealWidth(Integer realWidth) {
        this.realWidth = realWidth;
    }

    public Integer getX0() {
        return X0;
    }

    public void setX0(Integer x0) {
        X0 = x0;
    }

    public Integer getY0() {
        return Y0;
    }

    public void setY0(Integer y0) {
        Y0 = y0;
    }

    public String getFloorPlan() {
        return floorPlan;
    }

    public void setFloorPlan(String floorPlan) {
        this.floorPlan = floorPlan;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
