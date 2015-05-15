package com.dss.calvin.dao;

import java.util.Collection;

import javax.ejb.Local;
import javax.ws.rs.PathParam;

import com.dss.calvin.entity.Track;

@Local
public interface TracksDAO {

	public Collection<Track> getAllFromTracksTable();
	public void addTrack(Track track);
	public Collection<Track> getAllTracksByArtist(String artist);
	public Collection<Track> getAllTracksByGenre(String genre);
	public Collection<Track> getAllTracksByAlbum(String album);
	public Collection getAlbumListFromTracks();
	public Collection getArtistListFromTracks();
	public Collection getGenreListFromTracks();
	public Collection<Track> getAllTracksWithPersistID(String persistID);
	public Track getTrackById(Integer trackID);
	public void updateTrack(Integer trackID, String name, String artist, String album, String genre);
	
}
