package org.wx.weixiao.util;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;


import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;


/**
 * Created by lizhimu on 2016/12/27.
 */
public class ExcelUtil {
    /**
     * 将excel转为String数组
     * @param file_dir 为文件路径
     * @return String[] 返回的是excel里面每个单元格的内容
     */
    public static String[] excelToArray(String file_dir) throws IOException {
//        fail("Not yet implemented");
        ArrayList<String> excelList=new ArrayList<String>();
        //String file_dir = "D:/model.xlsx";
        Workbook book = null;
        book = getExcelWorkbook(file_dir);
        Sheet sheet = getSheetByNum(book,0);

        int lastRowNum = sheet.getLastRowNum();

        //System.out.println("last number is "+ lastRowNum);

        for(int i = 0 ; i <= lastRowNum ; i++){
            Row row = null;
            row = sheet.getRow(i);
            if( row != null ){
                //System.out.println("reading line is " + i);
                int lastCellNum = row.getLastCellNum();
                //System.out.println("lastCellNum is " + lastCellNum );
                Cell cell = null;

                for( int j = 0 ; j <= lastCellNum ; j++ ){
                    cell = row.getCell(j);
                    if( cell != null ){
                        // String cellValue = cell.getStringCellValue();
                        cell.setCellType(Cell.CELL_TYPE_STRING);
                        String cellValue = cell.getStringCellValue();
                        excelList.add(cellValue);
                        // System.out.println(cellValue);
                        //System.out.println("cell value is \n" + cellValue);
                    }
                }
            }

        }
        String[] array=new String[excelList.size()];
        excelList.toArray(array);
        return array;
    }

    public static Sheet getSheetByNum(Workbook book,int number){
        Sheet sheet = null;
        try {
            sheet = book.getSheetAt(number);
//          if(sheet == null){
//              sheet = book.createSheet("Sheet"+number);
//          }
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        return sheet;
    }
    public static Workbook getExcelWorkbook(String filePath) throws IOException{
        Workbook book = null;
        File file  = null;
        FileInputStream fis = null;

        try {
            file = new File(filePath);
            if(!file.exists()){
                throw new RuntimeException("文件不存在");
            }else{
                fis = new FileInputStream(file);
                book = WorkbookFactory.create(fis);


            }
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        } finally {
            if(fis != null){
                fis.close();
            }
        }
        return book;
    }


}
