package business;

import info.debatty.java.stringsimilarity.Levenshtein;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import persistence.Bigram;

@Stateless
public class BigramBean implements BigramBeanLocal {
    @PersistenceContext 
    private EntityManager em;
    
    public BigramBean() {
    }
    
    @Override
    public List<Bigram> getBigramFromFirstWord(String firstWord) {
        List<Bigram> bigrams = null;
        try {
            bigrams = em.createNamedQuery("Bigram.findFirst").setParameter("first", firstWord).getResultList();
        } catch(javax.persistence.NoResultException ex) {
            ex.printStackTrace();
        }
        return bigrams;
    }
    
    @Override
    public List<Bigram> getBigramFromSecondWord(String secondWord) {
        List<Bigram> bigrams = null;
        try {
            bigrams = em.createNamedQuery("Bigram.findSecond").setParameter("second", secondWord).getResultList();
        } catch(javax.persistence.NoResultException ex) {
            ex.printStackTrace();
        }
        return bigrams;
    }       
    
    @Override
    public Bigram getBetterBigram(Bigram bigram) {
        List<Bigram> firstFixedBigrams = getBigramFromFirstWord(bigram.getFirst());
        List<Bigram> secondFixedBigrams = getBigramFromFirstWord(bigram.getSecond());
        Set<Bigram> allBigrams = new TreeSet<>();
        
        allBigrams.addAll(firstFixedBigrams);
        allBigrams.addAll(secondFixedBigrams);
        
        int maxFrequency = -1;
        Bigram maxBigram = null;
        
        for(Bigram currentBigram : allBigrams) {
            int currentFrequency = currentBigram.getFrequency();
            
            if (currentFrequency > maxFrequency &&
                    bigram.getDistance(currentBigram) <= 2.0) {
                maxFrequency = currentFrequency;
                maxBigram = currentBigram;
            }
        }
        
        return maxBigram;
    }
}
