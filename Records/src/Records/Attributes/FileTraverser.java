package Records.Attributes;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.File;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.io.FilenameUtils;

import Records.Database.LibraryUpdaterImp;
import Records.Main.RecordsMain;

// TODO: Auto-generated Javadoc
/**
 * The Class FileTraverser.
 *
 * @author Alexander
 */
class FileTraverser {

	/**
	 * Import songs.
	 *
	 * @param Dir
	 *            the dir
	 */
	static void importSongs(String Dir) {
		System.out.println("FileTraverser.importSongs(" + Dir + ")");

		List<File> fileList = new LinkedList<>();
		walkTree(new File(Dir), fileList);
		LibraryMetadataExtractor meta;
		for (File f : fileList) {
			meta = new LibraryMetadataExtractor(f);
			LibraryDataSets LDS = new LibraryDataSets(meta.extract());
			LDS.insertLibraryData();
		}
		System.out.println("Library Import Complete!");
		LibraryUpdaterImp.startUpdate();
	}

	/**
	 * Walk tree.
	 *
	 * @param topFile
	 *            the top file
	 * @param fileList
	 *            the file list
	 */
	private static void walkTree(File topFile, List<File> fileList) {
		System.out.println("FileTraverser.walkTree()");

		if (topFile.isDirectory()) {
			for (File f : topFile.listFiles()) {
				walkTree(f, fileList);
			}
		} else if (topFile.isFile()) {
			if (FilenameUtils.isExtension(topFile.getAbsolutePath(), "m4a")
					|| FilenameUtils.isExtension(topFile.getAbsolutePath(),
							"mp3")) {
				fileList.add(topFile);
				RecordsMain.addToFilePathList(topFile.getAbsolutePath());
			}
		}
	}
}
