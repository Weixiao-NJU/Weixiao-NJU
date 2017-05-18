package org.wx.weixiao.model;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by Daniel on 2016/12/19.
 * 公众号信息
 */
@Entity
@Table(name = "media_info")
public class MediaInfo implements FakeDeletable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer mid;

    /**
     * 公众号原始ID
     */
    private String mediaId;
    /**
     * 公众号设置的微信号可以为空
     */
    private String mediaNum;
    /**
     * 公众号名称
     */
    private String mediaName;
    /**
     * 公众号类型
     * 0代表订阅号，1代表由历史老帐号升级后的订阅号，2代表服务号
     */
    private Integer mediaType;
    /**
     * 公众号信息页，可能为空
     */
    private String mediaUrl;
    /**
     * 公众号头像
     */
    private String avatarImage;
    /**
     * 公众号学校名称
     */
    private String schoolName;
    /**
     * 学校编号
     */
    private String schoolCode;
    /**
     * 认证类型
     * "-1":"未认证",
     * "0":"微信认证",
     * "1":"新浪微博认证",
     * "2":"腾讯微博认证",
     * "3":"已资质认证通过但还未通过名称认证",
     * "4":"已资质认证通过、还未通过名称认证，但通过了新浪微博认证",
     * "5":"已资质认证通过、还未通过名称认证，但通过了腾讯微博认证",
     */
    private Integer verifyType;
    /**
     * 是否被删除
     */
    @Column(columnDefinition = "INT default 0")
    private Integer isDelete;

    @OneToMany(mappedBy = "mediaInfo",cascade = CascadeType.ALL)
    private Set<Questioner> questioners;

    @OneToMany(mappedBy = "mediaInfo",cascade = CascadeType.ALL)
    private Set<Answerer> answerers;

    @OneToMany(mappedBy = "mediaInfo",cascade = CascadeType.ALL)
    private Set<Question> questions;

    public Integer getMid() {
        return mid;
    }

    public void setMid(Integer mid) {
        this.mid = mid;
    }

    public Set<Answerer> getAnswerers() {
        return answerers;
    }

    public void setAnswerers(Set<Answerer> answerers) {
        this.answerers = answerers;
    }

    public String getMediaId() {
        return mediaId;
    }

    public void setMediaId(String mediaId) {
        this.mediaId = mediaId;
    }

    public String getMediaNum() {
        return mediaNum;
    }

    public void setMediaNum(String mediaNum) {
        this.mediaNum = mediaNum;
    }

    public String getMediaName() {
        return mediaName;
    }

    public void setMediaName(String mediaName) {
        this.mediaName = mediaName;
    }

    public Integer getMediaType() {
        return mediaType;
    }

    public void setMediaType(Integer mediaType) {
        this.mediaType = mediaType;
    }

    public String getMediaUrl() {
        return mediaUrl;
    }

    public void setMediaUrl(String mediaUrl) {
        this.mediaUrl = mediaUrl;
    }

    public String getAvatarImage() {
        return avatarImage;
    }

    public void setAvatarImage(String avatarImage) {
        this.avatarImage = avatarImage;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public String getSchoolCode() {
        return schoolCode;
    }

    public void setSchoolCode(String schoolCode) {
        this.schoolCode = schoolCode;
    }

    public Integer getVerifyType() {
        return verifyType;
    }

    public void setVerifyType(Integer verifyType) {
        this.verifyType = verifyType;
    }

    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }

    public Set<Questioner> getQuestioners() {
        return questioners;
    }

    public void setQuestioners(Set<Questioner> questioners) {
        this.questioners = questioners;
    }

    public Set<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(Set<Question> questions) {
        this.questions = questions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MediaInfo mediaInfo = (MediaInfo) o;

        if (mid != null ? !mid.equals(mediaInfo.mid) : mediaInfo.mid != null) return false;
        if (mediaId != null ? !mediaId.equals(mediaInfo.mediaId) : mediaInfo.mediaId != null)
            return false;
        if (mediaNum != null ? !mediaNum.equals(mediaInfo.mediaNum) : mediaInfo.mediaNum != null)
            return false;
        if (mediaName != null ? !mediaName.equals(mediaInfo.mediaName) : mediaInfo.mediaName != null)
            return false;
        if (mediaType != null ? !mediaType.equals(mediaInfo.mediaType) : mediaInfo.mediaType != null)
            return false;
        if (mediaUrl != null ? !mediaUrl.equals(mediaInfo.mediaUrl) : mediaInfo.mediaUrl != null)
            return false;
        if (avatarImage != null ? !avatarImage.equals(mediaInfo.avatarImage) : mediaInfo.avatarImage != null)
            return false;
        if (schoolName != null ? !schoolName.equals(mediaInfo.schoolName) : mediaInfo.schoolName != null)
            return false;
        if (schoolCode != null ? !schoolCode.equals(mediaInfo.schoolCode) : mediaInfo.schoolCode != null)
            return false;
        if (verifyType != null ? !verifyType.equals(mediaInfo.verifyType) : mediaInfo.verifyType != null)
            return false;
        if (isDelete != null ? !isDelete.equals(mediaInfo.isDelete) : mediaInfo.isDelete != null)
            return false;
        if (questioners != null ? !questioners.equals(mediaInfo.questioners) : mediaInfo.questioners != null)
            return false;
        if (answerers != null ? !answerers.equals(mediaInfo.answerers) : mediaInfo.answerers != null)
            return false;
        return questions != null ? questions.equals(mediaInfo.questions) : mediaInfo.questions == null;

    }

    @Override
    public int hashCode() {
        int result = mid != null ? mid.hashCode() : 0;
        result = 31 * result + (mediaId != null ? mediaId.hashCode() : 0);
        result = 31 * result + (mediaNum != null ? mediaNum.hashCode() : 0);
        result = 31 * result + (mediaName != null ? mediaName.hashCode() : 0);
        result = 31 * result + (mediaType != null ? mediaType.hashCode() : 0);
        result = 31 * result + (mediaUrl != null ? mediaUrl.hashCode() : 0);
        result = 31 * result + (avatarImage != null ? avatarImage.hashCode() : 0);
        result = 31 * result + (schoolName != null ? schoolName.hashCode() : 0);
        result = 31 * result + (schoolCode != null ? schoolCode.hashCode() : 0);
        result = 31 * result + (verifyType != null ? verifyType.hashCode() : 0);
        result = 31 * result + (isDelete != null ? isDelete.hashCode() : 0);
        result = 31 * result + (questioners != null ? questioners.hashCode() : 0);
        result = 31 * result + (answerers != null ? answerers.hashCode() : 0);
        result = 31 * result + (questions != null ? questions.hashCode() : 0);
        return result;
    }
}
