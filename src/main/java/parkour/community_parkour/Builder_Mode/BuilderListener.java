package parkour.community_parkour.Builder_Mode;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import parkour.community_parkour.Main;

public class BuilderListener implements Listener {

    private Main main;
    public BuilderListener(Main main){ this.main = main;}

    @EventHandler
    public void onBreak(BlockBreakEvent e){

        if(!e.getPlayer().isOp()){

            if(e.getBlock().getType() == Material.BEDROCK){

                e.getPlayer().sendMessage(ChatColor.RED + "You cannot break the walls.");
                e.setCancelled(true);

            }

            if(!e.getPlayer().hasPermission("builder")){

                e.setCancelled(true);

            }

        }

    }

    @EventHandler
    public void onUse(PlayerInteractEvent e){

        if(e.getAction() == Action.RIGHT_CLICK_AIR){

            if(e.getHand() == EquipmentSlot.HAND){

                if(e.getPlayer().getItemInHand().getType() == Material.COMPASS){



                }

            }

        }

    }

}
