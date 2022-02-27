package parkour.community_parkour.Builder_Mode;

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
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import parkour.community_parkour.Main;

import java.io.IOException;

public class BuilderListener implements Listener {

    private Main main;
    public BuilderListener(Main main){ this.main = main;}

    @EventHandler
    public void onBreak(BlockDamageEvent e){

        if(!e.getPlayer().isOp()){

            if(e.getBlock().getType() == Material.BEDROCK){

                e.getPlayer().sendMessage(ChatColor.RED + "You cannot break the walls.");
                e.setCancelled(true);
                return;

            }

            if(e.getBlock().getType() == Material.BARRIER){

                e.getPlayer().sendMessage(ChatColor.RED + "You cannot break the walls.");
                e.setCancelled(true);
                return;

            }

            if(e.getBlock().getType() == Material.DEEPSLATE){

                e.getPlayer().sendMessage(ChatColor.RED + "You cannot break the walls.");
                e.setCancelled(true);
                return;

            }

            if(e.getPlayer().getPersistentDataContainer().get(Main.instance.buildingStatus, PersistentDataType.INTEGER) == 0){

                e.setCancelled(true);

            }

            if(e.getPlayer().getPersistentDataContainer().get(Main.instance.buildingStatus, PersistentDataType.INTEGER) == 1){

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
    public void onBlockPlace(BlockPlaceEvent e){

        if(e.getHand() == EquipmentSlot.HAND){

            if(e.getPlayer().getPersistentDataContainer().get(Main.instance.buildingStatus, PersistentDataType.INTEGER) == null){

                return;
            }
            if(e.getPlayer().getPersistentDataContainer().get(Main.instance.buildingStatus, PersistentDataType.INTEGER) == 1){

                e.getPlayer().getInventory().addItem(new ItemStack(e.getBlock().getType()));
                if(e.getBlock().getType() == Material.DISPENSER)
                {
                    Dispenser dispenser = (Dispenser) e.getBlock();
                    dispenser.getPersistentDataContainer().set(Main.instance.lbozo, PersistentDataType.INTEGER,-69);
                }
            }

        }

    }

    @EventHandler
    public void onLeave(PlayerQuitEvent e){

        if(e.getPlayer().getPersistentDataContainer().get(Main.instance.buildingStatus, PersistentDataType.INTEGER) == 1){

            e.getPlayer().getPersistentDataContainer().set(Main.instance.buildingStatus, PersistentDataType.INTEGER, 0);
            e.getPlayer().getPersistentDataContainer().set(Main.instance.PlayTesting, PersistentDataType.INTEGER, 0);
            Main.instance.SavePlot(e.getPlayer().getPersistentDataContainer().get(Main.instance.PlotID, PersistentDataType.INTEGER), e.getPlayer());
            Main.instance.resetPlot(e.getPlayer().getPersistentDataContainer().get(Main.instance.PlotID, PersistentDataType.INTEGER));

        }

    }



    @EventHandler
    public void onStep(PlayerMoveEvent e) throws IOException {

        Player player = e.getPlayer();
        if(player.getPersistentDataContainer().get(Main.instance.PlayTesting, PersistentDataType.INTEGER) == 1) {

            if (e.getFrom().getBlock().getRelative(BlockFace.DOWN).getType() == Material.DISPENSER) {
                Integer playerCheckpoint = player.getPersistentDataContainer().get(Main.instance.builderCheckPoint, PersistentDataType.INTEGER);
                Dispenser dispenser = (Dispenser) e.getFrom().getBlock().getRelative(BlockFace.DOWN);
                Integer checkpointID = dispenser.getPersistentDataContainer().get(Main.instance.lbozo, PersistentDataType.INTEGER);
                if (checkpointID == -69) {
                    dispenser.getPersistentDataContainer().set(Main.instance.lbozo, PersistentDataType.INTEGER, playerCheckpoint + 1);
                    checkpointID = playerCheckpoint + 1;
                }
                player.getPersistentDataContainer().set(Main.instance.builderCheckPoint, PersistentDataType.INTEGER, checkpointID);
            }
            if (player.getLocation().getBlock().getRelative(BlockFace.DOWN).getType() == Material.DEEPSLATE) {
                Main.instance.PastePlot(Main.instance.PlotCount, Main.instance.GetPlayerSchematic(player), BukkitAdapter.adapt(Bukkit.getWorld("world")));
                player.getPersistentDataContainer().set(Main.instance.PlayTesting, PersistentDataType.INTEGER, 0);
                Main.instance.resetPlot(player.getPersistentDataContainer().get(Main.instance.PlotID, PersistentDataType.INTEGER));
                player.teleport(new Location(Bukkit.getWorld("world"), 115.5, 192, 0.5)); //change y to the level of the door
                PersistentDataContainer persistentDataContainer = e.getPlayer().getPersistentDataContainer();
                Integer buildingStatus = persistentDataContainer.get(Main.instance.buildingStatus, PersistentDataType.INTEGER);
            }
        }
    }

    @EventHandler
    public void onOpen(PlayerInteractEvent e){

        if(e.getAction() == Action.RIGHT_CLICK_BLOCK){

            if(e.getClickedBlock().getType() == Material.DISPENSER){

                e.setCancelled(true);

            }

        }

    }

}
