package com.example.compilerprojectgui;

import java.io.File;
import java.io.FileNotFoundException;

import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class Main extends Application {

	public static void main(String[] args) throws FileNotFoundException {
		launch(args);
	}

	File f = null;

	@Override
	public void start(Stage primaryStage) throws Exception {

		TabPane tp = new TabPane();

		Tab fileTab = new Tab("File");
		Tab tokensTab = new Tab("Tokens");
		fileTab.setClosable(false);
		tokensTab.setClosable(false);

		tp.getTabs().addAll(fileTab, tokensTab);

		BorderPane fileBP = new BorderPane();
		GridPane fileGP = new GridPane();

		Label chooseFileL = new Label("Choose file containing syntax:");
		FileChooser fc = new FileChooser();
		Button chooseFileBtn = new Button("Choose File");
		Label fileNameL = new Label();
		chooseFileBtn.setOnAction(e -> {
			f = fc.showOpenDialog(primaryStage);
			fileNameL.setText(f.getName());
		});
		Button parseBtn = new Button("Parse File");

		Label parserOutputL = new Label("Parser's Output:");
		TextArea parserOutTA = new TextArea();
		parserOutTA.setEditable(false);

		TableView<Token> tableView = new TableView<>();

		TableColumn<Token, String> typeColumn = new TableColumn<>("Token Type");
		typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));

		TableColumn<Token, String> contentColumn = new TableColumn<>("Content");
		contentColumn.setCellValueFactory(new PropertyValueFactory<>("content"));

		TableColumn<Token, Integer> lineNumColumn = new TableColumn<>("Line Number");
		lineNumColumn.setCellValueFactory(new PropertyValueFactory<>("lineNum"));

		tableView.getColumns().addAll(typeColumn, contentColumn, lineNumColumn);
		tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

		parserOutTA.setMinWidth(600);

		parseBtn.setOnAction(e -> {
			Parser p = null;
			try {
				p = new Parser(f.getPath());
				parserOutTA.setText(p.result);
				tableView.getItems().clear();
				if(p.tokenizer!=null)
				for (Token t : p.tokenizer.tokens) {
					tableView.getItems().add(t);
				}

			} catch (Exception e1) {
				parserOutTA.setText(p.result);
				tableView.getItems().clear();
				if(p.tokenizer!=null)
				for (Token t : p.tokenizer.tokens) {
					tableView.getItems().add(t);
				}
			}
		});

		fileGP.add(chooseFileL, 0, 0);
		fileGP.add(chooseFileBtn, 1, 0);
		fileGP.add(fileNameL, 2, 0);
		fileGP.add(parseBtn, 1, 1);
		fileGP.setVgap(15);
		fileGP.setHgap(15);
		fileGP.setAlignment(Pos.CENTER);

		GridPane fileBottomGP = new GridPane();
		fileBottomGP.add(parserOutputL, 0, 0);
		fileBottomGP.add(parserOutTA, 0, 1);
		fileBottomGP.setVgap(15);
		fileBottomGP.setAlignment(Pos.CENTER);

		fileBP.setCenter(fileGP);
		fileBP.setBottom(fileBottomGP);
		fileBP.setMargin(fileBottomGP, new Insets(15));
		fileTab.setContent(fileBP);

		BorderPane tokensBP = new BorderPane();
		
		tokensBP.setCenter(tableView);
		tokensBP.setAlignment(tableView, Pos.CENTER);
		tokensTab.setContent(tokensBP);
		
		Scene scene = new Scene(tp, 800, 400);
//		scene.getStylesheets().add(getClass().getResource("/styles/style.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.setTitle("1210082 - Omar Kashour - Compiler Project - Recursive Decsent Parser");
		primaryStage.show();

	}
}
