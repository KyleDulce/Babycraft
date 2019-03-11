package babyCraft.Bukkit.Someoneawesome.java.Managers;

import java.util.*;

import org.bukkit.*;
import org.bukkit.command.*;
import org.bukkit.entity.*;

import babyCraft.Bukkit.Someoneawesome.*;
import babyCraft.Bukkit.Someoneawesome.java.GUIMenus.*;
import babyCraft.Bukkit.Someoneawesome.java.Managers.childSpawnManagers.*;
import babyCraft.Bukkit.Someoneawesome.java.enums.*;

@Deprecated
public class CommandManger implements CommandExecutor {

	private Babycraft plugin = Babycraft.getPlugin(Babycraft.class);
	
	public CommandManger() {
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {		
		
		//all /bc commands
		if(command.getName().equalsIgnoreCase("babycraft")) {
			//test if arguments are present
			if(args.length >= 1) {
				
				//admin console cmds
				if(Commands.isCommand(args[0], Commands.AdminPrefix)) {
					
					if(args.length >= 2) {
						
						if(Commands.isCommand(args[1], Commands.DespawnAll)) {
							if(sender instanceof Player) {
								Player player = (Player) sender;
								if(player.hasPermission(BcPermissions.Despawn.get())) {
									Child.despawnAll();
									player.sendMessage(ChatColor.GREEN + "All despwaned");
									return true;
								}
							} else {
								Child.despawnAll();
								sender.sendMessage("All despawned");
								return true;
							}
						}
						
						//bc admin reload
						if(Commands.isCommand(args[1], Commands.Admin_saveConfig)) {
							//tests if sender is a player
							if(sender instanceof Player) {
								Player player = (Player) sender;
								//tests for permission
								if(player.hasPermission(BcPermissions.Save.get())) {
									//saves
									plugin.configIO.saveAll();
									sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&aConfig successfully Saved"));
									return true;
								} else {
									//if no permission
									sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cYou Do Not have permission for this command"));
									plugin.console.sendMessage(ChatColor.RED + player.getName() + " was denied the command /bc SaveConfig");
									return true;
								}
							} else {
								//if console runs
								plugin.configIO.saveAll();
								plugin.console.sendMessage("Saved configs!");
								return true;
							}
						}
						
						if(Commands.isCommand(args[1], Commands.Admin_Reload)) {
							//tests if sender is a player
							if(sender instanceof Player) {
								Player player = (Player) sender;
								//tests for permission
								if(player.hasPermission(BcPermissions.reload.get())) {
									//reloads all
									plugin.reloadPluginConfig();
									sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&aConfig successfully loaded"));
									return true;
								} else {
									//if no permission
									sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cYou Do Not have permission for this command"));
									plugin.console.sendMessage(ChatColor.RED + player.getName() + " was denied the command /bc reload");
									return true;
								}
							} else {
								//if console runs
								plugin.reloadPluginConfig();
								plugin.console.sendMessage("reloaded configs!");
								return true;
							}
						}
						
					}
					
					
					
					sender.sendMessage(new String[] {
							ChatColor.translateAlternateColorCodes('&', "&b~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~"),
							ChatColor.translateAlternateColorCodes('&', "&b/bc Admin Reload"),
							ChatColor.translateAlternateColorCodes('&', "&b/bc Admin DespawnAll"),
							ChatColor.translateAlternateColorCodes('&', "&b/bc Admin SaveConfig"),
							ChatColor.translateAlternateColorCodes('&', "&b~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~")
					});
					return true;
					
					
					
				}
				
				//player only cmds
				if(sender instanceof Player){
					Player player = (Player) sender;
					
					// /bc haveChild
					if(Commands.isCommand(args[0], Commands.HaveChild)) {
						//tests if arguments present
						if(args.length >= 2) {
							//tests if other player is online
							if((Bukkit.getServer().getPlayer(args[1])).isOnline()) {
								//sets other player as a variable
								Player tosend = Bukkit.getServer().getPlayer(args[1]);
								//tests if executor has permission
								if(player.hasPermission(BcPermissions.children.get())) {
									//tests if tosend player has permission
									if(tosend.hasPermission(BcPermissions.children.get())) {
										//tests if players are in rang of 20 blocks
										if(player.getLocation().distance(tosend.getLocation()) <= 10D) {
											//tests if player exists in config
											if(plugin.configIO.contains(player)) {
												//tests if partner exists in config
												if(plugin.configIO.contains(tosend)) {
													//sets genders to a variable
													Gender parent1_gender = plugin.configIO.getGender(player);
													Gender parent2_gender = plugin.configIO.getGender(tosend);
													//tests if parent1's gender had been set
													if(!parent1_gender.equals(Gender.NULL)) {
														//tests if parent2's gender had been set
														if(!parent2_gender.equals(Gender.NULL)) {
															//tests if gender is the same
															if(parent2_gender.equals(parent2_gender)) {
																//tests if parent1 has permission
																if(player.hasPermission(BcPermissions.sameGender.get())) {
																	//tests is parent2 does not have permission
																	if(!tosend.hasPermission(BcPermissions.sameGender.get())) {
																		sender.sendMessage(ChatColor.RED + "You are the same gender as your partner and your partner does not have permission to do this");
																		plugin.console.sendMessage(ChatColor.RED + player.getName() + " was denied the command /bc haveChild because the partner does not have permission");
																		return true;
																	}
																} else {
																	sender.sendMessage(ChatColor.RED + "You are the same gender as your partner and you do not have permission to do this");
																	plugin.console.sendMessage(ChatColor.RED + player.getName() + " was denied the command /bc haveChild");
																	return true;
																}
															} // no else since it can go both ways
															//turn on creator
															Creator.runningCreators.put(player.getUniqueId(), new Creator());
															//send request
															tosend.sendMessage(ChatColor.YELLOW + player.getName() + " wants to have a baby with you");
															tosend.sendMessage(ChatColor.YELLOW + "do /bc accept or /bc deny");
															//set to pending requests
															//if player already has pending
															if(Creator.pendingRequests.containsKey(tosend.getUniqueId())) {
																Creator.runningCreators.remove(Creator.pendingRequests.get(tosend.getUniqueId()));
															}
															Creator.pendingRequests.put(tosend.getUniqueId(), player.getUniqueId());
															player.sendMessage(ChatColor.GREEN + "Request Sent!");
															plugin.console.sendMessage(player.getName() + " had sent a child request to " + player.getName());
															return true;
														} else {
															sender.sendMessage(ChatColor.RED + "Your Partner had not yet set his/her gender!");
															return true;
														}
													} else {
														sender.sendMessage(ChatColor.RED + "You have not yet set your gender!");
														return true;
													}
												} else {
													player.sendMessage(ChatColor.RED + "Your partner have not set their gender, tell them to do so");
													return true;
												} 
											} else {
												player.sendMessage(ChatColor.RED + "You have not set your gender yet, do that using /bc setgender");
												return true;
											}
											
										} else {
											sender.sendMessage(ChatColor.RED + "You must be in a 10 block range to have a baby");
											return true;
										} 
									}else {
										sender.sendMessage(ChatColor.RED + "your partner does not have perimssion to have a child");
										plugin.console.sendMessage(ChatColor.RED + player.getName() + " was denied the command /bc haveChild bc their partener does not have permission");
										return true;
									}
								} else {
									sender.sendMessage(ChatColor.RED + "You do not have permission to have a child");
									plugin.console.sendMessage(ChatColor.RED + player.getName() + " was denied the command /bc haveChild");
									return true;
								}
							} else {
								sender.sendMessage(ChatColor.RED + "Player cannot be found");
								return true;
							}
						} else {
							sender.sendMessage(ChatColor.RED + "Usage: /babycraft haveChild <Username>");
							return true;
						}
					}
					
					// /bc adoptChild
					if(Commands.isCommand(args[0], Commands.AdoptChild)) {
						if(plugin.configIO.contains(player)) {
							if(player.hasPermission(BcPermissions.solo.get())) {
								if(player.hasPermission(BcPermissions.children.get())) {
									Creator.runningCreators.put(player.getUniqueId(), new Creator());
									Creator.runningCreators.get(player.getUniqueId()).openCreator(player, player);
									plugin.console.sendMessage(player.getName() + " is adopting a child");
									return true;
								} else {
									player.sendMessage(ChatColor.RED + "You do not have permission to have a child");
									plugin.console.sendMessage(ChatColor.RED + player.getName() + " was denied the comamnd /bc adoptChild");
									return true;
								}
							} else {
								player.sendMessage(ChatColor.RED + "You do not have permission to adopt a child");
								plugin.console.sendMessage(ChatColor.RED + player.getName() + " was denied the comamnd /bc adoptChild");
								return true;
							}
						} else {
							player.sendMessage(ChatColor.RED + "You must set your gender first do /bc gender to do so");
							return true;
						}
						
					}
					
					// /bc setGender
					if(Commands.isCommand(args[0], Commands.SetGender)) {
						//tests to make sure that arguments present
						if(args.length >= 2) {
							//if it is a boy
							if(args[1].equalsIgnoreCase("boy") || args[1].equalsIgnoreCase("male")|| args[1].equalsIgnoreCase("man") || args[1].equalsIgnoreCase("m") || args[1].equalsIgnoreCase("b")) {
								plugin.configIO.setGender(player, Gender.male);
								plugin.reloadConfig();
								sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&aGender set to &bmale"));
								plugin.console.sendMessage(player.getName() + " had set their gender to male");
								return true;
							//if it is a girl
							}else if(args[1].equalsIgnoreCase("girl") || args[1].equalsIgnoreCase("female")|| args[1].equalsIgnoreCase("women") || args[1].equalsIgnoreCase("woman") || args[1].equalsIgnoreCase("f") || args[1].equalsIgnoreCase("g")) {
								plugin.configIO.setGender(player, Gender.female);
								plugin.reloadConfig();
								sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&aGender set to &dfemale"));
								plugin.console.sendMessage(player.getName() + " had set their gender to female");
								return true;
							//if invaild
							}else{
								sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c The gender you have put in is not vaild"));
								return true;
							}
						} else {
							sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cUsage: /babycraft setGender <boy/girl/male/female>"));
							return true;
						}
					}
						
					// /bc spawnChild
					if(Commands.isCommand(args[0], Commands.SpawnChild)) {
						//tests for arguments
						if(args.length >= 2) {
							//test if child exists in config
							//gets the list
							List<String> list = plugin.configIO.getChildren(player);
							//tests if has children
							if(list.contains(ConfigValue.NULL.toString())) {
								player.sendMessage(ChatColor.RED + "You have no children");
								return true;
							}
							//goes through list
							for(String uuid_child : list) {
								String name = plugin.configIO.getName(UUID.fromString(uuid_child));
								if(name == null)
									continue;
								if(name.equalsIgnoreCase(args[1])) {
									if(Child.children.containsKey(uuid_child)) {
										player.sendMessage(ChatColor.RED + name + " had been already spawned");
										return true;
									}
									Child.children.put(UUID.fromString(uuid_child), new Child());
									(Child.children.get(UUID.fromString(uuid_child))).spawn(UUID.fromString(uuid_child), player);
									plugin.console.sendMessage(name + " with uuid " + uuid_child + " is being spawned");
									return true;
								}
							}
							player.sendMessage(ChatColor.RED + "You do not have a child named that");
							return true;
						} else {
							player.sendMessage(ChatColor.RED + "Usage: /babycraft spawnchild <name>");
							return true;
						}
					}
					
					// /bc despawnChild
					if(Commands.isCommand(args[0], Commands.DespawnChild)) {
						//tests for aguments
						if(args.length >= 2) {
							//tests if all is used
							if(args[1].equalsIgnoreCase("all")) {
								//creates list
								List<String> list = plugin.configIO.getChildren(player);
								//tests if has children
								if(list.contains(ConfigValue.NULL.toString())) {
									player.sendMessage(ChatColor.RED + "You have no children");
									return true;
								}
								//goes through whole of list
								for(String child_uuid_String : list) {
									//converts to uuid
									UUID child_uuid = UUID.fromString(child_uuid_String);
									//tests if child is spawned
									if(Child.children.containsKey(child_uuid)) {
										//despawns
										(Child.children.get(child_uuid)).despawn();
										plugin.console.sendMessage("uuid child " + child_uuid + "is being despawned");
									}
								}
								player.sendMessage(ChatColor.GREEN + "All Despawned");
								return true;
							}
							
							//goes for name
							//creates list
							List<String> list = plugin.configIO.getChildren(player);
							//tests if has children
							if(list.contains(ConfigValue.NULL.toString())) {
								player.sendMessage(ChatColor.RED + "You have no children");
								return true;
							}
							
							//gets the uuid
							String uuid = "null";
							
							for(String current : list) {
								if((plugin.configIO.getName(UUID.fromString(current)).equals(args[1]))) {
									uuid = current;
									break;
								}	
							}
							
							if(uuid.equalsIgnoreCase("null")) {
								player.sendMessage(ChatColor.RED + "You do not have a child named that");
								return true;
							}
								
							
							//tests if child is spawned
							if(Child.children.containsKey(UUID.fromString(uuid))) {
								//despawns
								(Child.children.get(UUID.fromString(uuid))).despawn();
								player.sendMessage(ChatColor.GREEN + "Despawned!");
								plugin.console.sendMessage("uuid child " + uuid + " is being despawned");
								return true;
							} else {
								player.sendMessage(ChatColor.RED + "this child is not spawned");
								return true;
							}
						}
					}
						
					// /bc warpChild
					if(Commands.isCommand(args[0], Commands.WarpChild)) {
						if(args.length >= 2) {
							//goes for name
							//creates list
							List<String> list = plugin.configIO.getChildren(player);
							//tests if has children
							if(list.contains(ConfigValue.NULL.toString())) {
								player.sendMessage(ChatColor.RED + "You have no children");
								return true;
							}
							
							//gets the uuid
							String uuid = "null";
							
							for(String current : list) {
								if((plugin.configIO.getName(UUID.fromString(current))).equals(args[1])) {
									uuid = current;
									break;
								}	
							}
							
							if(uuid.equalsIgnoreCase("null")) {
								player.sendMessage(ChatColor.RED + "You do not have a child named that");
								return true;
							}
								
							
							//tests if child is spawned
							if(Child.children.containsKey(UUID.fromString(uuid))) {
								//despawns
								(Child.children.get(UUID.fromString(uuid))).tpHome();
								player.sendMessage(ChatColor.GREEN + "Child is home now");
								return true;
							} else {
								player.sendMessage(ChatColor.RED + "this child is not spawned");
								return true;
							}
						}
					}
					
					// /bc name 
					if(Commands.isCommand(args[0], Commands.Name)) {
						if(args.length >= 2) {
							if(Creator.pending.contains(player.getUniqueId())) {
								Creator.runningCreators.get(player.getUniqueId()).setName(args[1]);
								return true;
							} else {
								player.sendMessage(ChatColor.RED + "You are not pending to create a child");
								return true;
							}
						} else {
							player.sendMessage(ChatColor.RED + "You must include a name to name your child");
							return true;
						}
					}
					
					// /bc accept
					if(Commands.isCommand(args[0], Commands.Accept)) {
						if(Creator.pendingRequests.containsKey(player.getUniqueId())) {
							//sets cvariable for other player
							UUID uuid_other = Creator.pendingRequests.get(player.getUniqueId());
							Player parent1 = Bukkit.getPlayer(uuid_other);
							Creator.pendingRequests.remove(player.getUniqueId());
							//accepts and opens menu for other person
							Creator.runningCreators.get(uuid_other).openCreator(parent1, player);
							player.sendMessage(ChatColor.GREEN + "You accepted, now wait for your partner to finish configuration");
							plugin.console.sendMessage(player.getName() + " had accepted the invite of " + parent1.getName());
							return true;
						} else {
							player.sendMessage(ChatColor.RED + "You do not have any pending requests"); 
							return true;
						}
					}
					
					// /bc deny
					if(Commands.isCommand(args[0], Commands.Deny)) {
						if(Creator.pendingRequests.containsKey(player.getUniqueId())) {
							UUID uuid_other = Creator.pendingRequests.get(player.getUniqueId());
							Player parent1 = Bukkit.getPlayer(uuid_other);
							Creator.pendingRequests.remove(player.getUniqueId());
							//denies and opens menu for other person
							Creator.runningCreators.get(uuid_other).terminate();
							player.sendMessage(ChatColor.RED + "You denied, now wait for your partner to finish configuration");
							parent1.sendMessage(ChatColor.RED + "Your partner denied the request :(");
							plugin.console.sendMessage(player.getName() + " had denied the invite of " + parent1.getName());
							return true;
						} else {
							player.sendMessage(ChatColor.RED + "You do not have any pending requests");
							return true;
						}
					}
					
					// /bc list
					if(Commands.isCommand(args[0], Commands.List)) {
						//tests if has no children
						if(!(plugin.configIO.contains(player)) || (plugin.configIO.getChildren(player).contains(ConfigValue.NULL.toString()))) {
							player.sendMessage(ChatColor.RED + "You do not have children");
							return true;
						}
						List<String> list = plugin.configIO.getChildren(player);
						for(String value : list) {
							//gets gender
							if(plugin.configIO.getGender(UUID.fromString(value)).equals(Gender.male)) {
								player.sendMessage(ChatColor.AQUA + plugin.configIO.getName(UUID.fromString(value))); 
							} else {
								player.sendMessage(ChatColor.LIGHT_PURPLE + plugin.configIO.getName(UUID.fromString(value)));
							}
						}
						
						
						return true;
					}
				
					// handle no found arguments
					player.sendMessage(ChatColor.RED + "You have not put in a vaild argument, please try again or view the command list using /bc");
					return true;
				} else {
					Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED + "Console cannot run this command");
					return true;
				}
				
				
				
			} else {
			//no arguments, will list commands
				sender.sendMessage(new String[] {
						ChatColor.translateAlternateColorCodes('&', "&b~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~"),
						ChatColor.translateAlternateColorCodes('&', "&e&lBabycraft Commands"),
						ChatColor.translateAlternateColorCodes('&', "&b/babycraft &ehaveChild <Username>"),
						ChatColor.translateAlternateColorCodes('&', "&b/babycraft &eadoptChild"),
						ChatColor.translateAlternateColorCodes('&', "&b/babycraft &esetGender <boy/girl/male/female>"),
						ChatColor.translateAlternateColorCodes('&', "&b/babycraft &espawnChild <child name>"),
						ChatColor.translateAlternateColorCodes('&', "&b/babycraft &edespawnChild <child name/all>"),
						ChatColor.translateAlternateColorCodes('&', "&b/babycraft &ewarpChild"),
						ChatColor.translateAlternateColorCodes('&', "&b/babycraft &elist"),
						ChatColor.translateAlternateColorCodes('&', "&b/babycraft &eAdmin"),
						ChatColor.translateAlternateColorCodes('&', "&b~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~")
				});
				return true;
			}
		}
		
		return false;
	}

}
