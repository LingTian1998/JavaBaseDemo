package main.CompilationPrinciple;

import java.util.*;

public class Java_LRParserAnalysis {
    private static HashMap<Integer,Production> rules = new HashMap<Integer,Production>();
    private static HashMap<Integer, HashMap<String,Action>> LR1Table = new HashMap<Integer, HashMap<String, Action>>();
    private static LinkedList<TokenAndRow> tokens = new LinkedList<TokenAndRow>();
    /**
     * 初始化产生式
     */
    static {
        int index=1;
        intilize(index++,"program","compoundstmt");
        intilize(index++,"stmt", "ifstmt");
        intilize(index++,"stmt",  "whilestmt" );
        intilize(index++,"stmt", "assgstmt" );
        intilize(index++,"stmt", "compoundstmt");
        intilize(index++,"compoundstmt", "{", "stmts", "}");
        intilize(index++,"stmts", "stmt", "stmts");
        intilize(index++,"stmts", "E");
        intilize(index++,"ifstmt" , "if", "(", "boolexpr", ")", "then", "stmt", "else", "stmt");
        intilize(index++,"whilestmt" , "while", "(", "boolexpr", ")", "stmt");
        intilize(index++,"assgstmt" , "ID", "=", "arithexpr", ";");
        intilize(index++,"boolexpr" , "arithexpr", "boolop", "arithexpr");
        intilize(index++,"boolop" , "<");
        intilize(index++,"boolop" , ">");
        intilize(index++,"boolop" , "<=");
        intilize(index++,"boolop" , ">=");
        intilize(index++,"boolop" , "==");
        intilize(index++,"arithexpr" , "multexpr", "arithexprprime");
        intilize(index++,"arithexprprime" , "+", "multexpr","arithexprprime");
        intilize(index++,"arithexprprime" , "-", "multexpr","arithexprprime");
        intilize(index++,"arithexprprime" , "E");
        intilize(index++,"multexpr" , "simpleexpr", "multexprprime");
        intilize(index++,"multexprprime" , "*", "simpleexpr","multexprprime");
        intilize(index++,"multexprprime" , "/", "simpleexpr","multexprprime");
        intilize(index++,"multexprprime" , "E");
        intilize(index++,"simpleexpr" , "ID");
        intilize(index++,"simpleexpr" , "NUM");
        intilize(index++,"simpleexpr" , "(", "arithexpr",")");
    }

    /**
     * 初始化产生式列表的函数
     * @param string
     * @param list
     */
    private static void intilize(int index,String string, String...list){
        ArrayList<String> tempList = new ArrayList<String>();
        for ( String s: list)
        {
            if (!s.equals("E"))
                tempList.add(s);
        }
        rules.put(index,new Production(string,tempList));
    }

