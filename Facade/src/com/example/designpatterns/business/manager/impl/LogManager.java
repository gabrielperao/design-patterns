package com.example.designpatterns.business.manager.impl;

public class LogManager {
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_YELLOW = "\u001B[33m";
    private static final String ANSI_CYAN = "\u001B[36m";

    public static void debug(final String message) {
        log(paintLogMessage("[DEBUG] " + message, ANSI_CYAN));
    }

    public static void info(final String message) {
        log("[INFO] " + message);
    }

    public static void warn(final String message) {
        log(paintLogMessage("[WARN] " + message, ANSI_YELLOW));
    }

    public static void error(final String message) {
        log(paintLogMessage("[ERROR] " + message, ANSI_RED));
    }

    private static String paintLogMessage(final String message, final String ansi) {
        return ansi + message + ANSI_RESET;
    }

    private static void log(String message) {
        System.out.println(message);
    }
}
