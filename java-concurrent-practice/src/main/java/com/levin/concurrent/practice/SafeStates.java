package com.levin.concurrent.practice;

import java.util.Arrays;
import java.util.Collections;

public class SafeStates {
    private final String[] states= new String[]{
            "AK","AL"
    };

    public String[] getStates(){
        return Arrays.copyOf(states,states.length);
    }
}
