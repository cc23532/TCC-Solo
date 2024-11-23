package com.solo.api.util;

import java.util.HashMap;
import java.util.Map;

public class TimeConverter {

    public static Map<String, Integer> convertHours(int totalHours) {
        Map<String, Integer> timeConversion = new HashMap<>();

        int years = totalHours / (24 * 365); // Calcular anos
        totalHours %= (24 * 365); // Resto após calcular anos

        int months = totalHours / (24 * 30); // Calcular meses
        totalHours %= (24 * 30); // Resto após calcular meses

        int days = totalHours / 24; // Calcular dias restantes
        int hours = totalHours % 24; // Horas restantes

        // Adicionar os resultados no mapa
        timeConversion.put("years", years);
        timeConversion.put("months", months);
        timeConversion.put("days", days);
        timeConversion.put("hours", hours);

        return timeConversion;
    }

    public static Map<String, Integer> convertMinutes(int totalMinutes) {
        Map<String, Integer> timeConversion = new HashMap<>();

        int years = totalMinutes / (60 * 24 * 365); // Calcular anos
        totalMinutes %= (60 * 24 * 365); // Resto após calcular anos

        int months = totalMinutes / (60 * 24 * 30); // Calcular meses
        totalMinutes %= (60 * 24 * 30); // Resto após calcular meses

        int days = totalMinutes / (60 * 24); // Calcular dias restantes
        int hours = (totalMinutes % (60 * 24)) / 60; // Calcular horas
        int minutes = totalMinutes % 60; // Calcular minutos restantes

        // Adicionar os resultados no mapa
        timeConversion.put("years", years);
        timeConversion.put("months", months);
        timeConversion.put("days", days);
        timeConversion.put("hours", hours);
        timeConversion.put("minutes", minutes);

        return timeConversion;
    }
}
