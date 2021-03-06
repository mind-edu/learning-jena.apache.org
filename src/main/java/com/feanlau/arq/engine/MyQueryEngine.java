package com.feanlau.arq.engine;

import org.apache.jena.query.Query;
import org.apache.jena.sparql.ARQInternalErrorException;
import org.apache.jena.sparql.algebra.Op;
import org.apache.jena.sparql.algebra.Transform;
import org.apache.jena.sparql.algebra.TransformCopy;
import org.apache.jena.sparql.algebra.Transformer;
import org.apache.jena.sparql.algebra.op.OpBGP;
import org.apache.jena.sparql.core.DatasetGraph;
import org.apache.jena.sparql.engine.Plan;
import org.apache.jena.sparql.engine.QueryEngineFactory;
import org.apache.jena.sparql.engine.QueryEngineRegistry;
import org.apache.jena.sparql.engine.QueryIterator;
import org.apache.jena.sparql.engine.binding.Binding;
import org.apache.jena.sparql.engine.main.QueryEngineMain;
import org.apache.jena.sparql.util.Context;

/**
 * Example skeleton for a query engine.
 * To just extend ARQ by custom basic graph pattern matching (a very common case)
 * see the arq.examples.bgpmatching package.
 * To take full control of query execution, use this example for catching the
 * execution setup and see opexec for a customized OpExecutor for query execution.
 */

public class MyQueryEngine extends QueryEngineMain {
    // Do nothing template for a query engine.  

    static QueryEngineFactory factory = new MyQueryEngineFactory();

    public MyQueryEngine(Query query, DatasetGraph dataset, Binding initial, Context context) {
        super(query, dataset, initial, context);
    }

    public MyQueryEngine(Query query, DatasetGraph dataset) {
        // This will default to the global context with no initial settings
        this(query, dataset, null, null);
    }

    static public QueryEngineFactory getFactory() {
        return factory;
    }

    // ---- Registration of the factory for this query engine class. 

    // Query engine factory.
    // Call MyQueryEngine.register() to add to the global query engine registry. 

    static public void register() {
        QueryEngineRegistry.addFactory(factory);
    }

    static public void unregister() {
        QueryEngineRegistry.removeFactory(factory);
    }

    @Override
    public QueryIterator eval(Op op, DatasetGraph dsg, Binding initial, Context context) {
        // Extension point: access possible to all the parameters for execution.
        // Be careful to deal with initial bindings.
        Transform transform = new MyTransform();
        op = Transformer.transform(transform, op);
        return super.eval(op, dsg, initial, context);
    }

    @Override
    protected Op modifyOp(Op op) {
        // Extension point: possible place to alter the algebra expression.
        // Alternative to eval().
        op = super.modifyOp(op);
        // op = Algebra.toQuadForm(op) ;
        return op;
    }

    static class MyTransform extends TransformCopy {
        // Example, do nothing tranform. 
        @Override
        public Op transform(OpBGP opBGP) {
            return opBGP;
        }
    }

    static class MyQueryEngineFactory implements QueryEngineFactory {
        // Accept any dataset for query execution 
        @Override
        public boolean accept(Query query, DatasetGraph dataset, Context context) {
            return true;
        }

        @Override
        public Plan create(Query query, DatasetGraph dataset, Binding initial, Context context) {
            // Create a query engine instance.
            MyQueryEngine engine = new MyQueryEngine(query, dataset, initial, context);
            return engine.getPlan();
        }

        @Override
        public boolean accept(Op op, DatasetGraph dataset, Context context) {   // Refuse to accept algebra expressions directly.
            return false;
        }

        @Override
        public Plan create(Op op, DatasetGraph dataset, Binding inputBinding, Context context) {   // Shodul notbe called because acceept/Op is false
            throw new ARQInternalErrorException("MyQueryEngine: factory calleddirectly with an algebra expression");
        }
    }

}
