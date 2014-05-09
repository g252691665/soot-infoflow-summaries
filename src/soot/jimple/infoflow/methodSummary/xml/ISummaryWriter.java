package soot.jimple.infoflow.methodSummary.xml;

import javax.xml.stream.XMLStreamException;

import soot.jimple.infoflow.methodSummary.data.summary.MethodSummaries;


public interface ISummaryWriter {
	public void write(MethodSummaries flow) throws XMLStreamException;
}
