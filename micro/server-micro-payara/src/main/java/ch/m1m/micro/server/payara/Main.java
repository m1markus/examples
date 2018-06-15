package ch.m1m.micro.server.payara;


import fish.payara.micro.PayaraMicro;
import fish.payara.micro.BootstrapException;

public class Main
{
    public static void main(String[] args) throws BootstrapException
    {
        PayaraMicro.getInstance().setUserLogFile("/tmp/PayaraMicro.log").bootStrap();
    }
}