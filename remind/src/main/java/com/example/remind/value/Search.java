package com.example.remind.value;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Search")
public class Search {
    
	private long id;			// 검색 기록
    private String keyWord;		// 검색 키워드
    
    public Search() {

    }

    public Search(String keyWord) {
        this.keyWord = keyWord;
    }
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }   

    @Column(name = "keyword", nullable = false)
    public String getKeyWord() {
        return keyWord;
    }
    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }

}