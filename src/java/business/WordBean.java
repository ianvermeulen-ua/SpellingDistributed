//code from http://raelcunha.com/spell-correct/
package business;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import persistence.Word;

@Stateless
public class WordBean implements WordBeanLocal {

    @PersistenceContext
    private EntityManager em;

    public WordBean() {

    }

    @Override
    public void loadFromInputStream(InputStream inputStream) {
        try {
            BufferedReader bufferedReader
                    = new BufferedReader(new InputStreamReader(inputStream));
            
            Pattern p = Pattern.compile("\\w+");
            for (String temp = ""; temp != null; temp = bufferedReader.readLine()) {
                Matcher m = p.matcher(temp.toLowerCase());
                while (m.find()) {
                    String token = m.group();
                    
                    Word word = findWordByContent(token);
                    
                    if (word == null) {
                        word = new Word(token);
                        addWord(word);
                    }
                    else {
                        word.incrementFrequency();
                        editWord(word);
                    }
                }
            }
            
            bufferedReader.close();
        } catch (IOException ex) {
            Logger.getLogger(WordBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void addWord(Word word) {
        em.persist(word);
    }
    
    @Override
    public Word editWord(Word word) {
        return em.merge(word);
    }

    @Override
    public Word findWordByContent(String word) {
        try {
            List<Word> words = em.createNamedQuery("Word.findContent")
                    .setParameter("content", word)
                    .getResultList();
            
            if (!words.isEmpty()) {
                return words.get(0);
            }
        } catch (javax.persistence.NoResultException ex) {
            ex.printStackTrace();
        }
        return null;
    }
    
    @Override
    public Boolean hasWord(String word) {
        try {
            return !(em.createNamedQuery("Word.findContent")
                    .setParameter("content", word)
                    .getResultList()
                    .isEmpty());
        } catch (javax.persistence.NoResultException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    @Override
    public List<Word> getAllWords() {
        List<Word> result = null;

        try {
            result = em.createNamedQuery("Word.findAll").getResultList();
        } catch (javax.persistence.NoResultException ex) {
            ex.printStackTrace();
        }

        return result;
    }

    @Override
    public List<Word> getEditsLs(Word word, int maxDistance) {
        List<Word> allWords = getAllWords();
        List<Word> result = new ArrayList<>();

        for (Word currentWord : allWords) {
            if (currentWord.getDistanceLs(word) <= maxDistance) {
                result.add(currentWord);
            }
        }

        return result;
    }

    @Override
    public List<Word> getEditsNg(Word word, double maxDistance) {
        List<Word> allWords = getAllWords();
        List<Word> result = new ArrayList<>();

        for (Word currentWord : allWords) {
            if (currentWord.getDistanceNg(word) <= maxDistance) {
                result.add(currentWord);
            }
        }

        return result;
    }

}
