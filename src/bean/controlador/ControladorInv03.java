/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean.controlador;

import bean.entidad.Inv04Dat;
import bean.interfase.IControladorInv03;
import bean.utilitario.libreria.LibreriaHP;

/**
 *
 * @author henrypb
 */
public class ControladorInv03 implements IControladorInv03 {

    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    @Override
    public String descripProdEspecial(Inv04Dat inv04Dat) {
        String cadena = null;
        LibreriaHP libHP = new LibreriaHP(); 
        cadena = libHP.formatoDecimal.format(inv04Dat.getC4Taml())+"/"+libHP.formatoDecimal.format(inv04Dat.getC4Tamt())+" "; 
        cadena = cadena+libHP.formatoDecimal.format(inv04Dat.getC4Sepl())+" x" + libHP.formatoDecimal.format(inv04Dat.getC4Sept())+" "; 
        cadena = cadena+libHP.formatoDecimal.format(inv04Dat.getC4Diaml())+"/"+libHP.formatoDecimal.format(inv04Dat.getC4Diamt()); 
        return(cadena); 
    }  // descripProdEspecial(). 
    
}
