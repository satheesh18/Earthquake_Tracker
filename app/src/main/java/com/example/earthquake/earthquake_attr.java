package com.example.earthquake;
import java.text.SimpleDateFormat;
import java.util.Date;

public class earthquake_attr {
    private String magnitude;
    private String location;
    private long unix;
    private String date;
    private String time;
    private String url;
    public earthquake_attr(String temp1,String temp2,long temp3,String temp4){
        magnitude=temp1;
        location=temp2;
        unix=temp3;
        url=temp4;
    }
    public String getmagnitude(){
        return magnitude;
    }
    public String getlocation(){
        return location;
    }
    public long getunix(){
        return unix;
    }
    public String getdate(){
        Date object=new Date(getunix());
        SimpleDateFormat dat=new SimpleDateFormat("MMM dd,YYY");
        date=dat.format(object);
        return date;
    }
    public String gettime(){
        Date object=new Date(getunix());
        SimpleDateFormat dat=new SimpleDateFormat("h:mm a");
        time=dat.format(object);
        return time;
    }
    public String geturl(){
        return url;
    }
}

