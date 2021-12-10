package cz.martlin.jmop.core.sources.remote.youtube;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.SearchResult;
import com.google.api.services.youtube.model.Video;
import com.google.api.services.youtube.model.VideoListResponse;

import cz.martlin.jmop.common.data.misc.TrackData;
import cz.martlin.jmop.common.data.model.Track;
import cz.martlin.jmop.core.misc.DurationUtilities;
import cz.martlin.jmop.core.sources.remote.BaseRemotesConfiguration;
import cz.martlin.jmop.core.sources.remote.JMOPSourceryException;
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

	private final BaseRemotesConfiguration config;

	public YoutubeQuerier(BaseRemotesConfiguration config) {
		super();

		this.config = config;
	}

//	@Override
//	protected String createUrlOfSearchResult(String query) {
//		String encoded = encodeURLdata(query);
//		return "https://www.youtube.com/results?search_query=" + encoded; //$NON-NLS-1$
//	}

	@Override
	protected String createUrlOfTrack(Track track) {
		String id = track.getIdentifier();
		return "https://www.youtube.com/watch?v=" + id; //$NON-NLS-1$
	}

	@Override
	protected YouTube.Videos.List createLoadRequest(List<String> ids) throws Exception {
		YouTube youtube = obtainYoutubeService();

		YouTube.Videos.List listVideosRequest = youtube.videos().list("contentDetails,snippet"); //$NON-NLS-1$

		String idsStr = ids.stream().collect(Collectors.joining(","));
		listVideosRequest.setId(idsStr);

		return listVideosRequest;
	}

	@Override
	protected YouTube.Search.List createSearchRequest(String query) throws IOException {
		YouTube youtube = obtainYoutubeService();

		YouTube.Search.List searchListByKeywordRequest = youtube.search().list("snippet"); //$NON-NLS-1$
		searchListByKeywordRequest.setType("video"); //$NON-NLS-1$
		searchListByKeywordRequest.setMaxResults((long) config.getSearchCount());
		searchListByKeywordRequest.setQ(query);
		return searchListByKeywordRequest;
	}

	@Override
	protected YouTube.Search.List createLoadNextRequest(String id) throws IOException {
		YouTube youtube = obtainYoutubeService();

		YouTube.Search.List searchListRelatedVideosRequest = youtube.search().list("snippet"); //$NON-NLS-1$
		searchListRelatedVideosRequest.setType("video"); //$NON-NLS-1$
		searchListRelatedVideosRequest.setRelatedToVideoId(id);
		searchListRelatedVideosRequest.setMaxResults(3l);
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
	protected List<TrackData> convertLoadResponse(VideoListResponse response) throws Exception {
		return convertVideoListResponse(response);
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
	protected TrackData chooseNext(List<TrackData> tracks, String id) throws Exception {
		TrackData first = tracks.get(0);
		TrackData second = tracks.get(1);

		if (first.getIdentifier().equals(id)) {
			return second;
		} else {
			return first;
		}
	}

	/////////////////////////////////////////////////////////////////////////////////////


	private YouTube obtainYoutubeService() throws JMOPSourceryException {
		try {
			return YoutubeUtilities.getYouTubeService();
		} catch (Exception e) {
			throw new JMOPSourceryException("Cannot obtain the YouTube service", e);
		}
	}

	
	/**
	 * Converts videolist into track(s).
	 * 
	 * @param bundle
	 * @param response
	 * @return
	 */
	private List<TrackData> convertVideoListResponse(VideoListResponse response) {
		List<Video> results = response.getItems();

		return results.stream() //
				.map((v) -> videoToTrack(v)) //
				.collect(Collectors.toList());
	}

	/**
	 * Converts video to track.
	 * 
	 * @param bundle
	 * @param result
	 * @return
	 */
	private TrackData videoToTrack(Video result) {
		String identifier = result.getId();
		String title = result.getSnippet().getTitle();
		String description = result.getSnippet().getDescription();
		String durationStr = result.getContentDetails().getDuration();
		Duration duration = DurationUtilities.parseYoutubeDuration(durationStr);

		return new TrackData(identifier, title, description, duration);
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
	 * @
	 */
	private List<String> convertSearchListResponse(SearchListResponse response)  {
		List<SearchResult> results = response.getItems();

		return results.stream() //
				.map((r) -> searchResultToId(r)) //
				.collect(Collectors.toList());

	}

}
