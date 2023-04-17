package com.example.monad3;

public class RunMonadSample3_1 {

    public static Monad<City> getNextTalkCity(Speaker speaker) {
        return speaker.nextTalk()
                .bind(talk -> talk.getConference())
                .bind(conference -> conference.getCity());
    }

}
