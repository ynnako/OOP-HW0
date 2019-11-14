import java.util.Scanner;
import java.util.regex.*;
import java.io.File;
public class FleschGrade {

    public static int main(String[] args) throws Exception{
        if(args.length != 1){
            System.out.println("Worng number of arguments. This program expects one argument.");
            return -1;
        }
        String wordDelim = "[.,: ;?\\{\\}\\[\\]=\\-\\+_!@#$%^&*\\(\\)]+";
        String sentenceDelim = "[.,;?!]+";
        String syllableDelim = "[aeiouyAEIOUY]+";
        String sentence;
        String[] words;
        Pattern sentenceDelimPattern = Pattern.compile(sentenceDelim);
        Pattern syllableDelimPattern = Pattern.compile(syllableDelim);
        Scanner fileScanner = null;
        File file = new File(args[0]);
        if(! file.exists()){
            System.out.println("File: " + args[0] + " does'nt exist.");
            return -1;
        }

        double numOfWords = 0 , numOfSentences = 0 , totalNumOfSyllables = 0 , numOfSyllables = 0 , flesch = 0;
        try {
            fileScanner = new Scanner(file);
            fileScanner.useDelimiter(sentenceDelimPattern);

            while (fileScanner.hasNext()) {

                sentence = fileScanner.next().strip();
                if (sentence.equals("")) continue;
                words = sentence.split(wordDelim);
                for (String si : words) {
                    numOfSyllables = 0;
                    if (si.equals("")) continue;
                    si = si.strip();
                    if (si.equals("")) continue;
                    numOfWords++;
                    Matcher syllableMatcher = syllableDelimPattern.matcher(si);
                    while (syllableMatcher.find()) {
                        numOfSyllables++;
                    }
                    if (si.endsWith("e")) {
                        numOfSyllables = (numOfSyllables == 1 || numOfSyllables == 0) ? 1 : (numOfSyllables - 1);
                    }
                    totalNumOfSyllables += numOfSyllables;
                }
                numOfSentences++;
            }
        }
        catch (Exception e){
            System.out.println("Something went wrong");
        }
        finally {
            fileScanner.close();
        }
            if(numOfWords != 0 && numOfSentences != 0) {
                flesch = 206.835 - (84.6 * (totalNumOfSyllables / numOfWords)) - (1.015 * (numOfWords / numOfSentences));
                System.out.println("The result is: " + flesch);
            }
            else if(numOfWords == 0) {
                System.out.println("The number of words is zero. Thus Flesch grade could'nt be calculated.");
                return -1;
            }
            else if(numOfSentences == 0) {
                System.out.println("The number of sentences is zero. Thus Flesch grade could'nt be calculated.");
                return -1;
            }
    return 0;
    }


}
