package com.orange.plump.OrangeBot;

import java.util.Random;

import net.dv8tion.jda.core.OnlineStatus;
import net.dv8tion.jda.core.entities.Game;

public class BotPresence extends Thread {
	
	public int PresenceId;
	
	public void run() {
		repeatingCycle();
	}
	
	private void repeatingCycle() {
		Random ran = new Random();
		this.PresenceId = ran.nextInt(5);
		
		if (PresenceId == 0) {
			App.jda.getPresence().setPresence(OnlineStatus.ONLINE, Game.watching("Noobs Get Rekt"));
		} else if (PresenceId == 1) {
			App.jda.getPresence().setPresence(OnlineStatus.ONLINE, Game.listening("To Rod Spamming"));
		} else if (PresenceId == 2) {
			App.jda.getPresence().setPresence(OnlineStatus.ONLINE, Game.playing("Minecraft"));
		} else if (PresenceId == 3) {
			App.jda.getPresence().setPresence(OnlineStatus.ONLINE, Game.watching("The Guild Grow"));
		} else if (PresenceId == 4) {
			App.jda.getPresence().setPresence(OnlineStatus.ONLINE, Game.playing("The Pit"));
		} else if (PresenceId == 5) {
			App.jda.getPresence().setPresence(OnlineStatus.ONLINE, Game.watching("My FPS"));
		}
		try {
			sleep(new Random().nextInt(60000) + 60000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		repeatingCycle();
	}
}
