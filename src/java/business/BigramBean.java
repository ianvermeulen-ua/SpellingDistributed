package business;

import info.debatty.java.stringsimilarity.Levenshtein;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import persistence.Bigram;
import persistence.Word;

@Stateless
public class BigramBean implements BigramBeanLocal {
    @PersistenceContext 
    private EntityManager em;
    
    public BigramBean() {
    }
    
    @Override
    public void loadFromInputStream(InputStream inputStream) {
        try {
            BufferedReader bufferedReader
                    = new BufferedReader(new InputStreamReader(inputStream));
            
            String currentLine = "";
            while ( (currentLine = bufferedReader.readLine()) != null ) {
                // split on tab
                String[] columns = currentLine.split("\\s+");

                // check if there are enough columns
                if (columns.length != 3) {
                    System.out.println("Incorrect column format detected");
                    continue;
                }

                // get data
                String firstWord = columns[1];
                String secondWord = columns[2];

                // create entry
                Bigram bigram = new Bigram(firstWord, secondWord);
                
                // push entry
                addBigram(bigram);
            }
            
            bufferedReader.close();
        } catch (IOException ex) {
            Logger.getLogger(WordBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void addBigram(Bigram bigram) {
        em.persist(bigram);
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
