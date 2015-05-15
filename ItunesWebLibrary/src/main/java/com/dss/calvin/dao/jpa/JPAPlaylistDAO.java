package com.dss.calvin.dao.jpa;


import java.util.ArrayList;
import java.util.Collection;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.dss.calvin.dao.PlaylistDAO;
import com.dss.calvin.entity.Playlist;
import com.dss.calvin.entity.Track;

@Stateless
@Local
public class JPAPlaylistDAO implements PlaylistDAO {

	@PersistenceContext
	EntityManager entityManager;

	public Collection<Playlist> getAllFromPlaylistTable() {
		Query query = entityManager.createQuery("from Playlist");
		return query.getResultList();
	}

	public void addPlaylist(Playlist playlist) {
		entityManager.persist(playlist);

	}

	@Override
	public Collection<String> getAllTracksFromPlaylistPersistID(
			String playlistPersistenceID) {
		Query query = entityManager
				.createQuery("select p.track.name from Playlist p where"
						+ " p.playlistPersistenceID =:playlistPersistenceID");
		query.setParameter("playlistPersistenceID", playlistPersistenceID);
		return query.getResultList();
	}

	@Override
	public Collection<Integer> getAllPlaylistIDsFromPlaylistPersistID(
			String playlistPersistenceID) {
		Query query = entityManager
				.createQuery("select distinct p.playlistID from Playlist p where p.playlistPersistenceID =:playlistPersistenceID");
		query.setParameter("playlistPersistenceID", playlistPersistenceID);
		return query.getResultList();
	}

	@Override
	public Collection<Object> getAllTracksFromAPlaylistID(Integer playlistID) {
		Query query = entityManager
				.createQuery("select p.track.trackID, p.track.name, p.track.artist from Playlist p where p.playlistID =:playlistID");
		query.setParameter("playlistID", playlistID);
		return query.getResultList();
	}

	
	/**
	 * To delete a track, first we get the track from the tracks table by it's ID
	 * Then we use that as the reference to get the the playlist record for that track
	 * This is because of the way I mapped the relationship between tracks and playlist
	 * A track has a list of playlists that it is part of. And is mapped by the trackID
	 * but we store it as the track.
	 * 
	 * Then we just use the entity manager to delete the track and I am assuming takes
	 * care of the deletion of the playlist from the list in parent track object. 
	 * 
	 * To be honest I am still a little unsure on cascading and detached orphans etc hence my assumption
	 * 
	 * 
	 * 
	 */
	@Override
	public void deleteTrackFromPlaylist(Integer playlistID, Integer trackID, String persistID) {
		Query q = entityManager.createQuery("from Track t where t.trackID =:trackID");
		q.setParameter("trackID", trackID);
		Track track = (Track) q.getSingleResult();
		
		Query query = entityManager.createQuery(" from Playlist p where p.playlistID =:playlistID and p.track =:track");
		query.setParameter("playlistID", playlistID);
		query.setParameter("track", track);		
		Playlist p = (Playlist) query.getSingleResult();
		
		
		entityManager.remove(p);

	}

	@Override
	public Collection<Integer> getAllPlaylistIds() {
		Query query = entityManager
				.createQuery("select distinct p.playlistID from Playlist p");
		return query.getResultList();
	}

	@Override
	public void createPlaylist(Playlist plist) {
		entityManager.persist(plist);
		
	}

	@Override
	public void deletePlaylist(Integer playlistID) {
		Query query = entityManager.createQuery("from Playlist p where p.playlistID =:playlistID");
		query.setParameter("playlistID", playlistID);
		ArrayList<Playlist> list = (ArrayList<Playlist>) query.getResultList();
		
		for(Playlist l: list){
			entityManager.remove(l);
		}
		
	}

	

}
