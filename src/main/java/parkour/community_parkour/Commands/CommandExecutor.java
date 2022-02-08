package parkour.community_parkour.Commands;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandExecutor implements org.bukkit.command.CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        if(sender instanceof Player)
        {
            Player player = (Player) sender;
            if(cmd.getName().equalsIgnoreCase("editparkour"))
            {
                if(args[0].equalsIgnoreCase("new"))
                {
                    player.teleport(new Location(Bukkit.getWorld("world"), 0,0,0));

                }
            }
        }
        return false;
    }
}
