/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistence;

import info.debatty.java.stringsimilarity.Levenshtein;
import info.debatty.java.stringsimilarity.NGram;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

/**
 *
 * @author ianvermeulen
 */
@Entity
@Table
@NamedQueries({
    @NamedQuery(name="Word.findAll",query="SELECT e FROM Word e"),
    @NamedQuery(name="Word.findId", query="SELECT w FROM Word w WHERE w.id = :id"),
    @NamedQuery(name="Word.findContent", query="SELECT w FROM Word w WHERE w.content = :content")})
public class Word implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="ID")
    private int id;
    
    @Column
    private String content;
    
    @Column
    private int frequency;
    
    public Word() {
        content = "";
        frequency = 0;
    }
    
    public Word(String content) {
        this.content = content;
        this.frequency = 0;
    }
    
    public Word(String content, int frequency) {
        this.content = content;
        this.frequency = frequency;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }
    
    public void incrementFrequency() {
        this.frequency++;
    }
    
    public double getDistanceLs(Word otherWord) {
        Levenshtein ls = new Levenshtein();
        
        return ls.distance(content, otherWord.getContent());
    }
    
    public double getDistanceNg(Word otherWord) {
        NGram ng = new NGram();
        
        return ng.distance(content, otherWord.getContent());
    }
}
