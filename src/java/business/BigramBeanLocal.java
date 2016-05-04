package business;

import java.util.List;
import javax.ejb.Local;
import persistence.Bigram;

@Local
public interface BigramBeanLocal {
    List<Bigram> getBigramFromFirstWord(String firstWord);
    List<Bigram> getBigramFromSecondWord(String secondWord);
    Bigram getBetterBigram(Bigram bigram);
}
