package babyCraft.Bukkit.Someoneawesome.java.Managers;

import java.util.*;

import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.event.*;
import org.bukkit.event.entity.*;
import org.bukkit.event.inventory.*;
import org.bukkit.event.player.*;
import org.bukkit.inventory.*;

import babyCraft.Bukkit.Someoneawesome.*;
import babyCraft.Bukkit.Someoneawesome.java.GUIMenus.*;
import babyCraft.Bukkit.Someoneawesome.java.Managers.childSpawnManagers.*;
import babyCraft.Bukkit.Someoneawesome.java.enums.*;

public class EventHandlerClass implements Listener {

	private Babycraft plugin = Babycraft.getPlugin(Babycraft.class);
	
	@EventHandler
	public void InvenClick(InventoryClickEvent event) {
		
		//getting info of event
		Player player = (Player) event.getWhoClicked();	
		ItemStack item = event.getCurrentItem();
		int slot = event.getRawSlot();
		
		//note cursor and slot is swapped
		ItemStack cursor = event.getInventory().getItem(slot);
		Inventory inv = event.getInventory();
		ItemStack slotItem = player.getItemOnCursor();		
		Inventory open = event.getInventory();
		
		if(!(slot < inv.getSize()))
			return;
		
		// child menu
		if(open.getName().equals(ChatColor.AQUA + "Child's Menu")) {
			event.setCancelled(true);
			
			//send to armor menu
			if(item.getType().equals(Material.IRON_CHESTPLATE)) {
				Menu.setArmorMenu(player, Menu.menu.get(player.getUniqueId()));
				return;
			}
			
			//send to interact menu
			if(item.getType().equals(Material.SIGN)) {
				Menu.setInteractMenu(player);
				return;
			}
			
			//set home
			if(item.getType().equals(Material.RED_BED)) {
				Menu.menu.get(player.getUniqueId()).setHome(player.getLocation());
				player.sendMessage(ChatColor.GREEN + "Home set");
				player.closeInventory();
				return;
			}
			
			//movement
			if(item.getType().equals(Material.COMPASS)) {
				Menu.setMovementMenu(player);
				return;
			}
			
			//go home
			if(item.getType().equals(Material.OAK_DOOR)) {
				Menu.menu.get(player.getUniqueId()).tpHome();
				player.sendMessage(ChatColor.GREEN + "Teleported Home");
				player.closeInventory();
				return;
			}
			
			//settings
			if(item.getType().equals(Material.REDSTONE)) {
				Menu.setSettingMenu(player);
				return;
			}
			
			//close
			if(item.getType().equals(Material.BARRIER)) {
				player.closeInventory();
				return;
			}
		}
		
		//armor menu
		if(open.getName().equals(ChatColor.AQUA + "Child's Armor")) {
			//event.setCancelled(true);
			
			//if clicked the back button
			if(slot == 35) {
				event.setCurrentItem(null);
				Menu.setMainMenu(player);
				return;
			}
			
			if(!(slot == 4 || slot == 13 || slot == 22 || slot == 31)) {
				event.setCancelled(true);
				return;
			}
			
			if(cursor.getType().equals(Material.BARRIER)) {
				inv.setItem(slot, null);
			}
			
			boolean wasNull = false;
			
			if(slotItem == null || slotItem.getType().equals(Material.AIR)) {

				wasNull = true;
//				String name = "[error: slot not found]";
//				
//				//slot naming
//				if(slot == 4) {
//					name = "Helmet";
//				}
//				
//				if(slot == 13) {
//					name = "Body";
//				}
//				
//				if(slot == 22) {
//					name = "Legs";
//				}
//				
//				if(slot == 31) {
//					name = "Feet";
//				}
//				
//				ItemStack empty = new ItemStack(Material.BARRIER, 1, (short) 0);
//				ItemMeta item_meta = empty.getItemMeta(); 
//				item_meta.setDisplayName(name);
//				ArrayList<String> list = new ArrayList<>();
//				list.add("add armor to your child");
//				item_meta.setLore(list);
//				empty.setItemMeta(item_meta);
				
				//player.getInventory().addItem(slotItem);
				player.getWorld().dropItemNaturally(player.getLocation(), cursor);
				
			}
			
			Child instance = Menu.menu.get(player.getUniqueId());
			
			//variable to be the armor to be added
			ItemStack toAdd;
			
			//decide what should be added
			if(wasNull)
				toAdd = null;
			else
				toAdd = slotItem;
			
			//sets armor
			     if(slot == 4) 
				instance.setArmor(toAdd, ArmorSlot.Head);
			else if(slot == 13) 
				instance.setArmor(toAdd, ArmorSlot.Body);
			else if(slot == 22)
				instance.setArmor(toAdd, ArmorSlot.Legs);
			else if(slot == 31)
				instance.setArmor(toAdd, ArmorSlot.Feet);
			     
			if(wasNull) {
				player.closeInventory();
				Menu.setArmorMenu(player, instance);
			}
				
			
			return;
		}
		
		// interact menu
		if(open.getName().equals(ChatColor.AQUA + "Interact")) {
			event.setCancelled(true);
			
			Child instance = Menu.menu.get(player.getUniqueId());
			//gender of parent
			Gender gender = plugin.configIO.getGender(player);
			
			//chat button
			if(item.getType().equals(Material.BOOK)) {
				instance.chat(gender);
				player.closeInventory();
				return;
			}
			//joke button
			if(item.getType().equals(Material.TROPICAL_FISH)) {
				instance.reactTo(Action.JOKED, true, gender);
				player.closeInventory();
				return;
			}
			
			//hug button
			if(item.getType().equals(Material.TOTEM_OF_UNDYING)) {
				instance.reactTo(Action.HUGGED, true, gender);
				player.closeInventory();
				return;
			}
			
			//kiss button
			if(item.getType().equals(Material.POPPY)) {
				instance.reactTo(Action.KISSED, true, gender);
				player.closeInventory();
				return;
			}
			
			//back button
			if(item.getType().equals(Material.RED_WOOL)) {
				Menu.setMainMenu(player);
				return;
			}
			
		}
		
		// setting menu
		if(open.getName().equals(ChatColor.AQUA + "Settings")) {
			event.setCancelled(true);
			
			Child instance = Menu.menu.get(player.getUniqueId());
			
			//despawn
			if(item.getType().equals(Material.DIAMOND_SWORD)) {
				instance.despawn();
				player.closeInventory();
				player.sendMessage(ChatColor.GREEN + "Despawned!");
				return;
			}
			
			//kill
			if(item.getType().equals(Material.RED_STAINED_GLASS_PANE)) {
				Menu.setVerifyMenu(player);
				return;
			}
			
			//skin
			if(item.getType().equals(Material.LEATHER_CHESTPLATE)) {
				instance.toggleSkin();
				instance.reactTo(Action.CHANGED, true, plugin.configIO.getGender(player));
				player.closeInventory();
				return;
			}
			
			//back
			if(item.getType().equals(Material.RED_WOOL)) {
				Menu.setMainMenu(player);
				return;
			}
			
		}
		
		// verify menu 
		if(open.getName().equals(ChatColor.RED + "Are you sure")) {
			event.setCancelled(true);
			
			if(item.getItemMeta().getDisplayName().equalsIgnoreCase("Yes")) {
				Child instance = Menu.menu.get(player.getUniqueId());
				UUID instance_uuid = instance.getUUID();
				instance.despawn();
				Child.removeChild(instance_uuid);
				player.closeInventory();
				return;
			}
			
			if(item.getItemMeta().getDisplayName().equalsIgnoreCase("No")) {
				Menu.setSettingMenu(player);
				return;
			}
			
		}
		
		// movement menu
		if(open.getName().equals(ChatColor.AQUA + "Child's Movement Settings")) {
			event.setCancelled(true);
			
			Child instance = Menu.menu.get(player.getUniqueId());
			Gender gender = plugin.configIO.getGender(player);
			
			//follow
			if(item.getType().equals(Material.COMPASS)) {
				instance.follow(player, gender);
				player.closeInventory();
				return;
			}
			
			//stay
			if(item.getType().equals(Material.BEDROCK)) {
				instance.stay(gender);
				player.closeInventory();
				return;
			}
			
			//roam
			if(item.getType().equals(Material.FEATHER)) {
				instance.roam(gender);
				player.closeInventory();
				return;
			}
			
			//back
			if(item.getType().equals(Material.RED_WOOL)) {
				Menu.setMainMenu(player);
				return;
			}
			
		}
			
		//Creation menu
		if(open.getName().equals(ChatColor.GREEN + "Have your baby")) {
			
			event.setCancelled(true);
			//setting needed vaiables and event
			
			Creator instance = Creator.runningCreators.get(player.getUniqueId());
			
			//if gender was clicked
			if(item.getType().equals(Material.LIGHT_BLUE_DYE) || item.getType().equals(Material.PINK_DYE)) {
				if(player.hasPermission(BcPermissions.changeBabyGender.get())) {
					player.sendMessage(ChatColor.YELLOW + "Gender changed");
					instance.toggleGender();
					instance.refresh();
					return;
				} else {
					player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cYou do not have permission to do that"));
					return;
				}
				
			}
			
			//if setname was clicked
			if(item.getType().equals(Material.NAME_TAG)) {
				instance.requestName();
				player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b&lDo &6&l/Babycraft name <Name> &b&lto name your child"));
				return;
			}
			
			//if done was clicked
			if(item.getType().equals(Material.EMERALD_BLOCK)) {
				instance.finish();
				player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&bAll Done! do &6/babycraft spawnChild &bto spawn him/her into the world"));
				
				//particles
				//loc sides
				Location loc = player.getLocation(); //center
				Location loc1 = loc.add(3, 1, 0); // +x
				Location loc2 = loc.add(-3, 1, 0); // -x
				Location loc3 = loc.add(0, 1, 3); // +z
				Location loc4 = loc.add(0, 1, -3); // -z
				//corner locs
				Location loc5 = loc.add(2, 1, 2); //+x +z
				Location loc6 = loc.add(-2, 1, 2); //-x +z
				Location loc7 = loc.add(2, 1, -2); //+x -z
				Location loc8 = loc.add(-2, 1, -2); //-x -z
				
				World world = player.getWorld();
				world.spawnParticle(Particle.HEART, loc1, 5);
				world.spawnParticle(Particle.HEART, loc2, 5);
				world.spawnParticle(Particle.HEART, loc3, 5);
				world.spawnParticle(Particle.HEART, loc4, 5);
				world.spawnParticle(Particle.HEART, loc5, 5);
				world.spawnParticle(Particle.HEART, loc6, 5);
				world.spawnParticle(Particle.HEART, loc7, 5);
				world.spawnParticle(Particle.HEART, loc8, 5);
				
				if(player.getUniqueId().toString().equalsIgnoreCase(instance.getParent2().getUniqueId().toString()))
					return;
				else {
					Player parent = instance.getParent2();
					loc = parent.getLocation();
					
					//particules
					loc = player.getLocation(); //center
					loc1 = loc.add(3, 1, 0); // +x
					loc2 = loc.add(-3, 1, 0); // -x
					loc3 = loc.add(0, 1, 3); // +z
					loc4 = loc.add(0, 1, -3); // -z
					//corner locs
					loc5 = loc.add(2, 1, 2); //+x +z
					loc6 = loc.add(-2, 1, 2); //-x +z
					loc7 = loc.add(2, 1, -2); //+x -z
					loc8 = loc.add(-2, 1, -2); //-x -z
					
					world.spawnParticle(Particle.HEART, loc1, 5);
					world.spawnParticle(Particle.HEART, loc2, 5);
					world.spawnParticle(Particle.HEART, loc3, 5);
					world.spawnParticle(Particle.HEART, loc4, 5);
					world.spawnParticle(Particle.HEART, loc5, 5);
					world.spawnParticle(Particle.HEART, loc6, 5);
					world.spawnParticle(Particle.HEART, loc7, 5);
					world.spawnParticle(Particle.HEART, loc8, 5);
				}
				return;
			}
				
		}
	}
	
