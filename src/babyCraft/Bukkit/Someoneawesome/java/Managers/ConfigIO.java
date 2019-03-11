package babyCraft.Bukkit.Someoneawesome.java.Managers;

import java.util.*;

import org.bukkit.*;
import org.bukkit.configuration.file.*;
import org.bukkit.enchantments.*;
import org.bukkit.entity.*;
import org.bukkit.entity.Villager.*;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.*;
import org.bukkit.inventory.meta.Damageable;

import babyCraft.Bukkit.Someoneawesome.java.enums.*;

public class ConfigIO {
	
	//private static Babycraft plugin = Babycraft.getPlugin(Babycraft.class); 

	private ConfigManager manager;
	private FileConfiguration playerCfg;
	private FileConfiguration ChildrenCfg;
	private FileConfiguration mainCfg;
	
	public ConfigIO(ConfigManager manager) {
		this.manager = manager;
		playerCfg = manager.playerCfg;
		ChildrenCfg = manager.childrenCfg;
		mainCfg = manager.MainCfg;
	}

	public void setGender(Player player, Gender gender) {
		playerCfg.set(ConfigPlayerPath.toPath(player.getUniqueId(), ConfigPlayerPath.gender), gender.toString());
		manager.savePlayer();
	}
	
	public void setGender(UUID child, Gender gender) {
		ChildrenCfg.set(ConfigChildPath.toPath(child, ConfigChildPath.gender), gender.toString()); 
		manager.saveChildren();
	}
	
	public void setPregStat(Player player, int stat) {
		playerCfg.set(ConfigPlayerPath.toPath(player.getUniqueId(), ConfigPlayerPath.pregStat), stat);
		manager.savePlayer();
	}
	
	public void setPregStat(Player player) {
		playerCfg.set(ConfigPlayerPath.toPath(player.getUniqueId(), ConfigPlayerPath.pregStat), getPregStat(player) + 1);
		manager.savePlayer();
	}
	
	public void setPregWith(Player player, Player player2) {
		playerCfg.set(ConfigPlayerPath.toPath(player.getUniqueId(), ConfigPlayerPath.pregWith), player2.getUniqueId().toString());
		manager.savePlayer();
	}
	
	public void setPregNull(Player player) {
		playerCfg.set(ConfigPlayerPath.toPath(player.getUniqueId(), ConfigPlayerPath.preg), null);
		manager.savePlayer();
	}
	
	public void setName(UUID child, String name) {
		ChildrenCfg.set(ConfigChildPath.toPath(child, ConfigChildPath.name), name); 
		manager.saveChildren();
	}
	
	public void setParent1UUID(UUID child, Player player) {
		ChildrenCfg.set(ConfigChildPath.toPath(child, ConfigChildPath.parent1_UUID), player.getUniqueId().toString());
		manager.saveChildren();
	}
	
	public void setParent2UUID(UUID child, Player player) {
		ChildrenCfg.set(ConfigChildPath.toPath(child, ConfigChildPath.parent2_UUID), player.getUniqueId().toString());
		manager.saveChildren();
	}
	
	public void setParent2UUID_NULL(UUID child) {
		ChildrenCfg.set(ConfigChildPath.toPath(child, ConfigChildPath.parent2_UUID), ConfigValue.NULL.toString());
		manager.saveChildren();
	}
	
