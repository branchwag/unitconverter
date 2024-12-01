import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
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
		setLayout(new GridBagLayout());
		getContentPane().setBackground(Color.BLACK);
		
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(10, 10, 10, 10);
		
		JLabel titleLabel = new JLabel("Unit Converter", SwingConstants.CENTER);
		titleLabel.setForeground(Color.WHITE);
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 2;
		add(titleLabel, gbc);
		
		gbc.gridy++;
		gbc.gridwidth = 1;
		JLabel conversionTypeLabel = new JLabel("Conversion Type:", SwingConstants.LEFT);
		conversionTypeLabel.setForeground(Color.WHITE);
		add(conversionTypeLabel, gbc);
		
		gbc.gridx = 1;
		conversionTypeComboBox = new JComboBox<>(CONVERSION_TYPES);
		conversionTypeComboBox.addActionListener(new ConversionTypeListener());
		conversionTypeComboBox.setBackground(Color.DARK_GRAY);
		conversionTypeComboBox.setForeground(Color.WHITE);
		add(conversionTypeComboBox, gbc);
		
		gbc.gridx = 0;
		gbc.gridy++;
		JLabel fromUnitLabel = new JLabel("From Unit:", SwingConstants.LEFT);
		fromUnitLabel.setForeground(Color.WHITE);
		add(fromUnitLabel, gbc);
		
		gbc.gridx = 1;
		fromUnitComboBox = new JComboBox<>(DISTANCE_UNITS);
		fromUnitComboBox.setBackground(Color.DARK_GRAY);
		fromUnitComboBox.setForeground(Color.WHITE);
		add(fromUnitComboBox, gbc);
		
		gbc.gridx = 0;
		gbc.gridy++;
		JLabel toUnitLabel = new JLabel("To Unit:", SwingConstants.LEFT);
		toUnitLabel.setForeground(Color.WHITE);
		add(toUnitLabel, gbc);
		
		gbc.gridx = 1;
		toUnitComboBox = new JComboBox<>(DISTANCE_UNITS);
		toUnitComboBox.setBackground(Color.DARK_GRAY);
		toUnitComboBox.setForeground(Color.WHITE);
		add(toUnitComboBox, gbc);
		
		gbc.gridx = 0;
		gbc.gridy++;
		JLabel inputLabel = new JLabel("Input Value:", SwingConstants.LEFT);
		inputLabel.setForeground(Color.WHITE);
		add(inputLabel, gbc);
		
		gbc.gridx = 1;
		inputField = new JTextField();
		inputField.setBackground(Color.DARK_GRAY);
		inputField.setForeground(Color.WHITE);
		add(inputField, gbc);
		
		gbc.gridx = 0;
		gbc.gridy++;
		JLabel outputLabel = new JLabel("Converted Value:", SwingConstants.LEFT);
		outputLabel.setForeground(Color.WHITE);
		add(outputLabel, gbc);
		
		gbc.gridx = 1;
		outputField = new JTextField();
		outputField.setEditable(false);
		outputField.setBackground(Color.DARK_GRAY);
		outputField.setForeground(Color.WHITE);
		add(outputField, gbc);
		
		gbc.gridx = 0;
		gbc.gridy++;
		gbc.gridwidth = 2;
		JButton convertButton = new JButton("Convert");
		convertButton.setBackground(Color.DARK_GRAY);
		convertButton.setForeground(Color.WHITE);
		convertButton.addActionListener(new ConvertButtonListener());
		add(convertButton, gbc);
		
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
