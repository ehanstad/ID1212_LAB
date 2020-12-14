/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.id1212.lab3maven.dbHandler;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author erik_
 */
@Entity
@Table(name = "alternatives")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Alternatives.findAll", query = "SELECT a FROM Alternatives a"),
    @NamedQuery(name = "Alternatives.findByAid", query = "SELECT a FROM Alternatives a WHERE a.aid = :aid"),
    @NamedQuery(name = "Alternatives.findByAlternative", query = "SELECT a FROM Alternatives a WHERE a.alternative = :alternative"),
    @NamedQuery(name = "Alternatives.findByCorrect", query = "SELECT a FROM Alternatives a WHERE a.correct = :correct")})
public class Alternatives implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "aid")
    private Integer aid;
    @Basic(optional = false)
    @Column(name = "alternative")
    private String alternative;
    @Basic(optional = false)
    @Column(name = "correct")
    private short correct;
    @JoinColumn(name = "question", referencedColumnName = "qid")
    @ManyToOne(optional = false)
    private Question question;

    public Alternatives() {
    }

    public Alternatives(Integer aid) {
        this.aid = aid;
    }

    public Alternatives(Integer aid, String alternative, short correct) {
        this.aid = aid;
        this.alternative = alternative;
        this.correct = correct;
    }

    public Integer getAid() {
        return aid;
    }

    public void setAid(Integer aid) {
        this.aid = aid;
    }

    public String getAlternative() {
        return alternative;
    }

    public void setAlternative(String alternative) {
        this.alternative = alternative;
    }

    public short getCorrect() {
        return correct;
    }

    public void setCorrect(short correct) {
        this.correct = correct;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (aid != null ? aid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Alternatives)) {
            return false;
        }
        Alternatives other = (Alternatives) object;
        if ((this.aid == null && other.aid != null) || (this.aid != null && !this.aid.equals(other.aid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "controller.Alternatives[ aid=" + aid + " ]";
    }
    
}
