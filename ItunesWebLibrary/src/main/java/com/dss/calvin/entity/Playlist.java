package com.dss.calvin.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="playLists")
public class Playlist implements Serializable{
	
	
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Integer id;
	
	@Column(name="playlist_Id")
	private Integer playlistID;
	
	@Column(name="playlist_Persistence_Id")
	private String playlistPersistenceID;
	
	
	@ManyToOne
	@JoinColumn(name="track_ID")
	private Track track;
	

	public Playlist() {
		
	}
	
	public Playlist(Integer pID, String ppID, Integer tID){
		this.setPlaylistID(pID);
		this.setPlaylistPersistenceID(ppID);
		this.track = new Track();
		this.track.setTrackID(tID);
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getPlaylistID() {
		return playlistID;
	}

	public void setPlaylistID(Integer playlistID) {
		this.playlistID = playlistID;
	}

	public String getPlaylistPersistenceID() {
		return playlistPersistenceID;
	}

	public void setPlaylistPersistenceID(String playlistPersistenceID) {
		this.playlistPersistenceID = playlistPersistenceID;
	}

	public Track getTrack() {
		return track;
	}

	public void setTrack(Track track) {
		this.track = track;
	}

}
