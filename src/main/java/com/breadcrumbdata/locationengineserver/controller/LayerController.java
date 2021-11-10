package com.breadcrumbdata.locationengineserver.controller;

import com.breadcrumbdata.locationengineserver.config.CustomResponse;
import com.breadcrumbdata.locationengineserver.config.exceptions.LayerIdNotFoundException;
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
    public ResponseEntity<CustomResponse<LayerVO>> create(@RequestBody LayerDTO createLayerRequest) throws IOException {
        LayerVO layerVO = layerService.create(createLayerRequest);
        CustomResponse<LayerVO> customResponse = new CustomResponse<>();
        customResponse.setData(layerVO);
        customResponse.setMsg("success");
        customResponse.setCode(HttpStatus.OK.value());
        return ResponseEntity.ok(customResponse);
    }

    @GetMapping("/layers/{id}")
    public ResponseEntity<CustomResponse<LayerVO>> get(@PathVariable("id") Integer id) {
        LayerVO layerVO = layerService.get(id);
        CustomResponse<LayerVO> customResponse = new CustomResponse<>();
        customResponse.setData(layerVO);
        customResponse.setMsg("success");
        customResponse.setCode(HttpStatus.OK.value());
        return ResponseEntity.ok(customResponse);
    }

    @GetMapping("/layers")
    public ResponseEntity<CustomResponse<LayersResponse>> getLayerList(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable paging = PageRequest.of(page, size);
        Page<LayerVO> pageLayerVO = layerService.findAll(paging);
        List<LayerVO> layerVOList = pageLayerVO.getContent();
        Paginator paginator = new Paginator(pageLayerVO.getNumber(),pageLayerVO.getSize());
        LayersResponse layersResponse = new LayersResponse(layerVOList, pageLayerVO.getTotalElements(), paginator);
        CustomResponse<LayersResponse> customResponse = new CustomResponse<>();
        customResponse.setData(layersResponse);
        customResponse.setMsg("success");
        customResponse.setCode(HttpStatus.OK.value());
        return ResponseEntity.ok(customResponse);
    }

    @PutMapping("/layers/{id}")
    public ResponseEntity<CustomResponse<LayerVO>> update(@PathVariable("id") Integer id, @RequestBody LayerDTO updateRequest) {
        LayerVO layerVO = layerService.get(id);
        if(layerVO == null) {
            throw new LayerIdNotFoundException("Layer can not be found by id " + id);
        }
        LayerVO result = layerService.update(id, updateRequest);
        CustomResponse<LayerVO> customResponse = new CustomResponse<>();
        customResponse.setData(result);
        customResponse.setMsg("success");
        customResponse.setCode(HttpStatus.OK.value());
        return ResponseEntity.ok(customResponse);
    }

    @DeleteMapping("/layers/{id}")
    public ResponseEntity<CustomResponse<Boolean>> delete(@PathVariable("id") Integer id) {
        boolean result = layerService.delete(id);
        CustomResponse<Boolean> customResponse = new CustomResponse<>();
        customResponse.setData(result);
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
