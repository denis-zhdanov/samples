package org.denis.sample.java.spring.tx.util;

import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.TransactionStatus;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

import static org.springframework.transaction.TransactionDefinition.*;

public class AdaptivePlatformTransactionManager implements PlatformTransactionManager {

    private static final Set<Integer> PROPAGATIONS_WHICH_ENROLL_INTO_EXISTING_TX = new HashSet<>(Arrays.asList(
            PROPAGATION_MANDATORY, PROPAGATION_NESTED, PROPAGATION_REQUIRED, PROPAGATION_SUPPORTS
    ));

    private final ThreadLocal<Stack<Boolean>> ro = ThreadLocal.withInitial(Stack::new);

    private final PlatformTransactionManager roDelegate;
    private final PlatformTransactionManager rwDelegate;
    private final AdaptiveDataSource         dataSource;

    public AdaptivePlatformTransactionManager(PlatformTransactionManager roDelegate,
                                              PlatformTransactionManager rwDelegate,
                                              AdaptiveDataSource dataSource)
    {
        this.roDelegate = roDelegate;
        this.rwDelegate = rwDelegate;
        this.dataSource = dataSource;
    }

    @Override
    public TransactionStatus getTransaction(TransactionDefinition definition) throws TransactionException {
        boolean shouldUseRo = shouldUseRo(definition);
        PlatformTransactionManager delegate = shouldUseRo ? roDelegate : rwDelegate;
        ro.get().push(shouldUseRo);
        dataSource.activate(shouldUseRo);
        return delegate.getTransaction(definition);
    }

    private boolean shouldUseRo(TransactionDefinition definition) {
        boolean requestedRoTransaction = definition.isReadOnly();
        Stack<Boolean> stack = ro.get();
        if (stack.isEmpty()) {
            return requestedRoTransaction;
        }

        boolean activeTransactionIsRo = stack.peek();

        // When control flow reaches this place, that means that there is an existing transaction in progress.
        // We need to understand if given definition creates a new transaction or enrolls into the existing one.

        if (!PROPAGATIONS_WHICH_ENROLL_INTO_EXISTING_TX.contains(definition.getPropagationBehavior())) {
            // We don't enroll into the existing transaction, so, just use RO from the given definition.
            return requestedRoTransaction;
        }

        if (!activeTransactionIsRo) {
            // When we enroll into existing RW transaction, use RW as well
            return false;
        }

        // Active transaction is RO.
        if (!requestedRoTransaction) {
            throw new IllegalStateException("Can't start an RW transaction in the RO transaction context");
        }
        return true;
    }

//    private boolean willContinueInTxContent(TransactionDefinition definition) {
//        switch (definition.getPropagationBehavior()) {
//            case PROPAGATION_NEVER:
//            case PROPAGATION_NOT_SUPPORTED:
//                return false;
//            case PROPAGATION_REQUIRES_NEW:
//                return true;
//            case PROPAGATION_NESTED:
//            case PROPAGATION_REQUIRED:
//                return ro.get().isEmpty();
//            default:
//                return false;
//
//        }
//    }

    @Override
    public void commit(TransactionStatus status) throws TransactionException {
        Stack<Boolean> stack = ro.get();
        Boolean useRoTxManager = stack.pop();
        PlatformTransactionManager delegate = useRoTxManager ? roDelegate : rwDelegate;
        delegate.commit(status);
        dataSource.activate(!stack.isEmpty() && stack.peek());
    }

    @Override
    public void rollback(TransactionStatus status) throws TransactionException {
        Stack<Boolean> stack = ro.get();
        Boolean useRoTxManager = stack.pop();
        PlatformTransactionManager delegate = useRoTxManager ? roDelegate : rwDelegate;
        delegate.rollback(status);
        dataSource.activate(!stack.isEmpty() && stack.peek());
    }
}
