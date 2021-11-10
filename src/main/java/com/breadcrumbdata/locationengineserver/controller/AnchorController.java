package com.breadcrumbdata.locationengineserver.controller;

import com.breadcrumbdata.locationengineserver.config.CustomResponse;
import com.breadcrumbdata.locationengineserver.model.ListResponse;
import com.breadcrumbdata.locationengineserver.model.Paginator;
import com.breadcrumbdata.locationengineserver.model.dto.AnchorDTO;
import com.breadcrumbdata.locationengineserver.model.vo.AnchorVO;
import com.breadcrumbdata.locationengineserver.model.vo.LayerVO;
import com.breadcrumbdata.locationengineserver.service.AnchorService;
import com.breadcrumbdata.locationengineserver.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class AnchorController {

    private final AnchorService anchorService;

    @Autowired
    public AnchorController(AnchorService anchorService) {
        this.anchorService = anchorService;
    }

    @PostMapping("/anchors")
    public ResponseEntity<CustomResponse<AnchorVO>> create(@RequestBody AnchorDTO anchorDTO){
        AnchorVO result = anchorService.create(anchorDTO);
        CustomResponse<AnchorVO> customResponse = new CustomResponse<>();
        customResponse.setData(result);
        customResponse.setMsg("success");
        customResponse.setCode(HttpStatus.OK.value());
        return ResponseEntity.ok(customResponse);
    }

    @GetMapping("/anchors/{id}")
    public ResponseEntity<CustomResponse<AnchorVO>> get(@PathVariable Integer id) {
        AnchorVO result = anchorService.get(id);
        CustomResponse<AnchorVO> customResponse = new ResponseUtil<AnchorVO>().generate(result);
        return ResponseEntity.ok(customResponse);
    }

    @GetMapping("/anchors")
    public ResponseEntity<CustomResponse<AnchorResponse>> list(
            @RequestParam (defaultValue = "0") int page,
            @RequestParam (defaultValue = "10") int size
    ) {
        Pageable paging = PageRequest.of(page, size);
        Page<AnchorVO> anchorVOPage = anchorService.findAll(paging);
        List<AnchorVO> anchorVOList = anchorVOPage.getContent();
        Paginator paginator = new Paginator(anchorVOPage.getNumber(), anchorVOPage.getSize());
        AnchorResponse anchorResponse = new AnchorResponse(anchorVOList, anchorVOPage.getTotalElements(), paginator);
        CustomResponse<AnchorResponse> customResponse = new ResponseUtil<AnchorResponse>().generate(anchorResponse);
        return ResponseEntity.ok(customResponse);
    }

    class AnchorResponse extends ListResponse {
        List<AnchorVO> anchors;

        AnchorResponse(List<AnchorVO> anchors, Long total, Paginator paginator) {
            super(total, paginator);
            this.anchors = anchors;
        }
    }
}
