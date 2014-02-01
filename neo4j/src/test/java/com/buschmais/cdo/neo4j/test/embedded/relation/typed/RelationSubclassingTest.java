package com.buschmais.cdo.neo4j.test.embedded.relation.typed;

import com.buschmais.cdo.api.CdoManager;
import com.buschmais.cdo.neo4j.test.embedded.AbstractEmbeddedCdoManagerTest;

import com.buschmais.cdo.neo4j.test.embedded.relation.typed.composite.BaseType;
import com.buschmais.cdo.neo4j.test.embedded.relation.typed.composite.C;
import com.buschmais.cdo.neo4j.test.embedded.relation.typed.composite.D;
import com.buschmais.cdo.neo4j.test.embedded.relation.typed.composite.TypeA;
import com.buschmais.cdo.neo4j.test.embedded.relation.typed.composite.TypeB;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Ignore;
import org.junit.Test;

public class RelationSubclassingTest extends AbstractEmbeddedCdoManagerTest {

    @Override
    protected Class<?>[] getTypes() {
        return new Class<?>[]{C.class, D.class, TypeA.class, TypeB.class};
    }

    @Test
    public void testRelationSubclassing() {
        CdoManager cdoManager = getCdoManager();
        cdoManager.currentTransaction().begin();
        C c = cdoManager.create(C.class);
        D d1 = cdoManager.create(D.class);
        D d2 = cdoManager.create(D.class);
        BaseType relation1 = cdoManager.create(c, TypeA.class, d1);
        relation1.setVersion(1);
        BaseType relation2 = cdoManager.create(c, TypeB.class, d2);
        relation2.setVersion(2);
        cdoManager.currentTransaction().commit();

        cdoManager.currentTransaction().begin();
        assertThat(c.getTypeA().getVersion(), equalTo(relation1.getVersion()));
        assertThat(c.getTypeB().getVersion(), equalTo(relation2.getVersion()));
        cdoManager.currentTransaction().commit();        
    }

}
