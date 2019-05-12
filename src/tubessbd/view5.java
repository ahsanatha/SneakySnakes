/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tubessbd;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author user
 */
public class view5 {
    String qep;
    
    public view5(){
        
    }
    
    public void view5() throws IOException{
        try {
            BufferedReader readData = new BufferedReader(new FileReader("sharedpool.txt"));
             int j = 1;
            int i = 1;
            String bar  = readData.readLine();;
           while (bar != null){
                 System.out.println(i+". "+bar);
                 bar = readData.readLine();
                 i++;
           }
            
            
        } 
        catch (FileNotFoundException ex) {
           Logger.getLogger(main.class.getName()).log(Level.SEVERE, null, ex);
        }        
    }
}
