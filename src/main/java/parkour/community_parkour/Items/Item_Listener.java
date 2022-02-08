package parkour.community_parkour.Items;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.persistence.PersistentDataType;
import parkour.community_parkour.Main;
import org.bukkit.event.Listener;

public class Item_Listener implements Listener {

    private Main main;
    public Item_Listener(Main main){ this.main = main;}

    @EventHandler
    public void onUse (PlayerInteractEvent e){

        if(e.getAction() == Action.RIGHT_CLICK_AIR){
            Player player = e.getPlayer();
            if(e.getHand() == EquipmentSlot.HAND){

                if(player.getInventory().getItemInMainHand().getType() == Material.LIME_DYE){


                    Player[] players = Bukkit.getServer().getOnlinePlayers().toArray(new Player[0]);
                    for (Player value : players) {
                        player.hidePlayer(main, value);
                    }
                    player.getInventory().setItemInMainHand(HiddenPlayers_Item.supply);
                    return;

                }
                if(player.getInventory().getItemInMainHand().getType() == Material.GRAY_DYE){

                    Player[] players = Bukkit.getServer().getOnlinePlayers().toArray(new Player[0]);
                    for (Player value : players) {
                        player.showPlayer(main, value);
                    }
                    player.getInventory().setItemInMainHand(HidePlayers_Item.supply);
                    return;

                }
                if(player.getInventory().getItemInMainHand().getType() == Material.FEATHER){

                    if(player.getPersistentDataContainer().get(main.boost_cooldown, PersistentDataType.INTEGER) != null){

                        if(player.getPersistentDataContainer().get(main.boost_cooldown, PersistentDataType.INTEGER) == 0){
                            player.setVelocity(player.getLocation().getDirection());
                            player.getPersistentDataContainer().set(main.boost_cooldown, PersistentDataType.INTEGER, 1);
                        }
                        if(player.getPersistentDataContainer().get(main.boost_cooldown, PersistentDataType.INTEGER) == 1){

                            player.getPersistentDataContainer().set(main.boost_cooldown, PersistentDataType.INTEGER, -1);

                            Bukkit.getScheduler().runTaskTimer(main, new Runnable(){

                                int time = 60; //or any other number you want to start countdown from
                                boolean hasFinished = false;

                                @Override
                                public void run() {

                                    if (this.time == 0)
                                    {
                                        if(!hasFinished){
                                            Bukkit.broadcastMessage("Boost is ready!");
                                            player.getPersistentDataContainer().set(main.boost_cooldown, PersistentDataType.INTEGER, 0);
                                            hasFinished = true;
                                            return;
                                        }
                                    }

                                    this.time--;
                                }
                            }, 0L, 20L);

                        }

                    }else{
                        player.getPersistentDataContainer().set(main.boost_cooldown, PersistentDataType.INTEGER, 0);
                    }

                }

            }

        }

    }

    //give player boost item and hideplayer item when they join if they dont have it

    @EventHandler
    public void onJoin(PlayerJoinEvent e){

        Player player = e.getPlayer();

        if(!(player.getInventory().contains(HidePlayers_Item.supply) || player.getInventory().contains(Boost_Item.supply))){

            player.getInventory().setItem(7, Boost_Item.supply);
            player.getInventory().setItem(8, HidePlayers_Item.supply);

        }

    }

}