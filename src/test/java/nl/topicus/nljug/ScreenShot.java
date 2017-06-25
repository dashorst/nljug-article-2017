package nl.topicus.nljug;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.imageio.ImageIO;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.cropper.indent.IndentCropper;
import ru.yandex.qatools.ashot.cropper.indent.MonochromeFilter;

public class ScreenShot
{
	public static void takeScreenshot(WebDriver browser, String name)
	{
		takeScreenshot(browser, name, null);
	}

	public static void takeScreenshot(WebDriver browser, String name, By crop)
	{
		boolean popover = crop != null && crop.toString().contains(".popover");

		try
		{
			Screenshot screenshot;
			if (crop == null)
			{
				screenshot = new AShot().takeScreenshot(browser);
			}
			else
			{
				if (popover)
				{
					// bij popovers even wachten op de animatie van 200ms;
					Thread.sleep(200);
				}
				screenshot = new AShot()
					.imageCropper(new IndentCropper(25).addIndentFilter(new MonochromeFilter()))
					.takeScreenshot(browser, browser.findElements(crop));
			}
			Path targetFile = Paths.get("target/screenshots", "cheesr-" + name + ".png");
			Files.createDirectories(targetFile.getParent());
			ImageIO.write(screenshot.getImage(), "png", targetFile.toFile());
		}
		catch (IOException | InterruptedException e)
		{
			throw new RuntimeException(e);
		}
	}
}
