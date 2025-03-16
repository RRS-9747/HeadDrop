# HeadDropEvent - Custom Event for Head Drop Plugin

## **HeadDropEvent - Custom Event for Head Drop Plugin**

### **Overview**

`HeadDropEvent` is a custom event in the **HeadDrop** plugin. This event triggers when a mob head is about to drop from a killed entity. Developers can listen to this event and modify the head drop behavior (e.g., changing the dropped item, canceling the drop, or adding extra effects).

***

### **Class Definition**

```java
public class HeadDropEvent extends Event implements Cancellable
```

This class extends `Event` and implements `Cancellable`, allowing developers to modify or prevent the event from happening.

### **Methods and Description**

***

#### **Constructor**

```java
public HeadDropEvent(Player player, LivingEntity entity, ItemStack headItem)
```

* **`player`** → The player who killed the entity.
* **`entity`** → The entity that dropped the head.
* **`headItem`** → The item representing the dropped head.

***

***

#### **Get Methods (Access Data)**

**Get the player who triggered the event**

```java
public Player getPlayer()
```

* Returns the player who killed the entity and triggered the head drop.



**Get the entity that dropped the head**

```java
public LivingEntity getEntity()
```

* Returns the entity that was killed.



**Get the item (head) that is being dropped**

```java
public ItemStack getHeadItem()
```

* Returns the head item that is about to drop.

***



**Set Methods (Modify the Drop)**

**Change the item being dropped**

```java
public void setHeadItem(ItemStack headItem)
```

* Allows changing the dropped head item.
* **Example:** Change a zombie head to a custom skull.



**Cancel the Event (Prevent Drop)**

**Check if the event is cancelled**

```java
public boolean isCancelled()
```

* Returns `true` if the event is cancelled (i.e., the head drop will not occur).



**Set the event to cancelled**

```java
public void setCancelled(boolean cancel)
```

* If `true`, prevents the head from dropping.

***

#### **Event Handling (Required for Bukkit Events)**

**Get event handlers**

```java
javaCopyEditpublic HandlerList getHandlers()
```

* Required for Bukkit's event system.



**Get static event handler list**

```java
javaCopyEditpublic static HandlerList getHandlerList()
```

* Needed for event registration.

***

### **Example Usage**

Here’s an example of how to listen to this event in another plugin:

```java
@EventHandler
public void onHeadDrop(HeadDropEvent event) {
    Player player = event.getPlayer();
    LivingEntity entity = event.getEntity();

    // Check if the player has a special permission
    if (!player.hasPermission("head.drop")) {
        event.setCancelled(true); // Prevent the head from dropping
        player.sendMessage(ChatColor.RED + "You are not allowed to collect heads!");
        return;
    }

    // Change the dropped head item
    ItemStack newHead = new ItemStack(Material.PLAYER_HEAD);
    ItemMeta meta = newHead.getItemMeta();
    meta.setDisplayName(ChatColor.GOLD + "Rare Mob Head");
    newHead.setItemMeta(meta);

    event.setHeadItem(newHead);
    player.sendMessage(ChatColor.GREEN + "You got a special head!");
}
```

***

### **How the Event Works in the Plugin**

Within the plugin, the event is triggered in the `handleEntityDrop` method as follows:

```java
HeadDropEvent headDropEvent = new HeadDropEvent(killer, event.getEntity(), headItem);
Bukkit.getPluginManager().callEvent(headDropEvent);
if (headDropEvent.isCancelled()) {
    return;
}
```

* **Step 1:** The event is created when a head is about to drop.
* **Step 2:** Other plugins can listen to `HeadDropEvent` and modify or cancel it.
* **Step 3:** If the event is not cancelled, the head is added to the drop list.

***

### **Conclusion**

The `HeadDropEvent` gives developers full control over the head drop mechanism in the plugin. It allows you to:

* Change the dropped head item.
* Prevent certain players from collecting heads.
* Introduce custom effects or logic when a head drop occurs.

This event is a flexible tool for managing and enhancing mob head drops in your game.

