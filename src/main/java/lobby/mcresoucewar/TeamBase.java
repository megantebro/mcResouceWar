package lobby.mcresoucewar;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;

import java.util.ArrayList;
import java.util.List;

public class TeamBase {
    List<Block>blocks = new ArrayList<>();
    TeamBaseNexus nexus;
    McResouceWarTeam team;
    int x;
    int z;


    public TeamBase(McResouceWarTeam team, World world, int X, int Z){
        int sideLength = 17;
        this.x = X;
        this.z = Z;
        this.team = team;

        for(int x = X; x< (X + sideLength);x++){
            for(int z = Z; z< (Z + sideLength);z++){
                for(int y = -57;y>-61;y--){
                    Block block = world.getBlockAt(x,y,z);
                    blocks.add(block);
                    if(team.team.getColor() == ChatColor.RED){
                        block.setType(Material.RED_CONCRETE);
                    }else if(team.team.getColor() == ChatColor.BLUE){
                        block.setType(Material.BLUE_CONCRETE);
                    }
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

        if(Mcresoucewar.redBase == null){
            for(int z = Z;z < (Z + sideLength);z++){
                for(int y = -64;y <= 320; y++){
                    Block block = world.getBlockAt(X - 1,y,z);
                    block.setType(Material.BARRIER);
                }
            }
        }

        if(!(Mcresoucewar.redBase == null)){
            for(int z = Z;z < (Z + sideLength);z++){
                for(int y = -64;y <= 320; y++){
                    Block block = world.getBlockAt(X + sideLength,y,z);
                    block.setType(Material.BARRIER);
                }
            }
        }

        x = (int)Math.floor(X+sideLength/2);
        z = (int)Math.floor(Z+sideLength/2);
        int y = -56;
        nexus = new TeamBaseNexus(new Location(world,x,y,z),this);
    }


}
