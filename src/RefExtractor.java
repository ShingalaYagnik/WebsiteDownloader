import java.util.ArrayList;
import java.util.List;

class RefExtractor {
        private final String str;
        RefExtractor(String s) {
            str=s;
        }
        List<String> ListAll() throws Exception{
            List<String> ret = new ArrayList<>();
//                System.out.println("Yes");
                String data = str;
//                System.out.println(data);
                String src="src=\"";
                String href="href=\"";
                for(int i=0;i<data.length();){
                    if(data.charAt(i)==src.charAt(0)){
                        i++;
                        int j=1;
                        boolean flag=false;
                        for(;i<data.length()&&j<src.length();i++,j++){
                            if(src.charAt(j)!=data.charAt(i)){
                                flag=true;
                                break;
                            }
                        }

                        if(!flag&&j==src.length()){
                            String temp="";
                            String home = "https://cp-algorithms.com";
                            if(data.charAt(i)=='.'){
                                temp=home;
                                i++;
                            }
                            for(;i<data.length();i++){
                                if(data.charAt(i)=='"'){
                                    break;
                                }else {
                                    temp+=data.charAt(i);
                                }
                            }
                            ret.add(temp);
                        }
                    }else if(data.charAt(i)==href.charAt(0)){
                        i++;
                        int j=1;
                        boolean flag=false;
                        for(;i<data.length()&&j<href.length();i++,j++){
                            if(href.charAt(j)!=data.charAt(i)){
                                flag=true;
                                break;
                            }
                        }
                        if(!flag&&j==href.length()){
                            String temp="";
                            String home = "https://cp-algorithms.com";
                            if(data.charAt(i)=='.'){
                                temp=home;
                                i++;
                            }
                            for(;i<data.length();i++){
                                if(data.charAt(i)=='"'){
                                    break;
                                }else {
                                    temp+=data.charAt(i);
                                }
                            }
                            ret.add(temp);
                        }
                    }else{
                        i++;
                    }
                }
            return ret;
        }

//    public static void main(String[] args) throws Exception {
//        RefExtractor cls = new RefExtractor();
//        List<String> l =cls.ListAll();
//        for(String t:l){
//            System.out.println(t);
//        }
//    }
}
