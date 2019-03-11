package babyCraft.Bukkit.Someoneawesome.java.Managers.childSpawnManagers;

import java.util.*;

import org.bukkit.*;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_13_R1.entity.*;
import org.bukkit.entity.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Villager.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.*;

import babyCraft.Bukkit.Someoneawesome.*;
import babyCraft.Bukkit.Someoneawesome.java.GUIMenus.*;
import babyCraft.Bukkit.Someoneawesome.java.Managers.*;
import babyCraft.Bukkit.Someoneawesome.java.ai.*;
import babyCraft.Bukkit.Someoneawesome.java.enums.*;
import net.minecraft.server.v1_13_R1.*;

public class Child {
	public static HashMap<UUID, Child> children;
	public static HashMap<UUID, UUID> ids;
	
	private static Babycraft plugin = Babycraft.getPlugin(Babycraft.class);
	
	private UUID child_uuid;
	public UUID getUUID() {
		return child_uuid;
	}
	private String child_name;
	public String getName() {
		return child_name;
	}
	private String gender;
	public Gender getGender() {
		return Gender.fromString(gender);
	}
	
	private ItemStack[] armor;
	//0 = head, 1 = body, 2 = legs, 3 = feet
	private Location home;
	private Villager mob;
	//private int mobId;
	private UUID mobId;
	//parents
	private UUID parent1;
	private UUID parent2;
	//getters
	public UUID getParent1() {return parent1;}
	public UUID getParent2() {return parent2;}
	
	private ChatBot chat;
	
	private boolean Ai;

	private Profession clothesColor;
	private Iterator<Profession> professions;
	
	private Runnable follow;
	private int follow_id;
	
	public void spawn(UUID uuid, Player player) {
		//setting shotcut for config
		ConfigIO config = plugin.configIO;
		
		//setting uuid
		child_uuid = uuid;
		
		//testing if existant in config
		if(!config.contains(uuid)) {
			player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cAn Error occured, contact server administrator"));
			children.remove(uuid);
			return;
		}
		
		//general things
		child_name = config.getName(uuid);
		gender = config.getGender(uuid).toString();
		home = config.getHome(uuid);
		
		clothesColor = config.getProfession(uuid);
		
		//sets irterator for toggle usage
		professions = (Arrays.asList(Profession.values())).iterator();
		while(!professions.next().equals(clothesColor)) {}
		
		//armor setting
		
		armor = config.getArmor(uuid);
		
		//Parents setting
		parent1 = UUID.fromString(config.getParent1UUID(uuid));
		
		if(!(config.getParent2UUID(uuid).equalsIgnoreCase(ConfigValue.NULL.toString())))
			parent2 = UUID.fromString(config.getParent2UUID(uuid));
		else 
			parent2 = parent1;
		
		//spawning
		mob = player.getWorld().spawn(player.getLocation(), Villager.class);
		//CustomVillager.EntityTypes.spawnEntity(new CustomVillager(Bukkit.getWorld("world")), new Location(Bukkit.getWorld("world"), 100, 100, 100));
		
		//mobId = mob.getUniqueId();
		mobId = mob.getUniqueId();
		
		mob.setProfession(clothesColor);
		Ai = true;
		mob.setAI(Ai);
		mob.setBaby();
		mob.setAgeLock(true);
		
		//name color
		if(Gender.male.toString().equalsIgnoreCase(gender)) {
			mob.setCustomName(ChatColor.translateAlternateColorCodes('&', "&b" + child_name));
		} else {
			mob.setCustomName(ChatColor.translateAlternateColorCodes('&', "&d" + child_name));
		}
		
		mob.setCustomNameVisible(true);
		
		PotionEffect effect = new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, Integer.MAX_VALUE, 1000, true, true);
		mob.addPotionEffect(effect);
		
		if(armor[3] != null && !(armor[3].getType().equals(Material.AIR)))
			mob.getEquipment().setBoots(armor[3]);
		if(armor[2] != null && !(armor[2].getType().equals(Material.AIR)))
			mob.getEquipment().setLeggings(armor[2]);
		if(armor[1] != null && !(armor[1].getType().equals(Material.AIR)))
			mob.getEquipment().setChestplate(armor[1]);
		if(armor[0] != null && !(armor[0].getType().equals(Material.AIR)))
			mob.getEquipment().setHelmet(armor[0]);
		
		chat = new ChatBot(Gender.fromString(gender), this);
		
