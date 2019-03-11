package babyCraft.Bukkit.Someoneawesome.java.ai;

import java.util.*;

import org.bukkit.*;

import babyCraft.Bukkit.Someoneawesome.java.Managers.childSpawnManagers.*;
import babyCraft.Bukkit.Someoneawesome.java.enums.*;

public class ChatBot {
	private static final int RADIUS = 15;
	
	//used in msgs for chat
	//used for determining parents' genders
	private static final String PARENTGENDER = "PGENDER";
	private static final String OPPOSITEPARENTGENDER = "OPPPGENDER";
	
	//gender of opposite gender of child but give em a lover name ex.Boyfriend etc
	private static final String OWNLOVER = "LOVER";
	private String OwnLover;
	
	//gemder of own child and opposite ex. boy, girl
	private static final String OWNGENDER = "OWN";
	private static final String OWNOPPOSITEGENDER = "OPPOWN";
	private String Own;
	private String OpOwn;
	
	//gender pronouns of opposite gender of child ex. he, she (SUB, subjective), him, her (OBJ, objective)
	private static final String OWNOPPOSITEGENDERPROSUB = "OPPOWNPROSUB";
	private static final String OWNOPPOSITEGENDERPROOBJ = "OPPOWNPROOBJ";
	private String OpOwnSub;
	private String OpOwnObj;

	private String genderCode;
	private Child child;
	
	public ChatBot(Gender gender, Child child) {
		this.child = child;
		if(gender.equals(Gender.male))
			genderCode = "&b";
		else
			genderCode = "&d";
		
		if(child.getGender().equals(Gender.male)) {
			Own = "boy";
			OpOwn = "girl";
			OpOwnSub = "she";
			OpOwnObj = "her";
			OwnLover = "Girlfriend";
		} else {
			Own = "girl";
			OpOwn = "boy";
			OpOwnSub = "he";
			OpOwnObj = "him";
			OwnLover = "Boyfriend";
		}
	}

	public void interact(Gender gender) {
		String message = ReactionMessages.NULL.nextMsg();
		String parent = "[Gender not found]";
		String parentOP = "[Gender Not Found]";
		boolean isPositive = new Random().nextBoolean();
		boolean easter = false;
		
		int random = new Random().nextInt(25);
		
		if(random == 24)
			easter = true;
		else if (random >= 10)
			isPositive = true;
		else
			isPositive = false;
		
		
		if(gender.equals(Gender.male)) {
			parent = "Daddy";
			parentOP = "Mommy";
		}			
		else if(gender.equals(Gender.female)) {
			parent = "Mommy";
			parentOP = "Daddy";
		}			
		else {
			parent = "person";
			parentOP = "person";
		}
		
		if(isPositive) 
			message = ReactionMessages.Chat_pos.nextMsg();
		else
			message = ReactionMessages.Chat_neg.nextMsg();
		
		if(easter) {
			message = ReactionMessages.EASTEREGG.nextMsg();
		}
		
		message = replacePattern(OWNOPPOSITEGENDERPROSUB, OpOwnSub, message);
		message = replacePattern(OWNOPPOSITEGENDERPROOBJ, OpOwnObj, message);
		message = replacePattern(OWNOPPOSITEGENDER, OpOwn, message);
		message = replacePattern(OWNGENDER, Own, message);
		message = replacePattern(OPPOSITEPARENTGENDER, parentOP, message);
		message = replacePattern(PARENTGENDER, parent, message);
		message = replacePattern(OWNLOVER, OwnLover, message);
		
		say(message);
		
	}
	
	public void reactTo(Action action, Gender Actioner_Gender) {
		
		String message = "error 404: message not Found";
		String parent = "[Gender not found]";
		String parentOP = "[Gender not found]";
		boolean isPositive = false;
		
		int random = new Random().nextInt(100);

		if (random >= 25)
			isPositive = true;
		else
			isPositive = false;
		
		
		ReactionMessages msg = ReactionMessages.NULL;
		
		if(Actioner_Gender == null)
			parent = "person";
		
		if(Actioner_Gender.equals(Gender.male)) {
			parent = "Daddy";
			parentOP = "Mommy";
		}
		else {
			parent = "Mommy";
			parentOP = "Daddy";
		}
			
		
		// hug reaction
		if(action.equals(Action.HUGGED)) {
			if(isPositive) 
				msg = ReactionMessages.Hug_Pos;
			else
				msg = ReactionMessages.Hug_Neg;
		}
		
		// joked reaction
		if(action.equals(Action.JOKED)) {
			if(isPositive)
				msg = ReactionMessages.Joke_Pos;
			else
				msg = ReactionMessages.Joke_Neg;
		}
		
		// kiss reaction
		if(action.equals(Action.KISSED)) {
			if(isPositive)
				msg = ReactionMessages.Kiss_Pos;
			else
				msg = ReactionMessages.Kiss_Neg;
		}
		
		if(action.equals(Action.CHANGED)) {
			if(isPositive)
				msg = ReactionMessages.change_pos;
			else
				msg = ReactionMessages.change_neg;
		}
		
		message = msg.nextMsg();
		message = replacePattern(OWNOPPOSITEGENDERPROSUB, OpOwnSub, message);
		message = replacePattern(OWNOPPOSITEGENDERPROOBJ, OpOwnObj, message);
		message = replacePattern(OWNOPPOSITEGENDER, OpOwn, message);
		message = replacePattern(OWNGENDER, Own, message);
		message = replacePattern(OPPOSITEPARENTGENDER, parentOP, message);
		message = replacePattern(PARENTGENDER, parent, message);
		message = replacePattern(OWNLOVER, OwnLover, message);
		
		say(message);
	}
	
	// attacked reaction
	public void attackedReact(boolean isparent, Gender gender) {
		
		String adressed = "[Error 404: Adress title cannot be found]";
		
		if(isparent) {
			if(gender.equals(Gender.male))
				adressed = "Daddy";
			else
				adressed = "Mommy";
		} else {
			if(gender.equals(Gender.male))
				adressed = "Mr";
			else 
				adressed = "Ms";
		}
		
		String message = "Oww, why did you do that " + adressed + "!";
		
		say(message);
	}
	
	public void follow(Gender gender) {
		String message = "error 404: message not Found";
		String parent = "[Gender not found]";
		
		if(gender == null)
			parent = "person";
		
		if(gender.equals(Gender.male))
			parent = "Daddy";
		else 
			parent = "Mommy";
		
		message = "Ok i will follow you " + parent;
		say(message);
	}
	
	public void stay(Gender gender) {
		String message = "error 404: message not Found";
		String parent = "[Gender not found]";
		
		if(gender == null)
			parent = "person";
		
		if(gender.equals(Gender.male))
			parent = "Daddy";
		else 
			parent = "Mommy";
		
		message = "Ok " + parent + " I will stay here";
		say(message);
	}
	
	public void roam(Gender gender) {
		String message = "error 404: message not Found";
		String parent = "[Gender not found]";
		
		if(gender == null)
			parent = "person";
		
		if(gender.equals(Gender.male))
			parent = "Daddy";
		else 
			parent = "Mommy";
		
		message = "yay lets go play PGENDER!";
		message.replaceAll(PARENTGENDER, parent);
		say(message);
	}
	
	// say cmd
	private void say(String msg) {
		child.sayline(ChatColor.translateAlternateColorCodes('&', genderCode + "<" + child.getName() + "> &6" + msg), RADIUS);
	}
	
	private static String replacePattern(String pattern, String replaceWith, String input) {
		String newmsg = input.replaceAll(pattern, replaceWith);
		return newmsg;
	}
	
}
