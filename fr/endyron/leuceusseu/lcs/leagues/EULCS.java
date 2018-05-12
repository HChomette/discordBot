package fr.endyron.leuceusseu.lcs.leagues;

public class EULCS extends League {
	private final static String SOURCE_PAGE = "https://api.lolesports.com/api/v1/scheduleItems?leagueId=3";
	private static EULCS instance = null;

	private EULCS(){
		super();
	}

	public static EULCS getInstance(){
		if(instance == null) instance = new EULCS();
		return instance;
	}

	@Override
	String getSourcePage() {
		return SOURCE_PAGE;
	}
}
