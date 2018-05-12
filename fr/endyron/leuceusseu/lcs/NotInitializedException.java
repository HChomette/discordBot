package fr.endyron.leuceusseu.lcs;

public class NotInitializedException extends Exception {

	public NotInitializedException(String message) {
		super(message);
	}

	public NotInitializedException(String message, Throwable throwable) {
		super(message, throwable);
	}
}
