/*******
NOTE:
this is a very old code, it may have logical or syntax errors contained within as I'm not sure if it was the final version.
At this point, it's mostly for archive purposes.  

********/

/*******************************************************
Jerry Kensler
Final Project cs110
Resonance calculator
Fall 2014

Potential errors:
   entering non integers during the integer only input areas
   entering too high of a number or octave will produce an out of bounds error
   entering too high of an octaves integer that isn't out of bounds will produce an infinity error
   
   decision structures are using user input as string and then comparing them to avoid needless errors
   
Other notes:
   resonace length in an open tube number may be a slightly off due to having to tool out most of the formulas myself but overall, as a proof of concept it works
      should be close enough to actually make an instrument, just make sure to tune it.  I suggest schedule 40 pvc as opposed to wood for the first few tries  

*******************************************************/

import java.util.Scanner;
import java.util.Random;
import java.util.Arrays;
import java.io.*; 

public class FinalProjectMainEcho { 

//######################################################################################
   
   public static void IntroMessage(){
      System.out.println("This program calculates frequencies and their fundemental resonance lengths based upon user input");
      System.out.println("The values this program outputs can be directly applied to create simple musical instruments such as flutes");
      System.out.println("There will be a bit of a margin of error due to some aspects not being included in the equation");
      System.out.println("so make sure to measure results and tune accordingly");
   }
   
//#####################################################################################

   public static double[][] CalculateScale(int octavesRequired, int baseNote){
      
      System.out.println(" ");  
      System.out.println("The values of the new scale based upon your given frequency are as follows: ");
      System.out.println(" ");  
      
      
      double[][] scaleArray = new double [octavesRequired][12];
      
      int octaveInt = 2; //default one octave
      int alteredBaseNote = baseNote; //not needed just wanted for sanity sake
      
      
      for (int i = 0; i < octavesRequired; i++){
         
         
         if (i > 0){
            octaveInt = 1; //doubles the base note to calculate each octave
         }
         else{
            octaveInt = 2; //non doubling
         }
      
      /*
      MATH:
      
      basenote x 2^(n/12) is how you get notes
      http://www.intmath.com/trigonometric-graphs/music.php
      Math.pow(a,b) = a ^ b
      
      */
 
         double exponentVariable = 1;
      
         switch (octaveInt){
            case 1: //goes one octave higher
               alteredBaseNote = alteredBaseNote * 2; 
            case 2: //base note for each octave
               scaleArray[i][0] = alteredBaseNote;
               
               for (int j = 0; j < 12; j++){
                  exponentVariable = (double)j/12; //makes things nicer
                  scaleArray[i][j] = alteredBaseNote * Math.pow(2,exponentVariable);//uses incrementing in a loop to get the notes via math
                  System.out.println(scaleArray[i][j] + " Hertz"); //prints out the values for the user
               }//end loop 
            
               break;
         }//end case
      }//end loop
         
      
      return scaleArray;
      
   }
//######################################################################################
   
   public static int RandomNoteNeeded(){
      int randomBaseNote = 100;
      Random randomNumbers = new Random();
      randomBaseNote = randomNumbers.nextInt(301)+ 200;
      
      return randomBaseNote; //method returns random int between 200 and 500
   }

//######################################################################################
   public static void PrintSave(double[][] scaleArray, double[][] resonanceArray) throws IOException{
      System.out.println(" ");
      System.out.println("Would you like to save these values to a text file?");
      System.out.println("Enter Y for yes or anything else to exit");
      
      Scanner user_input = new Scanner(System.in); 
      String Answer = user_input.next(); //grabs user input as string to avoid errors, uses .equals(), any non specified answer is treated as close program desired
      
      if (Answer.toLowerCase().equals("y")){
         
         String fileName = "results.txt";
         PrintWriter fileToPrintTo = new PrintWriter(fileName);
         
         for (int i = 0; i < resonanceArray.length; i++){
            for (int j = 0; j < 12; j++){
               fileToPrintTo.println ("For the note: " + scaleArray[i][j] + " hertz, the fundemental resonance length in an open tube is: " + resonanceArray[i][j] + " cm"); //iterates through the arrays and prints new lines with each
               fileToPrintTo.println (" ");
            }
         }
          
         System.out.println("The results have been saved to the program's parent directory");
         
         fileToPrintTo.close();                 
         System.exit(0);
      }
      
      else{
         System.exit(0);
      }//end if statement
   
   
   } //saves to file in main directory
   
//######################################################################################

