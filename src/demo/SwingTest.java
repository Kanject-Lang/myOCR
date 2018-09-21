package demo;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

/**
 * @description: TODO
 * @author: Kanject
 */
public class SwingTest extends JFrame {
    JButton open = null;
    JButton commit = null;
    JLabel path = null;
    JLabel label = null;
    File destFile = null;
    public static void main(String[] args) {
        new SwingTest();
    }
    public SwingTest(){
        open = new JButton("浏览");
        path = new JLabel();
        label = new JLabel("请选择文件夹");
        commit = new JButton("识别");
        destFile = new File("C:\\Users\\ASUS\\Desktop\\ocr\\ocr测试数据\\天猫工商信息执照");
        path.setText(destFile.getAbsolutePath());
        open.setBounds(100, 100, 80, 30);
        path.setBounds(100, 50, 800, 30);
        label.setBounds(100,150, 800, 30);
        commit.setBounds(250, 100, 80, 30);
        this.add(open);
        this.add(path);
        this.add(label);
        this.add(commit);
        this.setTitle("OCR");
        this.setBounds(400, 200, 500, 300);
        this.setLayout(null);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        open.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                JFileChooser jfc=new JFileChooser();
                jfc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES );
                jfc.showDialog(new JLabel(), "选择");
                destFile = jfc.getSelectedFile();
                    if(destFile.isDirectory()){
                        path.setText(destFile.getAbsolutePath());
                        System.out.println("文件夹:"+destFile.getAbsolutePath());
                    }else if(destFile.isFile()){
                        path.setText(destFile.getAbsolutePath());
                        System.out.println("文件:"+destFile.getAbsolutePath());
                    }
                System.out.println(jfc.getSelectedFile().getName());
            }
        });
        commit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                label.setText("执行中...");
                try {
                    System.out.println(label.getText());
                    Test3.run(destFile.getAbsolutePath());
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                label.setText("执行成功");
            }
        });
    }

}
