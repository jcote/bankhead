package com.bankhead.api.resource;

import com.bankhead.api.json.BillJson;
import com.bankhead.data.DataStore;
import com.bankhead.data.model.Bill;
import com.bankhead.data.model.BillVersion;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created By: jordancote
 * Created On: 12/30/13
 */
@Singleton
@Path("bill/")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class BillResource {
    private final DataStore dataStore;
    private final Logger logger;

    @Inject
    public BillResource(DataStore dataStore, Logger logger) {
        this.dataStore = dataStore;
        this.logger = logger;
    }

    @POST
    @Path("bill")
    public void postBill(BillJson billJson) {
        Bill bill = new Bill();
        BillVersion billVersion = new BillVersion();
        bill.setCurrentVersion(billVersion);
        billVersion.setParentBill(bill);
        bill.setNumber(billJson.getNumber());
        billVersion.setTitle(billJson.getTitle());
        billVersion.setDescription(billJson.getDescription());
        dataStore.save(bill, billVersion);
    }

    @GET
    @Path("bills")
    public List<BillJson> getBills() {
        List<Bill> bills = dataStore.loadCurrentBills();
        List<BillJson> billJsons = new ArrayList<>();
        for (Bill bill : bills) {
            BillJson billJson = new BillJson(bill.getCurrentVersion());
            billJsons.add(billJson);
        }
        return billJsons;
    }
}
