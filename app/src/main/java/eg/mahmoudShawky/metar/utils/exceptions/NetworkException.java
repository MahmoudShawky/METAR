package eg.mahmoudShawky.metar.utils.exceptions;

import java.io.IOException;

/***
 * @author mahmoud.shawky
 *
 * To be thrown in case of no connection
 */
public class NetworkException extends IOException {
    public NetworkException() {
        super("No active internet connection");
    }

    NetworkException(String s) {
        super(s);
    }

    NetworkException(Exception e) {
        super(e);
    }
}
