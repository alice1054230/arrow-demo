package com.example.monad2;

public class RunMonadSample2 {

    public static Monad<City> getNextTalkCity(Speaker speaker) {
        return speaker.nextTalk()
                .bind(talk -> talk.getConference())
                .bind(conference -> conference.getCity());
    }

}
