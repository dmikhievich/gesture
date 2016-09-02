package com.gestureframework.retry.policy;

/**
 * Created by Dzmitry_Mikhievich.
 */
//TODO add hierarchy
//@Builder
public class DelayPolicy {

    private static final double DEFAULT_FACTOR = 1.0;

    private double timeout = 0;
    private double initialDelay = 0;
    private double delayBetweenRepeat = 0;
    private double factor;


    static class Bulilder {

        public Bulilder() {



        }

    }

}
