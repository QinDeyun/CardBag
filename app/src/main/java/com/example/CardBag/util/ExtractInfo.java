package com.example.CardBag.util;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExtractInfo {
    public static String getInf(String args) {
        String str = args;
        String namePattern = "姓名：(\\S+)";
        String idPattern = "学号：(\\S+)";
        String classPattern = "班级：(\\S+)";
        String collegePattern = "学院：(\\S+)";
        Pattern pattern;
        Matcher matcher;
        String result = "";

        pattern = Pattern.compile(namePattern);
        matcher = pattern.matcher(str);
        if (matcher.find()) {
            String shuChu = "姓名：" + matcher.group(1);
            System.out.println(shuChu);
            result += shuChu;
            result = result + "\r\n";
        }

        pattern = Pattern.compile(idPattern);
        matcher = pattern.matcher(str);
        if (matcher.find()) {
            String shuChu = "学号：" + matcher.group(1);
            System.out.println(shuChu);
            result += shuChu;
            result = result + "\r\n";
        }

        pattern = Pattern.compile(classPattern);
        matcher = pattern.matcher(str);
        if (matcher.find()) {
            String shuChu = "班级：" + matcher.group(1);
            System.out.println(shuChu);
            result += shuChu;
            result = result + "\r\n";
        }

        pattern = Pattern.compile(collegePattern);
        matcher = pattern.matcher(str);
        if (matcher.find()) {
            String shuChu = "学院：" + matcher.group(1);
            System.out.println(shuChu);
            result += shuChu;
        }
        return result;
    }

    public static HashMap<String,String> getmap(String args) {

        HashMap<String,String> result=new HashMap<>();
        String str = args;
        String namePattern = "姓名：(\\S+)";
        String idPattern = "学号：(\\S+)";
        String classPattern = "班级：(\\S+)";
        String collegePattern = "学院：(\\S+)";
        Pattern pattern;
        Matcher matcher;


        pattern = Pattern.compile(namePattern);
        matcher = pattern.matcher(str);
        if (matcher.find()) {

            result.put("sname",matcher.group(1));
        }

        pattern = Pattern.compile(idPattern);
        matcher = pattern.matcher(str);
        if (matcher.find()) {

            result.put("snum",matcher.group(1));
        }

        pattern = Pattern.compile(classPattern);
        matcher = pattern.matcher(str);
        if (matcher.find()) {


            result.put("sclass",matcher.group(1));
        }

        pattern = Pattern.compile(collegePattern);
        matcher = pattern.matcher(str);
        if (matcher.find()) {

            result.put("scollege",matcher.group(1));
        }
        return result;
    }
}
