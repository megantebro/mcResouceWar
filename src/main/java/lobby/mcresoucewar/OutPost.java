package lobby.mcresoucewar;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class OutPost {
    List<Block>blocks = new ArrayList<>();
    int x;
    int z;
    McResouceWarTeam OwnedTeam;
    OutPostNexus nexus;
    World world;
    OutPostResouces resouces;
    public OutPost(World world,int X,int Z){
        int sideLength = 17;

        this.world = world;
        x = X;
        z = Z;

        for(int x = X; x<(X + sideLength);x++){
            for(int z = Z; z<(Z + sideLength);z++){
                for(int y = -57;y>-61;y--){
                    Block block = world.getBlockAt(x,y,z);
                    blocks.add(block);
                    block.setType(Material.WHITE_CONCRETE);
                }
            }
        }

        for(int x = X; x <(X + sideLength);x++){
            for(int z = Z ;z < (Z + sideLength);z++){
                Block block = world.getBlockAt(x,-61,z);
                block.setType(Material.BEDROCK);
            }
        }

        for(int x = X;x < (X + sideLength); x++){
            for(int y = -64;y <= 320;y++){
                for(int i = 0;i < 2;i++){
                    if(i == 0){
                        Block block = world.getBlockAt(x,y,Z - 1);
                        block.setType(Material.BARRIER);
                    }
                    if(i == 1){
                        Block block = world.getBlockAt(x,y,Z + sideLength);
                        block.setType(Material.BARRIER);
                    }
                }
            }
        }

        X = (int)Math.floor(X+sideLength/2);
        Z = (int)Math.floor(Z+sideLength/2);
        int y = -56;
        nexus = new OutPostNexus(new Location(world,X,y,Z),this);

        resouces = new OutPostResouces(this);
    }



    public void setOccupationTeam(McResouceWarTeam team){
        OwnedTeam = team;

        if(OwnedTeam.team.getColor() == ChatColor.RED){
            for (Block block : blocks) {
                block.setType(Material.RED_CONCRETE);
            }
        }else if(OwnedTeam.team.getColor() == ChatColor.BLUE){
            for (Block block : blocks) {
                block.setType(Material.BLUE_CONCRETE);
            }
        }


    }

    public static boolean isPathSecured(OutPost targetOutpost, Player player) {
        // プレイヤーのチーム取得
        Scoreboard scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();
        Team team = scoreboard.getEntryTeam(player.getName());
        McResouceWarTeam playerTeam = McResouceWarTeam.getInstance(team);
        List<OutPost>outPosts = Mcresoucewar.outPosts;
        if(team.getColor() == ChatColor.RED){
            int currentOutPostIndex = outPosts.indexOf(targetOutpost);
            for(int i = 0;i<currentOutPostIndex;i++){
                if(outPosts.get(i).OwnedTeam != playerTeam)return false;
            }
        }
        if(team.getColor() == ChatColor.BLUE){
            int currentOutPostIndex = outPosts.indexOf(targetOutpost);
            for(int i = outPosts.size() - 1;i > currentOutPostIndex;i--){
                if(outPosts.get(i).OwnedTeam != playerTeam)return false;
            }
        }
        return true;
    }



}

