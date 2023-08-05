package com.z;


import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

public class UserInterface extends Application {

    FileProcess fileProcess = new FileProcess();
    File newFile;

    /**
     *
     */
    @Override
    public void start(Stage arg0) throws Exception {

        VBox total = new VBox();
        HBox massage = new HBox();
        VBox oldMassage = new VBox();
        VBox newMassage = new VBox();

        VBox getPath = new VBox();
        HBox getSoursePath = new HBox();
        HBox getNewPath = new HBox();

        HBox buttonSet = new HBox();

        Label label_sourse = new Label("原文件信息:");
        Label label_new = new Label("压缩文件信息:");
        Label label_soursePath = new Label("请输入原文件路径:");
        Label label_newPath = new Label("你想将压缩文件储存在:");

        final TextArea area_sourseMassage = new TextArea();
        final TextArea area_newMassage = new TextArea();
        final TextField field_soursePath = new TextField();
        final TextField field_newPath = new TextField();

        Button button_browserSourse = new Button("Browser");
        Button button_browserNewPath = new Button("Browser");

        Button button_compress = new Button("Compress");
        Button button_decode = new Button("Decode");
        final Button button_cancal = new Button("Cancal");

        area_sourseMassage.setEditable(false);
        area_sourseMassage.setWrapText(true);
        area_newMassage.setEditable(false);
        area_newMassage.setWrapText(true);

        oldMassage.getChildren().addAll(label_sourse, area_sourseMassage);
        newMassage.getChildren().addAll(label_new, area_newMassage);

        oldMassage.setPadding(new Insets(10, 10, 10, 20));
        newMassage.setPadding(new Insets(10, 20, 10, 0));

        field_soursePath.setPrefHeight(button_browserNewPath.getHeight());
        field_soursePath.setPrefWidth(430);
        field_newPath.setPrefHeight(button_browserNewPath.getHeight());
        field_newPath.setPrefWidth(430);

        button_compress.setPrefWidth(100);
        button_cancal.setPrefWidth(100);
        button_decode.setPrefWidth(100);

        getSoursePath.getChildren().addAll(label_soursePath, field_soursePath, button_browserSourse);
        getNewPath.getChildren().addAll(label_newPath, field_newPath, button_browserNewPath);

        getSoursePath.setSpacing(20);
        getSoursePath.setAlignment(Pos.CENTER_RIGHT);
        getNewPath.setSpacing(20);
        getNewPath.setAlignment(Pos.CENTER_RIGHT);

//		massage.setPadding(new Insets(10));
        massage.getChildren().addAll(oldMassage, newMassage);

        getPath.getChildren().addAll(getSoursePath, getNewPath);
        getPath.setPadding(new Insets(0, 15, 0, 0));
        getPath.setSpacing(10);

        buttonSet.getChildren().addAll(button_compress, button_cancal, button_decode);
        buttonSet.setAlignment(Pos.CENTER);
        buttonSet.setPadding(new Insets(10, 0, 0, 0));
        buttonSet.setSpacing(100);

        total.getChildren().addAll(massage, getPath, buttonSet);
        Scene scene = new Scene(total);
        arg0.setHeight(350);
        arg0.setWidth(700);
        arg0.setResizable(false);
        arg0.setScene(scene);
        arg0.setTitle("文本文件压缩系统");


        button_browserSourse.setOnAction(new EventHandler<ActionEvent>() {

            public void handle(ActionEvent arg0) {
                // TODO Auto-generated method stub
                FileChooser fileChooser = new FileChooser();
                //fileChooser.getExtensionFilters().add(new ExtensionFilter("文本文件", ".txt"));
                File fileSourse = fileChooser.showOpenDialog(null);
                try {
                    field_soursePath.setText(fileSourse.getAbsolutePath());
                    String path = field_soursePath.getText().replaceAll("\\\\", "\\\\\\\\");
                    File sourseFile = new File(path);
                    /**
                     * 信息输出格式： 文件名： 文件类型： 文件路径： 文件大小：
                     */
                    area_sourseMassage.setText("文件名：" + sourseFile.getName() + "\n" + "文件类型："
                            + sourseFile.toURL().openConnection().getContentType() + "\n" + "文件路径："
                            + sourseFile.getAbsolutePath() + "\n" + "文件大小：" + sourseFile.length() + "字节");

                } catch (NullPointerException e) {
                    // TODO: handle exception
                    area_sourseMassage.setText("请选择文件！！！");
                } catch (MalformedURLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        });

        final DirectoryChooser directoryChooser = new DirectoryChooser();
        button_browserNewPath.setOnAction(new EventHandler<ActionEvent>() {

            public void handle(ActionEvent arg0) {
                // TODO Auto-generated method stub
                File fileNew = directoryChooser.showDialog(null);
                try {
                    field_newPath.setText(fileNew.getPath());
                } catch (NullPointerException e) {
                    // TODO: handle exception
                    area_newMassage.setText("请选择压缩文件储存路径！！！");
                }
            }
        });

        button_compress.setOnAction(new EventHandler<ActionEvent>() {

            public void handle(ActionEvent arg0) {
                // TODO Auto-generated method stub
                if (field_soursePath.getText() == "") {
                    area_sourseMassage.setText("请选择文件！！！");
                } else {
                    // 将路径中的\换成\\
                    String path = field_soursePath.getText().replaceAll("\\\\", "\\\\\\\\");
                    File sourseFile = new File(path);

                    String newPath = null;

                    if (field_newPath.getText() == "") {
                        area_newMassage.setText("请选择压缩文件储存路径！！！");
                    } else {
                        newPath = field_newPath.getText().replaceAll("\\\\", "\\\\\\\\") + "\\" + sourseFile.getName()
                                + ".huf";
                        try {
                            fileProcess.compress(path, newPath); // 在新地址创建新文件
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                    newFile = new File(newPath);
                    try {
                        area_newMassage.setText("压缩成功！\n" + "文件名：" + newFile.getName() + "\n" + "文件类型："
                                + newFile.toURL().openConnection().getContentType() + "\n" + "文件路径："
                                + newFile.getAbsolutePath() + "\n" + "文件大小：" + newFile.length() + "字节");
                    } catch (MalformedURLException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

                }
                if (field_newPath.getText() == "") {
                    area_newMassage.setText("请选择压缩文件储存路径！！！");
                }
            }
        });
        button_cancal.setOnAction(new EventHandler<ActionEvent>() {

            public void handle(ActionEvent arg0) {
                // TODO Auto-generated method stub
                Stage stage = (Stage) button_cancal.getScene().getWindow();
                stage.close();
            }
        });

        button_decode.setOnAction(new EventHandler<ActionEvent>() {

            public void handle(ActionEvent arg0) {
                // TODO Auto-generated method stub
                try {
                    Stage stage = new Stage();

                    VBox vBox = new VBox();
                    HBox hBox1 = new HBox();
                    HBox hBox2 = new HBox();
                    HBox hBox3 = new HBox();


                    Label label = new Label();
                    label.setText("解码内容为:");
                    TextArea area_decodeString = new TextArea();
                    area_decodeString.setWrapText(true);

                    String path = newFile.getAbsolutePath().replaceAll("\\\\", "\\\\\\\\");
                    //FileProcess fileProcess = new FileProcess();
                    System.out.println(path);
                    try {
                        String string = fileProcess.decode(path, fileProcess.huffmanTree);
                        area_decodeString.setText(string);
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

                    final Button button = new Button();
                    button.setText("确认");
                    button.setPrefWidth(100);

                    hBox1.getChildren().add(label);
                    hBox2.getChildren().add(area_decodeString);
                    hBox3.getChildren().add(button);
                    hBox3.setAlignment(Pos.CENTER);
                    hBox1.setPadding(new Insets(10, 0, 0, 10));
                    hBox2.setPadding(new Insets(0, 10, 0, 10));
                    hBox3.setPadding(new Insets(10));
                    vBox.getChildren().addAll(hBox1, hBox2, hBox3);
                    vBox.setSpacing(10);
                    Scene scene = new Scene(vBox);
                    stage.setScene(scene);
                    stage.show();

                    button.setOnAction(new EventHandler<ActionEvent>() {

                        public void handle(ActionEvent arg0) {
                            // TODO Auto-generated method stub
                            Stage stage = (Stage) button.getScene().getWindow();
                            stage.close();
                        }
                    });
                } catch (NullPointerException e) {
                    // TODO: handle exception
                    area_newMassage.setText("***Error!:未找到压缩文件！！！***");
                }

            }
        });
        arg0.show();
    }
}

