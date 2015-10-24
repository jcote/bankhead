package com.bankhead.data.model;

import com.bankhead.data.DMY;
import com.bankhead.data.DataModel;
import com.bankhead.data.InvalidDateException;

import javax.persistence.*;

/**
 * Created By: jordancote
 * Created On: 12/7/13
 */
@Entity
@Table(name="voter", uniqueConstraints = {@UniqueConstraint(columnNames={"first_name", "last_name", "date_of_birth"})})
public class Voter extends DataModel {

    @Id
    @GeneratedValue
    private long id;

    @OneToOne(mappedBy = "voter", optional = false)
    private Account account;

    @Basic
    @Column(name = "date_of_birth")
    private int dateOfBirth;

    @Basic
    @Column(name = "first_name")
    private String firstName;

    @Basic
    @Column(name = "last_name")
    private String lastName;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getDateOfBirth() {
        return dateOfBirth;
    }

    public DMY getDateOfBirthDMY() throws InvalidDateException {
        return new DMY(getDateOfBirth());
    }

    public void setDateOfBirth(int day, int month, int year) throws InvalidDateException {
        String dobString = String.format("%d%d%d", year, month, day);
        DMY dmy = new DMY(dobString);

    }

    public void setDateOfBirth(int dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}
