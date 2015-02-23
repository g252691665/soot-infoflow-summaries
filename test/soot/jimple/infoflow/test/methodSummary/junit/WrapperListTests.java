package soot.jimple.infoflow.test.methodSummary.junit;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.stream.XMLStreamException;

import org.junit.Ignore;
import org.junit.Test;

import soot.jimple.infoflow.Infoflow;
import soot.jimple.infoflow.methodSummary.taintWrappers.TaintWrapperFactory;
import soot.jimple.infoflow.taintWrappers.ITaintPropagationWrapper;

@Ignore
public class WrapperListTests extends JUnitTests {
	static File files = new File("testSummaries");//String[] files =  {"testSummaries\\LinkedList.xml","testSummaries\\ArrayList.xml","testSummaries\\Stack.xml"};
	protected final ITaintPropagationWrapper wrapper;

	public WrapperListTests() throws FileNotFoundException, XMLStreamException {
			wrapper = (ITaintPropagationWrapper) TaintWrapperFactory.createTaintWrapper(files);
		}

	@Test
	public void concreteArrayListPos0Test() {
		Infoflow infoflow = initInfoflow();
		List<String> epoints = new ArrayList<String>();
		epoints.add("<soot.jimple.infoflow.test.ListTestCode: void concreteWriteReadPos0Test()>");
		infoflow.setTaintWrapper(wrapper);
		infoflow.computeInfoflow(appPath, libPath, epoints, sources, sinks);
		checkInfoflow(infoflow, 1);
	}

	@Test
	public void concreteArrayListPos1Test() {
		Infoflow infoflow = initInfoflow();
		List<String> epoints = new ArrayList<String>();
		epoints.add("<soot.jimple.infoflow.test.ListTestCode: void concreteWriteReadPos1Test()>");
		infoflow.setTaintWrapper(wrapper);
		infoflow.computeInfoflow(appPath, libPath, epoints, sources, sinks);
		checkInfoflow(infoflow, 1);
	}

	@Test
	public void concreteArrayListNegativeTest() {
		Infoflow infoflow = initInfoflow();
		List<String> epoints = new ArrayList<String>();
		epoints.add("<soot.jimple.infoflow.test.ListTestCode: void concreteWriteReadNegativeTest()>");
		infoflow.setTaintWrapper(wrapper);
		infoflow.computeInfoflow(appPath, libPath, epoints, sources, sinks);
		negativeCheckInfoflow(infoflow);
	}

	@Test
	public void listTest() {
		Infoflow infoflow = initInfoflow();
    	List<String> epoints = new ArrayList<String>();
    	epoints.add("<soot.jimple.infoflow.test.ListTestCode: void writeReadTest()>");
    	infoflow.setTaintWrapper(wrapper);
    	infoflow.computeInfoflow(appPath, libPath, epoints, sources, sinks);
		checkInfoflow(infoflow, 1);
	}

	@Test
	public void listIteratorTest() {
		Infoflow infoflow = initInfoflow();
		List<String> epoints = new ArrayList<String>();
		epoints.add("<soot.jimple.infoflow.test.ListTestCode: void iteratorTest()>");
		infoflow.setTaintWrapper(wrapper);
		infoflow.computeInfoflow(appPath, libPath, epoints, sources, sinks);
		checkInfoflow(infoflow, 1);
	}

	@Test
	public void listsubListTest() {
		Infoflow infoflow = initInfoflow();
		List<String> epoints = new ArrayList<String>();
		epoints.add("<soot.jimple.infoflow.test.ListTestCode: void subListTest()>");
		infoflow.setTaintWrapper(wrapper);
		infoflow.computeInfoflow(appPath, libPath, epoints, sources, sinks);
		checkInfoflow(infoflow, 1);
	}

	@Test
	public void concreteLinkedListNegativeTest() {
		Infoflow infoflow = initInfoflow();
		List<String> epoints = new ArrayList<String>();
		epoints.add("<soot.jimple.infoflow.test.ListTestCode: void linkedListConcreteWriteReadNegativeTest()>");
		infoflow.computeInfoflow(appPath, libPath, epoints, sources, sinks);
		infoflow.setTaintWrapper(wrapper);
		negativeCheckInfoflow(infoflow);
	}

