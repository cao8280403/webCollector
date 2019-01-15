package com.news.webcollector.entity;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "tb_news", schema = "db_financial_window", catalog = "")
public class TbNews {
    private String guid;
    private Timestamp createtime;
    private Timestamp updatetime;
    private String newsBody;
    private String newsImage;
    private String newsWebsite;
    private String newsTitle;
    private String newsType;
    private Integer newsClick;
    private String newsRank;
    private String platForm;
    private Timestamp newsSourceDistributeTime;
    private String newsSourceLink;
    private String newsSourcePlatform;
    private Integer showStatus;

    @Id
    @Column(name = "guid")
    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    @Basic
    @Column(name = "createtime")
    public Timestamp getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Timestamp createtime) {
        this.createtime = createtime;
    }

    @Basic
    @Column(name = "updatetime")
    public Timestamp getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Timestamp updatetime) {
        this.updatetime = updatetime;
    }

    @Basic
    @Column(name = "news_body")
    public String getNewsBody() {
        return newsBody;
    }

    public void setNewsBody(String newsBody) {
        this.newsBody = newsBody;
    }

    @Basic
    @Column(name = "news_image")
    public String getNewsImage() {
        return newsImage;
    }

    public void setNewsImage(String newsImage) {
        this.newsImage = newsImage;
    }

    @Basic
    @Column(name = "news_website")
    public String getNewsWebsite() {
        return newsWebsite;
    }

    public void setNewsWebsite(String newsWebsite) {
        this.newsWebsite = newsWebsite;
    }

    @Basic
    @Column(name = "news_title")
    public String getNewsTitle() {
        return newsTitle;
    }

    public void setNewsTitle(String newsTitle) {
        this.newsTitle = newsTitle;
    }

    @Basic
    @Column(name = "news_type")
    public String getNewsType() {
        return newsType;
    }

    public void setNewsType(String newsType) {
        this.newsType = newsType;
    }

    @Basic
    @Column(name = "news_click")
    public Integer getNewsClick() {
        return newsClick;
    }

    public void setNewsClick(Integer newsClick) {
        this.newsClick = newsClick;
    }

    @Basic
    @Column(name = "news_rank")
    public String getNewsRank() {
        return newsRank;
    }

    public void setNewsRank(String newsRank) {
        this.newsRank = newsRank;
    }

    @Basic
    @Column(name = "plat_form")
    public String getPlatForm() {
        return platForm;
    }

    public void setPlatForm(String platForm) {
        this.platForm = platForm;
    }

    @Basic
    @Column(name = "news_source_distribute_time")
    public Timestamp getNewsSourceDistributeTime() {
        return newsSourceDistributeTime;
    }

    public void setNewsSourceDistributeTime(Timestamp newsSourceDistributeTime) {
        this.newsSourceDistributeTime = newsSourceDistributeTime;
    }

    @Basic
    @Column(name = "news_source_link")
    public String getNewsSourceLink() {
        return newsSourceLink;
    }

    public void setNewsSourceLink(String newsSourceLink) {
        this.newsSourceLink = newsSourceLink;
    }

    @Basic
    @Column(name = "news_source_platform")
    public String getNewsSourcePlatform() {
        return newsSourcePlatform;
    }

    public void setNewsSourcePlatform(String newsSourcePlatform) {
        this.newsSourcePlatform = newsSourcePlatform;
    }

    @Basic
    @Column(name = "show_status")
    public Integer getShowStatus() {
        return showStatus;
    }

    public void setShowStatus(Integer showStatus) {
        this.showStatus = showStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TbNews tbNews = (TbNews) o;

        if (guid != null ? !guid.equals(tbNews.guid) : tbNews.guid != null) return false;
        if (createtime != null ? !createtime.equals(tbNews.createtime) : tbNews.createtime != null) return false;
        if (updatetime != null ? !updatetime.equals(tbNews.updatetime) : tbNews.updatetime != null) return false;
        if (newsBody != null ? !newsBody.equals(tbNews.newsBody) : tbNews.newsBody != null) return false;
        if (newsImage != null ? !newsImage.equals(tbNews.newsImage) : tbNews.newsImage != null) return false;
        if (newsWebsite != null ? !newsWebsite.equals(tbNews.newsWebsite) : tbNews.newsWebsite != null) return false;
        if (newsTitle != null ? !newsTitle.equals(tbNews.newsTitle) : tbNews.newsTitle != null) return false;
        if (newsType != null ? !newsType.equals(tbNews.newsType) : tbNews.newsType != null) return false;
        if (newsClick != null ? !newsClick.equals(tbNews.newsClick) : tbNews.newsClick != null) return false;
        if (newsRank != null ? !newsRank.equals(tbNews.newsRank) : tbNews.newsRank != null) return false;
        if (platForm != null ? !platForm.equals(tbNews.platForm) : tbNews.platForm != null) return false;
        if (newsSourceDistributeTime != null ? !newsSourceDistributeTime.equals(tbNews.newsSourceDistributeTime) : tbNews.newsSourceDistributeTime != null)
            return false;
        if (newsSourceLink != null ? !newsSourceLink.equals(tbNews.newsSourceLink) : tbNews.newsSourceLink != null)
            return false;
        if (newsSourcePlatform != null ? !newsSourcePlatform.equals(tbNews.newsSourcePlatform) : tbNews.newsSourcePlatform != null)
            return false;
        if (showStatus != null ? !showStatus.equals(tbNews.showStatus) : tbNews.showStatus != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = guid != null ? guid.hashCode() : 0;
        result = 31 * result + (createtime != null ? createtime.hashCode() : 0);
        result = 31 * result + (updatetime != null ? updatetime.hashCode() : 0);
        result = 31 * result + (newsBody != null ? newsBody.hashCode() : 0);
        result = 31 * result + (newsImage != null ? newsImage.hashCode() : 0);
        result = 31 * result + (newsWebsite != null ? newsWebsite.hashCode() : 0);
        result = 31 * result + (newsTitle != null ? newsTitle.hashCode() : 0);
        result = 31 * result + (newsType != null ? newsType.hashCode() : 0);
        result = 31 * result + (newsClick != null ? newsClick.hashCode() : 0);
        result = 31 * result + (newsRank != null ? newsRank.hashCode() : 0);
        result = 31 * result + (platForm != null ? platForm.hashCode() : 0);
        result = 31 * result + (newsSourceDistributeTime != null ? newsSourceDistributeTime.hashCode() : 0);
        result = 31 * result + (newsSourceLink != null ? newsSourceLink.hashCode() : 0);
        result = 31 * result + (newsSourcePlatform != null ? newsSourcePlatform.hashCode() : 0);
        result = 31 * result + (showStatus != null ? showStatus.hashCode() : 0);
        return result;
    }
}
