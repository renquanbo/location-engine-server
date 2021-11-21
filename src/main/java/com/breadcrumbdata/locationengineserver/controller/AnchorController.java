package com.breadcrumbdata.locationengineserver.controller;

import com.breadcrumbdata.locationengineserver.config.CustomResponse;
import com.breadcrumbdata.locationengineserver.config.exceptions.AnchorIdOrNameAlreadyExistsException;
import com.breadcrumbdata.locationengineserver.model.ListResponse;
import com.breadcrumbdata.locationengineserver.model.Paginator;
import com.breadcrumbdata.locationengineserver.model.dto.AnchorDTO;
import com.breadcrumbdata.locationengineserver.model.vo.AnchorVO;
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
        if(anchorService.anchorIdOrNameExists(anchorDTO.getId(), anchorDTO.getName())) {
            throw new AnchorIdOrNameAlreadyExistsException("Anchor id or name exists!");
        }
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
    public ResponseEntity<CustomResponse<AnchorListResponse>> getAnchorListByLayerId(
            @RequestParam () int layerId,
            @RequestParam (defaultValue = "0") int page,
            @RequestParam (defaultValue = "10") int size
    ) {
        Pageable paging = PageRequest.of(page, size);
        Page<AnchorVO> anchorVOPage = anchorService.findAllByLayerId(layerId, paging);
        return getAnchorListResponseByPage(anchorVOPage, layerId);
    }

    private ResponseEntity<CustomResponse<AnchorListResponse>> getAnchorListResponseByPage(Page<AnchorVO> anchorVOPage, Integer layerId) {
        List<AnchorVO> anchorVOList = anchorVOPage.getContent();
        Paginator paginator = new Paginator(anchorVOPage.getNumber(), anchorVOPage.getSize());
        AnchorListResponse anchorResponse = new AnchorListResponse(anchorVOList, anchorService.totalByLayerId(layerId), paginator);
        CustomResponse<AnchorListResponse> customResponse = new ResponseUtil<AnchorListResponse>().generate(anchorResponse);
        return ResponseEntity.ok(customResponse);
    }

    class AnchorListResponse extends ListResponse {
        List<AnchorVO> anchors;

        AnchorListResponse(List<AnchorVO> anchors, Long total, Paginator paginator) {
            super(total, paginator);
            this.anchors = anchors;
        }

        public List<AnchorVO> getAnchors() {
            return anchors;
        }

        public void setAnchors(List<AnchorVO> anchors) {
            this.anchors = anchors;
        }
    }
}
