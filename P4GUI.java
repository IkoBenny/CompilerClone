/*Benny Iko
 * 3/2/2020
 * Data Structures and Algorithms
 * CMSC 350 6383
 * P4GUI.Java
 * a program that behaves like the Java command line compiler.
 * The GUI is generated by code that I wrote. I did
 * not use a drag-and-drop GUI generator.Pressing the 
 * Build Directed Graph button should cause 
 * the specified input file that contains
 * class dependency information to be read in 
 * and the directed graph represented by those dependencies to be built. 
 */

import java.awt.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import javax.swing.border.Border;
import java.io.*;
import java.util.*;

public class P4GUI<T> extends JFrame{
    //GUI dimenisions and panel layouts
    private static final int WIDTH = 500, HEIGHT = 225;  
    private JPanel topHalf; 
    private JPanel bottomHalf;
    private JPanel firstSection;
    private JPanel secondSection;
    //GUI components
    private JLabel fileName;
    private JLabel classesToBeRecompiled;
    private JTextField fileNameField;
    private JTextField targetClassField; 
    private JButton buildGraphButton;
    private JButton topOrderButton;    
    //Text Area Field and DGraph
    private JTextArea recompOrderOutputField;
    private DGraph proj4Graph;
    private String userInput;
    private String userInput2;
    //main method
    public static void main (String [] args){             
        //instantiate GUI and make visible
        //create variable for input       
        P4GUI proj4 = new P4GUI();       
        proj4.display();         
    }
      //class constructor
    public P4GUI(){
        //create labels, butons and fields 
        super("Class Dependency Graph"); 
        fileName = new JLabel("Input file name: ");
        classesToBeRecompiled = new JLabel("Class to recompile: ");
        fileNameField = new JTextField(30);
        targetClassField = new JTextField(30); 
        buildGraphButton = new JButton("Build Directed Graph");
        topOrderButton = new JButton("Topological Order");
        //crete border and text area
        Border orderLabel = BorderFactory.createTitledBorder("Recompilation Order");
        recompOrderOutputField = new JTextArea(5, 20);      
        //create sections
        proj4Graph = new DGraph<String>();
        topHalf = new JPanel();
        bottomHalf = new JPanel();
        firstSection = new JPanel();
        secondSection = new JPanel();
        //add sections to top half of GUI
        topHalf.add(firstSection);
        topHalf.add(secondSection);   
        //add components to top sections
        firstSection.add(fileName);
        firstSection.add(fileNameField);
        firstSection.add(buildGraphButton);
        secondSection.add(classesToBeRecompiled);
        secondSection.add(targetClassField);
        secondSection.add(topOrderButton);        
        //add components to bottom section
        bottomHalf.setBorder(orderLabel);
        bottomHalf.add(recompOrderOutputField);
        //add completed sections to GUI
        add(topHalf);
        add(bottomHalf);
        //set layouts
        setFrame(WIDTH, HEIGHT);  
        setLayout(new GridLayout(3,0));
        topHalf.setLayout(new GridLayout(2,0));
        firstSection.setLayout(new GridLayout(0,3));
        secondSection.setLayout(new GridLayout(0,3));
        bottomHalf.setLayout(new GridLayout(1,0));
        recompOrderOutputField.setEditable(false);      
        //what happens when "Topological Order Button" is pressed
        //if className exists
        //perform topological sort
        //otherwise throw exception
        topOrderButton.addActionListener(new ActionListener(){
    public void actionPerformed(ActionEvent e){
            //error propogation
         try{        
                 userInput2 = targetClassField.getText();
                 recompOrderOutputField.setText(topOrdGeneration(userInput2));                 
            }
             catch (InvalidClassNameException icne){
                 JOptionPane.showMessageDialog(null,icne);               
            }
            }});
            
        //what happens when "Build Graph Button" is pressed
        buildGraphButton.addActionListener(new ActionListener(){ 
    public void actionPerformed(ActionEvent e){ 
             //error propogation
         try{ 
                 //clear previous input
                 //take new input
                 //try to build graph
                 //if file doesnt exist
                 //throw exception
                 proj4Graph.prepareforNew();
                 proj4Graph.checkforCycle();
                 userInput = fileNameField.getText();
                 buildDGraphFromFile(userInput);
                 //show graph
                 proj4Graph.showEverything();
                 JOptionPane.showMessageDialog(null,"Graph Built Successfully");
            }
            
            catch (GraphCycleException gce) {
                 JOptionPane.showMessageDialog(null,"Graph Cycle");
                }
            
             catch (FileNotFoundException fnfe) {
                 JOptionPane.showMessageDialog(null,"File Did Not Open");
                }
            }});}
			
