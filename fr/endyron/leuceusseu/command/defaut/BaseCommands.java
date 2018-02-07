package fr.endyron.leuceusseu.command.defaut;

import fr.endyron.leuceusseu.Main;
import fr.endyron.leuceusseu.command.Command;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;

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
}
