package me.rrs.Events;

import me.rrs.Database.LivingEntityHead;
import me.rrs.HeadDrop;
import me.rrs.util.ItemStack;
import org.bukkit.DyeColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

import java.util.Random;



public class Entitydeath implements Listener {
    Random random = new Random();
    int x = random.nextInt(101);


    @EventHandler(priority = EventPriority.NORMAL)
    public void onEntityDeath(EntityDeathEvent event) {
        Entity entity = event.getEntity();
        FileConfiguration config = HeadDrop.getInstance().getConfig();
        Player killerPlayer = event.getEntity().getKiller();


        if (event.getEntity().getKiller() == killerPlayer) {


            if (entity.getType() == EntityType.AXOLOTL) {
                if (config.getBoolean("Drop.AXOLOTL") && x <= config.getInt("Chance.AXOLOTL")) {
                    Axolotl axolotl = (Axolotl) event.getEntity();

                    switch (axolotl.getVariant()) {
                        case LUCY:
                            event.getDrops().add(LivingEntityHead.AXOLOTL_LUCY);
                            ItemStack.rename(LivingEntityHead.AXOLOTL_LUCY, "Axolotl");

                            break;
                        case BLUE:
                            event.getDrops().add(LivingEntityHead.AXOLOTL_BLUE);
                            ItemStack.rename(LivingEntityHead.AXOLOTL_BLUE, "Axolotl");

                            break;
                        case WILD:
                            event.getDrops().add(LivingEntityHead.AXOLOTL_WILD);
                            ItemStack.rename(LivingEntityHead.AXOLOTL_WILD, "Axolotl");

                            break;
                        case CYAN:
                            event.getDrops().add(LivingEntityHead.AXOLOTL_CYAN);
                            ItemStack.rename(LivingEntityHead.AXOLOTL_CYAN, "Axolotl");

                            break;
                        case GOLD:
                            event.getDrops().add(LivingEntityHead.AXOLOTL_GOLD);
                            ItemStack.rename(LivingEntityHead.AXOLOTL_GOLD, "Axolotl");
                            break;
                    }
                }
            } else if (entity.getType() == EntityType.BAT) {
                if (config.getBoolean("Drop.BAT") && x <= config.getInt("Chance.BAT")) {
                    event.getDrops().add(LivingEntityHead.BAT);
                    ItemStack.rename(LivingEntityHead.BAT, "Bat");
                }


            } else if (entity.getType() == EntityType.BEE) {
                if (config.getBoolean("Drop.BEE")) {
                    if (x <= config.getInt("Chance.BEE")) {
                        Bee bee = (Bee) event.getEntity();
                        if (bee.getAnger() > 0) {
                            event.getDrops().add(LivingEntityHead.BEE_Aware);
                           ItemStack.rename(LivingEntityHead.BEE_Aware, "Bee");
                        } else {
                           ItemStack.rename(LivingEntityHead.BEE, "Bee");
                            event.getDrops().add(LivingEntityHead.BEE);
                        }
                    }
                }


            } else if (entity.getType() == EntityType.BLAZE) {
                if (config.getBoolean("Drop.BLAZE") && x <= config.getInt("Chance.BLAZE")) {
                    event.getDrops().add(LivingEntityHead.BLAZE);
                    ItemStack.rename(LivingEntityHead.BLAZE, "Blaze");
                }


            } else if (entity.getType() == EntityType.CAT) {
                if (config.getBoolean("Drop.CAT") && x <= config.getInt("Chance.CAT")) {
                    Cat cat = (Cat) event.getEntity();
                    switch (cat.getCatType()) {
                        case BLACK:
                            event.getDrops().add(LivingEntityHead.CAT_BLACK);
                            ItemStack.rename(LivingEntityHead.CAT_BLACK, "Cat");

                            break;
                        case BRITISH_SHORTHAIR:
                            event.getDrops().add(LivingEntityHead.CAT_BRITISH);
                            ItemStack.rename(LivingEntityHead.CAT_BRITISH, "Cat");

                            break;
                        case CALICO:
                            event.getDrops().add(LivingEntityHead.CAT_CALICO);
                            ItemStack.rename(LivingEntityHead.CAT_CALICO, "Cat");

                            break;
                        case JELLIE:
                            event.getDrops().add(LivingEntityHead.CAT_JELLIE);
                            ItemStack.rename(LivingEntityHead.CAT_JELLIE, "Cat");

                            break;
                        case PERSIAN:
                            event.getDrops().add(LivingEntityHead.CAT_PERSIAN);
                            ItemStack.rename(LivingEntityHead.CAT_PERSIAN, "Cat");

                            break;
                        case RAGDOLL:
                            event.getDrops().add(LivingEntityHead.CAT_RAGDOLL);
                            ItemStack.rename(LivingEntityHead.CAT_RAGDOLL, "Cat");

                            break;
                        case RED:
                            event.getDrops().add(LivingEntityHead.CAT_RED);
                            ItemStack.rename(LivingEntityHead.CAT_RED, "Cat");

                            break;
                        case SIAMESE:
                            event.getDrops().add(LivingEntityHead.CAT_SIAMESE);
                            ItemStack.rename(LivingEntityHead.CAT_SIAMESE, "Cat");

                            break;
                        case TABBY:
                            event.getDrops().add(LivingEntityHead.CAT_TABBY);
                            ItemStack.rename(LivingEntityHead.CAT_TABBY, "Cat");

                            break;
                        case WHITE:
                            event.getDrops().add(LivingEntityHead.CAT_WHITE);
                            ItemStack.rename(LivingEntityHead.CAT_WHITE, "Cat");
                            break;
                    }
                }


            } else if (entity.getType() == EntityType.SPIDER) {
                if (config.getBoolean("Drop.SPIDER") && x <= config.getInt("Chance.SPIDER")) {
                    event.getDrops().add(LivingEntityHead.SPIDER);
                    ItemStack.rename(LivingEntityHead.SPIDER, "Spider");
                }


            } else if (entity.getType() == EntityType.CAVE_SPIDER) {
                if (config.getBoolean("Drop.CAVE_SPIDER") && x <= config.getInt("Chance.CAVE_SPIDER")) {
                    event.getDrops().add(LivingEntityHead.CAVE_SPIDER);
                    ItemStack.rename(LivingEntityHead.CAVE_SPIDER, "Cave Spider");
                }


            } else if (entity.getType() == EntityType.CHICKEN) {
                if (config.getBoolean("Drop.CHICKEN") && x <= config.getInt("Chance.CHICKEN")) {
                    event.getDrops().add(LivingEntityHead.CHICKEN);
                    ItemStack.rename(LivingEntityHead.CHICKEN, "Chicken");
                }


            } else if (entity.getType() == EntityType.COD) {
                if (config.getBoolean("Drop.COD") && x <= config.getInt("Chance.COD")) {
                    event.getDrops().add(LivingEntityHead.COD);
                    ItemStack.rename(LivingEntityHead.COD, "Cod");
                }


            } else if (entity.getType() == EntityType.COW) {
                if (config.getBoolean("Drop.COW") && x <= config.getInt("Chance.COW")) {
                    event.getDrops().add(LivingEntityHead.COW);
                    ItemStack.rename(LivingEntityHead.COW, "Cow");
                }


            } else if (entity.getType() == EntityType.DOLPHIN) {
                if (config.getBoolean("Drop.DOLPHIN") && x <= config.getInt("Chance.DOLPHIN")) {
                    event.getDrops().add(LivingEntityHead.DOLPHIN);
                    ItemStack.rename(LivingEntityHead.DOLPHIN, "Dolphin");
                }


            } else if (entity.getType() == EntityType.DONKEY) {
                if (config.getBoolean("Drop.DONKEY") && x <= config.getInt("Chance.DONKEY")) {
                    event.getDrops().add(LivingEntityHead.DONKEY);
                    ItemStack.rename(LivingEntityHead.DONKEY, "Donkey Head");
                }


            } else if (entity.getType() == EntityType.DROWNED) {
                if (config.getBoolean("Drop.DROWNED") && x <= config.getInt("Chance.DROWNED")) {
                    event.getDrops().add(LivingEntityHead.DROWNED);
                    ItemStack.rename(LivingEntityHead.DROWNED, "Drowned");
                }


            } else if (entity.getType() == EntityType.ELDER_GUARDIAN) {
                if (config.getBoolean("Drop.ELDER_GUARDIAN") && x <= config.getInt("Chance.ELDER_GUARDIAN")) {
                    event.getDrops().add(LivingEntityHead.ELDER_GUARDIAN);
                    ItemStack.rename(LivingEntityHead.ELDER_GUARDIAN, "Elder Guardian");
                }


            } else if (entity.getType() == EntityType.ENDERMAN) {
                if (config.getBoolean("Drop.ENDERMAN") && x <= config.getInt("Chance.ENDERMAN")) {
                    event.getDrops().add(LivingEntityHead.ENDERMAN);
                    ItemStack.rename(LivingEntityHead.ENDERMAN, "Enderman");
                }


            } else if (entity.getType() == EntityType.ENDERMITE) {
                if (config.getBoolean("Drop.ENDERMITE") && x <= config.getInt("Chance.ENDERMITE")) {
                    event.getDrops().add(LivingEntityHead.ENDERMITE);
                    ItemStack.rename(LivingEntityHead.ENDERMITE, "Endermite");
                }


            } else if (entity.getType() == EntityType.EVOKER) {
                if (config.getBoolean("Drop.EVOKER") && x <= config.getInt("Chance.EVOKER")) {
                    event.getDrops().add(LivingEntityHead.EVOKER);
                    ItemStack.rename(LivingEntityHead.EVOKER, "Evoker");
                }


            } else if (entity.getType() == EntityType.FOX) {
                if (config.getBoolean("Drop.FOX") && x <= config.getInt("Chance.FOX")) {
                    event.getDrops().add(LivingEntityHead.FOX);
                    ItemStack.rename(LivingEntityHead.FOX, "Fox");
                }


            } else if (entity.getType() == EntityType.GIANT) {
                if (config.getBoolean("Drop.GIANT") && x <= config.getInt("Chance.GIANT")) {
                    event.getDrops().add(LivingEntityHead.GIANT);
                    ItemStack.rename(LivingEntityHead.GIANT, "Giant");
                }


            } else if (entity.getType() == EntityType.GLOW_SQUID) {
                if (config.getBoolean("Drop.GLOW_SQUID") && x <= config.getInt("Chance.GLOW_SQUID")) {
                    event.getDrops().add(LivingEntityHead.GLOW_SQUID);
                    ItemStack.rename(LivingEntityHead.GLOW_SQUID, "Glow Squid");
                }


            } else if (entity.getType() == EntityType.GOAT) {
                if (config.getBoolean("Drop.GOAT") && x <= config.getInt("Chance.GOAT")) {
                    event.getDrops().add(LivingEntityHead.GOAT);
                    ItemStack.rename(LivingEntityHead.GOAT, "Goat");
                }


            } else if (entity.getType() == EntityType.GUARDIAN) {
                if (config.getBoolean("Drop.GUARDIAN") && x <= config.getInt("Chance.GUARDIAN")) {
                    event.getDrops().add(LivingEntityHead.GUARDIAN);
                    ItemStack.rename(LivingEntityHead.GUARDIAN, "Guardian");
                }


            } else if (entity.getType() == EntityType.HOGLIN) {
                if (config.getBoolean("Drop.HOGLIN") && x <= config.getInt("Chance.HOGLIN")) {
                    event.getDrops().add(LivingEntityHead.HOGLIN);
                    ItemStack.rename(LivingEntityHead.HOGLIN, "Hoglin");
                }


            } else if (entity.getType() == EntityType.HORSE) {
                if (config.getBoolean("Drop.HORSE") && x <= config.getInt("Chance.HORSE")) {
                    Horse horse = (Horse) event.getEntity();

                    switch (horse.getColor()) {
                        case WHITE:
                            event.getDrops().add(LivingEntityHead.HORSE_WHITE);
                            ItemStack.rename(LivingEntityHead.HORSE_WHITE, "Horse");

                            break;
                        case CREAMY:
                            event.getDrops().add(LivingEntityHead.HORSE_CREAMY);
                            ItemStack.rename(LivingEntityHead.HORSE_CREAMY, "Horse");

                            break;
                        case CHESTNUT:
                            event.getDrops().add(LivingEntityHead.HORSE_CHESTNUT);
                            ItemStack.rename(LivingEntityHead.HORSE_CHESTNUT, "Horse");

                            break;
                        case BROWN:
                            event.getDrops().add(LivingEntityHead.HORSE_BROWN);
                            ItemStack.rename(LivingEntityHead.HORSE_BROWN, "Horse");

                            break;
                        case BLACK:
                            event.getDrops().add(LivingEntityHead.HORSE_BLACK);
                            ItemStack.rename(LivingEntityHead.HORSE_BLACK, "Horse");

                            break;
                        case GRAY:
                            event.getDrops().add(LivingEntityHead.HORSE_GRAY);
                            ItemStack.rename(LivingEntityHead.HORSE_GRAY, "Horse");

                            break;
                        case DARK_BROWN:
                            event.getDrops().add(LivingEntityHead.HORSE_DARK_BROWN);
                            ItemStack.rename(LivingEntityHead.HORSE_DARK_BROWN, "Horse");
                            break;
                    }
                }


            } else if (entity.getType() == EntityType.HUSK) {
                if (config.getBoolean("Drop.HUSK") && x <= config.getInt("Chance.HUSK")) {
                    event.getDrops().add(LivingEntityHead.HUSK);
                    ItemStack.rename(LivingEntityHead.HUSK, "Husk");
                }


            } else if (entity.getType() == EntityType.ILLUSIONER) {
                if (config.getBoolean("Drop.ILLUSIONER") && x <= config.getInt("Chance.ILLUSIONER")) {
                    event.getDrops().add(LivingEntityHead.ILLUSIONER);
                    ItemStack.rename(LivingEntityHead.ILLUSIONER, "Illusioner");
                }


            } else if (entity.getType() == EntityType.IRON_GOLEM) {
                if (config.getBoolean("Drop.IRON_GOLEM") && x <= config.getInt("Chance.IRON_GOLEM")) {
                    event.getDrops().add(LivingEntityHead.IRON_GOLEM);
                    ItemStack.rename(LivingEntityHead.IRON_GOLEM, "Iron Golem");
                }


            } else if (entity.getType() == EntityType.LLAMA) {
                if (config.getBoolean("Drop.LLAMA") && x <= config.getInt("Chance.LLAMA")) {
                    Llama llama = (Llama) event.getEntity();

                    switch (llama.getColor()) {
                        case BROWN:
                            event.getDrops().add(LivingEntityHead.LLAMA_BROWN);
                            ItemStack.rename(LivingEntityHead.LLAMA_BROWN, "Llama");

                            break;
                        case GRAY:
                            event.getDrops().add(LivingEntityHead.LLAMA_GRAY);
                            ItemStack.rename(LivingEntityHead.LLAMA_GRAY, "Llama");

                            break;
                        case CREAMY:
                            event.getDrops().add(LivingEntityHead.LLAMA_CREAMY);
                            ItemStack.rename(LivingEntityHead.LLAMA_CREAMY, "Llama");

                            break;
                        case WHITE:
                            event.getDrops().add(LivingEntityHead.LLAMA_WHITE);
                            ItemStack.rename(LivingEntityHead.LLAMA_WHITE, "Llama");
                            break;
                    }
                }


            } else if (entity.getType() == EntityType.MAGMA_CUBE) {
                if (config.getBoolean("Drop.MAGMA_CUBE") && x <= config.getInt("Chance.MAGMA_CUBE")) {
                    event.getDrops().add(LivingEntityHead.MAGMA_CUBE);
                    ItemStack.rename(LivingEntityHead.MAGMA_CUBE, "Magma Cube");
                }


            } else if (entity.getType() == EntityType.MUSHROOM_COW) {
                if (config.getBoolean("Drop.MUSHROOM_COW") && x <= config.getInt("Chance.MUSHROOM_COW")) {
                    MushroomCow mushroomCow = (MushroomCow) event.getEntity();

                    switch (mushroomCow.getVariant()) {
                        case RED:
                            event.getDrops().add(LivingEntityHead.MUSHROOM_COW_RED);
                            ItemStack.rename(LivingEntityHead.MUSHROOM_COW_RED, "Mushroom Cow");

                            break;
                        case BROWN:
                            event.getDrops().add(LivingEntityHead.MUSHROOM_COW_BROWN);
                            ItemStack.rename(LivingEntityHead.MUSHROOM_COW_BROWN, "Mushroom Cow");
                            break;
                    }
                }


            } else if (entity.getType() == EntityType.MULE) {
                if (config.getBoolean("Drop.MULE") && x <= config.getInt("Chance.MULE")) {
                    event.getDrops().add(LivingEntityHead.MULE);
                    ItemStack.rename(LivingEntityHead.MULE, "Mule");
                }


            } else if (entity.getType() == EntityType.OCELOT) {
                if (config.getBoolean("Drop.OCELOT") && x <= config.getInt("Chance.OCELOT")) {
                    event.getDrops().add(LivingEntityHead.OCELOT);
                    ItemStack.rename(LivingEntityHead.OCELOT, "Ocelot");
                }


            } else if (entity.getType() == EntityType.PANDA) {
                if (config.getBoolean("Drop.PANDA") && x <= config.getInt("Chance.PANDA")) {
                    Panda panda = (Panda) event.getEntity();
                    if (panda.getMainGene() == Panda.Gene.BROWN) {
                        event.getDrops().add(LivingEntityHead.PANDA_BROWN);
                        ItemStack.rename(LivingEntityHead.PANDA_BROWN, "Panda");
                    } else {
                        event.getDrops().add(LivingEntityHead.PANDA);
                        ItemStack.rename(LivingEntityHead.PANDA, "Panda");
                    }
                }


            } else if (entity.getType() == EntityType.PHANTOM) {
                if (config.getBoolean("Drop.PHANTOM") && x <= config.getInt("Chance.PHANTOM")) {
                    event.getDrops().add(LivingEntityHead.PHANTOM);
                    ItemStack.rename(LivingEntityHead.PHANTOM, "Phantom");
                }


            } else if (entity.getType() == EntityType.PIG) {
                if (config.getBoolean("Drop.PIG") && x <= config.getInt("Chance.PIG")) {
                    event.getDrops().add(LivingEntityHead.PIG);
                    ItemStack.rename(LivingEntityHead.PIG, "Pig");
                }


            } else if (entity.getType() == EntityType.PIGLIN) {
                if (config.getBoolean("Drop.PIGLIN") && x <= config.getInt("Chance.PIGLIN")) {
                    event.getDrops().add(LivingEntityHead.PIGLIN);
                    ItemStack.rename(LivingEntityHead.PIGLIN, "Piglin");
                }


            } else if (entity.getType() == EntityType.PIGLIN_BRUTE) {
                if (config.getBoolean("Drop.PIGLIN_BRUTE") && x <= config.getInt("Chance.PIGLIN_BRUTE")) {
                    event.getDrops().add(LivingEntityHead.PIGLIN_BRUTE);
                    ItemStack.rename(LivingEntityHead.PIGLIN_BRUTE, "Piglin Brute");
                }


            } else if (entity.getType() == EntityType.PILLAGER) {
                if (config.getBoolean("Drop.PILLAGER") && x <= config.getInt("Chance.PILLAGER")) {
                    event.getDrops().add(LivingEntityHead.PILLAGER);
                    ItemStack.rename(LivingEntityHead.PILLAGER, "Pillager");
                }


            } else if (entity.getType() == EntityType.POLAR_BEAR) {
                if (config.getBoolean("Drop.POLAR_BEAR") && x <= config.getInt("Chance.POLAR_BEAR")) {
                    event.getDrops().add(LivingEntityHead.POLAR_BEAR);
                    ItemStack.rename(LivingEntityHead.POLAR_BEAR, "Polar Bear");
                }


            } else if (entity.getType() == EntityType.PUFFERFISH) {
                if (config.getBoolean("Drop.PUFFERFISH") && x <= config.getInt("Chance.PUFFERFISH")) {
                    event.getDrops().add(LivingEntityHead.PUFFERFISH);
                    ItemStack.rename(LivingEntityHead.PUFFERFISH, "Pufferfish");
                }


            } else if (entity.getType() == EntityType.RABBIT) {
                if (config.getBoolean("Drop.RABBIT") && x <= config.getInt("Chance.RABBIT")) {
                    event.getDrops().add(LivingEntityHead.RABBIT);
                    ItemStack.rename(LivingEntityHead.RABBIT, "Rabbit");
                }


            } else if (entity.getType() == EntityType.RAVAGER) {
                if (config.getBoolean("Drop.RAVAGER") && x <= config.getInt("Chance.RAVAGER")) {
                    event.getDrops().add(LivingEntityHead.RAVAGER);
                    ItemStack.rename(LivingEntityHead.RAVAGER, "Ravager");
                }


            } else if (entity.getType() == EntityType.SALMON) {
                if (config.getBoolean("Drop.SALMON") && x <= config.getInt("Chance.SALMON")) {
                    event.getDrops().add(LivingEntityHead.SALMON);
                    ItemStack.rename(LivingEntityHead.SALMON, "Salmon");
                }


            } else if (entity.getType() == EntityType.SHEEP) {
                if (config.getBoolean("Drop.SHEEP_WHITE") && x <= config.getInt("Chance.SHEEP_WHITE")) {
                    Sheep sheep = (Sheep) event.getEntity();

                    if (sheep.getColor() == DyeColor.WHITE) {
                        event.getDrops().add(LivingEntityHead.SHEEP_WHITE);
                        ItemStack.rename(LivingEntityHead.SHEEP_WHITE, "Sheep");

                    } else if (sheep.getColor() == DyeColor.BLACK) {
                        event.getDrops().add(LivingEntityHead.SHEEP_BLACK);
                        ItemStack.rename(LivingEntityHead.SHEEP_BLACK, "Sheep");

                    } else if (sheep.getColor() == DyeColor.BLACK) {
                        event.getDrops().add(LivingEntityHead.SHEEP_BLACK);
                        ItemStack.rename(LivingEntityHead.SHEEP_BLACK, "Sheep");

                    } else if (sheep.getColor() == DyeColor.BROWN) {
                        event.getDrops().add(LivingEntityHead.SHEEP_BROWN);
                        ItemStack.rename(LivingEntityHead.SHEEP_BROWN, "Sheep");

                    } else if (sheep.getColor() == DyeColor.BLUE) {
                        event.getDrops().add(LivingEntityHead.SHEEP_BLUE);
                        ItemStack.rename(LivingEntityHead.SHEEP_BLUE, "Sheep");

                    } else if (sheep.getColor() == DyeColor.CYAN) {
                        event.getDrops().add(LivingEntityHead.SHEEP_CYAN);
                        ItemStack.rename(LivingEntityHead.SHEEP_CYAN, "Sheep");

                    } else if (sheep.getColor() == DyeColor.GRAY) {
                        event.getDrops().add(LivingEntityHead.SHEEP_GRAY);
                        ItemStack.rename(LivingEntityHead.SHEEP_GRAY, "Sheep");

                    } else if (sheep.getColor() == DyeColor.GREEN) {
                        event.getDrops().add(LivingEntityHead.SHEEP_GREEN);
                        ItemStack.rename(LivingEntityHead.SHEEP_GREEN, "Sheep");

                    } else if (sheep.getColor() == DyeColor.LIGHT_BLUE) {
                        event.getDrops().add(LivingEntityHead.SHEEP_LIGHT_BLUE);
                        ItemStack.rename(LivingEntityHead.SHEEP_LIGHT_BLUE, "Sheep");

                    } else if (sheep.getColor() == DyeColor.LIGHT_GRAY) {
                        event.getDrops().add(LivingEntityHead.SHEEP_LIGHT_GRAY);
                        ItemStack.rename(LivingEntityHead.SHEEP_LIGHT_GRAY, "Sheep");

                    } else if (sheep.getColor() == DyeColor.LIME) {
                        event.getDrops().add(LivingEntityHead.SHEEP_LIME);
                        ItemStack.rename(LivingEntityHead.SHEEP_LIME, "Sheep");

                    } else if (sheep.getColor() == DyeColor.MAGENTA) {
                        event.getDrops().add(LivingEntityHead.SHEEP_MAGENTA);
                        ItemStack.rename(LivingEntityHead.SHEEP_MAGENTA, "Sheep");

                    } else if (sheep.getColor() == DyeColor.ORANGE) {
                        event.getDrops().add(LivingEntityHead.SHEEP_ORANGE);
                        ItemStack.rename(LivingEntityHead.SHEEP_ORANGE, "Sheep");

                    } else if (sheep.getColor() == DyeColor.PINK) {
                        event.getDrops().add(LivingEntityHead.SHEEP_PINK);
                        ItemStack.rename(LivingEntityHead.SHEEP_PINK, "Sheep");

                    } else if (sheep.getColor() == DyeColor.PURPLE) {
                        event.getDrops().add(LivingEntityHead.SHEEP_PURPLE);
                        ItemStack.rename(LivingEntityHead.SHEEP_PURPLE, "Sheep");

                    } else if (sheep.getColor() == DyeColor.RED) {
                        event.getDrops().add(LivingEntityHead.SHEEP_RED);
                        ItemStack.rename(LivingEntityHead.SHEEP_RED, "Sheep");

                    } else if (sheep.getColor() == DyeColor.YELLOW) {
                        event.getDrops().add(LivingEntityHead.SHEEP_YELLOW);
                        ItemStack.rename(LivingEntityHead.SHEEP_YELLOW, "Sheep");
                    }
                }


            } else if (entity.getType() == EntityType.SHULKER) {
                if (config.getBoolean("Drop.SHULKER") && x <= config.getInt("Chance.SHULKER")) {
                    event.getDrops().add(LivingEntityHead.SHULKER);
                    ItemStack.rename(LivingEntityHead.SHULKER, "Shulker");
                }


            } else if (entity.getType() == EntityType.SILVERFISH) {
                if (config.getBoolean("Drop.SILVERFISH") && x <= config.getInt("Chance.SILVERFISH")) {
                    event.getDrops().add(LivingEntityHead.SILVERFISH);
                    ItemStack.rename(LivingEntityHead.SILVERFISH, "Silverfish");
                }


            } else if (entity.getType() == EntityType.SKELETON_HORSE) {
                if (config.getBoolean("Drop.SKELETON_HORSE") && x <= config.getInt("Chance.SKELETON_HORSE")) {
                    event.getDrops().add(LivingEntityHead.SKELETON_HORSE);
                    ItemStack.rename(LivingEntityHead.SKELETON_HORSE, "Skeleton Horse");
                }


            } else if (entity.getType() == EntityType.SLIME) {
                if (config.getBoolean("Drop.SLIME") && x <= config.getInt("Chance.SLIME")) {
                    event.getDrops().add(LivingEntityHead.SLIME);
                    ItemStack.rename(LivingEntityHead.SLIME, "Slime");
                }


            } else if (entity.getType() == EntityType.SNOWMAN) {
                if (config.getBoolean("Drop.SNOW_GOLEM") && x <= config.getInt("Chance.SNOW_GOLEM")) {
                    event.getDrops().add(LivingEntityHead.SNOWMAN);
                    ItemStack.rename(LivingEntityHead.SNOWMAN, "Snow Golem");
                }


            } else if (entity.getType() == EntityType.SQUID) {
                if (config.getBoolean("Drop.SQUID") && x <= config.getInt("Chance.SQUID")) {
                    event.getDrops().add(LivingEntityHead.SQUID);
                    ItemStack.rename(LivingEntityHead.SQUID, "Squid");
                }


            } else if (entity.getType() == EntityType.STRAY) {
                if (config.getBoolean("Drop.STRAY") && x <= config.getInt("Chance.STRAY")) {
                    event.getDrops().add(LivingEntityHead.STRAY);
                    ItemStack.rename(LivingEntityHead.STRAY, "Stray");
                }


            } else if (entity.getType() == EntityType.STRIDER) {
                if (config.getBoolean("Drop.STRIDER") && x <= config.getInt("Chance.STRIDER")) {
                    event.getDrops().add(LivingEntityHead.STRIDER);
                    ItemStack.rename(LivingEntityHead.STRIDER, "Strider");
                }


            } else if (entity.getType() == EntityType.TURTLE) {
                if (config.getBoolean("Drop.TURTLE") && x <= config.getInt("Chance.TURTLE")) {
                    event.getDrops().add(LivingEntityHead.TURTLE);
                    ItemStack.rename(LivingEntityHead.TURTLE, "Turtle");
                }


            } else if (entity.getType() == EntityType.WANDERING_TRADER) {
                if (config.getBoolean("Drop.WANDERING_TRADER") && x <= config.getInt("Chance.WANDERING_TRADER")) {
                    event.getDrops().add(LivingEntityHead.WANDERING_TRADER);
                    ItemStack.rename(LivingEntityHead.WANDERING_TRADER, "Wandering Trader");
                }


            } else if (entity.getType() == EntityType.WITCH) {
                if (config.getBoolean("Drop.WITCH") && x <= config.getInt("Chance.WITCH")) {
                    event.getDrops().add(LivingEntityHead.WITCH);
                    ItemStack.rename(LivingEntityHead.WITCH, "Witch");
                }


            } else if (entity.getType() == EntityType.WITHER) {
                if (config.getBoolean("Drop.WITHER") && x <= config.getInt("Chance.WITHER")) {
                    event.getDrops().add(LivingEntityHead.WITHER);
                    ItemStack.rename(LivingEntityHead.WITHER, "Witcher");
                }


            } else if (entity.getType() == EntityType.ZOGLIN) {
                if (config.getBoolean("Drop.ZOGLIN") && x <= config.getInt("Chance.ZOGLIN")) {
                    event.getDrops().add(LivingEntityHead.ZOGLIN);
                    ItemStack.rename(LivingEntityHead.ZOGLIN, "Zoglin");
                }


            } else if (entity.getType() == EntityType.ZOMBIE_HORSE) {
                if (config.getBoolean("Drop.ZOMBIE_HORSE") && x <= config.getInt("Chance.ZOMBIE_HORSE")) {
                    event.getDrops().add(LivingEntityHead.ZOMBIE_HORSE);
                    ItemStack.rename(LivingEntityHead.ZOMBIE_HORSE, "Zombie Horse");
                }


            } else if (entity.getType() == EntityType.ZOMBIFIED_PIGLIN) {
                if (config.getBoolean("Drop.ZOMBIFIED_PIGLIN") && x <= config.getInt("Chance.ZOMBIFIED_PIGLIN")) {
                    event.getDrops().add(LivingEntityHead.ZOMBIFIED_PIGLIN);
                    ItemStack.rename(LivingEntityHead.ZOMBIFIED_PIGLIN, "Zombified Piglin");
                }


            } else if (entity.getType() == EntityType.GHAST) {
                if (config.getBoolean("Drop.GHAST") && x <= config.getInt("Chance.GHAST")) {
                    event.getDrops().add(LivingEntityHead.GHAST);
                    ItemStack.rename(LivingEntityHead.GHAST, "Ghast");
                }


            } else if (entity.getType() == EntityType.ZOMBIE_VILLAGER) {
                if (config.getBoolean("Drop.ZOMBIE_VILLAGER") && x <= config.getInt("Chance.ZOMBIE_VILLAGER")) {
                    ZombieVillager zombieVillager = (ZombieVillager) event.getEntity();

                    switch (zombieVillager.getVillagerProfession()) {
                        case ARMORER:
                            event.getDrops().add(LivingEntityHead.ZOMBIE_VILLAGER_ARMORER);
                            ItemStack.rename(LivingEntityHead.ZOMBIE_VILLAGER_ARMORER, "Zombie Villager");

                            break;
                        case BUTCHER:
                            event.getDrops().add(LivingEntityHead.ZOMBIE_VILLAGER_BUTCHER);
                            ItemStack.rename(LivingEntityHead.ZOMBIE_VILLAGER_BUTCHER, "Zombie Villager");

                            break;
                        case CARTOGRAPHER:
                            event.getDrops().add(LivingEntityHead.ZOMBIE_VILLAGER_CARTOGRAPHER);
                            ItemStack.rename(LivingEntityHead.ZOMBIE_VILLAGER_CARTOGRAPHER, "Zombie Villager");

                            break;
                        case CLERIC:
                            event.getDrops().add(LivingEntityHead.ZOMBIE_VILLAGER_CLERIC);
                            ItemStack.rename(LivingEntityHead.ZOMBIE_VILLAGER_CLERIC, "Zombie Villager");

                            break;
                        case FARMER:
                            event.getDrops().add(LivingEntityHead.ZOMBIE_VILLAGER_FARMER);
                            ItemStack.rename(LivingEntityHead.ZOMBIE_VILLAGER_FARMER, "Zombie Villager");

                            break;
                        case FISHERMAN:
                            event.getDrops().add(LivingEntityHead.ZOMBIE_VILLAGER_FISHERMAN);
                            ItemStack.rename(LivingEntityHead.ZOMBIE_VILLAGER_FISHERMAN, "Zombie Villager");

                            break;
                        case FLETCHER:
                            event.getDrops().add(LivingEntityHead.ZOMBIE_VILLAGER_FLETCHER);
                            ItemStack.rename(LivingEntityHead.ZOMBIE_VILLAGER_FLETCHER, "Zombie Villager");

                            break;
                        case LIBRARIAN:
                            event.getDrops().add(LivingEntityHead.ZOMBIE_VILLAGER_LIBRARIAN);
                            ItemStack.rename(LivingEntityHead.ZOMBIE_VILLAGER_LIBRARIAN, "Zombie Villager");

                            break;
                        case SHEPHERD:
                            event.getDrops().add(LivingEntityHead.ZOMBIE_VILLAGER_SHEPHERD);
                            ItemStack.rename(LivingEntityHead.ZOMBIE_VILLAGER_SHEPHERD, "Zombie Villager");

                            break;
                        case WEAPONSMITH:
                            event.getDrops().add(LivingEntityHead.ZOMBIE_VILLAGER_WEAPONSMITH);
                            ItemStack.rename(LivingEntityHead.ZOMBIE_VILLAGER_WEAPONSMITH, "Zombie Villager");

                            break;
                        default:
                            event.getDrops().add(LivingEntityHead.ZOMBIE_VILLAGER_NULL);
                            ItemStack.rename(LivingEntityHead.ZOMBIE_VILLAGER_NULL, "Zombie Villager");
                            break;
                    }
                }


            } else if (entity.getType() == EntityType.VINDICATOR) {
                if (config.getBoolean("Drop.VINDICATOR") && x <= config.getInt("Chance.VINDICATOR")) {
                    event.getDrops().add(LivingEntityHead.VINDICATOR);
                    ItemStack.rename(LivingEntityHead.VINDICATOR, "Vindicator");
                }


            } else if (entity.getType() == EntityType.TRADER_LLAMA) {
                if (config.getBoolean("Drop.TRADER_LLAMA") && x <= config.getInt("Chance.TRADER_LLAMA")) {
                    TraderLlama traderLlama = (TraderLlama) event.getEntity();

                    switch (traderLlama.getColor()) {
                        case BROWN:
                            event.getDrops().add(LivingEntityHead.TRADER_LLAMA_BROWN);
                            ItemStack.rename(LivingEntityHead.TRADER_LLAMA_BROWN, "Llama");

                            break;
                        case WHITE:
                            event.getDrops().add(LivingEntityHead.TRADER_LLAMA_WHITE);
                            ItemStack.rename(LivingEntityHead.TRADER_LLAMA_WHITE, "Llama");

                            break;
                        case GRAY:
                            event.getDrops().add(LivingEntityHead.TRADER_LLAMA_GRAY);
                            ItemStack.rename(LivingEntityHead.TRADER_LLAMA_GRAY, "Llama");

                            break;
                        case CREAMY:
                            event.getDrops().add(LivingEntityHead.TRADER_LLAMA_CREAMY);
                            ItemStack.rename(LivingEntityHead.TRADER_LLAMA_CREAMY, "Llama");

                            break;
                    }
                }


            } else if (entity.getType() == EntityType.WOLF) {
                if (config.getBoolean("Drop.WOLF") && x <= config.getInt("Chance.WOLF")) {
                    Wolf wolf = (Wolf) event.getEntity();

                    if (wolf.isAngry()) {
                        event.getDrops().add(LivingEntityHead.WOLF_ANGRY);
                        ItemStack.rename(LivingEntityHead.WOLF_ANGRY, "Wolf");
                    } else {
                        event.getDrops().add(LivingEntityHead.WOLF);
                        ItemStack.rename(LivingEntityHead.WOLF, "Wolf");
                    }
                }


            } else if (entity.getType() == EntityType.VEX) {
                if (config.getBoolean("Drop.VEX") && x <= config.getInt("Chance.VEX")) {

                    Vex vex = (Vex) event.getEntity();
                    if (vex.isCharging()) {
                        event.getDrops().add(LivingEntityHead.VEX_CHARGE);
                        ItemStack.rename(LivingEntityHead.VEX_CHARGE, "Vex");
                    } else {
                        event.getDrops().add(LivingEntityHead.VEX);
                        ItemStack.rename(LivingEntityHead.VEX, "Vex");
                    }
                }


            }else if (event.getEntityType() == EntityType.VILLAGER){
                if (config.getBoolean("Drop.VILLAGER") && x <= config.getInt("Chance.VILLAGER")) {
                    Villager villager = (Villager) event.getEntity();

                    switch (villager.getProfession()) {
                        case WEAPONSMITH:
                            event.getDrops().add(LivingEntityHead.VILLAGER_WEAPONSMITH);
                            ItemStack.rename(LivingEntityHead.VILLAGER_WEAPONSMITH, "Villager");

                            break;
                        case SHEPHERD:
                            event.getDrops().add(LivingEntityHead.VILLAGER_SHEPHERD);
                            ItemStack.rename(LivingEntityHead.VILLAGER_SHEPHERD, "Villager");

                            break;
                        case LIBRARIAN:
                            event.getDrops().add(LivingEntityHead.VILLAGER_LIBRARIAN);
                            ItemStack.rename(LivingEntityHead.VILLAGER_LIBRARIAN, "Villager");

                            break;
                        case FLETCHER:
                            event.getDrops().add(LivingEntityHead.VILLAGER_FLETCHER);
                            ItemStack.rename(LivingEntityHead.VILLAGER_FLETCHER, "Villager");

                            break;
                        case FISHERMAN:
                            event.getDrops().add(LivingEntityHead.VILLAGER_FISHERMAN);
                            ItemStack.rename(LivingEntityHead.VILLAGER_FISHERMAN, "Villager");

                            break;
                        case FARMER:
                            event.getDrops().add(LivingEntityHead.VILLAGER_FARMER);
                            ItemStack.rename(LivingEntityHead.VILLAGER_FARMER, "Villager");

                            break;
                        case CLERIC:
                            event.getDrops().add(LivingEntityHead.VILLAGER_CLERIC);
                            ItemStack.rename(LivingEntityHead.VILLAGER_CLERIC, "Villager");

                            break;
                        case CARTOGRAPHER:
                            event.getDrops().add(LivingEntityHead.VILLAGER_CARTOGRAPHER);
                            ItemStack.rename(LivingEntityHead.VILLAGER_CARTOGRAPHER, "Villager");

                            break;
                        case BUTCHER:
                            event.getDrops().add(LivingEntityHead.VILLAGER_BUTCHER);
                            ItemStack.rename(LivingEntityHead.VILLAGER_BUTCHER, "Villager");

                            break;
                        case ARMORER:
                            event.getDrops().add(LivingEntityHead.VILLAGER_ARMORER);
                            ItemStack.rename(LivingEntityHead.VILLAGER_ARMORER, "Villager");

                            break;
                        default:
                            event.getDrops().add(LivingEntityHead.VILLAGER_NULL);
                            ItemStack.rename(LivingEntityHead.VILLAGER_NULL, "Villager");
                            break;
                    }
                }


            }else if (event.getEntityType() == EntityType.TROPICAL_FISH){
                if (config.getBoolean("Drop.TROPICAL_FISH") && x <= config.getInt("Chance.TROPICAL_FISH")) {
                    event.getDrops().add(LivingEntityHead.TROPICAL_FISH);
                    ItemStack.rename(LivingEntityHead.TROPICAL_FISH, "Tropical Fish");
                }


            }else if (event.getEntityType() == EntityType.PARROT){
                if (config.getBoolean("Drop.PARROT") && x <= config.getInt("Chance.PARROT")) {
                    Parrot parrot = (Parrot) event.getEntity();

                    switch (parrot.getVariant()) {
                        case BLUE:
                            event.getDrops().add(LivingEntityHead.PARROT_BLUE);
                            ItemStack.rename(LivingEntityHead.PARROT_BLUE, "Parrot");

                            break;
                        case CYAN:
                            event.getDrops().add(LivingEntityHead.PARROT_CYAN);
                            ItemStack.rename(LivingEntityHead.PARROT_CYAN, "Parrot");

                            break;
                        case GRAY:
                            event.getDrops().add(LivingEntityHead.PARROT_GRAY);
                            ItemStack.rename(LivingEntityHead.PARROT_GRAY, "Parrot");

                            break;
                        case RED:
                            event.getDrops().add(LivingEntityHead.PARROT_RED);
                            ItemStack.rename(LivingEntityHead.PARROT_RED, "Parrot");

                            break;
                        case GREEN:
                            event.getDrops().add(LivingEntityHead.PARROT_GREEN);
                            ItemStack.rename(LivingEntityHead.PARROT_GREEN, "Parrot");
                            break;
                    }
                }
            }


        }






    }
}