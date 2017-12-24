package main.view;

import java.awt.Color;
import java.util.ArrayList;

import javafx.fxml.FXML;
import javafx.scene.Node;
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
	@FXML
	private TextField minYRangeField;
	@FXML
	private TextField maxYRangeField;
	
	 
	@SuppressWarnings("unused")
	private final char FIRST_FUNCTIONNAME = 'f';
	private char next_functionname;
	
	private MainGraph mainGraph;
	private ArrayList<XYChart.Series<Number, Number>> functionList;
	
	// sRGB luminance(Y) values
	final double rY = 0.212655;
	final double gY = 0.715158;
	final double bY = 0.072187;
	
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
			
			//Generate Random Color with no too much brightness
			Color color;
			do {
			color = new Color((int)(Math.random() * 0x1000000)).brighter();
			}while(gray(color) >= 180);
			
			String rgb = String.format("%d, %d, %d",
			        (int) (color.getRed()),
			        (int) (color.getGreen()),
			        (int) (color.getBlue()));
			
			ArrayList<XYChart.Series<Number, Number>> functionArray = new ArrayList<XYChart.Series<Number, Number>>();
			XYChart.Series<Number, Number> function = new XYChart.Series<Number, Number>();
			function.setName(next_functionname + "(x): " + equation.as_nice_string());
			
			boolean temp = false;
			//TODO Implementation for imaginary numbers(or the real part of it)
			for (long i=0; i<count; ++i) {
				double x = minRange +(double)i * acc;
				
				x_var.set_value(x);
				
				Double y = equation.calculate();
				
				if(y.isNaN() || y.isInfinite()) {
					continue;
				}
				if (y >= Double.parseDouble(minYRangeField.getText()) 
					&& y <= Double.parseDouble(maxYRangeField.getText())) {
					temp = true;
					function.getData().add(new XYChart.Data<Number,Number>(x,y));
				} else {
					if(temp) {
						functionArray.add(function);
						function = new XYChart.Series<Number, Number>();
						function.setName(next_functionname + "(x): " + equation.as_nice_string());
						temp = false;
					}
				}
				
				//System.out.println(">> "+ x +" "+ y);
			}
			if(!(function.getData().isEmpty())) {
				functionArray.add(function);
			}
			graph.getData().addAll(functionArray);
			next_functionname++;
			for( XYChart.Series<Number, Number> actFunction:functionArray) {
				Node line = actFunction.getNode().lookup(".chart-series-line");
				line.setStyle("-fx-stroke: rgba(" + rgb + ", 1.0);");
				line.applyCss();
			}
			
			//functionList.add(function);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	@FXML 
	private void handleReset() {
		next_functionname = FIRST_FUNCTIONNAME;
		graph.getData().clear();
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
		if(minYRangeField.getText() == null || minYRangeField.getText().trim().isEmpty()) {
			errors += "Unvalid MinYRange\n";
		}
		if(maxYRangeField.getText() == null || maxYRangeField.getText().trim().isEmpty()) {
			errors += "Unvalid MaxYRange\n";
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
	
	
	
	//Brightness Methods
	
	int gray(Color color) {
		
	    return gam_sRGB(
	            rY*inv_gam_sRGB(color.getRed()) +
	            gY*inv_gam_sRGB(color.getGreen()) +
	            bY*inv_gam_sRGB(color.getBlue())
	    );
	}
	
	// Inverse of sRGB "gamma" function. (approx 2.2)
	double inv_gam_sRGB(int ic) {
	    double c = ic/255.0;
	    if ( c <= 0.04045 )
	        return c/12.92;
	    else 
	        return Math.pow(((c+0.055)/(1.055)),2.4);
	}
	
	// sRGB "gamma" function (approx 2.2)
	int gam_sRGB(double v) {
	    if(v<=0.0031308)
	        v *= 12.92;
	    else 
	        v = 1.055*Math.pow(v,1.0/2.4)-0.055;
	    return (int) Math.floor((v*255+0.5)); 
	}
	
	
}
