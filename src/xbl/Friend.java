/*
 * Copyright (c) 2009. The Frontside Software, Inc.
 *
 * The contents of this file are subject to the Gnu General Public License version 2
 * or later (the "License"); You may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 */

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
