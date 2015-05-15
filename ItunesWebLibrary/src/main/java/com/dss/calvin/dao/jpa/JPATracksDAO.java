package com.dss.calvin.dao.jpa;

import java.util.Collection;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.dss.calvin.dao.TracksDAO;
import com.dss.calvin.entity.Track;

@Stateless
@Local
public class JPATracksDAO implements TracksDAO {

	@PersistenceContext
	EntityManager entityManager;

	public Collection<Track> getAllFromTracksTable() {
		Query query = entityManager.createQuery("from Track");
		return query.getResultList();
	}

	public void addTrack(Track track) {
		entityManager.persist(track);
	}

	@Override
	public Collection<Track> getAllTracksByArtist(String artist) {
		Query query = entityManager
				.createQuery("from Track t where t.artist =:artist");
		query.setParameter("artist", artist);
		return query.getResultList();

	}

	@Override
	public Collection<Track> getAllTracksByGenre(String genre) {
		Query query = entityManager
				.createQuery("from Track t where t.genre =:genre");
		query.setParameter("genre", genre);
		return query.getResultList();
	}

	@Override
	public Collection<Track> getAllTracksByAlbum(String album) {
		Query query = entityManager
				.createQuery("from Track t where t.album =:album");
		query.setParameter("album", album);
		return query.getResultList();
	}

	@Override
	public Collection getAlbumListFromTracks() {
		Query query = entityManager.createQuery("select distinct t.album from Track t");
		return query.getResultList();
	}

	@Override
	public Collection getArtistListFromTracks() {
		Query query = entityManager.createQuery("select distinct t.artist from Track t");
		return query.getResultList();
	}

	@Override
	public Collection getGenreListFromTracks() {
		Query query = entityManager.createQuery("select distinct t.genre from Track t");
		return query.getResultList();
	}

	@Override
	public Collection<Track> getAllTracksWithPersistID(String persistentID) {
		Query query = entityManager.createQuery("from Track t where t.persistentID =:persistentID");
		query.setParameter("persistentID", persistentID);
		return query.getResultList();
	}

	@Override
	public Track getTrackById(Integer trackID) {
		Query query = entityManager.createQuery("from Track t where t.trackID =:trackID");
		query.setParameter("trackID", trackID);
		return (Track) query.getSingleResult();
	}

	@Override
	public void updateTrack(Integer trackID, String name, String artist,
			String album, String genre) {
		Track track = entityManager.find(Track.class, trackID);
		
		track.setName(name);
		track.setArtist(artist);
		track.setAlbum(album);
		track.setGenre(genre);
		entityManager.merge(track);
		
	}

}
