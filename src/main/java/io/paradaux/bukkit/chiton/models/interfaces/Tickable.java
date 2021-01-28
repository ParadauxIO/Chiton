package io.paradaux.bukkit.chiton.models.interfaces;

import io.paradaux.bukkit.chiton.models.exceptions.TickingException;

public interface Tickable {

    /**
     * Calls on an object to be ticked.
     */
    void tick() throws TickingException;

}
