package be.project.javabeans;

import java.io.Serializable;

public class Participation implements Serializable{
	
	private static final long serialVersionUID = 3625545104081989661L;
	private int participationId;
	private User participant;
	private Double participationpart;
	private Gift gift;
	
	
	public Participation(int participationId, User participant, Double participationpart, Gift gift) {
		this.participationId=participationId;
		this.participant = participant;
		this.participationpart = participationpart;
		this.gift = gift;
	}


	public int getParticipationId() {
		return participationId;
	}


	public void setParticipationId(int participationId) {
		this.participationId = participationId;
	}
	public User getParticipant() {
		return participant;
	}


	public void setParticipant(User participant) {
		this.participant = participant;
	}


	public Double getParticipationpart() {
		return participationpart;
	}


	public void setParticipationpart(Double participationpart) {
		this.participationpart = participationpart;
	}


	public Gift getGift() {
		return gift;
	}


	public void setGift(Gift gift) {
		this.gift = gift;
	}
}
