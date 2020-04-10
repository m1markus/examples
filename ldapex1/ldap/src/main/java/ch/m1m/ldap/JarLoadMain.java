package ch.m1m.ldap;

import ch.m1m.ClientBuilder;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

/*

parts from: https://stackoverflow.com/questions/11759414/java-how-to-load-different-versions-of-the-same-class

 */

public class JarLoadMain {

    public static void main(String... args) throws ClassNotFoundException, IllegalAccessException, InstantiationException, IOException {

        ClassLoader loader1;
        ClassLoader loader2;

        System.out.println("starting...");

        // print current working directory
        //
        Path currentRelativePath = Paths.get("");
        String s = currentRelativePath.toAbsolutePath().toString();
        System.out.println("Current relative path is: " + s);

        // Thread.currentThread().setContextClassLoader(loader2);
        // is for JNDI Context operations
        //
        // Thread.currentThread().getContextClassLoader()
        //
        // loader1 = new URLClassLoader(new URL[] {new File("jarapia/target/jarapia-1.0-SNAPSHOT.jar").toURL()}, Thread.currentThread().getContextClassLoader());
        // JarClassLoader
        //
        ClassLoader appClassLoader = JarLoadMain.class.getClassLoader();
        loader1 = new JarClassLoader(new URL[]{new File("jarapiz/target/jarapiz-1.0-SNAPSHOT.jar").toURL()}, appClassLoader);
        loader2 = new JarClassLoader(new URL[]{new File("jarapia/target/jarapia-1.0-SNAPSHOT.jar").toURL()}, appClassLoader);

        Class<?> c1 = loader1.loadClass("ch.m1m.ClientBuilderA");
        ClientBuilder i1 = (ClientBuilder) c1.newInstance();
        i1.execute();

        Class<?> c2 = loader2.loadClass("ch.m1m.ClientBuilderA");
        ClientBuilder i2 = (ClientBuilder) c2.newInstance();
        i2.execute();

        //System.out.println("press any key...");
        //System.in.read();

        System.out.println("### end ###");
    }
}
