package demo;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import util.CmdOcrUtil;
import util.ImgUtil;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @description: TODO
 * @author: Kanject
 */
public class Test3 {
//    public static void main(String args[]) throws IOException {
//        run("C:\\Users\\ASUS\\Desktop\\ocr\\ocr测试数据\\修改后");
//        File numText = new File("C:\\Users\\ASUS\\Desktop\\ocr\\ocr测试数据\\修改后"+"\\num.txt");
//        File nameText = new File("C:\\Users\\ASUS\\Desktop\\ocr\\ocr测试数据\\修改后"+"\\name.txt");
//
//        numText.delete();
//        nameText.delete();
//    }

    public static void run(String folderPath) throws IOException {
        CmdOcrUtil util = new CmdOcrUtil();

        File file = new File(folderPath);
        String[] fileNames = file.list();

        //定义表头
        String[] title = {"企业名称", "企业注册号"};
        //创建excel工作簿
        HSSFWorkbook workbook = new HSSFWorkbook();
        //创建工作表sheet
        HSSFSheet sheet = workbook.createSheet("poiTest");
        //创建第一行
        HSSFRow row = sheet.createRow(0);
        //插入第一行数据的表头
        HSSFCell cell = null;
        for(int i=0;i<title.length;i++){
            cell=row.createCell(i);
            cell.setCellValue(title[i]);
        }

        int rowNum = 1;
        for(String fileName : fileNames ){
            if (fileName.contains(".png")) {
                System.out.println(folderPath+"\\"+fileName);

                //填充背景为白色
                BufferedImage image = ImgUtil.compose(new File(folderPath+"\\"+fileName));
                ImgUtil.generate(image, folderPath+"\\updated.png");

                //按区域切割图片
                ImgUtil.cut(145, 0,2000, 45, folderPath+"\\updated.png", folderPath+"\\num.png");
                ImgUtil.cut(118, 40,2000, 43, folderPath+"\\updated.png", folderPath+"\\name.png");

                //读取相应区域的文本
                String num = util.getCaptureText(folderPath+"\\num.png", "eng");
                String name = util.getCaptureText(folderPath+"\\name.png", "chi_sim");

                if (num.equals("") || num == null) {
                    num = fileName + "需要人工识别";
                }

                if (name.equals("") || name == null) {
                    name = fileName + "需要人工识别";
                }

                HSSFRow nrow=sheet.createRow(rowNum++);
                HSSFCell ncell=nrow.createCell(0);
                ncell.setCellValue(name);
                ncell=nrow.createCell(1);
                ncell.setCellValue(num);

                System.out.println(num);
                System.out.println(name);
                System.out.println("-----------------");

            }
        }

        //创建excel文件
        File excelFile=new File(folderPath+"\\poi.xlsx");
        try {
            excelFile.createNewFile();
            //将excel写入
            FileOutputStream stream= new FileOutputStream(excelFile);
            workbook.write(stream);
            stream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        File updatedImg = new File(folderPath+"\\updated.png");
        File numImg = new File(folderPath+"\\num.png");
        File nameImg = new File(folderPath+"\\name.png");
        File numText = new File(folderPath+"\\num.txt");
        File nameText = new File(folderPath+"\\name.txt");

        updatedImg.delete();
        numImg.delete();
        nameImg.delete();
        numText.delete();
        nameText.delete();
    }

}
