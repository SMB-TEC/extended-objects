package com.buschmais.xo.neo4j.test.demo;

import com.buschmais.xo.api.XOException;
import com.buschmais.xo.api.XOManager;
import com.buschmais.xo.api.bootstrap.XOUnit;
import com.buschmais.xo.neo4j.api.annotation.Indexed;
import com.buschmais.xo.neo4j.api.annotation.Label;
import com.buschmais.xo.neo4j.test.AbstractNeo4jXOManagerTest;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.net.URISyntaxException;
import java.util.Collection;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@RunWith(Parameterized.class)
public class CrudTest extends AbstractNeo4jXOManagerTest {

    public CrudTest(XOUnit xoUnit) {
        super(xoUnit);
    }

    @Parameterized.Parameters
    public static Collection<Object[]> getXOUnits() throws URISyntaxException {
        return xoUnits(A.class);
    }

    @Test
    public void create() throws InterruptedException {
        XOManager xoManager = getXoManager();
        xoManager.currentTransaction().begin();
        A a = xoManager.create(A.class);
        a.setName("Foo");
        xoManager.currentTransaction().commit();
        xoManager.currentTransaction().begin();
        a = xoManager.find(A.class, "Foo").getSingleResult();
        assertThat(a.getName(), equalTo("Foo"));
        a.setName("Bar");
        xoManager.currentTransaction().commit();
        xoManager.currentTransaction().begin();
        xoManager.createQuery("match (a:A) where a.name={name} return a").withParameter("name", "Bar").execute().getSingleResult();
        xoManager.delete(a);
        xoManager.currentTransaction().commit();
        xoManager.currentTransaction().begin();
        try {
            xoManager.createQuery("match (a:A) return a").execute().getSingleResult();
            Assert.fail("An exception is expected.");
        } catch (XOException e) {
        }
        xoManager.currentTransaction().commit();
    }

    @Label("A")
    public interface A {

        @Indexed
        String getName();

        void setName(String name);

    }
}
