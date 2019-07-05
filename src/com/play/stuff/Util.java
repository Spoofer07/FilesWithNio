package com.play.stuff;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static java.util.Objects.*;

public class Util {

	public static String randomString = "Lorem Imposum";
	public static String randomString2 = "This is a new string";
	public static String randomString3 = "Appendable string\n";
	public static String lineBreak = "\n";

	/*
	 * Basic fileCreating; I use the getProperty to go directly into the home of the
	 * current logged user; I can alse use a trick like 'getenv' which goes in the
	 * APPDATA/roaming folder of the windows this can be useful to quick acces the
	 * hidden folders of the PC(ex: the temp folder)
	 * TODO there are more ways to create a file(ex: createFile) 
	 */
	public static void writingRandomFileOnDesktop() {
		Path path = Paths.get(System.getProperty("user.home") + "/Desktop").resolve("randomFile.txt");
//		Path path = Paths.get(System.getenv("APPDATA")).resolve("randomFile.txt");
//		Path path = Paths.get(System.getenv("TEMP")).resolve("randomFile.txt");

		try {
			Files.write(path, randomString.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/*
	 * By default if we do the same thing again and the file already exists, it will
	 * overwrite the content
	 */
	public static void replaceContent() {
		Path path = Paths.get(System.getProperty("user.home") + "/Desktop").resolve("randomFile.txt");

		try {
			Files.write(path, randomString2.getBytes());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * If you want to append to the file you need to suggest an option like seen
	 * below
	 */
	public static void appendContent() {
		Path path = Paths.get(System.getProperty("user.home") + "/Desktop").resolve("randomFile.txt");

		try {
			Files.write(path, System.lineSeparator().getBytes(), StandardOpenOption.APPEND);
			Files.write(path, randomString3.getBytes(), StandardOpenOption.APPEND);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * Reading the contents of the file; readAll extracts each line and puts it into
	 * a list
	 */
	public static void readingContent() {
		Path path = Paths.get(System.getProperty("user.home") + "/Desktop").resolve("randomFile.txt");

		List<String> fileContent = new ArrayList<String>();
		try {
			fileContent = Files.readAllLines(path);
		} catch (IOException e) {
			e.printStackTrace();
		}

		fileContent.stream().skip(1).map(s -> s.substring(3)).forEach(System.out::println);
	}

	/*
	 * Verify if a certain file exists at a certain file location and do something
	 * .isReadable method is one way, but I don't think is the best way (maybe even isWritable)
	 */
	public static void verifyExistance1() {
		Path path = Paths.get(System.getProperty("user.home") + "/Desktop").resolve("randomFile.txt");

		System.out.println(Files.isReadable(path));
	}

	/*
	 * Finding all files in a path(I think u must pass a folder path), that respect a predicate
	 * 
	 */
	public static void findInPath() {
		Path testPath = Paths.get(System.getProperty("user.home") + "/Desktop/files");

		try {
			Stream<Path> stream = Files.find(testPath, 100, (path, basicFileAttributes) -> {
																						File file = path.toFile();
																						return !file.isDirectory() && file.getName().contains("1");
																								});
			stream.forEach(System.out::println);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		System.out.println(Files.isReadable(testPath));
	}
}