	// onInvQuit
	@EventHandler
	public void onInvLeave(InventoryCloseEvent event) {
		//set variables
		Player player = (Player) event.getPlayer();
		Inventory inv = event.getInventory();
		
		//tests if proper inv
		if(inv.getName().equals(ChatColor.GREEN + "Have your baby")) {
			//tests if is not in pending creations
			if(!Creator.pending.contains(player.getUniqueId())) {
				//tests if it should not close
				if(!((Creator.runningCreators.get(player.getUniqueId())).doNotClose)) {
					//conels action
					Player partner = (Creator.runningCreators.get(player.getUniqueId())).getParent2();
					partner.sendMessage(ChatColor.RED + "Your partner had cancelled the baby making");
					player.sendMessage(ChatColor.RED + "You have cancelled your baby");
					Creator.runningCreators.get(player.getUniqueId()).terminate();
					plugin.console.sendMessage(player.getName() + " had canceled making a baby");
					return;
				} else {
					Creator.runningCreators.get(player.getUniqueId()).refresh();
				}
			}	
		}
		
		
	}
	
	//PlayerInteract to entity
	@EventHandler
	public void onPlayerInteract(PlayerInteractEntityEvent event) {
		Player player = event.getPlayer();
		Entity clicked = event.getRightClicked();
		
		//checks for villager
		if(clicked.getType() == EntityType.VILLAGER) {
			//check ids against
			if(Child.ids.containsKey(clicked.getUniqueId())) {
				Child instance = Child.children.get(Child.ids.get(clicked.getUniqueId()));
				if(instance.isParent(player)) {
					instance.enterMenu(player);
					Menu.menu.put(player.getUniqueId(), instance);
				} else {
					instance.rejectMenu(player);
				}
			}
		}
	}
	
