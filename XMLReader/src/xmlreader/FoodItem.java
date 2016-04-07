/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xmlreader;

/**
 *
 * @author hp pc
 * <FoodItem country="GB">
        <id>100</id>
        <name>Steak and Kidney Pie</name>
        <description>Tender cubes of steak, with tender lamb kidney is succulent rich gravy.  Served with a side of mashed potatoes and peas.</description>
        <category>Dinner</category>
        <price>15.95</price>
    </FoodItem>
 */


public class FoodItem {

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public FoodItem() {
    }

    public FoodItem(String country, String name, int id, String description, String category, double price) {
        this.country = country;
        this.name = name;
        this.id = id;
        this.description = description;
        this.category = category;
        this.price = price;
    }
    
    private String country;
    private String name;
    private int id;
    private String description;
    private String category;
    private double price;
    
}
