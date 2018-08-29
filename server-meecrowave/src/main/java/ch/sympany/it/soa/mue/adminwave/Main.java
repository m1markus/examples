package ch.sympany.it.soa.mue.adminwave;

import org.apache.meecrowave.Meecrowave;

import javax.enterprise.inject.se.SeContainer;
import javax.enterprise.inject.se.SeContainerInitializer;

public class Main {

    public static void main(String...args) {

        System.out.println("starting meecrowave up...");

        int rc = start();

        System.out.println("### ended ###");
    }

    private static int start() {

        Meecrowave.Builder config = new Meecrowave.Builder();
        config.setHttpPort(8080);
        config.setHttp2(true);
        config.setUseShutdownHook(true);

        Meecrowave server = new Meecrowave(config);

        server.bake().await();

 /*
        new Meecrowave(new Meecrowave.Builder() {
            {
                randomHttpPort();
                setTomcatScanning(true);
                setTomcatAutoSetup(false);
                setHttp2(true);
                setTempDir("target/meecrowave/" + System.nanoTime());
                setUseShutdownHook(true);
            }
        }).bake()
          .await();
          */

        return 0;
    }


    private static int start_with_cdi() {
        try (final SeContainer container = SeContainerInitializer.newInstance()
                .addProperty("nameOfTheProperty", "toto")
                .initialize()) {
            container.select(Meecrowave.class).get().await();
        }

        return 0;
    }

}
