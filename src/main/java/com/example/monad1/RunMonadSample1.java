package com.example.monad1;

public class RunMonadSample1 {

    public static Monad<City> getNextTalkCity(Speaker speaker) {
        Monad<Monad<Conference>> wrapConference = speaker.nextTalk()
                .map(talk -> talk.getConference());
        Monad<Monad<City>> wrapCity = Monad.unwrap(wrapConference)
                .map(conference -> conference.getCity());

        return Monad.unwrap(wrapCity);
    }

}
