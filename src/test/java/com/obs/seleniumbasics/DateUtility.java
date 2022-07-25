package com.obs.seleniumbasics;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtility {
    public String getCurrentdate(){
        SimpleDateFormat formatter=new SimpleDateFormat("dd/mm/yyyy");
        Date date=new Date();
        String currentDate=formatter.format(date);
        return currentDate;
    }
}
