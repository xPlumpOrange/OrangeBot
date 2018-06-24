 package com.orange.plump.OrangeBot.listeners;


import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import com.orange.plump.OrangeBot.App;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.MessageEmbed;
import net.dv8tion.jda.core.entities.PrivateChannel;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class MessageListener extends ListenerAdapter {
	private String help = "";
	private List<String> answerArray;
	
	public MessageListener() {
		for (String command : App.commands) {
			help += command + "\n\n";
		}
		
		File f = new File("answer.txt");
		if (!f.exists()) answerArray = new ArrayList<String>();
		else {
			try {
				answerArray = Files.readAllLines(Paths.get(f.getPath()));
			} catch (IOException e) {
				
			}
		}
	}
	
	@Override
    public void onMessageReceived(MessageReceivedEvent event) {
		
		 Message msg = event.getMessage();
		 String rawMsg = msg.getContentRaw();
         MessageChannel msgChannel = event.getChannel();
         User msgAuthor = event.getAuthor();
         
         if (msgAuthor.isBot()) return;
         
         //**
         //**
         if (rawMsg.startsWith(App.commandPrefix + "announce")) {
        	 
        	if (msgChannel instanceof PrivateChannel) {
        		say("You Can Only Use This Command On A Server With The Bot", msgChannel);
        		return;
        	}
        	
        	for (Role r : event.getMember().getRoles()) {
        		if (r.getName().equalsIgnoreCase("Member") && !event.getMember().getNickname().equalsIgnoreCase("Plump")) {
        			try {
        	     		msg.delete().queue();
        	     	} catch (Exception e) {
        	     		//System.out.println("Couldnt Delete Message");
        	     	}
        			say(msgAuthor.getAsMention() + " You Don't Have Permission To Use This Command", msgChannel, 5);
        			return;
        		} else break;
        	}
        	
        	try {
     			msg.delete().queue();
     		} catch (Exception e) {
     			//System.out.println("Couldnt Delete Message");
     		}
        	
        	announce(msg, rawMsg, msgChannel);
        //**
        //**
        } else if (rawMsg.startsWith(App.commandPrefix + "help")) {
        	 
        	try {
     			msg.delete().queue();
     		} catch (Exception e) {
     			//System.out.println("Couldnt Delete Message");
     		} 
        	
        	help(msg, msgChannel, msgAuthor);
        //**
        //**
        } else if (rawMsg.startsWith(App.commandPrefix + "8ball")) eightBall(msgChannel, msgAuthor);
        //**
        //**
        else if (rawMsg.startsWith(App.commandPrefix + "ping")) {
        	 
        	try {
      			msg.delete().queue();
      		} catch (Exception e) {
      			//System.out.println("Couldnt Delete Message");
      		} 
        	
        	say("Pong", msgChannel, 5);
        //**
        //**
        } else if (rawMsg.startsWith(App.commandPrefix + "pong")) {
        	 
        	try {
        		 msg.delete().queue();
      		} catch (Exception e) {
      			//System.out.println("Couldnt Delete Message");
      		} 
        	
        	say("Ping", msgChannel, 5);
        	
        //**
        //**
        } else answerMachine(msgChannel, msg, msgAuthor);
	}
	
	/*
	 * 
	 * SAY
	 * 
	 */
	
	public void say(String s, MessageChannel mc) {
		mc.sendMessage(s).queue();
	}
	
	public void say(Message msg, MessageChannel mc) {
		mc.sendMessage(msg).queue();
	}
	
	public void say(MessageEmbed msgEm, MessageChannel mc) {
		mc.sendMessage(msgEm).queue();
	}
	
	public void say(String s, MessageChannel mc, long sec) {
		Message msg = mc.sendMessage(s).complete();
		msg.delete().queueAfter(sec, TimeUnit.SECONDS);
	}
	
	/*
	 * 
	 * COMMANDS
	 * 
	 */
	
	public void announce(Message msg, String rawMsg, MessageChannel msgChan) {
		String[] msgArray = rawMsg.replace(App.commandPrefix + "announce ", "").split(" ");
		if (msgArray == null) {
			say("Invalid Args, Do !help For Correct Usage", msgChan, 8);
		}
   	 	String newMsg = "";
   	 	int i = 0;
   	 	for (String s : msgArray) {
   	 		if (i != 0) newMsg += s + " ";
   	 		i++;
   		}
   	 	try {
   	 		if (App.jda.getTextChannelsByName(msgArray[0], true).get(0) != null) say(newMsg, App.jda.getTextChannelsByName(msgArray[0], true).get(0));
   	 	} catch (Exception e) {
   	 		
   	 	}
   	 	return;
	}
	
	private void help(Message msg, MessageChannel msgChannel, User msgAuthor) {
		EmbedBuilder embBuild = new EmbedBuilder();
		embBuild.setTitle("Command Help");
		embBuild.setThumbnail("https://image.ibb.co/gpm2Ty/help.png");
		embBuild.setDescription(this.help);
		embBuild.setColor(Color.ORANGE);
		say(embBuild.build(), msgAuthor.openPrivateChannel().complete());
		if (!(msgChannel instanceof PrivateChannel) && msgChannel instanceof TextChannel) {
			say(msgAuthor.getAsMention() + " Help Was Sent To DM's", msgChannel, 8);
		}
		
	}
	
	private void eightBall(MessageChannel msgChannel, User msgAuthor) {
		Random ran = new Random();
		int choice = ran.nextInt(9);
		switch (choice) {
		case (1): say (msgAuthor.getAsMention() + " Yes", msgChannel); break;
		case (2): say (msgAuthor.getAsMention() + " No", msgChannel); break;
		case (3): say (msgAuthor.getAsMention() + " Most Likely", msgChannel); break;
		case (4): say (msgAuthor.getAsMention() + " Proabably Not", msgChannel); break;
		case (5): say (msgAuthor.getAsMention() + " Most Defenitly", msgChannel); break;
		case (6): say (msgAuthor.getAsMention() + " Not Confident", msgChannel); break;
		case (7): say (msgAuthor.getAsMention() + " High Chance", msgChannel); break;
		case (8): say (msgAuthor.getAsMention() + " Please Ask Again", msgChannel); break;
		case (9): say (msgAuthor.getAsMention() + " Yes", msgChannel); break;
		case (0): say (msgAuthor.getAsMention() + " No", msgChannel); break;
		}
	}
	
	private void answerMachine(MessageChannel msgChannel, Message msg, User msgAuthor) {
		for (String ans: answerArray) {
			String question = ans.split(":")[0];
			String answer = ans.split(":")[1].replace("{USER}", msgAuthor.getAsMention());
			
			if (msg.getContentRaw().toLowerCase().contains(question.toLowerCase())) {
				say(answer, msgChannel); 
			}
		}
	}

}
