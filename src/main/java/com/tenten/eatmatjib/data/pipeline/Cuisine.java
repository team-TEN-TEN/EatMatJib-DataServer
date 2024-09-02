package com.tenten.eatmatjib.data.pipeline;

public enum Cuisine {
    KOREAN("한식"),
    JAPANESE("일식"),
    WESTERN("경양식"),
    CHINESE("중국식"),
    FOREIGN("외국음식전문점"),
    SNACK("분식"),
    OTHER("기타");

    private final String name;
    Cuisine(String name) {
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public static Cuisine koreanName(String name) {
        for (Cuisine cuisine : values()) {
            if (cuisine.getName().equals(name)) {
                return cuisine;
            }
        }
        return OTHER;
    }
}
