package Calculate;

import java.util.Stack;

/**
 * @author Ginger
 * @date 2021/11/26
 */
public class Calculate {
    public static String calculate(String formula) {
        Stack<Double> numStack= new Stack<>();
        Stack<Character> operateStack= new Stack<>();

        if(formula.charAt(0) == '-'){
            numStack.add(0.00);
        }
        //定义相关变量
        /**
         * list 用于扫描
         * num1 出栈数1
         * num2 出栈数2
         * operate 操作符
         * result 运算结果
         * ch 将每次扫描得到的char保存到ch
         * readNum 用于多位数拼接
         */
        int list=0;
        double num1;
        double num2;
        int operate;
        double result;
        char ch;
        String readNum = "";
        //循环扫描算数式
        try {
            while (list < formula.length()) {
                //依次得到算术式的每一个字符
                ch = formula.substring(list, list + 1).charAt(0);
                //判断ch是什么，然后进行相应处理
                if (ifOperate(ch)) {
                    //判断当前的符号栈是否为空
                    if (!operateStack.empty()) {
                        //如果符号栈不为空，就比较优先级，如果当前操作符优先级<=栈中操作符
                        if (priority(ch) <= priority(operateStack.peek())) {
                            //从数栈出两个数，从符号栈出一个符号进行计算
                            num1 = numStack.pop();
                            num2 = numStack.pop();
                            operate = operateStack.pop();
                            result = calculate(num1, num2, operate);
                            //把运算结果入数栈
                            numStack.add(result);
                            //把当前操作符入符号栈
                        }
                    }
                    operateStack.add(ch);
                } else {
                    readNum += ch;
                    if (list == formula.length() - 1) {
                        numStack.add(Double.parseDouble(readNum));
                    } else {
                        //判断下一个字符是否是数字,是数字就继续扫描，运算符就入栈,看后面一位，不是list后移
                        if (ifOperate(formula.substring(list + 1, list + 2).charAt(0))) {
                            //后一位是运算符就入栈
                            numStack.add(Double.parseDouble(readNum));
                            //清空拼接
                            readNum = "";
                        }
                    }
                }
                //让字符+1，并判断算术式是否扫描到最后,就结束
                list++;

            }
            //当算数式扫描到最后就顺序从数栈和符号栈中出栈，并计算
            while (!operateStack.empty()) {
                //如果符号栈为空就得到最后结果,数栈中只有一个数字就是结果
                //否则就
                //从数栈出两个数，从符号栈出一个符号进行计算
                num1 = numStack.pop();
                num2 = numStack.pop();
                operate = operateStack.pop();
                result = calculate(num1, num2, operate);
                //把运算结果入数栈
                numStack.add(result);
            }
            //将数栈出栈就是结果
            System.out.printf("表达式%s=%.2f\n", formula, numStack.peek());
            return String.format("%.2f", numStack.pop());
        }
        catch (Exception e){
            return "Error";
        }
    }

    /**
     *
     * 返回运算符的优先级
     */
    public static int priority(int operate){
        if (operate=='*'||operate=='/'){
            return 1;
        }else if (operate=='+'||operate=='-'){
            return 0;
        }else {
            return -1;
        }
    }

    /**
     * 判断是不是一个运算符
     * @param val 操作符
     * @return 操作符为true,其他为false
     */
    public static boolean ifOperate(char val){
        return val=='+'||val=='-'||val=='*'||val=='/';
    }

    public static double calculate(double num1,double num2,int operate){
        double result=0;
        switch (operate){
            case '+':
                result=num1+num2;
                break;
            case '-':
                result=num2-num1;
                break;
            case '*':
                result=num1*num2;
                break;
            case '/':
                result=num2/num1;
            default:
                break;
        }
        return result;
    }
}
