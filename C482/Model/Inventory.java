package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Class Inventory.java contains data structures storing
 * all part and product objects.
 */
public class Inventory {
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    /**
     * @param newPart the new part to be added to list allParts
     */
    public static void addPart(Part newPart) {
        allParts.add(newPart);
    }

    /**
     * @param newProduct the new part to be added to list allProducts
     */
    public static void addProduct(Product newProduct) {
        allProducts.add(newProduct);
    }

    /**
     * @param partId a part ID used to look up a part in allParts list.
     * @return The part matching the ID or null if no part is found.
     */
    public static Part lookupPart(int partId) {
       for (Part part : allParts) {
           if (part.getId() == partId) {
               return part;
           }
       }
       return null;
    }

    /**
     * @param productId a product ID used to look up a product in allProducts list.
     * @return The product matching the ID or null if no product is found.
     */
    public static Product lookupProduct(int productId) {
       for (Product product : allProducts) {
           if (product.getId() == productId) {
               return product;
           }
       }
       return null;
    }

    /**
     * @param partName a string used to look up a part by name in allParts list.
     * @return A list of parts that contain partName in the part name.
     */
    public static ObservableList<Part> lookupPart(String partName) {
        ObservableList<Part> selectedParts = FXCollections.observableArrayList();
        //iterate through list of all parts and add parts with the specified name to the return list
        for(Part part : allParts) {
            if (part.getName().toLowerCase().contains(partName.toLowerCase())) {
                selectedParts.add(part);
            }
        }
        return selectedParts;
    }

    /**
     * @param productName a string used to look up a product by name in allProducts list.
     * @return A list of products that contain productName in the product name.
     */
    public static ObservableList<Product> lookupProduct(String productName) {
        ObservableList<Product> selectedProducts = FXCollections.observableArrayList();
        //iterate through list of all products and add products with the specified name to the return list
        for(Product product : allProducts) {
            if (product.getName().toLowerCase().contains(productName.toLowerCase())) {
                selectedProducts.add(product);
            }
        }
        return selectedProducts;
    }

    /**
     * @param index, selectedPart an index location in the allParts list and a part object.
     * Uses the selectedPart to replace the old part data at the index location.
     */
    public static void updatePart(int index, Part selectedPart) {
        allParts.set(index, selectedPart);
    }

    /**
     * @param index, newProduct an index location in the allProducts list and a product object.
     * Uses the newProduct to replace the old product data at the index location.
     */
    public static void updateProduct(int index, Product newProduct) {
        allProducts.set(index, newProduct);
    }

    /**
     * @param selectedPart a part object to be removed from allParts list.
     * @return true if selectedPart was removed from allParts, false otherwise.
     */
    public static boolean deletePart(Part selectedPart) {
        return allParts.remove(selectedPart);
    }

    /**
     * @param selectedProduct a product object to be removed from allProducts list.
     * @return true if selectedProduct was removed from allProducts, false otherwise.
     */
    public static boolean deleteProduct(Product selectedProduct) {
        return allProducts.remove(selectedProduct);
    }

    /**
     * @return the list containing all parts, allParts.
     */
    public static ObservableList<Part> getAllParts() {
        return allParts;
    }

    /**
     * @return the list containing all products, allPproducts.
     */
    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }
}
