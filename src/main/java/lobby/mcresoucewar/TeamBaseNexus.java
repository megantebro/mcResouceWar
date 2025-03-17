package lobby.mcresoucewar;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class TeamBaseNexus {
    private int hitPoint = 200;
    Block nexusBlock;
    TeamBase teamBase;

    public TeamBaseNexus(Location loc, TeamBase teamBase) {
        nexusBlock = loc.getBlock();
        nexusBlock.setType(Material.END_STONE);
        this.teamBase = teamBase;

        for(Player player : Bukkit.getOnlinePlayers()){
            try {
                Mcresoucewar.glowingBlocks.setGlowing(nexusBlock,player, ChatColor.WHITE);
            } catch (ReflectiveOperationException e) {
                throw new RuntimeException(e);
            }
        }


        new BukkitRunnable() {
            @Override
            public void run() {
                nexusBlock.getLocation().getBlock().setType(Material.END_STONE);
            }
        }.runTaskTimer(Mcresoucewar.plugin,0L,1L);
    }
}
