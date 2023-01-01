package com.example.fileprotection.Utils;

import java.io.*;

public class FileUtil {
    public String readFile(File file) throws IOException {


        FileInputStream fis=new FileInputStream(file);
        InputStreamReader isr=new InputStreamReader(fis,"UTF-8");
        BufferedReader bufferedReader=new BufferedReader(isr);
        String content="";


        StringBuilder sb = new StringBuilder();
        String line;
        int count=0;
        while(( line = bufferedReader.readLine()) != null ) {
            sb.append( line );
            sb.append( '\n' );
            count++;
            System.out.println(count);
        }

        content=sb.toString();
//        return sb.toString();
//        String currentLine=bufferedReader.readLine();
//        while(currentLine!=null){
//            content+=currentLine+'\n';
//
//            currentLine= bufferedReader.readLine();
//        }
        bufferedReader.close();
        isr.close();
        fis.close();

        return content;
    }

    public void writeFile(String data,File file) throws IOException {
        FileWriter fileWriter=new FileWriter(file);
        BufferedWriter bufferedWriter=new BufferedWriter(fileWriter);
        bufferedWriter.write(data);
        bufferedWriter.flush();
        fileWriter.close();
    }
}