   public static double[][] ResonanceConverter(double[][] scaleArray){
      //steps:
      //
      //http://newt.phys.unsw.edu.au/jw/fluteacoustics.html
      //http://newt.phys.unsw.edu.au/jw/woodwind.html
      //Speed of sound in air at a temperature of 20°C: c = 34,357.31 cm/s or 13,526.5 inches/s
      //
      // 2 * length / speed of sound = lowest note in open tube
      // 34,357.31/frequency = resonance length //remember unit conversions
      
      double[][] resonanceArray = new double [scaleArray.length][12];
      
      System.out.println(" ");
      System.out.println("The resonance lengths in an open tube (from low to high) for your given scale are as follows: ");
      System.out.println(" ");
          
      for (int i = 0; i < scaleArray.length; i++){
         
         
         for (int j = 0; j < 12; j++){
            resonanceArray[i][j] = (34357.31 / scaleArray[i][j]) / 2;
            System.out.println(resonanceArray[i][j] + " cm");
         }   
                
         
      }//end loop
      //as a note, this damned resonance eqution area took the longest, I had to do a hell of a lot of math to condense it down to this, much simpler form
      return resonanceArray;
   }

//######################################################################################

   public static int[] GrabInitialFrequency(){
      
      int[] returnedValues = new int [2];
      returnedValues[0] = 1; //octaves default
      returnedValues[1] = 440; //basenote default
      
      Scanner user_input = new Scanner(System.in); 
      
      
      System.out.println(" ");
      System.out.println("Would you like to input a custom frequency or use a predefined one?");
      System.out.println("Enter 1 for user defined integer");
      System.out.println("Enter 2 for a random integer");
      System.out.println("Enter anything else to quit");
      
      String Answer = user_input.next(); //uses string and .equals to avoid needless errors, unspecified answers are treated as quit/close is desired
      
      if (Answer.equals("1")){
         System.out.println("you may enter your integer now");
         returnedValues[1] = user_input.nextInt();
      }
      else if (Answer.equals("2")){
         returnedValues[1] = RandomNoteNeeded(); //grabs specific random range
         System.out.println("Random");
      }
      else{
         System.exit(0);
      }//end if statement
       
      System.out.println(" ");
      System.out.println("How many octaves of this scale do you want?");
      System.out.println("Enter 1 for a single octave");
      System.out.println("Enter 2 for multiple octaves");
      System.out.println("Enter anything else to quit");
      
      
      Answer = user_input.next();
      
      if (Answer.equals("1")){
         returnedValues[0] = 1;
      }
      else if (Answer.equals("2")){
         System.out.println("you may enter the desired octaves as an integer now");
         returnedValues[0] = user_input.nextInt();
         
      }
      else{
         System.exit(0);
      }//end if statement
      
      
           
      return returnedValues; //returns number of octaves and base note
   }
   
//######################################################################################   
//###################################################################################### 
//###############MAIN######################################BLOCKADE##################### 
//###################################################################################### 
//###################################################################################### 
//###################################################################################### 

      
   public static void main(String[] args) throws IOException{ //main method
      IntroMessage();//basic instructions
         
      int[] noteInfo = GrabInitialFrequency(); //right above the main blockade
      
      int octavesRequired = noteInfo[0]; //makes coding easier to read
      int baseNote = noteInfo[1]; //makes coding easier to read
      
      
      double[][] scaleArray = CalculateScale(octavesRequired, baseNote); //gives all notes possible with user input        
      double[][] resonanceArray = ResonanceConverter(scaleArray); //gives the resonace frequencies for the notes
         
            
      PrintSave(scaleArray, resonanceArray); //print or save results?
         //takes the array from NotesDesired and prints
         //or saves it to text
         
   } //System.out.println("Would you like to play a game?");
}//System.out.println("How about Thermonuclear war?");
