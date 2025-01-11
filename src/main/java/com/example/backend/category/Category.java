package com.example.backend.category;

public enum Category {
    DEVELOPMENT("개발"),
    GAME_DEVELOPMENT("게임 개발"),
    DATA_SCIENCE("데이터 사이언스"),
    ARTIFICIAL_INTELLIGENCE("인공지능"),
    SECURITY("보안"),
    NETWORK("네트워크"),
    HARDWARE("하드웨어"),
    DESIGN("디자인"),
    ART("아트"),
    PLANNING("기획"),
    MANAGEMENT("경영"),
    MARKETING("마케팅"),
    CAREER("커리어"),
    SELF_DEVELOPMENT("자기 계발");

    private final String description;

    Category(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}