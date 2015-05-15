package com.dss.calvin.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.annotate.JsonIgnore;

@Entity
@XmlRootElement
@Table(name = "tracks")
public class Track implements Serializable {

	@Id
	private Integer trackID;

	private String name;

	private String artist;

	private String album;

	private String genre;

	private Integer size;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateAdded;

	private String persistentID;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "track")
	private List<Playlist> playlist;

	public Track() {
	}

	public Track(Integer trackID, String name, String artist, String album,
			String genre, Integer size, Date dateAdded, String persistentID) {

		this.trackID = trackID;
		this.name = name;
		this.album = album;
		this.artist = artist;
		this.genre = genre;
		this.dateAdded = dateAdded;
		this.size = size;
		this.persistentID = persistentID;
	}

	public Playlist addPlaylistData(Playlist plist){
		getPlaylistTrack().add(plist);
		plist.setTrack(this);
		return plist;
	}
	
	public Integer getTrackID() {
		return trackID;
	}

	public void setTrackID(Integer trackID) {
		this.trackID = trackID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getArtist() {
		return artist;
	}

	public void setArtist(String artist) {
		this.artist = artist;
	}

	public String getAlbum() {
		return album;
	}

	public void setAlbum(String album) {
		this.album = album;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}

	public Date getDateAdded() {
		return dateAdded;
	}

	public void setDateAdded(Date dateAdded) {
		this.dateAdded = dateAdded;
	}

	public String getPersistentID() {
		return persistentID;
	}

	public void setPersistentID(String persistentID) {
		this.persistentID = persistentID;
	}
	
	@JsonIgnore
	public List<Playlist> getPlaylistTrack(){
		return this.playlist;
		
	}
	
	public void setPlaylistTracks(List<Playlist> p){
		this.playlist = p;
	}

}
