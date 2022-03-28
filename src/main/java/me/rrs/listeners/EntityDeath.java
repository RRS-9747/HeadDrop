package me.rrs.listeners;

import dev.dejvokep.boostedyaml.YamlDocument;
import me.clip.placeholderapi.PlaceholderAPI;
import me.rrs.HeadDrop;
import me.rrs.util.Embed;
import me.rrs.util.Itemstack;
import me.rrs.util.SkullCreator;
import me.rrs.database.LivingEntityHead;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.World;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Random;



public class EntityDeath implements Listener {


    final YamlDocument config = HeadDrop.getConfiguration();
    Random random = new Random();
    String title, description, footer;

    @EventHandler(priority = EventPriority.NORMAL)
    public void EntityDropHeadEvent(EntityDeathEvent event) {
        if (event.getEntity().getKiller() == null) return;

        if (config.getBoolean("Config.Killer-Require-Permission")) {
            if (!event.getEntity().getKiller().hasPermission("headdrop.killer")) return;
        }

        Entity entity = event.getEntity();
        boolean isInDisabledWorld = false;
        int x = random.nextInt(100) + 1;

        List<String> worldList = HeadDrop.getConfiguration().getStringList("Config.Disable-Worlds");

        title = config.getString("Bot.Title")
                .replaceAll("%killer%", event.getEntity().getKiller().getName())
                .replaceAll("%mob%", entity.getName());


        description = config.getString("Bot.Description")
                .replaceAll("%killer%", event.getEntity().getKiller().getName())
                .replaceAll("%mob%", entity.getName());

        footer = config.getString("Bot.Footer")
                .replaceAll("%killer%", event.getEntity().getKiller().getName())
                .replaceAll("%mob%", entity.getName());


        if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
            title = PlaceholderAPI.setPlaceholders(event.getEntity().getKiller(), title);
            description = PlaceholderAPI.setPlaceholders(event.getEntity().getKiller(), description);
            footer = PlaceholderAPI.setPlaceholders(event.getEntity().getKiller(), footer);
        }


        for (String world : worldList) {
            World w = Bukkit.getWorld(world);
            if (entity.getWorld().equals(w)) {
                isInDisabledWorld = true;
            }
        }

        if (!isInDisabledWorld) {
            switch (entity.getType()) {
                case AXOLOTL:
                    if (config.getBoolean("AXOLOTL.Drop") && x <= config.getInt("AXOLOTL.Chance")) {
                        Axolotl axolotl = (Axolotl) entity;

                        switch (axolotl.getVariant()) {
                            case LUCY:
                                event.getDrops().add(LivingEntityHead.AXOLOTL_LUCY);
                                Itemstack.rename(LivingEntityHead.AXOLOTL_LUCY, ChatColor.YELLOW + config.getString("AXOLOTL.Name"));

                                break;
                            case BLUE:
                                event.getDrops().add(LivingEntityHead.AXOLOTL_BLUE);
                                Itemstack.rename(LivingEntityHead.AXOLOTL_BLUE, ChatColor.YELLOW + config.getString("AXOLOTL.Name"));

                                break;
                            case WILD:
                                event.getDrops().add(LivingEntityHead.AXOLOTL_WILD);
                                Itemstack.rename(LivingEntityHead.AXOLOTL_WILD, ChatColor.YELLOW + config.getString("AXOLOTL.Name"));


                                break;
                            case CYAN:
                                event.getDrops().add(LivingEntityHead.AXOLOTL_CYAN);
                                Itemstack.rename(LivingEntityHead.AXOLOTL_CYAN, ChatColor.YELLOW + config.getString("AXOLOTL.Name"));


                                break;
                            case GOLD:
                                event.getDrops().add(LivingEntityHead.AXOLOTL_GOLD);
                                Itemstack.rename(LivingEntityHead.AXOLOTL_GOLD, ChatColor.YELLOW + config.getString("AXOLOTL.Name"));
                                break;
                        }
                        if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);

                    }
                    break;
                case BAT:
                    if (config.getBoolean("BAT.Drop") && x <= config.getInt("BAT.Chance")) {
                        event.getDrops().add(LivingEntityHead.BAT);
                        Itemstack.rename(LivingEntityHead.BAT, ChatColor.YELLOW + config.getString("BAT.Name"));
                        if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);

                    }


                    break;
                case BEE:
                    if (config.getBoolean("BEE.Drop") && x <= config.getInt("BEE.Chance")) {
                        Bee bee = (Bee) entity;
                        if (bee.getAnger() > 0) {
                            event.getDrops().add(LivingEntityHead.BEE_Aware);
                            Itemstack.rename(LivingEntityHead.BEE_Aware, ChatColor.YELLOW + config.getString("BEE.Name"));
                        } else {
                            Itemstack.rename(LivingEntityHead.BEE, ChatColor.YELLOW + config.getString("BEE.Name"));
                            event.getDrops().add(LivingEntityHead.BEE);

                        }
                        if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
                    }


                    break;
                case BLAZE:
                    if (config.getBoolean("BLAZE.Drop") && x <= config.getInt("BLAZE.Chance")) {
                        event.getDrops().add(LivingEntityHead.BLAZE);
                        Itemstack.rename(LivingEntityHead.BLAZE, ChatColor.YELLOW + config.getString("BLAZE.Name"));
                        if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
                    }


                    break;
                case CAT:
                    if (config.getBoolean("CAT.Drop") && x <= config.getInt("CAT.Chance")) {
                        Cat cat = (Cat) entity;
                        switch (cat.getCatType()) {
                            case BLACK:
                                event.getDrops().add(LivingEntityHead.CAT_BLACK);
                                Itemstack.rename(LivingEntityHead.CAT_BLACK, ChatColor.YELLOW + config.getString("CAT.Name"));

                                break;
                            case BRITISH_SHORTHAIR:
                                event.getDrops().add(LivingEntityHead.CAT_BRITISH);
                                Itemstack.rename(LivingEntityHead.CAT_BRITISH, ChatColor.YELLOW + config.getString("CAT.Name"));

                                break;
                            case CALICO:
                                event.getDrops().add(LivingEntityHead.CAT_CALICO);
                                Itemstack.rename(LivingEntityHead.CAT_CALICO, ChatColor.YELLOW + config.getString("CAT.Name"));

                                break;
                            case JELLIE:
                                event.getDrops().add(LivingEntityHead.CAT_JELLIE);
                                Itemstack.rename(LivingEntityHead.CAT_JELLIE, ChatColor.YELLOW + config.getString("CAT.Name"));

                                break;
                            case PERSIAN:
                                event.getDrops().add(LivingEntityHead.CAT_PERSIAN);
                                Itemstack.rename(LivingEntityHead.CAT_PERSIAN, ChatColor.YELLOW + config.getString("CAT.Name"));

                                break;
                            case RAGDOLL:
                                event.getDrops().add(LivingEntityHead.CAT_RAGDOLL);
                                Itemstack.rename(LivingEntityHead.CAT_RAGDOLL, ChatColor.YELLOW + config.getString("CAT.Name"));

                                break;
                            case RED:
                                event.getDrops().add(LivingEntityHead.CAT_RED);
                                Itemstack.rename(LivingEntityHead.CAT_RED, ChatColor.YELLOW + config.getString("CAT.Name"));

                                break;
                            case SIAMESE:
                                event.getDrops().add(LivingEntityHead.CAT_SIAMESE);
                                Itemstack.rename(LivingEntityHead.CAT_SIAMESE, ChatColor.YELLOW + config.getString("CAT.Name"));

                                break;
                            case TABBY:
                                event.getDrops().add(LivingEntityHead.CAT_TABBY);
                                Itemstack.rename(LivingEntityHead.CAT_TABBY, ChatColor.YELLOW + config.getString("CAT.Name"));

                                break;
                            case WHITE:
                                event.getDrops().add(LivingEntityHead.CAT_WHITE);
                                Itemstack.rename(LivingEntityHead.CAT_WHITE, ChatColor.YELLOW + config.getString("CAT.Name"));
                                break;
                        }
                        if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
                    }


