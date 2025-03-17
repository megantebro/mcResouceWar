package lobby.mcresoucewar;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.scoreboard.Team;

import java.util.ArrayList;
import java.util.List;

public class OutPostNexusBreak implements Listener {
    @EventHandler
    public void nexusBreak(BlockBreakEvent event) {
        Block breakBlock = event.getBlock();
        Player player = event.getPlayer();
        Team team = player.getScoreboard().getEntryTeam(player.getName());
        McResouceWarTeam mcResouceWarTeam = McResouceWarTeam.getInstance(team);
        List<OutPost> outPosts = Mcresoucewar.outPosts;


        if (outPosts.isEmpty()) return;

        if(team == null) return ;

        for (OutPost outPost : outPosts) {
            if (breakBlock.getLocation().equals(outPost.nexus.nexusBlock.getLocation())) {

                if(outPost.OwnedTeam == null){
                    outPost.nexus.damegeHitPoint(mcResouceWarTeam);
                    break;
                }

                if (mcResouceWarTeam == outPost.OwnedTeam) {
                    outPost.nexus.healHitPoint();
                } else {
                    outPost.nexus.damegeHitPoint(mcResouceWarTeam);
                }

                break; // ネクサスが見つかったらループを抜ける
            }
        }
    }

}
