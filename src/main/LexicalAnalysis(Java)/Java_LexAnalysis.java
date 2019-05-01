import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Java_LexAnalysis {
    private static StringBuffer prog = new StringBuffer();
    private static ArrayList<String> arrayList = new ArrayList<String>();

    /**
     *  this method is to read the standard input
     */
    private static void read_prog()
    {
        Scanner sc = new Scanner(System.in);
        while(sc.hasNextLine())
        {
            arrayList.add(sc.nextLine());
        }
    }

    /**
     * 判断字符a是否是26个字母大小写中的一个
     * @param a
     * @return
     */
    public static boolean isLetter(char a){
        if (a>='A'&&a<='Z'||a>='a'&&a<='z')
            return true;
        return false;
    }

    /**
     * 判断字符a是否是数字
     * @param a
     * @return
     */
    public static boolean isDigit(char a){
        if (a>='0'&&a<='9')
            return true;
        return false;
    }

    /**
     * 判断字符a是否是空格
     */
    public static boolean isBlank(char a){
        if (a==' ')
            return true;
        return false;
    }


    /**
     *  LexicalAnalysis
     */
    private static int status=0;
    private static int index =0;
    private static char[] array;
    private static ArrayList<Character> bufferList = new ArrayList<Character>();
    private static final int END_STATE1 = 99998;
    private static final int END_STATE2 = 99999;

    private static String[] entryKey ={"struct","||","<<","<=","auto","extern","do","float","while","continue","else","if","case","^=","--","==","!","\"","static","void","sizeof","%","double","&","(","signed",")","*","/*注释*/","+",",","typedef","-","enum",".","/","-=","->","常数","%=",":",";","!=","<","<<=","=",">","?",">=","&&",">>","|=","const","for","long","switch","default","goto","*=","&=","[","]","^","++","//注释","break","volatile","union","int","/=","标识符","+=","char","short","unsigned","{","|","}",">>=","return","~","register"};
    private static String[] entryValue={"25","61","69","71","1","12","8","13","32","6","10","16","3","58","34","73","37","78","24","30","23","39","9","41","44","22","45","46","79","65","48","27","33","11","49","50","35","36","80","40","52","53","38","68","70","72","74","54","75","42","76","62","5","14","18","26","7","15","47","43","55","56","57","66","79","2","31","28","17","51","81","67","4","21","29","59","60","63","77","20","64","19"};
    private static HashMap<String,String> hashMap =new HashMap<String, String>();
    private static ArrayList<String> keyList =new ArrayList<String>();
    private static boolean dightFlag= false;
    private static boolean commentFlag =false;
    static {
        for (String s: entryKey)
            keyList.add(s);

        for (int i=0;i<entryKey.length;i++){
            hashMap.put(entryKey[i],entryValue[i]);
        }
    }
    private static void analysis(String string)
    {

        array = string.toCharArray();
        while (index!=array.length){
            char currentChar = array[index];
            if (status!=END_STATE2&&status!=END_STATE1)
                bufferList.add(currentChar);
            switch (status){
                case 0:
                    if (isLetter(currentChar))
                        status=10;
                    else if (isDigit(currentChar))
                        status=13;
                    else if (isBlank(currentChar))
                        status=23;
                    else if (currentChar=='-')
                        status=26;
                    else if (currentChar=='!')
                        status=32;
                    else if (currentChar=='%')
                        status=36;
                    else if (currentChar=='&')
                        status=40;
                    else if (currentChar=='*')
                        status=49;
                    else if (currentChar=='/')
                        status=57;
                    else if (currentChar=='^')
                        status=61;
                    else if (currentChar=='|')
                        status=65;
                    else if (currentChar=='+')
                        status=70;
                    else if (currentChar=='<')
                        status=75;
                    else if (currentChar=='=')
                        status=82;
                    else if (currentChar=='>')
                        status=86;
                    else
                        status=END_STATE1;
                    break;
                case 10:
                    if (isDigit(currentChar)||isLetter(currentChar))
                        status=10;
                    else
                        status=END_STATE2;
                    break;
                case 13:
                    if (isDigit(currentChar))
                        status=13;
                    else if (currentChar=='.')
                        status=14;
                    else if (currentChar=='E')
                        status=16;
                    else{
                        dightFlag=true;
                        status=END_STATE2;
                    }
                    break;
                case 14:
                    if (isDigit(currentChar))
                        status=15;
                    break;
                case 15:
                    if (isDigit(currentChar))
                        status=15;
                    else if (currentChar=='E')
                        status=16;
                    else{
                        dightFlag=true;
                        status=END_STATE2;
                    }
                    break;
                case 16:
                    if (currentChar=='+'||currentChar=='-')
                        status=17;
                    else if (isDigit(currentChar))
                        status=18;
                    break;
                case 17:
                    if (isDigit(currentChar)){
                        status=18;
                    }
                    break;
                case 18:
                    if (isDigit(currentChar))
                        status=18;
                    else{
                        dightFlag=true;
                        status=END_STATE2;
                    }
                    break;
                case 23:
                    if (isBlank(currentChar)){
                        status=23;
                    }else
                        status=END_STATE2;
                    break;
                case 26:
                    if (currentChar=='-')
                        status=END_STATE1;
                    else if (currentChar=='=')
                        status=END_STATE1;
                    else if (currentChar=='>')
                        status=END_STATE1;
                    else
                        status=END_STATE2;
                    break;
                case 32:
                    if (currentChar=='=')
                        status=END_STATE1;
                    else
                        status=END_STATE2;
                    break;
                case 36:
                    if (currentChar=='=')
                        status=END_STATE1;
                    else if (currentChar=='d')
                        status=END_STATE1;
                    else if (currentChar=='o')
                        status=END_STATE1;
                    else if (currentChar=='x')
                        status=END_STATE1;
                    else if (currentChar=='u')
                        status=END_STATE1;
                    else if (currentChar=='c')
                        status=END_STATE1;
                    else if (currentChar=='s')
                        status=END_STATE1;
                    else if (currentChar=='f')
                        status=END_STATE1;
                    else if (currentChar=='e')
                        status=END_STATE1;
                    else if (currentChar=='g')
                        status=END_STATE1;
                    else
                        status=END_STATE2;
                    break;
                case 40:
                    if (currentChar=='&')
                        status=END_STATE1;
                    else if (currentChar=='=')
                        status=END_STATE1;
                    else
                        status=END_STATE2;
                    break;
                case 49:
                    if (currentChar=='=')
                        status=END_STATE1;
                    else
                        status=END_STATE2;
                    break;
                case 57:
                    if (currentChar=='=')
                        status=END_STATE1;
                    else if (currentChar=='*')
                        status=94;
                    else if (currentChar=='/')
                        status=98;
                    else
                        status=END_STATE2;
                    break;
                case 61:
                    if (currentChar=='=')
                        status=END_STATE1;
                    else
                        status=END_STATE2;
                    break;
                case 65:
                    if (currentChar=='|')
                        status=END_STATE1;
                    else if (currentChar=='=')
                        status=END_STATE1;
                    else
                        status=END_STATE2;
                    break;
                case 70:
                    if (currentChar=='+'||currentChar=='=')
                        status=END_STATE1;
                    else
                        status=END_STATE2;
                    break;
                case 75:
                    if (currentChar=='<')
                        status=76;
                    else if (currentChar=='=')
                        status=END_STATE1;
                    else
                        status=END_STATE2;
                    break;
                case 76:
                    if (currentChar=='=')
                        status=END_STATE1;
                    else
                        status=END_STATE2;
                    break;
                case 82:
                    if (currentChar=='=')
                        status=END_STATE1;
                    else
                        status=END_STATE2;
                    break;
                case 86:
                    if (currentChar=='=')
                        status=END_STATE1;
                    else if (currentChar=='>')
                        status=88;
                    else
                        status=END_STATE2;
                    break;
                case 88:
                    if (currentChar=='=')
                        status=END_STATE1;
                    else
                        status=END_STATE2;
                    break;
                case 94:
                    status=95;
                    break;
                case 95:
                    if (currentChar=='*')
                        status=96;
                    else
                        status=95;
                    break;
                case 96:
                    if (currentChar=='/'){
                        commentFlag=true;
                        status=END_STATE1;
                    }
                    else
                        status=95;
                    break;
                case 98:
                    status=99;
                    break;
                case 99:
                    status=99;
                    break;
                case END_STATE1:
                    acceptType1();
                    break;
                case END_STATE2:
                    acceptType2();
                    break;
            }
            if (status!=END_STATE2&&status!=END_STATE1)
                nextChar();
        }
    }

    /**
     * 接受类型1直接接受
     */
    private static void acceptType1() {
        resetStatus();
        outPutDeal(bufferList);
    }

    /**
     * 接受类型2回退一个字符再接受
     */
    private static void acceptType2(){
        retract();
        resetStatus();
        bufferList.remove(bufferList.size()-1);
        outPutDeal(bufferList);
    }

    /**
     * 重置状态
     */
    private static void resetStatus() {
        status=0;
    }

    /**
     * 输出识别处理
     * @param bufferList
     */
    private static void outPutDeal(ArrayList<Character> bufferList) {
        if (bufferList.size()==0)
            return;

        StringBuffer stringBuffer = new StringBuffer("");
        for (Character character:bufferList)
            stringBuffer.append(character);

        String string = stringBuffer.toString();
        String pattern = "\\s+";
        String pattern1 = "([1-9]\\d*\\.?\\d*)|(0\\.\\d*[1-9])";
        if (Pattern.matches(pattern,string)){
            // TODO: 2019/5/1
        }
        else if (dightFlag){
            string="<"+string+","+80+">";
            output(string);
            dightFlag=false;
        }
        else if (commentFlag){
            string="<"+string+","+79+">";
            output(string);
            commentFlag=false;
        }
        else {
            if (keyList.contains(string)){
                string= "<"+string+","+hashMap.get(string)+">";
                output(string);
            }
            else{
                string="<"+string+","+81+">";
                output(string);
            }
        }
        bufferList.clear();
    }

    /**
     * 输出处理
     */
    private static boolean firstLine = true;
    private static int order =1;
    private static void output(String string) {
        if (firstLine){
            System.out.print(order+": "+string);
            order++;
            firstLine=false;
        }
        else{
            System.out.print("\n"+order+": "+string);
            order++;
        }
    }

    /**
     * 下个字符
     */
    private static void nextChar() {
        index++;
    }

    /**
     * 上个字符
     */
    private static void retract(){
        index--;
    }

    /**
     * this is the main method
     * @param args
     */
    public static void main(String[] args) {
        read_prog();
        for (String string:arrayList){
            analysis(string);
            if (status==99){
                status=END_STATE1;
                commentFlag=true;
            }
            index=0;
        }

    }
}
