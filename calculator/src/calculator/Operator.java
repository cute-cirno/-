package calculator;


public class Operator {                                             //加减乘除操作符的处理
    public static double add(double a,double b){
        return a+b;
    }
    public static double reduce(double a,double b){
        return a-b;
    }
    public static double multiply(double a,double b){return a*b;}
    public static double divide(double a,double b)throws Exception{
        if(b == 0){
            if(a>=0)
                throw new Exception("∞");
            if(a<0)
                throw new Exception("-∞");
        }
        return a/b;
    }
    public static double square(double x){
        return x*x;
    }
    public static double sqrt(double x){
        return Math.sqrt(x);
    }
    public static double calculate(String operator,double v1,double v2) throws Exception{
        switch (operator){
            case "+":return add(v1,v2);
            case "-":return reduce(v1,v2);
            case "×":return multiply(v1,v2);
            case "÷":return divide(v1,v2);
        }
        return 0;
    }
}
