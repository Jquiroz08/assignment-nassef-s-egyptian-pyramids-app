package com.example;

import java.util.*;
import org.json.simple.*;

public class EgyptianPyramidsAppExample {


  // I've used two arrays here for O(1) reading of the pharaohs and pyramids.
  // other structures or additional structures can be used
  protected Pharaoh[] pharaohArray;
  protected Pyramid[] pyramidArray;

  HashMap<String, String[]> contributors = new HashMap<String, String[]>();
  Set<Integer> listOfPyramids = new HashSet<>();

  public static void main(String[] args) {
    // create and start the app
    EgyptianPyramidsAppExample app = new EgyptianPyramidsAppExample();
    app.start();
  }

  // main loop for app
  public void start() {
    Scanner scan = new Scanner(System.in);
    Character command = '_';

    // loop until user quits
    while (command != 'q') {
      printMenu();
      System.out.print("Enter a command: ");
      command = menuGetCommand(scan);

      executeCommand(scan, command);
    }
  }

  // constructor to initialize the app and read commands
  public EgyptianPyramidsAppExample() {
    // read egyptian pharaohs
    String pharaohFile =
      "C:\\Users\\josep\\OneDrive\\Desktop\\Github\\assignment-nassef-s-egyptian-pyramids-app\\src\\demo\\src\\main\\java\\com\\example\\pharaoh.json";
    JSONArray pharaohJSONArray = JSONFile.readArray(pharaohFile);

    // create and intialize the pharaoh array
    initializePharaoh(pharaohJSONArray);

    // read pyramids
    String pyramidFile =
      "C:\\Users\\josep\\OneDrive\\Desktop\\Github\\assignment-nassef-s-egyptian-pyramids-app\\src\\demo\\src\\main\\java\\com\\example\\pyramid.json";
    JSONArray pyramidJSONArray = JSONFile.readArray(pyramidFile);

    // create and initialize the pyramid array
    initializePyramid(pyramidJSONArray);

  }

  // initialize the pharaoh array
  private void initializePharaoh(JSONArray pharaohJSONArray) {
    // create array and hash map
    pharaohArray = new Pharaoh[pharaohJSONArray.size()];

    // initalize the array
    for (int i = 0; i < pharaohJSONArray.size(); i++) {
      // get the object
      JSONObject o = (JSONObject) pharaohJSONArray.get(i);

      // parse the json object
      Integer id = toInteger(o, "id");
      String name = o.get("name").toString();
      Integer begin = toInteger(o, "begin");
      Integer end = toInteger(o, "end");
      Integer contribution = toInteger(o, "contribution");
      String hieroglyphic = o.get("hieroglyphic").toString();

      // add a new pharoah to array
      Pharaoh p = new Pharaoh(id, name, begin, end, contribution, hieroglyphic);
      pharaohArray[i] = p;
      
      String[] information = {name , Integer.toString(contribution)}; 
      contributors.put(hieroglyphic, information);
    }
  }

  // initialize the pyramid array
  private void initializePyramid(JSONArray pyramidJSONArray) {
      // create array and hash map
      pyramidArray = new Pyramid[pyramidJSONArray.size()];
  
      // initalize the array
      for (int i = 0; i < pyramidJSONArray.size(); i++) {
        // get the object
        JSONObject o = (JSONObject) pyramidJSONArray.get(i);
  
        // parse the json object
        Integer id = toInteger(o, "id");
        String name = o.get("name").toString();
        JSONArray contributorsJSONArray = (JSONArray) o.get("contributors");
        String[] contributors = new String[contributorsJSONArray.size()];
        for (int j = 0; j < contributorsJSONArray.size(); j++) {
          String c = contributorsJSONArray.get(j).toString();
          contributors[j] = c;
        }
        // add a new pyramid to array
        Pyramid p = new Pyramid(id, name, contributors);
        pyramidArray[i] = p;
      }
    }

  // get a integer from a json object, and parse it
  private Integer toInteger(JSONObject o, String key) {
    String s = o.get(key).toString();
    Integer result = Integer.parseInt(s);
    return result;
  }

  // get first character from input
  private static Character menuGetCommand(Scanner scan) {
    Character command = '_';

    String rawInput = scan.nextLine();

    if (rawInput.length() > 0) {
      rawInput = rawInput.toLowerCase();
      command = rawInput.charAt(0);
    }

    return command;
  }

