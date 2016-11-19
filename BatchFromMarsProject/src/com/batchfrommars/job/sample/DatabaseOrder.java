
package com.batchfrommars.job.sample;
import java.math.BigDecimal;
import java.util.ArrayList;

import com.batchfrommars.component.SymbolParser;

/**
 * operateType : 00 = update current price 01 = insert transaction detail
 * 
 * 
 * 
 * @author Yj
 *
 */
public class DatabaseOrder extends SymbolParser{
	
	private String operateType;
	private String stockId;
	private String txSeq;
	private BigDecimal currentPrice;
	private BigDecimal buyingPrice;
	private BigDecimal sellingPrice;
	private int amount;

	public String getOperateType() {
		return operateType;
	}

	public void setOperateType(String operateType) {
		this.operateType = operateType;
	}

	public String getStockId() {
		return stockId;
	}

	public void setStockId(String stockId) {
		this.stockId = stockId;
	}

	public String getTxSeq() {
		return txSeq;
	}

	public BigDecimal getCurrentPrice() {
		return currentPrice;
	}

	public void setCurrentPrice(BigDecimal currentPrice) {
		this.currentPrice = currentPrice;
	}

	public BigDecimal getBuyingPrice() {
		return buyingPrice;
	}

	public void setBuyingPrice(BigDecimal buyingPrice) {
		this.buyingPrice = buyingPrice;
	}

	public BigDecimal getSellingPrice() {
		return sellingPrice;
	}

	public void setSellingPrice(BigDecimal sellingPrice) {
		this.sellingPrice = sellingPrice;
	}

	public void setTxSeq(String txSeq) {
		this.txSeq = txSeq;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	@Override
	public String toString() {
		return operateType + "," + stockId + "," + txSeq + "," + currentPrice + "," + buyingPrice + "," + sellingPrice + "," + amount;
	}

	@Override
	protected ArrayList<String> getFields() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String getSymbol() {
		
		return ",";
	}

}
