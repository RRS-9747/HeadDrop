package me.rrs.TabCompleter;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HeadDrop implements TabCompleter {

    List< String > results = new ArrayList< >();

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String alias, String[] args) {



        if (cmd.getName().equals("headdrop")){
            if (args.length == 1) {
                results.clear();
                results.add("help");
                results.add("reload");

            }
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
