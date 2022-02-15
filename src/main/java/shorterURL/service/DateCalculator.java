package shorterURL.service;


import org.springframework.stereotype.Component;

import java.sql.Timestamp;
@Component
public class DateCalculator {
    public long NumberOfMillisecondsUntilToday(Timestamp startDate) {
        return new Timestamp(System.currentTimeMillis()).getTime() - startDate.getTime();
    }
}



