package com.Coupons.Entities;

import com.Coupons.Services.ClientService;

import java.util.Calendar;


public class MySession {
    private ClientService clientService;
   private Calendar calendar;

    public MySession(ClientService clientService, Calendar calendar) {
        this.clientService = clientService;
        this.calendar = calendar;
    }

    public ClientService getClientService() {
        return clientService;
    }

    public void setClientService(ClientService clientService) {
        this.clientService = clientService;
    }

    public Calendar getCalendar() {
        return calendar;
    }

    public void setCalendar(Calendar calendar) {
        this.calendar = calendar;
    }
}
