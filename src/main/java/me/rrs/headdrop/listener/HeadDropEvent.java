package me.rrs.headdrop.listener;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nullable;

public class HeadDropEvent extends Event implements Cancellable {

    private static final HandlerList handlers = new HandlerList();
    private boolean cancelled;

    private final ItemStack head;
    private final Player killer;
    private final LivingEntity entity;

    public HeadDropEvent(ItemStack head, @Nullable Player killer, LivingEntity entity) {
        this.head = head;
        this.killer = killer;
        this.entity = entity;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList(){
        return handlers;
    }


    public ItemStack getHead() {
        return head;
    }

    public LivingEntity getEntity() {
        return entity;
    }

    public Player getKiller() {
        return killer;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancel) {
        cancelled = cancel;
    }
}
