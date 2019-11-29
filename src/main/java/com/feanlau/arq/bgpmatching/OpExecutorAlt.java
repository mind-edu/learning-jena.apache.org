package com.feanlau.arq.bgpmatching;

import org.apache.jena.sparql.ARQNotImplemented;
import org.apache.jena.sparql.algebra.op.OpBGP;
import org.apache.jena.sparql.algebra.op.OpDatasetNames;
import org.apache.jena.sparql.algebra.op.OpQuadPattern;
import org.apache.jena.sparql.engine.ExecutionContext;
import org.apache.jena.sparql.engine.QueryIterator;
import org.apache.jena.sparql.engine.main.OpExecutor;
import org.apache.jena.sparql.engine.main.OpExecutorFactory;

/** Example of an alternative OpExecutor */
public class OpExecutorAlt extends OpExecutor {

    public static OpExecutorFactory factory = new OpExecutorFactory() {

        @Override
        public OpExecutor create(ExecutionContext execCxt) {
            return new OpExecutorAlt(execCxt);
        }
    };


    public OpExecutorAlt(ExecutionContext execCxt) {
        super(execCxt);
    }

    // The two places where we touch the storage, dpeneding on whether this is a
    // triple or a quad based execution.

    @Override
    protected QueryIterator execute(OpBGP opBGP, QueryIterator input) {
        return super.execute(opBGP, input);
    }

    // Default OpQuadPattern execution is a loop and it will call back into
    // execute(OpBGP, QueryIterator)
    @Override
    protected QueryIterator execute(OpQuadPattern opQuadPattern, QueryIterator input) {
        return super.execute(opQuadPattern, input);
    }

    // Quad form, "GRAPH ?g {}" 
    // Flip back to OpGraph.
    // Not needed for triples-based execution 
    // Normally quad stores override this.
    @Override
    protected QueryIterator execute(OpDatasetNames dsNames, QueryIterator input) {
        throw new ARQNotImplemented("execute/OpDatasetNames");
    }
}

