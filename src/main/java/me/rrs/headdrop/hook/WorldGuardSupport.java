package me.rrs.headdrop.hook;


import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.flags.Flag;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.flags.registry.FlagConflictException;
import com.sk89q.worldguard.protection.flags.registry.FlagRegistry;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import com.sk89q.worldguard.protection.regions.RegionQuery;
import org.bukkit.Bukkit;
import org.bukkit.Location;


public class WorldGuardSupport {
    private static StateFlag HEADDROP_FLAG;


    public static boolean canDrop(Location loc){
        WorldGuard wGuard = WorldGuard.getInstance();
        for (ProtectedRegion k : wGuard.getPlatform().getRegionContainer().get(BukkitAdapter.adapt(loc.getWorld())).getRegions().values()) {
            if (k.contains(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ())) {
                RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
                RegionQuery query = container.createQuery();
                ApplicableRegionSet set = query.getApplicableRegions(BukkitAdapter.adapt(loc));
                return set.testState(null, HEADDROP_FLAG);
            }
        }
        return true;
    }

    public WorldGuardSupport(){
        FlagRegistry registry = WorldGuard.getInstance().getFlagRegistry();
        try {
            StateFlag flag = new StateFlag("HeadDrop", false);
            registry.register(flag);
            HEADDROP_FLAG = flag;
            Bukkit.getLogger().info("[HeadDrop] Hooked into WorldGuard!");
        } catch (FlagConflictException e) {
            Flag<?> existing = registry.get("HeadDrop");
            if (existing instanceof StateFlag) {
                HEADDROP_FLAG = (StateFlag) existing;
            }
        }
    }
}
