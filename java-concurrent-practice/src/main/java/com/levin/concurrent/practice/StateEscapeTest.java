package com.levin.concurrent.practice;

public class StateEscapeTest {
    public static void main(String[] args) {
        UnsafeStates unsafeStates = new UnsafeStates();
        String[] states = unsafeStates.getStates();
        states[states.length-1] = "AM";

        System.out.println("The original second element of UnsafeStates is: [AL]");
        System.out.println(String.format("Now, the second element of UnsafeStates is: [%s]",
                unsafeStates.getStates()[1]));

        SafeStates safeStates = new SafeStates();
        String[] states2 = safeStates.getStates();
        states2[states2.length-1] = "AM";

        System.out.println("The original second element of SafeStates is: [AL]");
        System.out.println(String.format("Now, the second element of SafeStates is: [%s]",
                safeStates.getStates()[1]));
    }
}
