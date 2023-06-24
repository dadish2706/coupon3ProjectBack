package com.Coupons.Controllers;
import com.Coupons.Entities.MySession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.HashMap;

@Service
public class CheckService implements Runnable{

        @Autowired
        HashMap<String, MySession> sessions;


        @Override
        public void run() {
            while (true) {
                try {
                    checkTokens();
                    Thread.sleep(1000 * 60);
                } catch (InterruptedException Ignored) {
                }
            }
        }

        public void checkTokens(){
            Calendar calendar=Calendar.getInstance();
            sessions.values().removeIf(s -> calendar.getTimeInMillis() -s.getCalendar().getTimeInMillis()>= 1000*60*30);
}
    }

