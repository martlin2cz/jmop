package cz.martlin.jmop.core.sources.remotes;

import java.io.IOException;
import java.util.List;

import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.SearchResult;
import com.google.api.services.youtube.model.Video;
import com.google.api.services.youtube.model.VideoListResponse;

import cz.martlin.jmop.core.data.Bundle;
import cz.martlin.jmop.core.data.Track;
import cz.martlin.jmop.core.misc.DurationUtilities;
import cz.martlin.jmop.core.misc.InternetConnectionStatus;
import cz.martlin.jmop.core.misc.JMOPSourceException;
import cz.martlin.jmop.core.sources.remote.SimpleRemoteSource;
import javafx.util.Duration;

/**
 * The youtube.com remote source implemented by youtube API.
 * 
 * @author martin
 *
 */
public class YoutubeSource extends
		SimpleRemoteSource<//
				YouTube.Videos.List, VideoListResponse, //
				YouTube.Search.List, SearchListResponse, //
				YouTube.Search.List, SearchListResponse> {

	public YoutubeSource(InternetConnectionStatus connection) {
		super(connection);
	}

	@Override
	protected String urlOfTrack(String id) {
		return "https://www.youtube.com/watch?v=" + id; //$NON-NLS-1$
	}

	@Override
	protected YouTube.Videos.List createLoadRequest(String id) throws Exception {
		YouTube youtube = YoutubeUtilities.getYouTubeService();

		YouTube.Videos.List listVideosRequest = youtube.videos().list("contentDetails,snippet"); //$NON-NLS-1$
		listVideosRequest.setId(id);
		return listVideosRequest;
	}

	@Override
	protected YouTube.Search.List createSearchRequest(String query) throws IOException {
		YouTube youtube = YoutubeUtilities.getYouTubeService();

		YouTube.Search.List searchListByKeywordRequest = youtube.search().list("snippet"); //$NON-NLS-1$
		searchListByKeywordRequest.setType("video"); //$NON-NLS-1$
		searchListByKeywordRequest.setMaxResults(1l);
		searchListByKeywordRequest.setQ(query);
		return searchListByKeywordRequest;
	}

	@Override
	protected YouTube.Search.List createLoadNextRequest(String id) throws IOException {
		YouTube youtube = YoutubeUtilities.getYouTubeService();

		YouTube.Search.List searchListRelatedVideosRequest = youtube.search().list("snippet"); //$NON-NLS-1$
		searchListRelatedVideosRequest.setType("video"); //$NON-NLS-1$
		searchListRelatedVideosRequest.setRelatedToVideoId(id);
		return searchListRelatedVideosRequest;
	}

	/////////////////////////////////////////////////////////////////////////////////////

	@Override
	protected VideoListResponse executeLoadRequest(YouTube.Videos.List request) throws Exception {

		return request.execute();
	}

	@Override
	protected SearchListResponse executeSearchRequest(YouTube.Search.List request) throws Exception {

		return request.execute();
	}

	@Override
	protected SearchListResponse executeLoadNextRequest(YouTube.Search.List request) throws Exception {

		return request.execute();
	}

	/////////////////////////////////////////////////////////////////////////////////////

	@Override
	protected Track convertLoadResponse(Bundle bundle, VideoListResponse response) throws Exception {
		return convertVideoListResponse(bundle, response);
	}

	@Override
	protected Track convertSearchResponse(Bundle bundle, SearchListResponse response) throws Exception {
		return convertSearchListResponse(bundle, response);
	}

	@Override
	protected Track convertLoadNextResponse(Bundle bundle, SearchListResponse response) throws Exception {
		return convertSearchListResponse(bundle, response);
	}

	/////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Converts videolist into track.
	 * 
	 * @param bundle
	 * @param response
	 * @return
	 */
	private Track convertVideoListResponse(Bundle bundle, VideoListResponse response) {
		List<Video> results = response.getItems();
		Video result = results.get(0);
		Track track = videoToTrack(bundle, result);
		return track;
	}

	/**
	 * Converts video to track.
	 * 
	 * @param bundle
	 * @param result
	 * @return
	 */
	private Track videoToTrack(Bundle bundle, Video result) {
		String identifier = result.getId();
		String title = result.getSnippet().getTitle();
		String description = result.getSnippet().getDescription();
		String durationStr = result.getContentDetails().getDuration();
		Duration duration = DurationUtilities.parseYoutubeDuration(durationStr);
		// TODO get thumbnail

		return bundle.createTrack(identifier, title, description, duration);
	}

	/**
	 * Converts search result to track id.
	 * 
	 * @param result
	 * @return
	 */
	private String searchResultToId(SearchResult result) {
		String identifier = result.getId().getVideoId();
		return identifier;
	}

	/**
	 * Converts seachlist to track.
	 * 
	 * @param bundle
	 * @param response
	 * @return
	 * @throws JMOPSourceException
	 */
	private Track convertSearchListResponse(Bundle bundle, SearchListResponse response) throws JMOPSourceException {
		List<SearchResult> results = response.getItems();
		SearchResult result = results.get(0);
		String identifier = searchResultToId(result);

		Track track = getTrack(bundle, identifier);
		return track;
	}

}
