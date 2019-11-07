package com.revature.models;

import java.sql.Timestamp;

public class Tickets {
	private int ticketid;
	private double amount;
	private Timestamp datesubmitted;
	private Timestamp dateresolved;
	private String description;
	private String receipt;
	private int authorid;
	private int resolverid;
	private int statusid;
	private int typeid;
	public Tickets() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Tickets(int ticketid, double amount, Timestamp datesubmitted, Timestamp dateresolved, String description,
			String receipt, int authorid, int resolverid, int statusid, int typeid) {
		super();
		this.ticketid = ticketid;
		this.amount = amount;
		this.datesubmitted = datesubmitted;
		this.dateresolved = dateresolved;
		this.description = description;
		this.receipt = receipt;
		this.authorid = authorid;
		this.resolverid = resolverid;
		this.statusid = statusid;
		this.typeid = typeid;
	}
	@Override
	public String toString() {
		return "Tickets [ticketid=" + ticketid + ", amount=" + amount + ", datesubmitted=" + datesubmitted
				+ ", dateresolved=" + dateresolved + ", description=" + description + ", receipt=" + receipt
				+ ", authorid=" + authorid + ", resolverid=" + resolverid + ", statusid=" + statusid + ", typeid="
				+ typeid + "]";
	}
	public int getTicketid() {
		return ticketid;
	}
	public void setTicketid(int ticketid) {
		this.ticketid = ticketid;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public Timestamp getDatesubmitted() {
		return datesubmitted;
	}
	public void setDatesubmitted(Timestamp datesubmitted) {
		this.datesubmitted = datesubmitted;
	}
	public Timestamp getDateresolved() {
		return dateresolved;
	}
	public void setDateresolved(Timestamp dateresolved) {
		this.dateresolved = dateresolved;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getReceipt() {
		return receipt;
	}
	public void setReceipt(String receipt) {
		this.receipt = receipt;
	}
	public int getAuthorid() {
		return authorid;
	}
	public void setAuthorid(int authorid) {
		this.authorid = authorid;
	}
	public int getResolverid() {
		return resolverid;
	}
	public void setResolverid(int resolverid) {
		this.resolverid = resolverid;
	}
	public int getStatusid() {
		return statusid;
	}
	public void setStatusid(int statusid) {
		this.statusid = statusid;
	}
	public int getTypeid() {
		return typeid;
	}
	public void setTypeid(int typeid) {
		this.typeid = typeid;
	}
}
