package com.levin.concurrent.practice;

public class UnsafeStates {
    private final String[] states= new String[]{
            "AK","AL"
    };

    public String[] getStates(){
        return states;
    }
}
