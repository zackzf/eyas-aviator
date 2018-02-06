package com.fbart.research.aviator.core;

import com.googlecode.aviator.AviatorEvaluator;
import com.googlecode.aviator.Expression;
import org.junit.Test;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

public class AviatorTest {
    @Test
    public void test01() {
        Long result = (Long) AviatorEvaluator.execute("1+2+3");
        System.out.println(result);
    }

    @Test
    public void test02() {
        Map<String, Object> env = new HashMap<>();
        env.put("yourname", "Tom");
        String result = (String) AviatorEvaluator.execute("'Hello,'+yourname", env);
        System.out.println(result);
    }

    @Test
    public void test03() {
        String result;
        result = (String) AviatorEvaluator.execute(" 'a\"b' "); //字符串 a"b
        result = (String) AviatorEvaluator.execute(" \"a\'b\" "); //字符串 a'b
        result = (String) AviatorEvaluator.execute(" 'hello'+3 "); //字符串 hello3
        result = (String) AviatorEvaluator.execute(" 'hello'+ unknow "); //字符串 hellonull

        System.out.println(result);
    }

    /**
     * Aviator2.2新增方法，更方便（按顺序赋值）
     */
    @Test
    public void test04() {
        String name = "Tom";
        String word = "Welcome";
        String result = (String) AviatorEvaluator.exec("'Hello,'+yourname+','+word", name, word);
        System.out.println(result);
    }

    /**
     * 内置函数
     */
    @Test
    public void test05() {
        Map<String, Object> env = new HashMap<>();
        env.put("yourname", "Rachel");
        Long result = (Long) AviatorEvaluator.execute("string.length(yourname)", env);
        System.out.println(result);

        String n = "Kk";
        Long result2 = (Long) AviatorEvaluator.exec("string.length(name)", n);
        System.out.println(result2);

        Boolean result3 = (Boolean) AviatorEvaluator.execute("string.contains(\"test\",string.substring('hello',1,2))");
        System.out.println(result3);

    }

    /**
     * 自定义函数
     */
    @Test
    public void test06() {
        AviatorEvaluator.addFunction(new AddFunction());
        System.out.println(AviatorEvaluator.execute("add(1,2)"));
        System.out.println(AviatorEvaluator.execute("add(add(2,3),6)"));
    }

    @Test
    public void test07() {
        String expression = "a-(b-c)>100";
        Expression compiledExpression = AviatorEvaluator.compile(expression);
        Map<String, Object> env = new HashMap<>();
        env.put("a", 100.02);
        env.put("b", 5.21);
        env.put("c", 5.20);

        Boolean result = (Boolean) compiledExpression.execute(env);
        System.out.println(result);


        //--------

        String expression2 = "a-(b-c)";
        Expression compiledExpression2 = AviatorEvaluator.compile(expression2);

        Double result2 = (Double) compiledExpression2.execute(env);
        System.out.println(result2);


        result2 = 2111111122.020d;
        DecimalFormat format = new DecimalFormat("0.000");
        String format1 = format.format(result2);
        System.out.println(format1);

    }


}
