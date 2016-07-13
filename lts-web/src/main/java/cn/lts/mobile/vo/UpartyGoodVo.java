package cn.lts.mobile.vo;

import java.util.ArrayList;
import java.util.List;

/**
 * 组合商品
 * @author zhoujianyong
 * 2015年4月28日-下午8:48:39
 */
public class UpartyGoodVo {
	private String id;
	private String imageUrl;
	private String name;
	//商品数量，用来标识是否已售完
	private int num;
	private String oldPrice;
	private String price;
	private int offline = 0;
	private int sellOf = 0;
	private int priority;
	private List<GoodVo> goodVoList = new ArrayList<GoodVo>();

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public String getOldPrice() {
		return oldPrice;
	}

	public void setOldPrice(String oldPrice) {
		this.oldPrice = oldPrice;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}
	
	public int getOffline() {
		return offline;
	}
	
	public void setOffline(int offline) {
		this.offline = offline;
	}
	
	public int getSellOf() {
		return sellOf;
	}
	
	public void setSellOf(int sellOf) {
		this.sellOf = sellOf;
	}

	public int getPriority() {
		return priority;
	}
	
	public void setPriority(int priority) {
		this.priority = priority;
	}
	
	public List<GoodVo> getGoodVoList() {
		return goodVoList;
	}

	public void addGoodVo(GoodVo goodVo) {
		if(this.goodVoList == null){
			this.goodVoList = new ArrayList<GoodVo>();
		}
		this.goodVoList.add(goodVo);
	}
}