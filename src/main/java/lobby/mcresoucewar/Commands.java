package lobby.mcresoucewar;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public class Commands implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label,String[]args){
        if(command.getName().equalsIgnoreCase("generate_map")){
            if(!(sender instanceof Player)) return false;
            Player player = (Player) sender;
            Location playerLoc = player.getLocation();
            int playerX = (int)Math.round(playerLoc.getX());
            int playerZ = (int)Math.round(playerLoc.getZ());
            if(args.length == 0){
                for(int i = 0;i < 9; i++){
                    if(i == 0){
                        Scoreboard scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();
                        Team team = scoreboard.getTeam("red");
                        Mcresoucewar.redBase = new TeamBase(McResouceWarTeam.getInstance(team),player.getWorld(),playerX + (i * 17) + 1,playerZ);
                        continue;
                    }
                    if(i == 8){
                        Scoreboard scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();
                        Team team = scoreboard.getTeam("blue");
                        Mcresoucewar.blueBase = new TeamBase(McResouceWarTeam.getInstance(team),player.getWorld(),playerX + (i * 17) + 1,playerZ);
                        continue;
                    }
                    Mcresoucewar.outPosts.add(new OutPost(player.getWorld(),playerX + (i * 17) + 1,playerZ));
                }
                for(OutPost outPost : Mcresoucewar.outPosts){
                    //初期化
                    outPost.resouces.decideTier();
                    outPost.resouces.startGenerateResouce();
                }
                return true;
            }
        }
        if(command.getName().equals("game_start")){
            if(Mcresoucewar.blueBase == null){
                sender.sendMessage("マップが生成されていません先に/generate_mapを使用してください");
                return false;
            }
            new GameStart();
        }
        return false;
    }
}
