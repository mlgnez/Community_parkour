package parkour.community_parkour.Commands;

import com.sk89q.worldedit.LocalSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.session.SessionManager;
import com.sk89q.worldedit.world.World;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.persistence.PersistentDataType;
import parkour.community_parkour.Builder_Mode.BuilderInvItem;
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
                    for(int i = 0; i < Main.instance.plotAvaliability.length; i++ )
                    {
                        if(!Main.instance.plotAvaliability[i])
                        {
                            plot_id = i;
                            player.getPersistentDataContainer().set(Main.instance.PlotID, PersistentDataType.INTEGER, plot_id);
                            player.sendMessage("[DEBUG] Your Plot Id is: " + plot_id);
                            break;
                        }
                    }
                    player.teleport(new Location(Bukkit.getWorld("world"), 0.5, 84, (plot_id * 25) - 24.5f));

                    if(player.getPersistentDataContainer().get(Main.instance.Building, PersistentDataType.INTEGER) != null){


                        PermissionAttachment attachment = player.addAttachment(Main.instance);
                        attachment.setPermission("builder", true);
                        player.setAllowFlight(true);
                        player.getInventory().setItem(6, BuilderInvItem.supply);

                    }else{
                        player.getPersistentDataContainer().set(Main.instance.Building, PersistentDataType.INTEGER, 0);
                    }
                }
                if(args[0].equalsIgnoreCase("publish"))
                {

                    PermissionAttachment attachment = player.addAttachment(Main.instance);
                    attachment.setPermission("builder", false);
                    player.setAllowFlight(false);



                    Main.instance.SavePlot(player.getPersistentDataContainer().get(Main.instance.PlotID, PersistentDataType.INTEGER), player);
                    Main.instance.PastePlot(Main.instance.PlotCount, Main.instance.GetPlayerSchematic(player), BukkitAdapter.adapt(Bukkit.getWorld("world")));
                }
                if(args[0].equalsIgnoreCase("setPlotId") && player.isOp())
                {
                    player.getPersistentDataContainer().set(Main.instance.PlotID, PersistentDataType.INTEGER, Integer.parseInt(args[1]));
                }
            }
        }
        return false;
    }
}
