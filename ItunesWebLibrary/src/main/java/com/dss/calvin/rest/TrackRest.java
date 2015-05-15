package com.dss.calvin.rest;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.dss.calvin.entity.Playlist;
import com.dss.calvin.entity.Track;
import com.dss.calvin.services.PlaylistServiceLocal;
import com.dss.calvin.services.TrackServiceLocal;

@Path("/track")
public class TrackRest {

	@EJB
	private TrackServiceLocal service;
	private PlaylistServiceLocal serviceP;

	private Node tracksDict;
	private Node arrayPlaylist;
	private Document doc;
	
	@GET
	@Path("update/{trackUpdate : .+}")
	@Produces(MediaType.APPLICATION_JSON)
	public void updateTrack(@PathParam("trackUpdate") String trackUpdate) {
		String[] update = trackUpdate.split("£");
		Integer tID = Integer.parseInt(update[0]);
		String name = update[1];
		String artist = update[2];
		String album = update[3];
		String genre = update[4];
		service.updateTrack(tID, name, artist, album, genre);
		
	}
	
	@GET
	@Path("trackById/{trackID}")
	@Produces(MediaType.APPLICATION_JSON)
	public Track getByTrackID(@PathParam("trackID") String trackID) {
		return service.getTrackById(Integer.parseInt(trackID));
	}
	
