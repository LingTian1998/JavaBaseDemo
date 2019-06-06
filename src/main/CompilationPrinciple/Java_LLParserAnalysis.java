package main.CompilationPrinciple;

import java.util.*;
import java.util.regex.Pattern;

@SuppressWarnings("unchecked")
public class Java_LLParserAnalysis {
    private static ArrayList<String> inputList = new ArrayList<String>();
    private static LinkedList<Row> tokens = new LinkedList<Row>();
    private static ArrayList<String>[] principleLists = new ArrayList[29];
    private static LexAnalysis_LL lexAnalysisLL =new LexAnalysis_LL();
    private static HashMap<String,HashMap<String,Integer>> LL1Table = new HashMap<String, HashMap<String,Integer>>();
    private static TreeMap<Integer,String> errorList = new TreeMap<Integer, String>();
    private static ArrayList<String> terminator =new ArrayList<String>();
    private static ArrayList<OutputClass> outputList = new ArrayList<OutputClass>();
    private static ArrayList<String> tempList   = new ArrayList<String>();
    private static Stack<OutputClass> recoverStack   = new Stack<OutputClass>();
    private static HashMap<String,String> blankList = new HashMap<String, String>();

    /**
     *初始化终结符
     */
    static {
        terminator.add("{");
        terminator.add("}");
        terminator.add("if");
        terminator.add("(");
        terminator.add(")");
        terminator.add("then");
        terminator.add("else");
        terminator.add("while");
        terminator.add("ID");
        terminator.add("=");
        terminator.add(";");
        terminator.add("<");
        terminator.add(">");
        terminator.add("<=");
        terminator.add(">=");
        terminator.add("==");
        terminator.add("+");
        terminator.add("-");
        terminator.add("*");
        terminator.add("/");
        terminator.add("NUM");
        terminator.add("$");

    }
    /**
     * 初始化LL(1)的文法
     */
    static{
        principleLists[1] = lexAnalysisLL.Result_LexAnalysis_forString("compoundstmt");
        principleLists[2] = lexAnalysisLL.Result_LexAnalysis_forString("ifstmt");
        principleLists[3] = lexAnalysisLL.Result_LexAnalysis_forString("whilestmt");
        principleLists[4] = lexAnalysisLL.Result_LexAnalysis_forString("assgstmt");
        principleLists[5] = lexAnalysisLL.Result_LexAnalysis_forString("compoundstmt");
        principleLists[6] = lexAnalysisLL.Result_LexAnalysis_forString("{ stmts }");
        principleLists[7] = lexAnalysisLL.Result_LexAnalysis_forString("stmt stmts");
        principleLists[8] = lexAnalysisLL.Result_LexAnalysis_forString("E");
        principleLists[9] = lexAnalysisLL.Result_LexAnalysis_forString("if ( boolexpr ) then stmt else stmt");
        principleLists[10] = lexAnalysisLL.Result_LexAnalysis_forString("while ( boolexpr ) stmt");
        principleLists[11] = lexAnalysisLL.Result_LexAnalysis_forString("ID = arithexpr ;");
        principleLists[12] = lexAnalysisLL.Result_LexAnalysis_forString("arithexpr boolop arithexpr");
        principleLists[13] = lexAnalysisLL.Result_LexAnalysis_forString("<");
        principleLists[14] = lexAnalysisLL.Result_LexAnalysis_forString(">");
        principleLists[15] = lexAnalysisLL.Result_LexAnalysis_forString("<=");
        principleLists[16] = lexAnalysisLL.Result_LexAnalysis_forString(">=");
        principleLists[17] = lexAnalysisLL.Result_LexAnalysis_forString("==");
        principleLists[18] = lexAnalysisLL.Result_LexAnalysis_forString("multexpr arithexprprime");
        principleLists[19] = lexAnalysisLL.Result_LexAnalysis_forString("+ multexpr arithexprprime");
        principleLists[20] = lexAnalysisLL.Result_LexAnalysis_forString("- multexpr arithexprprime");
        principleLists[21] = lexAnalysisLL.Result_LexAnalysis_forString("E");
        principleLists[22] = lexAnalysisLL.Result_LexAnalysis_forString("simpleexpr  multexprprime");
        principleLists[23] = lexAnalysisLL.Result_LexAnalysis_forString("* simpleexpr multexprprime");
        principleLists[24] = lexAnalysisLL.Result_LexAnalysis_forString("/ simpleexpr multexprprime");
        principleLists[25] = lexAnalysisLL.Result_LexAnalysis_forString("E");
        principleLists[26] = lexAnalysisLL.Result_LexAnalysis_forString("ID");
        principleLists[27] = lexAnalysisLL.Result_LexAnalysis_forString("NUM");
        principleLists[28] = lexAnalysisLL.Result_LexAnalysis_forString("( arithexpr )");
    }

