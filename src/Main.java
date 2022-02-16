import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

public class Main {
    private final static String home = "https://cp-algorithms.com/";
//    public final static String home = "https://cdnjs.cloudflare.com/ajax/libs/mathjax/2.7.1/MathJax.js";
    private final static String homeDir = "D:\\c_c++\\TODO\\";
    private static Map<String,Boolean> mp = new HashMap<>();
    private static Set<String> st = new HashSet<>();
    private static void addingToSet(){
        st.add("https://github.com/e-maxx-eng/e-maxx-eng/commits/master/src/index.md");
        st.add("https://cp-algorithms-brasil.com/");
        st.add("https://cp-algorithms-brasil.com");
        st.add("http://e-maxx.ru/algo/");
        st.add("http://e-maxx.ru/algo");
        st.add("./test.php");
        st.add("./contrib.html");
        st.add("http://github.com/e-maxx-eng");
        st.add("http://github.com/e-maxx-eng/");
        st.add("https://cdnjs.cloudflare.com/ajax/libs/mathjax/2.7.1/MathJax.js?config=TeX-AMS-MML_HTMLorMML");
    }
    private static Deque<String> dq = new ArrayDeque<>();
    public static void main(String[] args) throws Exception{
        Scanner sc = new Scanner(System.in);

        addingToSet();

        dq.add(home);
        mp.put(home,false);
        while(!dq.isEmpty()){
            String top_url = dq.pop();
            if(top_url.length()==0)continue;
            if(st.contains(top_url))continue;

            if(mp.containsKey(top_url)&&mp.get(top_url))continue;
            System.out.println(top_url);
            String filename = "";

            for(int i=(top_url.charAt(top_url.length()-1)=='/')?(top_url.length()-2):(top_url.length()-1);i>=0;i--){
                if(top_url.charAt(i)=='/'){
                    break;
                }else{
                    filename = top_url.charAt(i)+filename;
                }
            }
            System.out.println(filename);
            filename = removehttps(filename);
            System.out.println(filename);
            filename=checkJS_CSS(filename);
            System.out.println(filename);
            int downloadOption=0;

            if(sc.hasNextInt()){
                downloadOption=sc.nextInt();
            }

            if(downloadOption!=1){
                continue;
            }

            if(mp.containsKey(top_url)){
                if(!mp.get(top_url)){
                    //download
                    if (!st.contains(top_url)){
                        boolean downloadBool = download(top_url,filename);

                        if (downloadBool) {
                            mp.replace(top_url, true);
                        }
                    }
                }
            }else{
                //download

                boolean downloadBool = download(top_url,filename);

                if(downloadBool){
                    mp.put(top_url,true);
                }

            }




        }
    }







    private static String removehttps(String s){
        String ret="";
        String https=  "https://";
        int i=0,j=0;
//        boolean flag = true;
        for(;i<s.length()&&j<https.length();i++,j++){
            if(s.charAt(i)!=https.charAt(j)){
//                flag=false;
                i=0;
                break;
            }
        }

        for(;i<s.length();i++){
            ret+=s.charAt(i);
        }
        String temp="";
//        ret="";
        String fs="\\";
        for(i=0;i<ret.length();i++){
            if(ret.charAt(i)=='/'){
                temp+=fs;
            }else{
                temp+=ret.charAt(i);
            }
        }
        return temp;
    }

    private static void insertList(List<String> linksInLine){
        if(linksInLine.size()==0)return;
        for(String strInList:linksInLine){
            if(st.contains(strInList))continue;
            else if(mp.containsKey(strInList))continue;
            else{
                mp.put(strInList,false);
                dq.add(strInList);
            }
        }
    }


    private static String removehttpsAll(String line) {
        String Https="https://";
        int i=0,j=0;
        String ret="";
        for(;i<line.length();j=0){
            if(Https.charAt(j)==line.charAt(i)){
                int flag=0;
                for(int t=i;j<Https.length()&&t<line.length();t++,j++){
                    if(Https.charAt(j)!=line.charAt(t)){
                        flag=1;
                        break;
                    }
                }
                if(flag==1){
                    ret+=line.charAt(i);
                    i++;
                }else{
                    i+=Https.length();
                    //add ..
                    ret+=homeDir;



                }
            }else{
                ret+=line.charAt(i);
                i++;
            }
        }
        return ret;
    }

    private static String checkJS_CSS(String filename) {
        int n = filename.length();
        if(n>=3&&filename.substring(n-3,n).equals(".js")){
            return filename;
        }else if(n>=4&&filename.substring(n-4,n).equals(".css")){
            return filename;
        }else if(n>=5&&filename.substring(n-5,n).equals(".html")){
            return filename;
        }else if(n>=9&&filename.substring(n-9,n).equals("HTMLorMML")){
            return filename;
        }else{
            filename+=".html";
        }
        return filename;
    }

    private static boolean download(String top_url,String filename) throws Exception {
        CheckDirectory url_dir_check = new CheckDirectory(top_url);
        String path = url_dir_check.check();
        if(path!=null){
            //write file here
            try{
                URL url = new URL(top_url);
                BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
                File file=new File(path,filename);
                BufferedWriter writer = new BufferedWriter(new FileWriter(file.getAbsoluteFile()));
                String line;
                while((line=reader.readLine())!=null){

                    RefExtractor ref = new RefExtractor(line);
                    List<String> linksInLine = ref.ListAll();
                    insertList(linksInLine);
                    line = removehttpsAll(line);
                    writer.write(line);
                    writer.newLine();
                }
                reader.close();
                writer.close();
                return true;
            }
            catch (MalformedURLException mue){
                System.out.println("MUE occured");
            }
            catch (IOException io){
                System.out.println("IO occured");
            }
        }else{
            System.out.println("Error");
        }
        return false;
    }


}
