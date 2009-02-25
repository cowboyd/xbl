package xbl;


public class Friend {
	private String gamerTag;
	private String tileURL;
	private int gamerScore;
	private String status;
	private String profileURL;
	private String info;


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

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public String getProfileURL() {
		return profileURL;
	}

	public void setProfileURL(String profileURL) {
		this.profileURL = profileURL;
	}

	public String toString() {
		return String.format("{gt: %s, tile: %s, score: %d, status: %s, info: %s, url: %s", gamerTag, tileURL, gamerScore, status, info, profileURL);
	}
}
