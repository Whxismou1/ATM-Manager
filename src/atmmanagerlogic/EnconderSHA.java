/***
 * @author Whxismou
 * 
 *Clase encargada de encriptar la contraseña en SHA-256 antes de
 *almacenarla en la BBDD
 * 
 */

package atmmanagerlogic;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class EnconderSHA {

    /***
     * 
     * @param userPassword: Contraseña introducida por el usuario la cual hay que
     *                      encriptar
     * @return passwordHashed: Contraseña encriptada para guardarla en la BBDD
     * @throws NoSuchAlgorithmException
     */
    protected String encode(String userCredential) throws NoSuchAlgorithmException {

        MessageDigest md = MessageDigest.getInstance("SHA-256");

        byte[] password2Bytes = userCredential.getBytes();

        byte[] password2Hash = md.digest(password2Bytes);

        StringBuilder passwordHashed = new StringBuilder();

        for (byte elem : password2Hash) {
            passwordHashed.append(elem);
        }

        return passwordHashed.toString();
    }

}
