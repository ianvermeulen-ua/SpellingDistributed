package persistence;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-05-04T19:54:52")
@StaticMetamodel(Bigram.class)
public class Bigram_ { 

    public static volatile SingularAttribute<Bigram, Integer> id;
    public static volatile SingularAttribute<Bigram, String> first;
    public static volatile SingularAttribute<Bigram, String> second;
    public static volatile SingularAttribute<Bigram, Integer> frequency;

}