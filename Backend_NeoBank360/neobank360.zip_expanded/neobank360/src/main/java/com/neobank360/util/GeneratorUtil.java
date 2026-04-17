package com.neobank360.util;

import java.util.Random;

public class GeneratorUtil {

    public static String generateUsername(String name) {
        return name.toLowerCase() + (1000 + new Random().nextInt(9000));
    }

    public static String generateAccountNumber() {
        return "1200" + (1000 + new Random().nextInt(9000));
    }
}