package com.buschmais.xo.neo4j.test.inheritance;

import java.net.URISyntaxException;
import java.util.Collection;
import java.util.List;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import com.buschmais.xo.api.XOManager;
import com.buschmais.xo.api.bootstrap.XOUnit;
import com.buschmais.xo.neo4j.test.AbstractNeo4jXOManagerTest;
import com.buschmais.xo.neo4j.test.inheritance.composite.*;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(Parameterized.class)
public class ValueHierarchyTest extends AbstractNeo4jXOManagerTest {

    public ValueHierarchyTest(XOUnit xoUnit) {
        super(xoUnit);
    }

    @Parameterized.Parameters
    public static Collection<Object[]> getXOUnits() throws URISyntaxException {
        return xoUnits(ObjectDescriptor.class, ArrayDescriptor.class, ValueDescriptor.class, ObjectValueDescriptor.class, ArrayValueDescriptor.class);
    }

    @Test
    public void valueHierarchy() {
        XOManager xoManager = getXoManager();
        xoManager.currentTransaction().begin();
        ObjectDescriptor master = xoManager.create(ObjectDescriptor.class);
        ObjectDescriptor detail = xoManager.create(ObjectDescriptor.class);
        ObjectValueDescriptor objectValue = xoManager.create(ObjectValueDescriptor.class);
        objectValue.setValue(detail);
        master.getValues().add(objectValue);

        ObjectDescriptor element = xoManager.create(ObjectDescriptor.class);
        ArrayValueDescriptor arrayValue = xoManager.create(ArrayValueDescriptor.class);
        arrayValue.getValue().add(element);
        master.getValues().add(arrayValue);

        xoManager.currentTransaction().commit();
        xoManager.currentTransaction().begin();
        TestResult result1 = executeQuery("MATCH (master:Object)-[:HAS_VALUE]->(:Object:Value)-[:IS]->(detail:Object) return master, detail");
        List<ObjectDescriptor> masters1 = result1.<ObjectDescriptor> getColumn("master");
        List<ObjectDescriptor> details1 = result1.<ObjectDescriptor> getColumn("detail");
        assertThat(masters1.size(), equalTo(1));
        assertThat(masters1.get(0), is(master));
        assertThat(details1.size(), equalTo(1));
        assertThat(details1.get(0), is(detail));
        TestResult result2 = executeQuery("MATCH (master:Object)-[:HAS_VALUE]->(:Array:Value)-[:HAS_ELEMENT]->(detail:Object) return master, detail");
        List<ObjectDescriptor> masters2 = result2.<ObjectDescriptor> getColumn("master");
        List<ObjectDescriptor> details2 = result2.<ObjectDescriptor> getColumn("detail");
        assertThat(masters2.size(), equalTo(1));
        assertThat(masters2.get(0), is(master));
        assertThat(details2.size(), equalTo(1));
        assertThat(details2.get(0), is(element));
        xoManager.currentTransaction().commit();
    }

}
