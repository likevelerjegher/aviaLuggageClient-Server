package com.example.demo.util;

import java.util.*;

public class BaggageStatusHistory {
    private static final Map<Long, List<String>> history = new HashMap<>();

    public static void addHistory(Long baggageId, BaggageStatus oldStatus, BaggageStatus newStatus) {
        String record = String.format("Status changed from %s to %s at %s", oldStatus, newStatus, new Date());
        history.computeIfAbsent(baggageId, k -> new ArrayList<>()).add(record);
    }

    public static List<String> getHistory(Long baggageId) {
        return history.getOrDefault(baggageId, List.of());
    }
}

