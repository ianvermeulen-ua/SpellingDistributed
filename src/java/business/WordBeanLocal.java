package business;

import java.util.List;
import javax.ejb.Local;
import persistence.Word;

@Local
public interface WordBeanLocal {
    List<Word> getAllWords();
    Boolean hasWord(String word);
    List<Word> getEditsLs(Word word, int maxDistance);
    List<Word> getEditsNg(Word word, double maxDistance);
}
