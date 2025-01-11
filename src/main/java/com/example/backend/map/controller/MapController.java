package com.example.backend.map.controller;

import com.example.backend.common.BaseResponse;
import com.example.backend.map.dto.MapDto;
import com.example.backend.map.service.MapService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class MapController {

    private final MapService mapService;

    @GetMapping("/studies/{studyId}/map")
    public MapDto calculateDistance(
            @PathVariable("studyId") Long studyId
    ) {
        MapDto mapDto = mapService.calculateDistance(studyId);
        BaseResponse<MapDto> baseResponse = new BaseResponse<>(mapDto);
        return baseResponse.getResult();
    }
}
