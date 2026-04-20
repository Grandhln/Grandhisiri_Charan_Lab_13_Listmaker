import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.Scanner;

public class FileListMaker {
	private static final ArrayList<String> list = new ArrayList<>();
	private static boolean needsToBeSaved = false;
	private static String currentFileName = "";
	private static final Scanner pipe = new Scanner(System.in);

	public static void main(String[] args) {
		boolean quit = false;

		do {
			displayMenu();
			String choice = getRegExString("Choice: ", "[AaDdIiMmOoSsCcVvQq]").toUpperCase();

			try {
				switch (choice) {
					case "A": addItem(); break;
					case "D": deleteItem(); break;
					case "I": insertItem(); break;
					case "M": moveItem(); break;
					case "O": openFile(); break;
					case "S": saveFile(); break;
					case "C": clearList(); break;
					case "V": viewList(); break;
					case "Q": quit = quitProgram(); break;
				}
			} catch (IOException e) {
				System.out.println("Error handling file: " + e.getMessage());
			}

		} while (!quit);
	}

	private static void displayMenu() {
		System.out.println("\n--- Current List ---");
		viewList();
		System.out.println("\nMenu Options:");
		System.out.println("A - Add  D - Delete  I - Insert  M - Move");
		System.out.println("O - Open S - Save    C - Clear   V - View  Q - Quit");
		if (needsToBeSaved) {
			System.out.println("(Unsaved Changes Pending)");
		}
	}

	private static void addItem() {
		System.out.print("Enter item to add: ");
		String item = pipe.nextLine();
		list.add(item);
		needsToBeSaved = true;
	}

	private static void deleteItem() {
		if (list.isEmpty()) {
			System.out.println("List is empty.");
			return;
		}
		int index = getRangedInt("Enter index to delete: ", list.size()) - 1;
		list.remove(index);
		needsToBeSaved = true;
	}

	private static void insertItem() {
		int index = getRangedInt("Enter index to insert at: ", list.size() + 1) - 1;
		System.out.print("Enter item: ");
		String item = pipe.nextLine();
		list.add(index, item);
		needsToBeSaved = true;
	}

	private static void moveItem() {
		if (list.isEmpty()) return;
		int from = getRangedInt("Index of item to move: ", list.size()) - 1;
		int to = getRangedInt("Move to which index: ", list.size()) - 1;

		String item = list.remove(from);
		list.add(to, item);
		needsToBeSaved = true;
	}

	private static void clearList() {
		if (getYNConfirm("Are you sure you want to clear the whole list?")) {
			list.clear();
			needsToBeSaved = true;
		}
	}

	private static void viewList() {
		if (list.isEmpty()) {
			System.out.println("[Empty]");
		} else {
			for (int i = 0; i < list.size(); i++) {
				System.out.printf("%d: %s%n", i + 1, list.get(i));
			}
		}
	}

	private static void openFile() throws IOException {
		if (needsToBeSaved) {
			if (getYNConfirm("You have unsaved changes. Save now?")) {
				saveFile();
			}
		}

		System.out.print("Enter filename to open (e.g., list.txt): ");
		String fileName = pipe.nextLine();
		Path path = Paths.get(fileName);

		if (Files.exists(path)) {
			list.clear();
			list.addAll(Files.readAllLines(path));
			currentFileName = fileName;
			needsToBeSaved = false;
			System.out.println("File loaded successfully.");
		} else {
			System.out.println("File not found.");
		}
	}

	private static void saveFile() throws IOException {
		if (currentFileName.isEmpty()) {
			System.out.print("Enter new filename to save (e.g., mylist.txt): ");
			currentFileName = pipe.nextLine();
			if (!currentFileName.toLowerCase().endsWith(".txt")) {
				currentFileName += ".txt";
			}
		}

		Path path = Paths.get(currentFileName);
		Files.write(path, list);
		needsToBeSaved = false;
		System.out.println("File saved as " + currentFileName);
	}

	private static boolean quitProgram() throws IOException {
		if (needsToBeSaved) {
			if (getYNConfirm("You have unsaved changes. Save before quitting?")) {
				saveFile();
			}
		}
		return getYNConfirm("Are you sure you want to quit?");
	}

	private static String getRegExString(String prompt, String regEx) {
		String input;
		do {
			System.out.print(prompt);
			input = pipe.nextLine();
		} while (!input.matches(regEx));
		return input;
	}

	private static int getRangedInt(String prompt, int high) {
		int val = -1;
		boolean valid = false;
		do {
			System.out.print(prompt + " [" + 1 + "-" + high + "]: ");
			if (pipe.hasNextInt()) {
				val = pipe.nextInt();
				pipe.nextLine();
				if (val >= 1 && val <= high) valid = true;
			} else {
				pipe.nextLine();
			}
		} while (!valid);
		return val;
	}

	private static boolean getYNConfirm(String prompt) {
		String input = getRegExString(prompt + " [Y/N]: ", "[YyNn]");
		return input.equalsIgnoreCase("y");
	}
}