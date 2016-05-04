package business;

import java.io.InputStream;
import java.util.List;
import javax.ejb.Local;
import persistence.Word;

@Local
public interface WordBeanLocal {
    void addWord(Word word);
    
    Word editWord(Word word);
    
    Word findWordByContent(String word);
    
    List<Word> getAllWords();
    
    void loadFromInputStream(InputStream inputStream);
    
    Boolean hasWord(String word);
    
    List<Word> getEditsLs(Word word, int maxDistance);
    
    List<Word> getEditsNg(Word word, double maxDistance);
}
