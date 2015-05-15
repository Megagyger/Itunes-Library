package com.dss.calvin.services;

import java.util.Collection;

import javax.ejb.Local;

import com.dss.calvin.entity.Playlist;
import com.dss.calvin.entity.Track;

@Local
public interface PlaylistServiceLocal {

	public Collection<Playlist> getAllPlaylistsFromTable();
	public void addPlaylist(Playlist playlist);
	public Collection<String> getAllTracksFromPlaylistPersistID(String persistenceID);
	public Collection<Integer> getAllPlaylistIDsFromPlaylistPersistID(String persistenceID);
	public Collection<Object> getAllTracksFromAPlaylistID(Integer playlistID);
	public void deleteTrackFromPlaylist(Integer playlistId, Integer trackId, String persistID);
	public Collection<Integer> getAllPlaylistIds();
	public void createPlaylist(Playlist plist);
	public void deletePlaylist(Integer playlistID);
	
}
