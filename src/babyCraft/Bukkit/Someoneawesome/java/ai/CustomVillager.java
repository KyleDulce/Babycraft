package babyCraft.Bukkit.Someoneawesome.java.ai;

import java.lang.reflect.*;
import java.util.*;

import org.bukkit.*;
import org.bukkit.craftbukkit.v1_13_R1.*;
import org.bukkit.entity.*;

import net.minecraft.server.v1_13_R1.*;
import net.minecraft.server.v1_13_R1.Entity;

public class CustomVillager extends EntityVillager {

	public CustomVillager(org.bukkit.World world) {
		super(((CraftWorld)world).getHandle());
		
		
		
		Villager craftVillager = (Villager)this.getBukkitEntity();
		
		this.ageLocked = true;
		this.setHealth(100);
	}
	
	public void follow(Player player) {
		stopFollow();
		this.targetSelector.a(1, new PathfinderGoalFollowEntity((EntityInsentient) player, 10.0, 10.0f, 1.0f));
	}
	
	public void stopFollow() {
		clear();
	}
	
	public void clear() {
		List goalB = (List)getPrivateField("b", PathfinderGoalSelector.class, goalSelector); goalB.clear();
        List goalC = (List)getPrivateField("c", PathfinderGoalSelector.class, goalSelector); goalC.clear();
        List targetB = (List)getPrivateField("b", PathfinderGoalSelector.class, targetSelector); targetB.clear();
        List targetC = (List)getPrivateField("c", PathfinderGoalSelector.class, targetSelector); targetC.clear();
        add();
	}
	
	public void add() {
		 this.goalSelector.a(0, new PathfinderGoalFloat(this));
		 this.goalSelector.a(5, new PathfinderGoalRandomStroll(this, 1.0D));
		 this.goalSelector.a(6, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 8.0F));
	        this.goalSelector.a(6, new PathfinderGoalRandomLookaround(this));
	}
	
	 public static Object getPrivateField(String fieldName, Class clazz, Object object)
	    {
	        Field field;
	        Object o = null;

	        try
	        {
	            field = clazz.getDeclaredField(fieldName);

	            field.setAccessible(true);

	            o = field.get(object);
	        }
	        catch(NoSuchFieldException e)
	        {
	            e.printStackTrace();
	        }
	        catch(IllegalAccessException e)
	        {
	            e.printStackTrace();
	        }

	        return o;
	    }
	 
	 public enum EntityTypes
	 {
	     //NAME("Entity name", Entity ID, yourcustomclass.class);
	     CUSTOM_VILLAGER("Villager", 54, CustomVillager.class); //You can add as many as you want.

	     private EntityTypes(String name, int id, Class<? extends Entity> custom)
	     {
	         addToMaps(custom, name, id);
	     }

	   public static void spawnEntity(Entity entity, Location loc)
	    {
	      entity.setLocation(loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch());
	      ((CraftWorld)loc.getWorld()).getHandle().addEntity(entity);
	    }

	     private static void addToMaps(Class clazz, String name, int id)
	     {
	         //getPrivateField is the method from above.
	         //Remove the lines with // in front of them if you want to override default entities (You'd have to remove the default entity from the map first though).
	         ((Map)getPrivateField("c", net.minecraft.server.v1_13_R1.EntityTypes.class, null)).put(name, clazz);
	         ((Map)getPrivateField("d", net.minecraft.server.v1_13_R1.EntityTypes.class, null)).put(clazz, name);
	         //((Map)getPrivateField("e", net.minecraft.server.v1_7_R4.EntityTypes.class, null)).put(Integer.valueOf(id), clazz);
	         ((Map)getPrivateField("f", net.minecraft.server.v1_13_R1.EntityTypes.class, null)).put(clazz, Integer.valueOf(id));
	         //((Map)getPrivateField("g", net.minecraft.server.v1_7_R4.EntityTypes.class, null)).put(name, Integer.valueOf(id));
	     }
	 }


}
