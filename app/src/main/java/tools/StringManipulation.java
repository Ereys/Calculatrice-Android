package tools;

public class StringManipulation {
    public static String removeLastChar(String s){
        System.out.println("remove");
        if(!(s.length() > 1)){
            return "0";
        }
        return s.substring(0, s.length()-1);
    }

    public static String invertString(String s){
        double a = Double.parseDouble(s);
        if(a > 0){
            a = a*(-1);
        }else{
            a = Math.abs(a);
        }
        return Double.toString(a);
    }
}