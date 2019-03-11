package babyCraft.Bukkit.Someoneawesome.java.GUIMenus;

import java.util.*;

import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.*;

import babyCraft.Bukkit.Someoneawesome.*;
import babyCraft.Bukkit.Someoneawesome.java.Managers.childSpawnManagers.*;
import babyCraft.Bukkit.Someoneawesome.java.enums.*;

public class Creator {
	public static HashMap<UUID, Creator> runningCreators;	//requesting parent, creator
	public static ArrayList<UUID> pending;					//requesting parent
	public static HashMap<UUID, UUID> pendingRequests;		//sent parent, requesting parent
	
	private Babycraft plugin = Babycraft.getPlugin(Babycraft.class);
	
	//menu layout
	//  gender   dyes
	//  setname  nametag
	//  done     em
	
	private String gender;
	public String getGender() {if(gender.equals("male")) return "boy"; else return  "girl";}
	
	private String name;
	public String getName() {return name;}
	
	private Player parent1;
	private Player Parent2;
	public Player getParent1() {return parent1;}
	public Player getParent2() {return Parent2;}
	public boolean doNotClose = false;

	//with partner
	public void openCreator(Player player, Player Partner) {
		
		parent1 = player;
		Parent2 = Partner;
		
		//0 = male
		//1 = female
		Random random = new Random();
		int gender_int = random.nextInt(2);
		
		//gender selection
		if(gender_int == 0) 
			gender = "male";
		else 
			gender = "female";
			
		//choosing starting name
		String[] namesBoy = new String[] {"Noah", "Liam", "William", "Mason", "James","Benjamin", "Jacob", "Michael", "Elijah", "Ethan"};
		String[] namesGirl = new String[] {"Emma", "Olivia", "Ava", "Sophia", "Isabella","Mia", "Charlotte", "Abigail", "Emily", "Harper"};
		
		int name = random.nextInt(9);
		if(gender.equalsIgnoreCase("male"))
			this.name = namesBoy[name];
		else
			this.name = namesGirl[name];
		
		openMenu(player, gender, this.name);
	}
	
	private void openMenu(Player player, String gender, String name) {
		Inventory inv = plugin.getServer().createInventory(null, 9, ChatColor.GREEN + "Have your baby");
		//sets gender
		ItemStack genderItem;
		if(gender.equalsIgnoreCase("male")) 
			genderItem = getItem(Material.LIGHT_BLUE_DYE, "Its a boy! click to change", (short) 0, "gender");
		else 
			genderItem = getItem(Material.PINK_DYE, "Its a girl! click to change", (short) 0, "gender");
			
		//item stack for name
		ItemStack nameItem = getItem(Material.NAME_TAG, this.name + " click to change the name", "name");
		
		//setting done
		ItemStack doneItem = getItem(Material.EMERALD_BLOCK, "Done", "done");
		
		inv.setItem(0, genderItem);
		inv.setItem(1, nameItem);
		inv.setItem(8, doneItem);
		
		player.openInventory(inv);
	}
	
	public void refresh() {
		if(doNotClose) {
			doNotClose = false;
			parent1.closeInventory();
			openMenu(parent1, gender, name);
			doNotClose = true;
		} else {
			parent1.closeInventory();
			openMenu(parent1, gender, name);
		}
		//doNotClose = true;
	}
	
	private ItemStack getItem(Material mat, String itemName,Short damage, String lore) {
		ItemStack item = new ItemStack(mat, 1);
		ItemMeta item_meta = item.getItemMeta();
		item_meta.setDisplayName(itemName);
		ArrayList<String> list = new ArrayList<>();
		list.add(lore);
		item_meta.setLore(list);
		item.setItemMeta(item_meta);
		return item;
	}
	
	private ItemStack getItem(Material mat, String itemName, String lore) {
		return getItem(mat, itemName,(short) 0, lore);
	}
	
	public void setName(String name) {
		this.name = name;
		pending.remove(parent1.getUniqueId());
		openMenu(parent1, gender, name);
		doNotClose = true;
	}
	
	public void requestName() {
		doNotClose = false;
		pending.add(parent1.getUniqueId());
		parent1.closeInventory();
	}
	
	public void toggleGender() {
		if(gender.equalsIgnoreCase("male"))
			gender = "female";
		else
			gender = "male";
	}
	
	public void finish() {
		doNotClose = false;
		parent1.closeInventory();
		Child.newChild(name, parent1, Parent2, gender);
		
		if(gender.equalsIgnoreCase(Gender.male.toString()))
			gender = "boy";
		else
			gender = "girl";
		
		if(!parent1.getDisplayName().equals(Parent2.getDisplayName())){
		Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&6&l" + parent1.getDisplayName() + "&a&l and &6&l" + Parent2.getDisplayName()
        																+ "&a&l had gotten a baby " + gender + " named&1 " + name));
		} else {
			Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&6&l" + parent1.getDisplayName() + "&a&l had adopted a baby " + gender + " named&1 " + name));	
		}
		terminate();
	}
	
	public void terminate() {
		runningCreators.remove(parent1.getUniqueId());
	}
}
