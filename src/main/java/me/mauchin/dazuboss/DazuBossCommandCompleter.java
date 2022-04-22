package me.mauchin.dazuboss;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DazuBossCommandCompleter implements TabCompleter {
    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        if (command.getName().equals("dazuboss")){
            if (args.length == 1){
                return new ArrayList<String>(){{add("set_vanilla");add("set_magic");add("spawn");add("list");add("remove");add("config");add("location");add("name");add("defeat");add("alivecheck");}};
            }
            else if (args.length == 2 && args[0].equals("spawn")){
                return new ArrayList<>(DazuBoss.getPlugin(DazuBoss.class).getPlacements().keySet());
            }
            else if (args.length == 2 && (args[0].equals("set_vanilla") || args[0].equals("set_magic"))){
                return new ArrayList<String>(){{add("<key>");}};
            }
            else if (args.length == 3 && args[0].equals("set_magic")){
                return new ArrayList<String>(){{add("<mmob>");}};
            }
            else if (args.length == 3 && args[0].equals("set_vanilla")){
                List<String> strings = new ArrayList<>();
                for(EntityType type:EntityType.values()){
                    try{
                        String key = type.key().asString();
                        if(key.contains(args[2])){
                            strings.add(type.key().asString());
                        }
                    } catch (Exception ignored) {
                    }
                }
                return strings;
            }
            else if (args.length == 4 && args[0].equals("set_vanilla")){
                return new ArrayList<String>(){{add("<nbt>");}};
            }
            else if (args.length == 2 && args[0].equals("remove")){
                return new ArrayList<>(DazuBoss.getPlugin(DazuBoss.class).getPlacements().keySet());
            }
            else if (args.length == 2 && args[0].equals("config")){
                return new ArrayList<String>(){{add("delay_base");add("delay_random");}};
            }
            else if (args.length == 3 && args[0].equals("config")){
                return new ArrayList<String>(){{add("<value>");}};
            }
            else if (args.length == 2 && (args[0].equals("name")||args[0].equals("location")||args[0].equals("alivecheck"))){
                return new ArrayList<>(DazuBoss.getPlugin(DazuBoss.class).getPlacements().keySet());
            }
            else if (args.length == 3 && (args[0].equals("name")||args[0].equals("location"))){
                return new ArrayList<String>(){{add("<value>");}};
            }
            else if (args.length == 3 && args[0].equals("alivecheck")){
                return new ArrayList<String>(){{add("true");add("false");}};
            }

        }
        return null;
    }
}
