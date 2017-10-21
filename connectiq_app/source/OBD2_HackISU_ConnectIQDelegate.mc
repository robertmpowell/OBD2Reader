using Toybox.Communications as Comm;

class CommListener extends Comm.ConnectionListener {
    function initialize()
    {
        Comm.ConnectionListener.initialize();
    }

    function onComplete()
    {
        Sys.println("Transmit Complete");
    }

    function onError()
    {
        Sys.println("Transmit Failed");
    }
}