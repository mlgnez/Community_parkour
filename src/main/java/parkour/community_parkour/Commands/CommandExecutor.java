package parkour.community_parkour.Commands;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;
import parkour.community_parkour.Main;

public class CommandExecutor implements org.bukkit.command.CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        if(sender instanceof Player)
        {
            Player player = (Player) sender;
            if(cmd.getName().equalsIgnoreCase("parkour"))
            {
                if(args[0].equalsIgnoreCase("edit"))
                {
                    int plot_id = -25;
                    if(player.getPersistentDataContainer().get(Main.instance.PlotID, PersistentDataType.INTEGER) == null)
                    {
                        for(int i = 0; i < Main.instance.plotAvaliability.length; i++ )
                        {
                            if(!Main.instance.plotAvaliability[i])
                            {
                                plot_id = i;
                                player.getPersistentDataContainer().set(Main.instance.PlotID, PersistentDataType.INTEGER, plot_id);
                                break;
                            }
                        }
                    }
                    player.teleport(new Location(Bukkit.getWorld("world"), 0.5, 84, (plot_id * 25) - 24.5f));
                }
                if(args[0].equalsIgnoreCase("publish"))
                {

                }
            }
        }
        return false;
    }
}