                    break;
                case SPIDER:
                    if (config.getBoolean("SPIDER.Drop") && x <= config.getInt("SPIDER.Chance")) {
                        event.getDrops().add(LivingEntityHead.SPIDER);
                        Itemstack.rename(LivingEntityHead.SPIDER, ChatColor.YELLOW + config.getString("SPIDER.Name"));
                        if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
                    }


                    break;
                case CAVE_SPIDER:
                    if (config.getBoolean("CAVE_SPIDER.Drop") && x <= config.getInt("CAVE_SPIDER.Chance")) {
                        event.getDrops().add(LivingEntityHead.CAVE_SPIDER);
                        Itemstack.rename(LivingEntityHead.CAVE_SPIDER, ChatColor.YELLOW + config.getString("CAVE_SPIDER.Name"));
                        if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
                    }


                    break;
                case CHICKEN:
                    if (config.getBoolean("CHICKEN.Drop") && x <= config.getInt("CHICKEN.Chance")) {
                        event.getDrops().add(LivingEntityHead.CHICKEN);
                        Itemstack.rename(LivingEntityHead.CHICKEN, ChatColor.YELLOW + config.getString("CHICKEN.Name"));
                        if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
                    }


                    break;
                case COD:
                    if (config.getBoolean("COD.Drop") && x <= config.getInt("COD.Chance")) {
                        event.getDrops().add(LivingEntityHead.COD);
                        Itemstack.rename(LivingEntityHead.COD, ChatColor.YELLOW + config.getString("COD.Name"));
                        if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
                    }


                    break;
                case COW:
                    if (config.getBoolean("COW.Drop") && x <= config.getInt("COW.Chance")) {
                        event.getDrops().add(LivingEntityHead.COW);
                        Itemstack.rename(LivingEntityHead.COW, ChatColor.YELLOW + config.getString("COW.Name"));
                        if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
                    }


                    break;
                case DOLPHIN:
                    if (config.getBoolean("DOLPHIN.Drop") && x <= config.getInt("DOLPHIN.Chance")) {
                        event.getDrops().add(LivingEntityHead.DOLPHIN);
                        Itemstack.rename(LivingEntityHead.DOLPHIN, ChatColor.YELLOW + config.getString("DOLPHIN.Name"));
                        if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
                    }


                    break;
                case DONKEY:
                    if (config.getBoolean("DONKEY.Drop") && x <= config.getInt("DONKEY.Chance")) {
                        event.getDrops().add(LivingEntityHead.DONKEY);
                        Itemstack.rename(LivingEntityHead.DONKEY, ChatColor.YELLOW + config.getString("DONKEY.Name"));
                        if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
                    }


                    break;
                case DROWNED:
                    if (config.getBoolean("DROWNED.Drop") && x <= config.getInt("DROWNED.Chance")) {
                        event.getDrops().add(LivingEntityHead.DROWNED);
                        Itemstack.rename(LivingEntityHead.DROWNED, ChatColor.YELLOW + config.getString("DROWNED.Name"));
                        if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
                    }


                    break;
                case ELDER_GUARDIAN:
                    if (config.getBoolean("ELDER_GUARDIAN.Drop") && x <= config.getInt("ELDER_GUARDIAN.Chance")) {
                        event.getDrops().add(LivingEntityHead.ELDER_GUARDIAN);
                        Itemstack.rename(LivingEntityHead.ELDER_GUARDIAN, ChatColor.YELLOW + config.getString("ELDER_GUARDIAN.Name"));
                        if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
                    }


                    break;
                case ENDERMAN:
                    if (config.getBoolean("ENDERMAN.Drop") && x <= config.getInt("ENDERMAN.Chance")) {
                        event.getDrops().add(LivingEntityHead.ENDERMAN);
                        Itemstack.rename(LivingEntityHead.ENDERMAN, ChatColor.YELLOW + config.getString("ENDERMAN.Name"));
                        if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
                    }


                    break;
                case ENDERMITE:
                    if (config.getBoolean("ENDERMITE.Drop") && x <= config.getInt("ENDERMITE.Chance")) {
                        event.getDrops().add(LivingEntityHead.ENDERMITE);
                        Itemstack.rename(LivingEntityHead.ENDERMITE, ChatColor.YELLOW + config.getString("ENDERMITE.Name"));
                        if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
                    }


                    break;
                case EVOKER:
                    if (config.getBoolean("EVOKER.Drop") && x <= config.getInt("EVOKER.Chance")) {
                        event.getDrops().add(LivingEntityHead.EVOKER);
                        Itemstack.rename(LivingEntityHead.EVOKER, ChatColor.YELLOW + config.getString("EVOKER.Name"));
                        if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
                    }


                    break;
                case FOX:
                    if (config.getBoolean("FOX.Drop") && x <= config.getInt("FOX.Chance")) {
                        Fox fox = (Fox) entity;

                        switch (fox.getFoxType()) {
                            case RED:
                                event.getDrops().add(LivingEntityHead.FOX);
                                Itemstack.rename(LivingEntityHead.FOX, ChatColor.YELLOW + config.getString("FOX.Name"));

                                break;
                            case SNOW:
                                event.getDrops().add(LivingEntityHead.FOX_WHITE);
                                Itemstack.rename(LivingEntityHead.FOX, ChatColor.YELLOW + config.getString("FOX.Name"));

                                break;
                        }
                        if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
                    }


                    break;
                case GIANT:
                    if (config.getBoolean("GIANT.Drop") && x <= config.getInt("GIANT.Chance")) {
                        event.getDrops().add(LivingEntityHead.GIANT);
                        Itemstack.rename(LivingEntityHead.GIANT, ChatColor.YELLOW + config.getString("GIANT.Name"));
                        if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
                    }


                    break;
                case GLOW_SQUID:
                    if (config.getBoolean("GLOW_SQUID.Drop") && x <= config.getInt("GLOW_SQUID.Chance")) {
                        event.getDrops().add(LivingEntityHead.GLOW_SQUID);
                        Itemstack.rename(LivingEntityHead.GLOW_SQUID, ChatColor.YELLOW + config.getString("GLOW_SQUID.Name"));
                        if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
                    }


