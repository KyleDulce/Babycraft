package babyCraft.Bukkit.Someoneawesome.java.enums;

import java.util.*;

public enum ReactionMessages {
	Hug_Pos(new String[] {
		"*hugs back* I love you too PGENDER!",
		"Thank you PGENDER, Love you too!",
		"PGENDER thank youu!"
	}),
	
	Kiss_Pos(new String[] {
		"*kisses back* I love you too PGENDER!",
		"*smiles* Love you PGENDER!",
		"PGENDER, love youuu!"
	}),
	
	Joke_Pos(new String[] {
		"hahaha, You are very funny PGENDER!",
		"haha i get it!",
		"Thats so funny PGENDER!"
	}),
	
	Hug_Neg(new String[] {
		"No no no, let me go PGENDER!",
		"Stop embarrassing me PGENDER!",
		"Why again PGENDER"
	}),
	
	Kiss_Neg(new String[] {
		"PGENDER noo you are embarrassing me!",
		"PGENDER no please PGENDER noo do not *pushes away* no!",
		"You always do that in front of my friends PGENDER stop it"
	}),
	
	Joke_Neg(new String[] {
		"You tell me that one all the time PGENDER, its not funny anymore",
		"ughh, that one again PGENDER?",
		"You are not funny PGENDER"
	}),
	
	Chat_pos(new String[] {
		"I love your stories PGENDER",
		"Love you!",
		"Thank you PGENDER",
		"Can we go now PGENDER, Pleaseeeede",
		"You are the best PGENDER",
		"Can i go play with the other kids PGENDER?",
		"Can we play?",
		"Hi PGENDER",
		"PGENDER, can we have a pet?",
		"PGENDER i want a doggy!",
		"Bye PGENDER",
		"I'm Hungry",
		"I'm Thirsty",
		"Ok PGENDER"
	}), 
	
	Chat_neg(new String[] {
		"PGENDER you told me that a million timesss",
		"I am not a baby PGENDER",
		"Whaat, no please PGENDER, i do not want to, no pleasee",
		"NOO! you always tell me to do that PGENDER, i do not want to please",
		"I'm bored",
		"I hate You",
		"PGENDER, you are mean, i want to play!",
		"*cries* no no no, i want this!"
	}),
	
	change_pos(new String[] {
		"Yay",
		"I look different now PGENDER",
		"This looks good",
		"I love these clothes! I will brag about it to my friends!",
		"Thanks PGENDER"
	}),
	
	change_neg(new String[] {
		"really, these clothes are sooo last year",
		"PGENDER, again, i don't like to wear this!",
		"PGENDER, you have no sense of fashion",
		"i don't like it",
		"*rolls eyes* really?"
	}),
	
	EASTEREGG(new String[] {
			"PGENDER, I feel weird around this OPPOWN, its like OPPOWNPROSUB makes me happy but i don't know why... WHAT! no i do not love them, PGENDER thats gross!",
			"PGENDER, I think i like this one OPPOWN",
			"PGENDER, I want to get married to my LOVER right now!",
			"PGENDER, are you cheating on OPPPGENDER",
			"PGENDER, I have a LOVER you know?",
			"WHAT! no i will never have a LOVER, and i will never get married",
			"PGENDER, why do dogs sniff other dog's butts?",
			"PGENDER, how are babies made?"
	}),
	
	NULL(new String[] {
			"[error 404: message not found]"
	});
	
	private String[] messages;
	
	private ReactionMessages(String[] args) {
		messages = args;
	}
	
	public String nextMsg() {
		return messages[new Random().nextInt(messages.length)];
	}
}
