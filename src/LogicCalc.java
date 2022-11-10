import java.text.DecimalFormat;
import java.util.Stack;

public class LogicCalc {

    public String useCalc(String expr) {
        try {
            checkError(expr);

            String prepared = preparingExpression(expr);
            String rpn = expressionToRpn(prepared);
            double result = rpnToResult(rpn);

            if (result == (int) result) {
                return (Integer.toString((int) result));
            } else {
                DecimalFormat decimalFormat = new DecimalFormat("#.#####");
                return decimalFormat.format(result);
            }
        } catch(Exception a) {
            return "Error";
        }
    }

    private String preparingExpression(String expr) {
        char symbolLast = expr.charAt(expr.length() - 1);
        if (symbolLast == '*' || symbolLast == '/') {
            expr += "1";
        } else if (symbolLast == '+' || symbolLast == '-') {
            expr += "0";
        }

        String preparedExpression = "";
        for (int i = 0; i < expr.length(); i++) {
            char symbol = expr.charAt(i);
            if (symbol == '-') {
                if (i == 0) {
                    preparedExpression += '0';
                } else if (expr.charAt(i - 1) == '(') {
                    preparedExpression += '0';
                }
            }
            preparedExpression += symbol;
        }
        return preparedExpression;
    }

    private String expressionToRpn(String expr) {
        StringBuilder current = new StringBuilder();
        Stack<Character> stack = new Stack<>();

        for (int i = 0; i < expr.length(); i++) {
            byte priority = getPriority(expr.charAt(i));
            char symbol = expr.charAt(i);

            switch (priority) {
                case -1:
                    current.append(" ");

                    while (getPriority(stack.peek()) != 1) {
                        current.append(stack.pop());
                    }
                    break;
                case 0:
                    current.append(symbol);
                    break;
                case 1:
                    stack.push(symbol);
                    break;
                case 2:
                case 3:
                    current.append(" ");

                    while (!stack.isEmpty()) {
                        if (getPriority(stack.peek()) >= priority) {
                            current.append(stack.pop());
                        } else {
                            break;
                        }
                    }
                    stack.push(symbol);
                    break;
            }
        }
        while (!stack.isEmpty()) {
            current.append(stack.pop());
        }
        return current.toString();
    }

    private double rpnToResult(String rpn) {
        String operand = "";
        Stack<Double> stack = new Stack<>();

        for (int i = 0; i <rpn.length(); i++) {
            if (rpn.charAt(i) == ' ') {
                continue;
            }
            if (getPriority(rpn.charAt(i)) == 0) {
                while (rpn.charAt(i) != ' ' && getPriority(rpn.charAt(i)) == 0) {
                    operand += rpn.charAt(i++);
                    if (i == rpn.length()) {
                        break;
                    }
                }
                stack.push(Double.parseDouble(operand));
                operand = "";
            }
            if (getPriority(rpn.charAt(i)) > 1) {
                double a = stack.pop();
                double b = stack.pop();

                switch (rpn.charAt(i)) {
                    case '+':
                        stack.push(b + a);
                        break;
                    case '-':
                        stack.push(b - a);
                        break;
                    case '*':
                        stack.push(b * a);
                        break;
                    case '/':
                        stack.push(b / a);
                        break;
                }
            }
        }
        return stack.pop();
    }

    private byte getPriority (char token){
        switch (token) {
            case '*':
            case '/':
                return 3;
            case '+':
            case '-':
                return 2;
            case '(':
                return 1;
            case ')':
                return -1;
            default:
                return 0;
        }
    }

    private void checkError(String expr) throws Exception {
        for (int i = 1; i < expr.length() - 1; i++) {
            char symbol = expr.charAt(i);
            if (symbol == '(' && isNumber(expr.charAt(i - 1))) {
                throw new Exception();
            } else if (symbol == ')' && isNumber(expr.charAt(i + 1))) {
                throw new Exception();
            }
        }

        int countLeftBracket = 0;
        int countRightBracket = 0;
        for (int i = 0; i < expr.length(); i++) {
            char symbol = expr.charAt(i);
            if (symbol == '(') {
                countLeftBracket++;
            } else if (symbol == ')') {
                countRightBracket++;
            }
        }

        if (countLeftBracket != countRightBracket) {
            throw new Exception();
        }
    }

    private boolean isNumber(char s) {
        return s >= '0' && s <= '9';
    }
}
