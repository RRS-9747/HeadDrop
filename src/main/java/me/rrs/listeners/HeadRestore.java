package me.rrs.listeners;

import de.tr7zw.nbtapi.NBTBlock;
import de.tr7zw.nbtapi.NBTItem;
import dev.dejvokep.boostedyaml.YamlDocument;
import me.rrs.HeadDrop;
import me.rrs.database.LivingEntityHead;
import me.rrs.util.ItemUtils;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;

public class HeadRestore implements Listener {
    LivingEntityHead entityHead = new LivingEntityHead();
    YamlDocument config = HeadDrop.getConfiguration();
    ItemStack item;
    NBTItem nbtItem;


    @EventHandler
    public void onHeadPlace(BlockPlaceEvent event) {
        NBTItem item = new NBTItem(event.getItemInHand());
        if (!item.getString("HeadDrop").isEmpty()) {
                NBTBlock headBlock = new NBTBlock(event.getBlock());
                headBlock.getData().setString("HeadDrop", item.getString("HeadDrop"));
        }

    }

    @EventHandler
    public void onHeadBreak(BlockBreakEvent event) {

        NBTBlock headBlock = new NBTBlock(event.getBlock());
        World world = event.getBlock().getWorld();
        if (!headBlock.getData().getString("HeadDrop").isEmpty()) {
            event.getBlock().getDrops().clear();

            switch (headBlock.getData().getString("HeadDrop")) {
                case "BEE":
                    item = ItemUtils.rename(entityHead.BEE, ChatColor.YELLOW + config.getString("BEE.Name"));
                    nbtItem = new NBTItem(item);
                    nbtItem.setString("HeadDrop", "BEE");

                    break;
                case "BEE_ANGER":
                    item = ItemUtils.rename(entityHead.BEE_Aware, ChatColor.YELLOW + config.getString("BEE.Name"));
                    nbtItem = new NBTItem(item);
                    nbtItem.setString("HeadDrop", "BEE_ANGER");

                    break;
                case "AXOLOTL_LUCY":
                    item = ItemUtils.rename(entityHead.AXOLOTL_LUCY, ChatColor.YELLOW + config.getString("AXOLOTL.Name"));
                    nbtItem = new NBTItem(item);
                    nbtItem.setString("HeadDrop", "AXOLOTL_LUCY");

                    break;
                case "AXOLOTL_BLUE":
                    item = ItemUtils.rename(entityHead.AXOLOTL_BLUE, ChatColor.YELLOW + config.getString("AXOLOTL.Name"));
                    nbtItem = new NBTItem(item);
                    nbtItem.setString("HeadDrop", "AXOLOTL_BLUE");

                    break;
                case "AXOLOTL_WILD":
                    item = ItemUtils.rename(entityHead.AXOLOTL_WILD, ChatColor.YELLOW + config.getString("AXOLOTL.Name"));
                    nbtItem = new NBTItem(item);
                    nbtItem.setString("HeadDrop", "AXOLOTL_WILD");

                    break;
                case "AXOLOTL_CYAN":
                    item = ItemUtils.rename(entityHead.AXOLOTL_CYAN, ChatColor.YELLOW + config.getString("AXOLOTL.Name"));
                    nbtItem = new NBTItem(item);
                    nbtItem.setString("HeadDrop", "AXOLOTL_CYAN");

                    break;
                case "AXOLOTL_GOLD":
                    item = ItemUtils.rename(entityHead.AXOLOTL_GOLD, ChatColor.YELLOW + config.getString("AXOLOTL.Name"));
                    nbtItem = new NBTItem(item);
                    nbtItem.setString("HeadDrop", "AXOLOTL_GOLD");

                    break;
                case "BAT":
                    item = ItemUtils.rename(entityHead.BAT, ChatColor.YELLOW + config.getString("BAT.Name"));
                    nbtItem = new NBTItem(item);
                    nbtItem.setString("HeadDrop", "BAT");

                    break;
                case "BLAZE":
                    item = ItemUtils.rename(entityHead.BLAZE, ChatColor.YELLOW + config.getString("BLAZE.Name"));
                    nbtItem = new NBTItem(item);
                    nbtItem.setString("HeadDrop", "BLAZE");

                    break;
                case "CAT_BLACK":
                    item = ItemUtils.rename(entityHead.CAT_BLACK, ChatColor.YELLOW + config.getString("CAT.Name"));
                    nbtItem = new NBTItem(item);
                    nbtItem.setString("HeadDrop", "CAT_BLACK");

                    break;
                case "CAT_BRITISH_SHORTHAIR":
                    item = ItemUtils.rename(entityHead.CAT_BRITISH, ChatColor.YELLOW + config.getString("CAT.Name"));
                    nbtItem = new NBTItem(item);
                    nbtItem.setString("HeadDrop", "CAT_BRITISH_SHORTHAIR");
                    break;

                case "CAT_CALICO":
                    item = ItemUtils.rename(entityHead.CAT_CALICO, ChatColor.YELLOW + config.getString("CAT.Name"));
                    nbtItem = new NBTItem(item);
                    nbtItem.setString("HeadDrop", "CAT_CALICO");

                    break;
                case "CAT_JELLIE":
                    item = ItemUtils.rename(entityHead.CAT_JELLIE, ChatColor.YELLOW + config.getString("CAT.Name"));
                    nbtItem = new NBTItem(item);
                    nbtItem.setString("HeadDrop", "CAT_JELLIE");

                    break;
                case "CAT_PERSIAN":
                    item = ItemUtils.rename(entityHead.CAT_PERSIAN, ChatColor.YELLOW + config.getString("CAT.Name"));
                    nbtItem = new NBTItem(item);
                    nbtItem.setString("HeadDrop", "CAT_PERSIAN");

                    break;
                case "CAT_RAGDOLL":
                    item = ItemUtils.rename(entityHead.CAT_RAGDOLL, ChatColor.YELLOW + config.getString("CAT.Name"));
                    nbtItem = new NBTItem(item);
                    nbtItem.setString("HeadDrop", "CAT_RAGDOLL");

                    break;
                case "CAT_RED":
                    item = ItemUtils.rename(entityHead.CAT_RED, ChatColor.YELLOW + config.getString("CAT.Name"));
                    nbtItem = new NBTItem(item);
                    nbtItem.setString("HeadDrop", "CAT_RED");

                    break;
                case "CAT_SIAMESE":
                    item = ItemUtils.rename(entityHead.CAT_SIAMESE, ChatColor.YELLOW + config.getString("CAT.Name"));
                    nbtItem = new NBTItem(item);
                    nbtItem.setString("HeadDrop", "CAT_SIAMESE");

                    break;
                case "CAT_TABBY":
                    item = ItemUtils.rename(entityHead.CAT_TABBY, ChatColor.YELLOW + config.getString("CAT.Name"));
                    nbtItem = new NBTItem(item);
                    nbtItem.setString("HeadDrop", "CAT_TABBY");

                    break;
                case "CAT_WHITE":
                    item = ItemUtils.rename(entityHead.CAT_WHITE, ChatColor.YELLOW + config.getString("CAT.Name"));
                    nbtItem = new NBTItem(item);
                    nbtItem.setString("HeadDrop", "CAT_WHITE");

                    break;
                case "SPIDER":
                    item = ItemUtils.rename(entityHead.SPIDER, ChatColor.YELLOW + config.getString("SPIDER.Name"));
                    nbtItem = new NBTItem(item);
                    nbtItem.setString("HeadDrop", "SPIDER");

                    break;
                case "CAVE_SPIDER":
                    item = ItemUtils.rename(entityHead.CAVE_SPIDER, ChatColor.YELLOW + config.getString("CAVE_SPIDER.Name"));
                    nbtItem = new NBTItem(item);
                    nbtItem.setString("HeadDrop", "CAVE_SPIDER");

                    break;
                case "CHICKEN":
                    item = ItemUtils.rename(entityHead.CHICKEN, ChatColor.YELLOW + config.getString("CHICKEN.Name"));
                    nbtItem = new NBTItem(item);
                    nbtItem.setString("HeadDrop", "CHICKEN");

                    break;
                case "COD":
                    item = ItemUtils.rename(entityHead.COD, ChatColor.YELLOW + config.getString("COD.Name"));
                    nbtItem = new NBTItem(item);
                    nbtItem.setString("HeadDrop", "COD");

                    break;
                case "COW":
                    item = ItemUtils.rename(entityHead.COW, ChatColor.YELLOW + config.getString("COW.Name"));
                    nbtItem = new NBTItem(item);
                    nbtItem.setString("HeadDrop", "COW");

                    break;
                case "DOLPHIN":
                    item = ItemUtils.rename(entityHead.DOLPHIN, ChatColor.YELLOW + config.getString("DOLPHIN.Name"));
                    nbtItem = new NBTItem(item);
                    nbtItem.setString("HeadDrop", "DOLPHIN");

                    break;
                case "DONKEY":
                    item = ItemUtils.rename(entityHead.DONKEY, ChatColor.YELLOW + config.getString("DONKEY.Name"));
                    nbtItem = new NBTItem(item);
                    nbtItem.setString("HeadDrop", "DONKEY");

                    break;
                case "DROWNED":
                    item = ItemUtils.rename(entityHead.DROWNED, ChatColor.YELLOW + config.getString("DROWNED.Name"));
                    nbtItem = new NBTItem(item);
                    nbtItem.setString("HeadDrop", "DROWNED");

                    break;
                case "ELDER_GUARDIAN":
                    item = ItemUtils.rename(entityHead.ELDER_GUARDIAN, ChatColor.YELLOW + config.getString("ELDER_GUARDIAN.Name"));
                    nbtItem = new NBTItem(item);
                    nbtItem.setString("HeadDrop", "ELDER_GUARDIAN");

                    break;
                case "ENDERMAN":
                    item = ItemUtils.rename(entityHead.ENDERMAN, ChatColor.YELLOW + config.getString("ENDERMAN.Name"));
                    nbtItem = new NBTItem(item);
                    nbtItem.setString("HeadDrop", "ENDERMAN");

                    break;
                case "ENDERMITE":
                    item = ItemUtils.rename(entityHead.ENDERMITE, ChatColor.YELLOW + config.getString("ENDERMITE.Name"));
                    nbtItem = new NBTItem(item);
                    nbtItem.setString("HeadDrop", "ENDERMITE");

                    break;
                case "EVOKER":
                    item = ItemUtils.rename(entityHead.EVOKER, ChatColor.YELLOW + config.getString("EVOKER.Name"));
                    nbtItem = new NBTItem(item);
                    nbtItem.setString("HeadDrop", "EVOKER");

                    break;
                case "FOX_RED":
                    item = ItemUtils.rename(entityHead.FOX, ChatColor.YELLOW + config.getString("FOX.Name"));
                    nbtItem = new NBTItem(item);
                    nbtItem.setString("HeadDrop", "FOX_RED");

                    break;
                case "FOX_SNOW":
                    item = ItemUtils.rename(entityHead.FOX_WHITE, ChatColor.YELLOW + config.getString("FOX.Name"));
                    nbtItem = new NBTItem(item);
                    nbtItem.setString("HeadDrop", "FOX_SNOW");
                    break;
                case "GIANT":
                    item = ItemUtils.rename(entityHead.GIANT, ChatColor.YELLOW + config.getString("GIANT.Name"));
                    nbtItem = new NBTItem(item);
                    nbtItem.setString("HeadDrop", "GIANT");

                    break;
                case "GLOW_SQUID":
                    item = ItemUtils.rename(entityHead.GLOW_SQUID, ChatColor.YELLOW + config.getString("GLOW_SQUID.Name"));
                    nbtItem = new NBTItem(item);
                    nbtItem.setString("HeadDrop", "GLOW_SQUID");

                    break;
                case "GOAT":
                    item = ItemUtils.rename(entityHead.GOAT, ChatColor.YELLOW + config.getString("GOAT.Name"));
                    nbtItem = new NBTItem(item);
                    nbtItem.setString("HeadDrop", "GOAT");

                    break;
                case "GUARDIAN":
                    item = ItemUtils.rename(entityHead.GUARDIAN, ChatColor.YELLOW + config.getString("GUARDIAN.Name"));
                    nbtItem = new NBTItem(item);
                    nbtItem.setString("HeadDrop", "GUARDIAN");

                    break;
                case "HOGLIN":
                    item = ItemUtils.rename(entityHead.HOGLIN, ChatColor.YELLOW + config.getString("HOGLIN.Name"));
                    nbtItem = new NBTItem(item);
                    nbtItem.setString("HeadDrop", "HOGLIN");

                    break;
                case "HORSE_WHITE":
                    item = ItemUtils.rename(entityHead.HORSE_WHITE, ChatColor.YELLOW + config.getString("HORSE.Name"));
                    nbtItem = new NBTItem(item);
                    nbtItem.setString("HeadDrop", "HORSE_WHITE");

                    break;
                case "HORSE_CREAMY":
                    item = ItemUtils.rename(entityHead.HORSE_CREAMY, ChatColor.YELLOW + config.getString("HORSE.Name"));
                    nbtItem = new NBTItem(item);
                    nbtItem.setString("HeadDrop", "HORSE_CREAMY");

                    break;
                case "HORSE_CHESTNUT":
                    item = ItemUtils.rename(entityHead.HORSE_CHESTNUT, ChatColor.YELLOW + config.getString("HORSE.Name"));
                    nbtItem = new NBTItem(item);
                    nbtItem.setString("HeadDrop", "HORSE_CHESTNUT");

                    break;
                case "HORSE_BROWN":
                    item = ItemUtils.rename(entityHead.HORSE_BROWN, ChatColor.YELLOW + config.getString("HORSE.Name"));
                    nbtItem = new NBTItem(item);
                    nbtItem.setString("HeadDrop", "HORSE_BROWN");

                    break;
                case "HORSE_BLACK":
                    item = ItemUtils.rename(entityHead.HORSE_BLACK, ChatColor.YELLOW + config.getString("HORSE.Name"));
                    nbtItem = new NBTItem(item);
                    nbtItem.setString("HeadDrop", "HORSE_BLACK");

                    break;
                case "HORSE_GRAY":
                    item = ItemUtils.rename(entityHead.HORSE_GRAY, ChatColor.YELLOW + config.getString("HORSE.Name"));
                    nbtItem = new NBTItem(item);
                    nbtItem.setString("HeadDrop", "HORSE_GRAY");

                    break;
                case "HORSE_DARK_BROWN":
                    item = ItemUtils.rename(entityHead.HORSE_DARK_BROWN, ChatColor.YELLOW + config.getString("HORSE.Name"));
                    nbtItem = new NBTItem(item);
                    nbtItem.setString("HeadDrop", "HORSE_DARK_BROWN");

                    break;
                case "HUSK":
                    item = ItemUtils.rename(entityHead.HUSK, ChatColor.YELLOW + config.getString("HUSK.Name"));
                    nbtItem = new NBTItem(item);
                    nbtItem.setString("HeadDrop", "HUSK");

                    break;
                case "ILLUSIONER":
                    item = ItemUtils.rename(entityHead.ILLUSIONER, ChatColor.YELLOW + config.getString("ILLUSIONER.Name"));
                    nbtItem = new NBTItem(item);
                    nbtItem.setString("HeadDrop", "ILLUSIONER");

                    break;
                case "IRON_GOLEM":
                    item = ItemUtils.rename(entityHead.IRON_GOLEM, ChatColor.YELLOW + config.getString("IRON_GOLEM.Name"));
                    nbtItem = new NBTItem(item);
                    nbtItem.setString("HeadDrop", "IRON_GOLEM");

                    break;
                case "LLAMA_BROWN":
                    item = ItemUtils.rename(entityHead.LLAMA_BROWN, ChatColor.YELLOW + config.getString("LLAMA.Name"));
                    nbtItem = new NBTItem(item);
                    nbtItem.setString("HeadDrop", "LLAMA_BROWN");

                    break;
                case "LLAMA_GRAY":
                    item = ItemUtils.rename(entityHead.LLAMA_GRAY, ChatColor.YELLOW + config.getString("LLAMA.Name"));
                    nbtItem = new NBTItem(item);
                    nbtItem.setString("HeadDrop", "LLAMA_GRAY");

                    break;
                case "LLAMA_CREAMY":
                    item = ItemUtils.rename(entityHead.LLAMA_CREAMY, ChatColor.YELLOW + config.getString("LLAMA.Name"));
                    nbtItem = new NBTItem(item);
                    nbtItem.setString("HeadDrop", "LLAMA_CREAMY");

                    break;
                case "LLAMA_WHITE":
                    item = ItemUtils.rename(entityHead.LLAMA_WHITE, ChatColor.YELLOW + config.getString("LLAMA.Name"));
                    nbtItem = new NBTItem(item);
                    nbtItem.setString("HeadDrop", "LLAMA_WHITE");

                    break;
                case "MAGMA_CUBE":
                    item = ItemUtils.rename(entityHead.MAGMA_CUBE, ChatColor.YELLOW + config.getString("MAGMA_CUBE.Name"));
                    nbtItem = new NBTItem(item);
                    nbtItem.setString("HeadDrop", "MAGMA_CUBE");

                    break;
                case "MUSHROOM_COW_RED":
                    item = ItemUtils.rename(entityHead.MUSHROOM_COW_RED, ChatColor.YELLOW + config.getString("MUSHROOM_COW.Name"));
                    nbtItem = new NBTItem(item);
                    nbtItem.setString("HeadDrop", "MUSHROOM_COW_RED");

                    break;
                case "MUSHROOM_COW_BROWN":
                    item = ItemUtils.rename(entityHead.MUSHROOM_COW_BROWN, ChatColor.YELLOW + config.getString("MUSHROOM_COW.Name"));
                    nbtItem = new NBTItem(item);
                    nbtItem.setString("HeadDrop", "MUSHROOM_COW_BROWN");

                    break;
                case "MULE":
                    item = ItemUtils.rename(entityHead.MULE, ChatColor.YELLOW + config.getString("MULE.Name"));
                    nbtItem = new NBTItem(item);
                    nbtItem.setString("HeadDrop", "MULE");

                    break;
                case "OCELOT":
                    item = ItemUtils.rename(entityHead.OCELOT, ChatColor.YELLOW + config.getString("OCELOT.Name"));
                    nbtItem = new NBTItem(item);
                    nbtItem.setString("HeadDrop", "OCELOT");

                    break;
                case "PANDA_BROWN":
                    item = ItemUtils.rename(entityHead.PANDA_BROWN, ChatColor.YELLOW + config.getString("PANDA.Name"));
                    nbtItem = new NBTItem(item);
                    nbtItem.setString("HeadDrop", "PANDA_BROWN");

                    break;
                case "PANDA_DEFAULT":
                    item = ItemUtils.rename(entityHead.PANDA, ChatColor.YELLOW + config.getString("PANDA.Name"));
                    nbtItem = new NBTItem(item);
                    nbtItem.setString("HeadDrop", "PANDA_DEFAULT");


                    break;
                case "PHANTOM":
                    item = ItemUtils.rename(entityHead.PHANTOM, ChatColor.YELLOW + config.getString("PHANTOM.Name"));
                    nbtItem = new NBTItem(item);
                    nbtItem.setString("HeadDrop", "PHANTOM");

                    break;
                case "PIG":
                    item = ItemUtils.rename(entityHead.PIG, ChatColor.YELLOW + config.getString("PIG.Name"));
                    nbtItem = new NBTItem(item);
                    nbtItem.setString("HeadDrop", "PIG");

                    break;
                case "PIGLIN":
                    item = ItemUtils.rename(entityHead.PIGLIN, ChatColor.YELLOW + config.getString("PIGLIN.Name"));
                    nbtItem = new NBTItem(item);
                    nbtItem.setString("HeadDrop", "PIGLIN");


                    break;
                case "PIGLIN_BRUTE":
                    item = ItemUtils.rename(entityHead.PIGLIN_BRUTE, ChatColor.YELLOW + config.getString("PIGLIN_BRUTE.Name"));
                    nbtItem = new NBTItem(item);
                    nbtItem.setString("HeadDrop", "PIGLIN_BRUTE");

                    break;
                case "PILLAGER":
                    item = ItemUtils.rename(entityHead.PILLAGER, ChatColor.YELLOW + config.getString("PILLAGER.Name"));
                    nbtItem = new NBTItem(item);
                    nbtItem.setString("HeadDrop", "PILLAGER");


                    break;
                case "POLAR_BEAR":
                    item = ItemUtils.rename(entityHead.POLAR_BEAR, ChatColor.YELLOW + config.getString("POLAR_BEAR.Name"));
                    nbtItem = new NBTItem(item);
                    nbtItem.setString("HeadDrop", "POLAR_BEAR");

                    break;
                case "PUFFERFISH":
                    item = ItemUtils.rename(entityHead.PUFFERFISH, ChatColor.YELLOW + config.getString("PUFFERFISH.Name"));
                    nbtItem = new NBTItem(item);
                    nbtItem.setString("HeadDrop", "PUFFERFISH");

                    break;
                case "RABBIT_BROWN":
                    item = ItemUtils.rename(entityHead.RABBIT_BROWN, ChatColor.YELLOW + config.getString("RABBIT.Name"));
                    nbtItem = new NBTItem(item);
                    nbtItem.setString("HeadDrop", "RABBIT_BROWN");

                    break;
                case "RABBIT_WHITE":
                    item = ItemUtils.rename(entityHead.RABBIT_WHITE, ChatColor.YELLOW + config.getString("RABBIT.Name"));
                    nbtItem = new NBTItem(item);
                    nbtItem.setString("HeadDrop", "RABBIT_WHITE");

                    break;
                case "RABBIT_BLACK":
                    item = ItemUtils.rename(entityHead.RABBIT_BLACK, ChatColor.YELLOW + config.getString("RABBIT.Name"));
                    nbtItem = new NBTItem(item);
                    nbtItem.setString("HeadDrop", "RABBIT_BLACK");

                    break;
                case "RABBIT_BLACK_AND_WHITE":
                    item = ItemUtils.rename(entityHead.RABBIT_BLACK_AND_WHITE, ChatColor.YELLOW + config.getString("RABBIT.Name"));
                    nbtItem = new NBTItem(item);
                    nbtItem.setString("HeadDrop", "RABBIT_BLACK_AND_WHITE");

                    break;
                case "RABBIT_GOLD":
                    item = ItemUtils.rename(entityHead.RABBIT_GOLD, ChatColor.YELLOW + config.getString("RABBIT.Name"));
                    nbtItem = new NBTItem(item);
                    nbtItem.setString("HeadDrop", "RABBIT_GOLD");

                    break;
                case "RABBIT_SALT_AND_PEPPER":
                    item = ItemUtils.rename(entityHead.RABBIT_SALT_AND_PEPPER, ChatColor.YELLOW + config.getString("RABBIT.Name"));
                    nbtItem = new NBTItem(item);
                    nbtItem.setString("HeadDrop", "RABBIT_SALT_AND_PEPPER");

                    break;
                case "RABBIT_THE_KILLER_BUNNY":
                    item = ItemUtils.rename(entityHead.RABBIT_THE_KILLER_BUNNY, ChatColor.YELLOW + config.getString("RABBIT.Name"));
                    nbtItem = new NBTItem(item);
                    nbtItem.setString("HeadDrop", "RABBIT_THE_KILLER_BUNNY");

                    break;
                case "RAVAGER":
                    item = ItemUtils.rename(entityHead.RAVAGER, ChatColor.YELLOW + config.getString("RAVAGER.Name"));
                    nbtItem = new NBTItem(item);
                    nbtItem.setString("HeadDrop", "RAVAGER");
                    break;
                case "SALMON":
                    item = ItemUtils.rename(entityHead.SALMON, ChatColor.YELLOW + config.getString("SALMON.Name"));
                    nbtItem = new NBTItem(item);
                    nbtItem.setString("HeadDrop", "SALMON");

                    break;
                case "SHULKER":
                    item = ItemUtils.rename(entityHead.SHULKER, ChatColor.YELLOW + config.getString("SHULKER.Name"));
                    nbtItem = new NBTItem(item);
                    nbtItem.setString("HeadDrop", "SHULKER");

                    break;
                case "SILVERFISH":
                    item = ItemUtils.rename(entityHead.SILVERFISH, ChatColor.YELLOW + config.getString("SILVERFISH.Name"));
                    nbtItem = new NBTItem(item);
                    nbtItem.setString("HeadDrop", "SILVERFISH");

                    break;
                case "SKELETON_HORSE":
                    item = ItemUtils.rename(entityHead.SKELETON_HORSE, ChatColor.YELLOW + config.getString("SKELETON_HORSE.Name"));
                    nbtItem = new NBTItem(item);
                    nbtItem.setString("HeadDrop", "");

                    break;
                case "SLIME":
                    item = ItemUtils.rename(entityHead.SLIME, ChatColor.YELLOW + config.getString("SLIME.Name"));
                    nbtItem = new NBTItem(item);
                    nbtItem.setString("HeadDrop", "");

                    break;
                case "SNOWMAN":
                    item = ItemUtils.rename(entityHead.SQUID, ChatColor.YELLOW + config.getString("SQUID.Name"));
                    nbtItem = new NBTItem(item);
                    nbtItem.setString("HeadDrop", "");

                    break;
                case "STRAY":
                    item = ItemUtils.rename(entityHead.STRAY, ChatColor.YELLOW + config.getString("STRAY.Name"));
                    nbtItem = new NBTItem(item);
                    nbtItem.setString("HeadDrop", "");

                    break;
                case "STRIDER":
                    item = ItemUtils.rename(entityHead.STRIDER, ChatColor.YELLOW + config.getString("STRIDER.Name"));
                    nbtItem = new NBTItem(item);
                    nbtItem.setString("HeadDrop", "");

                    break;
                case "TURTLE":
                    item = ItemUtils.rename(entityHead.TURTLE, ChatColor.YELLOW + config.getString("TURTLE.Name"));
                    nbtItem = new NBTItem(item);
                    nbtItem.setString("HeadDrop", "");

                    break;
                case "WANDERING_TRADER":
                    item = ItemUtils.rename(entityHead.WANDERING_TRADER, ChatColor.YELLOW + config.getString("WANDERING_TRADER.Name"));
                    nbtItem = new NBTItem(item);
                    nbtItem.setString("HeadDrop", "");

                    break;
                case "WITCH":
                    item = ItemUtils.rename(entityHead.WITCH, ChatColor.YELLOW + config.getString("WITCH.Name"));
                    nbtItem = new NBTItem(item);
                    nbtItem.setString("HeadDrop", "");

                    break;
                case "WITHER":
                    item = ItemUtils.rename(entityHead.WITHER, ChatColor.YELLOW + config.getString("WITHER.Name"));
                    nbtItem = new NBTItem(item);
                    nbtItem.setString("HeadDrop", "");

                    break;
                case "ZOGLIN":
                    item = ItemUtils.rename(entityHead.ZOGLIN, ChatColor.YELLOW + config.getString("ZOGLIN.Name"));
                    nbtItem = new NBTItem(item);
                    nbtItem.setString("HeadDrop", "");

                    break;
                case "ZOMBIE_HORSE":
                    item = ItemUtils.rename(entityHead.ZOMBIE_HORSE, ChatColor.YELLOW + config.getString("ZOMBIE_HORSE.Name"));
                    nbtItem = new NBTItem(item);
                    nbtItem.setString("HeadDrop", "");

                    break;
                case "ZOMBIFIED_PIGLIN":
                    item = ItemUtils.rename(entityHead.ZOMBIFIED_PIGLIN, ChatColor.YELLOW + config.getString("ZOMBIFIED_PIGLIN.Name"));
                    nbtItem = new NBTItem(item);
                    nbtItem.setString("HeadDrop", "");

                    break;
                case "GHAST":
                    item = ItemUtils.rename(entityHead.GHAST, ChatColor.YELLOW + config.getString("GHAST.Name"));
                    nbtItem = new NBTItem(item);
                    nbtItem.setString("HeadDrop", "");

                    break;
                case "ZOMBIE_VILLAGER_ARMORER":
                    item = ItemUtils.rename(entityHead.ZOMBIE_VILLAGER_ARMORER, ChatColor.YELLOW + config.getString("ZOMBIE_VILLAGER.Name"));
                    nbtItem = new NBTItem(item);
                    nbtItem.setString("HeadDrop", "");

                    break;
                case "ZOMBIE_VILLAGER_BUTCHER":
                    item = ItemUtils.rename(entityHead.ZOMBIE_VILLAGER_BUTCHER, ChatColor.YELLOW + config.getString("ZOMBIE_VILLAGER.Name"));
                    nbtItem = new NBTItem(item);
                    nbtItem.setString("HeadDrop", "");

                    break;
                case "ZOMBIE_VILLAGER_CARTOGRAPHER":
                    item = ItemUtils.rename(entityHead.ZOMBIE_VILLAGER_CARTOGRAPHER, ChatColor.YELLOW + config.getString("ZOMBIE_VILLAGER.Name"));
                    nbtItem = new NBTItem(item);
                    nbtItem.setString("HeadDrop", "");

                    break;
                case "ZOMBIE_VILLAGER_CLERIC":
                    item = ItemUtils.rename(entityHead.ZOMBIE_VILLAGER_CLERIC, ChatColor.YELLOW + config.getString("ZOMBIE_VILLAGER.Name"));
                    nbtItem = new NBTItem(item);
                    nbtItem.setString("HeadDrop", "");

                    break;
                case "ZOMBIE_VILLAGER_FARMER":
                    item = ItemUtils.rename(entityHead.ZOMBIE_VILLAGER_FARMER, ChatColor.YELLOW + config.getString("ZOMBIE_VILLAGER.Name"));
                    nbtItem = new NBTItem(item);
                    nbtItem.setString("HeadDrop", "");

                    break;
                case "ZOMBIE_VILLAGER_FISHERMAN":
                    item = ItemUtils.rename(entityHead.ZOMBIE_VILLAGER_FISHERMAN, ChatColor.YELLOW + config.getString("ZOMBIE_VILLAGER.Name"));
                    nbtItem = new NBTItem(item);
                    nbtItem.setString("HeadDrop", "");

                    break;
                case "ZOMBIE_VILLAGER_FLETCHER":
                    item = ItemUtils.rename(entityHead.ZOMBIE_VILLAGER_FLETCHER, ChatColor.YELLOW + config.getString("ZOMBIE_VILLAGER.Name"));
                    nbtItem = new NBTItem(item);
                    nbtItem.setString("HeadDrop", "");

                    break;
                case "ZOMBIE_VILLAGER_LIBRARIAN":
                    item = ItemUtils.rename(entityHead.ZOMBIE_VILLAGER_LIBRARIAN, ChatColor.YELLOW + config.getString("ZOMBIE_VILLAGER.Name"));
                    nbtItem = new NBTItem(item);
                    nbtItem.setString("HeadDrop", "");

                    break;
                case "ZOMBIE_VILLAGER_SHEPHERD":
                    item = ItemUtils.rename(entityHead.ZOMBIE_VILLAGER_SHEPHERD, ChatColor.YELLOW + config.getString("ZOMBIE_VILLAGER.Name"));
                    nbtItem = new NBTItem(item);
                    nbtItem.setString("HeadDrop", "");

                    break;
                case "ZOMBIE_VILLAGER_WEAPONSMITH":
                    item = ItemUtils.rename(entityHead.ZOMBIE_VILLAGER_WEAPONSMITH, ChatColor.YELLOW + config.getString("ZOMBIE_VILLAGER.Name"));
                    nbtItem = new NBTItem(item);
                    nbtItem.setString("HeadDrop", "");

                    break;
                case "ZOMBIE_VILLAGER_DEFAULT":
                    item = ItemUtils.rename(entityHead.ZOMBIE_VILLAGER_NULL, ChatColor.YELLOW + config.getString("ZOMBIE_VILLAGER.Name"));
                    nbtItem = new NBTItem(item);
                    nbtItem.setString("HeadDrop", "ZOMBIE_VILLAGER_DEFAULT");

                    break;
                case "VINDICATOR":
                    item = ItemUtils.rename(entityHead.VINDICATOR, ChatColor.YELLOW + config.getString("VINDICATOR.Name"));
                    nbtItem = new NBTItem(item);
                    nbtItem.setString("HeadDrop", "");

                    break;
                case "TRADER_LLAMA_BROWN":
                    item = ItemUtils.rename(entityHead.TRADER_LLAMA_BROWN, ChatColor.YELLOW + config.getString("TRADER_LLAMA.Name"));
                    nbtItem = new NBTItem(item);
                    nbtItem.setString("HeadDrop", "");

                    break;
                case "TRADER_LLAMA_WHITE":
                    item = ItemUtils.rename(entityHead.TRADER_LLAMA_WHITE, ChatColor.YELLOW + config.getString("TRADER_LLAMA.Name"));
                    nbtItem = new NBTItem(item);
                    nbtItem.setString("HeadDrop", "");

                    break;
                case "TRADER_LLAMA_GRAY":
                    item = ItemUtils.rename(entityHead.TRADER_LLAMA_GRAY, ChatColor.YELLOW + config.getString("TRADER_LLAMA.Name"));
                    nbtItem = new NBTItem(item);
                    nbtItem.setString("HeadDrop", "");

                    break;
                case "TRADER_LLAMA_CREAMY":
                    item = ItemUtils.rename(entityHead.TRADER_LLAMA_CREAMY, ChatColor.YELLOW + config.getString("TRADER_LLAMA.Name"));
                    nbtItem = new NBTItem(item);
                    nbtItem.setString("HeadDrop", "");

                    break;
                case "WOLF_ANGER":
                    item = ItemUtils.rename(entityHead.WOLF_ANGRY, ChatColor.YELLOW + config.getString("WOLF.Name"));
                    nbtItem = new NBTItem(item);
                    nbtItem.setString("HeadDrop", "WOLF_ANGER");

                    break;
                case "WOLF":
                    item = ItemUtils.rename(entityHead.WOLF, ChatColor.YELLOW + config.getString("WOLF.Name"));
                    nbtItem = new NBTItem(item);
                    nbtItem.setString("HeadDrop", "WOLF");

                    break;
                case "VEX_ANGER":
                    item = ItemUtils.rename(entityHead.VEX_CHARGE, ChatColor.YELLOW + config.getString("VEX.Name"));
                    nbtItem = new NBTItem(item);
                    nbtItem.setString("HeadDrop", "VEX_ANGER");

                    break;
                case "VEX":
                    item = ItemUtils.rename(entityHead.VEX, ChatColor.YELLOW + config.getString("VEX.Name"));
                    nbtItem = new NBTItem(item);
                    nbtItem.setString("HeadDrop", "VEX");

                    break;
                case "VILLAGER_WEAPONSMITH":
                    item = ItemUtils.rename(entityHead.VILLAGER_WEAPONSMITH, ChatColor.YELLOW + config.getString("VILLAGER.Name"));
                    nbtItem = new NBTItem(item);
                    nbtItem.setString("HeadDrop", "VILLAGER_WEAPONSMITH");

                    break;
                case "VILLAGER_SHEPHERD":
                    item = ItemUtils.rename(entityHead.VILLAGER_SHEPHERD, ChatColor.YELLOW + config.getString("VILLAGER.Name"));
                    nbtItem = new NBTItem(item);
                    nbtItem.setString("HeadDrop", "VILLAGER_SHEPHERD");

                    break;
                case "VILLAGER_LIBRARIAN":
                    item = ItemUtils.rename(entityHead.VILLAGER_LIBRARIAN, ChatColor.YELLOW + config.getString("VILLAGER.Name"));
                    nbtItem = new NBTItem(item);
                    nbtItem.setString("HeadDrop", "VILLAGER_LIBRARIAN");

                    break;
                case "VILLAGER_FLETCHER":
                    item = ItemUtils.rename(entityHead.VILLAGER_FLETCHER, ChatColor.YELLOW + config.getString("VILLAGER.Name"));
                    nbtItem = new NBTItem(item);
                    nbtItem.setString("HeadDrop", "VILLAGER_FLETCHER");

                    break;
                case "VILLAGER_FISHERMAN":
                    item = ItemUtils.rename(entityHead.VILLAGER_FISHERMAN, ChatColor.YELLOW + config.getString("VILLAGER.Name"));
                    nbtItem = new NBTItem(item);
                    nbtItem.setString("HeadDrop", "VILLAGER_FISHERMAN");

                    break;
                case "VILLAGER_FARMER":
                    item = ItemUtils.rename(entityHead.VILLAGER_FARMER, ChatColor.YELLOW + config.getString("VILLAGER.Name"));
                    nbtItem = new NBTItem(item);
                    nbtItem.setString("HeadDrop", "VILLAGER_FARMER");

                    break;
                case "VILLAGER_CLERIC":
                    item = ItemUtils.rename(entityHead.VILLAGER_CLERIC, ChatColor.YELLOW + config.getString("VILLAGER.Name"));
                    nbtItem = new NBTItem(item);
                    nbtItem.setString("HeadDrop", "VILLAGER_CLERIC");

                    break;
                case "VILLAGER_CARTOGRAPHER":
                    item = ItemUtils.rename(entityHead.VILLAGER_CARTOGRAPHER, ChatColor.YELLOW + config.getString("VILLAGER.Name"));
                    nbtItem = new NBTItem(item);
                    nbtItem.setString("HeadDrop", "VILLAGER_CARTOGRAPHER");

                    break;
                case "VILLAGER_BUTCHER":
                    item = ItemUtils.rename(entityHead.VILLAGER_BUTCHER, ChatColor.YELLOW + config.getString("VILLAGER.Name"));
                    nbtItem = new NBTItem(item);
                    nbtItem.setString("HeadDrop", "VILLAGER_BUTCHER");

                    break;
                case "VILLAGER_ARMORER":
                    item = ItemUtils.rename(entityHead.VILLAGER_ARMORER, ChatColor.YELLOW + config.getString("VILLAGER.Name"));
                    nbtItem = new NBTItem(item);
                    nbtItem.setString("HeadDrop", "VILLAGER_ARMORER");

                    break;
                case "VILLAGER_DEFAULT":
                    item = ItemUtils.rename(entityHead.VILLAGER_NULL, ChatColor.YELLOW + config.getString("VILLAGER.Name"));
                    nbtItem = new NBTItem(item);
                    nbtItem.setString("HeadDrop", "VILLAGER_DEFAULT");

                    break;
                case "TROPICAL_FISH_MAGENTA":
                    item = ItemUtils.rename(entityHead.TROPICAL_FISH_MAGENTA, ChatColor.YELLOW + config.getString("TROPICAL_FISH.Name"));
                    nbtItem = new NBTItem(item);
                    nbtItem.setString("HeadDrop", "TROPICAL_FISH_MAGENTA");

                    break;
                case "TROPICAL_FISH_LIGHT_BLUE":
                    item = ItemUtils.rename(entityHead.TROPICAL_FISH_LIGHT_BLUE, ChatColor.YELLOW + config.getString("TROPICAL_FISH.Name"));
                    nbtItem = new NBTItem(item);
                    nbtItem.setString("HeadDrop", "TROPICAL_FISH_LIGHT_BLUE");

                    break;
                case "TROPICAL_FISH_YELLOW":
                    item = ItemUtils.rename(entityHead.TROPICAL_FISH_YELLOW, ChatColor.YELLOW + config.getString("TROPICAL_FISH.Name"));
                    nbtItem = new NBTItem(item);
                    nbtItem.setString("HeadDrop", "TROPICAL_FISH_YELLOW");

                    break;
                case "TROPICAL_FISH_PINK":
                    item = ItemUtils.rename(entityHead.TROPICAL_FISH_PINK, ChatColor.YELLOW + config.getString("TROPICAL_FISH.Name"));
                    nbtItem = new NBTItem(item);
                    nbtItem.setString("HeadDrop", "TROPICAL_FISH_PINK");

                    break;
                case "TROPICAL_FISH_GRAY":
                    item = ItemUtils.rename(entityHead.TROPICAL_FISH_GRAY, ChatColor.YELLOW + config.getString("TROPICAL_FISH.Name"));
                    nbtItem = new NBTItem(item);
                    nbtItem.setString("HeadDrop", "TROPICAL_FISH_GRAY");

                    break;
                case "TROPICAL_FISH_LIGHT_GRAY":
                    item = ItemUtils.rename(entityHead.TROPICAL_FISH_LIGHT_GRAY, ChatColor.YELLOW + config.getString("TROPICAL_FISH.Name"));
                    nbtItem = new NBTItem(item);
                    nbtItem.setString("HeadDrop", "TROPICAL_FISH_LIGHT_GRAY");

                    break;
                case "TROPICAL_FISH_CYAN":
                    item = ItemUtils.rename(entityHead.TROPICAL_FISH_CYAN, ChatColor.YELLOW + config.getString("TROPICAL_FISH.Name"));
                    nbtItem = new NBTItem(item);
                    nbtItem.setString("HeadDrop", "TROPICAL_FISH_CYAN");

                    break;
                case "TROPICAL_FISH_BLUE":
                    item = ItemUtils.rename(entityHead.TROPICAL_FISH_BLUE, ChatColor.YELLOW + config.getString("TROPICAL_FISH.Name"));
                    nbtItem = new NBTItem(item);
                    nbtItem.setString("HeadDrop", "TROPICAL_FISH_BLUE");

                    break;
                case "TROPICAL_FISH_GREEN":
                    item = ItemUtils.rename(entityHead.TROPICAL_FISH_GREEN, ChatColor.YELLOW + config.getString("TROPICAL_FISH.Name"));
                    nbtItem = new NBTItem(item);
                    nbtItem.setString("HeadDrop", "TROPICAL_FISH_GREEN");

                    break;
                case "TROPICAL_FISH_RED":
                    item = ItemUtils.rename(entityHead.TROPICAL_FISH_RED, ChatColor.YELLOW + config.getString("TROPICAL_FISH.Name"));
                    nbtItem = new NBTItem(item);
                    nbtItem.setString("HeadDrop", "TROPICAL_FISH_RED");

                    break;
                case "TROPICAL_FISH_BLACK":
                    item = ItemUtils.rename(entityHead.TROPICAL_FISH_BLACK, ChatColor.YELLOW + config.getString("TROPICAL_FISH.Name"));
                    nbtItem = new NBTItem(item);
                    nbtItem.setString("HeadDrop", "TROPICAL_FISH_BLACK");

                    break;
                case "TROPICAL_FISH_DEFAULT":
                    item = ItemUtils.rename(entityHead.TROPICAL_FISH_ORANGE, ChatColor.YELLOW + config.getString("TROPICAL_FISH.Name"));
                    nbtItem = new NBTItem(item);
                    nbtItem.setString("HeadDrop", "TROPICAL_FISH_DEFAULT");

                    break;
                case "PARROT_BLUE":
                    item = ItemUtils.rename(entityHead.PARROT_BLUE, ChatColor.YELLOW + config.getString("PARROT.Name"));
                    nbtItem = new NBTItem(item);
                    nbtItem.setString("HeadDrop", "PARROT_BLUE");

                    break;
                case "PARROT_CYAN":
                    item = ItemUtils.rename(entityHead.PARROT_CYAN, ChatColor.YELLOW + config.getString("PARROT.Name"));
                    nbtItem = new NBTItem(item);
                    nbtItem.setString("HeadDrop", "PARROT_CYAN");

                    break;
                case "PARROT_GRAY":
                    item = ItemUtils.rename(entityHead.PARROT_GRAY, ChatColor.YELLOW + config.getString("PARROT.Name"));
                    nbtItem = new NBTItem(item);
                    nbtItem.setString("HeadDrop", "PARROT_GRAY");

                    break;
                case "PARROT_RED":
                    item = ItemUtils.rename(entityHead.PARROT_RED, ChatColor.YELLOW + config.getString("PARROT.Name"));
                    nbtItem = new NBTItem(item);
                    nbtItem.setString("HeadDrop", "PARROT_RED");

                    break;
                case "PARROT_GREEN":
                    item = ItemUtils.rename(entityHead.PARROT_GREEN, ChatColor.YELLOW + config.getString("PARROT.Name"));
                    nbtItem = new NBTItem(item);
                    nbtItem.setString("HeadDrop", "PARROT_GREEN");

                    break;
                case "SHEEP_WHITE":
                    item = ItemUtils.rename(entityHead.SHEEP_WHITE, ChatColor.YELLOW + config.getString("SHEEP.Name"));
                    nbtItem = new NBTItem(item);
                    nbtItem.setString("HeadDrop", "SHEEP_WHITE");

                    break;
                case "SHEEP_ORANGE":
                    item = ItemUtils.rename(entityHead.SHEEP_ORANGE, ChatColor.YELLOW + config.getString("SHEEP.Name"));
                    nbtItem = new NBTItem(item);
                    nbtItem.setString("HeadDrop", "SHEEP_ORANGE");

                    break;
                case "SHEEP_MAGENTA":
                    item = ItemUtils.rename(entityHead.SHEEP_MAGENTA, ChatColor.YELLOW + config.getString("SHEEP.Name"));
                    nbtItem = new NBTItem(item);
                    nbtItem.setString("HeadDrop", "SHEEP_MAGENTA");

                    break;
                case "SHEEP_LIGHT_BLUE":
                    item = ItemUtils.rename(entityHead.SHEEP_LIGHT_BLUE, ChatColor.YELLOW + config.getString("SHEEP.Name"));
                    nbtItem = new NBTItem(item);
                    nbtItem.setString("HeadDrop", "SHEEP_LIGHT_BLUE");

                    break;
                case "SHEEP_YELLOW":
                    item = ItemUtils.rename(entityHead.SHEEP_YELLOW, ChatColor.YELLOW + config.getString("SHEEP.Name"));
                    nbtItem = new NBTItem(item);
                    nbtItem.setString("HeadDrop", "SHEEP_YELLOW");

                    break;
                case "SHEEP_LIME":
                    item = ItemUtils.rename(entityHead.SHEEP_LIME, ChatColor.YELLOW + config.getString("SHEEP.Name"));
                    nbtItem = new NBTItem(item);
                    nbtItem.setString("HeadDrop", "SHEEP_LIME");

                    break;
                case "SHEEP_PINK":
                    item = ItemUtils.rename(entityHead.SHEEP_PINK, ChatColor.YELLOW + config.getString("SHEEP.Name"));
                    nbtItem = new NBTItem(item);
                    nbtItem.setString("HeadDrop", "SHEEP_PINK");

                    break;
                case "SHEEP_GRAY":
                    item = ItemUtils.rename(entityHead.SHEEP_GRAY, ChatColor.YELLOW + config.getString("SHEEP.Name"));
                    nbtItem = new NBTItem(item);
                    nbtItem.setString("HeadDrop", "SHEEP_GRAY");

                    break;
                case "SHEEP_LIGHT_GRAY":
                    item = ItemUtils.rename(entityHead.SHEEP_LIGHT_GRAY, ChatColor.YELLOW + config.getString("SHEEP.Name"));
                    nbtItem = new NBTItem(item);
                    nbtItem.setString("HeadDrop", "SHEEP_LIGHT_GRAY");

                    break;
                case "SHEEP_CYAN":
                    item = ItemUtils.rename(entityHead.SHEEP_CYAN, ChatColor.YELLOW + config.getString("SHEEP.Name"));
                    nbtItem = new NBTItem(item);
                    nbtItem.setString("HeadDrop", "SHEEP_CYAN");

                    break;
                case "SHEEP_PURPLE":
                    item = ItemUtils.rename(entityHead.SHEEP_PURPLE, ChatColor.YELLOW + config.getString("SHEEP.Name"));
                    nbtItem = new NBTItem(item);
                    nbtItem.setString("HeadDrop", "SHEEP_PURPLE");

                    break;
                case "SHEEP_BLUE":
                    item = ItemUtils.rename(entityHead.SHEEP_BLUE, ChatColor.YELLOW + config.getString("SHEEP.Name"));
                    nbtItem = new NBTItem(item);
                    nbtItem.setString("HeadDrop", "SHEEP_BLUE");

                    break;
                case "SHEEP_BROWN":
                    item = ItemUtils.rename(entityHead.SHEEP_BROWN, ChatColor.YELLOW + config.getString("SHEEP.Name"));
                    nbtItem = new NBTItem(item);
                    nbtItem.setString("HeadDrop", "SHEEP_BROWN");

                    break;
                case "SHEEP_GREEN":
                    item = ItemUtils.rename(entityHead.SHEEP_GREEN, ChatColor.YELLOW + config.getString("SHEEP.Name"));
                    nbtItem = new NBTItem(item);
                    nbtItem.setString("HeadDrop", "SHEEP_GREEN");

                    break;
                case "SHEEP_RED":
                    item = ItemUtils.rename(entityHead.SHEEP_RED, ChatColor.YELLOW + config.getString("SHEEP.Name"));
                    nbtItem = new NBTItem(item);
                    nbtItem.setString("HeadDrop", "SHEEP_RED");

                    break;
                case "SHEEP_BLACK":
                    item = ItemUtils.rename(entityHead.SHEEP_BLACK, ChatColor.YELLOW + config.getString("SHEEP.Name"));
                    nbtItem = new NBTItem(item);
                    nbtItem.setString("HeadDrop", "SHEEP_BLACK");

                    break;
            }
            world.dropItemNaturally(event.getBlock().getLocation(), nbtItem.getItem());
        }
    }
}
