package lobby.mcresoucewar;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.Team;
import org.spigotmc.event.player.PlayerSpawnLocationEvent;


public class PlayerMoveLimits implements Listener {

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event){
        if(!GameStart.canPlayerMove){
            event.setCancelled(true);
        }

    }

    @EventHandler
    public void onPlayerSpawn(PlayerRespawnEvent event){
        Player player = event.getPlayer();
        Team team = player.getScoreboard().getPlayerTeam(player);
        if(Mcresoucewar.redBase == null)return;
        if(Mcresoucewar.blueBase == null)return;

        new BukkitRunnable(){
            @Override
            public void run(){
                if(team.getColor() == ChatColor.RED){
                    player.teleport(Mcresoucewar.redBase.nexus.nexusBlock.getLocation().add(0.5,1,0.5));
                }
                if(team.getColor() == ChatColor.BLUE){
                    player.teleport(Mcresoucewar.blueBase.nexus.nexusBlock.getLocation().add(0.5,1,0.5));
                }
            }
        }.runTaskLater(Mcresoucewar.plugin,5L);
    }
}
