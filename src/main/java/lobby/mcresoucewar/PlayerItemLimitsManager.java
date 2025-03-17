package lobby.mcresoucewar;


import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;


import java.util.HashMap;
import java.util.Map;

public class PlayerItemLimitsManager implements Listener {
    int maxWeaponCost = 6;
    int maxFoodCost = 9;
    int maxArrow = 16;
    int maxBlock = 10;

    Map<Material,Integer> weaponAndToolCosts = new HashMap<Material,Integer>(){{
        put(Material.DIAMOND_SWORD,2);
        put(Material.IRON_SWORD,2);
        put(Material.GOLDEN_SWORD,1);
        put(Material.WOODEN_SWORD,2);

        put(Material.DIAMOND_AXE,4);
        put(Material.IRON_AXE,4);
        put(Material.GOLDEN_AXE,2);
        put(Material.WOODEN_AXE,4);

        put(Material.DIAMOND_PICKAXE,3);
        put(Material.IRON_PICKAXE,2);
        put(Material.GOLDEN_PICKAXE,3);
        put(Material.WOODEN_PICKAXE,3);

        put(Material.DIAMOND_SHOVEL,3);
        put(Material.IRON_SHOVEL,3);
        put(Material.GOLDEN_SHOVEL,2);
        put(Material.WOODEN_SHOVEL,3);

        put(Material.DIAMOND_HOE,1);
        put(Material.IRON_HOE,1);
        put(Material.GOLDEN_HOE,1);
        put(Material.WOODEN_HOE,1);
    }};
    HashMap<Material,Integer>foodCosts = new HashMap<>(){{
        put(Material.BREAD,1);
        put(Material.COOKED_BEEF,3);
    }};

    public PlayerItemLimitsManager(){
        regulerObservePlayerItem();
    }

    @EventHandler
    public void onPlayerItemPickup(PlayerPickupItemEvent event){
        int totalWeaponCost = 0;
        int totalFoodCost = 0;
        int totalArrow = 0;
        int totalBlock = 0;

        Inventory inventory = event.getPlayer().getInventory();
        for(ItemStack item : inventory.getContents()){
            if(item == null)continue;

            int[] result = addTotalCosts(totalWeaponCost,totalFoodCost,totalArrow,totalBlock,item);
            totalWeaponCost = result[0];
            totalFoodCost = result[1];
            totalArrow = result[2];
            totalBlock = result[3];
        }

        int[] result = addTotalCosts(totalWeaponCost,totalFoodCost,totalArrow,totalBlock,new ItemStack(event.getItem().getItemStack().getType()));
        totalWeaponCost = result[0];
        totalFoodCost = result[1];
        totalArrow = result[2];
        totalBlock = result[3];

        if(maxWeaponCost < totalWeaponCost || maxFoodCost < totalFoodCost
                || maxArrow < totalArrow || maxBlock < totalBlock){
            event.setCancelled(true);
        }

    }


    public void regulerObservePlayerItem(){
        new BukkitRunnable(){
            @Override
            public void run(){
                for(Player player : Bukkit.getOnlinePlayers()){
                    observePlayerItem(player);
                }
            }
        }.runTaskTimer(Mcresoucewar.plugin,0L,1L);
    }
    //アイテムを何らかの形で入手しようとすると実行される、アイテムを必要以上に持とうとするとtrueを返す
    public void observePlayerItem(Player player){

        Inventory inventory = player.getInventory();

        int totalWeaponCost = 0;
        int totalFoodCost = 0;
        int totalArrow = 0;
        int totalBlock = 0;


        for(ItemStack item : inventory.getContents()){
            if(item == null)continue;

            int[] result = addTotalCosts(totalWeaponCost,totalFoodCost,totalArrow,totalBlock,item);
            totalWeaponCost = result[0];
            totalFoodCost = result[1];
            totalArrow = result[2];
            totalBlock = result[3];
        }

        if(maxWeaponCost < totalWeaponCost){
            ItemStack[] contents = inventory.getContents();
            for (ItemStack item : contents) {
                if(item == null)continue;
                if (weaponAndToolCosts.containsKey(item.getType())) {
                    totalWeaponCost -= weaponAndToolCosts.get(item.getType());
                    player.getWorld().dropItemNaturally(player.getLocation(),new ItemStack(item.getType()));
                    item.setAmount(item.getAmount() - 1);
                    if(!(maxWeaponCost < totalWeaponCost))break;
                }
            }

        }

        if(maxFoodCost < totalFoodCost){
            ItemStack[] contents = inventory.getContents();
            itemLoop:for (ItemStack item : contents) {
                if(item == null)continue ;
                if (foodCosts.containsKey(item.getType())) {
                    while (maxFoodCost < totalFoodCost) {
                        if(item.getAmount() == 0)continue itemLoop;
                        totalFoodCost -= foodCosts.get(item.getType());
                        player.getWorld().dropItemNaturally(player.getLocation(),new ItemStack(item.getType()));
                        item.setAmount(item.getAmount() - 1);
                    }
                }
            }
        }

        if(maxArrow < totalArrow){
            ItemStack[] contents = inventory.getContents();
            itemLoop:for(ItemStack item : contents){
                if(item == null)continue ;
                if(item.getType() == Material.ARROW){
                    while(maxArrow < totalArrow){
                        if(item.getAmount() == 0)continue itemLoop;
                        totalArrow -= 1;
                        player.getWorld().dropItemNaturally(player.getLocation(),new ItemStack(item.getType()));
                        item.setAmount(item.getAmount() - 1);
                    }
                }
            }
        }

        if(maxBlock < totalBlock){
            ItemStack[] contents = inventory.getContents();
            itemLoop:for(ItemStack item : contents){
                if(item == null)continue;
                if(item.getType().isBlock()){
                    while(maxBlock < totalBlock){
                        if(item.getAmount() == 0)continue itemLoop;
                        totalBlock -= 1;
                        player.getWorld().dropItemNaturally(player.getLocation(),new ItemStack(item.getType()));
                        item.setAmount(item.getAmount() - 1);
                    }
                }
            }
        }
    }

    public int[] addTotalCosts(int totalWeaponCost,int totalFoodCost,int totalArrow,int totalBlock,ItemStack item){
        if(weaponAndToolCosts.containsKey(item.getType())){
            totalWeaponCost += weaponAndToolCosts.get(item.getType());
        }

        if(foodCosts.containsKey(item.getType())){
            totalFoodCost += foodCosts.get(item.getType()) * item.getAmount();
        }

        if(item.getType() == Material.ARROW){
            totalArrow += item.getAmount();
        }

        if(item.getType().isBlock()){
            totalBlock += item.getAmount();
        }

        return new int[]{totalWeaponCost,totalFoodCost,totalArrow,totalBlock};
    }
}
