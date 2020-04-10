package ch.m1m.ldap;

import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLClassLoader;

public class JarClassLoader extends ClassLoader {

    private ClassLoader urlClassLoader;

    public JarClassLoader(URL[] urls, ClassLoader parent) {
        super(parent);
        // FIXME: throw exception if JAR file does not exist
        urlClassLoader = new URLClassLoader(urls, parent);
    }

    @Override
    public Class loadClass(String name) throws ClassNotFoundException {
        System.out.println("JarClassLoader: Loading Class '" + name + "'");
        if (name.startsWith("ch.m1m")) {
            System.out.println("Loading Class using JarClassLoader");
            Class<?> clazzNew = urlClassLoader.loadClass(name);
            return clazzNew;
        }
        return super.loadClass(name);
    }
}