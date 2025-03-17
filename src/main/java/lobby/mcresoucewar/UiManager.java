package lobby.mcresoucewar;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class UiManager {
    @EventHandler
    public void onPlyaerInteract(PlayerInteractEvent event){

    }

    OutPost outPost;
    public void openResourceMenu(Player player,OutPost targetOutPost) {
        outPost = targetOutPost;

        //インベントリを作成
        Inventory inventory = Bukkit.createInventory(null, 27, ChatColor.GREEN + "資源管理メニュー");

        //資源の個数を確認できるようにする
        McResouceWarTeam team = McResouceWarTeam.getInstance(Bukkit.getScoreboardManager().getMainScoreboard().getPlayerTeam(player));
        ItemStack viewWheat = new ItemStack(Material.WHEAT);
        viewWheat.setAmount(team.wheat);

        ItemStack viewWood = new ItemStack(Material.BIRCH_WOOD);
        viewWood.setAmount(team.wood);

        ItemStack viewIron = new ItemStack(Material.IRON_INGOT);
        viewIron.setAmount(team.iron);

        ItemStack viewGold = new ItemStack(Material.GOLD_INGOT);
        viewGold.setAmount(team.gold);

        ItemStack viewDiamond = new ItemStack(Material.DIAMOND);
        viewDiamond.setAmount(team.diamond);

        ItemStack viewAllow = new ItemStack(Material.ARROW);
        viewAllow.setAmount(team.arrow);

        ItemStack viewBread = new ItemStack(Material.BREAD);
        viewBread.setAmount(team.bread);

        ItemStack viewSteak = new ItemStack(Material.COOKED_BEEF);
        viewSteak.setAmount(team.steak);




        // 資源を取り出すボタン
        ItemStack withdrawButton = new ItemStack(Material.CHEST);
        ItemMeta withdrawMeta = withdrawButton.getItemMeta();
        withdrawMeta.setDisplayName(ChatColor.RED + "資源を取り出す/入れる");
        withdrawButton.setItemMeta(withdrawMeta);

        // アイテムをクラフトするボタン
        ItemStack craftButton = new ItemStack(Material.CRAFTING_TABLE);
        ItemMeta craftMeta = craftButton.getItemMeta();
        craftMeta.setDisplayName(ChatColor.BLUE + "アイテムをクラフトする");
        craftButton.setItemMeta(craftMeta);

        // GUIにアイテムを配置
        inventory.setItem(1,viewWheat);
        inventory.setItem(2,viewWood);
        inventory.setItem(3,viewIron);
        inventory.setItem(4,viewGold);
        inventory.setItem(5,viewDiamond);
        inventory.setItem(6,viewAllow);
        inventory.setItem(7,viewBread);
        inventory.setItem(8,viewSteak);


        inventory.setItem(4 + 9, withdrawButton);   // 資源を取り出すボタン
        inventory.setItem(6 + 9, craftButton);      // アイテムをクラフトするボタン

        // プレイヤーにインベントリを開かせる
        player.openInventory(inventory);
    }


    public void resouceManageMenu(Player player){

        Inventory inventory = Bukkit.createInventory(null, 36, ChatColor.GREEN + "資源を取り出す/預ける");

        McResouceWarTeam team = McResouceWarTeam.getInstance(Bukkit.getScoreboardManager().getMainScoreboard().getPlayerTeam(player));
        setResouceItem(inventory,Material.WHEAT,team.wheat,1);
        setResouceItem(inventory,Material.BIRCH_WOOD,team.wood,2);
        setResouceItem(inventory,Material.IRON_INGOT,team.iron,3);
        setResouceItem(inventory,Material.GOLD_INGOT,team.gold,4);
        setResouceItem(inventory,Material.DIAMOND,team.diamond,5);
        setResouceItem(inventory,Material.ARROW,team.arrow,6);
        setResouceItem(inventory,Material.BREAD,team.bread,7);
        setResouceItem(inventory,Material.COOKED_BEEF,team.steak,8);

        player.openInventory(inventory);
    }


    public void setResouceItem(Inventory inventory,Material material,int amount,int Column){

        ItemStack leaveOne = new ItemStack(material);
        ItemMeta addOneMeta = leaveOne.getItemMeta();
        addOneMeta.setDisplayName(ChatColor.GREEN + "一つ預ける");
        if(amount == 0){
            leaveOne.setAmount(1);
        }else{
            leaveOne.setAmount(amount);
        }
        leaveOne.setItemMeta(addOneMeta);

        ItemStack removeOne = new ItemStack(material);
        ItemMeta removeOneMeta = removeOne.getItemMeta();
        removeOneMeta.setDisplayName(ChatColor.RED + "一つ取り出す");
        removeOne.setAmount(amount);
        removeOne.setItemMeta(removeOneMeta);

        ItemStack leaveTen = new ItemStack(material);
        ItemMeta leaveTenMeta = leaveTen.getItemMeta();
        leaveTenMeta.setDisplayName(ChatColor.GREEN + "十個預ける");
        if(amount == 0){
            leaveTen.setAmount(1);
        }else{
            leaveTen.setAmount(amount);
        }
        leaveTen.setItemMeta(leaveTenMeta);

        ItemStack removeTen = new ItemStack(material);
        ItemMeta removeTenMeta = removeTen.getItemMeta();
        removeTenMeta.setDisplayName(ChatColor.RED + "十個取り出す");
        removeTen.setAmount(amount);
        removeTen.setItemMeta(removeTenMeta);


        inventory.setItem(Column,leaveTen);
        inventory.setItem(Column+9,leaveOne);
        inventory.setItem(Column+18,removeOne);
        inventory.setItem(Column+27,removeTen);
    }
    /**
     * インベントリクリックイベントの処理
     */
    //なぜか二回実行されることへの対策
    public static int count = 0;

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        //一回しか実行されないようにする
        if(count % 2 == 0){count++;return;}
        count++;

        if (event.getView().getTitle().equals(ChatColor.GREEN + "資源管理メニュー")) {
            event.setCancelled(true); // デフォルトのクリック動作をキャンセル

            Player player = (Player) event.getWhoClicked();
            ItemStack clickedItem = event.getCurrentItem();

            if (clickedItem == null || !clickedItem.hasItemMeta()) return;

            String itemName = clickedItem.getItemMeta().getDisplayName();

            if (itemName.equals(ChatColor.BLUE + "アイテムをクラフトする")) {
                player.openWorkbench(null, true);
            }
            if(itemName.equals(ChatColor.RED + "資源を取り出す/入れる")){
                if(outPost == null){
                    resouceManageMenu(player);
                }else if(!OutPost.isPathSecured(outPost,player)){
                    //なぜかクリックしたときイベントが二回発生して一回目のイベントではoutPostがnullになるためインベントリを閉じる
                    player.closeInventory();

                    player.sendMessage("この基地は本拠地から接続されていません");
                }
            }
        }
        if (event.getView().getTitle().equals(ChatColor.GREEN + "資源を取り出す/預ける")){
            event.setCancelled(true); // デフォルトのクリック動作をキャンセル

            Player player = (Player) event.getWhoClicked();
            ItemStack clickedItem = event.getCurrentItem();
            if (clickedItem == null || !clickedItem.hasItemMeta()) return;
            String itemName = clickedItem.getItemMeta().getDisplayName();
            McResouceWarTeam team =  McResouceWarTeam.getInstance(Bukkit.getScoreboardManager().getMainScoreboard().getPlayerTeam(player));

            if(itemName.equals(ChatColor.RED + "一つ取り出す")){
                removeItem(1,clickedItem.getType(),team,player);
            }
            if(itemName.equals(ChatColor.RED + "十個取り出す")){
                removeItem(10,clickedItem.getType(),team,player);
            }
            if(itemName.equals(ChatColor.GREEN + "一つ預ける")){
                leaveItem(1,clickedItem.getType(),team,player);
            }
            if(itemName.equals(ChatColor.GREEN + "十個預ける")){
                leaveItem(10,clickedItem.getType(),team,player);
            }
        }
    }


    private void leaveItem(int amount,Material material,McResouceWarTeam team,Player player){
        ItemStack item = null;
        for(ItemStack itemStack : player.getInventory().getContents()){
            if(itemStack == null)continue;
            if(itemStack.getType() == material)item = itemStack;
        }
        if(item == null)return;
        if(item.getAmount() == 0)return;

        for(int i = 0;i < amount;i++) {
            if (material == Material.WHEAT) {
                item.setAmount(item.getAmount() - 1);
                team.wheat += 1;
            }
            if (material == Material.BIRCH_WOOD) {
                item.setAmount(item.getAmount() - 1);
                team.wood += 1;
            }
            if (material == Material.IRON_INGOT) {
                item.setAmount(item.getAmount() - 1);
                team.iron += 1;
            }
            if (material == Material.GOLD_INGOT) {
                item.setAmount(item.getAmount() - 1 );
                team.gold += 1;
            }
            if (material == Material.DIAMOND) {
                item.setAmount(item.getAmount() - 1);
                team.diamond += 1;
            }
            if (material == Material.ARROW) {
                item.setAmount(item.getAmount() - 1 );
                team.arrow += 1;
            }
            if (material == Material.BREAD) {
                item.setAmount(item.getAmount() - 1);
                team.bread += 1;
            }
            if (material == Material.COOKED_BEEF) {
                item.setAmount(item.getAmount() - 1);
                team.steak += 1;
            }
        }
    }
    private void removeItem(int amount,Material material,McResouceWarTeam team,Player player){
        ItemStack item = null;
        for(ItemStack itemStack : player.getInventory().getContents()){
            if(itemStack == null)continue;
            if(itemStack.getType() == material)item = itemStack;
        }
        if(item == null)return;
        if(item.getAmount() == 0)return;
        for(int i = 0;i < amount;i++) {
            if (material == Material.WHEAT) {
                item.setAmount(item.getAmount() + 1);
                team.wheat -= 1;
            }
            if (material == Material.BIRCH_WOOD) {
                item.setAmount(item.getAmount() + 1);
                team.wood -= 1;
            }
            if (material == Material.IRON_INGOT) {
                item.setAmount(item.getAmount() + 1);
                team.iron -= 1;
            }
            if (material == Material.GOLD_INGOT) {
                item.setAmount(item.getAmount() + 1);
                team.gold -= 1;
            }
            if (material == Material.DIAMOND) {
                item.setAmount(item.getAmount() + 1);
                team.diamond -= 1;
            }
            if (material == Material.ARROW) {
                item.setAmount(item.getAmount() + 1);
                team.arrow -= 1;
            }
            if (material == Material.BREAD) {
                item.setAmount(item.getAmount() + 1);
                team.bread -= 1;
            }
            if (material == Material.COOKED_BEEF) {
                item.setAmount(item.getAmount() + 1);
                team.steak -= 1;
            }
        }
    }
}
