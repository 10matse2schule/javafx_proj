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
import equation_parser.*;

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
	private final char FIRST_FUNCTIONNAME = 'f';
	private char next_functionname;
	
	private MainGraph mainGraph;
	private ArrayList<XYChart.Series<Number, Number>> functionList;
	
	@FXML
    private void initialize() {
    	next_functionname = FIRST_FUNCTIONNAME;
    	
		functionList = new ArrayList<XYChart.Series<Number, Number>>();
    	
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
			Equation equation = new Equation( equationField.getText() );
			
			Variable x_var = equation.add_var("x");
			
			if (!equation.parse()) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error");
				alert.setHeaderText("Error in equation parsing!");
				alert.setContentText(equation.get_error_msg());
				alert.showAndWait();
				return;
				
				//throw new Exception();
			}
			
			double acc = Double.parseDouble(accField.getText());
			double minRange = Double.parseDouble(minRangeField.getText());
			double maxRange = Double.parseDouble(maxRangeField.getText());
			
			double range = maxRange -minRange;
			long count = (long)Math.ceil(range / acc);
			
			XYChart.Series<Number, Number> function = new XYChart.Series<Number, Number>();
			function.setName(next_functionname + "(x): " + equation.as_nice_string());
			next_functionname++;
			
			final double CULL_Y = 10e20;
			
			for (long i=0; i<count; ++i) {
				double x = minRange +(double)i * acc;
				
				x_var.set_value(x);
				
				double y = equation.calculate();
				
				if (!(y >= -CULL_Y && y <= +CULL_Y)) continue; // get rid of very large or NaN values
				
				//System.out.println(">> "+ x +" "+ y);
				XYChart.Data<Number,Number> data = new XYChart.Data<Number,Number>(x,y);
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
		next_functionname = FIRST_FUNCTIONNAME;
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
			errors += "Invalid Equation\n";
		}
		if(accField.getText() == null || accField.getText().trim().isEmpty()) {
			errors += "Invalid Accuracy\n";
		}
		if(minRangeField.getText() == null || minRangeField.getText().trim().isEmpty()) {
			errors += "Invalid MinRange\n";
		}
		if(maxRangeField.getText() == null || maxRangeField.getText().trim().isEmpty()) {
			errors += "Invalid MaxRange\n";
		}
		try {
			Float.parseFloat(accField.getText());
			Float.parseFloat(minRangeField.getText());
			Float.parseFloat(maxRangeField.getText());
		}
		catch(NumberFormatException n) {
			errors += "Invalid Datatypes!\n";
		}
		
		return errors;
	}
	
	
	public void setMainGraph(MainGraph mainGraph) {
        this.mainGraph = mainGraph;
    }
	
	
}
