package eg.mahmoudShawky.metar.utils.exceptions;

import java.io.IOException;

public class NetworkException extends IOException {
    public NetworkException() {
        super("No internet available");
    }

    NetworkException(String s) {
        super(s);
    }

    NetworkException(Exception e) {
        super(e);
    }
}
