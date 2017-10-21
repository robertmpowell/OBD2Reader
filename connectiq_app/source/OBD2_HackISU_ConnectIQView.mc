using Toybox.WatchUi    as Ui;
using Toybox.System     as Sys;
using Toybox.Graphics   as Gfx;
using Toybox.System     as Sys;

class OBD2_HackISU_ConnectIQView extends Ui.View
{
    var screenShape;

    function drawIntroPage(dc)
    {
        findDrawableById("id_text_line01").setText("Rob is a giga dumbo");
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

        if(page == 0)
        {
            drawIntroPage(dc);
        }
        else
        {
            var i;
            var y = 50;

            var buf = "";

            findDrawableById("id_text_line01").setText("Strings Received:");
            //dc.drawText(dc.getWidth() / 2, 20,  Gfx.FONT_MEDIUM, "Strings Received:", Gfx.TEXT_JUSTIFY_CENTER);
            for(i = 0; i < stringsSize; i += 1)
            {
                buf += strings[i];
                //dc.drawText(dc.getWidth() / 2, y,  Gfx.FONT_SMALL, strings[i], Gfx.TEXT_JUSTIFY_CENTER);
            }
            findDrawableById("id_text_line01").setText(buf);
        }

        View.onUpdate(dc);
    }

    // Called when this View is removed from the screen. Save the
    // state of this View here. This includes freeing resources from
    // memory.
    function onHide()
    {
    }




}
