/*Benny Iko
 * 3/3/2020
 * Data Structures and Algorithms
 * CMSC 350 6383
 * InvalidClassNameException.Java
 * An invalid class name should generate an InvalidClassNameException
 custom exception causing an appropriate error message to be displayed in a JOptionPane.
 */

public class InvalidClassNameException extends Exception
{
      public InvalidClassNameException(String message){
      super("Not a class name: " + message);
    }
}
