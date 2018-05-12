package fr.endyron.leuceusseu.command;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import fr.endyron.leuceusseu.Main;
import fr.endyron.leuceusseu.command.defaut.BaseCommands;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.PrivateChannel;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.User;

/**
 * Map linking text commands to actual command code
 * The double dependency is not very important, as the core code is not supposed to change.
 * @author NeutronStars
 * TODO : Comment more
 */
public final class CommandMap {

	private final Main main;

	private final Map<String, SimpleCommand> commands = new HashMap<>();
	private final String tag = "!x "; //Commands start tag

	public CommandMap(Main main) {
		this.main = main;
		registerCommand(new BaseCommands(main));
	}

	public String getTag() {
		return tag;
	}

	public Collection<SimpleCommand> getCommands(){
		return commands.values();
	}

	/**
	 * Register commands from several classes at once
	 * @param objects classes containing the methods
	 */
	public void registerCommands(Object...objects){
		for(Object object : objects) registerCommand(object);
	}

	/**
	 * Register commands from a class
	 * @param object class containing the methods
	 */
	private void registerCommand(Object object){
		for(Method method : object.getClass().getDeclaredMethods()){
			if(method.isAnnotationPresent(Command.class)){
				Command command = method.getAnnotation(Command.class);
				method.setAccessible(true);
				SimpleCommand simpleCommand = new SimpleCommand(command.name(), command.description(), command.type(), object, method);
				commands.put(command.name(), simpleCommand);
			}
		}
	}

	public void commandConsole(String command){
		Object[] object = getCommand(command);
		if(object[0] == null || ((SimpleCommand)object[0]).getExecutorType() == Command.ExecutorType.USER){
			System.out.println("Commande inconnue.");
			return;
		}
		try{
			execute(((SimpleCommand)object[0]), command, (String[])object[1], null);
		}catch(Exception exception){
			System.out.println("La methode "+((SimpleCommand)object[0]).getMethod().getName()+" n'est pas correctement initialisé.");
			exception.printStackTrace();
		}
	}

	public boolean commandUser(User user, String command, Message message){
		Object[] object = getCommand(command);
		if(object[0] == null || ((SimpleCommand)object[0]).getExecutorType() == Command.ExecutorType.CONSOLE) return false;
		try{
			execute(((SimpleCommand)object[0]), command,(String[])object[1], message);
		}catch(Exception exception){
			System.out.println("La methode "+((SimpleCommand)object[0]).getMethod().getName()+" n'est pas correctement initialisé.");
			exception.printStackTrace();
		}
		return true;
	}

	private Object[] getCommand(String command){
		String[] commandSplit = command.split(" ");
		String[] args = new String[commandSplit.length-1];
		for(int i = 1; i < commandSplit.length; i++) args[i-1] = commandSplit[i];
		SimpleCommand simpleCommand = commands.get(commandSplit[0]);
		return new Object[]{simpleCommand, args};
	}

	private void execute(SimpleCommand simpleCommand, String command, String[] args, Message message) throws Exception{
		Parameter[] parameters = simpleCommand.getMethod().getParameters();
		Object[] objects = new Object[parameters.length];
		for(int i = 0; i < parameters.length; i++){
			if(parameters[i].getType() == String[].class) objects[i] = args;
			else if(parameters[i].getType() == User.class) objects[i] = message == null ? null : message.getAuthor();
			else if(parameters[i].getType() == TextChannel.class) objects[i] = message == null ? null : message.getTextChannel();
			else if(parameters[i].getType() == PrivateChannel.class) objects[i] = message == null ? null : message.getPrivateChannel();
			else if(parameters[i].getType() == Guild.class) objects[i] = message == null ? null : message.getGuild();
			else if(parameters[i].getType() == String.class) objects[i] = command;
			else if(parameters[i].getType() == Message.class) objects[i] = message;
			else if(parameters[i].getType() == JDA.class) objects[i] = main.getJda();
			else if(parameters[i].getType() == MessageChannel.class && message != null) objects[i] = message.getChannel();
		}
		simpleCommand.getMethod().invoke(simpleCommand.getObject(), objects);
	}
}