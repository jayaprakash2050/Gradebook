/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xmlreader;

/**
 *
 * @author hp pc
 */
public class XMLReader {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Reader read= new Reader();
        read.ReadXML();
         FoodItem item1 = new FoodItem("IN","Mutton",304,"Indian item","Veg",40.0);
         Writer write = new Writer();
         write.WriteIntoXML(item1);
    }
    
}
