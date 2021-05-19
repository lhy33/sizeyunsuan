package sizeyunsuan;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

public class MathAlgorithm {
	public static void main(String[] args) throws ScriptException {
	    List<String> list = Arrays.asList("+", "-", "*", "/", "(", ")");
	    // ����һ���ַ��������������׼���������㣻
	    // ���ʹ�ò����ķ�ʽ����������У����������ĺϷ���
	    String exp = "(((125.78+123.0/147.1*12.38+134-345+897-1000+245.67-1238)/10.1*12.37 + 51*81 - 21.39+100-23.56)/10 +100)*100 -123.56";
	    // ����һ��ջ        
	    Stack<String> stack = new Stack<String>();
	    // ��һ��ȥ�����ţ����ȼ������������
	    for(int i = 0; i<exp.length(); i++){
	        String character = String.valueOf(exp.charAt(i));
	        if (character.equals(" ")){
	            continue;
	        }
	        if (character.equals(")")){
	            // ������������ţ���ջ�е�����һ��һ��ȡ������������һ��ջ�У�ֱ������������
	            Stack<String> sb = new Stack();
	            while (!stack.isEmpty() && !stack.peek().equals("(")){
	                sb.push(stack.pop());
	            }
	            stack.pop();// ɾ��ջ�е�������
	            stack.push(calculate(sb));// ��������������ݼ���֮�����·���ջ��

	        }else {
	            // û������������
	            if (stack.isEmpty()){
	                stack.push(character);
	                continue;
	            }
	            if (list.contains(character)){
	                stack.push(character); // �������������������ţ�ֱ����ջ
	            }else {
	                // ��������ֻ���С���㣬��Ҫ��֮ǰ��ջ������ƴ��
	                if (list.contains(stack.peek())){
	                    stack.push(character); // �����һ����ջ�Ĳ������ֻ���С���㣬ֱ����ջ
	                }else { 
	                    // �����һ����ջ�������ֻ���С���㣬�ȳ�ջ����ƴ�ӣ������ջ
	                    StringBuilder sb = new StringBuilder();
	                    sb.append(stack.pop());
	                    sb.append(character);
	                    stack.push(sb.toString());
	                }
	            }
	        }
	    }
	    // �������Ľ��
	    if (stack.size() == 1){// ��������������ģ����ջ��ֻʣ��һ��Ԫ��
	        System.out.println(stack.pop());
	    }else {// ��������������ģ�ջ�л��ж��Ԫ�أ���������
	        // ���Ǵ�ʱջ�е�Ԫ�����������еģ���Ҫ��ɵ����ټ���
	        Stack<String> last = new Stack<String>();
	        while (!stack.isEmpty()){
	            last.push(stack.pop());
	        }
	        System.out.println(calculate(last));
	    }
	    // jdk�Դ��ķ������㣬���ڱȽϽ��
	    ScriptEngine se = new ScriptEngineManager().getEngineByName("JavaScript");
	    System.out.println(se.eval(exp));
	}
	// ���㲻�����ŵ��ַ��������ǲ���ջ�е����� �ǵ���ģ���Ϊջ��ԭ�����Ƚ����
	private static String calculate(Stack<String> exp) {
	    ArrayList<String> arrayList = new ArrayList<String>();
	    // ����˳���
	    while (!exp.isEmpty()){
	        if ("*".equals(exp.peek()) || "/".equals(exp.peek())){
	            String fuhao = exp.pop();
	            String number = exp.pop();
	            String last = arrayList.remove(arrayList.size()-1);
	            if (fuhao.equals("*")){
	                arrayList.add(new BigDecimal(last).multiply(new BigDecimal(number)).toString());
	            }else {
	                arrayList.add(new BigDecimal(last).divide(new BigDecimal(number), 12, BigDecimal.ROUND_HALF_EVEN).toString());
	            }
	        }else {
	            arrayList.add(exp.pop());
	        }
	    }
	    // ����Ӽ���
	    BigDecimal b = new BigDecimal(arrayList.remove(0));
	    Iterator<String> iterator = arrayList.iterator();
	    while (iterator.hasNext()){
	        String next = iterator.next();
	        if (next.equals("+")){
	            b = b.add(new BigDecimal(iterator.next()));
	        }else if (next.equals("-")){
	            b = b.subtract(new BigDecimal(iterator.next()));
	        }
	    }
	    return b.toString();
	}

}
