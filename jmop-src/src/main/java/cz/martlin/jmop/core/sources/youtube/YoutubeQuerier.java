package cz.martlin.jmop.core.sources.youtube;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.SearchResult;
import com.google.api.services.youtube.model.Video;
import com.google.api.services.youtube.model.VideoListResponse;

import cz.martlin.jmop.core.config.BaseConfiguration;
import cz.martlin.jmop.core.data.Bundle;
import cz.martlin.jmop.core.data.Track;
import cz.martlin.jmop.core.misc.DurationUtilities;
import cz.martlin.jmop.core.misc.InternetConnectionStatus;
import cz.martlin.jmop.core.misc.JMOPSourceException;
import cz.martlin.jmop.core.sources.remote.SimpleRemoteQuerier;
import javafx.util.Duration;

/**
 * The youtube.com remote source implemented by youtube API.
 * 
 * @author martin
 *
 */
public class YoutubeQuerier extends SimpleRemoteQuerier<//
		YouTube.Videos.List, VideoListResponse, //
		YouTube.Search.List, SearchListResponse, //
		YouTube.Search.List, SearchListResponse> {

	private final BaseConfiguration config;

	public YoutubeQuerier(BaseConfiguration config, InternetConnectionStatus connection) {
		super(connection);

		this.config = config;
	}

	protected String urlOfTrack(String id) {
		return "https://www.youtube.com/watch?v=" + id; //$NON-NLS-1$
	}

	@Override
	protected YouTube.Videos.List createLoadRequest(List<String> ids) throws Exception {
		YouTube youtube = YoutubeUtilities.getYouTubeService();

		YouTube.Videos.List listVideosRequest = youtube.videos().list("contentDetails,snippet"); //$NON-NLS-1$

		String idsStr = ids.stream().collect(Collectors.joining(","));
		listVideosRequest.setId(idsStr);

		return listVideosRequest;
	}

	@Override
	protected YouTube.Search.List createSearchRequest(String query) throws IOException {
		YouTube youtube = YoutubeUtilities.getYouTubeService();

		YouTube.Search.List searchListByKeywordRequest = youtube.search().list("snippet"); //$NON-NLS-1$
		searchListByKeywordRequest.setType("video"); //$NON-NLS-1$
		searchListByKeywordRequest.setMaxResults((long) config.getSearchCount());
		searchListByKeywordRequest.setQ(query);
		return searchListByKeywordRequest;
	}

	@Override
	protected YouTube.Search.List createLoadNextRequest(String id) throws IOException {
		YouTube youtube = YoutubeUtilities.getYouTubeService();

		YouTube.Search.List searchListRelatedVideosRequest = youtube.search().list("snippet"); //$NON-NLS-1$
		searchListRelatedVideosRequest.setType("video"); //$NON-NLS-1$
		searchListRelatedVideosRequest.setRelatedToVideoId(id);
		searchListRelatedVideosRequest.setMaxResults(2l);
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
	protected List<Track> convertLoadResponse(Bundle bundle, VideoListResponse response) throws Exception {
		return convertVideoListResponse(bundle, response);
	}

	@Override
	protected List<String> convertSearchResponse(SearchListResponse response) throws Exception {
		return convertSearchListResponse(response);
	}

	@Override
	protected List<String> convertLoadNextResponse(SearchListResponse response) throws Exception {
		return convertSearchListResponse(response);
	}
	
	@Override
	protected Track chooseNext(List<Track> tracks, String id) throws Exception {
		Track first = tracks.get(0);
		Track second = tracks.get(1);
		
		if (first.getIdentifier().equals(id)) {
			return second;
		} else {
			return first;
		}
	}

	/////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Converts videolist into track(s).
	 * 
	 * @param bundle
	 * @param response
	 * @return
	 */
	private List<Track> convertVideoListResponse(Bundle bundle, VideoListResponse response) {
		List<Video> results = response.getItems();

		return results.stream() //
				.map((v) -> videoToTrack(bundle, v)) //
				.collect(Collectors.toList());
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
	 * Converts seachlist to ids.
	 * 
	 * @param response
	 * @return
	 * @throws JMOPSourceException
	 */
	private List<String> convertSearchListResponse(SearchListResponse response) throws JMOPSourceException {
		List<SearchResult> results = response.getItems();

		return results.stream() //
				.map((r) -> searchResultToId(r)) //
				.collect(Collectors.toList());

	}

}