	@Test
	public void concreteLinkedListTest() {
		Infoflow infoflow = initInfoflow();
		List<String> epoints = new ArrayList<String>();
		epoints.add("<soot.jimple.infoflow.test.ListTestCode: void linkedListConcreteWriteReadTest()>");
		infoflow.setTaintWrapper(wrapper);
		infoflow.computeInfoflow(appPath, libPath, epoints, sources, sinks);
		checkInfoflow(infoflow, 1);
	}

	@Test
	public void writeReadLinkedListTest() {
		Infoflow infoflow = initInfoflow();
		List<String> epoints = new ArrayList<String>();
		epoints.add("<soot.jimple.infoflow.test.ListTestCode: void linkedListWriteReadTest()>");
		infoflow.setTaintWrapper(wrapper);
		infoflow.computeInfoflow(appPath, libPath, epoints, sources, sinks);
		checkInfoflow(infoflow, 1);
	}

	@Test
	public void concreteLinkedListIteratorTest() {
		Infoflow infoflow = initInfoflow();
		List<String> epoints = new ArrayList<String>();
		epoints.add("<soot.jimple.infoflow.test.ListTestCode: void linkedListIteratorTest()>");
		infoflow.setTaintWrapper(wrapper);
		infoflow.computeInfoflow(appPath, libPath, epoints, sources, sinks);
		checkInfoflow(infoflow, 1);
	}

	@Test
	public void subLinkedListTest() {
		Infoflow infoflow = initInfoflow();
		List<String> epoints = new ArrayList<String>();
		epoints.add("<soot.jimple.infoflow.test.ListTestCode: void linkedListSubListTest()>");
		infoflow.setTaintWrapper(wrapper);
		infoflow.computeInfoflow(appPath, libPath, epoints, sources, sinks);
		checkInfoflow(infoflow, 1);
	}

	@Test
	public void stackGetTest() {
		Infoflow infoflow = initInfoflow();
		List<String> epoints = new ArrayList<String>();
		epoints.add("<soot.jimple.infoflow.test.ListTestCode: void concreteWriteReadStackGetTest()>");
		infoflow.setTaintWrapper(wrapper);
		infoflow.computeInfoflow(appPath, libPath, epoints, sources, sinks);
		checkInfoflow(infoflow, 1);
	}

	@Test
	public void stackPeekTest() {
		Infoflow infoflow = initInfoflow();
		List<String> epoints = new ArrayList<String>();
		epoints.add("<soot.jimple.infoflow.test.ListTestCode: void concreteWriteReadStackPeekTest()>");
		infoflow.setTaintWrapper(wrapper);
		infoflow.computeInfoflow(appPath, libPath, epoints, sources, sinks);
		checkInfoflow(infoflow, 1);
	}

	@Test
	public void stackPopTest() {
		Infoflow infoflow = initInfoflow();
		List<String> epoints = new ArrayList<String>();
		epoints.add("<soot.jimple.infoflow.test.ListTestCode: void concreteWriteReadStackPopTest()>");
		infoflow.setTaintWrapper(wrapper);
		infoflow.computeInfoflow(appPath, libPath, epoints, sources, sinks);
		checkInfoflow(infoflow, 1);
	}

	@Test
	public void stackNegativeTest() {
		Infoflow infoflow = initInfoflow();
		List<String> epoints = new ArrayList<String>();
		epoints.add("<soot.jimple.infoflow.test.ListTestCode: void concreteWriteReadStackNegativeTest()>");
		infoflow.setTaintWrapper(wrapper);
		infoflow.computeInfoflow(appPath, libPath, epoints, sources, sinks);
		negativeCheckInfoflow(infoflow);
	}
	
    protected Infoflow initInfoflow(){
    	return initInfoflow(false);
    }
    
    protected Infoflow initInfoflow(boolean useTaintWrapper){
    	Infoflow result = new Infoflow();
    	WrapperListTestConfig testConfig = new WrapperListTestConfig();
    	result.setSootConfig(testConfig);
    	result.setTaintWrapper(wrapper);
    	return result;
    }

}