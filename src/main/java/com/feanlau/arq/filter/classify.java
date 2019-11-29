package com.feanlau.arq.filter;

import org.apache.jena.sparql.expr.NodeValue;
import org.apache.jena.sparql.function.FunctionBase1;

/**
 * Example filter function that returns an indicative type string.
 * <ul>
 * <li>"Number", if it's a number of some kind</li>
 * <li>"String", if it's string</li>
 * <li>"DateTime", if it's a date time</li>
 * <li>"unknown" otherwise</li>
 * </ul>
 * <p>
 * Usage:
 * <pre>
 *    PREFIX ext: <java:arq.examples.ext.>
 *  </pre>
 * <pre>
 *    FILTER ext:classify(3+?x)
 *  <pre>
 */

public class classify extends FunctionBase1 {
    public classify() {
        super();
    }

    @Override
    public NodeValue exec(NodeValue v) {
        if (v.isNumber()) return NodeValue.makeString("number");
        if (v.isDateTime()) return NodeValue.makeString("dateTime");
        if (v.isString()) return NodeValue.makeString("string");

        return NodeValue.makeString("unknown");
    }
}