                    break;
                case GOAT:
                    if (config.getBoolean("GOAT.Drop") && x <= config.getInt("GOAT.Chance")) {
                        event.getDrops().add(LivingEntityHead.GOAT);
                        Itemstack.rename(LivingEntityHead.GOAT, ChatColor.YELLOW + config.getString("GOAT.Name"));
                        if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
                    }


                    break;
                case GUARDIAN:
                    if (config.getBoolean("GUARDIAN.Drop") && x <= config.getInt("GUARDIAN.Chance")) {
                        event.getDrops().add(LivingEntityHead.GUARDIAN);
                        Itemstack.rename(LivingEntityHead.GUARDIAN, ChatColor.YELLOW + config.getString("GUARDIAN.Name"));
                        if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
                    }


                    break;
                case HOGLIN:
                    if (config.getBoolean("HOGLIN.Drop") && x <= config.getInt("HOGLIN.Chance")) {
                        event.getDrops().add(LivingEntityHead.HOGLIN);
                        Itemstack.rename(LivingEntityHead.HOGLIN, ChatColor.YELLOW + config.getString("HOGLIN.Name"));
                        if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
                    }


                    break;
                case HORSE:
                    if (config.getBoolean("HORSE.Drop") && x <= config.getInt("HORSE.Chance")) {
                        Horse horse = (Horse) entity;

                        switch (horse.getColor()) {
                            case WHITE:
                                event.getDrops().add(LivingEntityHead.HORSE_WHITE);
                                Itemstack.rename(LivingEntityHead.HORSE_WHITE, ChatColor.YELLOW + config.getString("HORSE.Name"));

                                break;
                            case CREAMY:
                                event.getDrops().add(LivingEntityHead.HORSE_CREAMY);
                                Itemstack.rename(LivingEntityHead.HORSE_CREAMY, ChatColor.YELLOW + config.getString("HORSE.Name"));

                                break;
                            case CHESTNUT:
                                event.getDrops().add(LivingEntityHead.HORSE_CHESTNUT);
                                Itemstack.rename(LivingEntityHead.HORSE_CHESTNUT, ChatColor.YELLOW + config.getString("HORSE.Name"));

                                break;
                            case BROWN:
                                event.getDrops().add(LivingEntityHead.HORSE_BROWN);
                                Itemstack.rename(LivingEntityHead.HORSE_BROWN, ChatColor.YELLOW + config.getString("HORSE.Name"));

                                break;
                            case BLACK:
                                event.getDrops().add(LivingEntityHead.HORSE_BLACK);
                                Itemstack.rename(LivingEntityHead.HORSE_BLACK, ChatColor.YELLOW + config.getString("HORSE.Name"));

                                break;
                            case GRAY:
                                event.getDrops().add(LivingEntityHead.HORSE_GRAY);
                                Itemstack.rename(LivingEntityHead.HORSE_GRAY, ChatColor.YELLOW + config.getString("HORSE.Name"));

                                break;
                            case DARK_BROWN:
                                event.getDrops().add(LivingEntityHead.HORSE_DARK_BROWN);
                                Itemstack.rename(LivingEntityHead.HORSE_DARK_BROWN, ChatColor.YELLOW + config.getString("HORSE.Name"));
                                break;
                        }
                        if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
                    }


                    break;
                case HUSK:
                    if (config.getBoolean("HUSK.Drop") && x <= config.getInt("HUSK.Chance")) {
                        event.getDrops().add(LivingEntityHead.HUSK);
                        Itemstack.rename(LivingEntityHead.HUSK, ChatColor.YELLOW + config.getString("HUSK.Name"));
                        if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
                    }


                    break;
                case ILLUSIONER:
                    if (config.getBoolean("ILLUSIONER.Drop") && x <= config.getInt("ILLUSIONER.Chance")) {
                        event.getDrops().add(LivingEntityHead.ILLUSIONER);
                        Itemstack.rename(LivingEntityHead.ILLUSIONER, ChatColor.YELLOW + config.getString("ILLUSIONER.Name"));
                        if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
                    }


                    break;
                case IRON_GOLEM:
                    if (config.getBoolean("IRON_GOLEM.Drop") && x <= config.getInt("IRON_GOLEM.Chance")) {
                        event.getDrops().add(LivingEntityHead.IRON_GOLEM);
                        Itemstack.rename(LivingEntityHead.IRON_GOLEM, ChatColor.YELLOW + config.getString("IRON_GOLEM.Name"));
                        if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
                    }


                    break;
                case LLAMA:
                    if (config.getBoolean("LLAMA.Drop") && x <= config.getInt("LLAMA.Chance")) {
                        Llama llama = (Llama) entity;

                        switch (llama.getColor()) {
                            case BROWN:
                                event.getDrops().add(LivingEntityHead.LLAMA_BROWN);
                                Itemstack.rename(LivingEntityHead.LLAMA_BROWN, ChatColor.YELLOW + config.getString("LLAMA.Name"));

                                break;
                            case GRAY:
                                event.getDrops().add(LivingEntityHead.LLAMA_GRAY);
                                Itemstack.rename(LivingEntityHead.LLAMA_GRAY, ChatColor.YELLOW + config.getString("LLAMA.Name"));

                                break;
                            case CREAMY:
                                event.getDrops().add(LivingEntityHead.LLAMA_CREAMY);
                                Itemstack.rename(LivingEntityHead.LLAMA_CREAMY, ChatColor.YELLOW + config.getString("LLAMA.Name"));

                                break;
                            case WHITE:
                                event.getDrops().add(LivingEntityHead.LLAMA_WHITE);
                                Itemstack.rename(LivingEntityHead.LLAMA_WHITE, ChatColor.YELLOW + config.getString("LLAMA.Name"));
                                break;
                        }
                        if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
                    }


                    break;
                case MAGMA_CUBE:
                    if (config.getBoolean("MAGMA_CUBE.Drop") && x <= config.getInt("MAGMA_CUBE.Chance")) {
                        event.getDrops().add(LivingEntityHead.MAGMA_CUBE);
                        Itemstack.rename(LivingEntityHead.MAGMA_CUBE, ChatColor.YELLOW + config.getString("MAGMA_CUBE.Name"));
                        if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
                    }


                    break;
                case MUSHROOM_COW:
                    if (config.getBoolean("MUSHROOM_COW.Drop") && x <= config.getInt("MUSHROOM_COW.Chance")) {
                        MushroomCow mushroomCow = (MushroomCow) entity;

                        switch (mushroomCow.getVariant()) {
                            case RED:
                                event.getDrops().add(LivingEntityHead.MUSHROOM_COW_RED);
                                Itemstack.rename(LivingEntityHead.MUSHROOM_COW_RED, ChatColor.YELLOW + config.getString("MUSHROOM_COW.Name"));

                                break;
                            case BROWN:
                                event.getDrops().add(LivingEntityHead.MUSHROOM_COW_BROWN);
                                Itemstack.rename(LivingEntityHead.MUSHROOM_COW_BROWN, ChatColor.YELLOW + config.getString("MUSHROOM_COW.Name"));
                                break;
                        }
                        if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
                    }


                    break;
                case MULE:
                    if (config.getBoolean("MULE.Drop") && x <= config.getInt("MULE.Chance")) {
                        event.getDrops().add(LivingEntityHead.MULE);
                        Itemstack.rename(LivingEntityHead.MULE, ChatColor.YELLOW + config.getString("MULE.Name"));
                        if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
                    }


