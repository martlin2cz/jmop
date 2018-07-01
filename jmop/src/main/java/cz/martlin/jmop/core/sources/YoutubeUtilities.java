package cz.martlin.jmop.core.sources;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.YouTubeScopes;
import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.SearchResult;
import com.google.api.services.youtube.model.Video;
import com.google.api.services.youtube.model.VideoListResponse;

/**
 * Based on
 * https://github.com/youtube/api-samples/blob/master/java/src/main/java/com/google/api/services/samples/youtube/cmdline/data/Quickstart.java
 * 
 * @author martin
 *
 */
public class YoutubeUtilities {

	/** Application name. */
	private static final String APPLICATION_NAME = "jmop";

	/** Directory to store user credentials for this application. */
	private static final java.io.File DATA_STORE_DIR = new java.io.File(System.getProperty("user.home"),
			".credentials/youtube-java-quickstart");

	/** Global instance of the {@link FileDataStoreFactory}. */
	private static FileDataStoreFactory DATA_STORE_FACTORY;

	/** Global instance of the JSON factory. */
	private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

	/** Global instance of the HTTP transport. */
	private static HttpTransport HTTP_TRANSPORT;

	/**
	 * Global instance of the scopes required by this quickstart.
	 *
	 * If modifying these scopes, delete your previously saved credentials at
	 * ~/.credentials/drive-java-quickstart
	 */
	private static final List<String> SCOPES = Arrays.asList(YouTubeScopes.YOUTUBE_READONLY);

	static {
		try {
			HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
			DATA_STORE_FACTORY = new FileDataStoreFactory(DATA_STORE_DIR);
		} catch (Throwable t) {
			t.printStackTrace();
			System.exit(1);
		}
	}

	/**
	 * Create an authorized Credential object.
	 * 
	 * @return an authorized Credential object.
	 * @throws IOException
	 */
	public static Credential authorize() throws IOException {
		// Load client secrets.
		InputStream in = YoutubeUtilities.class.getResourceAsStream("/cz/martlin/jmop/apis/youtube_client_secret.json");
		GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

		// Build flow and trigger user authorization request.
		GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(HTTP_TRANSPORT, JSON_FACTORY,
				clientSecrets, SCOPES).setDataStoreFactory(DATA_STORE_FACTORY).setAccessType("offline").build();
		Credential credential = new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver()).authorize("user");
		return credential;
	}

	/**
	 * Build and return an authorized API client service, such as a YouTube Data
	 * API client service.
	 * 
	 * @return an authorized API client service
	 * @throws IOException
	 */
	public static YouTube getYouTubeService() throws IOException {
		Credential credential = authorize();
		return new YouTube.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential).setApplicationName(APPLICATION_NAME)
				.build();
	}

	public static void main(String[] args) throws IOException {
		YouTube youtube = getYouTubeService();
		try {
			final String id = "MgApT3VHtZY";
			
			HashMap<String, String> parameters = new HashMap<>();
	        parameters.put("part", "snippet");
	        parameters.put("relatedToVideoId", id);
	        parameters.put("type", "video");

	        YouTube.Search.List searchListRelatedVideosRequest = youtube.search().list(parameters.get("part").toString());
	        if (parameters.containsKey("relatedToVideoId") && parameters.get("relatedToVideoId") != "") {
	            searchListRelatedVideosRequest.setRelatedToVideoId(parameters.get("relatedToVideoId").toString());
	        }

	        if (parameters.containsKey("type") && parameters.get("type") != "") {
	            searchListRelatedVideosRequest.setType(parameters.get("type").toString());
	        }

	        SearchListResponse response = searchListRelatedVideosRequest.execute();
	        for (SearchResult res: response.getItems()) {
	        	System.out.println("Related?" + res.getSnippet().getTitle());
	        }
			
			
			
			
//			YouTube.Videos.List req = youtube.videos().list("snippet");
//			req.setId(id);
//			VideoListResponse resp = req.execute();
//			Video video = resp.getItems().get(0);
//			System.out.println("Video " + video.getSnippet().getTitle() + ":\n " + video.getSnippet().getDescription());
//			
			
			/*
			 * YouTube.Channels.List channelsListByUsernameRequest =
			 * youtube.channels() .list("snippet,contentDetails,statistics");
			 * channelsListByUsernameRequest.setForUsername("GEJMR");
			 * 
			 * ChannelListResponse response =
			 * channelsListByUsernameRequest.execute(); Channel channel =
			 * response.getItems().get(0); System.out.
			 * printf("This channel's ID is %s. Its title is '%s', and it has %s views and: %s.\n"
			 * , channel.getId(), channel.getSnippet().getTitle(),
			 * channel.getStatistics().getViewCount(),
			 * channel.getContentDetails());
			 */
		} catch (GoogleJsonResponseException e) {
			e.printStackTrace();
			System.err.println(
					"There was a service error: " + e.getDetails().getCode() + " : " + e.getDetails().getMessage());
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}

	public YoutubeUtilities() {
		// TODO Auto-generated constructor stub
	}

}
