package fr.endyron.leuceusseu.lcs.leagues;

public class NALCS extends League {
	private final static String SOURCE_PAGE = "https://api.lolesports.com/api/v1/scheduleItems?leagueId=2";
	private static NALCS instance = null;

	private NALCS(){
		super();
	}

	public static NALCS getInstance(){
		if(instance == null) instance = new NALCS();
		return instance;
	}

	@Override
	String getSourcePage() {
		return SOURCE_PAGE;
	}
}
