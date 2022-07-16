package com.stack;

import java.util.Stack;

class EvaluationOfExpression {

    public static boolean isValid(String str) {
        Stack<Character> stack = new Stack<>();
        for(int i = 0; i < str.length(); i++){
            char ch = str.charAt(i);
            if(isOpeningParentheses(ch)){
                stack.push(ch);
            }else if(isClosingParentheses(ch) && !stack.isEmpty()){
                char c = stack.peek();
                if((c == '{' && ch == '}') || (c == '[' && ch == ']') || (c == '(' && ch == ')')){
                    stack.pop();
                }else{
                    return false;
                }
            }
        }

        return stack.isEmpty();
    }

    public static String InfixToPostfix(String str){
        StringBuilder ans = new StringBuilder();
        Stack<Character> stack = new Stack<>();

        for (int i = 0; i < str.length(); i++) {
            char ch = str.charAt(i);

            if (isOperator(ch)){
                while (!stack.isEmpty() && !isOpeningParentheses(ch) && hasHigherPrecedence(stack.peek(), ch)){
                    ans.append(stack.pop());
                }
                stack.push(ch);
            }

            else if (isOpeningParentheses(ch)){
                stack.push(ch);
            }

            else if (isClosingParentheses(ch)){
                while (!stack.isEmpty() && !isOpeningParentheses(stack.peek())){
                    ans.append(stack.pop());
                }
                stack.pop();
            }
            else{
                ans.append(ch);
            }
        }

        while(!stack.isEmpty()){
            ans.append(stack.pop());
        }

        return ans.toString();
    }

    private static boolean isOpeningParentheses(char ch) {
        return ch == '[' || ch == '{' || ch == '(';
    }

    private static boolean isClosingParentheses(char ch){
        return ch == ']' || ch == '}' || ch == ')';
    }

    private static boolean hasHigherPrecedence(Character peek, char ch) {
        if (peek == '/' && ch == '*')
            return true;

        if (peek == '*' && ch == '+')
            return true;

        if (peek == '*' && ch == '-')
            return true;

        if (peek == '+' && ch == '-')
            return true;

        return false;
    }

    private static boolean isOperator(char ch) {
        return ch == '/' || ch == '*' || ch == '+' || ch == '-';
    }

    public static void main(String[] args) {
        boolean b = isValid("(])");
        System.out.println(b);

        String s = "((a+b)*c-d)*e";
        System.out.println(InfixToPostfix(s));
    }
}