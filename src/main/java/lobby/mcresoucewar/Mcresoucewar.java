package lobby.mcresoucewar;

import fr.skytasul.glowingentities.GlowingBlocks;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import java.util.ArrayList;
import java.util.List;

public final class Mcresoucewar extends JavaPlugin implements Listener {

    public static GlowingBlocks glowingBlocks;
    public static List<OutPost>outPosts = new ArrayList<>();
    public static Plugin plugin;
    public static TeamBase blueBase;
    public static TeamBase redBase;

    @Override
    public void onEnable() {
        this.getCommand("generate_map").setExecutor(new Commands());
        this.getCommand("game_start").setExecutor(new Commands());
        glowingBlocks = new GlowingBlocks(this);
        McResouceWarTeam.initTeam();

        getServer().getPluginManager().registerEvents(new OutPostNexusBreak(),this);
        getServer().getPluginManager().registerEvents(new TeamBaseNexusBreak(),this);
        getServer().getPluginManager().registerEvents(new TeamBaseUI(),this);
        getServer().getPluginManager().registerEvents(new OutPostUi(),this);
        getServer().getPluginManager().registerEvents(new PlayerMoveLimits(),this);

        Mcresoucewar.outPosts = new ArrayList<>();
        plugin = this;

        getServer().getPluginManager().registerEvents(new PlayerItemLimitsManager(),this);

    }




    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
