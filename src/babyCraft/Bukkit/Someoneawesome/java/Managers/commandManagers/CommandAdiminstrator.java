package babyCraft.Bukkit.Someoneawesome.java.Managers.commandManagers;

import java.util.*;

import org.bukkit.*;
import org.bukkit.command.*;

import babyCraft.Bukkit.Someoneawesome.java.enums.*;

public class CommandAdiminstrator implements TabExecutor {

	@Override
	public List<String> onTabComplete(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
		// TODO Auto-generated method stub

		//find parent command
				for(Commands parent : Commands.getAllParents()) {
					//checks if is selected command
					if(Commands.isCommand(arg1.getName(), parent)) {
						//it is selected command
						//find child command
						for(Commands children : Commands.getChildren(parent)) {
							if(Commands.isCommand(arg3[0], children)) {
								return children.getExecutor().onTabComplete(arg0, removeFirstElement(arg3));
							}
						}
						return null;
					}
				}
		return null;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String alias, String[] arg) {
		
		//find parent command
		for(Commands parent : Commands.getAllParents()) {
			//checks if is selected command
			if(Commands.isCommand(command.getName(), parent)) {
				//it is selected command
				//find child command
				for(Commands children : Commands.getChildren(parent)) {
					if(Commands.isCommand(arg[0], children)) {
						return children.getExecutor().onCommand(sender, removeFirstElement(arg));
					}
				}
				//sender.sendMessage(arg0);
				Commands[] childs = (Commands[]) Commands.getChildren(parent).toArray();
				String[] cmds = new String[childs.length + 3];
				cmds[0] = ChatColor.translateAlternateColorCodes('&', "&b~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
				cmds[1] = ChatColor.translateAlternateColorCodes('&', "&e&lSubcommands");
				for(int x = 0; x < childs.length; x++) {
					cmds[x + 2] = ChatColor.YELLOW + childs[x].toString();
				}
				cmds[cmds.length - 1] = ChatColor.translateAlternateColorCodes('&', "&b~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
				return true;
			}
		}
		return false;
	}
	
	@SuppressWarnings("unchecked")
	private <T> T[] removeFirstElement(T[] array) {
		List<T> list = Arrays.asList(array);
		list.remove(0);
		return (T[]) list.toArray();
	}

}