    /**
     * 初始化LR1Table的函数
     */
    public static void TableFactory(int currentStatus, String condition, String action,int targetStatusOrRule){
        HashMap<String,Action> tempHashMap = LR1Table.get(currentStatus);
        if (tempHashMap==null)
            tempHashMap = new HashMap<String, Action>();

        Action tempAction = new Action(action,targetStatusOrRule);
        tempHashMap.put(condition,tempAction);
        LR1Table.put(currentStatus,tempHashMap);
        return;
    }
    /**
     * 初始化LR1分析表
     */
    static {
        TableFactory(0,"{","shift",1);
        TableFactory(0,"program","find",2);
        TableFactory(0,"compoundstmt","find",3);

        TableFactory(1,"if","shift",4);
        TableFactory(1,"while","shift",5);
        TableFactory(1,"ID","shift",6);
        TableFactory(1,"{","shift",1);
        TableFactory(1,"default","reduce",8); //需判断hashmap中是否含有default
        TableFactory(1,"stmt","find",7);
        TableFactory(1,"compoundstmt","find",8);
        TableFactory(1,"stmts","find",9);
        TableFactory(1,"ifstmt","find",10);
        TableFactory(1,"whilestmt","find",11);
        TableFactory(1,"assgstmt","find",12);

        TableFactory(2,"$","shift",13); //遇到$不用移入，直接跳转

        TableFactory(3,"default","reduce",1);

        TableFactory(4,"(","shift",14);

        TableFactory(5,"(","shift",15);

        TableFactory(6,"=","shift",16);

        TableFactory(7,"if","shift",4);
        TableFactory(7,"while","shift",5);
        TableFactory(7,"ID","shift",6);
        TableFactory(7,"{","shift",1);
        TableFactory(7,"default","reduce",8);
        TableFactory(7,"stmt","find",7);
        TableFactory(7,"compoundstmt","find",8);
        TableFactory(7,"stmts","find",17);
        TableFactory(7,"ifstmt","find",10);
        TableFactory(7,"whilestmt","find",11);
        TableFactory(7,"assgstmt","find",12);

        TableFactory(8,"default","reduce",5);

        TableFactory(9,"}","shift",18);

        TableFactory(10,"default","reduce",2);

        TableFactory(11,"default","reduce",3);

        TableFactory(12,"default","reduce",4);

        TableFactory(13,"default","accept",-1);

        TableFactory(14,"ID","shift",19);
        TableFactory(14,"NUM","shift",20);
        TableFactory(14,"(","shift",21);
        TableFactory(14,"boolexpr","find",22);
        TableFactory(14,"arithexpr","find",23);
        TableFactory(14,"multexpr","find",24);
        TableFactory(14,"simpleexpr","find",25);

        TableFactory(15,"ID","shift",19);
        TableFactory(15,"NUM","shift",20);
        TableFactory(15,"(","shift",21);
        TableFactory(15,"boolexpr","find",26);
        TableFactory(15,"arithexpr","find",23);
        TableFactory(15,"multexpr","find",24);
        TableFactory(15,"simpleexpr","find",25);

        TableFactory(16,"ID","shift",19);
        TableFactory(16,"NUM","shift",20);
        TableFactory(16,"(","shift",21);
        TableFactory(16,"arithexpr","find",27);
        TableFactory(16,"multexpr","find",24);
        TableFactory(16,"simpleexpr","find",25);

        TableFactory(17,"default","reduce",7);

        TableFactory(18,"default","reduce",6);

        TableFactory(19,"default","reduce",26);

        TableFactory(20,"default","reduce",27);

        TableFactory(21,"ID","shift",19);
        TableFactory(21,"NUM","shift",20);
        TableFactory(21,"(","shift",21);
        TableFactory(21,"arithexpr","find",28);
        TableFactory(21,"multexpr","find",24);
        TableFactory(21,"simpleexpr","find",25);

        TableFactory(22,")","shift",29);


        TableFactory(23,">","shift",30);
        TableFactory(23,"<","shift",31);
        TableFactory(23,"<=","shift",32);
        TableFactory(23,">=","shift",33);
        TableFactory(23,"==","shift",34);
        TableFactory(23,"boolop","find",35);

        TableFactory(24,"+","shift",36);
        TableFactory(24,"-","shift",37);
        TableFactory(24,"default","reduce",21);
        TableFactory(24,"arithexprprime","find",38);

        TableFactory(25,"*","shift",39);
        TableFactory(25,"/","shift",40);
        TableFactory(25,"default","reduce",25);
        TableFactory(25,"multexprprime","find",41);

        TableFactory(26,")","shift",42);

        TableFactory(27,";","shift",43);

        TableFactory(28,")","shift",44);

        TableFactory(29,"then","shift",45);

        TableFactory(30,"default","reduce",13);

        TableFactory(31,"default","reduce",14);

        TableFactory(32,"default","reduce",15);

        TableFactory(33,"default","reduce",16);

        TableFactory(34,"default","reduce",17);

        TableFactory(35,"ID","shift",19);
        TableFactory(35,"NUM","shift",20);
        TableFactory(35,"(","shift",21);
        TableFactory(35,"arithexpr","find",46);
        TableFactory(35,"multexpr","find",24);
        TableFactory(35,"simpleexpr","find",25);

        TableFactory(36,"ID","shift",19);
        TableFactory(36,"NUM","shift",20);
        TableFactory(36,"(","shift",21);
        TableFactory(36,"multexpr","find",47);
        TableFactory(36,"simpleexpr","find",25);

        TableFactory(37,"ID","shift",19);
        TableFactory(37,"NUM","shift",20);
        TableFactory(37,"(","shift",21);
        TableFactory(37,"multexpr","find",48);
        TableFactory(37,"simpleexpr","find",25);

        TableFactory(38,"default","reduce",18);

        TableFactory(39,"ID","shift",19);
        TableFactory(39,"NUM","shift",20);
        TableFactory(39,"(","shift",21);
        TableFactory(39,"simpleexpr","find",49);

        TableFactory(40,"ID","shift",19);
        TableFactory(40,"NUM","shift",20);
        TableFactory(40,"(","shift",21);
        TableFactory(40,"simpleexpr","find",50);

        TableFactory(41,"default","reduce",22);

        TableFactory(42,"if","shift",4);
        TableFactory(42,"while","shift",5);
        TableFactory(42,"ID","shift",6);
        TableFactory(42,"{","shift",1);
        TableFactory(42,"stmt","find",51);
        TableFactory(42,"compoundstmt","find",8);
        TableFactory(42,"ifstmt","find",10);
        TableFactory(42,"whilestmt","find",11);
        TableFactory(42,"assgstmt","find",12);

        TableFactory(43,"default","reduce",11);

        TableFactory(44,"default","reduce",28);

        TableFactory(45,"if","shift",4);
        TableFactory(45,"while","shift",5);
        TableFactory(45,"ID","shift",6);
        TableFactory(45,"{","shift",1);
        TableFactory(45,"stmt","find",52);
        TableFactory(45,"compoundstmt","find",8);
        TableFactory(45,"ifstmt","find",10);
        TableFactory(45,"whilestmt","find",11);
        TableFactory(45,"assgstmt","find",12);

        TableFactory(46,"default","reduce",12);

        TableFactory(47,"+","shift",36);
        TableFactory(47,"-","shift",37);
        TableFactory(47,"default","reduce",21);
        TableFactory(47,"arithexprprime","find",53);

        TableFactory(48,"+","shift",36);
        TableFactory(48,"-","shift",37);
        TableFactory(48,"default","reduce",21);
        TableFactory(48,"arithexprprime","find",54);

        TableFactory(49,"*","shift",39);
        TableFactory(49,"/","shift",40);
        TableFactory(49,"default","reduce",25);
        TableFactory(49,"multexprprime","find",55);

        TableFactory(50,"*","shift",39);
        TableFactory(50,"/","shift",40);
        TableFactory(50,"default","reduce",25);
        TableFactory(50,"multexprprime","find",56);

        TableFactory(51,"default","reduce",10);

        TableFactory(52,"else","shift",57);

        TableFactory(53,"default","reduce",19);

        TableFactory(54,"default","reduce",20);

        TableFactory(55,"default","reduce",23);

        TableFactory(56,"default","reduce",24);

        TableFactory(57,"if","shift",4);
        TableFactory(57,"while","shift",5);
        TableFactory(57,"ID","shift",6);
        TableFactory(57,"{","shift",1);
        TableFactory(57,"stmt","find",58);
        TableFactory(57,"compoundstmt","find",8);
        TableFactory(57,"ifstmt","find",10);
        TableFactory(57,"whilestmt","find",11);
        TableFactory(57,"assgstmt","find",12);

        TableFactory(58,"default","reduce",9);

    }
    private static void read_prog()
    {
        Scanner sc = new Scanner(System.in);
        int line =1 ;
        while(sc.hasNextLine())
        {
            String temp = sc.nextLine();
            String[] tempArray = temp.split("\\s+");
            for (String s : tempArray)
            {
                if (!s.equals(""))
                    tokens.add(new TokenAndRow(line, s));
            }
            line++;
        }
        tokens.add(new TokenAndRow(-1, "$"));
    }

