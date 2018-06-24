package com.orange.plump.OrangeBot;

import java.util.ArrayList;
import java.util.List;

import javax.security.auth.login.LoginException;

import com.orange.plump.OrangeBot.listeners.JoinAndLeaveListener;
import com.orange.plump.OrangeBot.listeners.MessageListener;

import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.entities.TextChannel;

public class App {
	public static JDA jda;
	private static final String token = "TOKEN";
	public static List<String> commands = new ArrayList<String>();
	public static final String commandPrefix = "~";
	public static TextChannel joinandLeaveChannel;
	
    public static void main(String[] args) {
    	setupHelp();
    	
    	try {
			jda = new JDABuilder(AccountType.BOT).setToken(token).buildBlocking();
		} catch (LoginException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	BotPresence presence = new BotPresence();
    	presence.start();
    	
    	for (TextChannel chan : jda.getTextChannels()) {
    		if (chan.getName().equalsIgnoreCase("welcome")) {
    			joinandLeaveChannel = chan;
    			break;
    		}
    	}

    	jda.addEventListener(new MessageListener());
    	jda.addEventListener(new JoinAndLeaveListener());
    }
    
    
    public static void setupHelp() {
    	commands.add("**Command Prefix '~'**\n");
    	commands.add("**announce** *[TextChannelName] [Message]* - Makes Bot Say Something In Given Text Channel *Administrator Only*");
    	commands.add("**8ball** *[Question]* - An 8Ball command");
    	commands.add("**help** - Shows This Message");
    }
}
