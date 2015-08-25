package com.demo.youtube;

import java.util.Scanner;

import javax.swing.JScrollBar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class YouTubeMain {

	private  static  String mBaseApi = "https://www.googleapis.com/youtube/v3/search";
	private String mBaseWatchLink = "https://www.youtube.com/watch?v=";
	private String mAPIKey = "AIzaSyArPjlsfnUL7_2h2vLCt14S4SqbkAoOfWc";
	
	public static void main(String[] args) {

		YouTubeMain youTubeMain = new YouTubeMain();

		String query = youTubeMain.getInput();

		String response = youTubeMain.makeAPICall(query);

		try {
			youTubeMain.parseAndPrintResponse(response);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}


	private String  getInput() {
		String s;
		Scanner in = new Scanner(System.in);
		System.out.println("Enter a search string");
		s = in.nextLine();
		return s;
	}	

	private   String makeAPICall(String query) {
		String url = mBaseApi + "?q=" + query + "&part=snippet&type=video&key="+ mAPIKey + "&max-results=10&order=viewCount";
		try {
			String response  = HttpHelper.doGet(url);
			return response;
			
		} catch (Exception e) {

			e.printStackTrace();
			return null;
		}		

	}

	private  void parseAndPrintResponse(String response) throws JSONException {
		JSONObject json = new JSONObject(response);

		JSONArray items = json.getJSONArray("items");  
		JSONObject jObj = items.getJSONObject(0); // first result, loop on items.length to get all result
		
		// Video Id and youtube link
		JSONObject id = jObj.getJSONObject("id");
		String videoId = id.getString("videoId");
		String watchLink = mBaseWatchLink + videoId;
		
		// Title and Description
		JSONObject snippet = jObj.getJSONObject("snippet");
		String title = snippet.getString("title");
		String description = snippet.getString("description");
		
		printResult(videoId, watchLink, title, description);

	}
	
	private void printResult(String videoId, String watchLink, String title, String albumDescription) {
		System.out.println("Video Id : " + videoId);
		System.out.println("YTLink : " + watchLink);
		System.out.println("Title : " + title);
		System.out.println("Description : " + albumDescription);
	}
}