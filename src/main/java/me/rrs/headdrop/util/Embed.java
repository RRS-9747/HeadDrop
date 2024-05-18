package me.rrs.headdrop.util;

import me.rrs.discordutils.DiscordUtils;
import me.rrs.headdrop.HeadDrop;
import net.dv8tion.jda.api.EmbedBuilder;
import org.jetbrains.annotations.NotNull;

public class Embed {
    public void msg(String Title, String Description, String Footer){

        try {
            EmbedBuilder builder = new EmbedBuilder()
                    .setTitle(Title)
                    .setDescription(Description)
                    .setFooter(Footer, null);
            DiscordUtils.getInstance().getJda().getTextChannelById(HeadDrop.getInstance().getConfiguration().getString("Bot.Channel-ID")).sendMessageEmbeds(builder.build()).queue();
        }catch (NoClassDefFoundError ignore){
            HeadDrop.getInstance().getLogger().severe("You need to install DiscordUtils for Discord notify to work!");
        }
    }

}
