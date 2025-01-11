package com.example.backend.member;

import java.util.Random;

public class RandomNumberGenerator {
    public static int generateRandomAvatar() {
        Random random = new Random();
        return random.nextInt(10) + 1; // 1부터 1000 사이의 숫자 생성
    }
}
