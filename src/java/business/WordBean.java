//code from http://raelcunha.com/spell-correct/
package business;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
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
    public Boolean hasWord(String word) {
        try {
            return !(em.createNamedQuery("Word.findContent")
                    .setParameter("content", word)
                    .getResultList()
                    .isEmpty());
        } catch(javax.persistence.NoResultException ex) {
            ex.printStackTrace();
        }
        return false;
    }
    
    @Override
    public List<Word> getAllWords() {
        List<Word> result = null;
        
        try {
            result = em.createNamedQuery("Word.findAll").getResultList();
        } catch(javax.persistence.NoResultException ex) {
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
