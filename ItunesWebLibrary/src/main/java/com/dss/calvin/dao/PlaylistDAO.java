package com.dss.calvin.dao;

import java.util.Collection;

import javax.ejb.Local;
import javax.ws.rs.PathParam;

import com.dss.calvin.entity.Playlist;
import com.dss.calvin.entity.Track;



@Local
public interface PlaylistDAO {

	public Collection<Playlist> getAllFromPlaylistTable();
	public void addPlaylist(Playlist playlist);
	public Collection<String> getAllTracksFromPlaylistPersistID(String persistenceID);
	public Collection<Integer> getAllPlaylistIDsFromPlaylistPersistID(String persistenceID);
	public Collection<Object> getAllTracksFromAPlaylistID(Integer playlistID);
	public void deleteTrackFromPlaylist(Integer playlistId, Integer trackId, String persistID);
	public Collection<Integer> getAllPlaylistIds();
	public void createPlaylist(Playlist plist);
	public void deletePlaylist(Integer playlistID);

}
