package org.wx.weixiao.util;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
/**
 * Created by lizhimu on 2017/1/8.
 */
public class DateUtil {
    public static boolean isBetween(String currentTime,String startTime,String endTime){
        String[]start=startTime.split(":");
        String[]end=endTime.split(":");
        String[]current=currentTime.split(":");
        int startHour=Integer.parseInt(start[0]);
        int startMinute=Integer.parseInt(start[1]);
        int endHour=Integer.parseInt(end[0]);
        int endMinute=Integer.parseInt(end[1]);
        int currentHour=Integer.parseInt(current[0]);
        int currentMinute=Integer.parseInt(current[1]);

        int startScore=startHour*60+startMinute;
        int endScore=endHour*60+endMinute;
        int currentScore=currentHour*60+currentMinute;
        if(currentScore>=startScore&&currentScore<=endScore){
            return true;
        }else{
            return false;
        }




    }

    public static String currentTimeToString(){
        Calendar now = Calendar.getInstance();
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        return sdf.format(d);
    }

    public static void main(String args[]){
        String currentTime = DateUtil.currentTimeToString();
        String startTime = "07:30";
        String endTime = "22:00";
        System.out.println(isBetween(currentTime,startTime,endTime));
    }
}
