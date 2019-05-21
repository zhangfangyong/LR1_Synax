import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.List;

public class LR1_Synax {
    public static String pu = "";
    protected ArrayList<node> analy = new ArrayList<>();
    protected ArrayList<node1> FirstSet = new ArrayList<>();//first��
    protected ArrayList<node2> CLOSURE = new ArrayList<>(); //��Ŀ�ıհ�
    protected List<Character> ter_copy = new ArrayList<>(); //ȥ$�ս��
    protected List<Character> ter_colt = new ArrayList<>(); //�ս��
    protected List<Character> non_colt = new ArrayList<>(); //���ս��
    protected Vector<Character> char_stack = new Vector<>();  //�ַ�����ջ
    protected Vector<Character> sur_str = new Vector<>(); //ʣ�������ַ���
    protected Vector<Integer>   int_stack = new Vector<>(); //״̬ջ
    protected List<Character> colt = new ArrayList<>();    //ȥ$�ķ�������
    protected char[][] Go;    //��ϵ����
    protected String[][] PTable; //������
    public static void main(String []arg) throws IOException{
 /*   FilereadUtil fr = new FilereadUtil();
    List<String> list = new ArrayList<>();
    List<Character> list1 = new ArrayList<>();
    List<String> list2 = new ArrayList<>();
    list = fr.Read("D:\\ProgramCode\\Java\\LR1_Synax\\test2.txt");
    SyntaxUtil util = new SyntaxUtil();
    util.Util(list);
    list1 = util.GetNon();
    if(util.Judge(list)){
        list2=util.Remove(list,list1);
        util.Util(list2);
        util.Con_First();
        util.Huifu();
        util.Util(list);
        util.Get_colt();
        util.Relation();
        util.Construc();
        util.Parser_Table();
        util.show();
        String s = "i+i#";
        util.AnalyStack(s);
    }else {
        util.Con_First();
        util.Get_colt();
        util.Relation();
        util.Construc();
        util.Parser_Table();
        util.show();
        String s = "aabab#";
        util.AnalyStack(s);
    }*/
        MyFrame my =new MyFrame("LR(1)������");
}
}
//�ṹ��
class node{          //���ڷ�����Ҳ���ʽ�������Ҳ�
    public char left;
    public String right;
    public node(char left,String right){
        this.left=left;
        this.right=right;
    }
}
class node1{         //���ڴ��First��
    public List<Character> Se = new ArrayList<>();
   // public  node1(){}
    public void add(char ch){
        this.Se.add(ch);
    }
}
class node2{         //���ڴ��ÿ����Ŀ��
    public List<String> St = new ArrayList<>();
    public void add(String str){this.St.add(str);}
}
//�ķ�������
class SyntaxUtil extends LR1_Synax {
    //ȷ�����ս��
    public boolean IsNotsymbols(char ch) {
        if (ch >= 'A' && ch <= 'Z') return true;
        else return false;
    }
    //��ȡ�ַ��ڷ��ս������±�
    public int Get_nindex(char temp) {
        for (int i = 0; i < non_colt.size(); i++) {
            if (temp == non_colt.get(i)) return i;
        }
        return -1;
    }
    //��ȡ���е��ķ����ż��ϣ�����дGO����
    public void Get_colt(){

        for(int i = 0;i<non_colt.size();i++){
            colt.add(non_colt.get(i));
        }
        for(int i = 0;i<ter_copy.size();i++){
            colt.add(ter_copy.get(i));
        }

    }
    //��ʼ����ϵ��ϵ����
    public void Relation(){
        Go = new char[100][100];
        for(int i=0;i<100;i++)
            for(int j=0;j<100;j++){
            Go[i][j] = '!';
            }
    }
    //��ȡÿ�����ս����First��
    public void Get_first(char temp) {
        int tag = 0;
        int flag = 0;
        for (int i = 0; i < analy.size(); i++) {
            if (analy.get(i).left == temp) {   //ƥ�����ʽ��
                if (!IsNotsymbols(analy.get(i).right.charAt(0)))   //����Ҳ���һ���ַ����ս����ֱ�Ӽ���first��
                    FirstSet.get(Get_nindex(temp)).add(analy.get(i).right.charAt(0));
                else {
                    for (int j = 0; j < analy.get(i).right.length(); j++) {
                        if (!IsNotsymbols(analy.get(i).right.charAt(j)))  //ֱ��Ѱ�ҵ��ս��Ϊֹ
                        {
                            FirstSet.get(Get_nindex(temp)).add(analy.get(i).right.charAt(j));
                            break;
                        }
                        Get_first(analy.get(i).right.charAt(j));  //�Ҳ��ݹ�Ѱ��
                        Iterator it2 = FirstSet.get(Get_nindex(analy.get(i).right.charAt(j))).Se.iterator();
                        while (it2.hasNext()) {
                            char next = (char) it2.next();
                            if (next == '$') flag = 1;
                            else {
                                FirstSet.get(Get_nindex(temp)).add(next);  //��FIRST(Y)�еķ�$�ͼ���FIRST(X)
                            }
                        }
                        if (flag == 0) break;
                        else {
                            tag = tag + flag;
                            flag = 0;
                        }
                    }
                    if (tag == analy.get(i).right.length())
                        FirstSet.get(Get_nindex(temp)).add('$');  //�����Ҳ�first(Y)����$,��$����FIRST(X)��
                }
            }
        }
    }
    //�ж���ݹ�
    public boolean Judge(List<String> list){
         ArrayList<node> analy = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            node aa = new node(list.get(i).charAt(0),list.get(i).substring(3));
            analy.add(aa);
        }
        for(int i=0;i<analy.size();i++){
            if(analy.get(i).left == analy.get(i).right.charAt(0)){   //������ʽ���󲿵����Ҳ���һ�����ţ��������ݹ�
                return true;
            }
        }
        return false;
    }
    //������ݹ�
    public List<String> Remove(List<String> list1,List<Character> list2){
        List<String> temp1 = new ArrayList<>();  //������������ݹ���µ�List
        ArrayList<node> analy = new ArrayList<>();
        List<Character> ch = new ArrayList<>();  //�����Ѿ�ʹ���˵���ĸ
        List<Character> List2 = list2;          //�̳в��������ݣ����ս������
        for (int i = 0; i < list1.size(); i++) {
            node aa = new node(list1.get(i).charAt(0),list1.get(i).substring(3));
            analy.add(aa);
        }
        for(int i=0;i<analy.size();i++){   //�������в���ʽ
            if(analy.get(i).left == analy.get(i).right.charAt(0)){   //�������󲿵����Ҳ���һ������
                ch.add(analy.get(i).left);                           //�Ը�����ĸ���б���
                for(int j=0;j<analy.size();j++){                     //Ѱ�Ҹ���ĸ����һ������ʽ���Ա�Ѱ��P->��P'�Ħ�
                    if((analy.get(j).left == analy.get(i).left)){
                        if (analy.get(j).left != analy.get(j).right.charAt(0)){
                        for(char k='A';k<='Z';k++){                 //������ĸ����Ѱ�ҿ����������ĸ
                            int flag =0;
                            Iterator it = List2.iterator();
                            while (it.hasNext()){
                                char t =(char)it.next();
                                if(k == t){
                                    flag = 1;                       //������ĸ��ԭ���ķ��ս�������ˣ���flag=1
                                }
                            }
                            if(flag == 0) {                       //�������ĸû��ʹ�ù����Ͱ���������ݹ�ķ����趨�µĲ���ʽ
                                String str1 = analy.get(i).left + "->" + analy.get(j).right.charAt(0) + k;
                                String str2 = k + "->" + analy.get(i).right.substring(1) + k;
                                String str3 = k + "->" + '$';
                                List2.add(k);          //������ĸ������ս������
                                temp1.add(str1);
                                temp1.add(str2);
                                temp1.add(str3);
                                break;
                            }
                        }
                    }
                }
            }
        }
    }
        for (int i =0;i<analy.size();i++){     //����ԭ���Ĳ���ʽ����û����ݹ�Ĳ���ʽ�����µ�List
            int flag =0;
            Iterator it = ch.iterator();
            while (it.hasNext()){                //���ԭ������ʽ������ch�����У���֤���÷��ž�����ݹ飬��flag=1
                char t =(char)it.next();
                if(analy.get(i).left == t){
                    flag = 1;
                }
            }
            if(flag == 0){                      //���û�г��֣�����������
                String str1 = analy.get(i).left+"->"+analy.get(i).right;
                temp1.add(str1);
            }
        }
        return temp1;
    }
    //�������ķ����н�������ȡȫ���ս�������ս��
    public void Util(List<String> list) {
        analy.clear();      //��������ݹ�ʱ�����еļ���Ҫ�������
        non_colt.clear();
        ter_colt.clear();
        ter_copy.clear();
        for (int i = 0; i < list.size(); i++) {
            node aa = new node(list.get(i).charAt(0),list.get(i).substring(3));
            analy.add(aa);
        }
        for (int index = 0; index < list.size(); index++) {
            String temp = analy.get(index).left + analy.get(index).right;
            for (int i = 0; i < temp.length(); i++) {
                if (IsNotsymbols(temp.charAt(i))) { //����Ƿ��ս��
                    int flag = 0;
                    for (int j = 0; j < non_colt.size(); j++) {   //�������ս������
                        if (non_colt.get(j) == temp.charAt(i))  //����Ѿ����ڣ���flag��Ϊ1
                        {
                            flag = 1;
                            break;
                        }
                    }
                    if (flag == 0) non_colt.add(temp.charAt(i)); //��������ڣ����
                } else {                            //������ս��
                    int flag = 0;
                    for (int j = 0; j < ter_colt.size(); j++) {
                        if (ter_colt.get(j) == temp.charAt(i)) {
                            flag = 1;
                            break;
                        }
                    }
                    if (flag == 0) ter_colt.add(temp.charAt(i));
                }
            }
        }
        ter_colt.add('#'); //�ս���������ӡ�#��
        for (int i = 0; i<ter_colt.size(); i++)  //����ȥ$�ս������
        {
            if (ter_colt.get(i) != '$')
                ter_copy.add(ter_colt.get(i));
        }
    }
    //���쵱ǰ�ķ���First��
    public void Con_First() {
        for (int i = 0; i<non_colt.size(); i++)    //���ݷ��ս������ȷ��First��������ĳ���
        {   node1 bb =new node1();
            FirstSet.add(bb);
        }
        for (int i=0;i<non_colt.size();i++){      //����ÿһ�����ս����First����
            Get_first(non_colt.get(i));
        }
        for (int i = 0; i < non_colt.size(); i++) {      //���ڴ��ÿ�����ս����First����List���������ظ������Բ���Set����������һ������
            Set<Character> s1 = new TreeSet<>();
            Iterator it = FirstSet.get(i).Se.iterator();
            while (it.hasNext()) {
                s1.add((char) it.next());
            }
            FirstSet.get(i).Se.clear();
            Iterator it1 = s1.iterator();
            while (it1.hasNext()) {
                char t = (char) it1.next();
                FirstSet.get(i).Se.add(t);
            }
        }
    }
    //���ⲿ��ȡ���ս������
    public List<Character> GetNon(){
        return non_colt;
    }
    //��CLOSURE����
    public void Closure(String str) {
        Iterator it = CLOSURE.get(CLOSURE.size() - 1).St.iterator();
        int flag = 0;
        while (it.hasNext()) {                         //�����ͽ����Ĳ���ʽ���м�⣬�Ƿ��ڵ�ǰ��Ŀ��
            String s = String.valueOf(it.next());
            if (s.equals(str)) {
                flag = 1;
                break;
            }
        }
        if (flag == 0) {        //������ڣ����
                CLOSURE.get(CLOSURE.size() - 1).add(str);   //��Ӹò���ʽ������Ѱ���Ƿ���Ҫ���ݸò���ʽ�Ե�ǰ��Ŀ����������
                int temp2 = CLOSURE.get(CLOSURE.size() - 1).St.get(CLOSURE.get(CLOSURE.size() - 1).St.size() - 1).indexOf('.');   //ȷ��'.'��λ��
                int temp3 = CLOSURE.get(CLOSURE.size() - 1).St.get(CLOSURE.get(CLOSURE.size() - 1).St.size() - 1).indexOf(',');   //ȷ��','��λ��
                char ch1 = CLOSURE.get(CLOSURE.size() - 1).St.get(CLOSURE.get(CLOSURE.size() - 1).St.size() - 1).charAt(temp2 + 1); //��¼'.��������ַ�
                char ch2 = CLOSURE.get(CLOSURE.size() - 1).St.get(CLOSURE.get(CLOSURE.size() - 1).St.size() - 1).charAt(temp2 + 2); //��¼'.'����ڶ������ַ�
                String temp4 = CLOSURE.get(CLOSURE.size() - 1).St.get(CLOSURE.get(CLOSURE.size() - 1).St.size() - 1).substring(temp3+1); //���浱ǰ����ʽ��չ����
                if (IsNotsymbols(ch1)) {      //���������Ƿ��ս��
                    if (ch2 == ',') {          //������ս������û���ַ�
                        for (int i = 0; i < analy.size(); i++) {
                            if (analy.get(i).left == ch1) {
                                String s1 = analy.get(i).left + "->" + '.' + analy.get(i).right + ',' + temp4;  //�µĲ���ʽ��չ����Ϊԭ����ʽ��չ����
                                Closure(s1);
                            }
                        }
                    } else if (IsNotsymbols(ch2)) {      //�����ս������Ϊ���ս��A
                        int flag1 = 0;
                        Iterator it1 = FirstSet.get(Get_nindex(ch2)).Se.iterator();  //�ȼ��A��First����û��$
                        while (it1.hasNext()) {
                            char c = (char) it1.next();
                            if (c == '$') {
                                flag1 = 1;
                                break;
                            }
                        }
                        if (flag1 == 0) {            //���û��$������ÿһ���·��ֲ���ʽ��չ�����趨Ϊfirst��
                            for (int i = 0; i < analy.size(); i++) {
                                if (analy.get(i).left == ch1) {
                                    Iterator it2 = FirstSet.get(Get_nindex(ch2)).Se.iterator();
                                    while (it2.hasNext()) {
                                        char c = (char) it2.next();
                                        String s1 = analy.get(i).left + "->" + '.' + analy.get(i).right + ',' + c;
                                        Closure(s1);
                                    }
                                }
                            }
                        }
                    }else if(!IsNotsymbols(ch2)){   //�����ս���������ս��
                        for (node node : analy) {
                            if (node.left == ch1) {
                                String s1 = node.left + "->" + '.' + node.right + ',' + ch2;
                                Closure(s1);
                            }
                        }
                      }
                }
            }
        }
