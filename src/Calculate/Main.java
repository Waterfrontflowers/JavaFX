package Calculate;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Ginger
 * @date 2021/11/23
 */
public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setTitle("Calculator");

        GridPane gridPane = new GridPane();

        Button[] button = new Button[19];

        for(int i = 0 ; i < 10 ; i++){
            button[i] = new Button(String.format("%d",i));
        }
        button[10] = new Button("+");
        button[11] = new Button("-");
        button[12] = new Button("*");
        button[13] = new Button("/");
        button[14] = new Button(".");
        button[15] = new Button("CE");
        button[16] = new Button("C");
        button[17] = new Button("退格");
        button[18] = new Button("=");


        gridPane.add(button[7],1,2);
        gridPane.add(button[8],2,2);
        gridPane.add(button[9],3,2);
        gridPane.add(button[4],1,3);
        gridPane.add(button[5],2,3);
        gridPane.add(button[6],3,3);
        gridPane.add(button[1],1,4);
        gridPane.add(button[2],2,4);
        gridPane.add(button[3],3,4);
        gridPane.add(button[0],2,5);
        gridPane.add(button[10],4,5);
        gridPane.add(button[11],4,4);
        gridPane.add(button[12],4,3);
        gridPane.add(button[13],4,2);
        gridPane.add(button[14],1,5);
        gridPane.add(button[18],3,5);
        gridPane.add(button[15],2,1);
        gridPane.add(button[16],3,1);
        gridPane.add(button[17],4,1);

        BorderPane borderPane = new BorderPane();

        TextField textField = new TextField();

        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            String rule = "[^0123456789.+-/*=]";

            Pattern pattern = Pattern.compile(rule);
            Matcher matcher = pattern.matcher(newValue);

            if (matcher.find()) {
                System.out.println("输入异常");
                textField.setText(oldValue);
            }

        });

        textField.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER)  {
                textField.setText(textField.getText() + button[18].getText()+Calculate.calculate(textField.getText()));
            }
            if (keyEvent.getCode() == KeyCode.ADD || keyEvent.getCode() == KeyCode.SUBTRACT || keyEvent.getCode() == KeyCode.MULTIPLY || keyEvent.getCode() == KeyCode.DIVIDE)  {
                String rule = ".*=((\\-)?\\d+(\\.\\d{2}))";
                Pattern pattern = Pattern.compile(rule);
                Matcher matcher = pattern.matcher(textField.getText());
                if (matcher.find()) {
                    textField.setText(matcher.group(1));
                    textField.positionCaret(textField.getText().length());
                }
            }
        });

        for (int i = 0 ; i < 15 ;i ++) {
            int finalI = i;
            button[i].setOnAction(event -> {
                if(finalI>9&&finalI<14) {
                    String rule = ".*=((\\-)?\\d+(\\.\\d{2}))";
                    Pattern pattern = Pattern.compile(rule);
                    Matcher matcher = pattern.matcher(textField.getText());
                    if (matcher.find()) {
                        textField.setText(matcher.group(1));
                    }
                }
                textField.setText(textField.getText() + button[finalI].getText());
            });
        }

        button[18].setOnAction(event -> textField.setText(textField.getText() + button[18].getText()+Calculate.calculate(textField.getText())));

        button[15].setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(!textField.getText().substring(textField.getText().length()-1).matches("[+\\-*/]")){
                    textField.setText(textField.getText().substring(0, textField.getText().length() - 1));
                }
                if(textField.getText().matches(".*=.*")){
                    textField.setText("");
                }
            }
        });

        button[16].setOnAction(event -> textField.setText(""));

        button[17].setOnAction(event -> {
            String rule = ".*=((\\-)?\\d+(\\.\\d{2}))";
            Pattern pattern = Pattern.compile(rule);
            Matcher matcher = pattern.matcher(textField.getText());
            if(matcher.find()){
                textField.setText(matcher.group(1));
            }
            if(textField.getText().length() > 0) {
                textField.setText(textField.getText().substring(0, textField.getText().length() - 1));
            }
        });


        borderPane.setTop(textField);
        borderPane.setCenter(gridPane);

        primaryStage.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                double f = newValue.doubleValue()/1080;
                //设置输入框的高度
                textField.setPrefHeight(200 * f);
                textField.setFont(Font.font(60 * f));
                //设置按钮的高度
                for(int i = 0 ; i < 19 ; i++) {
                    button[i].setPrefHeight(2000 * f);
                    //设置按钮字体大小
                    button[i].setFont(Font.font(60 * f));
                    //设置按钮位置Y
                    button[i].setLayoutY(800 * f);
                }
            }
        });
        primaryStage.widthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                double f = newValue.doubleValue() / 1920;
                //设置输入框的宽度
                textField.setPrefWidth(100 * f);
                textField.setFont(Font.font(60 * f));
                //设置按钮的宽度
                for(int i = 0 ; i < 19 ; i++) {
                    button[i].setPrefWidth(1000 * f);
                    //设置按钮字体大小
                    button[i].setFont(Font.font(60 * f));
                    //设置按钮位置X
                    button[i].setLayoutX(1375 * f);
                }
            }
        });

        primaryStage.setScene(new Scene(borderPane, 300, 275));
        primaryStage.show();

    }


    public static void main(String[] args) {
        launch(args);
    }
}
