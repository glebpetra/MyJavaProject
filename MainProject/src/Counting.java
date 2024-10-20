import java.util.Stack;

public class Counting {
 	public static String create_OPZ(String expression) {
	        StringBuilder output = new StringBuilder();
	        Stack<Character> operators = new Stack<Character>();

	        for (String token : expression.split(" ")) {
	            if (isNumeric(token)) {
	                output.append(token).append(" ");
	            } else if (isVariable(token)) {
	                output.append(token).append(" ");
	            } else if (token.equals("(")) {
	                operators.push('(');
	            } else if (token.equals(")")) {
	                while (!operators.isEmpty() && operators.peek() != '(') {
	                    output.append(operators.pop()).append(" ");
	                }
	                operators.pop();
	            } else {
	                while (!operators.isEmpty() && priority(token.charAt(0)) <= priority(operators.peek())) {
	                    output.append(operators.pop()).append(" ");
	                }
	                operators.push(token.charAt(0));
	            }
	        }

	        while (!operators.isEmpty()) {
	            output.append(operators.pop()).append(" ");
	        }
            System.out.println(output.toString());
	        return output.toString().trim();
	    }
	public static double calculate_OPZ(String opz) {
        Stack<Double> stack = new Stack<Double>();
        
        for (String token : opz.split(" ")) {
            if (isNumeric(token)) {
                stack.push(Double.parseDouble(token));
            } else if (isVariable(token)) {
                stack.push(Double.parseDouble(token));
            } else {
                double b = stack.pop();
                double a = stack.pop();
                switch (token.charAt(0)) {
                    case '+':
                        stack.push(a + b);
                        break;
                    case '-':
                        stack.push(a - b);
                        break;
                    case '*':
                        stack.push(a * b);
                        break;
                    case '/':
                        stack.push(a / b);
                        break;
                }
            }
        }

        return stack.pop();
    }
    public static boolean isNumeric(String str) {
        return str.matches("-?\\d+(\\.\\d+)?");
    }
    public static boolean isVariable(String str) {
        return str.matches("[a-zA-Z]+");
    }
    public static int priority(char op) {
        switch (op) {
            case '+':
            	return 1;
            case '-':
                return 1;
            case '*':
            	return 2;
            case '/':
                return 2;
            default:
                return 0;
        }
    }
}
