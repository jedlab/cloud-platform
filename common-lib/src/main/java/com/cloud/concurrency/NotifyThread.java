package com.cloud.concurrency;

public abstract class NotifyThread extends Thread
{
    NotifyThreadListener listener;
    Long id;

    public NotifyThread(NotifyThreadListener listener, Long id)
    {
        this.listener = listener;
        this.id = id;
    }

    @Override
    public void run()
    {
        try
        {
            doRun();
        }
        finally
        {
            listener.onComplete(id);
        }
    }

    public abstract void doRun();
}