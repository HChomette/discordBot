package fr.endyron.leuceusseu.command;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Custom annotation to indicate a text Discord command
 */

@Target(value= ElementType.METHOD) //Can only target methods
@Retention(RetentionPolicy.RUNTIME) //Is read at runtime by JVM
public @interface Command {

	public String name();
	public String description() default "Pas de description";
	public ExecutorType type() default ExecutorType.ALL;

	public enum ExecutorType{
		ALL, USER, CONSOLE;
	}
}
