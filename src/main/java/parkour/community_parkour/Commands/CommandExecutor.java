package parkour.community_parkour.Commands;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import parkour.community_parkour.Builder_Mode.BuilderInvItem;
import parkour.community_parkour.Main;

import java.io.IOException;

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
                    int plot_id = -50;
                    for(int i = 0; i < Main.instance.plotAvaliability.length; i++ )
                    {
                        if(!Main.instance.plotAvaliability[i])
                        {
                            plot_id = i;
                            Main.instance.plotAvaliability[i] = true;
                            player.getPersistentDataContainer().set(Main.instance.PlotID, PersistentDataType.INTEGER, plot_id);
                            player.sendMessage("[DEBUG] Your Plot Id is: " + plot_id);
                            try {
                                Main.instance.loadPlot(plot_id, Main.instance.GetPlayerSchematic(player), BukkitAdapter.adapt(Bukkit.getWorld("world")));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            break;
                        }
                    }
                    player.teleport(new Location(Bukkit.getWorld("world"), 0.5, 124, (plot_id * 50) - 23.5f));

                    if(player.getPersistentDataContainer().get(Main.instance.buildingStatus, PersistentDataType.INTEGER) == null){

                        player.getPersistentDataContainer().set(Main.instance.buildingStatus, PersistentDataType.INTEGER, 0);

                    }

                    player.getPersistentDataContainer().set(Main.instance.buildingStatus, PersistentDataType.INTEGER, 1);
                    player.setAllowFlight(true);
                    player.getInventory().setItem(6, BuilderInvItem.supply);
                }
                if(args[0].equalsIgnoreCase("publish"))
                {

                    player.setAllowFlight(false);

                    Main.instance.SavePlot(player.getPersistentDataContainer().get(Main.instance.PlotID, PersistentDataType.INTEGER), player);
                    Main.instance.playTest(player.getPersistentDataContainer().get(Main.instance.PlotID, PersistentDataType.INTEGER), player);

                    Main.instance.PlotCount++;

                    player.getInventory().setItem(6, new ItemStack(Material.AIR));

                }
                if(args[0].equalsIgnoreCase("setPlotId") && player.isOp())
                {
                    player.getPersistentDataContainer().set(Main.instance.PlotID, PersistentDataType.INTEGER, Integer.parseInt(args[1]));
                }
                if (args[0].equalsIgnoreCase("resetPlot") && player.isOp()) {

                    if(args[1] != null){

                        Main.instance.resetPlot(Integer.parseInt(args[1]));

                    }


                }else if(args[0].equalsIgnoreCase("resetPlot") && !player.isOp()){

                    player.sendMessage(ChatColor.RED + "You do not have permission to run this command.");

                }
                if(args[0].equalsIgnoreCase("play")){

                    if(player.getPersistentDataContainer().get(Main.instance.buildingStatus, PersistentDataType.INTEGER) != 1){

                        if(player.getPersistentDataContainer().get(Main.instance.PlayTesting, PersistentDataType.INTEGER) != 1){

                            player.teleport(new Location(Bukkit.getWorld("world"), 115.5,192,50.5)); //change y to the level of the door

                        }else {
                            player.sendMessage(ChatColor.RED + "You are currently play-testing, please finish before trying to play.");
                        }

                    }else {
                        player.sendMessage(ChatColor.RED + "You are currently building, please finish before trying to play.");
                    }


                }
                if(args[0].equalsIgnoreCase("plotCount")){

                    if(player.isOp()){

                        player.sendMessage(ChatColor.GOLD + "Plot Count is: " + Main.instance.PlotCount);

                    }else {
                        player.sendMessage(ChatColor.RED + "Error | Player is either not admin or this has broken");
                    }

                }
                if(args[0].equalsIgnoreCase("pause")){

                    if(player.getPersistentDataContainer().get(Main.instance.buildingStatus, PersistentDataType.INTEGER) == 1){

                        Main.instance.SavePlot(player.getPersistentDataContainer().get(Main.instance.PlotID, PersistentDataType.INTEGER), player);
                        player.getPersistentDataContainer().set(Main.instance.buildingStatus, PersistentDataType.INTEGER, 0);
                        player.teleport(new Location(Bukkit.getWorld("world"), 115.5,192,50.5)); //change y to the level of the door

                    }else{
                        player.sendMessage(ChatColor.RED + "You are not currently building");
                    }

                }
            }
        }
        return false;
    }
}
