package ch.m1m.quarkus.example;

import java.util.List;

public class ProxyRulePersitence {

    private List<ProxyRuleEntry> mapTargets;

    public  List<ProxyRuleEntry> getMapTargets() {
        return mapTargets;
    }

    public void setMapTargets(List<ProxyRuleEntry> mapTargets) {
        this.mapTargets = mapTargets;
    }
}
