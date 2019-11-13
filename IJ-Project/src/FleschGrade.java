import java.util.StringTokenizer;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.File;
public class FleschGrade {

    public static void main(String[] args) throws Exception{
         String wordDelim = ".,': ;?{}[]=-+_!@#$%^&*()";
         String sentenceDelim = ".,;?!";
         String syllableDelim = "aeiouy";
        File file = new File(args[0]); // add exception
        BufferedReader buffRead = new BufferedReader(new FileReader(file));
        String line;
        String word;
        String[] splitedWord;
        int numOfWords = 0;
        int totalNumOfSyllables = 0;
        int numOfSentences = 0;
        // check better way
        while((line = buffRead.readLine()) != null){
            StringTokenizer splitedLine = new StringTokenizer(line , wordDelim);
            StringTokenizer sentenceSplit = new StringTokenizer(line , sentenceDelim);
            numOfWords += splitedLine.countTokens();
            numOfSentences += sentenceSplit.countTokens();
            //might want to change to sentenceSplit instead of splitedLine
            while(splitedLine.hasMoreTokens()){
                int numOfSyllables = 0;
                word = splitedLine.nextToken();
                splitedWord = word.split("(e\\z)");
                if(splitedWord.length > 0) {
                    splitedWord = splitedWord[0].split("[^" + syllableDelim + "]" + "+");
                    for(String s : splitedWord){
                        if(! s.equals("")) {
                            numOfSyllables++;
                        }
                    }
                    numOfSyllables = numOfSyllables == 0 ? 1 : numOfSyllables;
                }
                totalNumOfSyllables = totalNumOfSyllables + numOfSyllables;
            }
        }
        // add exception
        double flesch = 206.835 - 84.6 * (totalNumOfSyllables / numOfWords) - 1.015 * (numOfWords / numOfSentences);
        System.out.println("The result is: " + flesch);
    }

}