    /**
     * 返回一个Column的HashMap
     * @param raw
     * @param column
     * @param integer
     * @return
     */
    public static void ColumnFactory(String raw,String column,Integer integer){
        HashMap<String,Integer> hashMap = LL1Table.get(raw);
        if (hashMap==null){
            HashMap<String,Integer> hashMap1 = new HashMap<String, Integer>();
            hashMap1.put(column,integer);
            LL1Table.put(raw,hashMap1);
            return;
        }

        hashMap.put(column,integer);
    }
    /**
     * 初始化LL(1)分析表
     */
    static {
        ColumnFactory("program","{",1);
        ColumnFactory("stmt","{",5);
        ColumnFactory("stmt","if",2);
        ColumnFactory("stmt","while",3);
        ColumnFactory("stmt","ID",4);
        ColumnFactory("compoundstmt","{",6);
        ColumnFactory("stmts","{",7);
        ColumnFactory("stmts","}",8);
        ColumnFactory("stmts","if",7);
        ColumnFactory("stmts","while",7);
        ColumnFactory("stmts","ID",7);
        ColumnFactory("ifstmt","if",9);
        ColumnFactory("whilestmt","while",10);
        ColumnFactory("assgstmt","ID",11);
        ColumnFactory("boolexpr","(",12);
        ColumnFactory("boolexpr","ID",12);
        ColumnFactory("boolexpr","NUM",12);
        ColumnFactory("boolop","<",13);
        ColumnFactory("boolop",">",14);
        ColumnFactory("boolop","<=",15);
        ColumnFactory("boolop",">=",16);
        ColumnFactory("boolop","==",17);
        ColumnFactory("arithexpr","(",18);
        ColumnFactory("arithexpr","ID",18);
        ColumnFactory("arithexpr","NUM",18);
        ColumnFactory("arithexprprime",")",21);
        ColumnFactory("arithexprprime",";",21);
        ColumnFactory("arithexprprime","<",21);
        ColumnFactory("arithexprprime",">",21);
        ColumnFactory("arithexprprime","<=",21);
        ColumnFactory("arithexprprime",">=",21);
        ColumnFactory("arithexprprime","==",21);
        ColumnFactory("arithexprprime","+",19);
        ColumnFactory("arithexprprime","-",20);
        ColumnFactory("multexpr","(",22);
        ColumnFactory("multexpr","ID",22);
        ColumnFactory("multexpr","NUM",22);
        ColumnFactory("multexprprime",")",25);
        ColumnFactory("multexprprime",";",25);
        ColumnFactory("multexprprime","<",25);
        ColumnFactory("multexprprime",">",25);
        ColumnFactory("multexprprime","<=",25);
        ColumnFactory("multexprprime",">=",25);
        ColumnFactory("multexprprime","==",25);
        ColumnFactory("multexprprime","+",25);
        ColumnFactory("multexprprime","-",25);
        ColumnFactory("multexprprime","*",23);
        ColumnFactory("multexprprime","/",24);
        ColumnFactory("simpleexpr","(",28);
        ColumnFactory("simpleexpr","ID",26);
        ColumnFactory("simpleexpr","NUM",27);
    }

    /**
     *  this method is to read the standard input
     */
    private static void read_prog()
    {
        Scanner sc = new Scanner(System.in);
        while(sc.hasNextLine())
        {
            inputList.add(sc.nextLine());
        }
        tokens.clear();
        tokens = lexAnalysisLL.Result_LexAnalysis_forList(inputList);
    }
    @SuppressWarnings("unchecked")
    public static void main(String[] args){
        Analysis();
    }

    /**
     * LL(1)分析
     */
    @SuppressWarnings("unchecked")
    private static void Analysis(){
        read_prog();

        Stack<OutputClass> stack = new Stack<OutputClass>();
        stack.push(new OutputClass("$",-1));
        stack.push(new OutputClass("program",0));
        tokens.add(new Row(-1,"$"));
        int lastwordRow = -1;
        while (stack.size()!=0){
            if (stack.peek().string.equals(tokens.getFirst().string)){
                outputList.add(stack.pop());
                lastwordRow=tokens.removeFirst().row;
            }
            else if (isTem(stack.peek())){
                // TODO: 2019/5/11  加到错误列表
                String error = stack.peek().string;
                errorList.put(lastwordRow,error);
                // TODO: 2019/5/12  将恢复列表中的string加入到stack中
                while (!recoverStack.empty())
                    stack.push(recoverStack.pop());
                // TODO: 2019/5/12 将error加入到tokens的第一位
                tokens.addFirst(new Row(lastwordRow,error));
            }
            else {
                OutputClass rowOutputUnit = stack.pop();
                String rowString = rowOutputUnit.string;
                String columnString    = tokens.getFirst().string;
                if (LL1Table.get(rowString).get(columnString)!=null) {
                    outputList.add(rowOutputUnit);
                    int index = LL1Table.get(rowString).get(columnString);
                    tempList.clear();
                    tempList.addAll(principleLists[index]);
                    // TODO: 2019/5/11 倒置temList
                    Collections.reverse(tempList);
                    for (String string : tempList)
                        if (!string.equals("E"))
                            stack.push(new OutputClass(string,rowOutputUnit.treeLine+1));
                        else
                            outputList.add(new OutputClass(string,rowOutputUnit.treeLine+1));
                }
                else {
                    recoverStack.push(rowOutputUnit);
                }
            }
        }
        outputList.remove(outputList.size()-1);
        outputDeal();
        return;
    }

