package babyCraft.Bukkit.Someoneawesome.java.Managers.childSpawnManagers;

import java.util.*;

import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.potion.*;

import babyCraft.Bukkit.Someoneawesome.*;
import babyCraft.Bukkit.Someoneawesome.java.GUIMenus.*;
import babyCraft.Bukkit.Someoneawesome.java.Managers.*;

public class PregnancyEffect implements Runnable {
	
	private static ArrayList<Player> Pregnant;
	private static ConfigIO config;

	@Override
	public void run() {
		for(Player p : Pregnant) {
			int stat = config.getPregStat(p);
			if(stat == 19) {
				Creator.runningCreators.put(p.getUniqueId(), new Creator());
				Creator.runningCreators.get(p.getUniqueId()).openCreator(p, config.getPregWith(p));
				Babycraft.getInstance().console.sendMessage(p.getName() + " is giving a child");
				config.setPregNull(p);
				Pregnant.remove(Pregnant.indexOf(p));
			} else {
				config.setPregStat(p);
				//PotionEffect e = new PotionEffect(type, duration, amplifier)
				Effects ef = Effects.random();
				PotionEffect e = new PotionEffect(ef.getEffect(), ef.dur, ef.amp);
				p.addPotionEffect(e);
			}
		}
	}
	
	private static int id;
	
	public static void init() {
		config = Babycraft.getInstance().configIO;
		Pregnant = (ArrayList<Player>) config.getAllPlayerPreg();
	}
	
	public static void addPregnancy(Player pregnant, Player partner) {
		Pregnant.add(pregnant);
		config.setPregWith(pregnant, partner);
		config.setPregStat(pregnant, 1);
	}
	
	public static void schedule() {
		id = Bukkit.getScheduler().scheduleSyncRepeatingTask(Babycraft.getInstance(), new PregnancyEffect(), 12000, 12000);
	}
	
	public static void cancelTask() {
		Bukkit.getScheduler().cancelTask(id);
	}
		
	public enum Effects {
		NAUSEA(PotionEffectType.CONFUSION, "You feel sick", 30, 5, 10),
		SLOWNESS(PotionEffectType.SLOW, "You have some pains in your back", 30, 2, 7),
		MINING_FATIGUE(PotionEffectType.SLOW_DIGGING, "You feel like you cannot work", 15, 2, 3),
		WEAKNESS(PotionEffectType.WEAKNESS, "You feel Weak", 15, 2, 3),
		HUNGER(PotionEffectType.HUNGER, "You feel Hungry", 30, 1, 3),
		POISON(PotionEffectType.POISON, "You feel pain", 5, 2, 3),
		GLOWING(PotionEffectType.GLOWING, "You feel well", 30, 1, 1)
		;
		PotionEffectType e;
		String msg;
		int dur;
		int amp;
		int r;
		private Effects(PotionEffectType e, String msg, int dur, int amp, int randomAmp) {
			this.e = e;
			this.msg = msg;
			this.dur = dur;
			this.amp = amp;
			r = randomAmp;
		} 
		public PotionEffectType getEffect() {
			return e;
		}
		
		public String getMessage() {
			return msg;
		}
		
		public float getDuration() {
			return dur;
		}
		
		public float getAmp() {
			return amp;
		}
		
		private static final List<Effects> VALUES = getE();
		private static final int SIZE = VALUES.size();
		private static final Random RANDOM = new Random();
		
		private static List<Effects> getE() {
			List<Effects> l = Arrays.asList(values());
			List<Effects> l2 = new ArrayList<>();
			for(Effects e: l) {
				for(int x = 0; x < e.r; x++) {
					l2.add(e);
				}
			}
			return Collections.unmodifiableList(l2);
		}

		public static Effects random()  {
			return VALUES.get(RANDOM.nextInt(SIZE));
		}
		
	}
	
}
