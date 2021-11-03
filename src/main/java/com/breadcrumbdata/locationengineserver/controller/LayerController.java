package com.breadcrumbdata.locationengineserver.controller;

import com.breadcrumbdata.locationengineserver.config.CustomResponse;
import com.breadcrumbdata.locationengineserver.model.ListResponse;
import com.breadcrumbdata.locationengineserver.model.Paginator;
import com.breadcrumbdata.locationengineserver.model.dto.LayerDTO;
import com.breadcrumbdata.locationengineserver.model.vo.LayerVO;
import com.breadcrumbdata.locationengineserver.service.LayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api")
public class LayerController {

    private final LayerService layerService;

    @Autowired
    public LayerController(LayerService layerService) {
        this.layerService = layerService;
    }

    @PostMapping("/layers")
    public ResponseEntity create(@RequestBody LayerDTO createLayerRequest) throws IOException {
        LayerVO layerVO = layerService.create(createLayerRequest);
        CustomResponse customResponse = new CustomResponse();
        customResponse.setData(layerVO);
        customResponse.setMsg("success");
        customResponse.setCode(HttpStatus.OK.value());
        return ResponseEntity.ok(customResponse);
    }

    @GetMapping("/layers/{id}")
    public ResponseEntity get(@PathVariable("id") Integer id) {
        LayerVO layerVO = layerService.get(id);
        CustomResponse customResponse = new CustomResponse();
        customResponse.setData(layerVO);
        customResponse.setMsg("success");
        customResponse.setCode(HttpStatus.OK.value());
        return ResponseEntity.ok(customResponse);
    }

    @GetMapping("/layers")
    public ResponseEntity getLayerList(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable paging = PageRequest.of(page, size);
        Page<LayerVO> pageLayerVO = layerService.findAll(paging);
        List<LayerVO> layerVOList = pageLayerVO.getContent();
        Paginator paginator = new Paginator(pageLayerVO.getNumber(),pageLayerVO.getSize());
        LayersResponse layersResponse = new LayersResponse(layerVOList, pageLayerVO.getTotalElements(), paginator);
        CustomResponse customResponse = new CustomResponse();
        customResponse.setData(layersResponse);
        customResponse.setMsg("success");
        customResponse.setCode(HttpStatus.OK.value());
        return ResponseEntity.ok(customResponse);
    }

    class LayersResponse extends ListResponse {
        List<LayerVO> layers;

        LayersResponse(List<LayerVO> layers, Long total, Paginator paginator) {
            super(total, paginator);
            this.layers = layers;
        }

        public List<LayerVO> getLayers() {
            return layers;
        }

        public void setLayers(List<LayerVO> layers) {
            this.layers = layers;
        }
    }
}