	@GET
	@Path("tracksEdit/{persistID}")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Track> getAllTracksWithPersistID(@PathParam("persistID") String persistID) {
		return service.getAllTracksWithPersistID(persistID);
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Track> getAllFromTrackTable() {
		return service.getAllFromTracksTable();
	}

	@GET
	@Path("/add")
	public void addFromFile() throws ParserConfigurationException,
			SAXException, IOException, ParseException {
		parseXML();

	}
	
	@GET
	@Path("/addTrackByUser/{track : .+}")
	public void addTrackByUser(@PathParam("track") String track){
		String[] trackInfo = track.split("£");
		Integer id = Integer.parseInt(trackInfo[0]);
		String name = trackInfo[1];
		String artist = trackInfo[2];
		String album = trackInfo[3];
		String genre = trackInfo[4];
		String pid = trackInfo[5];
		Integer size = 1024;
		Date today =new Date(Calendar.getInstance().getTimeInMillis());
		java.sql.Date timeNow = new java.sql.Date(today.getTime());
		Track trackToAdd = new Track(id, name, artist, album, genre, size, timeNow, pid);
		
		service.addTrack(trackToAdd);
	}

	@GET
	@Path("/artist/{artist}")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Track> getAllTracksByArtist(
			@PathParam("artist") String artist) {
		return service.getAllTracksByArtist(artist);
	}

	@GET
	@Path("/genre/{genre : .+}")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Track> getAllTracksByGenre(
			@PathParam("genre") String genre) {
		return service.getAllTracksByGenre(genre);
	}
	
	@GET
	@Path("/album/{album}")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Track> getAllTracksByAlbum(@PathParam("album") String album){
		return service.getAllTracksByAlbum(album);
	}
	
	@GET
	@Path("/artists")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection getArtistListFromTracks(){
		return service.getArtistListFromTracks();
	}
	
	@GET
	@Path("/albums")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection getAlbumListFromTracks(){
		return service.getAlbumListFromTracks();
	}
	
	@GET
	@Path("/genres")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection getGenreListFromTracks(){
		return service.getGenreListFromTracks();
	}

	public void parseXML() throws SAXException, IOException,
			ParserConfigurationException, ParseException {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		dbf.setValidating(true);
		dbf.setNamespaceAware(true);
		dbf.setIgnoringElementContentWhitespace(true);
		doc = db.parse(new File("C:/Users/Calvin21/Documents/Library1.xml"));
		doc.getDocumentElement().normalize();

		// Work through nodes finding the elements who have children we need
		// and add to appropriate list
		findAndInstantiateTrackDictAndPlaylistArray(findTheRootNodeReturnContainerNodeList(doc));
		NodeList childrenOfTracksDict = tracksDict.getChildNodes();
		NodeList childrenArrayPlayList = arrayPlaylist.getChildNodes();

		// Getting childList elements and ignoring empty #textNodes as I
		// can't get the parser to ignore white space
		ArrayList<Node> listOfTrackInformationDict = new ArrayList<Node>();
		ArrayList<Node> listOfPlayListArrayChilds = new ArrayList<Node>();

		for (int i = 0; i < childrenOfTracksDict.getLength(); i++) {

			if (childrenOfTracksDict.item(i).getNodeName().equals("dict")) {
				listOfTrackInformationDict.add(childrenOfTracksDict.item(i));
			}
		}

		for (int i = 0; i < childrenArrayPlayList.getLength(); i++) {
			if (childrenArrayPlayList.item(i).getNodeName().equals("dict")) {
				listOfPlayListArrayChilds.add(childrenArrayPlayList.item(i));
			}
		}

		// Track info for each trackDict and playListArray
		getTrackInformation(listOfTrackInformationDict);
		//getPlaylistInformation(listOfPlayListArrayChilds);

	}

	private void getPlaylistInformation(
			ArrayList<Node> listOfPlayListArrayChilds) {
		// Dicts holding a playlist each
		for (Node n : listOfPlayListArrayChilds) {
			// nodeList of each playlist
			NodeList nl = n.getChildNodes();

			// Getting the playListID, playListPersistID and the playlist tracks
			String pID = null, ppID = null;
			Node arrayOfPlayListTracks = null;
			for (int i = 0; i < nl.getLength(); i++) {
				if (nl.item(i).getTextContent().equals("Playlist ID")) {
					pID = nl.item(i).getNextSibling().getTextContent();
				} else if (nl.item(i).getTextContent()
						.equals("Playlist Persistent ID")) {
					ppID = nl.item(i).getNextSibling().getTextContent();
				} else if (nl.item(i).getNodeName().equals("array")) {
					arrayOfPlayListTracks = nl.item(i);
				}

			}

			extractPlaylistTrackIDs(pID, ppID, arrayOfPlayListTracks);
		}

	}

	private NodeList findTheRootNodeReturnContainerNodeList(Document doc2) {
		Node plist = doc.getDocumentElement();
		NodeList childrenOfRoot = plist.getChildNodes();

		Node containingDict = childrenOfRoot.item(1);
		NodeList childrenOfContainer = containingDict.getChildNodes();
		return childrenOfContainer;

	}

	private void findAndInstantiateTrackDictAndPlaylistArray(
			NodeList childrenOfContainer) {
		for (int i = 0; i < childrenOfContainer.getLength(); i++) {

			if (childrenOfContainer.item(i).getNodeName().equals("dict")) {
				tracksDict = childrenOfContainer.item(i);
			} else if (childrenOfContainer.item(i).getNodeName()
					.equals("array")) {
				arrayPlaylist = childrenOfContainer.item(i);
			}
		}

	}

	private void extractPlaylistTrackIDs(String pID, String ppID,
			Node arrayOfTrackDicts) {
		ArrayList<Node> playlistSingleTrackDict = new ArrayList<Node>();
		ArrayList<String> playlistTrackIDList = new ArrayList<String>();

		if (arrayOfTrackDicts != null) {
			NodeList nl = arrayOfTrackDicts.getChildNodes();
			for (int i = 0; i < nl.getLength(); i++) {
				if (nl.item(i).getNodeName().equals("dict")) {
					playlistSingleTrackDict.add(nl.item(i));
				}
			}
		}
		// we have tracks for each playlist
		for (Node n : playlistSingleTrackDict) {
			NodeList nodelist = n.getChildNodes();
			for (int i = 0; i < nodelist.getLength(); i++) {
				// Now add all playlist trackIDs to list
				if (nodelist.item(i).getTextContent().equals("Track ID")) {
					playlistTrackIDList.add(nodelist.item(i).getNextSibling()
							.getTextContent());

				}
			}
		}

		// We can finally persist our playlists
		for (String st : playlistTrackIDList) {
			serviceP.addPlaylist(new Playlist(Integer.valueOf(pID), ppID,
					Integer.valueOf(st)));
		}

	}

	private void getTrackInformation(ArrayList<Node> list)
			throws ParseException {
		String trackID = null, name = null, artist = null, album = null, genre = null, size = null, date = null, pID = null;
		for (Node n : list) {
			NodeList listOfTrackKeyValueElements = n.getChildNodes();
			for (int i = 0; i < listOfTrackKeyValueElements.getLength(); i++) {
				if (listOfTrackKeyValueElements.item(i).getTextContent()
						.equals("Track ID")) {
					trackID = listOfTrackKeyValueElements.item(i)
							.getNextSibling().getTextContent();
				} else if (listOfTrackKeyValueElements.item(i).getTextContent()
						.equals("Name")) {
					name = listOfTrackKeyValueElements.item(i).getNextSibling()
							.getTextContent();
				} else if (listOfTrackKeyValueElements.item(i).getTextContent()
						.equals("Artist")) {
					artist = listOfTrackKeyValueElements.item(i)
							.getNextSibling().getTextContent();
				} else if (listOfTrackKeyValueElements.item(i).getTextContent()
						.equals("Album")) {
					album = listOfTrackKeyValueElements.item(i)
							.getNextSibling().getTextContent();
				} else if (listOfTrackKeyValueElements.item(i).getTextContent()
						.equals("Genre")) {
					genre = listOfTrackKeyValueElements.item(i)
							.getNextSibling().getTextContent();
				} else if (listOfTrackKeyValueElements.item(i).getTextContent()
						.equals("Size")) {
					size = listOfTrackKeyValueElements.item(i).getNextSibling()
							.getTextContent();
				} else if (listOfTrackKeyValueElements.item(i).getTextContent()
						.equals("Date Added")) {
					date = listOfTrackKeyValueElements.item(i).getNextSibling()
							.getTextContent();
				} else if (listOfTrackKeyValueElements.item(i).getTextContent()
						.equals("Persistent ID")) {
					pID = listOfTrackKeyValueElements.item(i).getNextSibling()
							.getTextContent();
				}
			}

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date d = sdf.parse("2012-02-02");
			java.sql.Date sqlDate = new java.sql.Date(d.getTime());

			service.addTrack(new Track(Integer.valueOf(trackID), name, artist,
					album, genre, Integer.valueOf(size), sqlDate, pID));

		}

	}

}