                    break;
                case OCELOT:
                    if (config.getBoolean("OCELOT.Drop") && x <= config.getInt("OCELOT.Chance")) {
                        event.getDrops().add(LivingEntityHead.OCELOT);
                        Itemstack.rename(LivingEntityHead.OCELOT, ChatColor.YELLOW + config.getString("OCELOT.Name"));
                        if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
                    }


                    break;
                case PANDA:
                    if (config.getBoolean("PANDA.Drop") && x <= config.getInt("PANDA.Chance")) {
                        Panda panda = (Panda) entity;
                        if (panda.getMainGene() == Panda.Gene.BROWN) {
                            event.getDrops().add(LivingEntityHead.PANDA_BROWN);
                            Itemstack.rename(LivingEntityHead.PANDA_BROWN, ChatColor.YELLOW + config.getString("PANDA.Name"));
                        } else {
                            event.getDrops().add(LivingEntityHead.PANDA);
                            Itemstack.rename(LivingEntityHead.PANDA, ChatColor.YELLOW + config.getString("PANDA.Name"));
                        }
                        if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
                    }


                    break;
                case PHANTOM:
                    if (config.getBoolean("PHANTOM.Drop") && x <= config.getInt("PHANTOM.Chance")) {
                        event.getDrops().add(LivingEntityHead.PHANTOM);
                        Itemstack.rename(LivingEntityHead.PHANTOM, ChatColor.YELLOW + config.getString("PHANTOM.Name"));
                        if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
                    }


                    break;
                case PIG:
                    if (config.getBoolean("PIG.Drop") && x <= config.getInt("PIG.Chance")) {
                        event.getDrops().add(LivingEntityHead.PIG);
                        Itemstack.rename(LivingEntityHead.PIG, ChatColor.YELLOW + config.getString("PIG.Name"));
                        if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
                    }


                    break;
                case PIGLIN:
                    if (config.getBoolean("PIGLIN.Drop") && x <= config.getInt("PIGLIN.Chance")) {
                        event.getDrops().add(LivingEntityHead.PIGLIN);
                        Itemstack.rename(LivingEntityHead.PIGLIN, ChatColor.YELLOW + config.getString("PIGLIN.Name"));
                        if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
                    }


                    break;
                case PIGLIN_BRUTE:
                    if (config.getBoolean("PIGLIN_BRUTE.Drop") && x <= config.getInt("PIGLIN_BRUTE.Chance")) {
                        event.getDrops().add(LivingEntityHead.PIGLIN_BRUTE);
                        Itemstack.rename(LivingEntityHead.PIGLIN_BRUTE, ChatColor.YELLOW + config.getString("PIGLIN_BRUTE.Name"));
                        if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
                    }


                    break;
                case PILLAGER:
                    if (config.getBoolean("PILLAGER.Drop") && x <= config.getInt("PILLAGER.Chance")) {
                        event.getDrops().add(LivingEntityHead.PILLAGER);
                        Itemstack.rename(LivingEntityHead.PILLAGER, ChatColor.YELLOW + config.getString("PILLAGER.Name"));
                        if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
                    }


                    break;
                case POLAR_BEAR:
                    if (config.getBoolean("POLAR_BEAR.Drop") && x <= config.getInt("POLAR_BEAR.Chance")) {
                        event.getDrops().add(LivingEntityHead.POLAR_BEAR);
                        Itemstack.rename(LivingEntityHead.POLAR_BEAR, ChatColor.YELLOW + config.getString("POLAR_BEAR.Name"));
                        if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
                    }


                    break;
                case PUFFERFISH:
                    if (config.getBoolean("PUFFERFISH.Drop") && x <= config.getInt("PUFFERFISH.Chance")) {
                        event.getDrops().add(LivingEntityHead.PUFFERFISH);
                        Itemstack.rename(LivingEntityHead.PUFFERFISH, ChatColor.YELLOW + config.getString("PUFFERFISH.Name"));
                        if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
                    }


                    break;
                case RABBIT:
                    if (config.getBoolean("RABBIT.Drop") && x <= config.getInt("RABBIT.Chance")) {
                        Rabbit rabbit = (Rabbit) entity;
                        switch (rabbit.getRabbitType()) {

                            case BROWN:
                                event.getDrops().add(LivingEntityHead.RABBIT_BROWN);
                                Itemstack.rename(LivingEntityHead.RABBIT_BROWN, ChatColor.YELLOW + config.getString("RABBIT.Name"));

                                break;
                            case WHITE:
                                event.getDrops().add(LivingEntityHead.RABBIT_WHITE);
                                Itemstack.rename(LivingEntityHead.RABBIT_WHITE, ChatColor.YELLOW + config.getString("RABBIT.Name"));

                                break;
                            case BLACK:
                                event.getDrops().add(LivingEntityHead.RABBIT_BLACK);
                                Itemstack.rename(LivingEntityHead.RABBIT_BLACK, ChatColor.YELLOW + config.getString("RABBIT.Name"));

                                break;
                            case BLACK_AND_WHITE:
                                event.getDrops().add(LivingEntityHead.RABBIT_BLACK_AND_WHITE);
                                Itemstack.rename(LivingEntityHead.RABBIT_BLACK_AND_WHITE, ChatColor.YELLOW + config.getString("RABBIT.Name"));

                                break;
                            case GOLD:
                                event.getDrops().add(LivingEntityHead.RABBIT_GOLD);
                                Itemstack.rename(LivingEntityHead.RABBIT_GOLD, ChatColor.YELLOW + config.getString("RABBIT.Name"));

                                break;
                            case SALT_AND_PEPPER:
                                event.getDrops().add(LivingEntityHead.RABBIT_SALT_AND_PEPPER);
                                Itemstack.rename(LivingEntityHead.RABBIT_SALT_AND_PEPPER, ChatColor.YELLOW + config.getString("RABBIT.Name"));

                                break;
                            case THE_KILLER_BUNNY:
                                event.getDrops().add(LivingEntityHead.RABBIT_THE_KILLER_BUNNY);
                                Itemstack.rename(LivingEntityHead.RABBIT_THE_KILLER_BUNNY, ChatColor.YELLOW + config.getString("RABBIT.Name"));

                                break;
                        }
                        if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
                    }

                    break;
                case RAVAGER:
                    if (config.getBoolean("RAVAGER.Drop") && x <= config.getInt("RAVAGER.Chance")) {
                        event.getDrops().add(LivingEntityHead.RAVAGER);
                        Itemstack.rename(LivingEntityHead.RAVAGER, ChatColor.YELLOW + config.getString("RAVAGER.Name"));
                        if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
                    }


                    break;
                case SALMON:
                    if (config.getBoolean("SALMON.Drop") && x <= config.getInt("SALMON.Chance")) {
                        event.getDrops().add(LivingEntityHead.SALMON);
                        Itemstack.rename(LivingEntityHead.SALMON, ChatColor.YELLOW + config.getString("SALMON.Name"));
                        if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
                    }