     //this method takes a value and returns corresponding ClassName     
    public String getName (int v){       
        //variables for searching
        Set set = proj4Graph.hm.entrySet();
        Iterator i = set.iterator();
        String key = "";
        //search map and find key with given value 
        //return key
           while(i.hasNext()) {
            Map.Entry me = (Map.Entry)i.next();
            if(me.getValue().equals(v))
            key =  (String)me.getKey();                      
      }      
        return key;
    }
         
    public String topOrdGeneration (String s)throws InvalidClassNameException{
            //variables for generating topological order
            String topOrder ="";
            Set set = proj4Graph.hm.entrySet();
            Iterator i = set.iterator();
            boolean exists = false;
                  
            //if this classname exists create top order
            //otherwise throw exception
            while(i.hasNext()) {
            Map.Entry me = (Map.Entry)i.next();
            if(me.getKey().equals(s)){
                
                //add given ClassName to toporder
                // then get next clasname
                exists = true;                   
                topOrder= (String)me.getKey();
                int start = (Integer)me.getValue();
                int count = 1;
                int count2 =1;
                
                while(count < proj4Graph.getalIndSize(start)){
                int next = proj4Graph.getalIndicies(start,count++);
                //if it has no dependencies 
                //just print it
                if(proj4Graph.getalIndSize(next) == 1){               
                Iterator it = set.iterator();
                while(it.hasNext()) {
                Map.Entry ne = (Map.Entry)it.next();
                if(ne.getValue().equals(proj4Graph.getalIndicies(next, 0)))
                topOrder = topOrder +" "+ ne.getKey();}
            }
            //if recompilation is needed
            //then do so
            else{
                  int valueForaName = proj4Graph.getalIndicies(next,0);                     
                  String newOrder = topOrdGeneration(getName(valueForaName));
                  topOrder = topOrder +" " + newOrder;
                }
    }}}
            if(exists ==false)
            throw new InvalidClassNameException(s);
            
             
        return topOrder;}
    
    public void buildDGraphFromFile (String fName)throws FileNotFoundException{
       //variables for reading input file
       File file = 
       new File(fName); 
       Scanner sc = new Scanner(file); 
       String line ="";     
       //read the entire file
       //line by line
       //insert lines into an ArrayList
        while (sc.hasNextLine()){   
            boolean isClassDependedOn = true;
            line = sc.nextLine(); 
            StringTokenizer tokens = new StringTokenizer(line); 
            proj4Graph.getaLineFromInput(line);}
        //read input 
        //line by line from ArrayList
        //map input to integers
        for(int i =0; i < proj4Graph.getalforLinessize(); i++){
            maptoIntegers(proj4Graph.getalforLinesInput(i));       
        
        }
    
            // iterate through the whole set of keys/values
            //take every entry in the map and create a vertex            
            Set set = proj4Graph.hm.entrySet();
            Iterator i = set.iterator();
            while(i.hasNext()) {
            Map.Entry me = (Map.Entry)i.next();
            int  ind =  (Integer)me.getValue();
            proj4Graph.addVertex(ind);           
            }
        //sort verticies
        proj4Graph.sortVerticies();
      
    
        for(int h =0; h < proj4Graph.getalforLinessize(); h++){
            proj4Graph.skipFirstToken(proj4Graph.getalforLinesInput(h));      
        
        }
    }
    
    public void maptoIntegers(String s){
         boolean isDependedOn = true;      
         String line = s; 
         StringTokenizer tokens = new StringTokenizer(line);
         String token ="";
        
         while (tokens.hasMoreTokens()){
             token = tokens.nextToken(); 
              if(proj4Graph.findDuplicates(token) == true){
              // System.out.println("duplicate" + token);
                  continue;
              
            }
              else{
              if(isDependedOn == true){
               //map it, make head of a list
                proj4Graph.addtoMap(token);
                isDependedOn = false;
                // System.out.println("is first:" + token);                                
                }
            else
            proj4Graph.addtoMap(token);
            }
    }}       
    //Show GUI
    public void display(){
      setVisible(true);
   }
    //Set GUI dimensions 
    private void setFrame(int width, int height){
      setSize(width, height);     
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
   }
}