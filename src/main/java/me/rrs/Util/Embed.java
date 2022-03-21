package me.rrs.Util;

import me.rrs.HeadDrop;
import org.bukkit.Bukkit;
import ru.sal4i.sdiscordwebhook.EmbedObject;
import ru.sal4i.sdiscordwebhook.SDiscordWebhook;

import java.io.IOException;


public class Embed {

    protected static void embed(String Title, String Description, String Footer){

        SDiscordWebhook webhook = new SDiscordWebhook(HeadDrop.getConfiguration().getString("Bot.WebHook"));

        webhook.addEmbed(new EmbedObject()
                .setTitle(Title)
                .setDescription(Description)
                .setFooter(Footer, null)
        );
        try {
            webhook.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void msg(String title, String description, String footer) {
        if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")){
            embed(title, description, footer);
        }else embed(title, description, footer);

    }
}
