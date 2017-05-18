package org.wx.weixiao.dao;

import org.springframework.stereotype.Repository;
import org.wx.weixiao.dao.base.BaseDao;
import org.wx.weixiao.model.MediaInfo;

import java.util.List;

/**
 * Created by Daniel on 2016/12/19.
 */
@Repository
public class MediaInfoDAO extends BaseDao<MediaInfo, Integer> {

    public List<MediaInfo> getListByColumnValue(String colName, Object colValue) {
        String hqlString = "select distinct q from MediaInfo q where q." + colName + "=?";
        return getListByHQL(hqlString, colValue);
    }


    public MediaInfo getByMediaId(String media_id) {
        return getByHQL("from MediaInfo where mediaId=?", media_id);
    }

    public List<MediaInfo> getMediaInfos() {
        return getListByHQL("from MediaInfo");
    }
}
