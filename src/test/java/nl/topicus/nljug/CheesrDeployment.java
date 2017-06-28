package nl.topicus.nljug;

import java.io.File;

import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;

public class CheesrDeployment {
	// tag::createWar[]
	public static WebArchive createWar() {
		File[] dependencies = 
			Maven.configureResolver()
				.workOffline()
				.loadPomFromFile("pom.xml") // <1>
				.importCompileAndRuntimeDependencies()
				.resolve()
				.withTransitivity()
				.asFile(); // <2>

		WebArchive war = 
				ShrinkWrap.create(WebArchive.class)
				.addAsWebInfResource(new File("src/main/webapp/WEB-INF/web.xml"))
				.addAsLibraries(dependencies)
				.addAsResource(new File("target/classes"), ""); // <3>

		return war;
	}
	// end::createWar[]
}
