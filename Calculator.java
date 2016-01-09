import java.util.Collections;
import java.util.Stack;

        /*This is a file which I copied from the network, 
        but I have remade it so that it can use values from Snake as well as calculate pow
        this class helps Snake to calculate.*/
public class Calculator {
    private Stack<String> postfixStack  = new Stack<String>();//后缀式栈
    private Stack<Character> opStack  = new Stack<Character>();//运算符栈
    private int [] operatPriority  = new int[] {0,3,2,1,-1,1,0,2};//运用运算符ASCII码-40做索引的运算符优先级
    public double calculate(String expression) throws NumberFormatException {
        Stack<String> resultStack  = new Stack<String>();
        prepare(expression);
        Collections.reverse(postfixStack);//将后缀式栈反转
        String firstValue  ,secondValue,currentValue;//参与计算的第一个值，第二个值和算术运算符
        while(!postfixStack.isEmpty()) {
            currentValue  = postfixStack.pop();
            if(!isOperator(currentValue.charAt(0))) {//如果不是运算符则存入操作数栈中
                resultStack.push(currentValue);
            } else {//如果是运算符则从操作数栈中取两个值和该数值一起参与运算
                 secondValue  = resultStack.pop();
                 firstValue  = resultStack.pop();
                 String tempResult  = calculate(firstValue, secondValue, currentValue.charAt(0));
                 resultStack.push(tempResult);
            }
        }
        return Double.valueOf(resultStack.pop());
    }
    
    /**
     * 数据准备阶段将表达式转换成为后缀式栈
     * @param expression
     */
    private void prepare(String expression) {
        opStack.push(',');//运算符放入栈底元素逗号，此符号优先级最低
        char[] arr  = expression.toCharArray();
        int currentIndex  = 0;//当前字符的位置
        int count = 0;//上次算术运算符到本次算术运算符的字符的长度便于或者之间的数值
        char currentOp  ,peekOp;//当前操作符和栈顶操作符
        for(int i=0;i<arr.length;i++) {
            currentOp = arr[i];
            if(isOperator(currentOp)) {//如果当前字符是运算符
                if(count > 0) {
                    postfixStack.push(new String(arr,currentIndex,count));//取两个运算符之间的数字
                }
                peekOp = opStack.peek();
                if(currentOp == ')') {//遇到反括号则将运算符栈中的元素移除到后缀式栈中直到遇到左括号
                    while(opStack.peek() != '(') {
                        postfixStack.push(String.valueOf(opStack.pop()));
                    }
                    opStack.pop();
                } else {
                    while(currentOp != '(' && peekOp != ',' && compare(currentOp,peekOp) ) {
                        postfixStack.push(String.valueOf(opStack.pop()));
                        peekOp = opStack.peek();
                    }
                    opStack.push(currentOp);
                }
                count = 0;
                currentIndex = i+1;
            } else {
                count++;
            }
        }
        if(count > 1 || (count == 1 && !isOperator(arr[currentIndex]))) {//最后一个字符不是括号或者其他运算符的则加入后缀式栈中
            postfixStack.push(new String(arr,currentIndex,count));
        } 
        
        while(opStack.peek() != ',') {
            postfixStack.push(String.valueOf( opStack.pop()));//将操作符栈中的剩余的元素添加到后缀式栈中
        }
    }
    private boolean isOperator(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/' || c == '(' ||c == ')'|| c=='^';
    }
    public  boolean compare(char cur,char peek) {//Compare the priority
        boolean result  = false;
        if(operatPriority[(peek)-40] >= operatPriority[(cur) - 40]) {
           result = true;
        }
        return result;
    }
    private String calculate(String firstValue,String secondValue,char currentOp) {
        String result  = "";
        switch(currentOp) {
            case '+':
                result = String.valueOf(ArithHelper.add(firstValue, secondValue));
                break;
            case '-':
                result = String.valueOf(ArithHelper.sub(firstValue, secondValue));
                break;
            case '*':
                result = String.valueOf(ArithHelper.mul(firstValue, secondValue));
                break;
            case '/':
                result = String.valueOf(ArithHelper.div(firstValue, secondValue));
                break;
            case '^':
            	result = String.valueOf(ArithHelper.ChengFang(firstValue, secondValue));
        }
        return result;
    }
}
class ArithHelper {
    private ArithHelper() {
    }
    public static double add(String v1, String v2) {
    	double re=0;
    	double d1=0;
    	double d2=0;
    	try{
    		d1=new Double(v1);
    	}catch(NumberFormatException e){
    		d1=new Double(Snake.obj.get(v1));
    	}
    	try{
    		d2=new Double(v2);
    	}catch(NumberFormatException e){
    		d2=new Double(Snake.obj.get(v2));
    	}
    	re=d1+d2;
		return re;
    }
    public static double sub(String v1, String v2) {
    	double re=0;
    	double d1=0;
    	double d2=0;
    	try{
    		d1=new Double(v1);
    	}catch(NumberFormatException e){
    		d1=new Double(Snake.obj.get(v1));
    	}
    	try{
    		d2=new Double(v2);
    	}catch(NumberFormatException e){
    		d2=new Double(Snake.obj.get(v2));
    	}
    	re=d1-d2;
		return re;
    }
    public static double mul(String v1, String v2) {
    	double re=0;
    	double d1=0;
    	double d2=0;
    	try{
    		d1=new Double(v1);
    	}catch(NumberFormatException e){
    		d1=new Double(Snake.obj.get(v1));
    	}
    	try{
    		d2=new Double(v2);
    	}catch(NumberFormatException e){
    		d2=new Double(Snake.obj.get(v2));
    	}
    	re=d1*d2;
		return re;
    }
    public static double div(String v1, String v2) {
    	double re=0;
    	double d1=0;
    	double d2=0;
    	try{
    		d1=new Double(v1);
    	}catch(NumberFormatException e){
    		d1=new Double(Snake.obj.get(v1));
    	}
    	try{
    		d2=new Double(v2);
    	}catch(NumberFormatException e){
    		d2=new Double(Snake.obj.get(v2));
    	}
    	re=d1/d2;
		return re;
    }
    public static double ChengFang(String v1, String v2) {
    	double re=0;
    	double d1=0;
    	double d2=0;
    	try{
    		d1=new Double(v1);
    	}catch(NumberFormatException e){
    		d1=new Double(Snake.obj.get(v1));
    	}
    	try{
    		d2=new Double(v2);
    	}catch(NumberFormatException e){
    		d2=new Double(Snake.obj.get(v2));
    	}
    	re=Math.pow(d1,d2);
    	return re;
    }
}
