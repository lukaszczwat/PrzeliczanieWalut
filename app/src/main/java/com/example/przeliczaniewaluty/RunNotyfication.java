package com.example.przeliczaniewaluty;

import android.app.Notification;
import android.content.Context;

public class RunNotyfication {

    Context ctx;
    String bodyNotyfication;
    int y;
    ShowToast show_toast = new ShowToast();

    RunNotyfication(int y, String  bodyNotyfication, Context ctx){
        this.ctx = ctx;
        this.bodyNotyfication = bodyNotyfication;
        this.y = y;
        Run();
    }

    private void Run(){

        try {

            NotyficationShow notyficationShow;
            Notification.Builder nb;
            notyficationShow = new NotyficationShow(ctx);
            nb = notyficationShow.
                    getAndroidChannelNotification("Przelicz Walutę", bodyNotyfication);

            notyficationShow.getManager().notify(y, nb.build());

        } catch (Exception e) {
            String error = e.toString();
            show_toast.showToast(ctx, "Problem z wyświetleniem notyfikacji" + error);

        }
    }

}
