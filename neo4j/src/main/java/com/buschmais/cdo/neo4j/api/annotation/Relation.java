package com.buschmais.cdo.neo4j.api.annotation;

import com.buschmais.cdo.spi.annotation.RelationDefinition;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static com.buschmais.cdo.spi.annotation.RelationDefinition.FromDefinition;
import static com.buschmais.cdo.spi.annotation.RelationDefinition.ToDefinition;
import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Defines a relationship.
 * <p/>
 * Can be used on the following java elements
 * <ul>
 * <li>get methods references or collections of other composite objects (optional).</li>
 * <li>relation qualifier types (mandatory).</li>
 * <li>relation types (mandatory)</li>
 * </ul
 * </p>
 */
@RelationDefinition
@Retention(RUNTIME)
@Target({TYPE, ANNOTATION_TYPE, METHOD})
public @interface Relation {

    String DEFAULT_VALUE = "";

    /**
     * @return The name of the relation.
     */
    String value() default DEFAULT_VALUE;

    /**
     * Marks a property as incoming relationship.
     */
    @ToDefinition
    @Retention(RUNTIME)
    @Target({METHOD})
    public @interface Incoming {
    }

    /**
     * Marks a property as outgoing relationship.
     */
    @FromDefinition
    @Retention(RUNTIME)
    @Target({METHOD})
    public @interface Outgoing {
    }
}
