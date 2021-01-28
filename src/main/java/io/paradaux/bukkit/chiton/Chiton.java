package io.paradaux.bukkit.chiton;

import io.paradaux.bukkit.chiton.models.types.BukkitScoreboard;
import io.paradaux.bukkit.chiton.models.interfaces.DynamicText;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;

public abstract class Chiton extends BukkitScoreboard {

    private DynamicText displayName;
    private int ticks;

    public Chiton(Player player) {
        super(player);
    }

    private void displayFlashingTitle() {
        if (displayName != null) {
            displayName.changeText();

            updateDisplayName(DisplaySlot.SIDEBAR, displayName.getVisibleText());
        }
    }

    @Override
    public void tick() {
        if (isDisabled()) {
            return;
        }

        this.displayFlashingTitle();

        if (ticks % 2 == 0) {
            // this keeps the previous scoreboard
            updateTexts();
        }

        ticks++;
    }

    protected abstract void updateTexts();


    @Override
    public void disable() {
        getPlayer().setScoreboard(Bukkit.getScoreboardManager().getMainScoreboard());
        super.disable();
    }

}
