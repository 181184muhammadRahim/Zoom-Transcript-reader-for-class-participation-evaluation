package com.company;
import java.awt.*;
import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner; // Import the Scanner class to read text files
import java.util.stream.Collectors;
import java.io.FileWriter;
import java.io.IOException;
public class Main {

    public static void main(String[] args) {
        try {
            List<String> names=new ArrayList<String>();
            Scanner sc= new Scanner(System.in); //System.in is a standard input stream.
            System.out.print("Enter file path: ");
            String path= sc.nextLine(); //reads string.
            System.out.print("Enter file name ");
            String filename=sc.nextLine();
            File myObj = new File(path+"/"+filename);
            var count=0;
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                count++;
            }
            //count-=1;
            myReader.close();
            myObj = new File(path+"/"+filename);
            myReader = new Scanner(myObj);
            while (count!=0) {
                String data = myReader.nextLine();
                if((data.charAt(0)<'0' || data.charAt(0)>'9')){
                    String data1=names.get(names.size()-1);
                    data1=data1+data;
                    names.remove(names.size()-1);
                    names.add(data1);
                    count--;
                }else if(data.contains("From")==false){
                    String data1=names.get(names.size()-1);
                    data1=data1+data;
                    names.remove(names.size()-1);
                    names.add(data1);
                    count--;
                }
                else{
                int start=data.indexOf("m");
                start+=1;
                data=data.substring(start,data.length());
                names.add(data);
                count--;}
            }
            names.sort(Comparator.naturalOrder());
            count=1;
            List<Float> counts=new ArrayList<Float>();
            Boolean last=false;
            for(int i=0;i< names.size()-1;i++){
               String data=names.get(i).substring(0,names.get(i).indexOf(":"));
               if(names.get(i+1).indexOf(":")==-1){
                   System.out.print("Error:"+names.get(i+1));
                }
               String data1=names.get(i+1).substring(0,names.get(i+1).indexOf(":"));
               if(data.compareTo(data1)==0){
                   count++;
                   if(i==names.size()-2){
                       counts.add((float)count);
                   }
               }else{
                   counts.add((float)count);
                   count=1;
                   if(i== names.size()-2){
                       last=true;
                       counts.add((float)1.0);
                   }
               }
            }
            try {
                FileWriter myWriter = new FileWriter(path+"/chat_output.txt");
                int size=0;
                int sum=0;
                sum=sum + Math.round(counts.get(0));;
                Boolean flag=false;
                int counts_size=0;
                if(last==false){
                    counts_size=counts.size();
                }else{
                    counts_size=counts.size()+1;
                }
                for(int i=0;i< names.size();i++){
                    myWriter.write(names.get(i));
                    if(flag && size<counts_size) {
                        sum = sum + Math.round(counts.get(size));
                        flag=false;
                    }
                    if(i==(sum-1)){
                    myWriter.write("   ");

                    myWriter.write(String.valueOf(counts.get(size)));
                    size++;
                    flag=true;
                    myWriter.write('\n');
                    myWriter.write('\n');
                    }
                    myWriter.write('\n');
                }
                myWriter.close();
                System.out.println("Successfully wrote to the file.");
            } catch (IOException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();}
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}