	//eventHandler OnQuit
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		
		for(UUID value : Child.children.keySet()) {
			if((Child.children.get(value).isParent(player))) {
				UUID parent1 = Child.children.get(value).getParent1();
				UUID parent2 = Child.children.get(value).getParent1();
				
				if((Bukkit.getPlayer(parent1) == null) || (Bukkit.getOfflinePlayer(parent1).isOnline())) {
					if((Bukkit.getPlayer(parent2) == null) || (Bukkit.getOfflinePlayer(parent2).isOnline())) {
						Child.children.get(value).despawn();
					}
				}
				
			}
		}
	
	}
	
	@EventHandler
	public void onPlayerAttack(EntityDamageByEntityEvent event) {
		Entity damager = event.getDamager();
		Entity damaged = event.getEntity();
		
		//checks for correct conditions
		if(!(damager instanceof Player))
			return;
		
		if(!(damaged instanceof Villager))
			return;
		
		//sets the variables for player and villager
		Player player = (Player) damager;
		Villager villager = (Villager) damaged;
		
		//tests if villager is a "child"
		if(!Child.ids.containsKey(villager.getUniqueId()))
			return;
		
		//gets child
		UUID instance_UUID = Child.ids.get(villager.getUniqueId());
		
		//sets child instance
		Child instance = Child.children.get(instance_UUID);
		
		boolean isparent;
		
		if(instance.isParent(player))
			isparent = true;
		else
			isparent = false;
		
		Gender gender = plugin.configIO.getGender(player);
		
		instance.reactTo(Action.ATTACKED, isparent, gender);
		
	}
	
	
}
