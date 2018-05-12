package fr.endyron.leuceusseu.event;


import fr.endyron.leuceusseu.command.CommandMap;
import net.dv8tion.jda.core.events.Event;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.EventListener;

/**
 * Bot main event listener class
 */
public class BotListener implements EventListener{

	private final CommandMap commandMap;

	public BotListener(CommandMap commandMap){
		this.commandMap = commandMap;
	}

	/**
	 * Base event listener. Redirects to correct listener.
	 * @param event
	 */
	@Override
	public void onEvent(Event event) {
		if(event instanceof MessageReceivedEvent) onMessage((MessageReceivedEvent)event);
	}

	/**
	 * Message received listener.
	 * @param event
	 */
	private void onMessage(MessageReceivedEvent event){
		if(event.getAuthor().equals(event.getJDA().getSelfUser())) return;

		String message = event.getMessage().getContentDisplay(); //TODO : verify correct getContent
		if(message.startsWith(commandMap.getTag())) { //Verify start tag
			message = message.replaceFirst(commandMap.getTag(), "").toLowerCase(); //Erase start tag;
			if(commandMap.commandUser(event.getAuthor(), message, event.getMessage())){
				//When command found
			} else {
				System.out.println("Commande non reconnue");
			}
		}
	}
}
