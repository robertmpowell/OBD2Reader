using Toybox.WatchUi    as Ui;
using Toybox.System     as Sys;
using Toybox.Graphics   as Gfx;
using Toybox.System     as Sys;

class OBD2_HackISU_ConnectIQView03 extends Ui.View
{
    var screenShape;

    function drawIntroPage(dc)
    {
        findDrawableById("id_title").setText("OBD2Reader");
        findDrawableById("id_data_desc").setText("Fuel Level");
        findDrawableById("id_data_value").setText("Waiting...");
        findDrawableById("id_secondary_data_desc").setText("Fuel Consp.");
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
        // Call the parent onUpdate function to redraw the layout

        dc.setColor(Gfx.COLOR_TRANSPARENT, Gfx.COLOR_BLACK);
        dc.clear();
        dc.setColor(Gfx.COLOR_WHITE, Gfx.COLOR_TRANSPARENT);

        findDrawableById("id_title").setLocation(dc.getWidth()/2, 25);
        findDrawableById("id_data_desc").setLocation(dc.getWidth()/2, 50);
        findDrawableById("id_data_value").setLocation(dc.getWidth()/2, 80);
        findDrawableById("id_secondary_data_desc").setLocation(dc.getWidth()/2, 115);
        findDrawableById("id_secondary_data_value").setLocation(dc.getWidth()/2, 140);

        var fuel_level          = 0.0;
        var fuel_consumption    = 0.0;

        if(false == validData)
        {
            drawIntroPage(dc);
        }
        else
        {
            fuel_level          = queue[0].get("fuel_level");
            fuel_consumption    = queue[0].get("fuel_consumption");
            findDrawableById("id_title").setText("OBD2Reader");
            findDrawableById("id_data_desc").setText("Fuel Level");
            findDrawableById("id_data_value").setText(fuel_level.toString() + " %");
            findDrawableById("id_secondary_data_desc").setText("Fuel Consp.");
            findDrawableById("id_secondary_data_value").setText(fuel_consumption.toString() + " GPM");
        }

        View.onUpdate(dc);

        ViewHelper.drawGauge(dc, 100.0, fuel_level, true, 3, Gfx.COLOR_BLUE, Gfx.COLOR_WHITE);
        ViewHelper.drawGauge(dc, 0.50, fuel_consumption, false, 3, Gfx.COLOR_GREEN, Gfx.COLOR_WHITE);
    }

    // Called when this View is removed from the screen. Save the
    // state of this View here. This includes freeing resources from
    // memory.
    function onHide()
    {
    }




}
