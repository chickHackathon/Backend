package com.example.backend.map.service;

import com.example.backend.map.dto.MapDto;
import com.example.backend.member.entity.Member;
import com.example.backend.member.repository.MemberRepository;
import com.example.backend.study.repository.StudyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MapService {

    private final MemberRepository memberRepository;

    public MapDto calculateDistance(List<String> memberIds) {

        List<double[]> coordinates = memberIds.stream()
                .map(memberId -> {
                    Member member = memberRepository.findById(Long.parseLong(memberId))
                            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));

                    return new double[]{member.getLatitude(), member.getLongitude()};
                })
                .collect(Collectors.toList());

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
