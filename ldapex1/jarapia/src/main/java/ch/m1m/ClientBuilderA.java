package ch.m1m;

public class ClientBuilderA implements ClientBuilder {

    private String getVersion() {
        return "ClientBuilder " + Constant.MODULE_NAME;
    }

    public void execute() {
        System.out.println("module version is: " + getVersion());

        new MessageMapperFactory().execute();
    }
}
