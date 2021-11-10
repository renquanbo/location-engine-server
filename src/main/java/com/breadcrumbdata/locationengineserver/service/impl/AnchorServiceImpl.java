package com.breadcrumbdata.locationengineserver.service.impl;

import com.breadcrumbdata.locationengineserver.model.Anchor;
import com.breadcrumbdata.locationengineserver.model.dto.AnchorDTO;
import com.breadcrumbdata.locationengineserver.model.vo.AnchorVO;
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
        List<AnchorVO> anchorVOList = new ArrayList<>();
        anchors.stream().forEach(item -> {
            AnchorVO anchorVO = new AnchorVO();
            BeanUtils.copyProperties(item, anchorVO);
            anchorVOList.add(anchorVO);
        });
        return new PageImpl<>(anchorVOList);
    }
}
