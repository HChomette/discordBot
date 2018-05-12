package fr.endyron.leuceusseu;

import fr.endyron.leuceusseu.command.CommandMap;
import fr.endyron.leuceusseu.event.BotListener;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;

import javax.security.auth.login.LoginException;
import java.util.Scanner;

/**
 * Main Class. Leuceusseu is a Discord bot. Its basic task is notifying future LCS matches.<br/>
 * Other functionality may or may not be added.<br/>
 * The main class is the runnable starting the bot. Type 'stop' in console to shut down.
 */
public class Main implements Runnable {

	private final JDA jda;
	private final CommandMap commandMap = new CommandMap(this);
	private final Scanner scanner = new Scanner(System.in);

	//Discord bot token. If you read this from GitHub, the token is either outdated or linked to a useless bot.
	private static final String BOT_TOKEN = "FALSETOKEN";

	private boolean running;

	public Main() throws LoginException {
		//Bot builder. Bot token is set here
		jda = new JDABuilder(AccountType.BOT).setToken(BOT_TOKEN).buildAsync();
		jda.addEventListener(new BotListener(commandMap));
		System.out.println("Bot Leuceusseu connecté au serveur.");
	}

	public void setRunning(boolean running){
		this.running = running;
	}

	public JDA getJda(){return jda;}

	@Override
	public void run() {
		running = true;

		while(running){
			if(scanner.hasNextLine()) commandMap.commandConsole(scanner.nextLine());
		}

		scanner.close();
		System.out.println("Leuceusseu stopped");
		jda.shutdown();
		System.exit(0);
	}

	public static void main(String[] args) {
		try {
			Main main = new Main();
			new Thread(main, "botDiscord").start();
		} catch (LoginException e) {
			e.printStackTrace();
		}
	}

}
