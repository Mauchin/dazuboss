package me.mauchin.dazuboss;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.*;
import java.util.logging.Level;

@SuppressWarnings("deprecation")
public final class DazuBoss extends JavaPlugin {
    public FileConfiguration config;
    public static String prefix = "§x§b§a§0§2§f§b[§x§b§1§0§e§f§bD§x§a§9§1§b§f§ba§x§a§0§2§7§f§cz§x§9§7§3§4§f§cu§x§8§f§4§0§f§cB§x§8§6§4§d§f§co§x§7§d§5§9§f§ds§x§7§5§6§6§f§ds§x§6§c§7§2§f§d]";
    public static String chatColor = "§x§9§B§7§9§F§D";
    
    @Override
    public void onEnable() {
        config = getConfig();
        BukkitScheduler scheduler = getServer().getScheduler();
        config.addDefault("delay_base",72000);
        config.addDefault("delay_random", 36000);
        config.addDefault("boss_placements",new ArrayList<String>());
        config.addDefault("is_alive",false);
        this.getCommand("dazuboss").setExecutor(new DazuBossCommand());
        this.getCommand("dazuboss").setTabCompleter(new DazuBossCommandCompleter());
        saveConfig();
        int delay = getDelay();
        scheduler.runTaskLater(this,this::setNextBossTimer,delay);
        getLogger().log(Level.INFO,"Next boss scheduled in "+((int)delay/1200)+" minutes");
    }
    public Map<String,DazuBossPlacement> getPlacements(){
        DazuBoss plugin = DazuBoss.getPlugin(DazuBoss.class);
        Map<String,DazuBossPlacement> bossPlacements = new HashMap<>();
        for(String cmd:plugin.getConfig().getStringList("boss_placements")){
            String[] cmdArgs = cmd.split("\\\\");
            bossPlacements.put(cmdArgs[0],new DazuBossPlacement(cmdArgs[1],cmdArgs[2],cmdArgs[3], cmdArgs[4].equalsIgnoreCase("true")));
        }
        return bossPlacements;
    }
    public void savePlacements(Map<String,DazuBossPlacement> placements){
        DazuBoss plugin = DazuBoss.getPlugin(DazuBoss.class);
        List<String> stringList = new ArrayList<>();
        for(String key:placements.keySet()){
            DazuBossPlacement placement = placements.get(key);
            stringList.add(key+"\\"+placement.location +"\\"+placement.bossName+"\\"+placement.cmd+"\\"+((Boolean)placement.aliveCheck).toString());
        }
        plugin.getConfig().set("boss_placements",stringList);
        plugin.saveConfig();
    }
    public void setNextBossTimer(){
        Map<String,DazuBossPlacement> bossPlacements = getPlacements();
        if (bossPlacements.size() >= 1) {
            bossPlacements.get((String) bossPlacements.keySet().toArray()[new Random().nextInt(bossPlacements.size())]).run();
        }
        BukkitScheduler scheduler = getServer().getScheduler();
        int delay = getDelay();
        scheduler.runTaskLater(this,this::setNextBossTimer, delay);
        getLogger().log(Level.INFO,"Next boss scheduled in "+((int)delay/1200)+" minutes");
    }
    public int getDelay(){
        return getConfig().getInt("delay_base")+ new Random().nextInt(getConfig().getInt("delay_random")*2+1)- getConfig().getInt("delay_random");
    }


    @Override
    public void onDisable() {
        saveConfig();
    }
}
