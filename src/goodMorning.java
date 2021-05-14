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
    - Trending topics on Twitter (What you missed.. as you slept)
    - Top News stories of the day (BBC might have a decent api)
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
        
        // Get current date and time
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter time = DateTimeFormatter.ofPattern("\th:mm.ss a");
        String curDate = "\t"+months[now.getMonthValue()-1] + " " +now.getDayOfMonth() 
                         + ", "+  now.getYear();
        String curTime = time.format(now);
        int hour = now.getHour();

        // Display current date and time to console
        System.out.println();
        // send appropriate greeting
        if(hour < 12)
            System.out.println("Good Morning, It is currently: ");
        else if(hour < 17)
            System.out.println("Good Afternoon, It is currently: ");
        else 
            System.out.println("Good Evening, It is currently: ");
        System.out.println(curTime);
        System.out.println(curDate);
        System.out.println();

        // Moving on to the weather portion
        // Display current temperature and weather type
        // Display high and low for day

        // OpenWeatherMap API Connection
        String apiKey = "bbf444a810b821ffa6c703cfb0521459";
        String location = "33647,us"; // api call by zipcode and country
        String urlString = 
        "http://api.openweathermap.org/data/2.5/weather?zip="+location+"&appid="+apiKey+"&units=imperial&mode=xml";
        // connect to api and read input from .xml file
        URL url = null;
        try { // try for opening url and connection

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
                NodeList city = doc.getElementsByTagName("city");
                Node cityNode = city.item(0);
                NodeList temps = doc.getElementsByTagName("temperature");
                Node tempNode = temps.item(0);
                NodeList weather = doc.getElementsByTagName("weather");
                Node weatherNode = weather.item(0);
                String curCity = "", curTemp = "", weatherCode = "";
                
                if(cityNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element cityE = (Element) cityNode;
                    curCity = cityE.getAttribute("name");
                    Element tempE = (Element) tempNode;
                    curTemp = tempE.getAttribute("value") + '\u00B0' + 'F';
                    Element weatherE = (Element) weatherNode;
                    weatherCode = weatherE.getAttribute("number");
                }
                printWeather(curCity, curTemp, weatherCode);
                
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

        // Start building the Javafx GUI part of this program
        
        
    }//end main

    public static void printWeather(String city, String temp, String code) {
        // this method takes the weather code and outputs the correct
        // type of weather according to the "Weather Condition Codes"
        // on the OpenWeatherMap API website

        // print first part of weather reading
        System.out.print("\t" + city + " is currently " + temp + "\n\t");

        // print appropriate second part of weather reading according to weather code
        switch(code) {

            // thunderstorms
            case "200":
            case "210":
            case "230":
                System.out.println(" with light thunderstorms.");
                break;
            case "201":
            case "211":
            case "231":
                System.out.println(" with moderate thunderstorms.");
                break;
            case "202":
            case "212":
            case "232":
                System.out.println(" with heavy thunderstorms.");
                break;
            case "221":
                System.out.println(" with ragged thunderstorms.");
                break;

            // rain
            case "300":
            case "310":
            case "500":
            case "520":
                System.out.println(" with light rain.");
                break;
            case "301":
            case "311":
            case "313":
            case "321":
            case "501":
            case "521":
                System.out.println(" with moderate rain.");
                break;
            case "302":
            case "312":
            case "314":
            case "502":
            case "522":
                System.out.println(" with heavy rain.");
                break;
            case "503":
                System.out.println(" with very heavy rain.");
                break;
            case "504":
                System.out.println(" with extreme rain.");
                break;
            case "511":
                System.out.println(" with freezing rain.");
                break;
            case "531":
                System.out.println(" with ragged rain showers.");
                break;

            // snow
            case "600":
            case "620":
                System.out.println(" with light snow flurries.");
                break;
            case "601":
            case "621":
                System.out.println(" with moderate snowfall.");
                break;
            case "602":
            case "622":
                System.out.println(" with heavy snowfall.");
                break;
            case "611":
                System.out.println(" with sleet.");
                break;
            case "612":
                System.out.println(" with light shower sleet.");
                break;
            case "613":
                System.out.println(" with shower sleet.");
                break;
            case "615":
                System.out.println(" light rain and snow.");
                break;
            case "616":
                System.out.println(" with rain and snow.");
                break;
            
            // atmosphere
            case "701":
                System.out.println(" with mist in the air.");
                break;
            case "711":
                System.out.println(" with smoke in the air.");
                break;
            case "721":
                System.out.println(" with a slight haze in the air.");
                break;
            case "731":
            case "761":
                System.out.println(" with dust in the air.");
                break;
            case "741":
                System.out.println(" and foggy.");
                break;
            case "751":
                System.out.println(" with sand in the air");
                break;
            case "762":
                System.out.println(" with volcanic ash in the air.");
                break;
            case "771":
                System.out.println(" with squalls of wind.");
                break;
            case "781":
                System.out.println(" with tornadoes in the area.");
                break;
            
            // cloud coverage
            case "800":
                System.out.println(" with clear, blue skies.");
                break;
            case "801":
                System.out.println(" with few clouds.");
                break;
            case "802":
                System.out.println(" with scattered clouds.");
                break;
            case "803":
                System.out.println(" and mostly cloudy.");
                break;
            
            default:
                System.out.println(" *ERROR* : printWeather() - DEFAULT");
                break;
        }//end switch

    }//end printWeather


}//end goodMorning class