package ch.m1m.ldap;

import ch.m1m.ClientBuilder;
import ch.m1m.ClientBuilderA;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Path;
import java.nio.file.Paths;

public class JarLoadTest {

    public static void main(String... args) throws ClassNotFoundException, IllegalAccessException, InstantiationException, IOException {

        System.out.println("starting...");

        URLClassLoader loader1;
        URLClassLoader loader2;

        Path currentRelativePath = Paths.get("");
        String s = currentRelativePath.toAbsolutePath().toString();
        System.out.println("Current relative path is: " + s);

        // Thread.currentThread().getContextClassLoader()
        //
        // loader1 = new URLClassLoader(new URL[] {new File("jarapia/target/jarapia-1.0-SNAPSHOT.jar").toURL()}, Thread.currentThread().getContextClassLoader());
        //
        loader1 = new URLClassLoader(new URL[] {new File("jarapia/target/jarapia-1.0-SNAPSHOT.jar").toURL()}, Thread.currentThread().getContextClassLoader());
        loader2 = new URLClassLoader(new URL[] {new File("jarapiz/target/jarapiz-1.0-SNAPSHOT.jar").toURL()}, Thread.currentThread().getContextClassLoader());

        ClassLoader clOriginal = Thread.currentThread().getContextClassLoader();

        Thread.currentThread().setContextClassLoader(loader1);
        //Class<?> c1 = loader1.loadClass("ch.m1m.ClientBuilderA");
        //ClientBuilder i1 = (ClientBuilder) c1.newInstance();
        ClientBuilder i1 = new ClientBuilderA();

        System.out.println("A: " + i1.getVersion());

        Thread.currentThread().setContextClassLoader(loader2);
        //Class<?> c2 = loader2.loadClass("ch.m1m.ClientBuilderA");
        //ClientBuilder i2 = (ClientBuilder) c2.newInstance();
        ClientBuilder i2 = new ClientBuilderA();
        System.out.println("Z: " + i2.getVersion());

        System.out.println("press any key...");
        System.in.read();

        System.out.println("### end ###");
    }
}

/*

from: https://stackoverflow.com/questions/11759414/java-how-to-load-different-versions-of-the-same-class


common.jar:
BaseInterface

v1.jar:
SomeImplementation implements BaseInterface

v2.jar:
OtherImplementation implements BaseInterface

command-line:
java -classpath common.jar YourMainClass
// you don't put v1 nor v2 into the parent classloader classpath

Then in your program:

loader1 = new URLClassLoader(new URL[] {new File("v1.jar").toURL()}, Thread.currentThread().getContextClassLoader());
loader2 = new URLClassLoader(new URL[] {new File("v2.jar").toURL()}, Thread.currentThread().getContextClassLoader());

Class<?> c1 = loader1.loadClass("com.abc.Hello");
Class<?> c2 = loader2.loadClass("com.abc.Hello");

BaseInterface i1 = (BaseInterface) c1.newInstance();
BaseInterface i2 = (BaseInterface) c2.newInstance();

 */