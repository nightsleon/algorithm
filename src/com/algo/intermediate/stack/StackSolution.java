package com.algo.intermediate.stack;

import java.util.*;
/**
 * 难度级别: Intermediate
 * 分类: Stack
 * 
 * @author liangjun
 **/
public class StackSolution {
    private Map<Character, Character> map = new HashMap<>();
    private Deque<Character> dataStack = new LinkedList<>();

    public StackSolution() {
        map.put(')', '(');
        map.put(']', '[');
        map.put('}', '{');
    }

    public boolean isValid(String s) {
        for (char c : s.toCharArray()) {
            // 左括号压入栈
            if (!map.containsKey(c)) {
                dataStack.push(c);
            } else {
                // 右括号取出栈中数据对比，空 or 不想等则返回 flase
                if (dataStack.isEmpty() || dataStack.pop() != map.get(c)) return false;
            }
        }
        // 都遍历完了，栈非空返回 false
        return dataStack.isEmpty();
    }

    /*
    简化路径
     */
    public String simplifyPath(String path) {
        final String prePath = "..";
        final String currPath = ".";
        String[] paths = path.split("/+");
        Deque<String> stack = new LinkedList<>();
        for (String p : paths) {
            // 上级目录，去掉上层目录
            if (prePath.equals(p)) {
                if (!stack.isEmpty()) {
                    stack.pop();
                }
            } else if (!"".equals(p) && !currPath.equals(p)) {
                // 非空且不是当前目录(.) 入栈
                stack.push(p);
            }
        }
        StringBuilder sub = new StringBuilder();
        while (!stack.isEmpty()) {
            String s = stack.pollLast();
            sub.append("/");
            sub.append(s);
        }
        return sub.length() == 0 ? "/" : sub.toString();
    }

    /*
    最小栈
     */
    class MinStack {
        Deque<Integer> stack;
        Deque<Integer> minStack;

        public MinStack() {
            stack = new LinkedList<>();
            minStack = new LinkedList<>();
            minStack.push(Integer.MAX_VALUE);
        }

        public void push(int val) {
            stack.push(val);
            minStack.push(Math.min(minStack.peek(), val));
        }

        public void pop() {
            stack.pop();
            minStack.pop();
        }

        public int top() {
            return stack.peek();
        }

        public int getMin() {
            return minStack.peek();
        }
    }

    /*
    逆波兰表达式
     */
    public int evalRPN(String[] tokens) {
        final Set<String> operator = new HashSet<>();
        operator.add("+");
        operator.add("-");
        operator.add("*");
        operator.add("/");
        // 处理边界
        if (null == tokens || tokens.length == 0) {
            return 0;
        }
        Deque<Integer> stack = new LinkedList<>();
        for (String token : tokens) {
            // 处理操作符
            if (operator.contains(token)) {
                if (stack.isEmpty()) return 0;
                int second = stack.pop();
                int first = stack.pop();
                if ("+".equals(token)) {
                    stack.push(first + second);
                } else if ("-".equals(token)) {
                    stack.push(first - second);
                } else if ("*".equals(token)) {
                    stack.push(first * second);
                } else if ("/".equals(token)) {
                    stack.push(first / second);
                }
            } else {
                // 数字入栈
                stack.push(Integer.valueOf(token));
            }
        }
        return Integer.valueOf(stack.pop());
    }

    public static void main(String[] args) {
        String path = "/.../a/../b/c/../d/./";
        StackSolution solution = new StackSolution();
//        System.out.println(solution.simplifyPath(path));
        String[] tokens = {"10", "6", "9", "3", "+", "-11", "*", "/", "*", "17", "+", "5", "+"};
        System.out.println(solution.evalRPN(tokens));
    }

}
