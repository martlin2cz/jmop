package cz.martlin.jmop.core.sources.remotes;

import java.io.IOException;
import java.util.List;

import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.SearchResult;
import com.google.api.services.youtube.model.Video;
import com.google.api.services.youtube.model.VideoListResponse;

import cz.martlin.jmop.core.sources.SourceKind;
import cz.martlin.jmop.core.tracks.Track;
import cz.martlin.jmop.core.tracks.TrackIdentifier;

public class YoutubeSource extends
		SimpleRemoteSource<//
				YouTube.Videos.List, VideoListResponse, //
				YouTube.Search.List, SearchListResponse, //
				YouTube.Search.List, SearchListResponse> {

	public YoutubeSource() {
		super();
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
	protected Track convertLoadResponse(VideoListResponse response) throws Exception {
		return convertVideoListResponse(response);
	}

	@Override
	protected Track convertSearchResponse(SearchListResponse response) throws Exception {
		return convertSearchListResponse(response);
	}

	@Override
	protected Track convertLoadNextResponse(SearchListResponse response) throws Exception {
		return convertSearchListResponse(response);
	}

	/////////////////////////////////////////////////////////////////////////////////////

	private Track convertVideoListResponse(VideoListResponse response) {
		List<Video> results = response.getItems();
		Video result = results.get(0);
		Track track = videoToTrack(result);
		return track;
	}

	private Track videoToTrack(Video result) {
		SourceKind source = SourceKind.YOUTUBE;
		String id = result.getId();
		String title = result.getSnippet().getTitle();
		String description = result.getSnippet().getDescription();
		// TODO get thumbnail

		TrackIdentifier identifier = new TrackIdentifier(source, id);
		return new Track(identifier, title, description);
	}

	private Track convertSearchListResponse(SearchListResponse response) {
		List<SearchResult> results = response.getItems();
		SearchResult result = results.get(0);
		Track track = snippetToTrack(result);
		return track;
	}

	private Track snippetToTrack(SearchResult result) {
		SourceKind source = SourceKind.YOUTUBE;
		String id = result.getId().getVideoId();
		String title = result.getSnippet().getTitle();
		String description = result.getSnippet().getDescription();
		// TODO get thumbnail

		TrackIdentifier identifier = new TrackIdentifier(source, id);
		return new Track(identifier, title, description);
	}
}
