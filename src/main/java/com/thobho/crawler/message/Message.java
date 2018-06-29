package com.thobho.crawler.message;

import java.io.Serializable;
import java.util.Objects;

public class Message implements Serializable{

	public Message(String value, long id, long points) {
		this.value = value;
		this.id = id;
		this.points = points;
	}

	private long id;
	private String value;
	private long points;

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Message message = (Message) o;
		return id == message.id &&
				points == message.points &&
				Objects.equals(value, message.value);
	}

	@Override
	public int hashCode() {

		return Objects.hash(id, value, points);
	}
}
