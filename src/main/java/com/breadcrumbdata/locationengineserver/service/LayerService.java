package com.breadcrumbdata.locationengineserver.service;

import com.breadcrumbdata.locationengineserver.model.dto.LayerDTO;
import com.breadcrumbdata.locationengineserver.model.vo.LayerVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.io.IOException;

public interface LayerService {
    LayerVO create(LayerDTO layerDTO) throws IOException;
    LayerVO update(Integer id, LayerDTO layerDTO);
    LayerVO get(Integer id);
    Page<LayerVO> findAll(Pageable pageable);
    Boolean delete(Integer id);
}
