package persistence;

import info.debatty.java.stringsimilarity.Levenshtein;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author ianvermeulen
 */
@Entity
@Table
@NamedQueries({
    @NamedQuery(name="Bigram.getAll",query="SELECT e FROM Bigram e"),
    @NamedQuery(name="Bigram.findId", query="SELECT w FROM Bigram w WHERE w.id = :id"),
    @NamedQuery(name="Bigram.findFirst", query="SELECT w FROM Bigram w WHERE w.first = :first"),
    @NamedQuery(name="Bigram.findSecond", query="SELECT w FROM Bigram w WHERE w.second = :second"),
    @NamedQuery(name="Bigram.find", query="SELECT w FROM Bigram w WHERE w.first = :first AND w.second = :second")})
public class Bigram implements Comparable, Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="ID")
    private int id;
    @Column
    private String first;
    @Column
    private String second;
    @Column
    private int frequency;
    
    public Bigram() {
        first = "";
        second = "";
        frequency = 0;
    }
    
    public Bigram(Word firstWord, Word secondWord) {
        this.first = firstWord.getContent();
        this.second = secondWord.getContent();
        this.frequency = 0;
    }
    
    public Bigram(String firstWord, Word secondWord) {
        this.first = firstWord;
        this.second = secondWord.getContent();
        this.frequency = 0;
    }
    
    public Bigram(Word firstWord, String secondWord) {
        this.first = firstWord.getContent();
        this.second = secondWord;
        this.frequency = 0;
    }
    
    public Bigram(String firstWord, String secondWord) {
        this.first = firstWord;
        this.second = secondWord;
        this.frequency = 0;
    }

    public Bigram(String firstWord, String secondWord, int frequency) {
        this.first = firstWord;
        this.second = secondWord;
        this.frequency = frequency;
    }

    public String getFirst() {
        return first;
    }

    public void setFirst(String firstWord) {
        this.first = firstWord;
    }

    public String getSecond() {
        return second;
    }

    public void setSecond(String secondWord) {
        this.second = secondWord;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    } 
    
    public String toString() {
    	return this.getFirst() + " " + this.getSecond();
    }
    
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Bigram))
            return false;
        
        Bigram bigramObj = (Bigram)obj;
        
        return bigramObj.getFirst().equalsIgnoreCase(first) 
                && bigramObj.getSecond().equalsIgnoreCase(second); 
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 41 * hash + Objects.hashCode(this.first);
        hash = 41 * hash + Objects.hashCode(this.second);
        return hash;
    }

    @Override
    public int compareTo(Object o) {
        if (! (o instanceof Bigram))
            return 0;
        
        Bigram otherBigram = (Bigram)o;
        
        return otherBigram.getFirst().compareTo(otherBigram.getFirst());
    }
    
        
    public double getDistance(Bigram otherBigram) {
        Levenshtein ls = new Levenshtein();
        
        double totalDistance = ls.distance(getFirst(), otherBigram.getFirst())
                + ls.distance(getSecond(), otherBigram.getSecond());
        
        return totalDistance;
    }
}

