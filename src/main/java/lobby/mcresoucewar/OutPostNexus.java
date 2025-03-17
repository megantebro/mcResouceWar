package lobby.mcresoucewar;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class OutPostNexus {
    private int hitPoint = 10;
    Block nexusBlock;
    OutPost outPost;

    public OutPostNexus(Location loc,OutPost outPost) {
        nexusBlock = loc.getBlock();
        nexusBlock.setType(Material.END_STONE);
        this.outPost = outPost;

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

    public void healHitPoint(){
        if(hitPoint < 10){
            hitPoint++;
        }
    }
    public void damegeHitPoint(McResouceWarTeam enemyTeam){
        if(hitPoint > 1) {
            hitPoint--;
        }else{
            outPost.setOccupationTeam(enemyTeam);
            hitPoint = 10;
        }
    }

}
