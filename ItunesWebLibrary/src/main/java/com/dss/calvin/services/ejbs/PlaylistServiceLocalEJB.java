package com.dss.calvin.services.ejbs;

import java.util.Collection;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;

import com.dss.calvin.dao.PlaylistDAO;
import com.dss.calvin.entity.Playlist;
import com.dss.calvin.entity.Track;
import com.dss.calvin.services.PlaylistServiceLocal;

@Stateless
@Local
public class PlaylistServiceLocalEJB implements PlaylistServiceLocal {

	@EJB
	private PlaylistDAO dao;

	public Collection<Playlist> getAllPlaylistsFromTable() {
		return dao.getAllFromPlaylistTable();
	}

	public void addPlaylist(Playlist playlist) {
		dao.addPlaylist(playlist);
	}

	@Override
	public Collection<String> getAllTracksFromPlaylistPersistID(
			String persistenceID) {
		return dao.getAllTracksFromPlaylistPersistID(persistenceID);
	}

	@Override
	public Collection<Integer> getAllPlaylistIDsFromPlaylistPersistID(
			String persistenceID) {
		return dao.getAllPlaylistIDsFromPlaylistPersistID(persistenceID);
	}

	@Override
	public Collection<Object> getAllTracksFromAPlaylistID(Integer playlistID) {
		return dao.getAllTracksFromAPlaylistID(playlistID);
	}

	@Override
	public void deleteTrackFromPlaylist(Integer playlistId, Integer trackId, String persistID) {
		dao.deleteTrackFromPlaylist(playlistId, trackId, persistID);
	}

	@Override
	public Collection<Integer> getAllPlaylistIds() {
		return dao.getAllPlaylistIds();
	}

	@Override
	public void createPlaylist(Playlist plist) {
		dao.createPlaylist(plist);
		
	}

	@Override
	public void deletePlaylist(Integer playlistID) {
		dao.deletePlaylist(playlistID);
		
	}

	

}
