package soot.jimple.infoflow.methodSummary.generator;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import soot.SootMethod;
import soot.Value;
import soot.jimple.DefinitionStmt;
import soot.jimple.InstanceInvokeExpr;
import soot.jimple.Stmt;
import soot.jimple.infoflow.data.AccessPath;
import soot.jimple.infoflow.solver.IInfoflowCFG;
import soot.jimple.infoflow.taintWrappers.AbstractTaintWrapper;

/**
 * Taint wrapper to be used during summary construction. If we find a call for
 * which we have no callee, we create a gap in our summary. This means that this
 * taint wrapper needs to produce fake sources for the possible outcomes of the
 * code inside the gap.
 * 
 * @author Steven Arzt
 */
public class SummaryGenerationTaintWrapper extends AbstractTaintWrapper {
		
	@Override
	public void initialize() {
		
	}
	
	@Override
	public Set<AccessPath> getTaintsForMethod(Stmt stmt, AccessPath taintedPath, IInfoflowCFG icfg) {
		// This must be a method invocation
		if (!stmt.containsInvokeExpr())
			return null;
		
		// If we have callees, we analyze them as usual
		Collection<SootMethod> callees = icfg.getCalleesOfCallAt(stmt);
		if (callees != null && !callees.isEmpty())
			return null;
		
		// Produce a continuation
		Set<AccessPath> res = new HashSet<AccessPath>();
		if (stmt.getInvokeExpr() instanceof InstanceInvokeExpr)
			res.add(new AccessPath(((InstanceInvokeExpr) stmt.getInvokeExpr()).getBase(), true));
		for (Value paramVal : stmt.getInvokeExpr().getArgs())
			res.add(new AccessPath(paramVal, true));
		if (stmt instanceof DefinitionStmt)
			res.add(new AccessPath(((DefinitionStmt) stmt).getLeftOp(), true));
		
		return res;
	}

	@Override
	protected boolean isExclusiveInternal(Stmt stmt, AccessPath taintedPath, IInfoflowCFG icfg) {
		return false;
	}

	@Override
	public boolean supportsCallee(SootMethod method) {
		// Callees are always theoretically supported
		return true;
	}

	@Override
	public boolean supportsCallee(Stmt callSite, IInfoflowCFG icfg) {
		// We only wrap calls that have no callees
		Collection<SootMethod> callees = icfg.getCalleesOfCallAt(callSite);
		return callees == null || callees.isEmpty();
	}
	
}
