/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import persistence.Bigram;
import business.BigramBean;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import persistence.Word;
import util.NGramGenerator;

@Stateless
public class CorrectorBean implements CorrectorBeanLocal {
    @EJB
    private WordBeanLocal voc;
    
    @EJB
    private BigramBeanLocal bg;

    public CorrectorBean() {
        
    }

    private List<String> tokenizeSentence(String sentence) {
        // word regex
        Pattern p = Pattern.compile("\\w+");
        Matcher m = p.matcher(sentence.toLowerCase());

        // list for regex
        List<String> sentenceList = new ArrayList<>();
        while (m.find()) {
            sentenceList.add(m.group());
        }

        return sentenceList;
    }

    private Word correctWord(int wordIndex, List<String> sentenceList) {
        // get current word
        String wordString = sentenceList.get(wordIndex);
        Word word = new Word(wordString);
        
        // frequencies
        int leftBigramFreq = -1;
        int rightBigramFreq = -1;

        // the maximum suggested word
        int maxFrequency = -1;
        Word maxWord = word;

        for (Word suggestedWord : voc.getEditsLs(word, 1)) {

            // find bigrams for neighbours
            // left
            if (wordIndex > 0 && sentenceList.size() > 1) {
                Bigram leftBigram = new Bigram(sentenceList.get(wordIndex - 1),
                        suggestedWord);

                leftBigramFreq = leftBigram.getFrequency();
            }

            // right
            if (wordIndex < sentenceList.size() - 1 && sentenceList.size() > 1) {
                Bigram rightBigram = new Bigram(suggestedWord,
                        sentenceList.get(wordIndex + 1));
                rightBigramFreq = rightBigram.getFrequency();
            }

            // calculate current max
            int currentMax = Math.max(leftBigramFreq, rightBigramFreq);

            // check for new maximum
            if (maxFrequency < currentMax) {
                maxWord = suggestedWord;
                maxFrequency = currentMax;
            }
        }
        
        return maxWord;
    }
    
    @Override
    public String correct(String faultySentence) {
        List<String> sentenceList = tokenizeSentence(faultySentence);
        List<String> resultList = new ArrayList<>();

        for (int i = 0; i < sentenceList.size(); ++i) {
            // get current word
            String word = sentenceList.get(i);

            if (!voc.hasWord(word)) {
                Word maxWord = correctWord(i, sentenceList);
                // push new word to list
                resultList.add(maxWord.getContent());
            } else {
                // just add the word
                resultList.add(word);
            }

        }

        return String.join(" ", resultList);
    }
    
    public String correctContext(String faultySentence) {
        List<String> sentenceList = tokenizeSentence(faultySentence);
        
        Set<Bigram> sentenceBigrams = NGramGenerator.generateBigrams(sentenceList);
        
        for(Bigram currentBigram : sentenceBigrams) {
            if (currentBigram.getFrequency() < 10) {
                Bigram newBigram = bg.getBetterBigram(currentBigram);
                
                System.out.println("A better bigram for " + currentBigram 
                + " is " + newBigram);
            }
        }
        
        return "";
    }

}
