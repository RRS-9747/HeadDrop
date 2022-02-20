package me.rrs.Events;

import me.rrs.Database.LivingEntityHead;
import me.rrs.HeadDrop;
import me.rrs.util.ItemStack;
import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

import java.util.List;
import java.util.Random;



public class EntityDeath implements Listener {

    Entity entity;
    FileConfiguration config;
    Player killerPlayer;

    @EventHandler(priority = EventPriority.NORMAL)
    public void onEntityDropHeadEvent(EntityDeathEvent event) {
        this.entity = event.getEntity();
        this.config = HeadDrop.getInstance().getConfig();
        this.killerPlayer = event.getEntity().getKiller();
        boolean isInDisabledWorld = false;
        List<String> worldList = HeadDrop.getInstance().getConfig().getStringList("Config.Disable-Worlds");

        Random random = new Random();
        int x = random.nextInt(101);


        if (event.getEntity().getKiller() == killerPlayer) {



            for (String world : worldList) {
                World w = Bukkit.getWorld(world);
                if (event.getEntity().getWorld().equals(w)) {
                    isInDisabledWorld = true;
                }
            }


            if (!isInDisabledWorld) {
                if (entity.getType() == EntityType.AXOLOTL) {
                    if (config.getBoolean("AXOLOTL.Drop") && x <= config.getInt("AXOLOTL.Chance")) {
                        Axolotl axolotl = (Axolotl) event.getEntity();

                        switch (axolotl.getVariant()) {
                            case LUCY:
                                event.getDrops().add(LivingEntityHead.AXOLOTL_LUCY);
                                ItemStack.rename(LivingEntityHead.AXOLOTL_LUCY, config.getString("AXOLOTL.Name"));

                                break;
                            case BLUE:
                                event.getDrops().add(LivingEntityHead.AXOLOTL_BLUE);
                                ItemStack.rename(LivingEntityHead.AXOLOTL_BLUE, config.getString("AXOLOTL.Name"));

                                break;
                            case WILD:
                                event.getDrops().add(LivingEntityHead.AXOLOTL_WILD);
                                ItemStack.rename(LivingEntityHead.AXOLOTL_WILD, config.getString("AXOLOTL.Name"));

                                break;
                            case CYAN:
                                event.getDrops().add(LivingEntityHead.AXOLOTL_CYAN);
                                ItemStack.rename(LivingEntityHead.AXOLOTL_CYAN, config.getString("AXOLOTL.Name"));

                                break;
                            case GOLD:
                                event.getDrops().add(LivingEntityHead.AXOLOTL_GOLD);
                                ItemStack.rename(LivingEntityHead.AXOLOTL_GOLD, config.getString("AXOLOTL.Name"));
                                break;
                        }
                    }
                } else if (entity.getType() == EntityType.BAT) {
                    if (config.getBoolean("BAT.Drop") && x <= config.getInt("BAT.Chance")) {
                        event.getDrops().add(LivingEntityHead.BAT);
                        ItemStack.rename(LivingEntityHead.BAT, config.getString("BAT.Name"));
                    }


                } else if (entity.getType() == EntityType.BEE) {
                    if (config.getBoolean("BEE.Drop")) {
                        if (x <= config.getInt("BEE.Chance")) {
                            Bee bee = (Bee) event.getEntity();
                            if (bee.getAnger() > 0) {
                                event.getDrops().add(LivingEntityHead.BEE_Aware);
                                ItemStack.rename(LivingEntityHead.BEE_Aware, config.getString("BEE.Name"));
                            } else {
                                ItemStack.rename(LivingEntityHead.BEE, config.getString("BEE.Name"));
                                event.getDrops().add(LivingEntityHead.BEE);
                            }
                        }
                    }


                } else if (entity.getType() == EntityType.BLAZE) {
                    if (config.getBoolean("BLAZE.Drop") && x <= config.getInt("BLAZE.Chance")) {
                        event.getDrops().add(LivingEntityHead.BLAZE);
                        ItemStack.rename(LivingEntityHead.BLAZE, config.getString("BLAZE.Name"));
                    }


                } else if (entity.getType() == EntityType.CAT) {
                    if (config.getBoolean("CAT.Drop") && x <= config.getInt("CAT.Chance")) {
                        Cat cat = (Cat) event.getEntity();
                        switch (cat.getCatType()) {
                            case BLACK:
                                event.getDrops().add(LivingEntityHead.CAT_BLACK);
                                ItemStack.rename(LivingEntityHead.CAT_BLACK, config.getString("CAT.Name"));

                                break;
                            case BRITISH_SHORTHAIR:
                                event.getDrops().add(LivingEntityHead.CAT_BRITISH);
                                ItemStack.rename(LivingEntityHead.CAT_BRITISH, config.getString("CAT.Name"));

                                break;
                            case CALICO:
                                event.getDrops().add(LivingEntityHead.CAT_CALICO);
                                ItemStack.rename(LivingEntityHead.CAT_CALICO, config.getString("CAT.Name"));

                                break;
                            case JELLIE:
                                event.getDrops().add(LivingEntityHead.CAT_JELLIE);
                                ItemStack.rename(LivingEntityHead.CAT_JELLIE, config.getString("CAT.Name"));

                                break;
                            case PERSIAN:
                                event.getDrops().add(LivingEntityHead.CAT_PERSIAN);
                                ItemStack.rename(LivingEntityHead.CAT_PERSIAN, config.getString("CAT.Name"));

                                break;
                            case RAGDOLL:
                                event.getDrops().add(LivingEntityHead.CAT_RAGDOLL);
                                ItemStack.rename(LivingEntityHead.CAT_RAGDOLL, config.getString("CAT.Name"));

                                break;
                            case RED:
                                event.getDrops().add(LivingEntityHead.CAT_RED);
                                ItemStack.rename(LivingEntityHead.CAT_RED, config.getString("CAT.Name"));

                                break;
                            case SIAMESE:
                                event.getDrops().add(LivingEntityHead.CAT_SIAMESE);
                                ItemStack.rename(LivingEntityHead.CAT_SIAMESE, config.getString("CAT.Name"));

                                break;
                            case TABBY:
                                event.getDrops().add(LivingEntityHead.CAT_TABBY);
                                ItemStack.rename(LivingEntityHead.CAT_TABBY, config.getString("CAT.Name"));

                                break;
                            case WHITE:
                                event.getDrops().add(LivingEntityHead.CAT_WHITE);
                                ItemStack.rename(LivingEntityHead.CAT_WHITE, config.getString("CAT.Name"));
                                break;
                        }
                    }


                } else if (entity.getType() == EntityType.SPIDER) {
                    if (config.getBoolean("SPIDER.Drop") && x <= config.getInt("SPIDER.Chance")) {
                        event.getDrops().add(LivingEntityHead.SPIDER);
                        ItemStack.rename(LivingEntityHead.SPIDER, config.getString("SPIDER.Name"));
                    }


                } else if (entity.getType() == EntityType.CAVE_SPIDER) {
                    if (config.getBoolean("CAVE_SPIDER.Drop") && x <= config.getInt("CAVE_SPIDER.Chance")) {
                        event.getDrops().add(LivingEntityHead.CAVE_SPIDER);
                        ItemStack.rename(LivingEntityHead.CAVE_SPIDER, config.getString("CAVE_SPIDER.Name"));
                    }


                } else if (entity.getType() == EntityType.CHICKEN) {
                    if (config.getBoolean("CHICKEN.Drop") && x <= config.getInt("CHICKEN.Chance")) {
                        event.getDrops().add(LivingEntityHead.CHICKEN);
                        ItemStack.rename(LivingEntityHead.CHICKEN, config.getString("CHICKEN.Name"));
                    }


                } else if (entity.getType() == EntityType.COD) {
                    if (config.getBoolean("COD.Drop") && x <= config.getInt("COD.Chance")) {
                        event.getDrops().add(LivingEntityHead.COD);
                        ItemStack.rename(LivingEntityHead.COD, config.getString("COD.Name"));
                    }


                } else if (entity.getType() == EntityType.COW) {
                    if (config.getBoolean("COW.Drop") && x <= config.getInt("COW.Chance")) {
                        event.getDrops().add(LivingEntityHead.COW);
                        ItemStack.rename(LivingEntityHead.COW, config.getString("COW.Name"));
                    }


                } else if (entity.getType() == EntityType.DOLPHIN) {
                    if (config.getBoolean("DOLPHIN.Drop") && x <= config.getInt("DOLPHIN.Chance")) {
                        event.getDrops().add(LivingEntityHead.DOLPHIN);
                        ItemStack.rename(LivingEntityHead.DOLPHIN, config.getString("DOLPHIN.Name"));
                    }


                } else if (entity.getType() == EntityType.DONKEY) {
                    if (config.getBoolean("DONKEY.Drop") && x <= config.getInt("DONKEY.Chance")) {
                        event.getDrops().add(LivingEntityHead.DONKEY);
                        ItemStack.rename(LivingEntityHead.DONKEY, config.getString("DONKEY.Name"));
                    }


                } else if (entity.getType() == EntityType.DROWNED) {
                    if (config.getBoolean("DROWNED.Drop") && x <= config.getInt("DROWNED.Chance")) {
                        event.getDrops().add(LivingEntityHead.DROWNED);
                        ItemStack.rename(LivingEntityHead.DROWNED, config.getString("DROWNED.Name"));
                    }


                } else if (entity.getType() == EntityType.ELDER_GUARDIAN) {
                    if (config.getBoolean("ELDER_GUARDIAN.Drop") && x <= config.getInt("ELDER_GUARDIAN.Chance")) {
                        event.getDrops().add(LivingEntityHead.ELDER_GUARDIAN);
                        ItemStack.rename(LivingEntityHead.ELDER_GUARDIAN, config.getString("ELDER_GUARDIAN.Name"));
                    }


                } else if (entity.getType() == EntityType.ENDERMAN) {
                    if (config.getBoolean("ENDERMAN.Drop") && x <= config.getInt("ENDERMAN.Chance")) {
                        event.getDrops().add(LivingEntityHead.ENDERMAN);
                        ItemStack.rename(LivingEntityHead.ENDERMAN, config.getString("ENDERMAN.Name"));
                    }


                } else if (entity.getType() == EntityType.ENDERMITE) {
                    if (config.getBoolean("ENDERMITE.Drop") && x <= config.getInt("ENDERMITE.Chance")) {
                        event.getDrops().add(LivingEntityHead.ENDERMITE);
                        ItemStack.rename(LivingEntityHead.ENDERMITE, config.getString("ENDERMITE.Name"));
                    }


                } else if (entity.getType() == EntityType.EVOKER) {
                    if (config.getBoolean("EVOKER.Drop") && x <= config.getInt("EVOKER.Chance")) {
                        event.getDrops().add(LivingEntityHead.EVOKER);
                        ItemStack.rename(LivingEntityHead.EVOKER, config.getString("EVOKER.Name"));
                    }


                } else if (entity.getType() == EntityType.FOX) {
                    if (config.getBoolean("FOX.Drop") && x <= config.getInt("FOX.Chance")) {
                        event.getDrops().add(LivingEntityHead.FOX);
                        ItemStack.rename(LivingEntityHead.FOX, config.getString("FOX.Name"));
                    }


                } else if (entity.getType() == EntityType.GIANT) {
                    if (config.getBoolean("GIANT.Drop") && x <= config.getInt("GIANT.Chance")) {
                        event.getDrops().add(LivingEntityHead.GIANT);
                        ItemStack.rename(LivingEntityHead.GIANT, config.getString("GIANT.Name"));
                    }


                } else if (entity.getType() == EntityType.GLOW_SQUID) {
                    if (config.getBoolean("GLOW_SQUID.Drop") && x <= config.getInt("GLOW_SQUID.Chance")) {
                        event.getDrops().add(LivingEntityHead.GLOW_SQUID);
                        ItemStack.rename(LivingEntityHead.GLOW_SQUID, config.getString("GLOW_SQUID.Name"));
                    }


                } else if (entity.getType() == EntityType.GOAT) {
                    if (config.getBoolean("GOAT.Drop") && x <= config.getInt("GOAT.Chance")) {
                        event.getDrops().add(LivingEntityHead.GOAT);
                        ItemStack.rename(LivingEntityHead.GOAT, config.getString("GOAT.Name"));
                    }


                } else if (entity.getType() == EntityType.GUARDIAN) {
                    if (config.getBoolean("GUARDIAN.Drop") && x <= config.getInt("GUARDIAN.Chance")) {
                        event.getDrops().add(LivingEntityHead.GUARDIAN);
                        ItemStack.rename(LivingEntityHead.GUARDIAN, config.getString("GUARDIAN.Name"));
                    }


                } else if (entity.getType() == EntityType.HOGLIN) {
                    if (config.getBoolean("HOGLIN.Drop") && x <= config.getInt("HOGLIN.Chance")) {
                        event.getDrops().add(LivingEntityHead.HOGLIN);
                        ItemStack.rename(LivingEntityHead.HOGLIN, config.getString("HOGLIN.Name"));
                    }


                } else if (entity.getType() == EntityType.HORSE) {
                    if (config.getBoolean("HORSE.Drop") && x <= config.getInt("HORSE.Chance")) {
                        Horse horse = (Horse) event.getEntity();

                        switch (horse.getColor()) {
                            case WHITE:
                                event.getDrops().add(LivingEntityHead.HORSE_WHITE);
                                ItemStack.rename(LivingEntityHead.HORSE_WHITE, config.getString("HORSE.Name"));

                                break;
                            case CREAMY:
                                event.getDrops().add(LivingEntityHead.HORSE_CREAMY);
                                ItemStack.rename(LivingEntityHead.HORSE_CREAMY, config.getString("HORSE.Name"));

                                break;
                            case CHESTNUT:
                                event.getDrops().add(LivingEntityHead.HORSE_CHESTNUT);
                                ItemStack.rename(LivingEntityHead.HORSE_CHESTNUT, config.getString("HORSE.Name"));

                                break;
                            case BROWN:
                                event.getDrops().add(LivingEntityHead.HORSE_BROWN);
                                ItemStack.rename(LivingEntityHead.HORSE_BROWN, config.getString("HORSE.Name"));

                                break;
                            case BLACK:
                                event.getDrops().add(LivingEntityHead.HORSE_BLACK);
                                ItemStack.rename(LivingEntityHead.HORSE_BLACK, config.getString("HORSE.Name"));

                                break;
                            case GRAY:
                                event.getDrops().add(LivingEntityHead.HORSE_GRAY);
                                ItemStack.rename(LivingEntityHead.HORSE_GRAY, config.getString("HORSE.Name"));

                                break;
                            case DARK_BROWN:
                                event.getDrops().add(LivingEntityHead.HORSE_DARK_BROWN);
                                ItemStack.rename(LivingEntityHead.HORSE_DARK_BROWN, config.getString("HORSE.Name"));
                                break;
                        }
                    }


                } else if (entity.getType() == EntityType.HUSK) {
                    if (config.getBoolean("HUSK.Drop") && x <= config.getInt("HUSK.Chance")) {
                        event.getDrops().add(LivingEntityHead.HUSK);
                        ItemStack.rename(LivingEntityHead.HUSK, config.getString("HUSK.Name"));
                    }


                } else if (entity.getType() == EntityType.ILLUSIONER) {
                    if (config.getBoolean("ILLUSIONER.Drop") && x <= config.getInt("ILLUSIONER.Chance")) {
                        event.getDrops().add(LivingEntityHead.ILLUSIONER);
                        ItemStack.rename(LivingEntityHead.ILLUSIONER, config.getString("ILLUSIONER.Name"));
                    }


                } else if (entity.getType() == EntityType.IRON_GOLEM) {
                    if (config.getBoolean("IRON_GOLEM.Drop") && x <= config.getInt("IRON_GOLEM.Chance")) {
                        event.getDrops().add(LivingEntityHead.IRON_GOLEM);
                        ItemStack.rename(LivingEntityHead.IRON_GOLEM, config.getString("IRON_GOLEM.Name"));
                    }


                } else if (entity.getType() == EntityType.LLAMA) {
                    if (config.getBoolean("LLAMA.Drop") && x <= config.getInt("LLAMA.Chance")) {
                        Llama llama = (Llama) event.getEntity();

                        switch (llama.getColor()) {
                            case BROWN:
                                event.getDrops().add(LivingEntityHead.LLAMA_BROWN);
                                ItemStack.rename(LivingEntityHead.LLAMA_BROWN, config.getString("LLAMA.Name"));

                                break;
                            case GRAY:
                                event.getDrops().add(LivingEntityHead.LLAMA_GRAY);
                                ItemStack.rename(LivingEntityHead.LLAMA_GRAY, config.getString("LLAMA.Name"));

                                break;
                            case CREAMY:
                                event.getDrops().add(LivingEntityHead.LLAMA_CREAMY);
                                ItemStack.rename(LivingEntityHead.LLAMA_CREAMY, config.getString("LLAMA.Name"));

                                break;
                            case WHITE:
                                event.getDrops().add(LivingEntityHead.LLAMA_WHITE);
                                ItemStack.rename(LivingEntityHead.LLAMA_WHITE, config.getString("LLAMA.Name"));
                                break;
                        }
                    }


                } else if (entity.getType() == EntityType.MAGMA_CUBE) {
                    if (config.getBoolean("MAGMA_CUBE.Drop") && x <= config.getInt("MAGMA_CUBE.Chance")) {
                        event.getDrops().add(LivingEntityHead.MAGMA_CUBE);
                        ItemStack.rename(LivingEntityHead.MAGMA_CUBE, config.getString("MAGMA_CUBE.Name"));
                    }


                } else if (entity.getType() == EntityType.MUSHROOM_COW) {
                    if (config.getBoolean("MUSHROOM_COW.Drop") && x <= config.getInt("MUSHROOM_COW.Chance")) {
                        MushroomCow mushroomCow = (MushroomCow) event.getEntity();

                        switch (mushroomCow.getVariant()) {
                            case RED:
                                event.getDrops().add(LivingEntityHead.MUSHROOM_COW_RED);
                                ItemStack.rename(LivingEntityHead.MUSHROOM_COW_RED, config.getString("MUSHROOM_COW.Name"));

                                break;
                            case BROWN:
                                event.getDrops().add(LivingEntityHead.MUSHROOM_COW_BROWN);
                                ItemStack.rename(LivingEntityHead.MUSHROOM_COW_BROWN, config.getString("MUSHROOM_COW.Name"));
                                break;
                        }
                    }


                } else if (entity.getType() == EntityType.MULE) {
                    if (config.getBoolean("MULE.Drop") && x <= config.getInt("MULE.Chance")) {
                        event.getDrops().add(LivingEntityHead.MULE);
                        ItemStack.rename(LivingEntityHead.MULE, config.getString("MULE.Name"));
                    }


                } else if (entity.getType() == EntityType.OCELOT) {
                    if (config.getBoolean("OCELOT.Drop") && x <= config.getInt("OCELOT.Chance")) {
                        event.getDrops().add(LivingEntityHead.OCELOT);
                        ItemStack.rename(LivingEntityHead.OCELOT, config.getString("OCELOT.Name"));
                    }


                } else if (entity.getType() == EntityType.PANDA) {
                    if (config.getBoolean("PANDA.Drop") && x <= config.getInt("PANDA.Chance")) {
                        Panda panda = (Panda) event.getEntity();
                        switch (panda.getMainGene()) {
                            case BROWN:
                                event.getDrops().add(LivingEntityHead.PANDA_BROWN);
                                ItemStack.rename(LivingEntityHead.PANDA_BROWN, config.getString("PANDA.Name"));
                                break;
                            default:
                                event.getDrops().add(LivingEntityHead.PANDA);
                                ItemStack.rename(LivingEntityHead.PANDA, config.getString("PANDA.Name"));
                                break;
                        }
                    }


                } else if (entity.getType() == EntityType.PHANTOM) {
                    if (config.getBoolean("PHANTOM.Drop") && x <= config.getInt("PHANTOM.Chance")) {
                        event.getDrops().add(LivingEntityHead.PHANTOM);
                        ItemStack.rename(LivingEntityHead.PHANTOM, config.getString("PHANTOM.Name"));
                    }


                } else if (entity.getType() == EntityType.PIG) {
                    if (config.getBoolean("PIG.Drop") && x <= config.getInt("PIG.Chance")) {
                        event.getDrops().add(LivingEntityHead.PIG);
                        ItemStack.rename(LivingEntityHead.PIG, config.getString("PIG.Name"));
                    }


                } else if (entity.getType() == EntityType.PIGLIN) {
                    if (config.getBoolean("PIGLIN.Drop") && x <= config.getInt("PIGLIN.Chance")) {
                        event.getDrops().add(LivingEntityHead.PIGLIN);
                        ItemStack.rename(LivingEntityHead.PIGLIN, config.getString("PIGLIN.Name"));
                    }


                } else if (entity.getType() == EntityType.PIGLIN_BRUTE) {
                    if (config.getBoolean("PIGLIN_BRUTE.Drop") && x <= config.getInt("PIGLIN_BRUTE.Chance")) {
                        event.getDrops().add(LivingEntityHead.PIGLIN_BRUTE);
                        ItemStack.rename(LivingEntityHead.PIGLIN_BRUTE, config.getString("PIGLIN_BRUTE.Name"));
                    }


                } else if (entity.getType() == EntityType.PILLAGER) {
                    if (config.getBoolean("PILLAGER.Drop") && x <= config.getInt("PILLAGER.Chance")) {
                        event.getDrops().add(LivingEntityHead.PILLAGER);
                        ItemStack.rename(LivingEntityHead.PILLAGER, config.getString("PILLAGER.Name"));
                    }


                } else if (entity.getType() == EntityType.POLAR_BEAR) {
                    if (config.getBoolean("POLAR_BEAR.Drop") && x <= config.getInt("POLAR_BEAR.Chance")) {
                        event.getDrops().add(LivingEntityHead.POLAR_BEAR);
                        ItemStack.rename(LivingEntityHead.POLAR_BEAR, config.getString("POLAR_BEAR.Name"));
                    }


                } else if (entity.getType() == EntityType.PUFFERFISH) {
                    if (config.getBoolean("PUFFERFISH.Drop") && x <= config.getInt("PUFFERFISH.Chance")) {
                        event.getDrops().add(LivingEntityHead.PUFFERFISH);
                        ItemStack.rename(LivingEntityHead.PUFFERFISH, config.getString("PUFFERFISH.Name"));
                    }


                } else if (entity.getType() == EntityType.RABBIT) {
                    if (config.getBoolean("RABBIT.Drop") && x <= config.getInt("RABBIT.Chance")) {
                        event.getDrops().add(LivingEntityHead.RABBIT);
                        ItemStack.rename(LivingEntityHead.RABBIT, config.getString("RABBIT.Name"));
                    }


                } else if (entity.getType() == EntityType.RAVAGER) {
                    if (config.getBoolean("RAVAGER.Drop") && x <= config.getInt("RAVAGER.Chance")) {
                        event.getDrops().add(LivingEntityHead.RAVAGER);
                        ItemStack.rename(LivingEntityHead.RAVAGER, config.getString("RAVAGER.Name"));
                    }


                } else if (entity.getType() == EntityType.SALMON) {
                    if (config.getBoolean("SALMON.Drop") && x <= config.getInt("SALMON.Chance")) {
                        event.getDrops().add(LivingEntityHead.SALMON);
                        ItemStack.rename(LivingEntityHead.SALMON, config.getString("SALMON.Name"));
                    }


                } else if (entity.getType() == EntityType.SHEEP) {
                    if (config.getBoolean("SHEEP.Drop") && x <= config.getInt("SHEEP.Chance")) {
                        Sheep sheep = (Sheep) event.getEntity();

                        if (sheep.getColor() == DyeColor.WHITE) {
                            event.getDrops().add(LivingEntityHead.SHEEP_WHITE);
                            ItemStack.rename(LivingEntityHead.SHEEP_WHITE, config.getString("SHEEP.Name"));

                        } else if (sheep.getColor() == DyeColor.BLACK) {
                            event.getDrops().add(LivingEntityHead.SHEEP_BLACK);
                            ItemStack.rename(LivingEntityHead.SHEEP_BLACK, config.getString("SHEEP.Name"));

                        } else if (sheep.getColor() == DyeColor.BLACK) {
                            event.getDrops().add(LivingEntityHead.SHEEP_BLACK);
                            ItemStack.rename(LivingEntityHead.SHEEP_BLACK, config.getString("SHEEP.Name"));

                        } else if (sheep.getColor() == DyeColor.BROWN) {
                            event.getDrops().add(LivingEntityHead.SHEEP_BROWN);
                            ItemStack.rename(LivingEntityHead.SHEEP_BROWN, config.getString("SHEEP.Name"));

                        } else if (sheep.getColor() == DyeColor.BLUE) {
                            event.getDrops().add(LivingEntityHead.SHEEP_BLUE);
                            ItemStack.rename(LivingEntityHead.SHEEP_BLUE, config.getString("SHEEP.Name"));

                        } else if (sheep.getColor() == DyeColor.CYAN) {
                            event.getDrops().add(LivingEntityHead.SHEEP_CYAN);
                            ItemStack.rename(LivingEntityHead.SHEEP_CYAN, config.getString("SHEEP.Name"));

                        } else if (sheep.getColor() == DyeColor.GRAY) {
                            event.getDrops().add(LivingEntityHead.SHEEP_GRAY);
                            ItemStack.rename(LivingEntityHead.SHEEP_GRAY, config.getString("SHEEP.Name"));

                        } else if (sheep.getColor() == DyeColor.GREEN) {
                            event.getDrops().add(LivingEntityHead.SHEEP_GREEN);
                            ItemStack.rename(LivingEntityHead.SHEEP_GREEN, config.getString("SHEEP.Name"));

                        } else if (sheep.getColor() == DyeColor.LIGHT_BLUE) {
                            event.getDrops().add(LivingEntityHead.SHEEP_LIGHT_BLUE);
                            ItemStack.rename(LivingEntityHead.SHEEP_LIGHT_BLUE, config.getString("SHEEP.Name"));

                        } else if (sheep.getColor() == DyeColor.LIGHT_GRAY) {
                            event.getDrops().add(LivingEntityHead.SHEEP_LIGHT_GRAY);
                            ItemStack.rename(LivingEntityHead.SHEEP_LIGHT_GRAY, config.getString("SHEEP.Name"));

                        } else if (sheep.getColor() == DyeColor.LIME) {
                            event.getDrops().add(LivingEntityHead.SHEEP_LIME);
                            ItemStack.rename(LivingEntityHead.SHEEP_LIME, config.getString("SHEEP.Name"));

                        } else if (sheep.getColor() == DyeColor.MAGENTA) {
                            event.getDrops().add(LivingEntityHead.SHEEP_MAGENTA);
                            ItemStack.rename(LivingEntityHead.SHEEP_MAGENTA, config.getString("SHEEP.Name"));

                        } else if (sheep.getColor() == DyeColor.ORANGE) {
                            event.getDrops().add(LivingEntityHead.SHEEP_ORANGE);
                            ItemStack.rename(LivingEntityHead.SHEEP_ORANGE, config.getString("SHEEP.Name"));

                        } else if (sheep.getColor() == DyeColor.PINK) {
                            event.getDrops().add(LivingEntityHead.SHEEP_PINK);
                            ItemStack.rename(LivingEntityHead.SHEEP_PINK, config.getString("SHEEP.Name"));

                        } else if (sheep.getColor() == DyeColor.PURPLE) {
                            event.getDrops().add(LivingEntityHead.SHEEP_PURPLE);
                            ItemStack.rename(LivingEntityHead.SHEEP_PURPLE, config.getString("SHEEP.Name"));

                        } else if (sheep.getColor() == DyeColor.RED) {
                            event.getDrops().add(LivingEntityHead.SHEEP_RED);
                            ItemStack.rename(LivingEntityHead.SHEEP_RED, config.getString("SHEEP.Name"));

                        } else if (sheep.getColor() == DyeColor.YELLOW) {
                            event.getDrops().add(LivingEntityHead.SHEEP_YELLOW);
                            ItemStack.rename(LivingEntityHead.SHEEP_YELLOW, config.getString("SHEEP.Name"));
                        }
                    }


                } else if (entity.getType() == EntityType.SHULKER) {
                    if (config.getBoolean("SHULKER.Drop") && x <= config.getInt("SHULKER.Chance")) {
                        event.getDrops().add(LivingEntityHead.SHULKER);
                        ItemStack.rename(LivingEntityHead.SHULKER, config.getString("SHULKER.Name"));
                    }


                } else if (entity.getType() == EntityType.SILVERFISH) {
                    if (config.getBoolean("SILVERFISH.Drop") && x <= config.getInt("SILVERFISH.Chance")) {
                        event.getDrops().add(LivingEntityHead.SILVERFISH);
                        ItemStack.rename(LivingEntityHead.SILVERFISH, config.getString("SILVERFISH.Name"));
                    }


                } else if (entity.getType() == EntityType.SKELETON_HORSE) {
                    if (config.getBoolean("SKELETON_HORSE.Drop") && x <= config.getInt("SKELETON_HORSE.Chance")) {
                        event.getDrops().add(LivingEntityHead.SKELETON_HORSE);
                        ItemStack.rename(LivingEntityHead.SKELETON_HORSE, config.getString("SKELETON_HORSE.Name"));
                    }


                } else if (entity.getType() == EntityType.SLIME) {
                    if (config.getBoolean("SLIME.Drop") && x <= config.getInt("SLIME.Chance")) {
                        event.getDrops().add(LivingEntityHead.SLIME);
                        ItemStack.rename(LivingEntityHead.SLIME, config.getString("SLIME.Name"));
                    }


                } else if (entity.getType() == EntityType.SNOWMAN) {
                    if (config.getBoolean("SNOW_GOLEM.Drop") && x <= config.getInt("SNOW_GOLEM.Chance")) {
                        event.getDrops().add(LivingEntityHead.SNOWMAN);
                        ItemStack.rename(LivingEntityHead.SNOWMAN, config.getString("SNOW_GOLEM.Name"));
                    }


                } else if (entity.getType() == EntityType.SQUID) {
                    if (config.getBoolean("SQUID.Drop") && x <= config.getInt("SQUID.Chance")) {
                        event.getDrops().add(LivingEntityHead.SQUID);
                        ItemStack.rename(LivingEntityHead.SQUID, config.getString("SQUID.Name"));
                    }


                } else if (entity.getType() == EntityType.STRAY) {
                    if (config.getBoolean("STRAY.Drop") && x <= config.getInt("STRAY.Chance")) {
                        event.getDrops().add(LivingEntityHead.STRAY);
                        ItemStack.rename(LivingEntityHead.STRAY, config.getString("STRAY.Name"));
                    }


                } else if (entity.getType() == EntityType.STRIDER) {
                    if (config.getBoolean("STRIDER.Drop") && x <= config.getInt("STRIDER.Chance")) {
                        event.getDrops().add(LivingEntityHead.STRIDER);
                        ItemStack.rename(LivingEntityHead.STRIDER, config.getString("STRIDER.Name"));
                    }


                } else if (entity.getType() == EntityType.TURTLE) {
                    if (config.getBoolean("TURTLE.Drop") && x <= config.getInt("TURTLE.Chance")) {
                        event.getDrops().add(LivingEntityHead.TURTLE);
                        ItemStack.rename(LivingEntityHead.TURTLE, config.getString("TURTLE.Name"));
                    }


                } else if (entity.getType() == EntityType.WANDERING_TRADER) {
                    if (config.getBoolean("WANDERING_TRADER.Drop") && x <= config.getInt("WANDERING_TRADER.Chance")) {
                        event.getDrops().add(LivingEntityHead.WANDERING_TRADER);
                        ItemStack.rename(LivingEntityHead.WANDERING_TRADER, config.getString("WANDERING_TRADER.Name"));
                    }


                } else if (entity.getType() == EntityType.WITCH) {
                    if (config.getBoolean("WITCH.Drop") && x <= config.getInt("WITCH.Chance")) {
                        event.getDrops().add(LivingEntityHead.WITCH);
                        ItemStack.rename(LivingEntityHead.WITCH, config.getString("WITCH.Name"));
                    }


                } else if (entity.getType() == EntityType.WITHER) {
                    if (config.getBoolean("WITHER.Drop") && x <= config.getInt("WITHER.Chance")) {
                        event.getDrops().add(LivingEntityHead.WITHER);
                        ItemStack.rename(LivingEntityHead.WITHER, config.getString("WITHER.Name"));
                    }


                } else if (entity.getType() == EntityType.ZOGLIN) {
                    if (config.getBoolean("ZOGLIN.Drop") && x <= config.getInt("ZOGLIN.Chance")) {
                        event.getDrops().add(LivingEntityHead.ZOGLIN);
                        ItemStack.rename(LivingEntityHead.ZOGLIN, config.getString("ZOGLIN.Name"));
                    }


                } else if (entity.getType() == EntityType.ZOMBIE_HORSE) {
                    if (config.getBoolean("ZOMBIE_HORSE.Drop") && x <= config.getInt("ZOMBIE_HORSE.Chance")) {
                        event.getDrops().add(LivingEntityHead.ZOMBIE_HORSE);
                        ItemStack.rename(LivingEntityHead.ZOMBIE_HORSE, config.getString("ZOMBIE_HORSE.Name"));
                    }


                } else if (entity.getType() == EntityType.ZOMBIFIED_PIGLIN) {
                    if (config.getBoolean("ZOMBIFIED_PIGLIN.Drop") && x <= config.getInt("ZOMBIFIED_PIGLIN.Chance")) {
                        event.getDrops().add(LivingEntityHead.ZOMBIFIED_PIGLIN);
                        ItemStack.rename(LivingEntityHead.ZOMBIFIED_PIGLIN, config.getString("ZOMBIFIED_PIGLIN.Name"));
                    }


                } else if (entity.getType() == EntityType.GHAST) {
                    if (config.getBoolean("GHAST.Drop") && x <= config.getInt("GHAST.Chance")) {
                        event.getDrops().add(LivingEntityHead.GHAST);
                        ItemStack.rename(LivingEntityHead.GHAST, config.getString("GHAST.Name"));
                    }


                } else if (entity.getType() == EntityType.ZOMBIE_VILLAGER) {
                    if (config.getBoolean("ZOMBIE_VILLAGER.Drop") && x <= config.getInt("ZOMBIE_VILLAGER.Chance")) {
                        ZombieVillager zombieVillager = (ZombieVillager) event.getEntity();

                        switch (zombieVillager.getVillagerProfession()) {
                            case ARMORER:
                                event.getDrops().add(LivingEntityHead.ZOMBIE_VILLAGER_ARMORER);
                                ItemStack.rename(LivingEntityHead.ZOMBIE_VILLAGER_ARMORER, config.getString("ZOMBIE_VILLAGER.Name"));

                                break;
                            case BUTCHER:
                                event.getDrops().add(LivingEntityHead.ZOMBIE_VILLAGER_BUTCHER);
                                ItemStack.rename(LivingEntityHead.ZOMBIE_VILLAGER_BUTCHER, config.getString("ZOMBIE_VILLAGER.Name"));

                                break;
                            case CARTOGRAPHER:
                                event.getDrops().add(LivingEntityHead.ZOMBIE_VILLAGER_CARTOGRAPHER);
                                ItemStack.rename(LivingEntityHead.ZOMBIE_VILLAGER_CARTOGRAPHER, config.getString("ZOMBIE_VILLAGER.Name"));

                                break;
                            case CLERIC:
                                event.getDrops().add(LivingEntityHead.ZOMBIE_VILLAGER_CLERIC);
                                ItemStack.rename(LivingEntityHead.ZOMBIE_VILLAGER_CLERIC, config.getString("ZOMBIE_VILLAGER.Name"));

                                break;
                            case FARMER:
                                event.getDrops().add(LivingEntityHead.ZOMBIE_VILLAGER_FARMER);
                                ItemStack.rename(LivingEntityHead.ZOMBIE_VILLAGER_FARMER, config.getString("ZOMBIE_VILLAGER.Name"));

                                break;
                            case FISHERMAN:
                                event.getDrops().add(LivingEntityHead.ZOMBIE_VILLAGER_FISHERMAN);
                                ItemStack.rename(LivingEntityHead.ZOMBIE_VILLAGER_FISHERMAN, config.getString("ZOMBIE_VILLAGER.Name"));

                                break;
                            case FLETCHER:
                                event.getDrops().add(LivingEntityHead.ZOMBIE_VILLAGER_FLETCHER);
                                ItemStack.rename(LivingEntityHead.ZOMBIE_VILLAGER_FLETCHER, config.getString("ZOMBIE_VILLAGER.Name"));

                                break;
                            case LIBRARIAN:
                                event.getDrops().add(LivingEntityHead.ZOMBIE_VILLAGER_LIBRARIAN);
                                ItemStack.rename(LivingEntityHead.ZOMBIE_VILLAGER_LIBRARIAN, config.getString("ZOMBIE_VILLAGER.Name"));

                                break;
                            case SHEPHERD:
                                event.getDrops().add(LivingEntityHead.ZOMBIE_VILLAGER_SHEPHERD);
                                ItemStack.rename(LivingEntityHead.ZOMBIE_VILLAGER_SHEPHERD, config.getString("ZOMBIE_VILLAGER.Name"));

                                break;
                            case WEAPONSMITH:
                                event.getDrops().add(LivingEntityHead.ZOMBIE_VILLAGER_WEAPONSMITH);
                                ItemStack.rename(LivingEntityHead.ZOMBIE_VILLAGER_WEAPONSMITH, config.getString("ZOMBIE_VILLAGER.Name"));

                                break;
                            default:
                                event.getDrops().add(LivingEntityHead.ZOMBIE_VILLAGER_NULL);
                                ItemStack.rename(LivingEntityHead.ZOMBIE_VILLAGER_NULL, config.getString("ZOMBIE_VILLAGER.Name"));
                                break;
                        }
                    }


                } else if (entity.getType() == EntityType.VINDICATOR) {
                    if (config.getBoolean("VINDICATOR.Drop") && x <= config.getInt("VINDICATOR.Chance")) {
                        event.getDrops().add(LivingEntityHead.VINDICATOR);
                        ItemStack.rename(LivingEntityHead.VINDICATOR, config.getString("VINDICATOR.Name"));
                    }


                } else if (entity.getType() == EntityType.TRADER_LLAMA) {
                    if (config.getBoolean("TRADER_LLAMA.Drop") && x <= config.getInt("TRADER_LLAMA.Chance.Name")) {
                        TraderLlama traderLlama = (TraderLlama) event.getEntity();

                        switch (traderLlama.getColor()) {
                            case BROWN:
                                event.getDrops().add(LivingEntityHead.TRADER_LLAMA_BROWN);
                                ItemStack.rename(LivingEntityHead.TRADER_LLAMA_BROWN, config.getString("TRADER_LLAMA.Name"));

                                break;
                            case WHITE:
                                event.getDrops().add(LivingEntityHead.TRADER_LLAMA_WHITE);
                                ItemStack.rename(LivingEntityHead.TRADER_LLAMA_WHITE, config.getString("TRADER_LLAMA.Name"));

                                break;
                            case GRAY:
                                event.getDrops().add(LivingEntityHead.TRADER_LLAMA_GRAY);
                                ItemStack.rename(LivingEntityHead.TRADER_LLAMA_GRAY, config.getString("TRADER_LLAMA.Name"));

                                break;
                            case CREAMY:
                                event.getDrops().add(LivingEntityHead.TRADER_LLAMA_CREAMY);
                                ItemStack.rename(LivingEntityHead.TRADER_LLAMA_CREAMY, config.getString("TRADER_LLAMA.Name"));

                                break;
                        }
                    }


                } else if (entity.getType() == EntityType.WOLF) {
                    if (config.getBoolean("WOLF.Drop") && x <= config.getInt("WOLF.Chance")) {
                        Wolf wolf = (Wolf) event.getEntity();

                        if (wolf.isAngry()) {
                            event.getDrops().add(LivingEntityHead.WOLF_ANGRY);
                            ItemStack.rename(LivingEntityHead.WOLF_ANGRY, config.getString("WOLF.Name"));
                        } else {
                            event.getDrops().add(LivingEntityHead.WOLF);
                            ItemStack.rename(LivingEntityHead.WOLF, config.getString("WOLF.Name"));
                        }
                    }


                } else if (entity.getType() == EntityType.VEX) {
                    if (config.getBoolean("VEX.Drop") && x <= config.getInt("VEX.Chance")) {

                        Vex vex = (Vex) event.getEntity();
                        if (vex.isCharging()) {
                            event.getDrops().add(LivingEntityHead.VEX_CHARGE);
                            ItemStack.rename(LivingEntityHead.VEX_CHARGE, config.getString("VEX.Name"));
                        } else {
                            event.getDrops().add(LivingEntityHead.VEX);
                            ItemStack.rename(LivingEntityHead.VEX, config.getString("VEX.Name"));
                        }
                    }


                } else if (event.getEntityType() == EntityType.VILLAGER) {
                    if (config.getBoolean("VILLAGER.Drop") && x <= config.getInt("VILLAGER.Chance")) {
                        Villager villager = (Villager) event.getEntity();

                        switch (villager.getProfession()) {
                            case WEAPONSMITH:
                                event.getDrops().add(LivingEntityHead.VILLAGER_WEAPONSMITH);
                                ItemStack.rename(LivingEntityHead.VILLAGER_WEAPONSMITH, config.getString("VILLAGER.Name"));

                                break;
                            case SHEPHERD:
                                event.getDrops().add(LivingEntityHead.VILLAGER_SHEPHERD);
                                ItemStack.rename(LivingEntityHead.VILLAGER_SHEPHERD, config.getString("VILLAGER.Name"));

                                break;
                            case LIBRARIAN:
                                event.getDrops().add(LivingEntityHead.VILLAGER_LIBRARIAN);
                                ItemStack.rename(LivingEntityHead.VILLAGER_LIBRARIAN, config.getString("VILLAGER.Name"));

                                break;
                            case FLETCHER:
                                event.getDrops().add(LivingEntityHead.VILLAGER_FLETCHER);
                                ItemStack.rename(LivingEntityHead.VILLAGER_FLETCHER, config.getString("VILLAGER.Name"));

                                break;
                            case FISHERMAN:
                                event.getDrops().add(LivingEntityHead.VILLAGER_FISHERMAN);
                                ItemStack.rename(LivingEntityHead.VILLAGER_FISHERMAN, config.getString("VILLAGER.Name"));

                                break;
                            case FARMER:
                                event.getDrops().add(LivingEntityHead.VILLAGER_FARMER);
                                ItemStack.rename(LivingEntityHead.VILLAGER_FARMER, config.getString("VILLAGER.Name"));

                                break;
                            case CLERIC:
                                event.getDrops().add(LivingEntityHead.VILLAGER_CLERIC);
                                ItemStack.rename(LivingEntityHead.VILLAGER_CLERIC, config.getString("VILLAGER.Name"));

                                break;
                            case CARTOGRAPHER:
                                event.getDrops().add(LivingEntityHead.VILLAGER_CARTOGRAPHER);
                                ItemStack.rename(LivingEntityHead.VILLAGER_CARTOGRAPHER, config.getString("VILLAGER.Name"));

                                break;
                            case BUTCHER:
                                event.getDrops().add(LivingEntityHead.VILLAGER_BUTCHER);
                                ItemStack.rename(LivingEntityHead.VILLAGER_BUTCHER, config.getString("VILLAGER.Name"));

                                break;
                            case ARMORER:
                                event.getDrops().add(LivingEntityHead.VILLAGER_ARMORER);
                                ItemStack.rename(LivingEntityHead.VILLAGER_ARMORER, config.getString("VILLAGER.Name"));

                                break;
                            default:
                                event.getDrops().add(LivingEntityHead.VILLAGER_NULL);
                                ItemStack.rename(LivingEntityHead.VILLAGER_NULL, config.getString("VILLAGER.Name"));
                                break;
                        }
                    }


                } else if (event.getEntityType() == EntityType.TROPICAL_FISH) {
                    if (config.getBoolean("TROPICAL_FISH.Drop") && x <= config.getInt("TROPICAL_FISH.Chance")) {
                        event.getDrops().add(LivingEntityHead.TROPICAL_FISH);
                        ItemStack.rename(LivingEntityHead.TROPICAL_FISH, config.getString("TROPICAL_FISH.Name"));
                    }


                } else if (event.getEntityType() == EntityType.PARROT) {
                    if (config.getBoolean("PARROT.Drop") && x <= config.getInt("PARROT.Chance")) {
                        Parrot parrot = (Parrot) event.getEntity();

                        switch (parrot.getVariant()) {
                            case BLUE:
                                event.getDrops().add(LivingEntityHead.PARROT_BLUE);
                                ItemStack.rename(LivingEntityHead.PARROT_BLUE, config.getString("PARROT.Name"));

                                break;
                            case CYAN:
                                event.getDrops().add(LivingEntityHead.PARROT_CYAN);
                                ItemStack.rename(LivingEntityHead.PARROT_CYAN, config.getString("PARROT.Name"));

                                break;
                            case GRAY:
                                event.getDrops().add(LivingEntityHead.PARROT_GRAY);
                                ItemStack.rename(LivingEntityHead.PARROT_GRAY, config.getString("PARROT.Name"));

                                break;
                            case RED:
                                event.getDrops().add(LivingEntityHead.PARROT_RED);
                                ItemStack.rename(LivingEntityHead.PARROT_RED, config.getString("PARROT.Name"));

                                break;
                            case GREEN:
                                event.getDrops().add(LivingEntityHead.PARROT_GREEN);
                                ItemStack.rename(LivingEntityHead.PARROT_GREEN, config.getString("PARROT.Name"));
                                break;
                        }
                    }
                }
            }

        }
    }

}

