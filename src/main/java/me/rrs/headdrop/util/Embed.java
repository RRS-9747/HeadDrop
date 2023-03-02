package me.rrs.headdrop.util;

import me.rrs.centralbot.spigot.CentralBotAPI;
import me.rrs.headdrop.HeadDrop;
import net.dv8tion.jda.api.EmbedBuilder;


public class Embed {

    private void embed(String Title, String Description, String Footer){
        CentralBotAPI botAPI = new CentralBotAPI();
        botAPI.getJda().getTextChannelById(HeadDrop.getConfiguration().getString("Bot.Channel")).sendMessageEmbeds(new EmbedBuilder()
                .setTitle(Title)
                .setDescription(Description)
                .setFooter(Footer, null)
                .build()).queue();
    }

    public void msg(String title, String description, String footer) {
        embed(title, description, footer);
    }
}
