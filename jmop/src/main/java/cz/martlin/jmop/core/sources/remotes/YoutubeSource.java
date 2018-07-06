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

public class YoutubeSource extends
		SimpleRemoteSource<//
				YouTube.Videos.List, VideoListResponse, //
				YouTube.Search.List, SearchListResponse, //
				YouTube.Search.List, SearchListResponse> {

	public YoutubeSource() {
		super();
	}

	@Override
	protected String urlOfTrack(String id) {
		return "https://www.youtube.com/watch?v=" + id;
	}

	@Override
	protected YouTube.Videos.List createLoadRequest(String id) throws Exception {
		YouTube youtube = YoutubeUtilities.getYouTubeService();

		YouTube.Videos.List listVideosRequest = youtube.videos().list("snippet");
		listVideosRequest.setId(id);
		return listVideosRequest;
	}

	@Override
	protected YouTube.Search.List createSearchRequest(String query) throws IOException {
		YouTube youtube = YoutubeUtilities.getYouTubeService();

		YouTube.Search.List searchListByKeywordRequest = youtube.search().list("snippet");
		searchListByKeywordRequest.setType("video");
		searchListByKeywordRequest.setMaxResults(1l);
		searchListByKeywordRequest.setQ(query);
		return searchListByKeywordRequest;
	}

	@Override
	protected YouTube.Search.List createLoadNextRequest(String id) throws IOException {
		YouTube youtube = YoutubeUtilities.getYouTubeService();

		YouTube.Search.List searchListRelatedVideosRequest = youtube.search().list("snippet");
		searchListRelatedVideosRequest.setType("video");
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

	private Track convertVideoListResponse(Bundle bundle, VideoListResponse response) {
		List<Video> results = response.getItems();
		Video result = results.get(0);
		Track track = videoToTrack(bundle, result);
		return track;
	}

	private Track videoToTrack(Bundle bundle, Video result) {
		String identifier = result.getId();
		String title = result.getSnippet().getTitle();
		String description = result.getSnippet().getDescription();
		// TODO get thumbnail

		return new Track(bundle, identifier, title, description);
	}

	private Track convertSearchListResponse(Bundle bundle, SearchListResponse response) {
		List<SearchResult> results = response.getItems();
		SearchResult result = results.get(0);
		Track track = snippetToTrack(bundle, result);
		return track;
	}

	private Track snippetToTrack(Bundle bundle, SearchResult result) {
		String identifier = result.getId().getVideoId();
		String title = result.getSnippet().getTitle();
		String description = result.getSnippet().getDescription();
		// TODO get thumbnail

		return new Track(bundle, identifier, title, description);
	}
}
