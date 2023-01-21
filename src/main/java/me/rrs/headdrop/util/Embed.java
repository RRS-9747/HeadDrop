package me.rrs.headdrop.util;

import me.rrs.headdrop.HeadDrop;
import ru.sal4i.sdiscordwebhook.EmbedObject;
import ru.sal4i.sdiscordwebhook.SDiscordWebhook;

import java.io.IOException;


public class Embed {

    private void embed(String Title, String Description, String Footer){

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

    public void msg(String title, String description, String footer) {
        embed(title, description, footer);
    }
}
