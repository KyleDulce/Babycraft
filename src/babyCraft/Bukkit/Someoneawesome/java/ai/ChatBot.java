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
	
	//gender of own child and opposite ex. boy, girl
	private static final String OWNGENDER = "OWN";
	private static final String OWNOPPOSITEGENDER = "OPPOWN";
	private String Own;
	private String OpOwn;
	
	//gender pronouns of opposite gender of child ex. he, she (SUB, subjective), him, her (OBJ, objective)
	private static final String OWNOPPOSITEGENDERPROSUB = "OPPOWNPROSUB";
	private static final String OWNOPPOSITEGENDERPROOBJ = "OPPOWNPROOBJ";
	private String OpOwnSub;
	private String OpOwnObj;

	//color of name in chat
	private String genderCode;
	//child instance is connected to
	private Child child;
	
	//constructor
	public ChatBot(Gender gender, Child child) {
		//sets child instance
		this.child = child;
		
		//sets color for its gender, sets nouns
		if(child.getGender().equals(Gender.male)) {
			//if child is male
			Own = "boy";
			OpOwn = "girl";
			OpOwnSub = "she";
			OpOwnObj = "her";
			OwnLover = "Girlfriend";
			genderCode = "&b";
		} else {
			//if child is female
			Own = "girl";
			OpOwn = "boy";
			OpOwnSub = "he";
			OpOwnObj = "him";
			OwnLover = "Boyfriend";
			genderCode = "&d";
		}
	}

	//regular interaction message
	public void interact(Gender gender) {
		//set message to null (if message fails to be found)
		String message = ReactionMessages.NULL.nextMsg();
		//set gender of parent talking to
		String parent = "[Gender not found]";
		//set gender of the opposite parent
		String parentOP = "[Gender Not Found]";
		//is the message given positive
		boolean isPositive = new Random().nextBoolean();
		//is the message an easter egg
		boolean easter = false;
		
		//chooses if easteregg is taken
		int random = new Random().nextInt(22);
		
		//checks for easteregg or positive and negative message
		if(random == 21)
			easter = true;
		else if (random >= 10)
			isPositive = true;
		else
			isPositive = false;
		
		//checks for the parent talking to
		if(gender.equals(Gender.male)) {
			//if father talking
			parent = "Daddy";
			parentOP = "Mommy";
		}			
		else if(gender.equals(Gender.female)) {
			//if mother talking
			parent = "Mommy";
			parentOP = "Daddy";
		}			
		else {
			//if gender cannot be found
			parent = "person";
			parentOP = "person";
		}
		
		//generates message
		if(easter) {
			message = ReactionMessages.EASTEREGG.nextMsg();
		} else if(isPositive) 
			message = ReactionMessages.Chat_pos.nextMsg();
		else
			message = ReactionMessages.Chat_neg.nextMsg();
		
		//replaces placeholders with appropiate values
		message = replacePattern(OWNOPPOSITEGENDERPROSUB, OpOwnSub, message);
		message = replacePattern(OWNOPPOSITEGENDERPROOBJ, OpOwnObj, message);
		message = replacePattern(OWNOPPOSITEGENDER, OpOwn, message);
		message = replacePattern(OWNGENDER, Own, message);
		message = replacePattern(OPPOSITEPARENTGENDER, parentOP, message);
		message = replacePattern(PARENTGENDER, parent, message);
		message = replacePattern(OWNLOVER, OwnLover, message);
		
		//has child speak the message
		say(message);
	}
	
	//child reacts to action on them
	public void reactTo(Action action, Gender Actioner_Gender) {
		
		//sets message to null if not found
		String message = ReactionMessages.NULL.nextMsg();
		//checks for parents gender if not found
		String parent = "[Gender not found]";
		String parentOP = "[Gender not found]";
		//is the message positive
		boolean isPositive = false;
		
		//determines if message is positive
		int random = new Random().nextInt(100);

		//checks if message is positive
		if (random >= 25)
			isPositive = true;
		else
			isPositive = false;
		
		//the currently action to be taken
		ReactionMessages msg = ReactionMessages.NULL;
		
		//sets names for gender of actioner
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
		
		//replaces and sends appripiate message
		message = msg.nextMsg();
		message = replacePattern(OWNOPPOSITEGENDERPROSUB, OpOwnSub, message);
		message = replacePattern(OWNOPPOSITEGENDERPROOBJ, OpOwnObj, message);
		message = replacePattern(OWNOPPOSITEGENDER, OpOwn, message);
		message = replacePattern(OWNGENDER, Own, message);
		message = replacePattern(OPPOSITEPARENTGENDER, parentOP, message);
		message = replacePattern(PARENTGENDER, parent, message);
		message = replacePattern(OWNLOVER, OwnLover, message);
		
		//has child say the message
		say(message);
	}
	
	// attacked reaction
	public void attackedReact(boolean isparent, Gender gender) {
		
		//if the gender cannot be found
		String adressed = "[Error 404: Adress title cannot be found]";
		
		//sets adressed title
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
		
		//creates message
		String message = "Oww, why did you do that " + adressed + "!";
		
		//has child say the message
		say(message);
	}
	
	//message when child starts following parent
	public void follow(Gender gender) {
		//sets null messages
		String message = "error 404: message not Found";
		String parent = "[Gender not found]";
		
		//sets gender if found
		if(gender == null)
			parent = "person";
		
		if(gender.equals(Gender.male))
			parent = "Daddy";
		else 
			parent = "Mommy";
		
		//creates message and says
		message = "Ok i will follow you " + parent;
		say(message);
	}
	
	//message and child is told to stay
	public void stay(Gender gender) {
		//sets null messages
		String message = "error 404: message not Found";
		String parent = "[Gender not found]";
		
		//checks for gender of parent if found
		if(gender == null)
			parent = "person";
		
		if(gender.equals(Gender.male))
			parent = "Daddy";
		else 
			parent = "Mommy";
		
		//creates and send message
		message = "Ok " + parent + " I will stay here";
		say(message);
	}
	
	//message when child told it can roam
	public void roam(Gender gender) {
		//set null messages
		String message = "error 404: message not Found";
		String parent = "[Gender not found]";
		
		//set gender to parents
		if(gender == null)
			parent = "person";
		
		if(gender.equals(Gender.male))
			parent = "Daddy";
		else 
			parent = "Mommy";
		
		//creates and says message
		message = "yay lets go play PGENDER!";
		message.replaceAll(PARENTGENDER, parent);
		say(message);
	}
	
	// say cmd with colors
	private void say(String msg) {
		child.sayline(ChatColor.translateAlternateColorCodes('&', genderCode + "<" + child.getName() + "> &6" + msg), RADIUS);
	}
	
	private static String replacePattern(String pattern, String replaceWith, String input) {
		String newmsg = input.replaceAll(pattern, replaceWith);
		return newmsg;
	}
	
}
