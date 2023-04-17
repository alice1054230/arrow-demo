package com.example.smell2;

public class RunSmell2 {
    private RunSmell2() {
    }

    public static City getNextTalkCity(Speaker speaker) throws Exception {
        return speaker.nextTalk().get()
                .getConference().get()
                .getCity().get();
    }
}
