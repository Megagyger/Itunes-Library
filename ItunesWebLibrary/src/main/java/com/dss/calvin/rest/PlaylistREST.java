package com.dss.calvin.rest;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;

import javax.ejb.EJB;
import javax.ws.rs.DELETE;
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

@Path("/playlist")
public class PlaylistREST {

	@EJB
	private PlaylistServiceLocal service;

	private Node arrayPlaylist;
	private Document doc;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Playlist> getAllFromPlaylistTable() {
		return service.getAllPlaylistsFromTable();
	}

	@GET
	@Path("/add")
	public void addPlaylist() throws SAXException, IOException,
			ParserConfigurationException, ParseException {

		parseXML();
	}
	
	@DELETE
	@Path("/deletePList/{playlist}")
	public void deletePlaylist(@PathParam("playlist") String playlist){
		Integer plistId = Integer.parseInt(playlist);
		service.deletePlaylist(plistId);
	}
	
	@GET
	@Path("/createPlist/{plist}")
	public void createPlaylist(@PathParam("plist") String plist) {
		Integer pListId = Integer.parseInt(plist.split("£")[0]);
		String persistId = plist.split("£")[1];
		Integer trackId = Integer.parseInt(plist.split("£")[2]);
		
		service.createPlaylist(new Playlist(pListId, persistId, trackId));
		
	}
	
	@GET
	@Path("/playlistIds")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Integer> getAllPlaylistIds(){
		return service.getAllPlaylistIds();
		
	}
	
	@GET
	@Path("/addTrack/{plistTrack}")
	@Produces(MediaType.APPLICATION_JSON)
	public void addTrackToPlaylist(@PathParam("plistTrack") String plistTrack) {
		String[] x = plistTrack.split("£");
		Integer listId = Integer.parseInt(x[0]);
		Integer trackId = Integer.parseInt(x[1]);
		String persistId = x[2];
		service.addPlaylist(new Playlist(listId, persistId,
				trackId));
	}
	
	@DELETE
	@Path("/delete/{tIdPId}")
	public void deleteTrackFromPlaylist(@PathParam("tIdPId") String tIdPId){
		String[] x = tIdPId.split("£");	
		service.deleteTrackFromPlaylist(Integer.parseInt(x[0]), Integer.parseInt(x[1]),x[2]);
	}

	@GET
	@Path("/playListIDs/{ppID}")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Integer> getAllPlaylistIDsFromPlaylistPersistID(
			@PathParam("ppID") String persistenceID) {
		return service.getAllPlaylistIDsFromPlaylistPersistID(persistenceID);

	}

	@GET
	@Path("/persistedTracks/{ppID}")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<String> getAllTracksFromPlaylistPersistID(
			@PathParam("ppID") String playlistPersistID) {
		return service.getAllTracksFromPlaylistPersistID(playlistPersistID);

	}

	@GET
	@Path("/playlistTracks/{pID}")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Object> getAllTracksFromAPlaylistID(
			@PathParam("pID") String playlistID) {
		return service
				.getAllTracksFromAPlaylistID(Integer.parseInt(playlistID));
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

		NodeList childrenArrayPlayList = arrayPlaylist.getChildNodes();

		// Getting childList elements and ignoring empty #textNodes as I
		// can't get the parser to ignore white space
		ArrayList<Node> listOfPlayListArrayChilds = new ArrayList<Node>();

		for (int i = 0; i < childrenArrayPlayList.getLength(); i++) {
			if (childrenArrayPlayList.item(i).getNodeName().equals("dict")) {
				listOfPlayListArrayChilds.add(childrenArrayPlayList.item(i));
			}
		}

		// Track info for each trackDict and playListArray
		// getTrackInformation(listOfTrackInformationDict);
		getPlaylistInformation(listOfPlayListArrayChilds);

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

			if (childrenOfContainer.item(i).getNodeName().equals("array")) {
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
			if (st != null) {
				service.addPlaylist(new Playlist(Integer.valueOf(pID), ppID,
						Integer.valueOf(st)));
			}
		}

	}

}
