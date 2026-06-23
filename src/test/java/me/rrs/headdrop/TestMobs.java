package me.rrs.headdrop;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import me.rrs.headdrop.database.EntityHead;
import java.util.HashSet;
import java.util.Set;

public class TestMobs {
    public static void main(String[] args) {
        Set<String> headRegistry = new HashSet<>();
        for (EntityHead head : EntityHead.values()) {
            headRegistry.add(head.getName().toUpperCase());
        }
        
        Set<String> vanilla = new HashSet<>();
        vanilla.add("CREEPER");
        vanilla.add("SKELETON");
        vanilla.add("WITHER_SKELETON");
        vanilla.add("ZOMBIE");
        vanilla.add("ENDER_DRAGON");
        vanilla.add("PLAYER");
        vanilla.add("ARMOR_STAND");
        vanilla.add("GIANT"); // Actually GIANT is in EntityHead
        
        for (EntityType type : EntityType.values()) {
            if (type.getEntityClass() != null && LivingEntity.class.isAssignableFrom(type.getEntityClass())) {
                String name = type.name();
                if (!headRegistry.contains(name) && !vanilla.contains(name)) {
                    // MUSHROOM_COW mapped to MOOSHROOM
                    // SNOWMAN mapped to SNOW_GOLEM
                    if (name.equals("MUSHROOM_COW") || name.equals("SNOWMAN") || name.equals("ILLUSIONER")) continue;
                    System.out.println("MISSING: " + name);
                }
            }
        }
    }
}
