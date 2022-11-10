import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Panel {
    JFrame frame;
    JPanel panel;
    JTextField display;
    String[] buttonsOnPanel = {
            "(", ")", "C", "CE",
            "7", "8", "9", "*",
            "4", "5", "6", "/",
            "1", "2", "3", "+",
            ".", "0", "=", "-"
    };
    JButton[] buttons = new JButton[buttonsOnPanel.length];
    Font myFont = new Font("Times New Roman", Font.BOLD, 25);
    LogicCalc calc = new LogicCalc();

    public void startPanel() {
        frame = new JFrame("Calculator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 520);

        display = new JTextField("0");
        display.setFont(new Font("Times New Roman", Font.BOLD, 75));
        display.setEditable(false);

        panel = new JPanel();
        panel.setLayout(new GridLayout(5, 4, 5, 5));

        for (int i = 0; i < buttonsOnPanel.length; i++) {
            buttons[i] = new JButton(buttonsOnPanel[i]);
            buttons[i].setFont(myFont);
            buttons[i].setFocusable(false);
            panel.add(buttons[i]);
        }

        buttons[2].addActionListener(new LetterButton());
        buttons[3].addActionListener(new LetterButton());
        buttons[18].addActionListener(new EqualityButton());
        buttons[0].addActionListener(new SignsButton());
        buttons[1].addActionListener(new SignsButton());
        buttons[7].addActionListener(new SignsButton());
        buttons[11].addActionListener(new SignsButton());
        buttons[15].addActionListener(new SignsButton());
        buttons[19].addActionListener(new SignsButton());
        buttons[16].addActionListener(new DotButton());
        buttons[4].addActionListener(new NumberButton());
        buttons[5].addActionListener(new NumberButton());
        buttons[6].addActionListener(new NumberButton());
        buttons[8].addActionListener(new NumberButton());
        buttons[9].addActionListener(new NumberButton());
        buttons[10].addActionListener(new NumberButton());
        buttons[12].addActionListener(new NumberButton());
        buttons[13].addActionListener(new NumberButton());
        buttons[14].addActionListener(new NumberButton());
        buttons[17].addActionListener(new NumberButton());

        frame.getContentPane().add(BorderLayout.NORTH, display);
        frame.getContentPane().add(BorderLayout.CENTER, panel);
        frame.setVisible(true);
    }

    class LetterButton implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            checkError();
            String symbol = e.getActionCommand();
            String text = display.getText();

            if (symbol.equals("CE")) {
                if (text.length() < 2) {
                    display.setText("0");
                } else {
                    String str = text.substring(0, text.length() - 1);
                    display.setText(str);
                }
            } else {
                display.setText("0");
            }
        }
    }

    class EqualityButton implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            checkError();
            String text = display.getText();
            String result = calc.useCalc(text);
            display.setText(result);
        }
    }

    class SignsButton implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            checkError();
            String symbol = e.getActionCommand();
            String text = display.getText();

            if (isNumber(text.charAt(text.length() - 1)) ||text.charAt(text.length() - 1) == '.') {
                display.setText(text + symbol);
            } else {
                String str = text.substring(0, text.length() - 1);
                display.setText(str + symbol);
            }
        }
    }

    class DotButton implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            checkError();
            String symbol = e.getActionCommand();
            String text = display.getText();

            if (isNumber(text.charAt(text.length() - 1))) {
                display.setText(text + symbol);
            } else {
                display.setText(text + "0" + symbol);
            }
        }
    }

    class NumberButton implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            checkError();
            String symbol = e.getActionCommand();
            String text = display.getText();

            if (text.charAt(0) == '0' && text.length() == 1) {
                display.setText(symbol);
            } else {
                display.setText(text + symbol);
            }
        }
    }

    private boolean isNumber(char s) {
        return s >= '0' && s <= '9';
    }

    private void checkError() {
        if (display.getText().equals("Error")) {

            display.setText("0");
        }
    }
}