    public static void outputDeal(){
        for (Map.Entry<Integer,String> entry:errorList.entrySet()){
            System.out.println("语法错误,第"+entry.getKey()+"行,缺少\""+entry.getValue()+"\"");
        }
        String remain = "";
        int order =0;
        for (OutputClass s:outputList){
            order++;
            int num =s.treeLine;
            StringBuffer stringBuffer = new StringBuffer("");
            for (int i=0;i<num;i++){
                stringBuffer.append("\t");
            }
            if (order!=outputList.size())
                System.out.println(stringBuffer.toString()+s.string);
            else
                System.out.print(stringBuffer.toString()+s.string);
        }
    }
    /**
     * 判断是否是终结符
     * @param outputClass
     * @return
     */
    private static boolean isTem(OutputClass outputClass){
        if (terminator.contains(outputClass.string))
            return true;
        return false;
    }
}

/**
 * 词法分析类
 */
@SuppressWarnings("unchecked")
class LexAnalysis_LL {
    private static ArrayList<String> arrayList = new ArrayList<String>();
    private static ArrayList<String> resultList= new ArrayList<String>();
    private static LinkedList<Row> rowLLList = new LinkedList<Row>();
    private static int currentRow = -1;
    /**
     *  LexicalAnalysis
     */
    private static int status=0;
    private static int index =0;
    private static char[] array;
    private static ArrayList<Character> bufferList = new ArrayList<Character>();
    private static final int END_STATE1 = 99998;
    private static final int END_STATE2 = 99999;

    private static String[] entryKey ={"struct","||","<<","<=","auto","extern","do","float","while","continue","else","if","case","^=","--","==","!","\"","static","void","sizeof","%","double","&","(","signed",")","*","/*注释*/","+",",","typedef","-","enum",".","/","-=","->","常数","%=",":",";","!=","<","<<=","=",">","?",">=","&&",">>","|=","const","for","long","switch","default","goto","*=","&=","[","]","^","++","//注释","break","volatile","union","int","/=","标识符","+=","char","short","unsigned","{","|","}",">>=","return","~","register","then"};
    private static String[] entryValue={"25","61","69","71","1","12","8","13","32","6","10","16","3","58","34","73","37","78","24","30","23","39","9","41","44","22","45","46","79","65","48","27","33","11","49","50","35","36","80","40","52","53","38","68","70","72","74","54","75","42","76","62","5","14","18","26","7","15","47","43","55","56","57","66","79","2","31","28","17","51","81","67","4","21","29","59","60","63","77","20","64","19","82"};
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
    private static void analysis(int row,String string)
    {
        currentRow = row;
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
        if (Pattern.matches(pattern,string)){
            // TODO: 2019/5/1
        }
        else if (dightFlag){
            rowLLList.add(new Row(currentRow,string));
            resultList.add(string);
            string="<"+string+","+80+">";
            output(string);
            dightFlag=false;
        }
        else if (commentFlag){
            rowLLList.add(new Row(currentRow,string));
            resultList.add(string);
            string="<"+string+","+79+">";
            output(string);
            commentFlag=false;
        }
        else {
            if (keyList.contains(string)){
                rowLLList.add(new Row(currentRow,string));
                resultList.add(string);
                string= "<"+string+","+hashMap.get(string)+">";
                output(string);
            }
            else{
                rowLLList.add(new Row(currentRow,string));
                resultList.add(string);
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
            //System.out.print(order+": "+string);
            order++;
            firstLine=false;
        }
        else{
            //System.out.print("\n"+order+": "+string);
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

    public static void initVar(){
        arrayList.clear();
        resultList.clear();
        bufferList.clear();
        rowLLList.clear();
        currentRow=-1;
        dightFlag= false;
        commentFlag =false;
        index=0;
        status=0;
    }
    public  LinkedList<Row> Result_LexAnalysis_forList(ArrayList list) {
        initVar();
        arrayList.addAll(list);
        int row=1;
        for (String string:arrayList){
            analysis(row,string+" ");
            if (status==99){
                status=END_STATE1;
                commentFlag=true;
            }
            index=0;
            row++;
        }
        return rowLLList;
    }

    public  ArrayList<String> Result_LexAnalysis_forString(String string) {
        initVar();
        analysis(-1,string+" "); //末尾加空格防止分析结束时还未到终止状态
        if (status==99){
            status=END_STATE1;
            commentFlag=true;
        }
        index=0;
        //新建一个returnList不与resultList指向同一片内存； 并返回该引用
        ArrayList<String> returnList  = new ArrayList();
        returnList.addAll(resultList);
        return returnList;
    }
}
/**
 * 用于记录字符串在输入中的行数
 */
class Row {
    int row;
    String string;
    public Row(int integer, String string){
        this.string = string;
        this.row = integer;
    }
}

/**
 * 用于记录字符串在树中的层树
 */
class OutputClass{
    String string;
    int treeLine;
    public OutputClass(String string,int treeLine){
        this.string=string;
        this.treeLine=treeLine;
    }
}