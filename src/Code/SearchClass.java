/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Code;

/**
 *
 * @author Lakmina
 */
public class SearchClass {
    
    private String Pcode;
    private String Pname;
    private String Price;

    public SearchClass(String Pcode, String Pname, String Price) {
        this.Pcode = Pcode;
        this.Pname = Pname;
        this.Price = Price;
    }

    public String getPcode() {
        return Pcode;
    }

    public String getPname() {
        return Pname;
    }

    public String getPrice() {
        return Price;
    }
    
    
}
