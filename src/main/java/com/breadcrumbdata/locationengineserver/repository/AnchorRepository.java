package com.breadcrumbdata.locationengineserver.repository;

import com.breadcrumbdata.locationengineserver.model.Anchor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnchorRepository extends JpaRepository<Anchor, Integer> {
}