    private static LinkedList<TokenAndRow> errorList = new LinkedList<TokenAndRow>();
    private static Stack<TokenAndStatus> stack = new Stack<TokenAndStatus>();
    static {
        stack.push(new TokenAndStatus(new TokenAndRow(-1,"$"),0));
    }
    public static void Analysis() throws Exception{
        read_prog();

        boolean isFinish = false;
        int RowNum = -1;
        while (true){
            TokenAndStatus temp = null;
            temp = stack.peek();
            TokenAndRow tempColumn = tokens.getFirst();
            HashMap<String,Action> actionHashMap = LR1Table.get(temp.status);
            if (actionHashMap==null){
                throw  new Exception("木有该状态！"); 
            }
            Action tempAction = actionHashMap.get(tempColumn.token);
            if (tempAction==null){
                tempAction = actionHashMap.get("default");
            }
            if (tempAction ==null)
            {
                /**
                 * 错误处理
                 */
                String LostString = null;
                for (Map.Entry<String,Action> entry: LR1Table.get(temp.status).entrySet()){
                    LostString = entry.getKey();
                }
                TokenAndRow LostStringAndRow =new TokenAndRow(RowNum,LostString);

                StringBuilder originLinkedListStr = new StringBuilder("");
                for (Iterator<TokenAndRow> iterator = tokens.iterator(); iterator.hasNext();){
                    String s = iterator.next().token;
                    if (!s.equals("$"))
                        originLinkedListStr.append(" "+s);
                }

                tokens.addFirst(LostStringAndRow);

                StringBuilder currentLinkedListStr = new StringBuilder("");
                for (Iterator<TokenAndRow> iterator = tokens.iterator(); iterator.hasNext();){
                    String s  = iterator.next().token;
                    if (!s.equals("$"))
                        currentLinkedListStr.append(" "+s);
                }

                Stack<OutputClass_LR> tempStack = new Stack<OutputClass_LR>();
                while (!OutputStack.isEmpty()){
                    OutputClass_LR tempOutputClass_LR = OutputStack.pop();
                    if (tempOutputClass_LR.linkerListStr.contains(originLinkedListStr.toString())){
                        tempOutputClass_LR.linkerListStr=tempOutputClass_LR.linkerListStr.replace(originLinkedListStr,currentLinkedListStr);
                    }
                    tempStack.push(tempOutputClass_LR);
                }
                while (!tempStack.isEmpty())
                    OutputStack.push(tempStack.pop());

                errorList.addFirst(LostStringAndRow);
                continue;
            }

            if (tempAction.action.equals("shift")){
                if (tokens.getFirst().token!="$")
                    tokens.removeFirst();
                stack.push(new TokenAndStatus(tempColumn,tempAction.targetStatusOrRule));
                RowNum = tempColumn.row;
            }
            else if (tempAction.action.equals("reduce")){
                //System.out.println(stack.toString());
                OutputDeal();
                int targetProduction = tempAction.targetStatusOrRule;
                Production production = rules.get(targetProduction);
                if (production==null)
                    throw new Exception("该产生式不存在！");
                String productionLeft = production.left;
                ArrayList<String> productionRight= production.right;

                int size = productionRight.size();
                while (size>0&&stack.size()>1) {
                    stack.pop();
                    size--;
                }

                int currentStatus = stack.peek().status;
                Action targetStatusAction = LR1Table.get(currentStatus).get(productionLeft);
                if (targetStatusAction==null){
                    throw new Exception("找不到接下来要转换的状态！"+ currentStatus + " :" + productionLeft);
                }
                stack.push(new TokenAndStatus(new TokenAndRow(RowNum,productionLeft),targetStatusAction.targetStatusOrRule));
            }
            else if (tempAction.action.equals("accept")){
                OutputDeal();
                isFinish =true;
                break;
            }
        }
        Output();
    }