  // print all pharaohs
  private void printAllPharaoh() {
    for (int i = 0; i < pharaohArray.length; i++) {
      printMenuLine();
      pharaohArray[i].print();
      printMenuLine();
    }
  }
  
  private void printAllPyramids(int index, int length){
    while(length>0){
      printMenuLine();
      System.out.printf("Pyramid Name %s\n", pyramidArray[index].name);
      System.out.printf("\tid: %d\n", pyramidArray[index].id);
      int sum = 0;
      for(int j = 0; j < pyramidArray[index].contributors.length; j++){
          System.out.printf("\tContributor %s %s ", j + ": ", contributors.get(pyramidArray[index].contributors[j])[0]);
          System.out.printf("%s gold coins\n", contributors.get(pyramidArray[index].contributors[j])[1]);
          sum = sum + Integer.parseInt(contributors.get(pyramidArray[index].contributors[j])[1]);
      }
      System.out.printf("\tTotal Contribution: %d gold coins\n", sum);
      printMenuLine();
      index++;
      length--;
    }
  }

  private void printAllPyramids(){
    printAllPyramids(0, pyramidArray.length);
  }

  private Boolean executeCommand(Scanner scan, Character command) {
    Boolean success = true;

    switch (command) {
      case '1':
        printAllPharaoh();
        break;
      case '2':
        getPharaoh(scan);
        break;
      case '3':
        printAllPyramids();
        break;
      case '4':
        getPyramid(scan);
        break;
      case '5':
        printList();
        break;
      case 'q':
        System.out.println("Thank you for using Nassef's Egyptian Pyramid App!");
        break;
      default:
        System.out.println("ERROR: Unknown commmand");
        success = false;
    }

    return success;
  }

  private static void printMenuCommand(Character command, String desc) {
    System.out.printf("%s\t\t%s\n", command, desc);
  }

  private static void printMenuLine() {
    System.out.println(
      "--------------------------------------------------------------------------"
    );
  }

  // prints the menu
  public static void printMenu() {
    printMenuLine();
    System.out.println("Nassef's Egyptian Pyramids App");
    printMenuLine();
    System.out.printf("Command\t\tDescription\n");
    System.out.printf("-------\t\t---------------------------------------\n");
    printMenuCommand('1', "List all the pharoahs");
    printMenuCommand('2', "Display a specific Egyptian pharaoh");
    printMenuCommand('3', "List all the pyramids");
    printMenuCommand('4', "Display a specific pyramid");
    printMenuCommand('5', "Display a list of requested pyramids");
    printMenuCommand('q', "Quit");
    printMenuLine();
  }
  
  private void getPharaoh(Scanner scan){
      System.out.println("Please input pharaoh ID");
      String pharaohID = scan.nextLine();
      if(!checkInt(pharaohID) || (Integer.parseInt(pharaohID) < 0 || Integer.parseInt(pharaohID)>= pharaohArray.length)){
        System.out.println("Please input valid pharaoh ID");
        return;
      }
      pharaohArray[Integer.parseInt(pharaohID)].print();
    }

  private void getPyramid(Scanner scan){
    System.out.println("Please input pyramid ID");
    String pyramidID = scan.nextLine();
    if(!checkInt(pyramidID) || (Integer.parseInt(pyramidID) < 0 || Integer.parseInt(pyramidID)>= pyramidArray.length)){
      System.out.println("Please input valid pyramid ID");
      return;
    }
    printAllPyramids(Integer.parseInt(pyramidID), 1);
    listOfPyramids.add(Integer.parseInt(pyramidID));
  }

  private boolean checkInt(String input){
       try{ 
            Integer.parseInt(input); 
       }catch(NumberFormatException e){ 
           return false; 
       }catch(NullPointerException e){
      return false;
  }
  return true;
  }
  
  private void printList(){
    System.out.println("List of Request Pyramids");
    System.out.printf("\t ID \tName\n");
    System.out.printf("\t --- \t------------\n");
    for (Integer id : listOfPyramids) {
      System.out.printf("\t %S \t %s\n", id, pyramidArray[id].name); 
    }
  }

  }



