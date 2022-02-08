package parkour.community_parkour.Items;

import org.bukkit.Bukkit;
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

        if(e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK){

            Player player = e.getPlayer();

            if(e.getHand() == EquipmentSlot.HAND){

                if(player.getItemInUse() != null){

                    if(player.getItemInHand().getItemMeta().equals(HidePlayers_Item.supply.getItemMeta())){
                        if(player.getPersistentDataContainer().get(main.hidden, PersistentDataType.INTEGER) != null){
                            if(player.getPersistentDataContainer().get(main.hidden,PersistentDataType.INTEGER) == 0){

                                Player[] players = Bukkit.getServer().getOnlinePlayers().toArray(new Player[0]);

                                for(int i = 0; i < players.length; i++){

                                    player.hidePlayer(main, players[i]);

                                }

                                player.getItemInHand().setType(Material.GRAY_DYE);

                                player.getPersistentDataContainer().set(main.hidden,PersistentDataType.INTEGER, 1);

                            }else if(player.getPersistentDataContainer().get(main.hidden, PersistentDataType.INTEGER) == 1){

                                Player[] players = Bukkit.getServer().getOnlinePlayers().toArray(new Player[0]);

                                for(int i = 0; i < players.length; i++){

                                    player.showPlayer(main, players[i]);

                                }

                                player.getItemInHand().setType(Material.LIME_DYE);

                                player.getPersistentDataContainer().set(main.hidden,PersistentDataType.INTEGER, 0);

                            }

                        }else{
                            player.getPersistentDataContainer().set(main.hidden, PersistentDataType.INTEGER, 0);
                        }

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

            player.getInventory().setItem(1, Boost_Item.supply);
            player.getInventory().setItem(2, HidePlayers_Item.supply);

        }

    }

}
