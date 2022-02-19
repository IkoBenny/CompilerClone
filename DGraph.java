/*Benny Iko
 * 3/3/2020
 * Data Structures and Algorithms
 * CMSC 350 6383
 * DGraph.Java
 * Whenever we request that the Java compiler recompile a particular class, 
 * it not only recompiles that class but every other class that depends upon it,
 * directly or indirectly, and in a particular order. 
 * To make the determination about which classes need recompilation, 
 * the Java compiler maintains a directed graph of class dependencies.
 */

import java.util.*;
import java.util.Map.Entry;
import java.util.Set;

public class DGraph<T> {
   HashMap<T, Integer> hm = new HashMap<T,Integer>();
   ArrayList<LinkedList<Integer>> al = new ArrayList<LinkedList<Integer>>();
   ArrayList<String> alforLines = new ArrayList<String>();
   ArrayList<String> classeswithDependencies = new ArrayList<String>();
   static int count = 0;
 
   
   //skips first Token to create dependencies
   public void skipFirstToken(String s){
        String dependedOnLine = s;
        StringTokenizer tokens = new StringTokenizer(dependedOnLine);
        String classDependedOn = tokens.nextToken();
        String currentName = "";
        int index = 0;                     
        
        while(tokens.hasMoreTokens()){
            currentName = tokens.nextToken();
            index = createaValue(currentName);
            addEdge(hm.get(classDependedOn),index);
        }
       
    }
    
    public void prepareforNew(){
              hm.clear();
             al.clear();
             alforLines.clear();
             classeswithDependencies.clear();
    }
    
    public void checkforCycle()throws GraphCycleException{
        String className ="";
        String forCompare = "";
        boolean cycle = false;
        
        //checks all input for class name with back edge
        for(int i =0; i< alforLines.size(); i++){
            className = alforLines.get(i);
            StringTokenizer tokens = new StringTokenizer(className);
            while(tokens.hasMoreTokens()){
                forCompare = tokens.nextToken();
                if(className.equals(forCompare))
                cycle = true;                           
            }
            //if there is a class with a back edge
            //throw exception
            if(cycle == true)
            throw new GraphCycleException("Cycle Occured");
        }
    }
    
    //given a className this method returns the corresponing int value
    public int createaValue(String s){
        //variables for the index and iterating map
        int value =0;
        Set set = hm.entrySet();
        Iterator i = set.iterator();
        //search map and find key with given ClassName then return value
           while(i.hasNext()) {
            Map.Entry me = (Map.Entry)i.next();
            if(me.getKey().equals(s))
            value =  (Integer)me.getValue();                      
      }      
        return value;
    }
   
   
    
   public void determineDependencies(){
       //variables for tokenizing Strings
       
       String line = ""; 
       String token ="";
       //find every class with dependencies
       //save those class names
       for(int i =0; i < alforLines.size(); i++){
           line = alforLines.get(i);   
           StringTokenizer tokens = new StringTokenizer(line);
           token = tokens.nextToken();
           classeswithDependencies.add(token);
           System.out.println(token);
        }
       //iterate those ClassNames
       //link dependent classes to them
        
    }
   //shows linked lists
     public void showEverything(){
       for(int i =0;  i < al.size(); i++){          
         System.out.println(al.get(i));}        
     }
    
   
   //sorts Verticies in ascending order 
   public void sortVerticies(){
       for(int i = al.size()-1; i >1;i--){
           for(int j =0; j <i; j++)
           if(al.get(j).get(0) > al.get(j+1).get(0)){ 
               int temp = al.get(j).get(0);
               al.get(j).set(0,al.get(j+1).get(0));
               al.get(j+1).set(0,temp);
        }
    }}
   
   public <T> DGraph(){
       // adjacentList = new ArrayList<LinkedList<Integer>>();
       //TreeMap map = new TreeMap<T, Integer>();     
    }
    //counter variable to keep track of Classes
    public static int index(){
        return count++;
    }
    //returns size of list with input from user
    public int getalforLinessize(){
        return alforLines.size();
    }
    
    public int getalIndSize(int i){
        return al.get(i).size();
    }
    
    
     public int getalSize(){
        return al.size();
    }
    
    public int getalIndicies(int i, int j){
        return al.get(i).get(j);
    }
    
    public int classeswithDependenciesSize(){
        return classeswithDependencies.size();
    }
    //returns a line from the input file
    public String getalforLinesInput(int i){
        return alforLines.get(i);
    }
    
    public String getadependedClass(int i){
        return classeswithDependencies.get(i);
    }
    //adds line from file to ArrayList for later
   public void getaLineFromInput(String t){
       alforLines.add(t);
    }       
   //prints user input from stored array
   public void printInput(){        
        for(int i =0; i < alforLines.size(); i++)
        System.out.println(alforLines.get(i));
        }
    //searches for Class instance, returns true if it already exists
   public boolean findDuplicates (T t){
       // Set<T> keys = hm.keySet();           
       return hm.containsKey(t);
    }
    //displays all keys/values in map
    public void printMap(){        
        Set<T> keys = hm.keySet();
        for(T t:keys){
            System.out.println(t+"==>"+hm.get(t));
        }}
    //maps a Classname to an integer value
   public void addtoMap(T t){       
        hm.put(t,index());
    }
        //creates a new vertex
     public void addVertex(int i){
           LinkedList <Integer> vertex = new LinkedList<Integer>();
           vertex.addFirst(i);
           addVertextoAL(vertex);
        } 
        //adds vertex represented by LinkedList to ArrayList
      public void addVertextoAL(LinkedList<Integer> v){
           al.add(v);
        }   
       public void addEdge(int i, int j){
           al.get(i).add(j);
        }
       
        
       // ... other methods (if necessary)}
    }