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
public class SearchSalesClass {
    
    private String datetime;
    private String pcode;
    private String pname;
    private String qty;
    private String uprice;
    private String amount;

    public SearchSalesClass(String datetime, String pcode, String pname, String qty, String uprice, String amount) {
        this.datetime = datetime;
        this.pcode = pcode;
        this.pname = pname;
        this.qty = qty;
        this.uprice = uprice;
        this.amount = amount;
    }

    public String getDatetime() {
        return datetime;
    }

    public String getPcode() {
        return pcode;
    }

    public String getPname() {
        return pname;
    }

    public String getQty() {
        return qty;
    }

    public String getUprice() {
        return uprice;
    }

    public String getAmount() {
        return amount;
    }
    
    
    
}
