/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tubessbd;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;



/**
 *
 * @author ASUS
 */
public class getData {
   
    int getP() throws IOException{
        try {
            BufferedReader br = new BufferedReader(new FileReader("db.txt"));
            String line1 = br.readLine();
            String[] a1 = line1.split("\\t|=|;|#");
            int indexP = 0;
            for (int i = 0; i < a1.length; i++) {
                if(a1[i].equalsIgnoreCase("P")){
                    indexP = i;
                }
                
            }
            int nilaiP = Integer.parseInt(a1[indexP+1]);
            return nilaiP;  
        } 
        catch (FileNotFoundException ex) {
           Logger.getLogger(main.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }
    
    int getB() throws IOException{
        try {
            BufferedReader br = new BufferedReader(new FileReader("db.txt"));
            String line1 = br.readLine();
            String[] a1 = line1.split("\\t|=|;|#");
            int indexB = 0;
            for (int i = 0; i < a1.length; i++) {
                if(a1[i].equalsIgnoreCase("B")){
                    indexB = i;
                }
                
            }
            int nilaiB = Integer.parseInt(a1[indexB+1]);
            return nilaiB;  
        } 
        catch (FileNotFoundException ex) {
           Logger.getLogger(main.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }
    
    String getTbName1() throws IOException{ //get movies
        try {
            BufferedReader br = new BufferedReader(new FileReader("db.txt"));
            String line1 = br.readLine();
            String line2 = br.readLine();
            String[] p = line2.split(";");            
            String tn1 = p[0]; //table name 1
            return tn1;
        } 
        catch (FileNotFoundException ex) {
           Logger.getLogger(main.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    String getTbName2() throws IOException{ // get user
        try {
            BufferedReader br = new BufferedReader(new FileReader("db.txt"));
            String line1 = br.readLine();
            String line2 = br.readLine();
            String line3 = br.readLine();
            String[] q = line3.split(";");
            String tn2 = q[0]; 
            return tn2;
        } 
        catch (FileNotFoundException ex) {
           Logger.getLogger(main.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    String getTbName3() throws IOException{ //get usermovie
        try {
            BufferedReader br = new BufferedReader(new FileReader("db.txt"));
            String line1 = br.readLine();
            String line2 = br.readLine();
            String line3 = br.readLine();
            String line4 = br.readLine();
            String[] r = line4.split(";");
            String tn3 = r[0]; 
            return tn3;
        } 
        catch (FileNotFoundException ex) {
           Logger.getLogger(main.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    int getRmovie() throws IOException{
        try {
            BufferedReader br = new BufferedReader(new FileReader("db.txt"));
            String line1 = br.readLine();
            String line2 = br.readLine(); 
            String[] r1 = line2.split("\\t|=|;"); // R pada table 1 (movie) , p[2]=8
            int indexR = 0;
            for (int i = 0; i < r1.length; i++) {
                if(r1[i].equalsIgnoreCase("R")){
                    indexR = i;
                }
            }
            int nilaiR = Integer.parseInt(r1[indexR+1]);
            return nilaiR;
        } 
        catch (FileNotFoundException ex) {
           Logger.getLogger(main.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }
    
    int getNmovie() throws IOException{
        try {
            BufferedReader br = new BufferedReader(new FileReader("db.txt"));
            String line1 = br.readLine();
            String line2 = br.readLine();
            String[] n1 = line2.split("\\t|=|;");
            int indexN = 0;
            for (int i = 0; i < n1.length; i++) {
                if(n1[i].equalsIgnoreCase("N")){
                    indexN = i;
                }
            }
            int nilaiN = Integer.parseInt(n1[indexN+1]);
            return nilaiN;
        } 
        catch (FileNotFoundException ex) {
           Logger.getLogger(main.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }
    
    int getVmovie() throws IOException{
        try {
            BufferedReader br = new BufferedReader(new FileReader("db.txt"));
            String line1 = br.readLine();
            String line2 = br.readLine();
            String[] v1 = line2.split("\\t|=|;|#");
            int indexV = 0;
            for (int i = 0; i < v1.length; i++) {
                if(v1[i].equalsIgnoreCase("V")){
                    indexV = i;
                }
            }
            int nilaiV = Integer.parseInt(v1[indexV+1]);
            return nilaiV;
        } 
        catch (FileNotFoundException ex) {
           Logger.getLogger(main.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }
    
    int getRuser() throws IOException{
        try {
            BufferedReader br = new BufferedReader(new FileReader("db.txt"));
            String line1 = br.readLine();
            String line2 = br.readLine();
            String line3 = br.readLine();
            String[] RUser = line3.split("\\t|=|;");
            int indexR = 0;
            for (int i = 0; i < RUser.length; i++) {
                if(RUser[i].equalsIgnoreCase("R")){
                    indexR = i;
                }
            }
            int nilaiR = Integer.parseInt(RUser[indexR+1]);
            return nilaiR;
        } 
        catch (FileNotFoundException ex) {
           Logger.getLogger(main.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }
    
    int getNuser() throws IOException{
        try {
            BufferedReader br = new BufferedReader(new FileReader("db.txt"));
            String line1 = br.readLine();
            String line2 = br.readLine();
            String line3 = br.readLine();
            String[] NUser = line3.split("\\t|=|;");
            int indexN = 0;
            for (int i = 0; i < NUser.length; i++) {
                if(NUser[i].equalsIgnoreCase("N")){
                    indexN = i;
                }
            }
            int nilaiN = Integer.parseInt(NUser[indexN+1]);
            return nilaiN;
        } 
        catch (FileNotFoundException ex) {
           Logger.getLogger(main.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }
    
    int getVuser() throws IOException{
        try {
            BufferedReader br = new BufferedReader(new FileReader("db.txt"));
            String line1 = br.readLine();
            String line2 = br.readLine();
            String line3 = br.readLine();
            String[] VUser = line3.split("\\t|=|;|#");
            int indexV = 0;
            for (int i = 0; i < VUser.length; i++) {
                if(VUser[i].equalsIgnoreCase("V")){
                    indexV = i;
                }
            }
            int nilaiV = Integer.parseInt(VUser[indexV+1]);
            return nilaiV;
        } 
        catch (FileNotFoundException ex) {
           Logger.getLogger(main.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }
    
    int getRuserMovie() throws IOException{
        try {
            BufferedReader br = new BufferedReader(new FileReader("db.txt"));
            String line1 = br.readLine();
            String line2 = br.readLine();
            String line3 = br.readLine();
            String line4 = br.readLine();
            String[] r = line4.split("\\t|=|;");
            int indexR = 0;
            for (int i = 0; i < r.length; i++) {
                if(r[i].equalsIgnoreCase("R")){
                    indexR = i;
                }
            }
            int nilaiR = Integer.parseInt(r[indexR+1]);
            return nilaiR;
        } 
        catch (FileNotFoundException ex) {
           Logger.getLogger(main.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }
    
    int getNuserMovie() throws IOException{
        try {
            BufferedReader br = new BufferedReader(new FileReader("db.txt"));
            String line1 = br.readLine();
            String line2 = br.readLine();
            String line3 = br.readLine();
            String line4 = br.readLine();
            String[] n = line4.split("\\t|=|;");
            int indexN = 0;
            for (int i = 0; i < n.length; i++) {
                if(n[i].equalsIgnoreCase("N")){
                    indexN = i;
                }
            }
            int nilaiN = Integer.parseInt(n[indexN+1]);
            return nilaiN;
        } 
        catch (FileNotFoundException ex) {
           Logger.getLogger(main.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }
    
    int getVuserMovie() throws IOException{
        try {
            BufferedReader br = new BufferedReader(new FileReader("db.txt"));
            String line1 = br.readLine();
            String line2 = br.readLine();
            String line3 = br.readLine();
            String line4 = br.readLine();
            String[] v = line4.split("\\t|=|;|#");
            int indexV = 0;
            for (int i = 0; i < v.length; i++) {
                if(v[i].equalsIgnoreCase("V")){
                    indexV = i;
                }
            }
            int nilaiV = Integer.parseInt(v[indexV+1]);
            return nilaiV;
        } 
        catch (FileNotFoundException ex) {
           Logger.getLogger(main.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }    
    String[] getAttMovies() throws IOException{
        System.out.println("called");
        BufferedReader readData = new BufferedReader(new FileReader("db.txt"));
        String bar1 = readData.readLine();
        String bar2 = readData.readLine();
        String[] p = bar2.split(";");
        String col = p[1];
        String[] att = col.split(",");
        String[] kolom = new String[att.length];
        for (int i = 0; i<att.length; i++){
            kolom[i] = att[i];
        }
        return kolom; //mengembalikan alamat array kolom pembeli
    }
    String[] getAttUser() throws IOException{
        BufferedReader readData = new BufferedReader(new FileReader("db.txt"));
        String bar1 = readData.readLine();
        String bar2 = readData.readLine();        
        String bar3 = readData.readLine();
        String[] p = bar3.split(";");
        String col = p[1];
        String[] a = col.split(",");
        String[] kolom = new String[a.length];
        for (int i = 0; i<a.length; i++){
            kolom[i] = a[i];
        }
        return kolom; //mengembalikan alamat array kolom pembeli
    }
    String[] getAttUserMovie() throws IOException{
        BufferedReader readData = new BufferedReader(new FileReader("db.txt"));
        String bar1 = readData.readLine();
        String bar2 = readData.readLine();        
        String bar3 = readData.readLine();
        String bar4 = readData.readLine();
        String[] p = bar4.split(";");
        String col = p[1];
        String[] a = col.split(",");
        String[] kolom = new String[a.length];
        for (int i = 0; i<a.length; i++){
            kolom[i] = a[i];
        }
        return kolom; //mengembalikan alamat array kolom pembeli
    }
   
}

 