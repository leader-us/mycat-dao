package io.mycat.housesys.domain;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.data.relational.core.mapping.Column;

/**
 * mk_bill
 * 
 * @author leader
 * @email leader@mycat.io
 * @date Fri Mar 27 23:53:48 CST 2020
 */

@Table("mk_bill")
public class  MkBill {

    /**
	 * 主键，
	 */
    @Id
    
	@Column("id")
	private Long id;
    /**
	 * 
	 */
    
	@Column("create_date")
	private java.util.Date createDate;
    /**
	 * 合同ID
	 */
    
	@Column("contract_id")
	private Long contractId;
    /**
	 * 缴费月份
	 */
    
	@Column("months")
	private Integer months;
    /**
	 * 上月水表
	 */
    
	@Column("last_month_water")
	private java.math.BigDecimal lastMonthWater;
    /**
	 * 上月电表
	 */
    
	@Column("last_month_electricity")
	private java.math.BigDecimal lastMonthElectricity;
    /**
	 * 本月水表
	 */
    
	@Column("now_water")
	private java.math.BigDecimal nowWater;
    /**
	 * 本月电表
	 */
    
	@Column("now_electricity")
	private java.math.BigDecimal nowElectricity;
    /**
	 * 缴费金额
	 */
    
	@Column("totle_price")
	private java.math.BigDecimal totlePrice;
    /**
	 * 催收日期
	 */
    
	@Column("collect_day")
	private Integer collectDay;
    /**
	 * 收款方式 1微信 2支付宝 3现金
	 */
    
	@Column("pay_method")
	private Short payMethod;
    /**
	 * 是否下月底 合同到期
	 */
    
	@Column("is_last")
	private Short isLast;
    /**
	 * 用户ID
	 */
	@io.mycat.dao.DomainER.ForeginKey(MkUser.class)
	@Column("user_id")
	private Long userId;
    /**
	 * 房租
	 */
    
	@Column("rent")
	private java.math.BigDecimal rent;
    /**
	 * 0 草稿 1已经上传 2待交租 3已交租
	 */
    
	@Column("state")
	private Short state;
    /**
	 * 水费
	 */
    
	@Column("water_price")
	private java.math.BigDecimal waterPrice;
    /**
	 * 电费
	 */
    
	@Column("electricity_price")
	private java.math.BigDecimal electricityPrice;
    /**
	 * 上报用户ID
	 */
    
	@Column("report_user_id")
	private Long reportUserId;
    /**
	 * 附件ID
	 */
    
	@Column("attach_id")
	private Long attachId;
    /**
	 * 账单类型(1、押金账单 2、房租水电 3、电器租金 4、服务费 5、维修费 6、购买商品 7、其他)
	 */
    
	@Column("bill_type")
	private Short billType;
    /**
	 * 备注
	 */
    
	@Column("remark")
	private String remark;
    /**
	 * 最后交租期限
	 */
    
	@Column("lease_date")
	private java.util.Date leaseDate;
    /**
	 * 实际交租日期
	 */
    
	@Column("rent_date")
	private java.util.Date rentDate;
    /**
	 * 确认人
	 */
    
	@Column("confirm_user_id")
	private Long confirmUserId;
    /**
	 * 账期
	 */
    
	@Column("payment")
	private String payment;
    /**
	 * 催收次数
	 */
    
	@Column("cs_count")
	private Integer csCount;
    /**
	 * 找钱
	 */
    
	@Column("change_money")
	private java.math.BigDecimal changeMoney;


