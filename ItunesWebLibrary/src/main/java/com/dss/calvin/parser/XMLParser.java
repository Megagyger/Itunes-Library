package com.dss.calvin.parser;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.ejb.EJB;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.dss.calvin.entity.Track;
import com.dss.calvin.services.TrackServiceLocal;

public class XMLParser {

	private Node tracksDict;
	private Node arrayPlaylist;
	private Document doc;

	@EJB
	private TrackServiceLocal service;

	public XMLParser() throws ParserConfigurationException, SAXException,
			IOException, ParseException {
		addFromFile();
	}

	public void addFromFile() throws ParserConfigurationException,
			SAXException, IOException, ParseException {

		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		dbf.setValidating(true);
		dbf.setNamespaceAware(true);
		dbf.setIgnoringElementContentWhitespace(true);
		doc = db.parse(new File("C:/Users/Calvin21/Documents/library.xml"));
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
		System.out.println(pID);
		System.out.println(ppID);
		for (String st : playlistTrackIDList) {
			System.out.print(st + ":");
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
			System.out.println(trackID + " " + name + " " + artist + " "
					+ album + " " + genre + " " + pID);

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date d = sdf.parse("2012-02-02");
			java.sql.Date sqlDate = new java.sql.Date(d.getTime());

			// service.addTrack(new Track(Integer.valueOf(trackID), name,
			// artist, album,
			// genre, Integer.valueOf(size),sqlDate,pID ));

			service.addTrack(new Track(1111, "name", "artist", "geb", "albuj",
					1024, sqlDate, "pid"));

		}

	}

	public static void main(String[] args) throws ParserConfigurationException,
			SAXException, IOException, ParseException {
		XMLParser xmlParser = new XMLParser();

	}

}
