package com.feanlau.arq.propertyfunction;

import org.apache.jena.graph.Node;
import org.apache.jena.graph.NodeFactory;
import org.apache.jena.sparql.pfunction.PFuncAssignToObject;

/**
 * Example property function that uppercases the lexical form of a literal.
 * The subject must be bound, the object not bound. {@link localname} shows a
 * property function that handles more cases of subject or object bing bound or unbound.
 * <pre>
 *     PREFIX ext: <java:arq.examples.propertyfunction.>
 *  </pre>
 * <pre>
 *     { ?string ext:uppercase ?uppercase }
 *  </pre>
 * <pre>
 *     { "lower case" ext:uppercase ?uppercase }
 *  </pre>
 * Else fails to match.
 */

public class uppercase extends PFuncAssignToObject {
    @Override
    public Node calc(Node node) {
        if (!node.isLiteral())
            return null;
        String str = node.getLiteralLexicalForm().toUpperCase();
        return NodeFactory.createLiteral(str);
    }
}
