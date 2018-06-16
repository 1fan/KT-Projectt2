import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yifan on 2017/10/9.
 */
public class FileOperation {
    private List<String> attr = new ArrayList<String>();
    private List<String> BodyParts = new ArrayList<String>();
    private List<String> DrugName= new ArrayList<String>();
    private List<String> Symptom = new ArrayList<String>();


//    Read tweets from txt, write to arff
    public void ReadTXTWriteARFF(String readpath, String readArffPath, String writepath) throws IOException {
        setBodyParts();
        setDrugName();
        setSymptom();
        // read file content from file
        StringBuffer sb = new StringBuffer("");
        FileReader reader = new FileReader(readpath);
        FileWriter writer = new FileWriter(writepath,true);
        BufferedWriter bw = new BufferedWriter(writer);
        BufferedReader br = new BufferedReader(reader);
        String str = null;
        bw.write("@RELATION twitter-adr"+'\n');
        for(int i=0;i<attr.size()-1;i++){
            bw.write("@ATTRIBUTE "+attr.get(i)+" NUMERIC"+'\n');
        }
//        bw.write("@ATTRIBUTE isBodyPart {N,Y}"+'\n');
//        bw.write("@ATTRIBUTE isDrugName {N,Y}"+'\n');
//        bw.write("@ATTRIBUTE isSymptom {N,Y}"+'\n');
        bw.write("@ATTRIBUTE isDrug_Symptom {N,Y}"+'\n');
//        bw.write("@ATTRIBUTE isALL {N,Y}"+'\n');
        bw.write("@ATTRIBUTE class {N,Y}\n" +
                "@DATA\n");
        while ((str = br.readLine()) != null) {
            String ID = str.split("\t")[0];
            String Class = str.split("\t")[1];
            String Content = str.split("\t")[2];
//            bw.write(ID+'\t'+checkIsDrugSymptomToTXT(str)+'\t'+Content+'\n');
//            bw.write(readAttributeFromArffForTweet(readArffPath,ID)+checkIsBodyPart(str)+checkIsDrugName(str)+checkIsSymptom(str)+Class+'\n');
            bw.write(readAttributeFromArffForTweet(readArffPath,ID)+checkIsDrugSymptom(str)+"?"+'\n');
            System.out.println(ID+"+"+Content);
        }
        br.close();
        reader.close();
        bw.close();
        writer.close();
    }

    public void readAttr(String path) throws IOException{
        StringBuffer sb = new StringBuffer("");
        FileReader reader = new FileReader(path);
        BufferedReader br = new BufferedReader(reader);
        String str = null;
        while ((str = br.readLine()) != null) {
            if(str.contains("@ATTRIBUTE")){
                String att = str.split(" ")[1];
                attr.add(att);
            }
        }
        br.close();
        reader.close();
    }

//    Check all the attributes EXCEPT the class.
    public String checkAttribute(String tweet){
        String result = "";
        for(int i=0;i<attr.size()-1;i++){
            if(tweet.contains(attr.get(i))){
                result+="1,";
            }else {
                result+="0,";
            }
        }
        return result;
    }

    public String readAttributeFromArffForTweet(String path, String id) throws IOException {
        StringBuffer sb = new StringBuffer("");
        FileReader reader = new FileReader(path);
        BufferedReader br = new BufferedReader(reader);
        String str = null;
        while ((str = br.readLine()) != null) {
            if(str.contains(id)){
                return str.substring(0,str.length()-1);
            }
        }
        br.close();
        reader.close();
        return "";
    }


    public String checkIsBodyPart(String tweet){
        for(int i=0;i<BodyParts.size();i++){
            if(tweet.contains(BodyParts.get(i))){
                return "Y,";
            }
        }
        return "N,";
    }

    public String checkIsDrugName(String tweet){
        for(int i=0;i<DrugName.size();i++){
            if(tweet.contains(DrugName.get(i))){
                return "Y,";
            }
        }
        return "N,";
    }

    public String checkIsSymptom(String tweet){
        for(int i=0;i<Symptom.size();i++){
            if(tweet.contains(Symptom.get(i))){
                return "Y,";
            }
        }
        return "N,";
    }

    public String checkIsBodyDrug(String tweet){
        if(checkIsBodyPart(tweet).equals("Y,") && checkIsDrugName(tweet).equals("Y,")){
            return "Y,";
        }else {
            return "N,";
        }
    }
    public String checkIsBodySymptom(String tweet){
        if(checkIsBodyPart(tweet).equals("Y,") && checkIsSymptom(tweet).equals("Y,")){
            return "Y,";
        }else {
            return "N,";
        }
    }
    public String checkIsDrugSymptom(String tweet){
        if(checkIsDrugName(tweet).equals("Y,") && checkIsSymptom(tweet).equals("Y,")){
            return "Y,";
        }else {
            return "N,";
        }
    }
    public String checkIsDrugSymptomToTXT(String tweet){
        if(checkIsDrugName(tweet).equals("Y,") && checkIsSymptom(tweet).equals("Y,")){
            return "Y";
        }else {
            return "N";
        }
    }
    public String checkAll(String tweet){
        if(checkIsBodyPart(tweet).equals("Y,") && checkIsSymptom(tweet).equals("Y,") && checkIsSymptom(tweet).equals("Y,")){
            return "Y,";
        }else {
            return "N,";
        }
    }

//    Add body parts to the list.
    public void setBodyParts() throws IOException {
        StringBuffer sb = new StringBuffer("");
        FileReader reader = new FileReader("./data/attribute/body.txt");
        BufferedReader br = new BufferedReader(reader);
        String str = null;
        while ((str = br.readLine()) != null) {
            if(str.length()>1){
                BodyParts.add(str);
            }
        }
        br.close();
        reader.close();
        System.out.println(BodyParts.size());
    }

    public void setDrugName() throws IOException {
        StringBuffer sb = new StringBuffer("");
        FileReader reader = new FileReader("./data/attribute/drug.txt");
        BufferedReader br = new BufferedReader(reader);
        String str = null;
        while ((str = br.readLine()) != null) {
            DrugName.add(str);
        }
        br.close();
        reader.close();
    }

    public void setSymptom() throws IOException {
        StringBuffer sb = new StringBuffer("");
        FileReader reader = new FileReader("./data/attribute/symptom.txt");
        BufferedReader br = new BufferedReader(reader);
        String str = null;
        while ((str = br.readLine()) != null) {
            Symptom.add(str.trim());
        }
        br.close();
        reader.close();
        System.out.println(Symptom.size());
    }

}
