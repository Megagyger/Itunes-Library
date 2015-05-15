package com.dss.calvin.services.ejbs;

import java.util.Collection;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;

import com.dss.calvin.dao.TracksDAO;
import com.dss.calvin.entity.Track;
import com.dss.calvin.services.TrackServiceLocal;

@Stateless
@Local
public class TrackServiceLocalEJB implements TrackServiceLocal{

	@EJB
	private TracksDAO dao;

	public Collection<Track> getAllFromTracksTable() {
		return dao.getAllFromTracksTable();
	}

	public void addTrack(Track track) {
		dao.addTrack(track);
		
	}

	@Override
	public Collection<Track> getAllTracksByArtist(String artist) {
		return dao.getAllTracksByArtist(artist);
	}

	@Override
	public Collection<Track> getAllTracksByGenre(String genre) {
		return dao.getAllTracksByGenre(genre);
	}

	@Override
	public Collection<Track> getAllTracksByAlbum(String album) {
		return dao.getAllTracksByAlbum(album);
	}

	@Override
	public Collection getAlbumListFromTracks() {
		return dao.getAlbumListFromTracks();
	}

	@Override
	public Collection getArtistListFromTracks() {
		return dao.getArtistListFromTracks();
	}

	@Override
	public Collection getGenreListFromTracks() {
		return dao.getGenreListFromTracks();
	}

	@Override
	public Collection<Track> getAllTracksWithPersistID(String persistID) {
		return dao.getAllTracksWithPersistID(persistID);
	}

	@Override
	public Track getTrackById(Integer trackID) {
		return dao.getTrackById(trackID);
	}

	@Override
	public void updateTrack(Integer trackID, String name, String artist,
			String album, String genre) {
		dao.updateTrack(trackID, name, artist, album, genre);
		
	}
	


}
