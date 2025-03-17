package lobby.mcresoucewar;

import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scoreboard.Team;

public class OutPostUi extends UiManager implements Listener {
    @EventHandler
    public void onPlyaerInteract(PlayerInteractEvent event){
        if(event.getAction().isLeftClick()){return;}

        Player player = event.getPlayer();
        Team playerTeam = Bukkit.getScoreboardManager().getMainScoreboard().getPlayerTeam(player);
        Block clickedBlock = event.getClickedBlock();
        if(clickedBlock == null) return;
        for(OutPost outPost : Mcresoucewar.outPosts){
            if(outPost.nexus.nexusBlock.getLocation().equals(clickedBlock.getLocation())){
                if(outPost.OwnedTeam == null)continue;
                if(outPost.OwnedTeam == McResouceWarTeam.getInstance(playerTeam)){
                    openResourceMenu(player,outPost);
                }
            }
        }

    }
}
