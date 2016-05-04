package business;

import java.io.InputStream;
import java.util.List;
import javax.ejb.Local;
import persistence.Bigram;

@Local
public interface BigramBeanLocal {
    void addBigram(Bigram bigram);
    
    List<Bigram> getBigramFromFirstWord(String firstWord);
    
    List<Bigram> getBigramFromSecondWord(String secondWord);
    
    Bigram getBetterBigram(Bigram bigram);
    
    void loadFromInputStream(InputStream inputStream);
}
