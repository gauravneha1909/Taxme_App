package com.example.gplusin.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DataUtils {

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");

    private static final SimpleDateFormat dateFormathypen = new SimpleDateFormat("MM-dd-yyyy");

    public static String formatDate(Date date) {
        return date != null ? dateFormat.format(date) : null;
    }

    public static String formatDatehypen(Date date) {
        return date != null ? dateFormathypen.format(date) : null;
    }

    public static String booleanToString(boolean value) {
        return value ? "true" : "false";
    }

    public static String floatToString(Float value) {
        return value != null ? value.toString() : null;
    }
}

