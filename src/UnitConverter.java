import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

public class UnitConverter extends JFrame {
	
	private JTextField inputField;
	private JTextField outputField;
	
	private JComboBox<String> conversionTypeComboBox;
	private JComboBox<String> fromUnitComboBox;
	private JComboBox<String> toUnitComboBox;
	
	private static final String[] CONVERSION_TYPES = {
			"Distance", "Time"
	};
	
	private static final String[] DISTANCE_UNITS = {
			"Meters", "Feet", "Inches", "Kilometers", "Miles"
	};
	
	private static final String[] TIME_UNITS = {
			"Seconds", "Minutes", "Hours", "Days"
	};
	
	public UnitConverter() {
		setTitle("Unit Converter");
		setSize(500, 500);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new GridLayout(6, 2, 10, 10));
		getContentPane().setBackground(Color.BLACK);
		
		JLabel titleLabel = new JLabel("Unit Converter", SwingConstants.CENTER);
		titleLabel.setForeground(Color.WHITE);
		
		conversionTypeComboBox = new JComboBox<>(CONVERSION_TYPES);
		conversionTypeComboBox.addActionListener(new ConversionTypeListener());
		
		fromUnitComboBox = new JComboBox<>(DISTANCE_UNITS);
		toUnitComboBox = new JComboBox<>(DISTANCE_UNITS);
		
		inputField = new JTextField();
		outputField = new JTextField();
		outputField.setEditable(false);
		
		JButton convertButton = new JButton("Convert");
		convertButton.addActionListener(new ConvertButtonListener());
		
		add(titleLabel);
		add(new JLabel());
		
		add(new JLabel("Conversion Type:", SwingConstants.RIGHT));
		add(conversionTypeComboBox);
		
		add(new JLabel("From Unit:", SwingConstants.RIGHT));
		add(fromUnitComboBox);
		
		add(new JLabel("To Unit:", SwingConstants.RIGHT));
		add(toUnitComboBox);
		
		add(new JLabel("Input Value:", SwingConstants.RIGHT));
		add(inputField);
		
		add(new JLabel("Converted Value:", SwingConstants.RIGHT));
		add(outputField);
		
		add(new JLabel());
		add(convertButton);
		
        titleLabel.setForeground(Color.WHITE);
        conversionTypeComboBox.setBackground(Color.DARK_GRAY);
        conversionTypeComboBox.setForeground(Color.WHITE);
        fromUnitComboBox.setBackground(Color.DARK_GRAY);
        fromUnitComboBox.setForeground(Color.WHITE);
        toUnitComboBox.setBackground(Color.DARK_GRAY);
        toUnitComboBox.setForeground(Color.WHITE);
        inputField.setBackground(Color.DARK_GRAY);
        inputField.setForeground(Color.WHITE);
        outputField.setBackground(Color.DARK_GRAY);
        outputField.setForeground(Color.WHITE);
        convertButton.setBackground(Color.DARK_GRAY);
        convertButton.setForeground(Color.WHITE);
		
	}
	
	private class ConversionTypeListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			String selectedType = (String) conversionTypeComboBox.getSelectedItem();
			if ("Distance".equals(selectedType)) {
				fromUnitComboBox.setModel(new DefaultComboBoxModel<>(DISTANCE_UNITS));
				toUnitComboBox.setModel(new DefaultComboBoxModel<>(DISTANCE_UNITS));
			} else if ("Time".equals(selectedType)) {
				fromUnitComboBox.setModel(new DefaultComboBoxModel<>(TIME_UNITS));
				toUnitComboBox.setModel(new DefaultComboBoxModel<>(TIME_UNITS));
			}
		}
	}
	
	private class ConvertButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				double inputValue = Double.parseDouble(inputField.getText());
				String fromUnit = (String) fromUnitComboBox.getSelectedItem();
				String toUnit = (String) toUnitComboBox.getSelectedItem();
				String conversionType = (String) conversionTypeComboBox.getSelectedItem();
				
				double convertedValue = performConversion(inputValue, fromUnit, toUnit, conversionType);
				
				outputField.setText(String.format("%.4f", convertedValue));
			} catch (IllegalArgumentException ex) {
				JOptionPane.showMessageDialog(UnitConverter.this, ex.getMessage(), "Conversion Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	
	private double performConversion(double inputValue, String fromUnit, String toUnit, String conversionType) {
		if ("Distance".equals(conversionType)) {
			return convertDistance(inputValue, fromUnit, toUnit);
		} else if ("Time".equals(conversionType)) {
			return convertTime(inputValue, fromUnit, toUnit);
		}
		
		throw new IllegalArgumentException("Invalid conversion type");
	}
	
	private double convertDistance(double inputValue, String fromUnit, String toUnit) {
		double meters = convertToMeters(inputValue, fromUnit);
		return convertFromMeters(meters, toUnit);
	}
	
	private double convertToMeters(double value, String fromUnit) {
        switch (fromUnit) {
        case "Meters": return value;
        case "Feet": return value * 0.3048;
        case "Inches": return value * 0.0254;
        case "Kilometers": return value * 1000;
        case "Miles": return value * 1609.344;
        default: throw new IllegalArgumentException("Unknown distance unit");
        }
	}
	
	private double convertFromMeters(double meters, String toUnit) {
		switch (toUnit) {
		case "Meters": return meters;
		case "Feet": return meters/ 0.3048;
		case "Inches" : return meters / 0.0254;
		case "Kilometers" : return meters / 1000;
		case "Miles" : return meters / 1609.344;
		default: throw new IllegalArgumentException("Unknown distance unit");
		}
	}
	
    private double convertTime(double inputValue, String fromUnit, String toUnit) {
        double seconds = convertToSeconds(inputValue, fromUnit);
        return convertFromSeconds(seconds, toUnit);
    }
    
    private double convertToSeconds(double value, String fromUnit) {
        switch (fromUnit) {
            case "Seconds": return value;
            case "Minutes": return value * 60;
            case "Hours": return value * 3600;
            case "Days": return value * 86400;
            default: throw new IllegalArgumentException("Unknown time unit");
        }
    }
    
    private double convertFromSeconds(double seconds, String toUnit) {
        switch (toUnit) {
            case "Seconds": return seconds;
            case "Minutes": return seconds / 60;
            case "Hours": return seconds / 3600;
            case "Days": return seconds / 86400;
            default: throw new IllegalArgumentException("Unknown time unit");
        }
    }
 
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			UnitConverter converter = new UnitConverter();
			converter.setVisible(true);
		});
		
	}

}
