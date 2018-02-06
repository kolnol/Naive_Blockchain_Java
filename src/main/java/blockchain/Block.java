package blockchain;

import java.util.Date;


public class Block {
	private Long index;
	private String hash;
	private Date timestamp;
	private Data data;
	private String prevHash;

	public Block(Long index, String hash, Date timestamp, Data data, String prevHash) {
		this.index = index;
		this.hash = hash;
		this.timestamp = timestamp;
		this.data = data;
		this.prevHash = prevHash;
	}

	public Long getIndex() {
		return index;
	}

	public void setIndex(Long index) {
		this.index = index;
	}

	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public Data getData() {
		return data;
	}

	public void setData(Data data) {
		this.data = data;
	}

	public String getPrevHash() {
		return prevHash;
	}

	public void setPrevHash(String prevHash) {
		this.prevHash = prevHash;
	}

	@Override
	public String toString() {
		return "Block{" +
				"index=" + index +
				", hash='" + hash + '\'' +
				", timestamp=" + timestamp +
				", data=" + data +
				", prevHash='" + prevHash + '\'' +
				'}';
	}
}