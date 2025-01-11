package com.example.backend.map.service;

import com.example.backend.map.dto.MapDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MapService {

    public MapDto calculateDistance(Long studyId) {

        // 더미 데이터
        List<double[]> coordinates = Arrays.asList(
                new double[]{37.5665, 126.9780},
                new double[]{34.0522, -118.2437},
                new double[]{48.8566, 2.3522}
        );

        double[] midpoint = calculateGeographicalMidpoint(coordinates);

        return new MapDto(midpoint[0], midpoint[1]);
    }

    public static double[] calculateGeographicalMidpoint(List<double[]> coordinates) {

        // x, y, z 구성 요소의 합 초기화
        double x = 0.0;
        double y = 0.0;
        double z = 0.0;

        // 각 좌표를 반복 처리
        for (double[] coord : coordinates) {
            // 위도와 경도를 도 단위에서 라디안 단위로 변환
            double latitude = Math.toRadians(coord[0]);
            double longitude = Math.toRadians(coord[1]);

            // 구면 좌표를 직교 좌표로 변환
            x += Math.cos(latitude) * Math.cos(longitude);
            y += Math.cos(latitude) * Math.sin(longitude);
            z += Math.sin(latitude);
        }

        // x, y, z 평균 계산
        int totalPoints = coordinates.size();
        x /= totalPoints;
        y /= totalPoints;
        z /= totalPoints;

        // 직교 좌표를 다시 구면 좌표로 변환
        double centralLongitude = Math.atan2(y, x); // 경도 계산
        double centralSquareRoot = Math.sqrt(x * x + y * y); // 위도를 위한 대각선 계산
        double centralLatitude = Math.atan2(z, centralSquareRoot); // 위도 계산

        // 중간 지점을 위도와 경도로 반환 (도 단위)
        return new double[]{
                Math.toDegrees(centralLatitude),
                Math.toDegrees(centralLongitude)
        };

    }
}
