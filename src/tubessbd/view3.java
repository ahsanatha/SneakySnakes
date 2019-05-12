/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tubessbd;

import java.io.IOException;
import java.util.Scanner;

/**
 *
 * @author user
 */
public class view3 {
    getData a = new getData();
    
    public view3(){
        
    }
    
    public void countBlock() throws IOException{
        System.out.print("Cari Rekord ke : ");
        Scanner scan = new Scanner(System.in);
        int ke = scan.nextInt();      
        Scanner scann = new Scanner(System.in);
        System.out.print("Nama Tabel : ");
        String nt = scann.nextLine();
        
        int B = a.getB();
        int P = a.getP();
        String tN1 = a.getTbName1();
        String tN2 = a.getTbName2();
        String tN3 = a.getTbName3();
       
       
        if (nt.equals(tN1)){
            int R1 = a.getRmovie();
            int V1 = a.getVmovie();
            int N1 = a.getNmovie();
            int TBD1 = TBData(N1, B, V1, R1);
            int JBD1 = JmlBlok(ke, B, R1);
            System.out.println("Menggunakan indeks, jumlah blok yang diakses : "+TBD1);
            System.out.println("Tanpa indeks, jumlah blok yang diakses : "+JBD1);
        } else if (nt.equals(tN2)){
            int R2 = a.getRuser();
            int V2 = a.getVuser();
            int N2 = a.getNuser();
            int TBD2 = TBData(N2, B, V2, R2);
            int JBD2 = JmlBlok(ke, B, R2);
            System.out.println("Menggunakan indeks, jumlah blok yang diakses : "+TBD2);
            System.out.println("Tanpa indeks, jumlah blok yang diakses : "+JBD2);           
        } else if (nt.equals(tN3)){
            int R3 = a.getRuserMovie();
            int V3 = a.getVuserMovie();
            int N3 = a.getNuserMovie();
            int TBD3 = TBData(N3, B, V3, R3);
            int JBD3 = JmlBlok(ke, B, R3);
            System.out.println("Menggunakan indeks, jumlah blok yang diakses : "+TBD3);
            System.out.println("Tanpa indeks, jumlah blok yang diakses : "+JBD3);   
        } else {
           System.out.println("Nama Tabel Tidak Ada");
        }
    }
    
    int TBData(int i, int B, int V,int R){ //Total Blok Data, i=posisi atau keberapa
        //with index
        view1 y = new view1();
        int temp = (int) y.countFr(B, V, R);
        int totalBlok = (i/temp);
        int sisa = i % temp;
        if (sisa == 0){
            return totalBlok;
        } else {
            return totalBlok+1;
        }
    }
    
    int JmlBlok (int i , int B, int R){ //Jumlah Blok yang diakses
        //without index
        view1 bfr = new view1();
        int temp = (int) bfr.countBfr(B, R);
        int JumBlok = (i/temp);
        int sisa = i % temp;
        if (sisa == 0){
            return JumBlok;
        } else {
            return JumBlok+1;
        }
    }
}
