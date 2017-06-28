package nl.topicus.nljug;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.imageio.ImageIO;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.cropper.indent.IndentCropper;
import ru.yandex.qatools.ashot.cropper.indent.MonochromeFilter;

public class Camera {
	// tag::singleshot[]
	public static void takeScreenshot(WebDriver browser, String name) {
		BufferedImage screenshot = new AShot()
			.takeScreenshot(browser)
			.getImage();

		saveScreenshot(name, screenshot);
	}
	// end::singleshot[]

	// tag::multishot[]
	public static void takeScreenshot(WebDriver browser, String name, By crop) {
		IndentCropper cropper = new IndentCropper(25)
			.addIndentFilter(new MonochromeFilter());

		BufferedImage screenshot = new AShot()
			.imageCropper(cropper)
			.takeScreenshot(browser, browser.findElements(crop))
			.getImage();

		saveScreenshot(name, screenshot);
	}
	// end::multishot[]

	private static void saveScreenshot(String name, BufferedImage screenshot) {
		try {
			Path targetFile = Paths.get("target/screenshots", name);
			Files.createDirectories(targetFile.getParent());
			ImageIO.write(screenshot, "png", targetFile.toFile());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
