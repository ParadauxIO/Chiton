package io.paradaux.bukkit.chiton.models.types;

import io.paradaux.bukkit.chiton.utils.BukkitScheduler;
import org.bukkit.util.StringUtil;

public class Cooldown {

    //Represents the time (in mills) when the time will expire
    private long expireTicks = 0;
    private long expireTime = 0;

    public Cooldown() {
    }

    public Cooldown(int ticks) {
        setWait(ticks);
    }

    //Setters
    public void setWait(int ticks) {
        this.expireTicks = BukkitScheduler.getCurrentTick() + ticks;
        this.expireTime = System.currentTimeMillis() + (ticks * 50);
    }

    public void reset() {
        this.expireTicks = 0;
    }

    //Getters
    public boolean isReady() {
        return getTickLeft() <= 0;
    }

    public boolean isReadyRealTime() {
        return getMillisecondsLeft() <= 0;
    }

    private long getMillisecondsLeft() {
        return expireTime - System.currentTimeMillis();
    }

    public long getTickLeft() {
        return expireTicks - BukkitScheduler.getCurrentTick();
    }

    public String getFormattedTimeLeft(boolean verbose) {
        return ""; // TODO
    }

}
