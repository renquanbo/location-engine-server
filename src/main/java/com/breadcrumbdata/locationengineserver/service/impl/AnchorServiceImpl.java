package com.breadcrumbdata.locationengineserver.service.impl;

import com.breadcrumbdata.locationengineserver.model.Anchor;
import com.breadcrumbdata.locationengineserver.model.dto.AnchorDTO;
import com.breadcrumbdata.locationengineserver.model.vo.AnchorVO;
import com.breadcrumbdata.locationengineserver.model.vo.LayerVO;
import com.breadcrumbdata.locationengineserver.repository.AnchorRepository;
import com.breadcrumbdata.locationengineserver.service.AnchorService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class AnchorServiceImpl implements AnchorService {

    private final AnchorRepository anchorRepository;

    @Autowired
    public AnchorServiceImpl(AnchorRepository anchorRepository) {
        this.anchorRepository = anchorRepository;
    }

    @Override
    public AnchorVO create(AnchorDTO anchorDTO) {
        Anchor anchor = new Anchor();
        anchor.setBatteryPercentage(100);
        BeanUtils.copyProperties(anchorDTO, anchor);
        Anchor result = anchorRepository.save(anchor);
        AnchorVO anchorVO = new AnchorVO();
        BeanUtils.copyProperties(result, anchorVO);
        return anchorVO;
    }

    @Override
    public Boolean delete(Integer id) {
        return null;
    }

    @Override
    public AnchorVO update(Integer id, AnchorDTO anchorDTO) {
        return null;
    }

    @Override
    public AnchorVO get(Integer id) {
        Anchor anchor = anchorRepository.getById(id);
        AnchorVO anchorVO = new AnchorVO();
        BeanUtils.copyProperties(anchor, anchorVO);
        return anchorVO;
    }

    @Override
    public Page<AnchorVO> findAll(Pageable pageable) {
        Page<Anchor> anchors = anchorRepository.findAll(pageable);
        return getAnchorVOS(anchors);
    }

    @Override
    public Page<AnchorVO> findAllByLayerId(Integer layerId, Pageable pageable) {
        Page<Anchor> anchors = anchorRepository.findAllByLayerId(layerId, pageable);
        return getAnchorVOS(anchors);
    }

    private Page<AnchorVO> getAnchorVOS(Page<Anchor> anchors) {
        Random rand = new Random();
        List<AnchorVO> anchorVOList = new ArrayList<>();
        anchors.stream().forEach(item -> {
            AnchorVO anchorVO = new AnchorVO();
            BeanUtils.copyProperties(item, anchorVO);
            anchorVO.setBatteryPercentage(rand.nextInt(100));
            anchorVOList.add(anchorVO);
        });
        return new PageImpl<>(anchorVOList);
    }

    @Override
    public Boolean anchorIdOrNameExists(Integer id, String name) {
        if(anchorRepository.existsById(id)) {
            return true;
        }
        return anchorRepository.existsByName(name);
    }

    @Override
    public long totalByLayerId(Integer layerId) {
        return anchorRepository.countByLayerId(layerId);
    }
}
