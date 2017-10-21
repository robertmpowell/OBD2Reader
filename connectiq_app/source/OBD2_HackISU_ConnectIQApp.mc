using Toybox.Application    as App;
using Toybox.Communications as Comm;
using Toybox.WatchUi        as Ui;
using Toybox.System as Sys;

var strings = ["","","","",""];
var stringsSize = 5;
var phoneMethod;
var page = 0;

class OBD2_HackISU_ConnectIQApp extends App.AppBase {

    function initialize()
    {
        AppBase.initialize();

        phoneMethod = method(:onPhone);
        Comm.registerForPhoneAppMessages(phoneMethod);
    }

    // onStart() is called on application start up
    function onStart(state)
    {
    }

    // onStop() is called when your application is exiting
    function onStop(state)
    {
    }

    // Return the initial view of your application here
    function getInitialView()
    {
        return [ new OBD2_HackISU_ConnectIQView() ];
    }

    function onMail(mailIter)
    {
	    var mail;

	    mail = mailIter.next();

	    while(mail != null)
        {
	        var i;
	        for(i = (stringsSize - 1); i > 0; i -= 1)
            {
	            strings[i] = strings[i-1];
	        }
	        strings[0] = mail.toString();
	        page = 1;
	        mail = mailIter.next();
        }

        Comm.emptyMailbox();
        Ui.requestUpdate();
    }

    function onPhone(msg)
    {
        var i;

        //if((crashOnMessage == true) && msg.data.equals("Hi")) {
        //    foo = bar;
        //}

        for(i = (stringsSize - 1); i > 0; i -= 1) {
            strings[i] = strings[i-1];
        }
        strings[0] = msg.data.toString();
        page = 1;

        Ui.requestUpdate();
    }

}