                    break;
                case SHEEP:
                    if (config.getBoolean("SHEEP.Drop") && x <= config.getInt("SHEEP.Chance")) {
                        Sheep sheep = (Sheep) entity;

                        if (sheep.getColor() == DyeColor.WHITE) {
                            event.getDrops().add(LivingEntityHead.SHEEP_WHITE);
                            Itemstack.rename(LivingEntityHead.SHEEP_WHITE, ChatColor.YELLOW + config.getString("SHEEP.Name"));

                        } else if (sheep.getColor() == DyeColor.BLACK) {
                            event.getDrops().add(LivingEntityHead.SHEEP_BLACK);
                            Itemstack.rename(LivingEntityHead.SHEEP_BLACK, ChatColor.YELLOW + config.getString("SHEEP.Name"));

                        } else if (sheep.getColor() == DyeColor.BLACK) {
                            event.getDrops().add(LivingEntityHead.SHEEP_BLACK);
                            Itemstack.rename(LivingEntityHead.SHEEP_BLACK, ChatColor.YELLOW + config.getString("SHEEP.Name"));

                        } else if (sheep.getColor() == DyeColor.BROWN) {
                            event.getDrops().add(LivingEntityHead.SHEEP_BROWN);
                            Itemstack.rename(LivingEntityHead.SHEEP_BROWN, ChatColor.YELLOW + config.getString("SHEEP.Name"));

                        } else if (sheep.getColor() == DyeColor.BLUE) {
                            event.getDrops().add(LivingEntityHead.SHEEP_BLUE);
                            Itemstack.rename(LivingEntityHead.SHEEP_BLUE, ChatColor.YELLOW + config.getString("SHEEP.Name"));

                        } else if (sheep.getColor() == DyeColor.CYAN) {
                            event.getDrops().add(LivingEntityHead.SHEEP_CYAN);
                            Itemstack.rename(LivingEntityHead.SHEEP_CYAN, ChatColor.YELLOW + config.getString("SHEEP.Name"));

                        } else if (sheep.getColor() == DyeColor.GRAY) {
                            event.getDrops().add(LivingEntityHead.SHEEP_GRAY);
                            Itemstack.rename(LivingEntityHead.SHEEP_GRAY, ChatColor.YELLOW + config.getString("SHEEP.Name"));

                        } else if (sheep.getColor() == DyeColor.GREEN) {
                            event.getDrops().add(LivingEntityHead.SHEEP_GREEN);
                            Itemstack.rename(LivingEntityHead.SHEEP_GREEN, ChatColor.YELLOW + config.getString("SHEEP.Name"));

                        } else if (sheep.getColor() == DyeColor.LIGHT_BLUE) {
                            event.getDrops().add(LivingEntityHead.SHEEP_LIGHT_BLUE);
                            Itemstack.rename(LivingEntityHead.SHEEP_LIGHT_BLUE, ChatColor.YELLOW + config.getString("SHEEP.Name"));

                        } else if (sheep.getColor() == DyeColor.LIGHT_GRAY) {
                            event.getDrops().add(LivingEntityHead.SHEEP_LIGHT_GRAY);
                            Itemstack.rename(LivingEntityHead.SHEEP_LIGHT_GRAY, ChatColor.YELLOW + config.getString("SHEEP.Name"));

                        } else if (sheep.getColor() == DyeColor.LIME) {
                            event.getDrops().add(LivingEntityHead.SHEEP_LIME);
                            Itemstack.rename(LivingEntityHead.SHEEP_LIME, ChatColor.YELLOW + config.getString("SHEEP.Name"));

                        } else if (sheep.getColor() == DyeColor.MAGENTA) {
                            event.getDrops().add(LivingEntityHead.SHEEP_MAGENTA);
                            Itemstack.rename(LivingEntityHead.SHEEP_MAGENTA, ChatColor.YELLOW + config.getString("SHEEP.Name"));

                        } else if (sheep.getColor() == DyeColor.ORANGE) {
                            event.getDrops().add(LivingEntityHead.SHEEP_ORANGE);
                            Itemstack.rename(LivingEntityHead.SHEEP_ORANGE, ChatColor.YELLOW + config.getString("SHEEP.Name"));

                        } else if (sheep.getColor() == DyeColor.PINK) {
                            event.getDrops().add(LivingEntityHead.SHEEP_PINK);
                            Itemstack.rename(LivingEntityHead.SHEEP_PINK, ChatColor.YELLOW + config.getString("SHEEP.Name"));

                        } else if (sheep.getColor() == DyeColor.PURPLE) {
                            event.getDrops().add(LivingEntityHead.SHEEP_PURPLE);
                            Itemstack.rename(LivingEntityHead.SHEEP_PURPLE, ChatColor.YELLOW + config.getString("SHEEP.Name"));

                        } else if (sheep.getColor() == DyeColor.RED) {
                            event.getDrops().add(LivingEntityHead.SHEEP_RED);
                            Itemstack.rename(LivingEntityHead.SHEEP_RED, ChatColor.YELLOW + config.getString("SHEEP.Name"));

                        } else if (sheep.getColor() == DyeColor.YELLOW) {
                            event.getDrops().add(LivingEntityHead.SHEEP_YELLOW);
                            Itemstack.rename(LivingEntityHead.SHEEP_YELLOW, ChatColor.YELLOW + config.getString("SHEEP.Name"));
                        }
                        if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
                    }


                    break;
                case SHULKER:
                    if (config.getBoolean("SHULKER.Drop") && x <= config.getInt("SHULKER.Chance")) {
                        event.getDrops().add(LivingEntityHead.SHULKER);
                        Itemstack.rename(LivingEntityHead.SHULKER, ChatColor.YELLOW + config.getString("SHULKER.Name"));
                        if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
                    }


                    break;
                case SILVERFISH:
                    if (config.getBoolean("SILVERFISH.Drop") && x <= config.getInt("SILVERFISH.Chance")) {
                        event.getDrops().add(LivingEntityHead.SILVERFISH);
                        Itemstack.rename(LivingEntityHead.SILVERFISH, ChatColor.YELLOW + config.getString("SILVERFISH.Name"));
                        if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
                    }


                    break;
                case SKELETON_HORSE:
                    if (config.getBoolean("SKELETON_HORSE.Drop") && x <= config.getInt("SKELETON_HORSE.Chance")) {
                        event.getDrops().add(LivingEntityHead.SKELETON_HORSE);
                        Itemstack.rename(LivingEntityHead.SKELETON_HORSE, ChatColor.YELLOW + config.getString("SKELETON_HORSE.Name"));
                        if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
                    }


                    break;
                case SLIME:
                    if (config.getBoolean("SLIME.Drop") && x <= config.getInt("SLIME.Chance")) {
                        event.getDrops().add(LivingEntityHead.SLIME);
                        Itemstack.rename(LivingEntityHead.SLIME, ChatColor.YELLOW + config.getString("SLIME.Name"));
                        if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
                    }


                    break;
                case SNOWMAN:
                    if (config.getBoolean("SNOW_GOLEM.Drop") && x <= config.getInt("SNOW_GOLEM.Chance")) {
                        event.getDrops().add(LivingEntityHead.SNOWMAN);
                        Itemstack.rename(LivingEntityHead.SNOWMAN, ChatColor.YELLOW + config.getString("SNOW_GOLEM.Name"));
                        if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
                    }


