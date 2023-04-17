package com.example.smell1;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class RunSmell1 {
    private RunSmell1() {
    }

    public static List<City> getAllCitiesToVisit(Speaker speaker) {
        List<City> resultList = new ArrayList<>();
        for (Talk talk : speaker.getTalks()) {
            for (Conference conference : talk.getConferences()) {
                for (City city: conference.getCities()) {
                    resultList.add(city);
                }
            }
        }
        return resultList;
    }

    public static List<City> getAllCitiesToVisit2(Speaker speaker) {
        return speaker.getTalks().stream()
                .flatMap(talk -> talk.getConferences().stream())
                .flatMap(conference -> conference.getCities().stream())
                .collect(Collectors.toList());
    }
}
