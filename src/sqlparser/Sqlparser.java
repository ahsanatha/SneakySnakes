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
 * @author DAFA
 */
public class Sqlparser {

    public static boolean status = true;
    public static String error = "";
    public static String tabelUtama = "";
    public static List<String> coloumn;
    public static List<List<String>> db;
    public static List<String> db_table_name = new ArrayList<>();
    public static List<String> tables = new ArrayList<>();

    public static void main(String[] args) {
        db = bukaFile("db.txt"); // membuka file
        //System.out.println(db.get(0).get(0));
        String query = "select id_movie,title,id_user from movies join usermovies on (movies.id_movie=usermovies.id_movie);";//query. nanti di bikin jadi inputan
        cekSyntax(query); // cek sintak
        if (status) {
            //System.out.println("==> query jalan");
            tampilOutput();
            for (String col : coloumn) {
                // System.out.println(col);
            }
        } else {
            System.out.println(error);
        }

    }

    private static void tampilOutput() {
        int idx;
        System.out.println("============================================================");
        for (int i = -1; i < tables.size(); i++) {
            System.out.print("Tabel " + (i + 2) + " : ");
            if (i == -1) {
                System.out.println(tabelUtama);
            } else {
                System.out.println(tables.get(i));
            }
            System.out.print("List Kolom : ");
            for (String col : coloumn) {
                if (i == -1) {
                    idx = db_table_name.indexOf(tabelUtama);
                } else {
                    idx = db_table_name.indexOf(tables.get(i));
                }

                if (idx != -1) {
                    if (db.get(idx).contains(col)) {
                        System.out.print(col + ", ");
                    }
                }
            }
            System.out.println("");

        }

    }

    private static void cekSyntax(String query) {
        List<String> tokens = tokenizer(query.toLowerCase(), ' '); // bikin query jadi hufuf kecil semua trus dipotong potong berdasarkan spasi
        char[] x = tokens.get(tokens.size() - 1).toCharArray();
        if (x[x.length - 1] != ';') { // cek udah ada ; belum
            status = false;
            error = "Syntax Error : ';' expected";
        } else {
            List<String> nrwo = stopWordRemoval(tokens); // not reserved word (bukan select,from,dll)
            coloumn = tokenizer(nrwo.get(0), ','); //potong kumpulan namakolom jadi list. dipisahkan pake koma
            if (coloumn.contains("")) { // kalo ada koma tapi ga ada nama kolomnya, error
                status = false;
                error = "Syntax Error : column name expected after ','";
            }
            tabelUtama = nrwo.get(1);
            for (int i = 2; i < nrwo.size(); i++) {
                for (int j = 0; j < db.size(); j++) {
                    if (db.get(j).get(0).contains(nrwo.get(i))) {
                        tables.add(nrwo.get(i));
                    }
                }

            }
        }

    }

    private static List<String> tokenizer(String query, char div) {
        List<String> al = new ArrayList<>();
        String token = "";
        for (char ch : query.toCharArray()) {
            if (ch == div) {
                al.add(token);
                token = "";
            } else {
                token += ch;
            }
        }
        al.add(token);
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
        for (String word : tokens) {
            if (sw.contains(word)) {
                if ((order > sw.indexOf(word)) && (order != 10)) {
                    //System.out.println(sw.indexOf(word));
                    status = false;
                    error = "Syntax error : Wrong order";
                } else {
                    if(order >= 2){
                        order = sw.indexOf(word) - 2;
                    }else{
                        order = sw.indexOf(word);
                    }

                }
            } else {
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
            FileReader fileReader
                    = new FileReader(fileName);
            BufferedReader bufferedReader
                    = new BufferedReader(fileReader);

            while ((line = bufferedReader.readLine()) != null) {
                //System.out.println(line);
                List<String> col = tokenizer(line, ';');
                db.add(col);
                db_table_name.add(col.get(0));
            }
            bufferedReader.close();
        } catch (FileNotFoundException ex) {
            System.out.println(
                    "Unable to open file '"
                    + fileName + "'");
        } catch (IOException ex) {
            System.out.println(
                    "Error reading file '"
                    + fileName + "'");
            // Or we could just do this: 
            // ex.printStackTrace();
        }
        return db;
    }

}
