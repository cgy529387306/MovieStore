package com.android.mb.movie.db;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class Search {

    @Id(autoincrement = true)
    private Long id;
    private String keyWord;
    @Generated(hash = 707290387)
    public Search(Long id, String keyWord) {
        this.id = id;
        this.keyWord = keyWord;
    }
    @Generated(hash = 1644193961)
    public Search() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getKeyWord() {
        return this.keyWord;
    }
    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }

}
