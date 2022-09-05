/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package templates;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import org.apache.commons.codec.digest.DigestUtils;

/**
 *
 * @author Familia
 */
public class vistaTemplate {
           
    public Icon iconoExcepcion(String tipoError){
        
        if(tipoError.equals("error")){
            Icon n = new ImageIcon(getClass().getResource("/img/message/error.png"));
            return n;
        }else if(tipoError.equals("success")){
            Icon n = new ImageIcon(getClass().getResource("/img/message/success.png"));
            return n;
        }else if(tipoError.equals("pregunta")){
            Icon n = new ImageIcon(getClass().getResource("/img/message/pregunta.png"));
            return n;
        }
        
        return null;
        
    }
    
    public String encriptarContraseña(String password){
        String encriptMD5 = DigestUtils.md5Hex(password);
        return encriptMD5;
    }
    
}
