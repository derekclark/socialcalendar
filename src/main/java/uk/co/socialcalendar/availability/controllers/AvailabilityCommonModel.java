package uk.co.socialcalendar.availability.controllers;

import java.util.HashMap;
import java.util.Map;

public class AvailabilityCommonModel {
    public Map<String, Object> getSection() {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        modelMap.put("section","availability");
        return modelMap;
    }
}
