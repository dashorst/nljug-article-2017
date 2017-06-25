package nl.topicus.nljug;

import java.io.File;
import java.nio.file.Paths;

import org.jboss.shrinkwrap.api.GenericArchive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.importer.ExplodedImporter;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.jboss.shrinkwrap.resolver.api.maven.PomEquippedResolveStage;

public class CheesrDeployment {
	public static WebArchive createWar() {
		PomEquippedResolveStage pom = Maven.configureResolver().workOffline().loadPomFromFile("pom.xml");

		GenericArchive webapp = ShrinkWrap.create(GenericArchive.class).as(ExplodedImporter.class)
				.importDirectory("src/main/webapp").as(GenericArchive.class);

		File[] dependencies = pom.importCompileAndRuntimeDependencies().resolve().withTransitivity().asFile();

		WebArchive war = ShrinkWrap.create(WebArchive.class);
		war.addAsLibraries(dependencies).addAsResource(Paths.get(".", "target/classes").toFile(), "").merge(webapp);
		return war;
	}
}
