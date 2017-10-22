using Toybox.WatchUi    as Ui;
using Toybox.System     as Sys;
using Toybox.Graphics   as Gfx;
using Toybox.System     as Sys;

class OBD2_HackISU_ConnectIQView01 extends Ui.View
{
    var screenShape;

    function drawIntroPage(dc)
    {
        findDrawableById("id_title").setText("OBD2Reader");
        findDrawableById("id_data_desc").setText("Speed");
        findDrawableById("id_data_value").setText("Waiting...");
        findDrawableById("id_secondary_data_desc").setText("Tach");
        findDrawableById("id_secondary_data_value").setText("Waiting...");
    }

    function initialize()
    {
        View.initialize();
    }

    // Load your resources here
    function onLayout(dc)
    {
        setLayout(Rez.Layouts.MainLayout(dc));
        screenShape = Sys.getDeviceSettings().screenShape;
        
        findDrawableById("id_title").setLocation(dc.getWidth()/2, 25);
        findDrawableById("id_data_desc").setLocation(dc.getWidth()/2, 50);
        findDrawableById("id_data_value").setLocation(dc.getWidth()/2, 80);
        findDrawableById("id_secondary_data_desc").setLocation(dc.getWidth()/2, 115);
        findDrawableById("id_secondary_data_value").setLocation(dc.getWidth()/2, 140);
    }

    // Called when this View is brought to the foreground. Restore
    // the state of this View and prepare it to be shown. This includes
    // loading resources into memory.
    function onShow()
    {
    }

    // Update the view
    function onUpdate(dc)
    {
        dc.clear();

        var speed   = 0.0;
        var tach    = 0.0;

        if(false == validData)
        {
            drawIntroPage(dc);
        }
        else
        {
            speed = queue[0].get("speed");
            tach  = queue[0].get("tach");
            //findDrawableById("id_title").setText("OBD2Reader");
            //findDrawableById("id_data_desc").setText("Speed");
            findDrawableById("id_data_value").setText(speed.toString());
            //findDrawableById("id_secondary_data_desc").setText("Tach");
            findDrawableById("id_secondary_data_value").setText(tach.toString());


        }
        View.onUpdate(dc);
        //ViewHelper.drawGauge(dc, 100.0, speed, true, 3, Gfx.COLOR_BLUE, Gfx.COLOR_WHITE);
        //ViewHelper.drawGauge(dc, 8000.0, tach,  false, 3, Gfx.COLOR_GREEN, Gfx.COLOR_WHITE);
    }

    // Called when this View is removed from the screen. Save the
    // state of this View here. This includes freeing resources from
    // memory.
    function onHide()
    {
    }

}
