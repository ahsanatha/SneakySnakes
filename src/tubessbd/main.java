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
 * @author ASUS
 */
public class main {     

    private static Object dua;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        // TODO code application logic here
        System.out.println(">>>Menu Utama<<");
        System.out.println("1. Tampilkan BFR dan Fanout Ratio Setiap Tabel");
        System.out.println("2. Tampilkan Total Blok Data + Blok Index Setiap Tabel");
        System.out.println("3. Tampilkan Jumlah Blok yang Diakses Untuk Pencarian Rekord");
        System.out.println("4. Tampilkan QEP dan Cost");
        System.out.println("5. Tampilkan Isi File Shared Pool");
        System.out.println(">>> PIlih : ");
        
        Scanner scan = new Scanner(System.in);
        int pil = scan.nextInt();
            if (pil == 1){
                System.out.println("Menu 1 : BFR dan Fanout Ratio");
                view1 satu = new view1();
                satu.BFRandFR();
            }
           else if(pil==2){
                System.out.println("Menu 2 : Total Blok dan Blok Index");
                view2 dua = new view2();
                dua.countDataIndex();
            }
            else if (pil == 3){
                System.out.println("Menu 3 : Pencarian Record");
                view3 tiga = new view3();
                tiga.countBlock();
            }
            else if (pil == 4){
                System.out.println("Menu 4 : Tampilkan QEP dan Cost");
                view4 empat = new view4();
                empat.doMainView4();
//                String a = "halohalobandung".contains('halo');
//                System.out.println(a);

            }
            else if (pil== 5){
                System.out.println("Menu 5 : Share Pool");
                view5 lima = new view5();
                lima.view5();
            }
            System.out.println("__________________________________________");
        }
    }
