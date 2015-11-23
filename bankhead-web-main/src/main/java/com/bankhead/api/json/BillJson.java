package com.bankhead.api.json;

import com.bankhead.data.model.Bill;
import com.bankhead.data.model.BillVersion;

/**
 * Created By: jordancote
 * Created On: 1/3/14
 */
public class BillJson {
    private String title;
    private int number;
    private String description;

    public BillJson() {}

    public BillJson(BillVersion billVersion) {
        Bill bill = billVersion.getParentBill();
        setTitle(billVersion.getTitle());
        setNumber(bill.getNumber());
        setDescription(billVersion.getDescription());
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
