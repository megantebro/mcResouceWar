package lobby.mcresoucewar;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scoreboard.Team;

public class TeamBaseUI extends UiManager implements Listener {
    @EventHandler
    public void onPlyaerInteract(PlayerInteractEvent event){
        Player player = event.getPlayer();
        Team playerTeam = Bukkit.getScoreboardManager().getMainScoreboard().getPlayerTeam(player);
        Block clickedBlock = event.getClickedBlock();
        if(clickedBlock == null) return;
        if(Mcresoucewar.redBase == null) return;
        if(Mcresoucewar.blueBase == null) return;

        if(playerTeam.getColor() == ChatColor.RED){
            if(clickedBlock.getLocation().equals(Mcresoucewar.redBase.nexus.nexusBlock.getLocation())){
                openResourceMenu(player,null);
            }
        }else if(playerTeam.getColor() == ChatColor.BLUE){
            if(clickedBlock.getLocation().equals(Mcresoucewar.blueBase.nexus.nexusBlock.getLocation())){
                openResourceMenu(player,null);
            }
        }

    }
}
