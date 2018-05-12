package fr.endyron.leuceusseu.lcs;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Match implements Comparable<Match>{
	private Date scheduledDate;
	private String team1;
	private String team2;

	public Match(Date scheduledDate, String team1, String team2){
		this.scheduledDate = scheduledDate;
		this.team1 = team1;
		this.team2 = team2;
	}

	public Date getScheduledDate() {
		return scheduledDate;
	}

	public String getTeam1() {
		return team1;
	}

	public String getTeam2() {
		return team2;
	}

	public String toString(){
		SimpleDateFormat df = new SimpleDateFormat("EEE dd MMM à kk:mm");
		return(team1 + " - " + team2 + " : " + df.format(scheduledDate));
	}


	@Override
	public int compareTo(Match o) {
		if(this.scheduledDate.before(o.getScheduledDate())) return -1;
		else return 1;
	}
}