	/**
	 * 设置：主键，
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * 获取：主键，
	 */
    public Long isId() {
		return id;
	}
	/**
	 * 设置：
	 */
	public void setCreateDate(java.util.Date createDate) {
		this.createDate = createDate;
	}
	/**
	 * 获取：
	 */
    public java.util.Date isCreateDate() {
		return createDate;
	}
	/**
	 * 设置：合同ID
	 */
	public void setContractId(Long contractId) {
		this.contractId = contractId;
	}
	/**
	 * 获取：合同ID
	 */
    public Long isContractId() {
		return contractId;
	}
	/**
	 * 设置：缴费月份
	 */
	public void setMonths(Integer months) {
		this.months = months;
	}
	/**
	 * 获取：缴费月份
	 */
    public Integer isMonths() {
		return months;
	}
	/**
	 * 设置：上月水表
	 */
	public void setLastMonthWater(java.math.BigDecimal lastMonthWater) {
		this.lastMonthWater = lastMonthWater;
	}
	/**
	 * 获取：上月水表
	 */
    public java.math.BigDecimal isLastMonthWater() {
		return lastMonthWater;
	}
	/**
	 * 设置：上月电表
	 */
	public void setLastMonthElectricity(java.math.BigDecimal lastMonthElectricity) {
		this.lastMonthElectricity = lastMonthElectricity;
	}
	/**
	 * 获取：上月电表
	 */
    public java.math.BigDecimal isLastMonthElectricity() {
		return lastMonthElectricity;
	}
	/**
	 * 设置：本月水表
	 */
	public void setNowWater(java.math.BigDecimal nowWater) {
		this.nowWater = nowWater;
	}
	/**
	 * 获取：本月水表
	 */
    public java.math.BigDecimal isNowWater() {
		return nowWater;
	}
	/**
	 * 设置：本月电表
	 */
	public void setNowElectricity(java.math.BigDecimal nowElectricity) {
		this.nowElectricity = nowElectricity;
	}
	/**
	 * 获取：本月电表
	 */
    public java.math.BigDecimal isNowElectricity() {
		return nowElectricity;
	}
	/**
	 * 设置：缴费金额
	 */
	public void setTotlePrice(java.math.BigDecimal totlePrice) {
		this.totlePrice = totlePrice;
	}
	/**
	 * 获取：缴费金额
	 */
    public java.math.BigDecimal isTotlePrice() {
		return totlePrice;
	}
	/**
	 * 设置：催收日期
	 */
	public void setCollectDay(Integer collectDay) {
		this.collectDay = collectDay;
	}
	/**
	 * 获取：催收日期
	 */
    public Integer isCollectDay() {
		return collectDay;
	}
	/**
	 * 设置：收款方式 1微信 2支付宝 3现金
	 */
	public void setPayMethod(Short payMethod) {
		this.payMethod = payMethod;
	}
	/**
	 * 获取：收款方式 1微信 2支付宝 3现金
	 */
    public Short isPayMethod() {
		return payMethod;
	}
	/**
	 * 设置：是否下月底 合同到期
	 */
	public void setIsLast(Short isLast) {
		this.isLast = isLast;
	}
	/**
	 * 获取：是否下月底 合同到期
	 */
    public Short isIsLast() {
		return isLast;
	}
	/**
	 * 设置：用户ID
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	/**
	 * 获取：用户ID
	 */
    public Long isUserId() {
		return userId;
	}
	/**
	 * 设置：房租
	 */
	public void setRent(java.math.BigDecimal rent) {
		this.rent = rent;
	}
	/**
	 * 获取：房租
	 */
    public java.math.BigDecimal isRent() {
		return rent;
	}
	/**
	 * 设置：0 草稿 1已经上传 2待交租 3已交租
	 */
	public void setState(Short state) {
		this.state = state;
	}
	/**
	 * 获取：0 草稿 1已经上传 2待交租 3已交租
	 */
    public Short isState() {
		return state;
	}
	/**
	 * 设置：水费
	 */
	public void setWaterPrice(java.math.BigDecimal waterPrice) {
		this.waterPrice = waterPrice;
	}
	/**
	 * 获取：水费
	 */
    public java.math.BigDecimal isWaterPrice() {
		return waterPrice;
	}
	/**
	 * 设置：电费
	 */
	public void setElectricityPrice(java.math.BigDecimal electricityPrice) {
		this.electricityPrice = electricityPrice;
	}
	/**
	 * 获取：电费
	 */
    public java.math.BigDecimal isElectricityPrice() {
		return electricityPrice;
	}
	/**
	 * 设置：上报用户ID
	 */
	public void setReportUserId(Long reportUserId) {
		this.reportUserId = reportUserId;
	}
	/**
	 * 获取：上报用户ID
	 */
    public Long isReportUserId() {
		return reportUserId;
	}
	/**
	 * 设置：附件ID
	 */
	public void setAttachId(Long attachId) {
		this.attachId = attachId;
	}
	/**
	 * 获取：附件ID
	 */
    public Long isAttachId() {
		return attachId;
	}
	/**
	 * 设置：账单类型(1、押金账单 2、房租水电 3、电器租金 4、服务费 5、维修费 6、购买商品 7、其他)
	 */
	public void setBillType(Short billType) {
		this.billType = billType;
	}
	/**
	 * 获取：账单类型(1、押金账单 2、房租水电 3、电器租金 4、服务费 5、维修费 6、购买商品 7、其他)
	 */
    public Short isBillType() {
		return billType;
	}
	/**
	 * 设置：备注
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}
	/**
	 * 获取：备注
	 */
    public String isRemark() {
		return remark;
	}
	/**
	 * 设置：最后交租期限
	 */
	public void setLeaseDate(java.util.Date leaseDate) {
		this.leaseDate = leaseDate;
	}
	/**
	 * 获取：最后交租期限
	 */
    public java.util.Date isLeaseDate() {
		return leaseDate;
	}
	/**
	 * 设置：实际交租日期
	 */
	public void setRentDate(java.util.Date rentDate) {
		this.rentDate = rentDate;
	}
	/**
	 * 获取：实际交租日期
	 */
    public java.util.Date isRentDate() {
		return rentDate;
	}
	/**
	 * 设置：确认人
	 */
	public void setConfirmUserId(Long confirmUserId) {
		this.confirmUserId = confirmUserId;
	}
	/**
	 * 获取：确认人
	 */
    public Long isConfirmUserId() {
		return confirmUserId;
	}
	/**
	 * 设置：账期
	 */
	public void setPayment(String payment) {
		this.payment = payment;
	}
	/**
	 * 获取：账期
	 */
    public String isPayment() {
		return payment;
	}
	/**
	 * 设置：催收次数
	 */
	public void setCsCount(Integer csCount) {
		this.csCount = csCount;
	}
	/**
	 * 获取：催收次数
	 */
    public Integer isCsCount() {
		return csCount;
	}
	/**
	 * 设置：找钱
	 */
	public void setChangeMoney(java.math.BigDecimal changeMoney) {
		this.changeMoney = changeMoney;
	}
	/**
	 * 获取：找钱
	 */
    public java.math.BigDecimal isChangeMoney() {
		return changeMoney;
	}

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
  }
}