	public void setArmor(UUID child, ItemStack item, ArmorSlot slot) {		
		
		if(slot.equals(ArmorSlot.Head)) {
			
			if(item != null)
				setItemStack(ChildrenCfg, ConfigChildPath.toPath(child, ConfigChildPath.armor_head), item);
			else
				ChildrenCfg.set(ConfigChildPath.toPath(child, ConfigChildPath.armor_head), ConfigValue.NULL.toString());
			
		} else if(slot.equals(ArmorSlot.Body)) {
			
			if(item != null)
				setItemStack(ChildrenCfg, ConfigChildPath.toPath(child, ConfigChildPath.armor_body), item);
			else
				ChildrenCfg.set(ConfigChildPath.toPath(child, ConfigChildPath.armor_body), ConfigValue.NULL.toString());
			
		} else if(slot.equals(ArmorSlot.Legs)) {
			
			if(item != null)
				setItemStack(ChildrenCfg, ConfigChildPath.toPath(child, ConfigChildPath.armor_legs), item);
			else
				ChildrenCfg.set(ConfigChildPath.toPath(child, ConfigChildPath.armor_legs), ConfigValue.NULL.toString());
			
		} else if(slot.equals(ArmorSlot.Feet)) {
			
			if(item != null)
				setItemStack(ChildrenCfg, ConfigChildPath.toPath(child, ConfigChildPath.armor_feet), item);
			else
				ChildrenCfg.set(ConfigChildPath.toPath(child, ConfigChildPath.armor_feet), ConfigValue.NULL.toString());
			
		}
		
		manager.saveChildren();
	}
	
	public void setArmor(UUID child, ItemStack[] armor) {
				
		if(armor[0] != null) 
			setItemStack(ChildrenCfg, ConfigChildPath.toPath(child, ConfigChildPath.armor_head), armor[0]);
		else
			ChildrenCfg.set(ConfigChildPath.toPath(child, ConfigChildPath.armor_head), ConfigValue.NULL.toString());
			
		if(armor[1] != null)
			setItemStack(ChildrenCfg, ConfigChildPath.toPath(child, ConfigChildPath.armor_body), armor[1]);
		else
			ChildrenCfg.set(ConfigChildPath.toPath(child, ConfigChildPath.armor_body), ConfigValue.NULL.toString());
		
		if(armor[2] != null)
			setItemStack(ChildrenCfg, ConfigChildPath.toPath(child, ConfigChildPath.armor_legs), armor[2]);
		else
			ChildrenCfg.set(ConfigChildPath.toPath(child, ConfigChildPath.armor_legs), ConfigValue.NULL.toString());
		
		if(armor[3] != null)
			setItemStack(ChildrenCfg, ConfigChildPath.toPath(child, ConfigChildPath.armor_feet), armor[3]);
		else
			ChildrenCfg.set(ConfigChildPath.toPath(child, ConfigChildPath.armor_feet), ConfigValue.NULL.toString());
		
		manager.saveChildren();
	}
	
	public void setHome(Location loc, UUID child) {
		ChildrenCfg.set(ConfigChildPath.toPath(child, ConfigChildPath.home_world), loc.getWorld().getUID().toString());
		ChildrenCfg.set(ConfigChildPath.toPath(child, ConfigChildPath.home_x), loc.getX());
		ChildrenCfg.set(ConfigChildPath.toPath(child, ConfigChildPath.home_y), loc.getY());
		ChildrenCfg.set(ConfigChildPath.toPath(child, ConfigChildPath.home_z), loc.getZ()); 
		manager.saveChildren();
	}
	
	public void setProfession(UUID child, Profession prof) {
		ChildrenCfg.set(ConfigChildPath.toPath(child, ConfigChildPath.clothesColor), prof.toString());
		manager.saveChildren();
	}
	
	public void setChildren(Player player, List<String> list) {
		playerCfg.set(ConfigPlayerPath.toPath(player.getUniqueId(), ConfigPlayerPath.children), list); 
		manager.savePlayer();
	}
	
	//getters
	
	public List<Player> getAllPlayers() {
		List<String> s = playerCfg.getStringList(ConfigPlayerPath.player.toString());
		List<Player> p = new ArrayList<>();
		for(String a : s) {
			p.add(Bukkit.getPlayer(UUID.fromString(a)));
		}
		return p;
	}
	
	public String getName(UUID child) {
		return ChildrenCfg.getString(ConfigChildPath.toPath(child, ConfigChildPath.name));
	}
	
