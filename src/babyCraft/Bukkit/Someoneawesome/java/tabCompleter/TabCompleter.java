package babyCraft.Bukkit.Someoneawesome.java.tabCompleter;

import java.util.*;

import org.bukkit.command.*;
import org.bukkit.entity.*;

import babyCraft.Bukkit.Someoneawesome.*;
import babyCraft.Bukkit.Someoneawesome.java.enums.*;

@Deprecated
public class TabCompleter implements org.bukkit.command.TabCompleter {

	private Babycraft plugin = Babycraft.getPlugin(Babycraft.class);
	
	private enum cmdGender {
								gender(new String[] {
										"boy", "man", "male", "girl", "woman", "female"
								});
		
								private String[] args;
							
								private cmdGender(String[] args) {
									this.args = args;
								}
								
								public String[] get() {
									return args;
								}
							}
	
	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		if(sender instanceof Player) {
			Player player = (Player) sender;
			ArrayList<String> list = new ArrayList<>();
			
			if(command.getName().equalsIgnoreCase("babycraft")) {
				//if on the first agrument
				if(args.length == 1) {
					//tests if they have started typing already
					if(!args[0].equalsIgnoreCase("")) {
						
						list = (ArrayList<String>) Commands.getMajorCmdList();
						ArrayList<String> newlist = new ArrayList<>();
						
						for(String value : list) {
							if(value.toLowerCase().startsWith(args[0].toLowerCase())) {
								newlist.add(value);
							}
						}
						
						Collections.sort(newlist); 
						return newlist;
						
					} else {
						list = (ArrayList<String>) Commands.getMajorCmdList();
						Collections.sort(list);
						return list;
					}
				}
				//if on the 2nd argument
				if(args.length == 2) {
					//looks for the following cmds
					if(Commands.isCommand(args[0], Commands.DespawnChild) || Commands.isCommand(args[0], Commands.SpawnChild)) {
						//tests if started typing
						if(!(args[1].equalsIgnoreCase(""))) {
						
							list = (ArrayList<String>) plugin.configIO.getChildren(player);
						
							if(list.contains(ConfigValue.NULL.toString())) {
								return null;
							}
						
							ArrayList<String> newlist = new ArrayList<>();
						
							for(String value : list) {
								//name assiated with uuid in list
								String name = plugin.configIO.getName(UUID.fromString(value));
								if(name.toLowerCase().startsWith(args[1].toLowerCase())) {
									newlist.add(name);
								}
							}
						
							Collections.sort(newlist);
							return newlist;
						} else {
							list = (ArrayList<String>) plugin.configIO.getChildren(player);

							if(list.contains(ConfigValue.NULL.toString())) {
								return null;
							}
						
							ArrayList<String> newList = new ArrayList<>();
							
							for(String value : list) {
								//name assiated with uuid in list
								String name = plugin.configIO.getName(UUID.fromString(value));
								newList.add(name);
							}
							
							Collections.sort(newList);
							return newList;
						}
					} 
					
				
					if(Commands.isCommand(args[0], Commands.SetGender)) {
						if(!args[1].equalsIgnoreCase("")) {
							
							list = new ArrayList<>();
							
							String[] temp = cmdGender.gender.get();
							for(int x = 0; x < temp.length; x++)
								list.add(temp[x]);
							
							ArrayList<String> newlist = new ArrayList<>();
							for(String value : list) {
								if(value.toLowerCase().startsWith(args[1].toLowerCase())) {
									newlist.add(value);
								}
							}
							
							Collections.sort(newlist);
							return newlist;	
						} else {
							
							String[] temp = cmdGender.gender.get();
							for(int x = 0; x < temp.length; x++)
								list.add(temp[x]);
							
							Collections.sort(list);
							return list;
						}
						
						
					}
					
					if(Commands.isCommand(args[0], Commands.AdminPrefix)) {
						if(!(args[1].equalsIgnoreCase(""))) {
							
							list = (ArrayList<String>) Commands.getAdminCmdList();
						
							if(list.contains(ConfigValue.NULL.toString())) {
								return null;
							}
						
							ArrayList<String> newlist = new ArrayList<>();
						
							for(String value : list) {
								//name assiated with uuid in list
								if(value.toLowerCase().startsWith(args[1].toLowerCase())) {
									newlist.add(value);
								}
							}
						
							Collections.sort(newlist);
							return newlist;
						} else {						
							list = (ArrayList<String>) Commands.getAdminCmdList();
							
							Collections.sort(list);
							return list;
						}
					}
					
				}
			}
		}
		return null;
	}
	
}
