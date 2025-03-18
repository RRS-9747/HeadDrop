package me.rrs.headdrop.api;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;

public class HeadDropEvent extends Event implements Cancellable {
    private static final HandlerList handlers = new HandlerList();
    private final Player player;
    private final LivingEntity entity;
    private ItemStack headItem;
    private boolean isCancelled;

    public HeadDropEvent(Player player, LivingEntity entity, ItemStack headItem) {
        this.player = player;
        this.entity = entity;
        this.headItem = headItem;
        this.isCancelled = false;
    }

    public Player getPlayer() {
        return player;
    }

    public LivingEntity getEntity() {
        return entity;
    }

    public ItemStack getHeadItem() {
        return headItem;
    }

    public void setHeadItem(ItemStack headItem) {
        this.headItem = headItem;
    }

    @Override
    public boolean isCancelled() {
        return isCancelled;
    }

    @Override
    public void setCancelled(boolean cancel) {
        this.isCancelled = cancel;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}