package io.paradaux.bukkit.chiton.models.types;

import io.paradaux.bukkit.chiton.models.exceptions.InvalidNameException;
import io.paradaux.bukkit.chiton.models.exceptions.TickingException;
import io.paradaux.bukkit.chiton.models.interfaces.DynamicText;
import io.paradaux.bukkit.chiton.models.interfaces.IScoreboard;
import io.paradaux.bukkit.chiton.models.interfaces.Tickable;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static com.google.common.base.Preconditions.checkArgument;

public abstract class BukkitScoreboard implements IScoreboard, Tickable {

    private final Map<Integer, String> sidebarTexts = new HashMap<>();
    private boolean loaded;
    private Scoreboard scoreboard;
    private DynamicText displayName;
    private final Player player;

    public BukkitScoreboard(Player player) {
        this.player = player;
    }

    protected static String trimScoreboardText(String str) {
        if (str.length() > 30) {
            str = ChatColor.stripColor(str);
            if (str.length() > 30) {
                str = str.substring(0, 30);
            }
        }
        return str;
    }

    public void addEntryToTeam(String key, String value) {
        Team team = this.scoreboard.getTeam(key);
        if (team != null) {
            team.addEntry(value);
        }
    }

    public void clearScore(String name) {
        scoreboard.resetScores(name);
    }

    public void createObjective(DisplaySlot slot, String name, String displayName, String criteria) {
        if (name.length() > 16) {
            throw new InvalidNameException();
        }

        Objective objective = scoreboard.getObjective(slot);

        if (null != objective) {
            objective.unregister();
        }

        objective = scoreboard.registerNewObjective(name, criteria, displayName);
        objective.setDisplaySlot(slot);
    }

    public void load() {
        this.scoreboard = Bukkit.getServer().getScoreboardManager().getNewScoreboard();
        this.loaded = true;
    }

    public void updateScoreboardPlayerEntry(String key, String playerName) {
        if (!isDisabled()) {
            addEntryToTeam(key, playerName);
        }
    }

    public void removeEntryFromTeam(String entry) {
        if (!isDisabled()) {
            Team team = this.scoreboard.getEntryTeam(entry);
            if (team != null) {
                team.removeEntry(entry);
            }
        }
    }

    public void removeFromTeam(String rank, String playerName) {
        Team team = this.scoreboard.getTeam(rank);
        team.removeEntry(playerName);
    }

    public void resetAllScores() {
        Set<String> entries = scoreboard.getEntries();
        if (entries != null) {
            for (String entry : entries) {
                scoreboard.resetScores(entry);
            }
        }
        sidebarTexts.clear();
    }

    protected void setBlankSidebarText(int slot) {
        updateSidebarText(slot, null);
    }

    public void unregisterAllObjectives() {
        for (Objective objective : scoreboard.getObjectives()) {
            objective.unregister();
        }
    }

    public void unregisterAllTeams() {
        for (Team team : scoreboard.getTeams()) {
            team.unregister();
        }
    }

    public void updateDisplayName(DisplaySlot slot, String displayName) {
        Objective existing = scoreboard.getObjective(slot);

        if (existing == null) {
            throw new InvalidNameException("This objective does not exist");
        }

        existing.setDisplayName(displayName);
    }

    public void updateScore(DisplaySlot slot, String name, int value) {
        Objective existing = scoreboard.getObjective(slot);

        if (existing == null) {
            throw new TickingException();
        }

        existing.getScore(name).setScore(value);
    }

    public void clearSidebarSlot(int slot) {
        String text = sidebarTexts.remove(slot);

        if (text != null) {
            clearScore(text);
        }
    }

    public void updateSidebarText(int slot, String text) {
        checkArgument(slot >= 0 && slot <= 23);

        if (StringUtils.isBlank(text)) {
            text = StringUtils.EMPTY;
        }

        text = trimScoreboardText(text);
        text += ChatColor.values()[slot].toString();

        // find old text for slot
        String oldText = sidebarTexts.put(slot, text);

        if (text.equals(oldText)) {
            return;
        }

        if (oldText != null) {
            clearScore(oldText);
        }

        updateScore(DisplaySlot.SIDEBAR, text, slot);
    }

    public void disable() {
        resetAllScores();
        unregisterAllObjectives();
        unregisterAllTeams();
        sidebarTexts.clear();
        displayName = null;
        scoreboard = null;
    }

    public boolean isDisabled() {
        return scoreboard == null;
    }

    public Player getPlayer() {
        return player;
    }
}
