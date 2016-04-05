/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author kalinga
 */
public class WordWrapper {

    /**
     * @param args the command line arguments
     */
    static ArrayList<String> output=new ArrayList<String>();
    
    //This method will calculate cost for all possible line breaks and it will return cost table for all solutions
    
    static int[][] calculateCost(int[] lengthOfwords,int maximumWidth,int numberOfwords){
        int[][] remainingSpaces=new int[numberOfwords+1][numberOfwords+1];  
        int[][] costTable=new int[numberOfwords+1][numberOfwords+1];
        
        for(int i=1; i<=numberOfwords;i++){
            remainingSpaces[i][i]=maximumWidth-lengthOfwords[i-1];
            for(int j=i+1;j<=numberOfwords;j++)
                remainingSpaces[i][j]=remainingSpaces[i][j-1]-lengthOfwords[j-1]-1;
        }
        
        for (int i=1;i<=numberOfwords;i++){
            for(int j=i;j<=numberOfwords;j++){
                if (remainingSpaces[i][j]<0)
                    costTable[i][j]=Integer.MAX_VALUE;
                else if (j==numberOfwords&&remainingSpaces[i][j]>=0)
                    costTable[i][j]=0;
                else
                    costTable[i][j]=remainingSpaces[i][j]*remainingSpaces[i][j]*remainingSpaces[i][j];
            }
        }
        return costTable;
    }
    
    //This method will decide what is the minimum cost path and where to put line breaks
    //It will also return total cost for minimum cost path
    
    int placeLineBreaks(String paragraph){
        String[] input=paragraph.split(" ");
        int maximumWidth=Integer.parseInt(input[0]);
        String[] words=new String[input.length-1];
        for(int i=0;i<=input.length-2;i++)
            words[i]=input[i+1];
        int numberOfwords=words.length;
        int[] lengthOfwords=new int[numberOfwords];
        for(int i=0;i<=numberOfwords-1;i++){
            lengthOfwords[i]=words[i].length();
        }
        
        int[][] costTable=calculateCost(lengthOfwords, maximumWidth, numberOfwords);
        
        int[] cost=new int[numberOfwords+1];
        int[] lineBreaks=new int[numberOfwords+1];
        for (int j = 1; j <= numberOfwords; j++){
            cost[j] = Integer.MAX_VALUE;
            for (int i=1;i<=j;i++){
                if (cost[i-1]!=Integer.MAX_VALUE&&costTable[i][j]!=Integer.MAX_VALUE&&(cost[i-1]+costTable[i][j]<cost[j])){
                    cost[j]=cost[i-1]+costTable[i][j];
                    lineBreaks[j]=i;
                }
            }
        }
        
        printLines(words,lineBreaks,numberOfwords);
        System.out.println(cost[numberOfwords]);
        return cost[numberOfwords];
    }
    
    //This method will print the lines according to result of previous 2  methods
    
    static int printLines(String[] words, int lineBreaks[], int numberOfwords){
        String correctLine="";
        int lineNumber;
        if(lineBreaks[numberOfwords]==1)
            lineNumber=1;
        else
            lineNumber=printLines(words,lineBreaks, lineBreaks[numberOfwords]-1)+1;
        for(int i=lineBreaks[numberOfwords]-1;i<=numberOfwords-1;i++){
            System.out.print(words[i]+" ");
            correctLine+=words[i]+" ";
        }
        System.out.print("\n");
        output.add(correctLine);
        return lineNumber;
    }
    
    public static void main(String[] args) {
        WordWrapperGUI wp=new WordWrapperGUI();
        wp.setVisible(true);
        
    }
    
}
