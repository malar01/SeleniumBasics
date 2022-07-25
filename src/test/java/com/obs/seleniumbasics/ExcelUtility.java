package com.obs.seleniumbasics;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ExcelUtility {
    /*public static String readStringData(int i,int j,String sheetname) throws IOException, FileNotFoundException {
        String filepath=System.getProperty("user.dir") + "\\src\\main\\resources\\TestData.xlsx\\";
        int rowTotal ;
        FileInputStream f=new FileInputStream(filepath);
        XSSFWorkbook w=new XSSFWorkbook(f);
        XSSFSheet s=w.getSheet(sheetname);
        Row r=s.getRow(i);
        Cell c=r.getCell(j);
        rowTotal=s.getLastRowNum();
        if ((rowTotal > 0) || (s.getPhysicalNumberOfRows() > 0)) {
            rowTotal++;
            return c.getStringCellValue();

    }*/
/*    public static String readIntegerData(int i,int j) throws IOException
    {
        FileInputStream f=new FileInputStream("F:\\IdeaProjects\\Seleniumbasics\\src\\main\\resources\\TestData.xlsx");
        XSSFWorkbook w=new XSSFWorkbook(f);
        XSSFSheet s=w.getSheet("Sheet1");
        Row r=s.getRow(i);
        Cell c=r.getCell(j);
        int a=(int) c.getNumericCellValue();
        return String.valueOf(a);
    }*/

        public List<String> readDataFromExcel(String filePath, String sheetName) throws IOException {
            String path = System.getProperty("user.dir")+ File.separator+ filePath;
            List<String> list = new ArrayList<String>();
            DataFormatter formatter = new DataFormatter();
            FileInputStream file = new FileInputStream(path);
            XSSFWorkbook workbook = new XSSFWorkbook(file);
            XSSFSheet sheet = workbook.getSheet(sheetName);
            for (Row r : sheet) {
                for (Cell c : r) {
                    list.add(formatter.formatCellValue(c));
                }
            }
            return list;
        }
    }

