package babyCraft.Bukkit.Someoneawesome.java.enums;

import java.util.*;

public enum ConfigChildPath {
	
	//path values
	parent1_UUID {
		public String toString() {
			return ".parents.parent1.playeruuid";
		}
	},
	parent1_gender {
		public String toString() {
			return ".parents.parent1.gender";
		}
	},
	parent2_UUID {
		public String toString() {
			return ".parents.parent2.playeruuid";
		}
	},
	parent2_gender {
		public String toString() {
			return ".parents.parent2.gender";
		}
	},
	armor_head {
		public String toString() {
			return ".armor.head";
		}
	},
	armor_body {
		public String toString() {
			return ".armor.body";
		}
	},
	armor_legs {
		public String toString() {
			return ".armor.legs";
		}
	},
	armor_feet {
		public String toString() {
			return ".armor.feet";
		}
	},
	gender {
		public String toString() {
			return ".gender";
		}
	},
	name {
		public String toString() {
			return ".name";
		}
	},
	clothesColor {
		public String toString() {
			return ".color";
		}
	},
	home_world {
		public String toString(){
			return ".home.world";
		}
	},
	home_x {
		public String toString() {
			return ".home.x";
		}		
	},
	home_y {
		public String toString() {
			return ".home.y";
		}
	},
	home_z {
		public String toString() {
			return ".home.z";
		}
	},
	child {
		public String toString() {
			return "";
		}
	};
	
	public static String toPath(UUID uuid, ConfigChildPath path) {
		return uuid.toString() + path.toString();
	}
	
}
