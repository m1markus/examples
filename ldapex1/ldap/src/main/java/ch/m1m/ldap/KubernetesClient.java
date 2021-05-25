package ch.m1m.ldap;

import java.io.IOException;

import io.kubernetes.client.openapi.ApiClient;
import io.kubernetes.client.openapi.ApiException;
import io.kubernetes.client.openapi.Configuration;
import io.kubernetes.client.openapi.apis.CoreV1Api;
import io.kubernetes.client.openapi.models.V1Namespace;
import io.kubernetes.client.openapi.models.V1NamespaceList;
import io.kubernetes.client.openapi.models.V1Pod;
import io.kubernetes.client.openapi.models.V1PodList;
import io.kubernetes.client.util.Config;

public class KubernetesClient {

    public static void main(String... args) {

        System.out.println("kube client starting...");

        try {

            KubernetesClient kc = new KubernetesClient();

            CoreV1Api api = kc.createClientAPI();

            //kc.listNS(api);
            kc.listPods(api);

        } catch (Exception e) {

            System.err.println("ERROR: kube api error: " + e.toString());
        }

        System.out.println("### end ###");
    }

    private void listNS(CoreV1Api api) throws ApiException {

        System.out.println("list namespaces");
        V1NamespaceList list = api.listNamespace(null, true, null, null, null, 0, null, Integer.MAX_VALUE, Boolean.FALSE);
        for (V1Namespace item : list.getItems()) {
            System.out.println(item.getMetadata().getName());
        }
    }

    private void listPods(CoreV1Api api) throws IOException, ApiException {
        String namespace = "backendservices-master";
        System.out.println("list pods for ns: " + namespace);

        V1PodList list = api.listNamespacedPod(namespace,
                null,
                null,
                null,
                null,
                null,
                Integer.MAX_VALUE,
                null,
                30,
                Boolean.FALSE);
        for (V1Pod item : list.getItems()) {
            System.out.println(item.getMetadata().getName());
        }
    }

    public CoreV1Api createClientAPI() throws IOException {
        ApiClient client = Config.defaultClient();
        client.setBasePath("https://api.ocp4-test.sympany-test.ads:6443");
        client.setApiKey("G...E");
        client.setVerifyingSsl(false);
        Configuration.setDefaultApiClient(client);

        return new CoreV1Api();
    }
}
