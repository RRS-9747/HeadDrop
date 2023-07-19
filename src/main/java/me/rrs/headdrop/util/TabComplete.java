package me.rrs.headdrop.util;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TabComplete implements TabCompleter {

    final List< String > results = new ArrayList< >();

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, Command cmd, @NotNull String alias, @NotNull String[] args) {

        if (cmd.getName().equals("headdrop") && args.length ==1){
                results.clear();
                results.add("help");
                results.add("reload");
                results.add("leaderboard");
                results.add("gui");
        }
        return sortedResults(args[0]);
    }

    public List < String > sortedResults(String arg) {
        final List < String > completions = new ArrayList < > ();
        StringUtil.copyPartialMatches(arg, results, completions);
        Collections.sort(completions);
        results.clear();
        results.addAll(completions);
        return results;
    }
}
