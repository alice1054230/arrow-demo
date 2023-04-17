package com.example.smell0;

import java.util.Optional;

public class RunSmell0 {
    private RunSmell0() {
    }

    public static City getNextTalkCity(Speaker speaker) {
        Talk talk = speaker.nextTalk();
        Conference conference = talk.getConference();
        City city = conference.getCity();
        return city;
    }

    public static City getNextTalkCity2(Speaker speaker) {
        return speaker.nextTalk()
                .getConference()
                .getCity();
    }

    /**
     *  nullable
     */
    public static City getNextTalkCity3(Speaker speaker) {
        Talk talk = speaker.nextTalk();
        if (talk == null)
            return null;

        Conference conference = talk.getConference();
        if (conference == null)
            return null;

        City city = conference.getCity();
        return city;
    }

    public static City getNextTalkCity4(Speaker speaker) {
        Optional<City> city = Optional.ofNullable(speaker.nextTalk())
                .map(talk -> Optional.ofNullable(talk.getConference()))
                .map(conference -> Optional.ofNullable(conference.get().getCity()))
                .get();
        return speaker.nextTalkOptional()
                .flatMap(talk -> talk.getConferenceOptional())
                .flatMap(conference -> conference.getCityOptional())
                .orElse(null);
    }

    public static City getNextTalkCity5(Speaker speaker) {
        return speaker.nextTalkOptional()
                .flatMap(Talk::getConferenceOptional)
                .flatMap(Conference::getCityOptional)
                .orElse(null);
    }
}
