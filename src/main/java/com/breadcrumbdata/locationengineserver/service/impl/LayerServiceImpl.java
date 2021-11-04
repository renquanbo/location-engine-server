package com.breadcrumbdata.locationengineserver.service.impl;

import com.breadcrumbdata.locationengineserver.config.exceptions.LayerIdNotFoundException;
import com.breadcrumbdata.locationengineserver.model.Layer;
import com.breadcrumbdata.locationengineserver.model.dto.LayerDTO;
import com.breadcrumbdata.locationengineserver.model.vo.LayerVO;
import com.breadcrumbdata.locationengineserver.repository.LayerRepository;
import com.breadcrumbdata.locationengineserver.service.LayerService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class LayerServiceImpl implements LayerService {

    private final LayerRepository layerRepository;

    @Autowired
    public LayerServiceImpl(LayerRepository layerRepository) {
        this.layerRepository = layerRepository;
    }

    @Override
    public LayerVO create(LayerDTO layerDTO) throws IOException {
        Layer layer = new Layer();
        BeanUtils.copyProperties(layerDTO,layer);
        Layer result = layerRepository.save(layer);
        LayerVO layerVO = new LayerVO();
        layerVO.setId(result.getId());
        BeanUtils.copyProperties(result,layerVO);
        return layerVO;
    }

    @Override
    public LayerVO update(Integer id, LayerDTO layerDTO) {
        Layer layer = new Layer();
        BeanUtils.copyProperties(layerDTO, layer);
        layer.setId(id);
        Layer result = layerRepository.save(layer);
        LayerVO layerVO = new LayerVO();
        layerVO.setId(result.getId());
        BeanUtils.copyProperties(result,layerVO);
        return layerVO;
    }

    @Override
    public LayerVO get(Integer id) {
        Layer layer = layerRepository.getById(id);
        LayerVO layerVO = new LayerVO();
        BeanUtils.copyProperties(layer,layerVO);
        return layerVO;
    }

    @Override
    public Page<LayerVO> findAll(Pageable pageable) {
        Page<Layer> layers = layerRepository.findAll(pageable);
        List<LayerVO> layerVOList = new ArrayList<>();
        layers.stream().forEach(item -> {
            LayerVO layerVO = new LayerVO();
            BeanUtils.copyProperties(item, layerVO);
            layerVOList.add(layerVO);
        });
        return new PageImpl<LayerVO>(layerVOList);
    }

    @Override
    public Boolean delete(Integer id) {
        boolean exists = layerRepository.existsById(id);
        if(exists) {
            layerRepository.deleteById(id);
            return true;
        } else {
            throw new LayerIdNotFoundException("The layer cannot be deleted, because the layer cannot be found");
        }
    }
}
