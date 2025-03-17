package lobby.mcresoucewar;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;

import java.util.HashMap;
import java.util.Map;

public class McResouceWarTeam {
    private static final Map<Team, McResouceWarTeam> teamCache = new HashMap<>();

    public Team team;
    public int wheat;
    public int wood;
    public int iron;
    public int gold;
    public int diamond;
    public int arrow;
    public int bread;
    public int steak;

    // プライベートコンストラクタ（外部から直接インスタンス化を防ぐ）
    private McResouceWarTeam(Team team) {
        this.team = team;
        wheat = 0;
        wood  = 0;
        iron = 0;
        gold = 0;
        diamond = 0;
        arrow = 0;
        bread = 0;
        steak = 0;
    }

    // インスタンスを取得する静的メソッド
    public static McResouceWarTeam getInstance(Team team) {
        // キャッシュに存在しない場合、新しいインスタンスを作成してキャッシュに保存
        if (!teamCache.containsKey(team)) {
            teamCache.put(team, new McResouceWarTeam(team));
        }
        // キャッシュから既存のインスタンスを返す
        return teamCache.get(team);
    }

    public static void initTeam(){
        ScoreboardManager manager = Bukkit.getScoreboardManager();
        Scoreboard scoreboard = manager.getMainScoreboard();
        if(scoreboard.getTeam("red") == null){
            Team redTeam = scoreboard.registerNewTeam("red");

            redTeam.setColor(ChatColor.RED);
            redTeam.setAllowFriendlyFire(false);
        }
        if(scoreboard.getTeam("blue") == null){
            Team blueTeam = scoreboard.registerNewTeam("blue");

            blueTeam.setColor(ChatColor.BLUE);
            blueTeam.setAllowFriendlyFire(false);
        }
    }

}