package io.paradaux.bukkit.chiton.models.enums;

public enum Rate {

    MIN_64(3840000L),
    MIN_32(1920000L),
    MIN_16(960000L),
    MIN_08(480000L),
    MIN_04(240000L),
    MIN_02(120000L),
    MIN_01(60000L),
    SLOWEST(32000L),
    SLOWER(16000L),
    SEC_10(10000L),
    SEC_8(8000L),
    SEC_6(6000L),
    SEC_4(4000L),
    SEC_2(2000L),
    SEC(1000L),
    FAST(500L),
    FASTER(250L),
    FASTEST(125L),
    TICK(50L),
    INSTANT(0L);

    private final long time;
    private volatile long last;
    private volatile long timeSpent;
    private volatile long timeCount;

    Rate(long time) {
        this.time = time;
        this.last = System.currentTimeMillis();
    }

    public static synchronized boolean elapsed(long from, long required) {
        return System.currentTimeMillis() - from > required;
    }

    public synchronized boolean hasElapsed() {
        if (elapsed(this.last, this.time)) {
            this.last = System.currentTimeMillis();
            return true;
        }
        return false;
    }

    public long getTime() {
        return time;
    }

    public void startTime() {
        this.timeCount = System.currentTimeMillis();
    }

    public void stopTime() {
        this.timeSpent += System.currentTimeMillis() - this.timeCount;
    }

    public void printAndResetTime() {
        System.out.println(name() + " in a second: " + this.timeSpent);
        this.timeSpent = 0L;
    }

}