    private static Stack<OutputClass_LR> OutputStack = new Stack<OutputClass_LR>();
    public static void OutputDeal(){
        StringBuilder stringBuilder = new StringBuilder("");
        boolean isFirst = true;
        for (Iterator<TokenAndStatus> iterator = stack.iterator();iterator.hasNext();){
            String token = iterator.next().token.token;
            if (token!="$"){
                if (isFirst) {
                    stringBuilder.append(token);
                    isFirst = false;
                }
                else
                    stringBuilder.append(" "+token);
            }
        }
        String stackStr = stringBuilder.toString();
        stringBuilder = new StringBuilder("");
        for (Iterator<TokenAndRow> iterator = tokens.iterator(); iterator.hasNext();){
            String token = iterator.next().token;
            if (token!="$")
                stringBuilder.append(" "+token);
        }
        String linkedListStr = stringBuilder.toString();
        OutputStack.push(new OutputClass_LR(stackStr,linkedListStr));
    }
    public static void Output(){
        if (!errorList.isEmpty()){
            for (Iterator<TokenAndRow> iterator= errorList.iterator(); iterator.hasNext();){
                TokenAndRow temp = iterator.next();
                System.out.println("语法错误，第"+temp.row+"行，缺少\""+temp.token+"\"");
            }
        }
        while (!OutputStack.isEmpty()){
            OutputClass_LR tempOutputClass_LR = OutputStack.pop();
            String finalStr = tempOutputClass_LR.stackStr+tempOutputClass_LR.linkerListStr;
            if (OutputStack.size()!=0)
            {
                System.out.println(finalStr+" => ");
            }
            else
                System.out.print(finalStr+" ");
        }
    }
    public static void main(String[] args) throws Exception{
        Analysis();
        return;
    }

}
class Action{
    String action = null;
    int targetStatusOrRule = -1;

    public Action(String action, int targetStatusOrRule){
        this.action = action;
        this.targetStatusOrRule = targetStatusOrRule;
    }
}
class TokenAndStatus {
    TokenAndRow token;
    int status;
    public TokenAndStatus(TokenAndRow token, int status){
        this.token =  token;
        this.status = status;
    }

    @Override
    public String toString() {
        return "TokenAndStatus{" +
                "token=" + token +
                ", status=" + status +
                '}';
    }
}
class TokenAndRow {
    int row;
    String token;
    public TokenAndRow(int row, String token){
        this.row=row;
        this.token=token;
    }

    @Override
    public String toString() {
        return "TokenAndRow{" +
                "row=" + row +
                ", token='" + token + '\'' +
                '}';
    }
}
class Production{
    String left;
    ArrayList<String> right;
    public Production(String left,ArrayList<String> right){
        this.left= left;
        this.right= right;
    }

    @Override
    public String toString() {
        return "Production{" +
                "left='" + left + '\'' +
                ", right=" + right +
                '}';
    }
}
class OutputClass_LR{
    String stackStr;
    String linkerListStr;
    public OutputClass_LR(String stackStr,String linkerListStr){
        this.stackStr = stackStr;
        this.linkerListStr = linkerListStr;
    }

    @Override
    public String toString() {
        return "OutputClass_LR{" +
                "stackStr='" + stackStr + '\'' +
                ", linkerListStr='" + linkerListStr + '\'' +
                '}';
    }
}