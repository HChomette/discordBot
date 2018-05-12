package fr.endyron.leuceusseu.command.defaut;

import fr.endyron.leuceusseu.Main;
import fr.endyron.leuceusseu.command.Command;
import fr.endyron.leuceusseu.lcs.leagues.*;
import fr.endyron.leuceusseu.lcs.Match;
import fr.endyron.leuceusseu.lcs.NotInitializedException;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;

import java.util.ArrayList;
import java.util.Date;

/**
 * Basic command pack.
 */
public class BaseCommands {

	private Main main;

	public BaseCommands(Main main) {
		this.main = main;
	}

	@Command(name="stop", type= Command.ExecutorType.CONSOLE)
	private void stop(){
		main.setRunning(false);
	}

	@Command(name="info", type = Command.ExecutorType.USER)
	private void info(User user, MessageChannel channel){
		channel.sendMessage("Le bot Leuceusseu est un bot open source réalisé par Hector Chomette.").queue(); //Placeholder message
	}

	@Command(name="help", type = Command.ExecutorType.USER)
	private void help(User user, MessageChannel channel){
		channel.sendMessage("Format des commandes : \nProchain match : next[Code de région]"
		+" \nMatches du jour : day[Code de région] \nCode des régions : EU, NA, KR").queue();
	}

	//TODO : Faire une liste des classes league
	@Command(name="alllcs", type = Command.ExecutorType.CONSOLE)
	private void alllcs(User user, MessageChannel channel){
		eulcs(user, channel);
		nalcs(user, channel);
		krlck(user, channel);
	}

	@Command(name="eulcs", type = Command.ExecutorType.CONSOLE)
	private void eulcs(User user, MessageChannel channel){
		EULCS.getInstance().initialize();
		//schedule
	}

	@Command(name="nalcs", type = Command.ExecutorType.CONSOLE)
	private void nalcs(User user, MessageChannel channel){
		NALCS.getInstance().initialize();
		//schedule
	}

	@Command(name="krlck", type = Command.ExecutorType.CONSOLE)
	private void krlck(User user, MessageChannel channel){
		KRLCK.getInstance().initialize();
		//schedule
	}

	@Command(name="msi", type = Command.ExecutorType.CONSOLE)
	private void msi(User user, MessageChannel channel){
		MSI.getInstance().initialize();
		//schedule
	}


	@Command(name="nexteu", type = Command.ExecutorType.USER)
	private void nexteu(User user, MessageChannel channel) {
		try {
			Match next = EULCS.getInstance().getNextMatch();
			channel.sendMessage("Le prochain match est " + next.getTeam1() + " VS " + next.getTeam2() + " : " + next.getScheduledDate()).queue();
		} catch (NotInitializedException e){
			e.printStackTrace();
			channel.sendMessage("Les matchs n'ont pas été chargés par le bot. Réessayez plus tard.").queue();
		}
	}

	@Command(name="nextna", type = Command.ExecutorType.USER)
	private void nextna(User user, MessageChannel channel) {
		try {
			Match next = NALCS.getInstance().getNextMatch();
			channel.sendMessage("Le prochain match est " + next.getTeam1() + " VS " + next.getTeam2() + " : " + next.getScheduledDate()).queue();
		} catch (NotInitializedException e){
			e.printStackTrace();
			channel.sendMessage("Les matchs n'ont pas été chargés par le bot. Réessayez plus tard.").queue();
		}
	}

	@Command(name="nextkr", type = Command.ExecutorType.USER)
	private void nextkr(User user, MessageChannel channel) {
		try {
			Match next = KRLCK.getInstance().getNextMatch();
			channel.sendMessage("Le prochain match est " + next.getTeam1() + " VS " + next.getTeam2() + " : " + next.getScheduledDate()).queue();
		} catch (NotInitializedException e){
			e.printStackTrace();
			channel.sendMessage("Les matchs n'ont pas été chargés par le bot. Réessayez plus tard.").queue();
		}
	}

	@Command(name="nextmsi", type = Command.ExecutorType.USER)
	private void nextmsi(User user, MessageChannel channel) {
		try {
			Match next = MSI.getInstance().getNextMatch();
			channel.sendMessage("Le prochain match est " + next.getTeam1() + " VS " + next.getTeam2() + " : " + next.getScheduledDate()).queue();
		} catch (NotInitializedException e){
			e.printStackTrace();
			channel.sendMessage("Les matchs n'ont pas été chargés par le bot. Réessayez plus tard.").queue();
		}
	}

	@Command(name="dayeu", type = Command.ExecutorType.USER)
	private void dayeu(User user, MessageChannel channel){
		ArrayList<Match> nexts = EULCS.getInstance().getDayMatches(new Date());
		channel.sendMessage("Les matchs du jour sont : " + nexts).queue();
	}

	@Command(name="dayna", type = Command.ExecutorType.USER)
	private void dayna(User user, MessageChannel channel){
		ArrayList<Match> nexts = NALCS.getInstance().getDayMatches(new Date());
		channel.sendMessage("Les matchs du jour sont : " + nexts).queue();
	}

	@Command(name="daykr", type = Command.ExecutorType.USER)
	private void daykr(User user, MessageChannel channel){
		ArrayList<Match> nexts = KRLCK.getInstance().getDayMatches(new Date());
		channel.sendMessage("Les matchs du jour sont : " + nexts).queue();
	}

	@Command(name="daymsi", type = Command.ExecutorType.USER)
	private void daymsi(User user, MessageChannel channel){
		ArrayList<Match> nexts = MSI.getInstance().getDayMatches(new Date());
		channel.sendMessage("Les matchs du jour sont : " + nexts).queue();
	}


}
