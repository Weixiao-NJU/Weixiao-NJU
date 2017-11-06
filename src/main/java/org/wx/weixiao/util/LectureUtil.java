package org.wx.weixiao.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import org.wx.weixiao.Info.LectureInfo;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by darxan on 2017/4/26.
 */
public class LectureUtil {

    private final static String LECTURE_URL =
            "http://elite.nju.edu.cn/jiaowu/tssDataService.do?method=getLectureList&timestamp=123&sign=568d2a167570e094144a69b1771419e8";

    public static void main(String[] args) {
        read();
    }

    public static List<LectureInfo> read() {
        return read(null, -1);
    }
    /**
     *
     * @param after ==null:获取当前学期所有讲座信息， !=null:获取在after时间点后才发生的讲座信息
     * @return
     */
    public static List<LectureInfo> read(Date after, int id) {


        System.out.println(System.getProperty("file.encoding"));
        String lectureStringArray = HttpRequestUtil.sendGet(LECTURE_URL, "UTF-8");

        Gson gson = new GsonBuilder()
                    .registerTypeAdapter(
                                new TypeToken<List<LectureInfo>>(){}.getType(),
                                new LectureInfoAdapter(after, id))
                    .create();
        List<LectureInfo> lectureInfoList =
                    gson.fromJson(
                        lectureStringArray,
                        new TypeToken<List<LectureInfo>>(){}.getType()
                    );

        System.out.println(lectureInfoList.size());
        for (LectureInfo l : lectureInfoList) {
            System.out.println(l.getId()+"   "+l.getTitle()+l.getStartTime());
        }
        return lectureInfoList;
    }

    static class LectureInfoAdapter extends TypeAdapter<List<LectureInfo>> {

        private final Date after;

        private final int min_id;

        public LectureInfoAdapter(Date after, int min_id) {
            this.after = after;
            this.min_id = min_id;
        }

        public void write(JsonWriter out, List<LectureInfo> value) throws IOException {

        }

        private final SimpleDateFormat sdf
                = new SimpleDateFormat ("EEE MMM dd HH:mm:ss Z yyyy", Locale.ENGLISH);

        public List<LectureInfo> read(JsonReader in) throws IOException {
            List<LectureInfo> lecture = new ArrayList<>(16);
            in.beginArray();
            LectureInfo l = new LectureInfo();
            while (in.hasNext()) {
                if (convertLecture(l, in)) {
                    lecture.add(l);
                    l = new LectureInfo();
                }
            }
            in.endArray();
            return lecture;
        }

        private boolean convertLecture(LectureInfo lecture, JsonReader in) throws IOException {
            boolean valid = true;
            in.beginObject();
            while (in.hasNext()) {
                switch (in.nextName()) {
                    case "id":
                        lecture.setId(in.nextInt());
                        if (min_id>0 && lecture.getId()<=min_id) {
                            valid = false;
                        }
                        break;
                    case "title":
                        lecture.setTitle(in.nextString());
                        break;
                    case "teacher":
                        lecture.setTeacher(in.nextString());
                        break;
                    case "academy":
                        lecture.setAcademy(in.nextString());
                        break;
                    case "startTime":
                        try {
                            lecture.setStartTime(sdf.parse(in.nextString()));
                            /**
                             * 只取出未进行的演讲后的
                             */
                            if (after!=null && lecture.getStartTime().before(after)) {
                                valid = false;
                            }
                        }catch (Exception e) {
                            e.printStackTrace();
                        }
                        break;
                    case "endTime":
                        try {
                            lecture.setEndTime(sdf.parse(in.nextString()));
                        }catch (Exception e) {
                            e.printStackTrace();
                        }
                        break;
                    case "place":
                        lecture.setPlace(in.nextString());
                        break;
                    case "introduction":
                        lecture.setIntroduction(in.nextString());
                        break;
                    default:
                        System.err.println(in.nextString());
                }
            }
            in.endObject();
            return valid;
        }
    }

}
