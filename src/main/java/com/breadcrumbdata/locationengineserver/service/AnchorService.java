package com.breadcrumbdata.locationengineserver.service;

import com.breadcrumbdata.locationengineserver.model.dto.AnchorDTO;
import com.breadcrumbdata.locationengineserver.model.vo.AnchorVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AnchorService {
    AnchorVO create(AnchorDTO anchorDTO);
    Boolean delete(Integer id);
    AnchorVO update(Integer id, AnchorDTO anchorDTO);
    AnchorVO get(Integer id);
    Page<AnchorVO> findAll(Pageable pageable);
    Page<AnchorVO> findAllByLayerId(Integer layerId, Pageable pageable);
    Boolean anchorIdOrNameExists(Integer id, String name);
    long totalByLayerId(Integer layerId);
}
