package dev.rabies.machinelite.utils;

public class TimerUtil {
    private long prevMS = 0L;

    public boolean delay(float milliSec) {
        return ((float) this.getIncremental((getTime() - this.prevMS), 50.0D) >= milliSec);
    }

    public void reset() {
        this.prevMS = getTime();
    }

    public long getTime() {
        return System.nanoTime() / 1000000L;
    }

    private double getIncremental(double val, double inc) {
        double one = 1.0D / inc;
        return Math.round(val * one) / one;
    }
}
