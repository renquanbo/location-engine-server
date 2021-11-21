package com.breadcrumbdata.locationengineserver.repository;

import com.breadcrumbdata.locationengineserver.model.Anchor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnchorRepository extends JpaRepository<Anchor, Integer> {
    Page<Anchor> findAllByLayerId(int layerId, Pageable pageable);
    Boolean existsByName(String name);
    long countByLayerId(int layerId);
}
