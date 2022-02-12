package parkour.community_parkour.Builder_Mode;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryInteractEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import parkour.community_parkour.Main;

public class BuilderListener implements Listener {

    private Main main;
    public BuilderListener(Main main){ this.main = main;}

    @EventHandler
    public void onBreak(BlockDamageEvent e){

        if(!e.getPlayer().isOp()){

            if(e.getBlock().getType() == Material.BEDROCK){

                e.getPlayer().sendMessage(ChatColor.RED + "You cannot break the walls.");
                e.setCancelled(true);

            }

            if(!e.getPlayer().hasPermission("builder")){

                e.setCancelled(true);

            }

            if(e.getPlayer().hasPermission("builder")){

                e.getBlock().setType(Material.AIR);

            }

        }

    }

    @EventHandler
    public void onUse(PlayerInteractEvent e){

        if(e.getAction() == Action.RIGHT_CLICK_AIR){

            if(e.getHand() == EquipmentSlot.HAND){

                if(e.getPlayer().getItemInHand().getType() == Material.COMPASS){

                    BuilderInventory builderInventory = new BuilderInventory();

                    e.getPlayer().openInventory(builderInventory.getInventory());

                }

            }

        }

    }

    @EventHandler
    public void yourMother(InventoryClickEvent e){

        if(e.getInventory().getHolder() instanceof  BuilderInventory){

            if(e.getCurrentItem() == null){return;}

            if(e.getCurrentItem().getType() == Material.BLACK_STAINED_GLASS_PANE){

                e.setCancelled(true);

                return;

            }

            e.getWhoClicked().getInventory().addItem(new ItemStack(e.getCurrentItem().getType(), 64));

            e.setCancelled(true);

        }

    }

    @EventHandler
    public void onPlaceBlockGiveBackBlock(BlockPlaceEvent e){

        if(e.getHand() == EquipmentSlot.HAND){

            if(e.getPlayer().hasPermission("builder")){

                e.getPlayer().getInventory().addItem(new ItemStack(e.getBlock().getType()));

            }

        }

    }

}
