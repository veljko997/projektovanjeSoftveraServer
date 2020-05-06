/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package staticFactory;

import domain.Confederation;

/**
 *
 * @author Veljko
 */
public class ConfederationFactory {
    
     public static Confederation createConfederation(String confederationName) {
            if(confederationName.equals("AFRICA")) return Confederation.AFRICA;
            if(confederationName.equals("EUROPE")) return Confederation.EUROPE;
            if(confederationName.equals("SOUTHAMERICA")) return Confederation.SOUTHAMERICA;
            if(confederationName.equals("NORTHAMERICA")) return Confederation.NORTHAMERICA;
            if(confederationName.equals("OCEANIA")) return Confederation.OCEANIA;
            if(confederationName.equals("ASIA")) return Confederation.ASIA;
            return null;
    }
}
