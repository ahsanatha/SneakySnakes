/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sqlparser;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Muhammad Ahsan Athallah
 */
public class Sqlparser {
    public static boolean status = true;
    public static String error = "";
    public static String tabelUtama = "";
    public static List<String> coloumn = new ArrayList<>();
    
    public static void main(String[] args) {
        List<List<String>> db = bukaFile("db.txt"); // membuka file
        //System.out.println(db.get(0).get(0));
        String a = "select id_mov,title from movies join usermovies on (movies.id_mov=usermovies.id_mov);";//query. nanti di bikin jadi inputan
        cekSyntax(a); // cek sintak
        if (status) {
            System.out.println("query jalan");
        }else{
            System.out.println(error);
        }
        
    }
    
    private static void cekSyntax(String query){
        List<String> tokens = tokenizer(query.toLowerCase(),' '); // bikin query jadi hufuf kecil semua trus dipotong potong berdasarkan spasi
        char[] x = tokens.get(tokens.size()-1).toCharArray();
        if(x[x.length-1] != ';'){ // cek udah ada ; belum
            status = false;
            error = "Syntax Error : ';' expected";
        }else{
            List<String> nrwo = stopWordRemoval(tokens); // not reserved word (bukan select,from,dll)
            List<String> coloumn = tokenizer(nrwo.get(0),','); //potong kumpulan namakolom jadi list. dipisahkan pake koma
            if(coloumn.contains("")){ // kalo ada koma tapi ga ada nama kolomnya, error
                status = false;
                error = "Syntax Error : column name expected after ','";
            }

        }
        
    }
    

    private static List<String> tokenizer(String query,char div) {
        List<String> al = new ArrayList<>();
        String token = "";
        for(char ch: query.toCharArray()){
            if (ch == div){
                al.add(token);
                //System.out.println(token);
                token = "";
            }else{
                token += ch;
            }
        }
        al.add(token);
        //System.out.println(token);
        return al;
    }

    private static List<String> stopWordRemoval(List<String> tokens) {
        int order = 10;
        List<String> al = new ArrayList<>();
        List<String> sw = new ArrayList<>();
        sw.add("select");
        sw.add("from");
        sw.add("join");
        sw.add("on");
        for(String word : tokens){
            if(sw.contains(word)){
                if ((order > sw.indexOf(word))&&(order != 10)){
                    //System.out.println(sw.indexOf(word));
                    status = false;
                    error = "Syntax error : Wrong order";
                }else{
                    order = sw.indexOf(word);
                }
            }else{
                al.add(word);
            }
        }
        return al;
    }

    private static List<List<String>> bukaFile(String string) {
        String fileName = string;
        String line = null;
        List<List<String>> db = new ArrayList<>();
    try {
        FileReader fileReader = 
            new FileReader(fileName);
        BufferedReader bufferedReader = 
            new BufferedReader(fileReader);

        while((line = bufferedReader.readLine()) != null) {
            //System.out.println(line);
            List<String> col = tokenizer(line,';');
            db.add(col);
        }  
        bufferedReader.close();         
    }
    catch(FileNotFoundException ex) {
        System.out.println(
            "Unable to open file '" + 
            fileName + "'");                
    }
    catch(IOException ex) {
        System.out.println(
            "Error reading file '" 
            + fileName + "'");                  
        // Or we could just do this: 
        // ex.printStackTrace();
      }
    return db;
   }
}