                    break;
                case SQUID:
                    if (config.getBoolean("SQUID.Drop") && x <= config.getInt("SQUID.Chance")) {
                        event.getDrops().add(LivingEntityHead.SQUID);
                        Itemstack.rename(LivingEntityHead.SQUID, ChatColor.YELLOW + config.getString("SQUID.Name"));
                        if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
                    }


                    break;
                case WITHER_SKELETON:
                    break;
                case STRAY:
                    if (config.getBoolean("STRAY.Drop") && x <= config.getInt("STRAY.Chance")) {
                        event.getDrops().add(LivingEntityHead.STRAY);
                        Itemstack.rename(LivingEntityHead.STRAY, ChatColor.YELLOW + config.getString("STRAY.Name"));
                        if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
                    }


                    break;
                case STRIDER:
                    if (config.getBoolean("STRIDER.Drop") && x <= config.getInt("STRIDER.Chance")) {
                        event.getDrops().add(LivingEntityHead.STRIDER);
                        Itemstack.rename(LivingEntityHead.STRIDER, ChatColor.YELLOW + config.getString("STRIDER.Name"));
                        if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
                    }


                    break;
                case TURTLE:
                    if (config.getBoolean("TURTLE.Drop") && x <= config.getInt("TURTLE.Chance")) {
                        event.getDrops().add(LivingEntityHead.TURTLE);
                        Itemstack.rename(LivingEntityHead.TURTLE, ChatColor.YELLOW + config.getString("TURTLE.Name"));
                        if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
                    }


                    break;
                case WANDERING_TRADER:
                    if (config.getBoolean("WANDERING_TRADER.Drop") && x <= config.getInt("WANDERING_TRADER.Chance")) {
                        event.getDrops().add(LivingEntityHead.WANDERING_TRADER);
                        Itemstack.rename(LivingEntityHead.WANDERING_TRADER, ChatColor.YELLOW + config.getString("WANDERING_TRADER.Name"));
                        if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
                    }


                    break;
                case WITCH:
                    if (config.getBoolean("WITCH.Drop") && x <= config.getInt("WITCH.Chance")) {
                        event.getDrops().add(LivingEntityHead.WITCH);
                        Itemstack.rename(LivingEntityHead.WITCH, ChatColor.YELLOW + config.getString("WITCH.Name"));
                        if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
                    }


                    break;
                case WITHER:
                    if (config.getBoolean("WITHER.Drop") && x <= config.getInt("WITHER.Chance")) {
                        event.getDrops().add(LivingEntityHead.WITHER);
                        Itemstack.rename(LivingEntityHead.WITHER, ChatColor.YELLOW + config.getString("WITHER.Name"));
                        if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
                    }


                    break;
                case ZOGLIN:
                    if (config.getBoolean("ZOGLIN.Drop") && x <= config.getInt("ZOGLIN.Chance")) {
                        event.getDrops().add(LivingEntityHead.ZOGLIN);
                        Itemstack.rename(LivingEntityHead.ZOGLIN, ChatColor.YELLOW + config.getString("ZOGLIN.Name"));
                        if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
                    }


                    break;
                case ZOMBIE_HORSE:
                    if (config.getBoolean("ZOMBIE_HORSE.Drop") && x <= config.getInt("ZOMBIE_HORSE.Chance")) {
                        event.getDrops().add(LivingEntityHead.ZOMBIE_HORSE);
                        Itemstack.rename(LivingEntityHead.ZOMBIE_HORSE, ChatColor.YELLOW + config.getString("ZOMBIE_HORSE.Name"));
                        if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
                    }


                    break;
                case ZOMBIFIED_PIGLIN:
                    if (config.getBoolean("ZOMBIFIED_PIGLIN.Drop") && x <= config.getInt("ZOMBIFIED_PIGLIN.Chance")) {
                        event.getDrops().add(LivingEntityHead.ZOMBIFIED_PIGLIN);
                        Itemstack.rename(LivingEntityHead.ZOMBIFIED_PIGLIN, ChatColor.YELLOW + config.getString("ZOMBIFIED_PIGLIN.Name"));
                        if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
                    }


                    break;
                case GHAST:
                    if (config.getBoolean("GHAST.Drop") && x <= config.getInt("GHAST.Chance")) {
                        event.getDrops().add(LivingEntityHead.GHAST);
                        Itemstack.rename(LivingEntityHead.GHAST, ChatColor.YELLOW + config.getString("GHAST.Name"));
                        if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
                    }


                    break;
                case ZOMBIE_VILLAGER:
                    if (config.getBoolean("ZOMBIE_VILLAGER.Drop") && x <= config.getInt("ZOMBIE_VILLAGER.Chance")) {
                        ZombieVillager zombieVillager = (ZombieVillager) entity;

                        switch (zombieVillager.getVillagerProfession()) {
                            case ARMORER:
                                event.getDrops().add(LivingEntityHead.ZOMBIE_VILLAGER_ARMORER);
                                Itemstack.rename(LivingEntityHead.ZOMBIE_VILLAGER_ARMORER, ChatColor.YELLOW + config.getString("ZOMBIE_VILLAGER.Name"));

                                break;
                            case BUTCHER:
                                event.getDrops().add(LivingEntityHead.ZOMBIE_VILLAGER_BUTCHER);
                                Itemstack.rename(LivingEntityHead.ZOMBIE_VILLAGER_BUTCHER, ChatColor.YELLOW + config.getString("ZOMBIE_VILLAGER.Name"));

                                break;
                            case CARTOGRAPHER:
                                event.getDrops().add(LivingEntityHead.ZOMBIE_VILLAGER_CARTOGRAPHER);
                                Itemstack.rename(LivingEntityHead.ZOMBIE_VILLAGER_CARTOGRAPHER, ChatColor.YELLOW + config.getString("ZOMBIE_VILLAGER.Name"));

                                break;
                            case CLERIC:
                                event.getDrops().add(LivingEntityHead.ZOMBIE_VILLAGER_CLERIC);
                                Itemstack.rename(LivingEntityHead.ZOMBIE_VILLAGER_CLERIC, ChatColor.YELLOW + config.getString("ZOMBIE_VILLAGER.Name"));

                                break;
                            case FARMER:
                                event.getDrops().add(LivingEntityHead.ZOMBIE_VILLAGER_FARMER);
                                Itemstack.rename(LivingEntityHead.ZOMBIE_VILLAGER_FARMER, ChatColor.YELLOW + config.getString("ZOMBIE_VILLAGER.Name"));

                                break;
                            case FISHERMAN:
                                event.getDrops().add(LivingEntityHead.ZOMBIE_VILLAGER_FISHERMAN);
                                Itemstack.rename(LivingEntityHead.ZOMBIE_VILLAGER_FISHERMAN, ChatColor.YELLOW + config.getString("ZOMBIE_VILLAGER.Name"));

                                break;
                            case FLETCHER:
                                event.getDrops().add(LivingEntityHead.ZOMBIE_VILLAGER_FLETCHER);
                                Itemstack.rename(LivingEntityHead.ZOMBIE_VILLAGER_FLETCHER, ChatColor.YELLOW + config.getString("ZOMBIE_VILLAGER.Name"));

                                break;
                            case LIBRARIAN:
                                event.getDrops().add(LivingEntityHead.ZOMBIE_VILLAGER_LIBRARIAN);
                                Itemstack.rename(LivingEntityHead.ZOMBIE_VILLAGER_LIBRARIAN, ChatColor.YELLOW + config.getString("ZOMBIE_VILLAGER.Name"));

                                break;
                            case SHEPHERD:
                                event.getDrops().add(LivingEntityHead.ZOMBIE_VILLAGER_SHEPHERD);
                                Itemstack.rename(LivingEntityHead.ZOMBIE_VILLAGER_SHEPHERD, ChatColor.YELLOW + config.getString("ZOMBIE_VILLAGER.Name"));

                                break;
                            case WEAPONSMITH:
                                event.getDrops().add(LivingEntityHead.ZOMBIE_VILLAGER_WEAPONSMITH);
                                Itemstack.rename(LivingEntityHead.ZOMBIE_VILLAGER_WEAPONSMITH, ChatColor.YELLOW + config.getString("ZOMBIE_VILLAGER.Name"));

                                break;
                            default:
                                event.getDrops().add(LivingEntityHead.ZOMBIE_VILLAGER_NULL);
                                Itemstack.rename(LivingEntityHead.ZOMBIE_VILLAGER_NULL, ChatColor.YELLOW + config.getString("ZOMBIE_VILLAGER.Name"));
                                break;
                        }
                        if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
                    }


