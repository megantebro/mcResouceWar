package lobby.mcresoucewar;


import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.Team;

public class GameStart{

    static boolean canPlayerMove = true;
    public GameStart(){
        canPlayerMove = false;

        for(Player player : Bukkit.getOnlinePlayers()){
            Team team = player.getScoreboard().getPlayerTeam(player);
            if(team.getColor() == ChatColor.RED){
                Location spawnLoc = Mcresoucewar.redBase.nexus.nexusBlock.getLocation();
                player.setBedSpawnLocation(spawnLoc);
                player.teleport(spawnLoc);
            }
            if(team.getColor() == ChatColor.BLUE){
                Location spawnLoc = Mcresoucewar.blueBase.nexus.nexusBlock.getLocation();
                player.teleport(spawnLoc);
            }
        }


        //開始するまで動けないようにする,
        new BukkitRunnable(){
            @Override
            public void run(){
                canPlayerMove = true;
            }
        }.runTaskLater(Mcresoucewar.plugin,100L);
    }


}
