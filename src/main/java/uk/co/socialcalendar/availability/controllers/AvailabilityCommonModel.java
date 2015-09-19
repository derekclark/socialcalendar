package uk.co.socialcalendar.availability.controllers;

import uk.co.socialcalendar.availability.entities.Availability;

import java.util.HashMap;
import java.util.Map;

public class AvailabilityCommonModel {
    public Map<String, Object> getSection() {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        modelMap.put("section","availability");
        return modelMap;
    }

    public Map<String, Object> getNewAvailability() {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        modelMap.put("newAvailability",new Availability());
        return modelMap;
    }
}
