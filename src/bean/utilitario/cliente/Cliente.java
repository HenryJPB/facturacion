/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bean.utilitario.cliente;

/**
 *
 * @author henrypb
 */
public class Cliente {
    
    private String codCliente; 
    private String nombreCliente;  

    public Cliente(String codCliente, String nombreCliente) {
        this.codCliente = codCliente;
        this.nombreCliente = nombreCliente;
    }
    
    public String getCodCliente() {
        return codCliente;
    }

    public void setCodCliente(String codCliente) {
        this.codCliente = codCliente;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }
    
}  // Cliente.  
