package xbl;


public class Friend {
	private String gamerTag;
	private String tileURL;
	private int gamerScore;
	private String status;
	private String lastSeen;
	private String lastActivity;
	private String profileURL;


	public String getGamerTag() {
		return gamerTag;
	}

	public void setGamerTag(String gamerTag) {
		this.gamerTag = gamerTag;
	}

	public String getTileURL() {
		return tileURL;
	}

	public void setTileURL(String tileURL) {
		this.tileURL = tileURL;
	}

	public int getGamerScore() {
		return gamerScore;
	}

	public void setGamerScore(int gamerScore) {
		this.gamerScore = gamerScore;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getLastSeen() {
		return lastSeen;
	}

	public void setLastSeen(String lastSeen) {
		this.lastSeen = lastSeen;
	}

	public String getLastActivity() {
		return lastActivity;
	}

	public void setLastActivity(String lastActivity) {
		this.lastActivity = lastActivity;
	}

	public String getProfileURL() {
		return profileURL;
	}

	public void setProfileURL(String profileURL) {
		this.profileURL = profileURL;
	}

	public String toString() {
		String seen = lastActivity != null && !lastActivity.trim().equals("") ? lastSeen + "(" + lastActivity + ")" : lastSeen;
		return String.format("{gt: %s, tile: %s, score: %d, status: %s, lastSeen: %s, url: %s", gamerTag, tileURL, gamerScore, status, seen, profileURL);
	}

	public boolean hasLastActivity() {
		return lastActivity != null && !"".equals(lastActivity.trim());
	}
}
