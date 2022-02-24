package parkour.community_parkour.Commands;

import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.LocalSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.WorldEditException;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.extent.Extent;
import com.sk89q.worldedit.function.operation.ForwardExtentCopy;
import com.sk89q.worldedit.function.operation.Operation;
import com.sk89q.worldedit.function.operation.Operations;
import com.sk89q.worldedit.function.operation.SetLocatedBlocks;
import com.sk89q.worldedit.function.pattern.Pattern;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.regions.CuboidRegion;
import com.sk89q.worldedit.session.SessionManager;
import com.sk89q.worldedit.world.World;
import com.sk89q.worldedit.world.block.BaseBlock;
import com.sk89q.worldedit.world.block.BlockType;
import com.sk89q.worldedit.world.block.BlockTypes;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.persistence.PersistentDataType;
import org.checkerframework.checker.units.qual.C;
import org.graalvm.compiler.core.common.cfg.AbstractBlockBase;
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
                    int plot_id = -50;
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
                    player.teleport(new Location(Bukkit.getWorld("world"), 0.5, 124, (plot_id * 50) - 23.5f));

                    if(player.getPersistentDataContainer().get(Main.instance.Building, PersistentDataType.INTEGER) == null){

                        player.getPersistentDataContainer().set(Main.instance.Building, PersistentDataType.INTEGER, 0);

                    }

                    player.getPersistentDataContainer().set(Main.instance.Building, PersistentDataType.INTEGER, 1);
                    player.setAllowFlight(true);
                    player.getInventory().setItem(6, BuilderInvItem.supply);
                }
                if(args[0].equalsIgnoreCase("publish"))
                {

                    player.setAllowFlight(false);

                    Main.instance.SavePlot(player.getPersistentDataContainer().get(Main.instance.PlotID, PersistentDataType.INTEGER), player);
                    Main.instance.PastePlot(Main.instance.PlotCount, Main.instance.GetPlayerSchematic(player), BukkitAdapter.adapt(Bukkit.getWorld("world")));

                    Main.instance.PlotCount++;

                    player.getInventory().setItem(6, new ItemStack(Material.AIR));

                }
                if(args[0].equalsIgnoreCase("setPlotId") && player.isOp())
                {
                    player.getPersistentDataContainer().set(Main.instance.PlotID, PersistentDataType.INTEGER, Integer.parseInt(args[1]));
                }
                if (args[0].equalsIgnoreCase("resetPlot") && player.isOp()) {

                    int PlotCount = Main.instance.PlotCount;
                    //set the line of plots to air | do this when adding plots to the line is added and
                    player.sendMessage(ChatColor.RED + "This hasn't been implemented yet. If you think this is an error, please contact the developers");
                    //add the corners of the first level and add the corner of the last level using some math to make this work (remember, its cornerx, cornery, PlotCount * 50)

                    int z1 = 0;
                    int z2 = PlotCount * 50;

                    BlockVector3 vector1 = BlockVector3.at(15,63, z1);
                    BlockVector3 vector2 = BlockVector3.at(-15,199,z2);

                    CuboidRegion cuboidRegion = new CuboidRegion(vector1, vector2);


                    try(EditSession editSession = WorldEdit.getInstance().newEditSession(BukkitAdapter.adapt(Bukkit.getWorld("world")))){
                        editSession.setBlocks(cuboidRegion, (Pattern) BlockTypes.AIR);
                    } catch (WorldEditException e) {
                        e.printStackTrace();
                    }

                    Main.instance.PlotCount = 0;

                }else if(args[0].equalsIgnoreCase("resetPlot") && !player.isOp()){

                    player.sendMessage(ChatColor.RED + "You do not have permission to run this command.");

                }
                if(args[0].equalsIgnoreCase("play")){

                    player.teleport(new Location(Bukkit.getWorld("world"), 115.5,192,0.5)); //change y to the level of the door

                }
            }
        }
        return false;
    }
}
