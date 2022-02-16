import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;

public class page {
        private final String home;

    public page(String home) {
        this.home = home;
    }
    public String removehttps(String s){
        String ret=new String();
        int i=0;
        for(;i<s.length();i++){
            if(s.charAt(i)=='c'){
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

    public void download(String string_url){
        try{

            URL url = new URL(string_url);
            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
            String temp = "";
            int cont=0;
            for(int i=string_url.length()-1;i>=0;i--){
                if(string_url.charAt(i)=='/'){
                    cont++;
                    if(cont==2)
                    break;
                }
                if(string_url.charAt(i)!='/')
                    temp+=string_url.charAt(i);
            }
            String file_name="";
            for(int i=temp.length()-1;i>=0;i--){
                file_name+=temp.charAt(i);
            }
            file_name+=".html";
            String filepath="D:\\c_c++\\TODO";
            String urlWithouthttps = removehttps(string_url);
            File check = new File(filepath+"\\"+"cp-algorithms.com");
            if(check.exists()){
                System.out.println("file exits");
            }else{
                System.out.println("na");
                boolean bool=check.mkdir();
                if(bool){
                    System.out.println("Yo");;
                }else{
                    System.out.println("Ha");
                }

            }
            System.out.println(urlWithouthttps);
                    File file=new File(filepath,file_name);
            BufferedWriter writer = new BufferedWriter(new FileWriter(file.getAbsoluteFile()));
            String line;
            while((line=reader.readLine())!=null){
                writer.write(line);
                writer.newLine();
            }
            reader.close();
            writer.close();
            System.out.println(string_url+ " downloaded as "+ file_name);
        }
        catch (MalformedURLException mue){
            System.out.println("MUE occured");
        }
        catch (IOException io){
            System.out.println("IO occured");
        }
    }

    public static void main(String[] args) throws IOException {
        String url = "https://cp-algorithms.com";
//        download(url);
        page cpAlgorithms =new page(url);
        cpAlgorithms.download(url);
    }
}
