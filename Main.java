import java.time.LocalDate;
import java.util.Scanner;
import java.util.Map;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("Grocery List Creator");

        Person person = createPersonBasedOnUserInput();

        GroceryStore aldi = createAldiStore();
        GroceryStore target = createTargetStore();

        GroceryList groceryList = new GroceryList();
        chooseStoreAndAddItems(aldi, target, groceryList);

        groceryList.setPerson(person);
        System.out.println("\nYour Grocery List:");
        System.out.println(groceryList.toString());
    }

    private static Person createPersonBasedOnUserInput() {
        System.out.print("Are you a student? (yes/no): ");
        String isStudent = scanner.nextLine().toLowerCase();

        System.out.print("Enter your first name: ");
        String givenNames = scanner.nextLine();
        System.out.print("Enter your last name: ");
        String familyName = scanner.nextLine();
        System.out.print("Enter birth year: ");
        int year = scanner.nextInt();
        System.out.print("Enter birth month: ");
        int month = scanner.nextInt();
        System.out.print("Enter birth day: ");
        int day = scanner.nextInt();
        scanner.nextLine(); // Consume the newline

        if (isStudent.equals("yes")) {
            System.out.print("Enter your student ID: ");
            int studentID = scanner.nextInt();
            scanner.nextLine(); // Consume the newline
            return new Student(studentID, familyName, givenNames, LocalDate.of(year, month, day));
        } else {
            return new Person(familyName, givenNames, LocalDate.of(year, month, day));
        }
    }

    private static void chooseStoreAndAddItems(GroceryStore aldi, GroceryStore target, GroceryList groceryList) {
        System.out.println("\nAvailable Grocery Stores:");
        System.out.println("1. Aldi");
        System.out.println("2. Target");

        GroceryStore chosenStore = null;
        while (chosenStore == null) {
            System.out.print("Enter the number of the store you want to shop at: ");
            int storeChoice = readInt();
            if (storeChoice == 1) {
                chosenStore = aldi;
            } else if (storeChoice == 2) {
                chosenStore = target;
            } else {
                System.out.println("Invalid choice.");
            }
        }
        addItemsFromStore(chosenStore, groceryList);
    }

    private static void addItemsFromStore(GroceryStore store, GroceryList groceryList) {
        System.out.println("\nAvailable Items in " + store.GroceryStore() + ":");
        Map<String, GroceryItem> stock = store.getCatalog();
        stock.forEach((name, item) -> System.out.println(name + " - $" + item.getUnitPrice() + " in " + item.getLocation()));

        System.out.println("Enter the name of the item you want to add (or 'done' to finish):");
        String itemName = scanner.nextLine();
        while (!itemName.equalsIgnoreCase("done")) {
            if (stock.containsKey(itemName)) {
                System.out.print("Enter quantity: ");
                int quantity = readInt();
                GroceryItemOrder order = new GroceryItemOrder(itemName, quantity, stock.get(itemName).getUnitPrice());
                groceryList.add(order);
                System.out.println(quantity + " " + itemName + " added to the list.");
            } else {
                System.out.println("Item not found.");
            }
            System.out.println("Enter the name of the next item you want to add (or 'done' to finish):");
            itemName = scanner.nextLine();
        }
    }

    // Utility method for reading integers safely
    private static int readInt() {
        while (!scanner.hasNextInt()) {
            System.out.println("Invalid input. Please enter a number.");
            scanner.next(); // Consume the incorrect input
            scanner.nextLine(); // Move to the next line
        }
        int number = scanner.nextInt();
        scanner.nextLine(); // Consume the newline left-over
        return number;
    }

    // Methods for creating Aldi and Target stores with sample items
    private static GroceryStore createAldiStore() {
        GroceryStore aldi = new GroceryStore("Aldi");
        aldi.addGroceryItem(new GroceryItem("Apples", 0.99, "Aisle 1"),
                // Continuing from the addOrUpdateItem method calls for the Aldi store
                0.99, "Aisle 1");
        aldi.addGroceryItem(new GroceryItem("Bread", 1.29, "Aisle 2"), 1.29, "Aisle 2");
        aldi.addGroceryItem(new GroceryItem("Milk", 0.89, "Aisle 3"), 0.89, "Aisle 3");
        return aldi;
    }

    private static GroceryStore createTargetStore() {
        GroceryStore target = new GroceryStore("Target");
        target.addGroceryItem(new GroceryItem("Eggs", 2.09, "Aisle 1"), 2.09, "Aisle 1");
        target.addGroceryItem(new GroceryItem("Pasta", 0.99, "Aisle 2"), 0.99, "Aisle 2");
        target.addGroceryItem(new GroceryItem("Rice", 1.49, "Aisle 3"), 1.49, "Aisle 3");
        target.addGroceryItem(new GroceryItem("Chicken", 5.49, "Aisle 4"), 5.49, "Aisle 4");
        return target;
    }
}

// Utility method for reading integers safely, already defined