		ids.put(mobId, child_uuid);
		plugin.getLogger().info(child_name + " was spawned");
	}
	
	public void despawn() {
		refreshMob();
		
		Location loc = mob.getLocation();
		loadChunks(loc);
		
		if(!Ai)
			mob.setAI(true);
		if(follow != null)
			stopFollow();
		
		mob.setHealth(0);
		ids.remove(mobId);
		children.remove(child_uuid);
		
		unLoadChunk(loc);
		
		plugin.getLogger().info(child_name + " was despawned");
	}
	
	public void tpHome() {
		refreshMob();
		mob.teleport(home);
	}
	
	public void setHome(Location location) {
		refreshMob();
		//set it to object
		home = location;
		//set it to config
		plugin.configIO.setHome(location, child_uuid);
	}
	
	public void setArmor(ItemStack[] armor) {
		
		refreshMob();
		
		
		//set to mob
		mob.getEquipment().setBoots(armor[3]);
		mob.getEquipment().setLeggings(armor[2]);
		mob.getEquipment().setChestplate(armor[1]);
		mob.getEquipment().setHelmet(armor[0]);
		
		
		
		//set to object
		this.armor = armor;
		//set to config
		plugin.configIO.setArmor(child_uuid, armor);
	}
	
	public void setArmor(ItemStack item, ArmorSlot slot) {
		refreshMob();
		
		//update current armor
		if(slot.equals(ArmorSlot.Head))
			mob.getEquipment().setHelmet(item);
		else if(slot.equals(ArmorSlot.Body))
			mob.getEquipment().setChestplate(item);
		else if(slot.equals(ArmorSlot.Legs))
			mob.getEquipment().setLeggings(item);
		else if(slot.equals(ArmorSlot.Feet))
			mob.getEquipment().setBoots(item);
		
		plugin.configIO.setArmor(child_uuid, item, slot);
	}
	
	private void changeSkin(Profession color) {
		refreshMob();
		
		//set to mob
		mob.setProfession(color);
		
		//set to object
		this.clothesColor = color;
		
		//set to config
		plugin.configIO.setProfession(child_uuid, color);
	}
	
	@SuppressWarnings("deprecation")
	public void toggleSkin() {
		refreshMob();
		if(professions.hasNext()) {
			Profession prof = professions.next();
			
			if(prof.equals(Profession.HUSK)) {
				professions = (Arrays.asList(Profession.values())).iterator();
				prof = professions.next();
			}
			
			if(prof.equals(Profession.NORMAL)) {
				prof = professions.next();
			}
			
			changeSkin(prof);
			return;
		} else {
			professions = (Arrays.asList(Profession.values())).iterator();
			changeSkin(professions.next());
			return;
		}
	}
	
	// allows child to chat
	public void sayline(String msg, int radius) {
		refreshMob();
		
		double squaredRadius = (double) radius * radius;
		
		//get all the entityies in raduis
		List<Entity> list = mob.getNearbyEntities(radius, radius, radius); // entities in box
		
		for(Entity value : list) {
			//test if distance is in radius
			if(value.getLocation().distanceSquared(mob.getLocation()) > squaredRadius) //entites in sphere
				continue;
			
			if(value instanceof Player) {
				value.sendMessage(msg);
			}
		}
	}
	
	//chatting method
	public void reactTo(Action action, boolean isParent, Gender gender) {
		
		if(action.equals(Action.ATTACKED))
			chat.attackedReact(isParent, gender);
		else
			chat.reactTo(action, gender);
	}
	
	public void chat(Gender Parentgender) {
		chat.interact(Parentgender);
	}
	
	//enters menu of child
	public void enterMenu(Player player) {
		Menu.setMainMenu(player);
		plugin.console.sendMessage(player.getName() + "had opened their child's menu");
	}
	
	//will allow mob to wonder or not
	private void toggleAi(boolean state) {
		refreshMob();
		Ai = state;
		mob.setAI(Ai);
	}
	
	//allows child to reject player to menu
	public void rejectMenu(Player player) {
		player.sendMessage(ChatColor.RED + "The Child does not want you to access their menu");
	}
	
	public void follow(Player player, Gender gender) {
		refreshMob();
		toggleAi(true);
		
		followPlayer(player, mob, 0.75d);
		
		chat.follow(gender);
	}
	
	public void stay(Gender gender) {
		refreshMob();
		mob.setTarget(null);
		toggleAi(false);
		chat.stay(gender);
		stopFollow();
		follow = null;
	}
	
	public void roam(Gender gender) {
		refreshMob();
		mob.setTarget(null);
		toggleAi(true);
		chat.roam(gender);
		stopFollow();
		follow = null;
	}
	
	public boolean isParent(Player player) {
		if(parent1.toString().equalsIgnoreCase(player.getUniqueId().toString())) {
			return true;
		}
		if(parent2.toString().equalsIgnoreCase(player.getUniqueId().toString())) {
			return true;
		}
		return false;
	}
	
	@Deprecated
	public void followPlayer(Player player, LivingEntity entity, double d) {
		
		stopFollow();
		
		final LivingEntity e = entity;
		final Player p = player;
		final float f = (float) d;
		
		follow = new Runnable() {	
			@Override
			public void run() {
				((EntityInsentient) ((CraftEntity) e).getHandle()).getNavigation().a(p.getLocation().getX(), p.getLocation().getY(), p.getLocation().getZ(), f);
				if(p.getLocation().getWorld() != e.getLocation().getWorld())
					return;
                if(Distance(p.getLocation(), e.getLocation()) > 20){
                    e.teleport(p.getLocation().add(1,0,1));
                    p.sendMessage(ChatColor.RED + "Your child is being left behind! Slow down!");
                }
			}
			
			public double Distance(Location playerloc, Location entityloc){
                double distance = playerloc.distance(entityloc);
                return distance;
            }

		};
		
		follow_id = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, follow, 0 * 20, 2 * 20);
	}
	
	public void followPlayer(Player player, LivingEntity entity, double d, boolean thing) {
		stopFollow();
		
		final LivingEntity e = entity;
		final Player p = player;
		final float f = (float) d;
		
		follow = new Runnable() {	
			@Override
			public void run() {
				//((EntityInsentient) ((CraftEntity) e).getHandle()).getNavigation().a(p.getLocation().getX(), p.getLocation().getY(), p.getLocation().getZ(), f);
				if(p.getLocation().getWorld() != e.getLocation().getWorld())
					return;
				
				//start pathfinding
				((EntityCreature) e).setGoalTarget((EntityLiving) ((CraftPlayer) p).getHandle());
				
                if(Distance(p.getLocation(), e.getLocation()) > 20){
                    e.teleport(p.getLocation().add(1,0,1));
                    p.sendMessage(ChatColor.RED + "Your child is being left behind! Slow down!");
                }
			}
			
			public double Distance(Location playerloc, Location entityloc){
                double distance = playerloc.distance(entityloc);
                return distance;
            }

		};
		
		follow_id = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, follow, 0 * 20, 2 * 20);
	}
	
	public void stopFollow() {
		Bukkit.getScheduler().cancelTask(follow_id);
	}
	
	private void refreshMob() {
		if(mob.getLocation().getChunk().isLoaded())
			mob = (Villager) Bukkit.getServer().getEntity(mobId);
		else return;
	}
	
	public static void newChild(String name, Player parent1, Player parent2, String gender) {
		//note must test if parent exists in yml before using
		//parent1 is the one doing the command

		ConfigIO config = plugin.configIO;
		
		//set uuid
		UUID uuid = UUID.randomUUID();
		
		//set data in yml for children
		//name
		config.setName(uuid, name);
		//parent1
		config.setParent1UUID(uuid, parent1);
		//parent2
		if(!parent2.getName().equalsIgnoreCase(parent1.getName()))
			config.setParent2UUID(uuid, parent2);
		else
			config.setParent2UUID_NULL(uuid);
		
		//armor
		config.setArmor(uuid, new ItemStack[4]);
		
		//gender
		config.setGender(uuid, Gender.fromString(gender));
		
		//home
		config.setHome(parent1.getLocation(), uuid);
		
		//clothescolor
		config.setProfession(uuid, Profession.LIBRARIAN);
		
		//set data in yml for players
		
		List<String> list = config.getChildren(parent1);
		if(list.contains(ConfigValue.NULL.toString())) 
			list.clear();
		//add children
		list.add(uuid.toString());
		
		config.setChildren(parent1, list);	
		//parent2
		
		//tests if adopted
		if(!parent2.getName().equalsIgnoreCase(parent1.getName())) {
			//children test
			list = config.getChildren(parent2);
			if(list.contains(ConfigValue.NULL.toString())) 
				list.clear();
			//add children
			list.add(uuid.toString());
			config.setChildren(parent2, list);
		}
	}
	
	public void loadChunks(Location loc) {
		refreshMob();
		loc.getWorld().loadChunk(loc.getChunk());
	}
	
	public void unLoadChunk(Location loc) {
		loc.getWorld().unloadChunk(loc.getChunk());
	}
	
	public static void removeChild(UUID uuid) {
		ConfigIO config = plugin.configIO;
		
		//remove from parents list
		//parent1
		Player parent1 = Bukkit.getPlayer(UUID.fromString(config.getParent1UUID(uuid)));
		
		List<String> list = config.getChildren(parent1);
		if(list.size() == 1) {
			list.clear();
			list.add(ConfigValue.NULL.toString());
		} else
			list.remove(uuid.toString());
		
		config.setChildren(parent1, list);
		
		//parent2
		
		if(!config.getParent2UUID(uuid).equalsIgnoreCase(ConfigValue.NULL.toString())) {
			Player parent2 = Bukkit.getPlayer(UUID.fromString(config.getParent2UUID(uuid)));
			list = config.getChildren(parent2);
			if(list.size() == 1) {
				list.clear();
				list.add(ConfigValue.NULL.toString());
			} else
				list.remove(uuid.toString());
			
			config.setChildren(parent2, list);
		}
		
		config.remove(uuid);
	}
	
	public static void despawnAll() {
		//warning msg
		Bukkit.broadcastMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "DESPAWNING ALL CHILDREN you can respawn them later when you are allowed to");
		//despawning
		for(UUID value : children.keySet()) {
			children.get(value).despawn();
		}
	}
	
}
