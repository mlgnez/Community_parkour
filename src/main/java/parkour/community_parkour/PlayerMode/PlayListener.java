package parkour.community_parkour.PlayerMode;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Dispenser;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import parkour.community_parkour.Main;

public class PlayListener implements Listener {
    private Main main;
    public PlayListener(Main main){ this.main = main;}

    @EventHandler
    public void onJoin(PlayerJoinEvent e){

        e.getPlayer().teleport(new Location(Bukkit.getWorld("world"), 0, 100 , 0));
        e.getPlayer().getPersistentDataContainer().set(main.boost_cooldown, PersistentDataType.INTEGER, 0);
        e.getPlayer().getPersistentDataContainer().set(Main.instance.PlayTesting, PersistentDataType.INTEGER, 0);
        e.getPlayer().getPersistentDataContainer().set(Main.instance.buildingStatus, PersistentDataType.INTEGER, 0);
        e.getPlayer().getPersistentDataContainer().set(Main.instance.builderCheckPoint, PersistentDataType.INTEGER, 0);
        e.getPlayer().getPersistentDataContainer().set(Main.instance.checkpoint, PersistentDataType.INTEGER, 0);
        e.getPlayer().getPersistentDataContainer().set(Main.instance.level, PersistentDataType.INTEGER, 0);

        e.getPlayer().sendMessage(ChatColor.GOLD + "Welcome to Parkour! On this server, the community builds the parkour courses. If you want to create parkour courses, do /parkour edit. If you want to play parkour courses, so /parkour play. If you start building, and you are done, do /parkour publish.");

    }

    @EventHandler
    public void PlayerMove(PlayerMoveEvent e)
    {
        Player player = e.getPlayer();
        if(player.getPersistentDataContainer().get(Main.instance.PlayTesting, PersistentDataType.INTEGER) != 1 && player.getPersistentDataContainer().get(Main.instance.buildingStatus, PersistentDataType.INTEGER) == 2) {

            if (e.getFrom().getBlock().getRelative(BlockFace.DOWN).getType() == Material.DISPENSER) {
                Integer playerCheckpoint = player.getPersistentDataContainer().get(Main.instance.checkpoint, PersistentDataType.INTEGER);
                Dispenser dispenser = (Dispenser) e.getFrom().getBlock().getRelative(BlockFace.DOWN);
                Integer checkpointID = dispenser.getPersistentDataContainer().get(Main.instance.lbozo, PersistentDataType.INTEGER);
                if (checkpointID == -69) {
                    System.out.println(" aw shit");
                }
                player.getPersistentDataContainer().set(Main.instance.checkpoint, PersistentDataType.INTEGER, checkpointID);
            }
            if (player.getLocation().getBlock().getRelative(BlockFace.DOWN).getType() == Material.DEEPSLATE) {
                Integer playerCheckpoint = player.getPersistentDataContainer().get(Main.instance.checkpoint, PersistentDataType.INTEGER);
                player.getPersistentDataContainer().set(Main.instance.checkpoint, PersistentDataType.INTEGER, playerCheckpoint + 1);
            }
        }
    }
}
