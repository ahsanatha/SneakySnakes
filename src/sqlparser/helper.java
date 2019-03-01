/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sqlparser;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author TSR
 */
public class helper {
    List<String> tokenizer(String query){
        List<String> al = new ArrayList<String>();
        for(char ch: query.toCharArray()){
            System.out.println(ch);
        }
        return al;
    }
}
