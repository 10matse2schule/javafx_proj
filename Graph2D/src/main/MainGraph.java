package main;

import java.io.IOException;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import main.model.Equation;
import main.view.BetterGraphController;
import main.view.RootLayoutController;


public class MainGraph extends Application {
		
	private Stage primaryStage;
	private BorderPane rootLayout;
	
	private Equation equation;

		 
	@Override
	public void start(Stage primaryStage) throws Exception {
		this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Graph2D");
        
        initRootLayout();
      
        showBetterGraph();
		
	}
	
	public void showBetterGraph() {
		try {
      
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainGraph.class.getResource("view/BetterGraph.fxml"));
            AnchorPane inputFunction = (AnchorPane) loader.load();

            rootLayout.setCenter(inputFunction);
            
            BetterGraphController controller = loader.getController();
            controller.setMainGraph(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
		
	}

	private void initRootLayout() {
		try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainGraph.class
                    .getResource("view/RootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();

            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);

            RootLayoutController controller = loader.getController();
            controller.setMainGraph(this);

            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
		
	}

	public static void main(String[] args) {
		launch(args);
	}

	public void setEquation(Equation equation) {
		this.equation = equation;
	}
	
	public Equation getEquation() {
		return equation;
	}


}
