/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tubessbd;

import java.io.IOException;


/**
 *
 * @author ASUS
 */

public class view1 {
    getData data = new getData();
    public void BFRandFR() throws IOException{   
        int B = data.getB();
        int P = data.getP();
        int Rm = data.getRmovie();
        int Vm = data.getVmovie();
        String tableName1 = data.getTbName1();
        
        double bfr1 = countBfr(B,Rm);
        double fr1 = countFr(B, Vm, P);
        System.out.println("   BFR "+tableName1+" : "+bfr1);
        System.out.println("   Fanout Ratio "+tableName1+" : "+fr1);
        
        int Ru = data.getRuser();
        int Vu = data.getVuser();
        String tableName2 = data.getTbName2();
        
        double bfr2 = countBfr(B,Ru);
        double fr2 = countFr(B, Vu, P);
        System.out.println("   BFR "+tableName2+" : "+bfr2);
        System.out.println("   Fanout Ratio "+tableName2+" : "+fr2);

        int Rum = data.getRuserMovie();
        int Vum = data.getVuserMovie();
        String tableName3 = data.getTbName3();
        
        double bfr3 = countBfr(B,Rum);
        double fr3 = countFr(B, Vum, P);
        System.out.println("   BFR "+tableName3+" : "+bfr3);
        System.out.println("   Fanout Ratio "+tableName3+" : "+fr3);
    }
    
    double countBfr(int B, int R){
        double bfr = (B/R);
        return bfr;        
    }
    
    double bfrMovie() throws IOException{
        int B = data.getB();
        int R1 = data.getRmovie();        
        double bfr = countBfr(B,R1);
        return bfr;
    }
    
    double bfrUser() throws IOException{
        int B = data.getB();
        int R2 = data.getRmovie();        
        double bfr2 = countBfr(B,R2);
        return bfr2;
    }
    
    double bfrUserMovie() throws IOException{
        int B = data.getB();
        int R3 = data.getRuserMovie();
        double bfr = countBfr(B,R3);
        return bfr;
    }
    
    double countFr(int B, int V, int P){
        int fr = B / (V+P);
        return fr;
    }
    
    double yMovie () throws IOException{
        //ambilData a = new ambilData();
        int B = data.getB();
        int P = data.getP();
        int v1 = data.getVmovie();
        double fr1 = countFr(B, v1, P);
        return fr1;
    }
    
    double yUser () throws IOException {
        int B = data.getB();
        int P = data.getP(); 
        int r2 = data.getRuser();
        int v2 = data.getVuser();
        double fr2 = countFr(B, v2, P);
        return fr2;
    }
    
    double yUserMovie() throws IOException{
        int B = data.getB();
        int P = data.getP();
        int r3 = data.getRuserMovie();
        int v3 = data.getVuserMovie();
        double fr3 = countFr(B, v3, P);
        return fr3;
    }
    }