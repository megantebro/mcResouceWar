package lobby.mcresoucewar;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.scoreboard.Team;

import java.util.List;

public class TeamBaseNexusBreak implements Listener {

    @EventHandler
    public void nexusBreak(BlockBreakEvent event) {
        Block breakBlock = event.getBlock();
        Player player = event.getPlayer();
        Team team = player.getScoreboard().getEntryTeam(player.getName());
        List<OutPost> outPosts = Mcresoucewar.outPosts;

        McResouceWarTeam mcResouceWarTeam = McResouceWarTeam.getInstance(team);

        if (outPosts.isEmpty()) return;

        if(team == null) return ;


    }
}
