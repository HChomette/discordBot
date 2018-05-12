package fr.endyron.leuceusseu.lcs.leagues;

public class MSI extends League {
	private final static String SOURCE_PAGE = "https://api.lolesports.com/api/v1/scheduleItems?leagueId=10";
	private static MSI instance = null;

	private MSI(){
		super();
	}

	public static MSI getInstance(){
		if(instance == null) instance = new MSI();
		return instance;
	}

	@Override
	String getSourcePage() {
		return SOURCE_PAGE;
	}
}
