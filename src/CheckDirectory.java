import java.io.File;
import java.util.ArrayList;
import java.util.List;

class CheckDirectory {
    private final String s;
    private final String https="https://";
    private final String home = "D:\\c_c++\\TODO\\";
    private final String cpalgo = "cp-algorithms.com";
    CheckDirectory(String url){
        this.s = url;
    }
    public String removeHttps(){
        int i=0,j=0;
        boolean flag = true;
        for(;i<s.length()&&j<https.length();i++,j++){
            if(s.charAt(i)!=https.charAt(j)){
                flag=false;
                break;
            }
        }
        String ret="";
        if(flag){
            ret="";
            for(int k=https.length();k<s.length();k++){
                ret+=s.charAt(k);
            }
        }else{
            ret=s;
        }
        return ret;
    }
    public List<String> heirachy(){
        ArrayList<String> ret = new ArrayList<>();
        String str =  removeHttps();
        String temp="";
        for(int i=0;i<str.length();i++){
            if(str.charAt(i)=='/'){
                ret.add(temp);
                temp = "";
            }else{
                temp+=str.charAt(i);
                if(i==(str.length()-1)){
                    ret.add(temp);
                }
            }
        }
        return ret;
    }
    public String check(){
        List<String> lst = heirachy();
        String currentDir = home;
        String fs="\\";
        int i=0;
        if(lst.size()>0&&lst.get(i).equals(cpalgo)){
            i++;
        }
        for(;i<lst.size()-1;i++){
            currentDir+=lst.get(i);
            currentDir+=fs;
            File file = new File(currentDir);
            if(file.exists()&&file.isDirectory()){
                System.out.println(currentDir+ "is already there");
            }else{
                boolean bool = file.mkdir();
                if(bool){
                    System.out.println("directory created");
                }else{
                    currentDir=null;
                    System.out.println("Directory creation failed");
                }
            }

        }

        return currentDir;
    }

//    public static void main(String[] args) {
//        CheckDirectory chk = new CheckDirectory();
//        if(chk.check()!=null){
//            System.out.println("Made it");
//        }else{
//            System.out.println("Something is basmaring");
//        }
//    }

}


