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
public class StockSearchClass {
    
     private String pcode;
    private String pname;
     private String uprice;
    private String qty;

    public StockSearchClass(String pcode, String pname, String uprice, String qty) {
        this.pcode = pcode;
        this.pname = pname;
        this.uprice = uprice;
        this.qty = qty;
    }

    public String getPcode() {
        return pcode;
    }

    public String getPname() {
        return pname;
    }

    public String getUprice() {
        return uprice;
    }

    public String getQty() {
        return qty;
    }

    
}
