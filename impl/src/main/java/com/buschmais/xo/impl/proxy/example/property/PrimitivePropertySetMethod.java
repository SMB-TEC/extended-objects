package com.buschmais.xo.impl.proxy.example.property;

import com.buschmais.xo.api.proxy.ProxyMethod;
import com.buschmais.xo.spi.metadata.method.PrimitivePropertyMethodMetadata;

import java.util.Map;

public class PrimitivePropertySetMethod implements ProxyMethod<Map<PrimitivePropertyMethodMetadata<?>, Object>> {

    private final PrimitivePropertyMethodMetadata<?> methodMetadata;

    public PrimitivePropertySetMethod(PrimitivePropertyMethodMetadata<?> methodMetadata) {
        this.methodMetadata = methodMetadata;
    }

    @Override
    public Object invoke(Map<PrimitivePropertyMethodMetadata<?>, Object> properties, Object instance, Object[] args) throws Exception {
        properties.put(methodMetadata, args[0]);
        return null;
    }
}
