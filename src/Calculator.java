import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.math.RoundingMode;
import javax.swing.*;

public class Calculator extends JFrame {
    private JFrame frame = new JFrame("Калькулятор");
    private JTextField display = new JTextField();
    private double result = 0;
    private double expo = 0;
    private int a = 0;
    private String lastVal = "";
    private String lastValue = "=";
    private boolean start = true;

    public static void main(String[] args) {

        Calculator calculator = new Calculator();
        calculator.createFrame();
    }

    final public void createButton (String name, Container container, ActionListener listener, int r, int g, int b) {
        Button button = new Button(name, container, listener, r, g, b);
    }
    final public void createTextField () {
        Font bigFont = new Font("Mono821BT", Font.BOLD, 45);
        display.setFont(bigFont);
       // display.setBackground(new Color(98, 176, 206));
        display.setHorizontalAlignment(JTextField.RIGHT);
        display.setEnabled(false);
        display.setDocument(new JTextFieldLimits(15));
        display.setText("0");
    }

    public void createFrame() {
        JPanel panel1 = new JPanel();
        ActionListener value = new EnteredValue();
        ActionListener deleteValue = new DeleteValue();
        ActionListener functions = new ArithmeticFunctions();
        createTextField();

        GridLayout number1 = new GridLayout(5,4, 3, 3);
        panel1.setLayout(number1);

        createButton("C", panel1, deleteValue,43, 123, 155);
        createButton("^", panel1, functions, 43, 123, 155);
        createButton("sqrt", panel1, functions, 43, 123, 155);
        createButton("/", panel1, functions, 43, 123, 155);

        createButton("7", panel1, value, 27, 97, 125);
        createButton("8", panel1, value, 27, 97, 125);
        createButton("9", panel1, value, 27, 97, 125);
        createButton("*", panel1, functions, 43, 123, 155);

        createButton("4", panel1, value, 27, 97, 125);
        createButton("5", panel1, value, 27, 97, 125);
        createButton("6", panel1, value, 27, 97, 125);
        createButton("-", panel1, functions, 43, 123, 155);

        createButton("1", panel1, value, 27, 97, 125);
        createButton("2", panel1, value, 27, 97, 125);
        createButton("3", panel1, value, 27, 97, 125);
        createButton("+", panel1, functions, 43, 123, 155);

        createButton("+/-", panel1, functions, 27, 97, 125);
        createButton("0", panel1, value, 27, 97, 125);
        createButton(".", panel1, value, 27, 97, 125);
        createButton("=", panel1, functions, 45, 163, 219);
        panel1.setBackground(new Color(98, 176, 206));

        frame.getContentPane().setBackground(new java.awt.Color(98,176,206));
        frame.getContentPane().add(BorderLayout.NORTH, display);
        frame.getContentPane().add(BorderLayout.CENTER, panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400,600);
        frame.setVisible(true);

        pack();
    }

    public void calculate(Double value) {
        switch (lastValue) {
            case "+":
                result += value;
                break;
            case "-":
                result -= value;
                break;
            case "*":
                result *= value;
                break;
            case "/":
                result /= value;
                break;
            case "=":
                result = value;
                break;
            case "+/-":
                result = -value;
                break;
            case "sqrt":
                result = Math.sqrt(value);
                break;
            case "^":
                result = Math.pow(expo, value);
                break;
        }
        intToDouble(result);
    }
    public void intToDouble (Double value) {
        double b = value;
        int d = (int) b;
        double c = b - d;
        BigDecimal bigDecimal = new BigDecimal(Double.toString(value));
        bigDecimal = bigDecimal.setScale(5, RoundingMode.HALF_UP);
        if (c == 0) {
            display.setText(" " + d);
        } else display.setText(" " + bigDecimal.doubleValue());
    }

    public void modal (double a) {
        if (a > 0) result = -a;
        else result = -a;
    }

    private class EnteredValue implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent event) {
            String value = event.getActionCommand();
            if (start) {
                if (value.equals(".") && a == 0) {
                    display.setText(display.getText() + value);
                    ++a;
                }
                if (value.equals("0") && display.getText().equals("0")) {
                    display.setText(value);
                }
                display.setText(value);
            } else {
            if (value.equals(".") && a == 0) {
                display.setText(display.getText() + value);
                ++a;
            } else if (lastVal.equals("0") && !value.equals(".") && display.getText().equals("0"))
                display.setText(value);
           else if (value.equals(".") && a > 0) display.getText();
            else {
                display.setText(display.getText() + value);
            }
            }
            lastVal = value;
            start = false;
        }
    }
    class DeleteValue implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            display.setText("0");
            result = 0;
            start = true;
            a = 0;
        }
    }
    class ArithmeticFunctions implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent event) throws NumberFormatException {
            String functions = event.getActionCommand();
            if (start) {
                if ((functions.equals("+/-") || functions.equals("^") || functions.equals("/") ||
                functions.equals("*") || functions.equals("sqrt")) && display.getText().equals("0")) {
                    display.setText("0");
                    start = false;
                } else {
                    lastValue = functions;
                    if (result == 0) result = Double.parseDouble(display.getText());
                }
            } else {
                if ((lastValue.equals("+/-") || lastValue.equals("+") || lastValue.equals("-") || lastValue.equals("*") ||
                        lastValue.equals("/") || lastValue.equals("^") || lastValue.equals("sqrt")) && (functions.equals("+/-") || functions.equals("^") || functions.equals("/") ||
                        functions.equals("*") || functions.equals("sqrt")) && display.getText().equals("0")) {
                    display.setText("0");
                    start = false;
                }
                if (result == 0) result = Double.parseDouble(display.getText());
                if (functions.equals("^")) {
                    expo = Double.parseDouble(display.getText());
                    display.setText(display.getText());
                }
               if (lastValue.equals("^")) {
                    display.setText(display.getText());
                }
                if (functions.equals("sqrt")) lastValue = functions;
                if (display.getText().equals(" ")) {
                    display.setText("0");
                    start = false;
                } else {
                    calculate(Double.parseDouble(display.getText()));
                    lastValue = functions;
                    start = true;
                    a = 0;
                }
            }
        }
    }
}
