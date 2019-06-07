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
public class view2 {
    getData data = new getData();
    public void countDataIndex() throws IOException{
        //ambilData a = new ambilData();
        int B = data.getB();
        int P = data.getP();
        int R1 = data.getRmovie();
        int V1 = data.getVmovie();
        int n1 = data.getNmovie();
        String tableName1 = data.getTbName1();
        
        int countData1 = countData(n1, B, R1);
        int countIndex1 = countIndex(n1, B, V1, P);
        System.out.println("   Tabel Data "+tableName1+" : "+countData1);  
        System.out.println("   Index "+tableName1+" : "+countIndex1);
        
        int R2 = data.getRuser();
        int V2 = data.getVuser();
        int n2 = data.getNuser();
        String tableName2 = data.getTbName2();
        
        int countData2 = countData(n2, B, R2);
        int countIndex2 = countIndex(n2, B, V2, P);
        System.out.println("   Tabel Data "+tableName2+" : "+countData2);
        System.out.println("   Index "+tableName2+" : "+countIndex2);
            
        int R3 = data.getRuserMovie();
        int V3 = data.getVuserMovie();
        int n3 = data.getNuserMovie();
        String tableName3 = data.getTbName3();
        
        int countData3 = countData(n3, B, R3);
        int countIndex3 = countIndex(n3, B, V3, P);
        System.out.println("   Tabel Data "+tableName3+" : "+countData3);
        System.out.println("   Index "+tableName3+" : "+countIndex3);
    }
    
    int countData(int n, int  B, int R) throws IOException{
        view1 bfr = new view1();
        int a = (int) bfr.countBfr(B, R);
        int cData = (n/a); // hitung table data
        int sisa = n % a;
        if (sisa == 0){
            return cData;
        } else {
            return cData+1;
        }
        
    }
    
    int countIndex(int n, int B, int V, int P) throws IOException{
        view1 fr = new view1();
        int b = (int) Math.floor(fr.countFr(B, V, P));
        int cIndex = n/b;
        int sisa = n % b;
        if (sisa == 0){
            return cIndex;
        } else {
            return cIndex+1;
        }
    }
    
    double bMovie () throws IOException{
        int B = data.getB();
        int R = data.getRmovie();
        int n = data.getNmovie();
        double b = countData(n, B, R);
        return b;
    }
    
    double bUser () throws IOException{
        int B = data.getB();
        int R = data.getRuser();
        int n = data.getNuser();
        double b = countData(n, B, R);
        return b;
    }
    
    double bUserMovie () throws IOException{
        int B = data.getB();
        int R = data.getRuserMovie();
        int n = data.getNuserMovie();
        double b = countData(n, B, R);
        return b;
    }
    
    

}
