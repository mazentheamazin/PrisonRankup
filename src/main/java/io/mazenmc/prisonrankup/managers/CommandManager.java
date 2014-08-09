/**
 PrisonRankup, the most feature-packed rankup plugin out there.
 Copyright (C) 2014 Mazen K.

 This program is free software: you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation, either version 3 of the License, or
 (at your option) any later version.

 This program is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package io.mazenmc.prisonrankup.managers;

import io.mazenmc.prisonrankup.PrisonRankupPlugin;
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

                if(args.length >= 1) {
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

                            return true;
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

        List<SubCommand> ls = commandData.get(cmd);

        ls.add(subCommand);

        commandData.remove(cmd);
        commandData.put(cmd, ls);
    }

    public void registerCommand(Command cmd) {
        commandData.put(cmd, new ArrayList<SubCommand>());

        PrisonRankupPlugin.getInstance().getCommand(cmd.getName()).setExecutor(this);
    }

    @Override
    public void cleanup() {
        instance = null;
    }
}
