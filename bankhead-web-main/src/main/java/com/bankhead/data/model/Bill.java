package com.bankhead.data.model;

import com.bankhead.data.DataModel;

import javax.persistence.*;
import java.util.HashMap;

/*
 * BILL
 * A written proposal to change existing law or enact a new law prepared by the Legislative Commissioners’ Office.
 */
@Entity
@Table(name="bill")
public class Bill extends DataModel {

    @Id
    @GeneratedValue
    private long id;

    /* BILL NUMBER
     * Number given to each bill by the House or Senate clerk when it is first introduced in a legislative session. 
     * Senate bills are numbered 1 to 4999; House bills are numbered 5000 and up.
     */
    @Basic
	private int number = -1;

	/* BILL STATUS
	 * The stage in the legislative process that a bill has reached at a given time. 
	 * A summary of a bill’s status shows all the action taken on the bill up to the moment the status is requested.
	 */
    @Basic
	private String status = null;
	
	/* CALENDAR NUMBER
	 * The number each bill receives when it is placed on the calendar for the first time. 
	 * Bills are renumbered in chronological order based on when they go onto the calendar.
	 * Thus, a low calendar number indicates a bill that has been awaiting action since early in the session.
	 */
    @Basic
	private int calendarNumber = -1;
		
	/* SPONSOR
	 * The originator of a legislative proposal, either a legislator or a committee.
	 */
    //@Transient
	//private LinkedList<Sponsor> sponsors = null;

    @Transient
	private HashMap<Integer,BillVersion> versions = null;

    @OneToOne
    @JoinColumn(name = "current_version_id")
    private BillVersion currentVersion;

	public Bill() {
		versions = new HashMap<Integer,BillVersion>();
	//	sponsors = new LinkedList<Sponsor>();
	}
	
	public Bill(int number) {
		this();
		this.number = number;
	}

	public boolean isHouseBill() {
		return (number >= 5000);
	}
	public boolean isSenateBill() {
		return (number > 0 && number < 5000);
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getCalendarNumber() {
		return calendarNumber;
	}

	public void setCalendarNumber(int calendarNumber) {
		this.calendarNumber = calendarNumber;
	}
/*
	public LinkedList<Sponsor> getSponsors() {
		return sponsors;
	}

	public void setSponsors(LinkedList<Sponsor> sponsors) {
		this.sponsors = sponsors;
	}

	public void addSponsor(Sponsor sponsor) {
		this.sponsors.add(sponsor);
	}
*/
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public BillVersion getCurrentVersion() {
        return currentVersion;
    }

    public void setCurrentVersion(BillVersion currentVersion) {
        this.currentVersion = currentVersion;
    }

    public boolean hasVersion(int revision) {
		return (versions.get(revision) != null);
	}
	
	public BillVersion getOrAddVersion(int revision) {
		BillVersion billVersion = versions.get(revision);
		if (billVersion == null) {
			addVersion(revision);
		}
		return billVersion;
	}
	
	public BillVersion getVersion(int revision) {
		return versions.get(revision);
	}

	public BillVersion addVersion(int revision) {
		BillVersion version = new BillVersion(revision);
		versions.put(revision, version);
		return version;
	}
	
	public void addVersion(int revision, BillVersion version) {
		versions.put(revision, version);
	}

}
