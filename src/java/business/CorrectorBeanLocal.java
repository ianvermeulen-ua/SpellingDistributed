package business;

import javax.ejb.Local;

@Local
public interface CorrectorBeanLocal {
    String correct(String faultySentence);
}
