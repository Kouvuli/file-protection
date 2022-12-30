package com.example.fileprotection.Utils;

import java.sql.Timestamp;
import java.util.Random;

public class CryptoUtil {
    public String encyptData(String key,Timestamp ts, String data){
        int randPos=genNumberic(1,10);
        String dataPos=String.valueOf(randPos);
        int lengthPos= randPos+data.length()+genNumberic(0,10);
        String length=String.valueOf(data.length());
        int lengthSize = (int) (Math.log10(data.length()) + 1);
        int lengthPosSize = (int) (Math.log10(lengthPos) + 1);
        char b= (char) ('a'+lengthSize);
        char c=(char) ('a'+lengthPosSize);
        String k1=new StringBuilder("a").append(b).toString();
        String k2=new StringBuilder("a").append(c).toString();
//        String postData=dataPos+genRandomAlphaNumeric(Integer.parseInt(dataPos)-2);
//        data=postData+data;
        String encData=genRandomAlphaNumeric(15);

        encData=insertString(encData,data,randPos-1);
        encData=insertString(encData,k1+length,lengthPos-1);
        encData=dataPos+k2+lengthPos+encData;
        int i=0;
        long k=getEcryptKey(key,ts);
        String result="";


        while (i<encData.length()){
            if(encData.charAt(i)!='\n'){

                int temp= (int) (encData.charAt(i)+k);
                temp=temp<<2;
                result+=(char)temp;
            }
            else{
                result+=encData.charAt(i);
            }
            i++;
        }
        return result;
    }

    public String decryptData(String key,Timestamp ts,String data){



        long k=getEcryptKey(key,ts);
        String result="";
        int i=0;
        while (i<data.length()){
            if(data.charAt(i)!='\n'){

                int temp=(int)data.charAt(i)>>2;
                temp-=k;
                result+=(char)temp;
            }else {
                result+=data.charAt(i);
            }
            i++;
        }
        int dataPos=Integer.parseInt(Character.toString(result.charAt(0)));
        int k2=(int)result.charAt(2)-(int)result.charAt(1);
        int lengthPos=Integer.parseInt(result.substring(3,3+k2));
        result=result.substring(3+k2);
        int k1=result.charAt(lengthPos+1)-result.charAt(lengthPos);
        int length = Integer.parseInt(result.substring(lengthPos + 2, lengthPos + 2+k1));

        result=result.substring(dataPos,dataPos+length);
        return result;
    }
//
//    public String encryptKey(String key) {
//        int position=Integer.parseInt(genNumberic(2));
//        int length=key.length();
//        String preString=null;
//
//        preString=genRandomAlphaNumeric(5);
//
//        preString= position +preString;
//
//        String postString=genAlpha(3)+ length;
//
//        return preString+key+postString;
//    }

    public Long getEcryptKey(String encKey, Timestamp createAt) {


        long key=  keyToNumber(encKey)+createAt.toInstant().getEpochSecond()%256;
        return key;
    }
    public String insertString(
            String originalString,
            String stringToBeInserted,
            int index)
    {

        // Create a new string
        String newString = new String();

        for (int i = 0; i < originalString.length(); i++) {

            // Insert the original string character
            // into the new string
            newString += originalString.charAt(i);

            if (i == index) {

                // Insert the string to be inserted
                // into the new string
                newString += stringToBeInserted;
            }
        }

        // return the modified String
        return newString;
    }
    public long keyToNumber(String key) {
        long sum=0;
        for (int i = 0; i < key.length(); i++) {
            sum+=(int) key.charAt(i);
        }
        return sum;
    }
    public String genRandomAlphaNumeric(int length){
        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'

        Random random = new Random();

        String generatedString = random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(length)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();

        return generatedString;
    }

    public int genNumberic(int min,int max){
        Random rand = new Random(); //instance of random class

        //generate random values from 0-24
        int int_random = rand.nextInt(min,max);


        return int_random;
    }
    public String genAlpha(int length){
        int leftLimit = 65; // 'A'
        int rightLimit = 112; // 'z'

        Random random = new Random();

        String generatedString = random.ints(leftLimit, rightLimit + 1)
                .filter(i-> (i <= 90 || i >= 97))
                .limit(length)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();

        return generatedString;
    }
}