	public String getParent1UUID(UUID child) {
		return ChildrenCfg.getString(ConfigChildPath.toPath(child, ConfigChildPath.parent1_UUID));
	}
	
	public String getParent2UUID(UUID child) {
		return ChildrenCfg.getString(ConfigChildPath.toPath(child, ConfigChildPath.parent2_UUID));
	}
	
	public ItemStack[] getArmor(UUID child) {
		
		ItemStack[] stack = new ItemStack[4];
		
		if(!ChildrenCfg.getString(ConfigChildPath.toPath(child, ConfigChildPath.armor_head)).equalsIgnoreCase(ConfigValue.NULL.toString())) {
			stack[0] = getItemStack(ChildrenCfg, ConfigChildPath.toPath(child, ConfigChildPath.armor_head));
		}
		
		if(!ChildrenCfg.getString(ConfigChildPath.toPath(child, ConfigChildPath.armor_body)).equalsIgnoreCase(ConfigValue.NULL.toString())) {
			stack[1] = getItemStack(ChildrenCfg, ConfigChildPath.toPath(child, ConfigChildPath.armor_body));
		}
		
		if(!ChildrenCfg.getString(ConfigChildPath.toPath(child, ConfigChildPath.armor_legs)).equalsIgnoreCase(ConfigValue.NULL.toString())) {
			stack[2] = getItemStack(ChildrenCfg, ConfigChildPath.toPath(child, ConfigChildPath.armor_legs));
		}
		
		if(!ChildrenCfg.getString(ConfigChildPath.toPath(child, ConfigChildPath.armor_feet)).equalsIgnoreCase(ConfigValue.NULL.toString())) {
			stack[3] = getItemStack(ChildrenCfg, ConfigChildPath.toPath(child, ConfigChildPath.armor_feet));
		}
		
		return stack;
		
	}
	
	public Gender getGender(UUID child) {
		String gender = ChildrenCfg.getString(ConfigChildPath.toPath(child, ConfigChildPath.gender));
		return Gender.fromString(gender);
	}
	
	public Gender getGender(Player player) {
		String gender = playerCfg.getString(ConfigPlayerPath.toPath(player.getUniqueId(), ConfigPlayerPath.gender));
		return Gender.fromString(gender);
	}
	
	public int getPregStat(Player player) {
		return playerCfg.getInt(ConfigPlayerPath.toPath(player.getUniqueId(), ConfigPlayerPath.pregStat), -1);
	}
	
	public Player getPregWith(Player player) {
		return Bukkit.getPlayer(UUID.fromString(playerCfg.getString(ConfigPlayerPath.toPath(player.getUniqueId(), ConfigPlayerPath.pregWith))));
	}
	
	public List<Player> getAllPlayerPreg() {
		List<Player> raw = getAllPlayers();
		List<Player> r = new ArrayList<>();
		for(Player p : raw) {
			if(getPregStat(p) != -1) {
				r.add(p);
			}
		}
		return r;
	}
	
	public Location getHome(UUID child) {
		World world = Bukkit.getWorld(UUID.fromString(ChildrenCfg.getString(ConfigChildPath.toPath(child, ConfigChildPath.home_world))));
		double x = ChildrenCfg.getDouble(ConfigChildPath.toPath(child, ConfigChildPath.home_x));
		double y = ChildrenCfg.getDouble(ConfigChildPath.toPath(child, ConfigChildPath.home_y));
		double z = ChildrenCfg.getDouble(ConfigChildPath.toPath(child, ConfigChildPath.home_z));
		return new Location(world, x, y, z);
	}
	
	public Profession getProfession(UUID child) {
		String profession = ChildrenCfg.getString(ConfigChildPath.toPath(child, ConfigChildPath.clothesColor));
		return Profession.valueOf(profession);
	}
	
	public List<String> getChildren(Player player) {
		return playerCfg.getStringList(ConfigPlayerPath.toPath(player.getUniqueId(), ConfigPlayerPath.children)); 
	}
	
