package com.example.backend.category;

public enum Category {
    ART("예술"),
    ARTIFICIAL_INTELLIGENCE("인공지능"),
    CAREER("커리어"),
    DATA_SCIENCE("데이터 과학"),
    DESIGN("디자인"),
    DEVELOPMENT("개발"),
    GAME_DEVELOPMENT("게임 개발"),
    HARDWARE("하드웨어"),
    MANAGEMENT("경영"),
    MARKETING("마케팅"),
    NETWORK("네트워크"),
    PLANNING("기획"),
    SECURITY("시큐리티"),
    SELF_DEVELOPMENT("자기 계발");

    private final String displayName;

    Category(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}

