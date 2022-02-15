package shorterURL.validator;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

@Component
public class URLValidator {

    public boolean checkURL(String address) {
        try {
            URL url = new URL(address);
            URLConnection conn = url.openConnection();
            conn.connect();
        } catch (IOException e) {
            return false;
        }
        return true;
    }
}