//GO����
 public void GO(node2 no,char X){
      Iterator it = no.St.iterator();
      while (it.hasNext()){//����Ƿ����A->��.X��
            String te = String.valueOf(it.next());
            if(te.charAt(te.indexOf('.')+1) == X){    //�������
                  int flag = 0;
                  char[] cha = te.toCharArray();  //�������λ��
                  char c =cha[te.indexOf('.')];
                  cha[te.indexOf('.')] = cha[te.indexOf('.')+1];
                  cha[te.indexOf('.')+1] = c;
                  String s1 = te.valueOf(cha);
                  for(int j=CLOSURE.size()-1;j>=0;j--){
                      Iterator it1=CLOSURE.get(j).St.iterator();
                      while (it1.hasNext()){
                          String te1 = String.valueOf(it1.next());
                          if(s1.equals(te1)){       //�����µĲ���ʽ������Ƿ���������Ŀ�����Ѿ�����
                              flag = 1;
                              Go[CLOSURE.indexOf(no)][j] = X;  //������ڣ��򽫵�ǰ��Ŀ�������иò���ʽ����Ŀ���Ĺ�ϵ������Ϊ��Ӧ��X
                              break;
                          }
                      }
                      if(flag == 1) break;       //ֹͣ����
                  }
                  if(flag == 0){                //���û����������Ŀ���д���
                          String s2 = te.substring(0,te.indexOf(','));
                          node2 aa = new node2();
                          CLOSURE.add(aa);
                          int in2 = CLOSURE.size()-1;
                          Go[CLOSURE.indexOf(no)][in2] = X;
                          Iterator tt = no.St.iterator();
                          while (tt.hasNext()){               //�Ը���Ŀ��������.�����ַ�ΪX�Ĳ���ʽ
                              String s = String.valueOf(tt.next());
                              if(s2.charAt(s2.indexOf('.')+1) == s.charAt(s.indexOf('.')+1)) {
                                  char[] cha1 = s.toCharArray();  //�������λ��
                                  char c1 =cha1[s.indexOf('.')];
                                  cha1[s.indexOf('.')] = cha1[s.indexOf('.')+1];
                                  cha1[s.indexOf('.')+1] = c1;
                                  String sss = s.valueOf(cha1);
                                  Closure(sss);
                              }
                          }
                  }
            }
      }
 }
