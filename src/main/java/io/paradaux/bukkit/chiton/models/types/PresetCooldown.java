package io.paradaux.bukkit.chiton.models.types;

public class PresetCooldown extends Cooldown {

    private int wait;

    public PresetCooldown(int defaultWait) {
        wait = defaultWait;
    }

    public void go() {
        super.setWait(wait);
    }

    public int getWait() {
        return wait;
    }

    public void setWait(int wait) {
        this.wait = wait;
    }
}
