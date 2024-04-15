package me.rrs.headdrop.util;


import me.rrs.discordutils.DiscordUtils;
import me.rrs.headdrop.HeadDrop;
import net.dv8tion.jda.api.EmbedBuilder;
import org.jetbrains.annotations.NotNull;
import ru.sal4i.sdiscordwebhook.EmbedObject;
import ru.sal4i.sdiscordwebhook.SDiscordWebhook;

import java.io.IOException;


public class Embed {

    private void embed(String Title, String Description, String Footer){

        try {
            EmbedBuilder builder = new EmbedBuilder()
                    .setTitle(Title)
                    .setDescription(Description)
                    .setFooter(Footer, null);
            DiscordUtils.getInstance().getJda().getTextChannelById(HeadDrop.getConfiguration().getString("Bot.Channel-ID")).sendMessageEmbeds(builder.build()).queue();
        }catch (NoClassDefFoundError ignore){
            HeadDrop.getInstance().getLogger().severe("You need to install DiscordUtils for Discord notify to work!");
        }
    }

    public void msg(@NotNull String title, String description, @NotNull String footer) {
        try {
            HeadDrop.getInstance().getServer().getScheduler().runTaskAsynchronously(HeadDrop.getInstance(), () -> embed(title, description, footer));
        }catch (UnsupportedOperationException ignored) {
            embed(title, description, footer);
        }
    }
}
