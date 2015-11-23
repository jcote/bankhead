package com.bankhead.data.model;


import com.bankhead.data.DataModel;

import javax.persistence.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
@Table(name="billversion")
public class BillVersion extends DataModel {
	public enum Type {
		NONE,
		OTHER,
		RESOLUTION,
		PROPOSED,
		RAISED,
		GOVERNORS,
		SUBSTITUTE,
		FILE,
	}

    @Id
    @GeneratedValue
    private long id;

    @OneToOne(mappedBy = "currentVersion")
	private Bill parentBill;
	
	/* LCO NUMBER
	 * A number assigned to each item drafted in LCO. 
	 * Each version of a bill and each amendment has a different LCO number. 
	 * The number is used to identify versions of a bill or amendments to a bill,
	 *  before they receive their number or letter designations.
	 */
    @Basic
	private int lcoNumber;
	
	/* FILE NUMBER
	 * Number given to each bill reported out of a committee and printed and ready for House or Senate action. 
	 * If a new version of a bill is printed because of amendments by one house, it receives a new file number. 
	 * Files are numbered from one ("1"), regardless of whether they are House or Senate bills.
	 */
    @Basic
	private int fileNumber;

    @Basic
	private int revisionNumber;

	/* BILL TITLE
	 * 
	 */
    @Basic
	private String title = null;

    @Transient
	private String referredTo = null;

//    @Transient
//	private List<Sponsor> sponsors = new ArrayList<Sponsor>();
	
	/* BILL SECTIONS
	 * The sections of the bill version.
	 * These are the main content of fully drafted bills (not resolution or proposed bill)
	 */
//    @Transient
//	private ArrayList<BillSection> billSections = null;
	
	/* RESOLUTION
	 * The resoltion declared by this Bill Version (if it is a resolution)
	 */
//    @Transient
//	private ResolutionBillSection resolution = null;
	
	/* PROPOSED BILL
	 * The text of the bill version if it is not fully drafted (a proposed bill)
	 */
    @Basic
	private String proposedText = null;
	
	/* STATEMENT OF PURPOSE
	 * 
	 */
    @Basic
	private String statementOfPurpose;

	/* DATE
	 * The date this Bill Version was published.
	 */
    @Temporal(value = TemporalType.DATE)
	private Date date;
	
	/* ID
	 * That weird ID code on the draft versions of Bills.
	 */
    @Transient
	private String idCode;
	
	/* DESCRIPTION
	 * A brief description of the bill.
	 */
    @Basic
    @Column(columnDefinition="text")
	private String description;

    @Basic
    private String rawHtml;

	public BillVersion() {
		//billSections = new ArrayList<BillSection>();
	}

	public BillVersion(int revision) {
		this();
		this.revisionNumber = revision;
	}
/*
	public BillSection getBillSection(int number) {
		return billSections.get(number);
	}
*/
    public String getRawHtml() {
        return rawHtml;
    }

    public void setRawHtml(String rawHtml) {
        this.rawHtml = rawHtml;
    }

    public int getLcoNumber() {
        return lcoNumber;
    }

    public void setLcoNumber(int lcoNumber) {
        this.lcoNumber = lcoNumber;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    /* addSectionMod
         * @param number: The Bill Section number of the Bill version.
         * @param mod: The object containing details on what Section is modified and how.
         *
         * Bill drafts use the word Section to declare an addition or change to existing law.
         * For a section modification, there are 2 Section numbers: the one from the draft
         *  and the one being modified.  The number here is the one from the draft.
         */

	public Bill getParentBill() {
		return parentBill;
	}

	public void setParentBill(Bill bill) {
		this.parentBill = bill;
	}

	public int getLCONumber() {
		return lcoNumber;
	}

	public void setLCONumber(int lCONumber) {
		lcoNumber = lCONumber;
	}

	public String getReferredTo() {
		return referredTo;
	}

	public void setReferredTo(String referredTo) {
		this.referredTo = referredTo;
	}
/*
	public void addBillSection(int number, BillSection billSection) {
		billSections.add(number, billSection);
	}

	public ResolutionBillSection getResolution() {
		return resolution;
	}

	public void setResolution(ResolutionBillSection resolution) {
		this.resolution = resolution;
	}

	public ResolutionBillSection getOrCreateResolution() {
		if (this.resolution == null) {
		    this.resolution = new ResolutionBillSection();
		}
		return this.resolution;
	}
	*/
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
	public void setDate(String date) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("MMMMM d yyyy");
		this.date = sdf.parse(date);
	}
	
	public int getFileNumber() {
		return fileNumber;
	}

	public void setFileNumber(int fileNumber) {
		this.fileNumber = fileNumber;
	}

	public String getProposedText() {
		return proposedText;
	}

	public void setProposedText(String proposedText) {
		this.proposedText = proposedText;
	}
	
	public boolean isProposed() {
		return (this.proposedText != null);
	}
/*
	public boolean isResolution() {
		return (this.resolution != null);
	}
*/
    public String getIdCode() {
        return idCode;
    }

    public void setIdCode(String idCode) {
        this.idCode = idCode;
    }

    public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getStatementOfPurpose() {
		return statementOfPurpose;
	}

	public void setStatementOfPurpose(String statementOfPurpose) {
		this.statementOfPurpose = statementOfPurpose;
	}
	
	public int getRevisionNumber() {
		return revisionNumber;
	}

	public void setRevisionNumber(int revisionNumber) {
		this.revisionNumber = revisionNumber;
	}
/*
	public List<Sponsor> getSponsors() {
		return sponsors;
	}

	public void setSponsors(List<Sponsor> sponsors) {
		this.sponsors = sponsors;
	}

	public void addSponsor(Sponsor sponsor) {
		this.sponsors.add(sponsor);
	}
*/
}
