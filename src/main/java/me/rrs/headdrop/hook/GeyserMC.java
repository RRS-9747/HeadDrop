package me.rrs.headdrop.hook;

import me.rrs.headdrop.database.EntityHead;
import org.geysermc.geyser.api.event.EventRegistrar;
import org.geysermc.geyser.api.event.lifecycle.GeyserDefineCustomSkullsEvent;

public class GeyserMC implements EventRegistrar {

    public static void onDefineCustomSkulls(GeyserDefineCustomSkullsEvent event) {
        for (EntityHead head : EntityHead.values()){
            event.register(head.getHeadHash(), GeyserDefineCustomSkullsEvent.SkullTextureType.PROFILE);
        }
    }
}