//������Ŀ��
    public void Construc(){
     node2 bb =new node2();    //��ӳ�ʼ�ĳ�̬����ʽ
     CLOSURE.add(bb);
     String st = 'Z'+"->"+'.'+non_colt.get(0)+','+'#';
     Closure(st);
       for(int i=0;i<CLOSURE.size();i++){    //CLOSURE.size()�Ƕ�̬���ģ������������һ���������е���Ŀ���������//////
         for(int j=0;j<colt.size();j++){
            // System.out.println(i+" "+j+" 00");
             GO(CLOSURE.get(i),colt.get(j));
         }
       }
    }
    //���������
    public void Parser_Table(){
        PTable =new String[100][100];
        for(int i=0;i<100;i++){
            for(int j=0;j<100;j++){
                PTable[i][j] = "!";               //��ʼ��������
            }
        }
        for(int k =0;k<CLOSURE.size();k++){
            for(int h=0;h<colt.size();h++){
                if(IsNotsymbols(colt.get(h))){         //���ڷ��ս��
                    Iterator it = CLOSURE.get(k).St.iterator();
                    while (it.hasNext()){
                        String str = String.valueOf(it.next());
                        char ch = str.charAt(str.indexOf('.')+1);     //��¼ÿ����Ŀ����ÿ������ʽ.������ַ�
                        if(colt.get(h) == ch){                            //��.����Ķ��ַ����ڸ��ַ�
                            for(int j=0;j<CLOSURE.size();j++){        //����Go��ϵ�������Ը���Ŀ��Ϊ����Ԫ��
                                if(Go[k][j] == ch){                   //�������յ�
                                    PTable[k][h] = String.valueOf(j); //��Goto = �յ�
                                    break;
                                }
                            }
                            break;   //ֹͣ����Ŀ���Ĳ���ʽ����
                        }
                    }
                }else{                                                   //���������Ƿ��ս��
                    Iterator it = CLOSURE.get(k).St.iterator();
                    while (it.hasNext()){
                        String str = String.valueOf(it.next());
                        char ch1 = str.charAt(str.indexOf('.')+1);      //��¼.������ַ�
                        char ch2 = str.charAt(str.indexOf('.')-1);      //��¼.ǰ����ַ�
                        char ch3 = str.charAt(str.indexOf(',')+1);      //��¼,�����չ����
                        if((ch2 == colt.get(0)) && (colt.get(h) == '#') && (str.charAt(0) == 'Z')){       //������ʽΪS'->S.,#����������������Ӧλ��ΪAcc
                                String s ="Acc";
                                PTable[k][h] = s;
                                break;
                        }else {
                            if((ch1 == ',') && (ch3 == colt.get(h))){      //������ʽΪA->��.,a�����ù�Լ����
                                for(int i=0;i<analy.size();i++){
                                    String s = str.substring(str.indexOf('>')+1,str.indexOf('.'));
                                    if((str.charAt(0) == analy.get(i).left) && (s.equals(analy.get(i).right))){   //�Բ���ʽ���б�����Ѱ�Ҷ�Ӧ��Լ����ʽ
                                        String st = 'r'+String.valueOf(i+1);
                                        PTable[k][h] = st;
                                        break;
                                    }
                                }
                            }else {                                             //������ʽΪA->��.a��,b
                                if(ch1 == colt.get(h)){
                                    for(int j=0;j<CLOSURE.size();j++){
                                        if(Go[k][j] == ch1){                    //�Թ�ϵ���������Ѱ��ת����״̬
                                            String st = 's'+String.valueOf(j);
                                            PTable[k][h] = st;
                                            break;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    //��ʽ���������
    public String format(String s){
        String out = s;
        int i = s.length();
        while(i<=10){
            out += " ";
            i++;
        }
        return out;
    }
    //�����ַ�������
    public void AnalyStack(String s){
        int in = 0;
        int in1 = 0;
        int in2 =0;
        int in3 =0;
        for(int i=s.length()-1;i>=0;i--){
            sur_str.add(s.charAt(i));
        }
        char_stack.add('#');
        int_stack.add(0);
        System.out.print("����      ״̬ջ    ����ջ    ʣ�����봮    ����"+"\n");
        pu += "����      ״̬ջ    ����ջ    ʣ�����봮    ����"+"\n";   //
        while (sur_str.size() > 0){
            System.out.print(format(String.valueOf(in)));
            pu += format(String.valueOf(in));//
            String temp = "";
             for(int j=0;j<int_stack.size();j++){
                temp =temp+String.valueOf(int_stack.get(j))+"|";
            }
            System.out.print(format(temp));
            pu += format(temp);//
            temp = "";
            for(int j=0;j<char_stack.size();j++){
                temp += char_stack.get(j);
            }
            System.out.print(format(temp));
            pu += format(temp);
            temp = "";
            for(int j=sur_str.size()-1;j>=0;j--){
                temp += sur_str.get(j);
            }
            System.out.print(format(temp));
            pu += format(temp);
            int in4 = int_stack.get(int_stack.size()-1);
            char ch = sur_str.get(sur_str.size()-1);
            for (int i=0;i<colt.size();i++){
                if(ch == colt.get(i)) in1 =i;
            }
            String st = PTable[in4][in1];
            if(st.charAt(0) == 's'){
                String tt = String.valueOf(st.charAt(1));
                int_stack.add(Integer.parseInt(tt));
                char_stack.add(sur_str.get(sur_str.size()-1));
                sur_str.remove(sur_str.size()-1);
                System.out.print(format("״̬"+int_stack.get(int_stack.size()-1)+"��ջ"));
                pu += format("״̬"+int_stack.get(int_stack.size()-1)+"��ջ");
            }
            if(st.charAt(0) == 'r'){
                String tt = String.valueOf(st.charAt(1));
               int in5 = Integer.parseInt(tt)-1;
               for(int i=0;i<analy.get(in5).right.length();i++){
                   char_stack.remove(char_stack.size()-1);
                   int_stack.remove(int_stack.size()-1);
               }
                char_stack.add(analy.get(in5).left);
                for (int i=0;i<colt.size();i++){
                    if(char_stack.get(char_stack.size()-1) == colt.get(i)){
                        in2 = i;
                    }
                }
                int_stack.add(Integer.parseInt(PTable[int_stack.get(int_stack.size()-1)][in2]));
                System.out.print(format(analy.get(in5).left+"->"+analy.get(in5).right+"��Լ"));
                pu += format(analy.get(in5).left+"->"+analy.get(in5).right+"��Լ");
            }
            if(st.equals("!")) {
                System.out.print(format("Error������"));
                pu += format("Error������");
                break;
            }
            for(int i=0;i<colt.size();i++){
                if(colt.get(i) == '#') in3 = i;
            }
            char cc = char_stack.get(char_stack.size()-1);
            char ss = sur_str.get(sur_str.size()-1);
            if(PTable[int_stack.get(int_stack.size()-1)][in3].equals("Acc") && (cc == non_colt.get(0)) && (ss == '#')){
                System.out.println();
                System.out.print(format(String.valueOf(in+1)));
                pu += "\n"+format(String.valueOf(in+1));
                String temp1 = "";
                for(int j=0;j<int_stack.size();j++){
                    temp1 =temp1+String.valueOf(int_stack.get(j))+"|";
                }
                System.out.print(format(temp1));
                pu += format(temp1);//
                temp1 = "";
                for(int j=0;j<char_stack.size();j++){
                    temp1 += char_stack.get(j);
                }
                System.out.print(format(temp1));
                pu += format(temp1);//
                temp1 = "";
                for(int j=sur_str.size()-1;j>=0;j--){
                    temp1 += sur_str.get(j);
                }
                System.out.print(format(temp1)+format("Success"));
                pu += format(temp1)+format("Success");
                break;
            }
            in +=1;
            System.out.println();
            pu += "\n";
        }
    }
    public void Huifu(){
        ArrayList<node1> temp = new ArrayList<>();
        for(int i=0;i<FirstSet.size();i++){
            int flag = 0;
            Iterator it = FirstSet.get(i).Se.iterator();
            while (it.hasNext()){
                char ch = (char)it.next();
                if(ch == '$'){
                    flag = 1;
                    break;
                }
            }
            if(flag == 0){
                node1 aa = new node1();
                temp.add(aa);
                Iterator it1 = FirstSet.get(i).Se.iterator();
                while (it1.hasNext()){
                    char ch = (char)it1.next();
                    temp.get(temp.size()-1).add(ch);
                }
            }
        }
        FirstSet.clear();
        System.out.println(temp.size());
        for (int i=0;i<temp.size();i++){
            node1 bb =new node1();
            FirstSet.add(bb);
            Iterator it = temp.get(i).Se.iterator();
            while (it.hasNext()){
                char ch1 =(char)it.next();
                FirstSet.get(FirstSet.size()-1).Se.add(ch1);
            }
        }
    }
    //��ʾ����
    public void show(){
        //��ʾ��Ŀ��
       for(int i=0;i<CLOSURE.size();i++){
           Iterator it = CLOSURE.get(i).St.iterator();
           while (it.hasNext()){
               String ss = String.valueOf(it.next());
               System.out.println(ss);
               pu += ss+"\n";
           }
           System.out.println();
           pu += "\n";
       }
       //��ʾ������
        System.out.println("\t"+"\t"+"Goto"+"\t"+"Action");
        System.out.print("״̬"+"\t");
        pu += "\t"+"\t"+"Goto"+"\t"+"Action"+"\n";
        pu += "״̬"+"\t";
        for(int i=0;i<colt.size();i++){
            System.out.print(colt.get(i)+"\t");
            pu += colt.get(i)+"\t";
        }
        pu += "\n";
        System.out.println();
        for(int i=0;i<CLOSURE.size();i++){
            System.out.print(i+":"+"\t");
            pu += i+":"+"\t";
            for(int j=0;j<colt.size();j++){
                System.out.print("\t"+PTable[i][j]);
                pu += "\t"+PTable[i][j];
            }
            System.out.println();
            pu += "\n";
        }
    }
}
//�ļ���ȡ��
class FilereadUtil {
    public List<String> Read(String filename) throws IOException {
        BufferedReader in = new BufferedReader(new FileReader(filename));
        String s;
        List<String> list = new ArrayList<>();
        while ((s = in.readLine()) != null) list.add(s);
        in.close();
        return list;
    }
}
//���滯
class MyFrame extends JFrame {
    JLabel lable;
    JButton button1, button2;
    JTextField tf1;
    JTextArea ta;

    MyFrame(String s) {//���캯��
        super(s);
        lable = new JLabel("����������ַ�����");
        lable.setBounds(15, 5, 100, 30);
        tf1 = new JTextField(15);
        tf1.setBounds(85, 45, 260, 30);
        button1 = new JButton("�﷨����");
        button1.setBounds(370, 45, 100, 30);
        ButtonListener b1 = new ButtonListener();
        button1.addActionListener(b1);//���ü�����
        ta = new JTextArea();
        ta.setFont(new Font("����",Font.PLAIN,20));
        JScrollPane jsp = new JScrollPane(ta);//����������
        jsp.setBounds(15, 105, 650, 350);
        button2 = new JButton("�˳�");
        button2.setBounds(370, 500, 100, 30);
        ButtonListener b2 = new ButtonListener();
        button2.addActionListener(b2);
        Container window1 = this.getContentPane();
        window1.setLayout(null);//�����в�������
        window1.add(lable);
        window1.add(tf1);
        window1.add(button1);
        window1.add(button2);
        window1.add(jsp);
        this.setSize(800, 600);//���ô�С
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//�ɹر�
        this.setVisible(true);//���ÿɼ�
    }

    class ButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == button1) {
                FilereadUtil fr = new FilereadUtil();
                List<String> list = new ArrayList<>();
                List<Character> list1 = new ArrayList<>();
                List<String> list2 = new ArrayList<>();
                try {
                    list = fr.Read("D:\\ProgramCode\\Java\\LR1_Synax\\test1.txt");
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                SyntaxUtil util = new SyntaxUtil();
                util.Util(list);
                list1 = util.GetNon();
                if (util.Judge(list)) {
                    list2 = util.Remove(list, list1);
                    util.Util(list2);
                    util.Con_First();
                    util.Huifu();
                    util.Util(list);
                    util.Get_colt();
                    util.Relation();
                    util.Construc();
                    util.Parser_Table();
                    util.show();
                   // String s = "i+i#";
                    util.AnalyStack(tf1.getText());
                    ta.append(util.pu);
                } else {
                    util.Con_First();
                    util.Get_colt();
                    util.Relation();
                    util.Construc();
                    util.Parser_Table();
                    util.show();
                   // String s = "aabab#";
                    util.AnalyStack(tf1.getText());
                    ta.append(util.pu);
                }
            }
                if (e.getSource() == button2) {
                    System.exit(0);
                }
        }
    }
}
