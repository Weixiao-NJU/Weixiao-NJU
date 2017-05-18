package org.wx.weixiao.service.impl;

import java.io.File;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.ProgressListener;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.wx.weixiao.service.ImportAnswererService;
import org.wx.weixiao.service.UploadService;
import org.wx.weixiao.util.ErrorCodeUtil;


/**
 * Created by lizhimu on 2017/1/8.
 */
@Component("UploadService")
public class UploadServiceImpl implements UploadService {
    @Autowired
    private ImportAnswererService answererService;

//    private String wrongExtNameMark="WRONG_DIR";
    private static Pattern pattern = Pattern.compile("\\&\\#(\\d+)");
//    private String Ext_Name = "gif,jpg,jpeg,png,bmp,"
//            + "swf,flv,mp3,wav,wma,wmv,mid,avi,mpg,asf,rm,rmvb,doc,docx,xls,xlsx," + "ppt,htm,html,txt,zip,rar,gz,bz2";
    private String Ext_Name="xlsx";


    private int saveFileInDatabase(int type,HttpServletRequest request,String path,String mediaId){
        String saveFileName=saveFile(request,path);
        if(saveFileName==null){
            System.out.println("Error code is "+ErrorCodeUtil.POSTFIX_WRONG);
            return ErrorCodeUtil.POSTFIX_WRONG;
        }
//        System.out.println("saveFileName is "+saveFileName);
//        if(saveFileName.equals(wrongExtNameMark)){
//            System.out.println("postfix is not .xlsx");
//            return ErrorCodeUtil.POSTFIX_WRONG;
//        }
//        System.out.println("saveFileName return is "+saveFileName);
        path=path+"\\"+saveFileName;
        System.out.println("path is "+path);
        String windowsLoc=path.replaceAll("\\\\","/");

        int state;
        if(type==0) {
            state=answererService.importAnswererInfo(windowsLoc);
        }else{
            state=answererService.importKnowledgeInfo(windowsLoc);
        }
        System.out.println("state is "+state);
        return state;
    }
    @Override
    public int saveAnswererFileInDatabase(HttpServletRequest request,String path,String mediaId) {
       return  saveFileInDatabase(0,request,path,mediaId);
    }

    @Override
    public int saveKnowledgeFileInDatabase(HttpServletRequest request,String path,String mediaId){
        return  saveFileInDatabase(1,request,path,mediaId);
    }


