package com.buschmais.cdo.neo4j.impl.proxy.method.object;

import com.buschmais.cdo.neo4j.api.proxy.ProxyMethod;
import org.neo4j.graphdb.Node;

public class HashCodeMethod implements ProxyMethod {

    @Override
    public Object invoke(Node node, Object instance, Object[] args) {
        return Long.valueOf(node.getId());
    }
}