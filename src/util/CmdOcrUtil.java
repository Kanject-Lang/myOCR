package util;

import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @description: TODO
 * @author: Kanject
 */
public class CmdOcrUtil {

    List<String> keys;
    List<String> values;

    public CmdOcrUtil() {
        this.keys = new ArrayList<String>();
        this.values = new ArrayList<String>();
    }

    public CmdOcrUtil(List<String> keys, List<String> values) {
        this.keys = new ArrayList<String>();
        this.values = new ArrayList<String>();
    }

    public String getCaptureText(String imgPath, String language) {
        String result = "";
        BufferedReader bufReader = null;
        InputStreamReader inputStreamReader = null;
        try {
            String outPath = imgPath.substring(0, imgPath.lastIndexOf("."));
            Runtime runtime = Runtime.getRuntime();
            String command = "tesseract" + " " +  imgPath + " " + outPath +" "+ "-l " + language + " -psm 7";
            Process ps = runtime.exec(command);
            ps.waitFor();
            // 读取文件
            File file = new File(outPath + ".txt");
            bufReader = new BufferedReader(new InputStreamReader(new FileInputStream(file),"utf-8"));
            String temp = "";
            StringBuffer sb = new StringBuffer();
            while ((temp = bufReader.readLine()) != null) {
                sb.append(temp);
//                temp = temp.replaceAll(" ", "");

//                System.out.println(temp);
//                pickStr(temp);
            }
            bufReader.close();
            // 文字结果
            result = sb.toString();
            if (StringUtils.isNotBlank(result))
                result = result.replaceAll(" ", "");
        } catch (Exception e) {
            //logger.error("识别验证码异常，Exception:{}", e.getMessage());
            e.printStackTrace();
        }

//        show();
        return result.substring(0, result.length() - 1);

    }

    public void pickStr(String str) {
        int flag = str.indexOf(":");
        if (flag != -1) {
            String before = str.substring(0, flag);
            String after = str.substring(flag + 1, str.length());

            if (before != null || !before.equals("")) {
                keys.add(before);
            }

            if (after != null && !after.equals("")) {
                values.add(after);
            }

//            System.out.println(flag);
//            System.out.println(before);
//            System.out.println(after);
        } else {
            str.trim();
            if (str != null && !str.equals("")) {
                values.add(str);
            }
        }
    }

    public void show() {

        for (String key: keys
             ) {
            System.out.println(key);
        }

        System.out.println("-----------------");

        for (String value: values
                ) {
            System.out.println(value);
        }

//            System.out.println(flag);
//            System.out.println(before);
//            System.out.println(after);

    }
}
