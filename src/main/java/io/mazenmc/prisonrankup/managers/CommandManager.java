package io.mazenmc.prisonrankup.managers;

import io.mazenmc.prisonrankup.enums.Message;
import io.mazenmc.prisonrankup.objects.Command;
import io.mazenmc.prisonrankup.objects.SubCommand;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommandManager extends Manager implements CommandExecutor {

    private static CommandManager instance = new CommandManager();

    private Map<Command, List<SubCommand>> commandData = new HashMap<>();

    private CommandManager() {
        //
    }

    @Override
    public boolean onCommand(CommandSender cs, org.bukkit.command.Command cmd, String label, String[] args) {

        for(Map.Entry<Command, List<SubCommand>> entry : commandData.entrySet()) {
            Command command = entry.getKey();
            List<SubCommand> subCommands = entry.getValue();

            if(command.getName().equalsIgnoreCase(cmd.getName())) {

                // Before we do anything check if the player has the permission to run this command
                if(!(cs.hasPermission(cmd.getPermission()))) {
                    cs.sendMessage(Message.NO_PERMISSION.toString());
                    return true;
                }

                if(args.length > 1) {
                    // First try and find a sub command that matches the command
                    for(SubCommand sc : subCommands) {
                        if(sc.getClass().getSimpleName().equalsIgnoreCase(args[0])) {
                            String[] newArgs = new String[(args.length - 1)];

                            System.arraycopy(args, 1, newArgs, 0, (args.length - 1));

                            // Execute the sub-command
                            if(cs instanceof Player) {
                                sc.onExecute((Player) cs, cmd, label, newArgs);
                            }else{
                                sc.onExecute(cs, cmd, label, newArgs);
                            }
                        }
                    }

                    // Can't find the sub-command so might as well execute the
                    if(cs instanceof Player) {
                        command.onExecute((Player) cs, cmd, label, args);
                    }else{
                        command.onExecute(cs, cmd, label, args);
                    }
                }else{
                    //Execute the command

                    if(cs instanceof Player) {
                        command.onExecute((Player) cs, cmd, label, args);
                    }else{
                        command.onExecute(cs, cmd, label, args);
                    }
                }

                // Break out of the loop, since we've found a command that matches
                break;
            }
        }

        return false;
    }

    public static CommandManager getInstance() {
        return instance;
    }

    public void registerSubCommand(String name, SubCommand subCommand) {
        Command cmd = null;

        for(Command command : commandData.keySet()) {
            if(command.getName().equalsIgnoreCase(name))
                cmd = command;
        }

        if(cmd == null) {
            throw new IllegalArgumentException("Provided command does not exist!");
        }

        commandData.get(cmd).add(subCommand);
    }

    public void registerCommand(Command cmd) {
        commandData.put(cmd, new ArrayList<SubCommand>());
    }

    @Override
    public void cleanup() {
        instance = null;
    }
}
