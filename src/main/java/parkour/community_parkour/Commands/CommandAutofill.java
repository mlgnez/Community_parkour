package parkour.community_parkour.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;

public class CommandAutofill implements TabCompleter {
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        ArrayList<String> AutoFillData = new ArrayList<>();
        if(command.getName().equalsIgnoreCase("editparkour"))
        {
            if(args.length == 1)
            {
                AutoFillData.add("new");
            }
        }

        return null;
    }
}
