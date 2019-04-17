package sorcer.pml.modeling;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sorcer.test.ProjectContext;
import org.sorcer.test.SorcerTestRunner;
import sorcer.core.context.model.EntModel;
import sorcer.pml.model.EntryModeler;
import sorcer.service.Invocation;
import sorcer.service.Task;
import sorcer.service.modeling.Model;

import static org.junit.Assert.assertTrue;
import static sorcer.co.operator.outPaths;
import static sorcer.eo.operator.*;
import static sorcer.ent.operator.invoke;
import static sorcer.so.operator.*;

/**
 * @author Mike Sobolewski
 */
@RunWith(SorcerTestRunner.class)
@ProjectContext("examples/cml")
public class EntModelServices {
	private final static Logger logger = LoggerFactory.getLogger(EntModelServices.class
			.getName());
	
	@Test
	public void callModelerTest() throws Exception {

		EntModel em = EntryModeler.getEntryModel();
		logger.info("outDispatcher: " + invoke(em, "expr"));
		assertTrue(invoke(em, "expr").equals(60.0));

	}

	@Test
	public void callObjectModelServiceTest() throws Exception {

		Model em = EntryModeler.getEntryModel();
		Task pmt = task(sig("invoke", em),
				context(result("invoke/outDispatcher", outPaths("expr"))));

		assertTrue(exec(pmt).equals(60.0));

		assertTrue(get(exert(pmt), "invoke/outDispatcher").equals(60.0));

	}

	@Test
	public void callNetModelServiceTest() throws Exception {

		// the provider in ex6/bin parmodel-prv-run.xml
		Task emt = task(sig("invoke", Invocation.class, prvName("EntModel Service")),
				context(result("invoke/outDispatcher", outPaths("expr"))));

		assertTrue(exec(emt).equals(60.0));
	}
	
}
