package fr.endyron.leuceusseu.lcs.leagues;

import fr.endyron.leuceusseu.lcs.DateConverter;
import fr.endyron.leuceusseu.lcs.Match;
import fr.endyron.leuceusseu.lcs.NotInitializedException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

public abstract class League {
	private ArrayList<Match> scheduledItems;

	League(){
		this.scheduledItems = new ArrayList<>();
	}

	/**
	 * Initialize schedule from schedule link
	 * In case of source page format change, this method becomes incorrect and the command stops working
	 */
	public void initialize(){
		JSONObject global = getJsonFromLink(getSourcePage());

		JSONArray scheduleItems = global.getJSONArray("scheduleItems");
		Date now = new Date();
		for(int i = 0; i < scheduleItems.length(); i++){
			JSONObject item = (JSONObject) scheduleItems.get(i);
			try {
				String scheduledTime = (String) item.get("scheduledTime");
				Date date = DateConverter.stringToDate(scheduledTime);
				if (date.after(now)) {
					String tournamentId = item.getString("tournament");
					String matchId = item.getString("match");
					JSONObject matchDetails = getJsonFromLink(
							"https://api.lolesports.com/api/v2/highlanderMatchDetails?tournamentId=" + tournamentId + "&matchId=" + matchId);
					JSONArray teams = matchDetails.getJSONArray("teams");
					JSONObject team1 = teams.getJSONObject(0);
					String team1Name = team1.getString("name");
					JSONObject team2 = teams.getJSONObject(1);
					String team2Name = team2.getString("name");
					this.scheduledItems.add(new Match(date, team1Name, team2Name));
				}
			} catch (JSONException e){
				System.err.println("Erreur de parsing : " + item);
				e.printStackTrace();
			}
		}
		Collections.sort(this.scheduledItems);
		System.out.println("Initialisation terminée");
		System.out.println(this.scheduledItems);
	}

	/**
	 * Get JSONObject from HTTP link
	 * @param link HTTP link
	 * @return
	 */
	private JSONObject getJsonFromLink(String link){
		try {
			URLConnection connect = new URL(link).openConnection();

			//Properties to avoid 403 forbidden
			connect.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
			connect.connect();
			BufferedReader reader = new BufferedReader(new InputStreamReader(connect.getInputStream()));

			String inputLine;
			inputLine = reader.readLine();

			JSONObject global = new JSONObject(inputLine);

			reader.close();
			return global;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Return next future match
	 * @return
	 * @throws NotInitializedException
	 */
	public Match getNextMatch() throws NotInitializedException{
		Date now = new Date();
		for(Match m : scheduledItems){
			if(m.getScheduledDate().after(now)) return m;
		}
		throw new NotInitializedException("No next match found");
	}

	/**
	 * Return a list of matches on a given date
	 * @param date
	 * @return
	 */
	public ArrayList<Match> getDayMatches(Date date){
		ArrayList<Match> res = new ArrayList<>();
		for(Match m : scheduledItems){
			if(m.getScheduledDate().after(date)
					&& date.getDay() == m.getScheduledDate().getDay()
					&& date.getMonth() == m.getScheduledDate().getMonth()){
				res.add(m);
			} else if (m.getScheduledDate().after(date)) return res;
		}
		return res;
	}

	/**
	 * Return next match if scheduled in less than 6 hours. Else returns null
	 * @return
	 * @throws NotInitializedException
	 */
	public Match notifyFutureMatch() throws NotInitializedException{
		Match m = getNextMatch();
		long diff = m.getScheduledDate().getTime() - new Date().getTime();
		if(diff/(1000 * 60 * 60) < 6){
			return m;
		} else return null;
	}

	abstract String getSourcePage();
}
