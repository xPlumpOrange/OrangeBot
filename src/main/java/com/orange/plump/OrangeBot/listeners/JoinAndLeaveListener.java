package com.orange.plump.OrangeBot.listeners;

import com.orange.plump.OrangeBot.App;

import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.core.events.guild.member.GuildMemberLeaveEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class JoinAndLeaveListener extends ListenerAdapter {

	@Override
	public void onGuildMemberJoin(GuildMemberJoinEvent event) {
			User user = event.getUser();
			say("Welcome " + user.getAsMention() + ", Make Sure To Read The Rules! *Do " + App.commandPrefix + "help For More Info On My Commands.*", App.joinandLeaveChannel);
	}
	
	@Override
	public void onGuildMemberLeave(GuildMemberLeaveEvent event) {
			User user = event.getUser();
			say("Bye " + user.getAsMention() + ", You'll Be Dearly Missed.", App.joinandLeaveChannel);
	}
	
	public void say(String s, TextChannel mc) {
		mc.sendMessage(s).queue();
	}
	
}