	public List<String> getAllChildren() {
		return ChildrenCfg.getStringList("");
	}
	
	public boolean shouldSimulatePreg() {
		return mainCfg.getBoolean(ConfigMainPath.toPath(ConfigMainPath.PREGNANCY));
	}
	
	//testers
	public boolean contains(Player player) {
		return manager.playerCfg.contains(ConfigPlayerPath.toPath(player.getUniqueId(), ConfigPlayerPath.player));
	}
	
	public boolean contains(UUID child) {
		return manager.childrenCfg.contains(ConfigChildPath.toPath(child, ConfigChildPath.child));
	}
	
	//remove 
	public void remove(UUID child) {
		manager.childrenCfg.set(ConfigChildPath.toPath(child, ConfigChildPath.child), null);
	}
	
	//items
	private final String materialPath = ".item.material";
	private final String amountPath = ".item.amount";
	private final String durabliltyPath = ".item.durablilty";
	//itemMeta
	private final String DisplayName = ".item.meta.displayname";
	private final String Enchants = ".item.meta.enchantments";
	private final String lorePath = ".item.meta.lore";
	
	//private methods
	private void setItemStack(FileConfiguration config, String path, ItemStack item) {
		
		//convert itemstack to understandable variables
		//itemstack properties
		String type = item.getType().toString();
		int amount = item.getAmount();
		//short durablilty = item.getDurability();
		//short durablilty = item.
		
		//meta data
		ItemMeta meta = item.getItemMeta();
		int durablilty = ((Damageable) item).getDamage();
		
		if(meta != null) {
			String displayname = meta.getDisplayName();
			Map<Enchantment, Integer> enchantments = meta.getEnchants();
			boolean isEmpty = true;
			
			List<String> toWrite = new ArrayList<String>();
			for(Enchantment e : enchantments.keySet()) {
				int lvl = enchantments.get(e);
				//toWrite.add(e.getName() + ":" + lvl);
				toWrite.add(e.getKey().getKey() + ":" + lvl);
			}
			
			List<String> lore = meta.getLore();
			
			config.set(path + DisplayName, displayname);
			if(!isEmpty)
				config.set(path + Enchants, toWrite);
			else
				config.set(path + Enchants, ConfigValue.NULL.toString());
			config.set(path + lorePath, lore);
		}
		
		config.set(path + materialPath, type);
		config.set(path + amountPath, amount);
		config.set(path + durabliltyPath, durablilty);
	}
	
	private ItemStack getItemStack(FileConfiguration config, String path) {		
		
		Material type = Material.valueOf(config.getString(path + materialPath));
		int amount = config.getInt(path + amountPath);
		int durablity = Short.valueOf(config.getString(path + durabliltyPath));
		
		//ItemStack item = new ItemStack(type, amount, durablity);
		ItemStack item = new ItemStack(type, amount);
		ItemMeta meta = item.getItemMeta();
		((Damageable) item).setDamage(durablity);
		
		//meta
		String display = config.getString(path + DisplayName);
		
		if(!config.getString(path + Enchants).equalsIgnoreCase(ConfigValue.NULL.toString())) {
//			@SuppressWarnings("unchecked")
//			Map<Enchantment, Integer> enchants = ((Map<Enchantment, Integer>) config.getMapList(path + Enchants));
//			item.addEnchantments(enchants);
			
			List<String> enchants = config.getStringList(path + Enchants);
			for(String value : enchants) {
				String[] tied = value.split(":", 2);
				//item.addEnchantment(Enchantment.getByName(tied[0]), new Integer(tied[1]));
				item.addEnchantment(Enchantment.getByKey(NamespacedKey.minecraft(tied[0])), new Integer(tied[1]));
			}
			
		}
		
		List<String> lore = config.getStringList(path + lorePath);
		
		meta.setDisplayName(display);
		meta.setLore(lore);
		return item;
	}
	
	public void saveAll() {
		manager.saveChildren();
		manager.savePlayer();
	}
	
}
