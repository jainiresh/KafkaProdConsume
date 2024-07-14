package org.example;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class EventGenerator {

    String[] userNames = {"jai","niresh","aswin","charu","varun","jeevana"};
    public Event generateEvent(){
        return Event.builder().eventId(Math.random()).eventName(userNames[(int)(Math.random()*3)]).build();
    }
}