                    break;
                case VINDICATOR:
                    if (config.getBoolean("VINDICATOR.Drop") && x <= config.getInt("VINDICATOR.Chance")) {
                        event.getDrops().add(LivingEntityHead.VINDICATOR);
                        Itemstack.rename(LivingEntityHead.VINDICATOR, ChatColor.YELLOW + config.getString("VINDICATOR.Name"));
                        if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
                    }


                    break;
                case TRADER_LLAMA:
                    if (config.getBoolean("TRADER_LLAMA.Drop") && x <= config.getInt("TRADER_LLAMA.Chance.Name")) {
                        TraderLlama traderLlama = (TraderLlama) entity;

                        switch (traderLlama.getColor()) {
                            case BROWN:
                                event.getDrops().add(LivingEntityHead.TRADER_LLAMA_BROWN);
                                Itemstack.rename(LivingEntityHead.TRADER_LLAMA_BROWN, ChatColor.YELLOW + config.getString("TRADER_LLAMA.Name"));

                                break;
                            case WHITE:
                                event.getDrops().add(LivingEntityHead.TRADER_LLAMA_WHITE);
                                Itemstack.rename(LivingEntityHead.TRADER_LLAMA_WHITE, ChatColor.YELLOW + config.getString("TRADER_LLAMA.Name"));

                                break;
                            case GRAY:
                                event.getDrops().add(LivingEntityHead.TRADER_LLAMA_GRAY);
                                Itemstack.rename(LivingEntityHead.TRADER_LLAMA_GRAY, ChatColor.YELLOW + config.getString("TRADER_LLAMA.Name"));

                                break;
                            case CREAMY:
                                event.getDrops().add(LivingEntityHead.TRADER_LLAMA_CREAMY);
                                Itemstack.rename(LivingEntityHead.TRADER_LLAMA_CREAMY, ChatColor.YELLOW + config.getString("TRADER_LLAMA.Name"));

                                break;
                        }
                        if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
                    }


                    break;
                case WOLF:
                    if (config.getBoolean("WOLF.Drop") && x <= config.getInt("WOLF.Chance")) {
                        Wolf wolf = (Wolf) entity;

                        if (wolf.isAngry()) {
                            event.getDrops().add(LivingEntityHead.WOLF_ANGRY);
                            Itemstack.rename(LivingEntityHead.WOLF_ANGRY, ChatColor.YELLOW + config.getString("WOLF.Name"));
                        } else {
                            event.getDrops().add(LivingEntityHead.WOLF);
                            Itemstack.rename(LivingEntityHead.WOLF, ChatColor.YELLOW + config.getString("WOLF.Name"));
                        }
                        if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
                    }


                    break;
                case VEX:
                    if (config.getBoolean("VEX.Drop") && x <= config.getInt("VEX.Chance")) {

                        Vex vex = (Vex) entity;
                        if (vex.isCharging()) {
                            event.getDrops().add(LivingEntityHead.VEX_CHARGE);
                            Itemstack.rename(LivingEntityHead.VEX_CHARGE, ChatColor.YELLOW + config.getString("VEX.Name"));
                        } else {
                            event.getDrops().add(LivingEntityHead.VEX);
                            Itemstack.rename(LivingEntityHead.VEX, ChatColor.YELLOW + config.getString("VEX.Name"));
                        }
                        if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
                    }


                    break;
                case VILLAGER:
                    if (config.getBoolean("VILLAGER.Drop") && x <= config.getInt("VILLAGER.Chance")) {
                        Villager villager = (Villager) entity;

                        switch (villager.getProfession()) {
                            case WEAPONSMITH:
                                event.getDrops().add(LivingEntityHead.VILLAGER_WEAPONSMITH);
                                Itemstack.rename(LivingEntityHead.VILLAGER_WEAPONSMITH, ChatColor.YELLOW + config.getString("VILLAGER.Name"));

                                break;
                            case SHEPHERD:
                                event.getDrops().add(LivingEntityHead.VILLAGER_SHEPHERD);
                                Itemstack.rename(LivingEntityHead.VILLAGER_SHEPHERD, ChatColor.YELLOW + config.getString("VILLAGER.Name"));

                                break;
                            case LIBRARIAN:
                                event.getDrops().add(LivingEntityHead.VILLAGER_LIBRARIAN);
                                Itemstack.rename(LivingEntityHead.VILLAGER_LIBRARIAN, ChatColor.YELLOW + config.getString("VILLAGER.Name"));

                                break;
                            case FLETCHER:
                                event.getDrops().add(LivingEntityHead.VILLAGER_FLETCHER);
                                Itemstack.rename(LivingEntityHead.VILLAGER_FLETCHER, ChatColor.YELLOW + config.getString("VILLAGER.Name"));

                                break;
                            case FISHERMAN:
                                event.getDrops().add(LivingEntityHead.VILLAGER_FISHERMAN);
                                Itemstack.rename(LivingEntityHead.VILLAGER_FISHERMAN, ChatColor.YELLOW + config.getString("VILLAGER.Name"));

                                break;
                            case FARMER:
                                event.getDrops().add(LivingEntityHead.VILLAGER_FARMER);
                                Itemstack.rename(LivingEntityHead.VILLAGER_FARMER, ChatColor.YELLOW + config.getString("VILLAGER.Name"));

                                break;
                            case CLERIC:
                                event.getDrops().add(LivingEntityHead.VILLAGER_CLERIC);
                                Itemstack.rename(LivingEntityHead.VILLAGER_CLERIC, ChatColor.YELLOW + config.getString("VILLAGER.Name"));

                                break;
                            case CARTOGRAPHER:
                                event.getDrops().add(LivingEntityHead.VILLAGER_CARTOGRAPHER);
                                Itemstack.rename(LivingEntityHead.VILLAGER_CARTOGRAPHER, ChatColor.YELLOW + config.getString("VILLAGER.Name"));

                                break;
                            case BUTCHER:
                                event.getDrops().add(LivingEntityHead.VILLAGER_BUTCHER);
                                Itemstack.rename(LivingEntityHead.VILLAGER_BUTCHER, ChatColor.YELLOW + config.getString("VILLAGER.Name"));

                                break;
                            case ARMORER:
                                event.getDrops().add(LivingEntityHead.VILLAGER_ARMORER);
                                Itemstack.rename(LivingEntityHead.VILLAGER_ARMORER, ChatColor.YELLOW + config.getString("VILLAGER.Name"));

                                break;
                            default:
                                event.getDrops().add(LivingEntityHead.VILLAGER_NULL);
                                Itemstack.rename(LivingEntityHead.VILLAGER_NULL, ChatColor.YELLOW + config.getString("VILLAGER.Name"));
                                break;
                        }
                        if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
                    }