    public String saveFile(HttpServletRequest request,String path) {
        //param
        //String tmpPath = this.getServletContext().getRealPath("tem");
        String tmpPath=path;
        String saveFileName=null;
        File tmpFile = new File(tmpPath);
        if (!tmpFile.exists()) {
            // 创建临时目录
            tmpFile.mkdirs();
        }

        // 消息提示
        String message = "";

        try {

            // 使用Apache文件上传组件处理文件上传步骤：
            // 1.创建一个DiskFileItemFactory工厂



            DiskFileItemFactory factory = new DiskFileItemFactory();
            // 设置工厂的缓冲区的大小，当上传的文件大小超过缓冲区的大小时，就会生成一个临时文件存放到指定的临时目录当中
            factory.setSizeThreshold(1024 * 10);// 设置缓冲区的大小为100KB，如果不指定，那么默认缓冲区的大小是10KB
            // 设置上传时生成的临时文件的保存目录
            factory.setRepository(tmpFile);
            // 2.创建一个文件上传解析器
            ServletFileUpload upload = new ServletFileUpload(factory);
            // 监听文件上传进度


            upload.setProgressListener(new ProgressListener() {

                @Override
                public void update(long readedBytes, long totalBytes, int currentItem) {
                    // TODO Auto-generated method stub
                    System.out.println("当前已处理：" + readedBytes + "-----------文件大小为：" + totalBytes + "--" + currentItem);
                }
            });



            // 解决上传文件名的中文乱码问题
            upload.setHeaderEncoding("UTF-8");


            // 设置上传单个文件的最大值
            upload.setFileSizeMax(1024 * 1024 * 1);// 1M
            // 设置上传文件总量的最大值，就是本次上传的所有文件的总和的最大值
            upload.setSizeMax(1024 * 1024 * 10);// 10M
            List items = upload.parseRequest(request);

            System.out.println("list size "+items.size());

            Iterator itr = items.iterator();
            while (itr.hasNext()) {
                FileItem item = (FileItem) itr.next();
                // 如果fileitem中封装的是普通的输入想数据
                if (item.isFormField()) {

                    String name = item.getFieldName();
                    // 解决普通输入项数据中文乱码问题

                    String value = item.getString("UTF-8");

                    //value = new String(value.getBytes("iso8859-1"),"UTF-8");
                    System.out.println(name + "=" + value);
                } else// 如果fileitem中封装的是上传文件
                {

                    // 得到上传文件的文件名
                    String fileName = item.getName();
                    System.out.println("文件名：" + fileName);
                    if (fileName == null && fileName.trim().length() == 0) {
                        continue;

                    }
                    // 注意：不同的浏览器提交的文件名是不一样的，有些浏览器提交上来的文件名是带有路径的
                    // 如: C:\Users\H__D\Desktop\1.txt 而有些则是 ： 1.txt
                    // 处理获取到的上传文件的文件名的路径部分，只保留文件名部分
                    fileName = fileName.substring(fileName.lastIndexOf("\\") + 1);
                    //new String(multipartFile.getOriginalFilename().getBytes("ISO-8859-1"), "UTF-8");
                    // 得到上传文件的扩展名
                    String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
                    // 检查扩展名
                    // 如果需要限制上传的文件类型，那么可以通过文件的扩展名来判断上传的文件类型是否合法
                    System.out.println("上传的文件的扩展名是：" + fileExt);
                    if (!Ext_Name.contains(fileExt)) {

                        System.out.println("上传文件扩展名是不允许的扩展名：" + fileExt);
                        message = message + "文件：" + fileName + "，上传文件扩展名是不允许的扩展名：" + fileExt + "<br/>";

                        return null;

                    }

                    // 检查文件大小
                    if (item.getSize() == 0)
                        continue;
                    if (item.getSize() > 1024 * 1024 * 1) {
                        System.out.println("上传文件大小：" + item.getSize());
                        message = message + "文件：" + fileName + "，上传文件大小超过限制大小：" + upload.getFileSizeMax() + "<br/>";
                        break;
                    }

                    // 得到存文件的文件名
                    //String saveFileName = makeFileName(fileName);
                    saveFileName=fileName;
                    //saveFileName=new String(saveFileName.getBytes("ISO-8859-1"), "UTF-8");

                    item.delete();
                    //String path1= "D:\\";
                    //param
                    //String path1= this.getServletContext().getRealPath("/");
                    String path1=path;
                    path1=path1.replaceAll("\\\\", "\\\\\\\\");
//                    path1=path1+"\\excel";

                    //String temp="ajlasdjflsafj    &#35838;&#31243;";
                    //System.out.println("temp is "+tr(temp));
                    System.out.println(path1);
                    //File uploadedFile = new File(savePath, saveFileName);
                    saveFileName=UploadServiceImpl.tr(saveFileName);


                    saveFileName+=".xlsx";
                    //	xlsx.xlsx
                    if(saveFileName.length()>9){
                        String checkString=saveFileName.substring(saveFileName.length()-9);
                        if(checkString.equals("xlsx.xlsx")){
                            saveFileName=saveFileName.substring(0,saveFileName.length()-4);
                        }
                    }
                    System.out.println("save file name is "+saveFileName);
                    File uploadedFile = new File(path1, saveFileName);
                    //System.out.println("save file name is "+saveFileName);
                    item.write(uploadedFile);
                    message = message + "file:" + fileName + "，upload successfully<br/>";




                }

            }


        } catch (Exception e) {
            // TODO Auto-generated catch block

            e.printStackTrace();

        } finally {

            request.setAttribute("message", message);
            //request.getRequestDispatcher("/view/message.jsp").forward(request, response);
            return saveFileName;
        }

    }

    //private String makeFileName(String fileName) {
    // 为防止文件覆盖的现象发生，要为上传文件产生一个唯一的文件名
    //return UUID.randomUUID().toString().replaceAll("-", "") + "_" + fileName;

    //}

    public static String tr(String str){
        String[] header=str.split("\\&");
        StringBuilder sb = new StringBuilder();
        Matcher m =pattern.matcher(str);

        while (m.find()){
            sb.append((char)Integer.valueOf(m.group(1)).intValue());
        }
        return header[0]+sb.toString();
    }

}
