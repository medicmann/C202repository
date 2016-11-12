import java.util.*;
import java.io.*;
import java.lang.*;

/**
 * @author Trevor
 * @version 1.0
 * @since 11-11-16
 * @class C202-Fall 2016
 * 
 * Description: In this program, we are tasked with reading in a file and 
 *              comparing to a non-alphabetized dictionary. If the word is not
 *              in the dictionary, count it and the average comparisons it took 
 *              to get that result, else, count the correct words and the 
 *              average comparisons it took to find all the correct words.
 */
public class programmingAssignment5 {
    
    //Instantiation of a Binary Search Tree array of length 26
    BinarySearchTree[] list = new BinarySearchTree[26];
    
    /**
     * Description: This is a void method that populates the 26 BST's with 
     *              alphabetically based indices where the first letter of
     *              each word has its own tree.
     */
    public void popDic(){
        for(int i = 0; i < list.length; i++) {
            list[i] = new BinarySearchTree<String>();
        }
        
        try {
            File f = new File("random_dictionary.txt");
            Scanner in = new Scanner(f);
            while(in.hasNext()) {
                String a = in.next().toLowerCase();
                int b = a.charAt(0) - 97;
                list[b].insert(a);
            }
            in.close();
        }
        catch (IOException e) {
            System.out.println("Error");
        }
    }
    
    //Placeholders for tracking data
    private double wordsFound = 0;
    private double wordsNotFound = 0;
    private double compsFound;
    private double compsNotFound;
    
    /**
     * Description: This is a void method that takes each word in oliver.txt as a 
     *              single String type variable, removes special characters, and
     *              if the first character is null or "" then ignore it. If not,
     *              if the dictionary contains that String then track correct words
     *              and comparisons, else, do the opposite. After execution it 
     *              will output the words found and not found. It will also do 
     *              the average comparisons for each word found and not found 
     *              (separately)
     */
    public void readOliver() {
        try {
            File f = new File("oliver.txt");
            Scanner in = new Scanner(f);
            while(in.hasNext()) {
                String a = in.next().replaceAll("\\W", "").replaceAll("[0-9]","").replaceAll("[-_]", "").toLowerCase();
                if(!a.equals("")) {
                    int b = a.charAt(0);
                    int[] count = new int[1];
                    count[0] = 0;
                    
                    if(list[b - 97].search(a, count)) {
                        wordsFound++;
                        compsFound += count[0];
                    } else {
                        wordsNotFound++;
                        compsNotFound += count[0];
                    }
                }
            }
            in.close();
            System.out.println("Number of words found: " + wordsFound);
            System.out.println("Number of words NOT found: " + wordsNotFound);
            System.out.println("");
            System.out.println("Average computations for correct words: " + compsFound/wordsFound);
            System.out.println("Average computations for incorrect words: " + compsNotFound/wordsNotFound);
        }
        catch(IOException e) {
            System.out.println("Error");
        }
    }
    
    public static void main(String[] args) {
        programmingAssignment5 x = new programmingAssignment5();
        x.popDic();
        x.readOliver();
    }
}
