/*Benny Iko
 * 3/3/2020
 * Data Structures and Algorithms
 * CMSC 350 6383
 * GraphCycleException.Java
 * If the graph contains a cycle among the Java classes,
 * a GraphCycleException custom exception
will be thrown causing an appropriate error message
 to be displayed in a JOptionPane.
 */

public class  GraphCycleException extends Exception
{
      public  GraphCycleException(String message){
      super("Not a class name: " + message);
    }
}
