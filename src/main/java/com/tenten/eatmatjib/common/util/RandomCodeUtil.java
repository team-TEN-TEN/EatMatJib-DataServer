package com.tenten.eatmatjib.common.util;

import java.util.Random;

public class RandomCodeUtil {

    private static final int LENGTH = 6;
    private static final int ORIGIN = 48;
    private static final int BOUND = 122;

    public static String getRandomCode() {
        Random random = new Random();
        StringBuilder stringBuilder = new StringBuilder();

        random.ints(ORIGIN, BOUND + 1)
            .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
            .limit(LENGTH)
            .forEach(stringBuilder::appendCodePoint);

        return stringBuilder.toString();
    }
}
