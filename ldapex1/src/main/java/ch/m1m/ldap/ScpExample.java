package ch.m1m.ldap;

import net.schmizz.sshj.SSHClient;
import net.schmizz.sshj.sftp.SFTPClient;
import net.schmizz.sshj.xfer.FileSystemFile;
import net.schmizz.sshj.xfer.LocalSourceFile;

import java.io.IOException;

public class ScpExample {

    public static void main(String... args) {

        SSHClient ssh = null;
        SFTPClient sftp = null;

        String userId = "mue";
        String passWd = null;

        String hostName = "tvvvgslwls039.sympany-test.ads";

        System.out.println("running...");

        try {

            System.out.print("SSHClient()... ");
            System.out.flush();
            ssh = new SSHClient();
            System.out.println("instance created");
            System.out.flush();

            ssh.loadKnownHosts();

            System.out.print("connecting... ");
            System.out.flush();
            ssh.connect(hostName);
            System.out.println("connected");
            System.out.flush();


            System.out.print("authPassword()... ");
            System.out.flush();
            ssh.authPassword(userId, passWd);
            System.out.println("returned");
            System.out.flush();

            // ssh.isConnected();
            // ssh.loadKeys("/conf/keyfile", "passphrase");

            System.out.print("newSFTPClient()... ");
            System.out.flush();
            sftp = ssh.newSFTPClient();
            System.out.println("instance created");
            System.out.flush();


            System.out.print("put()... ");
            System.out.flush();
            sftp.put(new FileSystemFile("/Users/mue/work/README.playlist.txt"), "/tmp/from_mac_by_scp.txt");
            System.out.println("ok");
            System.out.flush();

        } catch (IOException e) {

            System.out.println("Error occured: " + e);
            System.out.flush();

        } finally {

            if (sftp != null) {
                try {
                    sftp.close();
                } catch (IOException e) {
                    // ignore
                }
            }

            if (ssh != null) {
                try {
                    ssh.disconnect();
                    ssh.close();
                } catch (IOException e) {
                    // ignore
                }
            }
        }

        System.out.println("### end ###");
    }

}

/*

SSHClient ssh = new SSHClient();
ssh.loadKnownHosts();
ssh.connect("host");
try {
    ssh.authPassword("username", "password");
    SFTPClient sftp = ssh.newSFTPClient();
    try {
        sftp.put(new FileSystemFile("/path/of/local/file"), "/path/of/ftp/file");
    } finally {
        sftp.close();
    }
} finally {
    ssh.disconnect();
}

*/
