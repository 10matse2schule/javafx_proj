package main.view;

import java.util.ArrayList;

import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import main.MainGraph;
import main.model.DoublePoint;
import main.model.Equation;
import util.EquationUtil;
import util.ParserUtil;

public class BetterGraphController {
	
	@FXML
	private LineChart<Number,Number> graph;
	@FXML
	private NumberAxis yAxis;
	@FXML
	private NumberAxis xAxis;
	@FXML 
	private TextField equationField;
	@FXML
	private TextField minRangeField;
	@FXML
	private TextField maxRangeField;
	@FXML
	private TextField accField;
	@FXML 
	private Button reset;
	@FXML
	private Button add;
	
	 
	@SuppressWarnings("unused")
	private final int FUNCTIONNAME = 102;
	private MainGraph mainGraph;
	private ArrayList<XYChart.Series<Number, Number>> functionList;
	private int functionNumber;
	
    @FXML
    private void initialize() {
    	functionList = new ArrayList<XYChart.Series<Number, Number>>();
    	functionNumber = FUNCTIONNAME;
    	xAxis.setLabel("X-Axis");
		yAxis.setLabel("Y-Axis");
		graph.setCreateSymbols(false);
		
    }
	
	@FXML
	private void handleAdd() {
		String possibleErrors = checkFields();
		if(!(possibleErrors.equals(""))) {
			Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Some of your fields are invalid!");
            alert.setContentText(possibleErrors);
            alert.showAndWait();
            return;
		}
		try {
			Equation actualEquation = ParserUtil.parseEquation(equationField.getText());
			ArrayList<DoublePoint> points = EquationUtil.generatePoints(
					actualEquation,
					Double.parseDouble(accField.getText()),
					Double.parseDouble(minRangeField.getText()),
					Double.parseDouble(maxRangeField.getText())
					);
			
			XYChart.Series<Number, Number> function = new XYChart.Series<Number, Number>();
			function.setName(Character.toString((char)functionNumber) + "(x): " + actualEquation.toString());
			functionNumber++;
			for(DoublePoint fp : points) {
				System.out.println(fp.getX() + " " + fp.getY());
				XYChart.Data<Number,Number> data = new XYChart.Data<Number,Number>(fp.getX(),fp.getY());
				function.getData().add(data);
			}
			
			functionList.add(function);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		draw();
	}
	
	@FXML 
	private void handleReset() {
		//TODO Fix Draw Problem (Duplicate Problem)
		//functionList.clear();
		//draw();
		functionNumber = FUNCTIONNAME;
		functionList.clear();
		graph.getData().clear();
	}
	
	public void draw() {
		//TODO Fix Draw Problem (Duplicate Problem)
		//graph.getData().addAll(functionList);
		if(functionList.size() > 0) {
			graph.getData().add(functionList.get(functionList.size() -1));
		}
	}
	
	
	private String checkFields() {
		String errors = "";
		if(equationField.getText() == null || equationField.getText().trim().isEmpty()) {
			errors += "Unvalid Equation\n";
		}
		if(accField.getText() == null || accField.getText().trim().isEmpty()) {
			errors += "Unvalid Accuracy\n";
		}
		if(minRangeField.getText() == null || minRangeField.getText().trim().isEmpty()) {
			errors += "Unvalid MinRange\n";
		}
		if(maxRangeField.getText() == null || maxRangeField.getText().trim().isEmpty()) {
			errors += "Unvalid MaxRange\n";
		}
		try {
			Float.parseFloat(accField.getText());
			Float.parseFloat(minRangeField.getText());
			Float.parseFloat(maxRangeField.getText());
		}
		catch(NumberFormatException n) {
			errors += "Unvalid Datatypes!\n";
		}
		
		return errors;
	}
	
	
	public void setMainGraph(MainGraph mainGraph) {
        this.mainGraph = mainGraph;
    }
	
	
}
