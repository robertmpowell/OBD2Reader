using Toybox.WatchUi as Ui;

class BaseInputDelegate extends Ui.BehaviorDelegate {

    function initialize()
    {
        BehaviorDelegate.initialize();
    }

    function onSelect()
    {
    }

    function onPreviousPage()
    {
        if (page == 1) {
            page = 3;
        } else {
            page = page - 1;
        }

        switchView(page, Ui.SLIDE_DOWN);
        Ui.requestUpdate();
        return true;
    }

    function onNextPage()
    {

        if (page == 3) {
            page = 1;
        } else {
            page = page + 1;
        }

        switchView(page, Ui.SLIDE_UP);
        Ui.requestUpdate();
        return true;
    }

    function onMenu()
    {
    }

    function switchView(pageNum, slideType) {

        var newView = null;
        var inputDelegate = new BaseInputDelegate();

        if(1 == page)
        {
            newView = new OBD2_HackISU_ConnectIQView01();
        }
        else if(2 == page)
        {
            newView = new OBD2_HackISU_ConnectIQView02();
        }
        else if(3 == page)
        {
            newView = new OBD2_HackISU_ConnectIQView03();
        }

        switchToView(newView, inputDelegate, slideType);
    }
}