package controller;

import java.util.HashMap;
import java.util.Map;

public class ReportControl {
    public Map<String, Integer> getSummaryStats() {
        Map<String, Integer> stats = new HashMap<>();
        // In a real app, these would be SQL 'SELECT COUNT(*)' results
        stats.put("Pending Parcels", 124);
        stats.put("Dispatched Today", 85);
        stats.put("Inventory Items", 1200);
        stats.put("Active Staff", 8);
        return stats;
    }
}