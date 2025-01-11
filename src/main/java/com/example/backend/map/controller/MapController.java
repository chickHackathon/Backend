package com.example.backend.map.controller;

import com.example.backend.common.BaseResponse;
import com.example.backend.map.dto.MapDto;
import com.example.backend.map.service.MapService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MapController {

    private final MapService mapService;

    @GetMapping("/map")
    public BaseResponse<MapDto> calculateDistance(
            @RequestBody List<String> memberIds) {
        MapDto mapDto = mapService.calculateDistance(memberIds);
        return new BaseResponse<>(mapDto);

    }
}
