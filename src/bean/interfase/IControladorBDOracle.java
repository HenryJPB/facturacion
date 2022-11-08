/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean.interfase;

import java.sql.Connection;

/**
 *
 * @author henrypb
 */
public interface IControladorBDOracle {
    
    public abstract Connection getConeccionOracle();  
    
}
