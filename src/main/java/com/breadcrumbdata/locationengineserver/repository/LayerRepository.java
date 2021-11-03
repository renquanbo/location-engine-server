package com.breadcrumbdata.locationengineserver.repository;

import com.breadcrumbdata.locationengineserver.model.Layer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LayerRepository extends JpaRepository<Layer, Integer> {
}
