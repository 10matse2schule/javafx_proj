package main.view;

import javafx.fxml.FXML;
import main.MainGraph;

public class RootLayoutController {
	
	private MainGraph mainGraph;

	public void setMainGraph(MainGraph mainGraph) {
        this.mainGraph = mainGraph;
    }
	
	@FXML
	public void handleQuit() {
		System.exit(0);
	}
	
	@FXML
	private void handleRepaint() {
		mainGraph.showBetterGraph();
	}
}
