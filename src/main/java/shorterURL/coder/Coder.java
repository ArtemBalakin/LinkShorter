package shorterURL.coder;


import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Component;


@Component
public class Coder {
    static int hashLength = 5;

    public String randomString() {
        return RandomStringUtils.randomAlphanumeric(hashLength);
    }


}
