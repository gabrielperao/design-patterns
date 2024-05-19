package com.example.designpatterns.module;

import com.example.designpatterns.business.facade.RoomBookingFacade;
import com.example.designpatterns.business.facade.impl.RoomBookingFacadeImpl;
import com.example.designpatterns.business.manager.InvoiceManager;
import com.example.designpatterns.business.manager.NotificationManager;
import com.example.designpatterns.business.manager.RefundManager;
import com.example.designpatterns.business.manager.ReservationManager;
import com.example.designpatterns.business.manager.impl.InvoiceManagerImpl;
import com.example.designpatterns.business.manager.impl.NotificationManagerImpl;
import com.example.designpatterns.business.manager.impl.RefundMangerImpl;
import com.example.designpatterns.business.manager.impl.ReservationManagerImpl;
import com.google.inject.AbstractModule;
import com.google.inject.Singleton;

public class AppModule extends AbstractModule {
    @Override
    protected void configure() {
        // Binding for managers
        bind(InvoiceManager.class).to(InvoiceManagerImpl.class).in(Singleton.class);
        bind(RefundManager.class).to(RefundMangerImpl.class).in(Singleton.class);
        bind(NotificationManager.class).to(NotificationManagerImpl.class).in(Singleton.class);
        bind(ReservationManager.class).to(ReservationManagerImpl.class).in(Singleton.class);

        // Binding for facades
        bind(RoomBookingFacade.class).to(RoomBookingFacadeImpl.class).in(Singleton.class);
    }
}
