package parkour.community_parkour.Builder_Mode;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
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
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
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
                return;

            }

            if(e.getBlock().getType() == Material.BARRIER){

                e.getPlayer().sendMessage(ChatColor.RED + "You cannot break the walls.");
                e.setCancelled(true);
                return;

            }

            if(e.getPlayer().getPersistentDataContainer().get(Main.instance.Building, PersistentDataType.INTEGER) == 0){

                e.setCancelled(true);

            }

            if(e.getPlayer().getPersistentDataContainer().get(Main.instance.Building, PersistentDataType.INTEGER) == 1){

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

            if(e.getPlayer().getPersistentDataContainer().get(Main.instance.Building, PersistentDataType.INTEGER) == null){

                return;

            }

            if(e.getPlayer().getPersistentDataContainer().get(Main.instance.Building, PersistentDataType.INTEGER) == 1){

                e.getPlayer().getInventory().addItem(new ItemStack(e.getBlock().getType()));

            }

        }

    }

    @EventHandler
    public void onLeave(PlayerQuitEvent e){

        if(e.getPlayer().getPersistentDataContainer().get(Main.instance.Building, PersistentDataType.INTEGER) == 1){

            e.getPlayer().getPersistentDataContainer().set(Main.instance.Building, PersistentDataType.INTEGER, 0);
            e.getPlayer().getPersistentDataContainer().set(Main.instance.PlayTesting, PersistentDataType.INTEGER, 0);

        }

    }

    @EventHandler
    public void onJin(PlayerJoinEvent e){

        e.getPlayer().teleport(new Location(Bukkit.getWorld("world"), 0, 100 , 0));
        e.getPlayer().getPersistentDataContainer().set(main.boost_cooldown, PersistentDataType.INTEGER, 0);
        e.getPlayer().getPersistentDataContainer().set(Main.instance.PlayTesting, PersistentDataType.INTEGER, 0);

        e.getPlayer().sendMessage(ChatColor.GOLD + "Welcome to Parkour! On this server, the community builds the parkour courses. If you want to create parkour courses, do /parkour edit. If you want to play parkour courses, so /parkour play. If you start building, and you are done, do /parkour publish.");

    }

    @EventHandler
    public void onStep(PlayerMoveEvent e){

        Player player = e.getPlayer();

        if(player.getPersistentDataContainer().get(Main.instance.PlayTesting, PersistentDataType.INTEGER) == 1){

            if(player.getLocation().getBlock().getRelative(BlockFace.DOWN).getType() == Material.DEEPSLATE){

                Main.instance.PastePlot(Main.instance.PlotCount, Main.instance.GetPlayerSchematic(player), BukkitAdapter.adapt(Bukkit.getWorld("world")));
                player.getPersistentDataContainer().set(Main.instance.PlayTesting, PersistentDataType.INTEGER, 0);

            }
        }

    }

}
