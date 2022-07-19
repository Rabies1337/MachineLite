package dev.rabies.machinelite.utils;

public class TimerUtil {
    private long prevMS = 0L;

    public boolean delay(float milliSec) {
        return ((float) this.getIncremental((getTime() - this.prevMS), 50.0D) >= milliSec);
    }

    public void reset() {
        this.prevMS = getTime();
    }

    public void reset(long offset) {
        this.prevMS = getTime() + offset;
    }

    public long getTime() {
        return System.nanoTime() / 1000000L;
    }

    public long getDifference() {
        return getTime() - this.prevMS;
    }

    public void setDifference(long difference) {
        this.prevMS = getTime() - difference;
    }

    private double getIncremental(double val, double inc) {
        double one = 1.0D / inc;
        return Math.round(val * one) / one;
    }
}