                    break;
                case TROPICAL_FISH:
                    if (config.getBoolean("TROPICAL_FISH.Drop") && x <= config.getInt("TROPICAL_FISH.Chance")) {

                        TropicalFish tropicalFish = (TropicalFish) entity;

                        switch (tropicalFish.getBodyColor()) {
                            case MAGENTA:
                                event.getDrops().add(LivingEntityHead.TROPICAL_FISH_MAGENTA);
                                Itemstack.rename(LivingEntityHead.TROPICAL_FISH_MAGENTA, ChatColor.YELLOW + config.getString("TROPICAL_FISH.Name"));

                                break;
                            case LIGHT_BLUE:
                                event.getDrops().add(LivingEntityHead.TROPICAL_FISH_LIGHT_BLUE);
                                Itemstack.rename(LivingEntityHead.TROPICAL_FISH_LIGHT_BLUE, ChatColor.YELLOW + config.getString("TROPICAL_FISH.Name"));

                                break;
                            case YELLOW:
                                event.getDrops().add(LivingEntityHead.TROPICAL_FISH_YELLOW);
                                Itemstack.rename(LivingEntityHead.TROPICAL_FISH_YELLOW, ChatColor.YELLOW + config.getString("TROPICAL_FISH.Name"));

                                break;
                            case PINK:
                                event.getDrops().add(LivingEntityHead.TROPICAL_FISH_PINK);
                                Itemstack.rename(LivingEntityHead.TROPICAL_FISH_PINK, ChatColor.YELLOW + config.getString("TROPICAL_FISH.Name"));

                                break;
                            case GRAY:
                                event.getDrops().add(LivingEntityHead.TROPICAL_FISH_GRAY);
                                Itemstack.rename(LivingEntityHead.TROPICAL_FISH_GRAY, ChatColor.YELLOW + config.getString("TROPICAL_FISH.Name"));

                                break;
                            case LIGHT_GRAY:
                                event.getDrops().add(LivingEntityHead.TROPICAL_FISH_LIGHT_GRAY);
                                Itemstack.rename(LivingEntityHead.TROPICAL_FISH_LIGHT_GRAY, ChatColor.YELLOW + config.getString("TROPICAL_FISH.Name"));

                                break;
                            case CYAN:
                                event.getDrops().add(LivingEntityHead.TROPICAL_FISH_CYAN);
                                Itemstack.rename(LivingEntityHead.TROPICAL_FISH_CYAN, ChatColor.YELLOW + config.getString("TROPICAL_FISH.Name"));

                                break;
                            case BLUE:
                                event.getDrops().add(LivingEntityHead.TROPICAL_FISH_BLUE);
                                Itemstack.rename(LivingEntityHead.TROPICAL_FISH_BLUE, ChatColor.YELLOW + config.getString("TROPICAL_FISH.Name"));

                                break;
                            case GREEN:
                                event.getDrops().add(LivingEntityHead.TROPICAL_FISH_GREEN);
                                Itemstack.rename(LivingEntityHead.TROPICAL_FISH_GREEN, ChatColor.YELLOW + config.getString("TROPICAL_FISH.Name"));

                                break;
                            case RED:
                                event.getDrops().add(LivingEntityHead.TROPICAL_FISH_RED);
                                Itemstack.rename(LivingEntityHead.TROPICAL_FISH_RED, ChatColor.YELLOW + config.getString("TROPICAL_FISH.Name"));

                                break;
                            case BLACK:
                                event.getDrops().add(LivingEntityHead.TROPICAL_FISH_BLACK);
                                Itemstack.rename(LivingEntityHead.TROPICAL_FISH_BLACK, ChatColor.YELLOW + config.getString("TROPICAL_FISH.Name"));

                                break;

                            default:
                                event.getDrops().add(LivingEntityHead.TROPICAL_FISH_ORANGE);
                                Itemstack.rename(LivingEntityHead.TROPICAL_FISH_ORANGE, ChatColor.YELLOW + config.getString("TROPICAL_FISH.Name"));
                        }
                        if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
                    }


                    break;
                case PARROT:
                    if (config.getBoolean("PARROT.Drop") && x <= config.getInt("PARROT.Chance")) {
                        Parrot parrot = (Parrot) entity;

                        switch (parrot.getVariant()) {
                            case BLUE:
                                event.getDrops().add(LivingEntityHead.PARROT_BLUE);
                                Itemstack.rename(LivingEntityHead.PARROT_BLUE, ChatColor.YELLOW + config.getString("PARROT.Name"));

                                break;
                            case CYAN:
                                event.getDrops().add(LivingEntityHead.PARROT_CYAN);
                                Itemstack.rename(LivingEntityHead.PARROT_CYAN, ChatColor.YELLOW + config.getString("PARROT.Name"));

                                break;
                            case GRAY:
                                event.getDrops().add(LivingEntityHead.PARROT_GRAY);
                                Itemstack.rename(LivingEntityHead.PARROT_GRAY, ChatColor.YELLOW + config.getString("PARROT.Name"));

                                break;
                            case RED:
                                event.getDrops().add(LivingEntityHead.PARROT_RED);
                                Itemstack.rename(LivingEntityHead.PARROT_RED, ChatColor.YELLOW + config.getString("PARROT.Name"));

                                break;
                            case GREEN:
                                event.getDrops().add(LivingEntityHead.PARROT_GREEN);
                                Itemstack.rename(LivingEntityHead.PARROT_GREEN, ChatColor.YELLOW + config.getString("PARROT.Name"));
                                break;
                        }
                        if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
                    }


                    break;
                case PLAYER:
                    if (config.getBoolean("PLAYER.Require-Permission")) {
                        if (!event.getEntity().hasPermission("headdrop.player")) return;
                    }

                    if (config.getBoolean("PLAYER.Drop") && x <= config.getInt("PLAYER.Chance")) {
                        ItemStack skull = SkullCreator.itemFromName(event.getEntity().getName());
                        event.getDrops().add(skull);
                        if (config.getBoolean("Bot.Enable")) Embed.msg(title, description, footer);
                    }


                    break;
            }
        }
    }
}