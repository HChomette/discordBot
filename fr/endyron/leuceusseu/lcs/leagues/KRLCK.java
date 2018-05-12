package fr.endyron.leuceusseu.lcs.leagues;

public class KRLCK extends League {
	private final static String SOURCE_PAGE = "https://api.lolesports.com/api/v1/scheduleItems?leagueId=6";
	private static KRLCK instance = null;

	private KRLCK(){
		super();
	}

	public static KRLCK getInstance(){
		if(instance == null) instance = new KRLCK();
		return instance;
	}

	@Override
	String getSourcePage() {
		return SOURCE_PAGE;
	}
}
