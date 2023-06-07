package com.simple.bank.entity;

import com.simple.bank.entity.Account;
import com.simple.bank.process.TransactionHandler;
import com.simple.bank.utils.Constants;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.*;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.Instant;

@Entity
public class Transactions {
	
	@Id
	@GeneratedValue (strategy = GenerationType.AUTO)
	private int tid;
	
	@NotNull
	@ManyToOne
	@JoinColumn(name="account.accNo")
	private Account account;
	
	@NotNull
	private float amount;
	
	@NotNull
	private float oldBalance;
	
	@NotNull
	private float newBalance;
	
	@NotNull
	private String type;

	private String remarks;
	
	@NotNull
	private Date date;
	
	@NotNull
	private String status;
	
	@NotNull
	private final Timestamp created;
	
	@NotNull
	private Timestamp updated;
	
	public Transactions(Account account, float amount, String type, String remarks) {
		this.account = account;
		this.amount = amount;
		this.type = type;
		this.remarks = remarks;
		this.status = Constants.TRANSACTIONS_STATUS_STARTED;
		this.oldBalance = account.getBalance();
		this.newBalance = account.getBalance();
		this.date = new Date (new java.util.Date().getTime());
		this.created = Timestamp.from(Instant.now());
		this.updated = Timestamp.from(Instant.now());
	}

	public Transactions() {
		created = Timestamp.from(Instant.now());
		updated = Timestamp.from(Instant.now());
	}
	
	public void setAccount (Account account) {
		this.account = account;
		this.oldBalance = account.getBalance();
	}

	public int getTid() {
		return tid;
	}
	
	public Account getAccount () {
		return this.account;
	}
	
	public void setAmount (float amount) {
		this.amount = amount;
	}
	
	public float getAmount () {
		return this.amount;
	}
	
	public float getOldBalance () {
		return this.oldBalance;
	}
	
	public void setNewBalance (float newBalance) {
		this.newBalance = newBalance;
	}
	
	public float getNewBalance () {
		return this.newBalance;
	}
	
	public void setType (String type) {
		this.type = type;
	}
	
	public String getType () {
		return this.type;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public void setDate (Date date) {
		this.date = date;
	}
	
	public Date getDate () {
		return this.date;
	}
	
	public void setStatus (String status) {
		this.status = status;
	}
	
	public String getStatus () {
		return this.status;
	}
	
	public Timestamp getCreated () {
		return this.created;
	}
	
	public void setUpdated (Timestamp updated) {
		this.updated = updated;
	}
	
	public Timestamp getUpdated () {
		return this.updated;
	}
}
