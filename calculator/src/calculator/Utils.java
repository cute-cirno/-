package calculator;

public class Utils {                                //工具类

    public static String check(String str){         //是整数 ? 整数字符串 ： 原字符串
        if(str.equals("")){
            return str;
        }
        double a = Double.parseDouble(str);
        return isInt(a) ? String.valueOf((int)a) : str;
    }

    public static boolean isInt(double x){          //整数的判断
        return x == Math.floor(x);
    }


}
