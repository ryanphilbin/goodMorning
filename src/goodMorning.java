/*
    This project was created to display a "good morning" screen to the user
    that has information that someone may want to see as they wake up.
    Currently, this information includes:
    - Time
    - Date
    - Current Weather

    The information will be displayed on a pop-up window, and will probably  
    not have any other utility. I will work on some of my JavaFX skills
    while building this window.

    Retrieving the current weather based on the user's MANUALLY entered location
    is helping me utilize an API for my first time. I have also learned
    how to parse a .xml file for specific information, since the API call
    returns the weather information in .xml format.

    Eventually, I would like for it also to include:
    - 
    - 
    - 

*/

import java.io.*;
import java.net.*;
import java.time.*;
import java.time.format.DateTimeFormatter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


public class goodMorning {

    public static void main(String args[]) {

        // Store names of months
        String[] months = {
            "January", "February", "March", "April",
            "May", "June", "July", "August", 
            "September", "October", "November", "December"
        };
        
        // Create Date and Time formats and get current time
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter time = DateTimeFormatter.ofPattern("\th:mm.ss a");
        String curDate = "\t"+months[now.getMonthValue()-1] + " " +now.getDayOfMonth() 
                         + ", "+  now.getYear();
        String curTime = time.format(now);

        // Display current date and time to console
        System.out.println();
        System.out.println("Good Morning, It is currently: ");
        System.out.println(curTime);
        System.out.println(curDate);
        System.out.println();

        // Moving on to the weather portion
        // Display current temperature and weather type
        // Display high and low for day

        // OpenWeatherMap API Connection
        String apiKey = "bbf444a810b821ffa6c703cfb0521459";
        String location = "32817,us"; // api call by zipcode and country
        String urlString = 
        "http://api.openweathermap.org/data/2.5/weather?zip="+location+"&appid="+apiKey+"&units=imperial&mode=xml";
        // connect to api and read input (xml or json)
        URL url = null;
        try { // try for opening url

            url = new URL(urlString);
            URLConnection conn = url.openConnection();
            
            // parse xml file
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            try { // try for creating docBuilder and doc

                // Parse xml file and return as DOM Document
                DocumentBuilder db = factory.newDocumentBuilder();
                Document doc = db.parse(conn.getInputStream());

                // normalize xml file
                doc.getDocumentElement().normalize();

                // get information from document using NodeList and Node from org.w3c
                NodeList cities = doc.getElementsByTagName("city");
                Node thisCity = cities.item(0);
                NodeList temps = doc.getElementsByTagName("temperature");
                Node temp = temps.item(0);
                String city = "", curTemp = "";
                if(thisCity.getNodeType() == Node.ELEMENT_NODE) {
                    Element cityE = (Element) thisCity;
                    Element tempE = (Element) temp;
                    System.out.println("\tCity Name: " + cityE.getAttribute("name"));
                    System.out.println("\tCurrent Temp: " + tempE.getAttribute("value") + '\u00B0' + 'F');
                }

            } catch (ParserConfigurationException e) {
                e.printStackTrace();
            } catch (SAXException e) {
                e.printStackTrace();
            }
            System.out.println();
            
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } // end try catch for url's
        
        
    }//end main


}//end goodMorning class