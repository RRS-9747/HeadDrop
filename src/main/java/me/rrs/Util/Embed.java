package me.rrs.Util;

import me.rrs.HeadDrop;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;
import org.bukkit.Bukkit;

import java.util.Date;

public class Embed {

    protected static void embed(String Title, String Description, String Footer){

        String serverChannel = HeadDrop.getConfiguration().getString("Bot.Channel");

        if (serverChannel == null){
            Bukkit.getLogger().severe("Channel can't be null!");
            return;
        }

            TextChannel channel = HeadDrop.getBot().getTextChannelById(serverChannel);

        if (channel == null){
            Bukkit.getLogger().severe("Please put a valid channel ID!");
            return;
        }

            EmbedBuilder embed = new EmbedBuilder();
            embed.setTitle(Title);
            embed.setDescription(Description);
            embed.setFooter(Footer);
            embed.setTimestamp(new Date().toInstant());
            channel.sendMessageEmbeds(embed.build()).queue();
    }

    public static void msg(String title, String description, String footer) {
        if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")){
            embed(title, description, footer);
        }else embed(title, description, footer);

    }
}
