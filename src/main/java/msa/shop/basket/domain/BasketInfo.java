package msa.shop.basket.domain;

import com.google.gson.Gson;

public class BasketInfo {

	private String productId;
	private String amount;

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	@Override
	public String toString() {
		return new Gson().toJson(this);
	}

}
