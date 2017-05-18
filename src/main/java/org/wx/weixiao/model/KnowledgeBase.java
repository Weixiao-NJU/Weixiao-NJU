package org.wx.weixiao.model;
import com.chenlb.mmseg4j.analysis.MMSegAnalyzer;
import org.hibernate.search.annotations.*;
import org.hibernate.search.annotations.Index;

import javax.persistence.*;

/**
 * Created by Jerry Wang on 2016/12/15.
 * 知识库系统
 */
@Entity
@Indexed
@Analyzer(impl=MMSegAnalyzer.class)
public class KnowledgeBase implements FakeDeletable{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    /**
     * 问题内容
     */
    @Field(index= Index.YES, analyze= Analyze.YES, store= Store.YES)
    private String question;
    /**
     * 问题答案
     */
    @Field(index= Index.YES, analyze= Analyze.YES, store= Store.YES)
    @Column(length = 1024)
    private String answer;
    /**
     * 公众号id
     */
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "mediaId")
    private MediaInfo mediaInfo;
    /**
     * 是否被删除
     */
    @Column(columnDefinition = "INT default 0")
    private Integer isDelete;

    @Column(columnDefinition = "INT default 0")
    private Long heat;

    public KnowledgeBase(){

    }

    public Long getHeat() {
        return heat;
    }

    public void setHeat(Long heat) {
        this.heat = heat;
    }

    public MediaInfo getMediaInfo() {
        return mediaInfo;
    }

    public void setMediaInfo(MediaInfo mediaInfo) {
        this.mediaInfo = mediaInfo;
    }

    public KnowledgeBase(String question, String answer) {
        this.question = question;
        this.answer = answer;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    @Override
    public Integer getIsDelete() {
        return this.isDelete;
    }

    @Override
    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }
}
