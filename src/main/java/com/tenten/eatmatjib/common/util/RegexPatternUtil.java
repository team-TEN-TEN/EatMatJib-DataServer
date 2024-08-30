package com.tenten.eatmatjib.common.util;

public class RegexPatternUtil {

    public static final String PASSWORD = "^(?!.*(.)\\1\\1)(?=.*[A-Za-z].*[\\d!@#$%^&*]|.*[\\d!@#$%^&*].*[A-Za-z])[A-Za-z\\d!@#$%^&*]{10,}$";
}
