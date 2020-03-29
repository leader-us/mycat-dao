package io.mycat.housesys.domain;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.data.relational.core.mapping.Column;

/**
 * mk_contract
 * 
 * @author leader
 * @email leader@mycat.io
 * @date Fri Mar 27 23:53:48 CST 2020
 */

@Table("mk_contract")
public class  MkContract {

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
	 * 分类 1日租 2公寓
	 */
    
	@Column("cfytype")
	private Short cfytype;
    /**
	 * 预约ID
	 */
    
	@Column("cst_id")
	private Long cstId;
    /**
	 * 用户ID
	 */
	@io.mycat.dao.DomainER.ForeginKey(MkUser.class)
	@Column("user_id")
	private Long userId;
    /**
	 * 楼号ID
	 */
    
	@Column("building_id")
	private Long buildingId;
    /**
	 * 房间号ID
	 */
    
	@Column("estate_id")
	private Long estateId;
    /**
	 * 身份编号
	 */
    
	@Column("identity_no")
	private String identityNo;
    /**
	 * 工作单位
	 */
    
	@Column("company")
	private String company;
    /**
	 * 联系电话
	 */
    
	@Column("phone")
	private String phone;
    /**
	 * 承租人姓名
	 */
    
	@Column("lessee_name")
	private String lesseeName;
    /**
	 * 入住人数
	 */
    
	@Column("live_count")
	private Integer liveCount;
    /**
	 * 紧急联系人姓名
	 */
    
	@Column("emergency_name")
	private String emergencyName;
    /**
	 * 紧急联系人电话
	 */
    
	@Column("emergency_phone")
	private String emergencyPhone;
    /**
	 * 承租房屋
	 */
    
	@Column("estate")
	private String estate;
    /**
	 * 入住日期
	 */
    
	@Column("check_in_date")
	private java.util.Date checkInDate;
    /**
	 * 居住时长
	 */
    
	@Column("live_days")
	private Integer liveDays;
    /**
	 * 退租日期
	 */
    
	@Column("live_date")
	private java.util.Date liveDate;
    /**
	 * 租金
	 */
    
	@Column("rent")
	private java.math.BigDecimal rent;
    /**
	 * 押金
	 */
    
	@Column("deposit")
	private java.math.BigDecimal deposit;
    /**
	 * 电费
	 */
    
	@Column("electricity_price")
	private java.math.BigDecimal electricityPrice;
    /**
	 * 水费
	 */
    
	@Column("water_price")
	private java.math.BigDecimal waterPrice;
    /**
	 * 水收费类型 0按吨 1包月
	 */
    
	@Column("water_type")
	private Short waterType;
    /**
	 * 状态
	 */
    
	@Column("state")
	private Short state;
    /**
	 * 入住时电表
	 */
    
	@Column("electricity")
	private java.math.BigDecimal electricity;
    /**
	 * 入住时水表
	 */
    
	@Column("water")
	private java.math.BigDecimal water;
    /**
	 * 创建人
	 */
    
	@Column("create_user_id")
	private Long createUserId;
    /**
	 * 催收日期(号)
	 */
    
	@Column("collection_day")
	private Short collectionDay;
    /**
	 * 电收费类型 0按吨 2预付费
	 */
    
	@Column("electricity_type")
	private Short electricityType;
    /**
	 * 最后退租日期
	 */
    
	@Column("last_date")
	private java.util.Date lastDate;
    /**
	 * 
	 */
    
	@Column("remark")
	private String remark;


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
	 * 设置：分类 1日租 2公寓
	 */
	public void setCfytype(Short cfytype) {
		this.cfytype = cfytype;
	}
	/**
	 * 获取：分类 1日租 2公寓
	 */
    public Short isCfytype() {
		return cfytype;
	}
	/**
	 * 设置：预约ID
	 */
	public void setCstId(Long cstId) {
		this.cstId = cstId;
	}
	/**
	 * 获取：预约ID
	 */
    public Long isCstId() {
		return cstId;
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
	 * 设置：楼号ID
	 */
	public void setBuildingId(Long buildingId) {
		this.buildingId = buildingId;
	}
	/**
	 * 获取：楼号ID
	 */
    public Long isBuildingId() {
		return buildingId;
	}
	/**
	 * 设置：房间号ID
	 */
	public void setEstateId(Long estateId) {
		this.estateId = estateId;
	}
	/**
	 * 获取：房间号ID
	 */
    public Long isEstateId() {
		return estateId;
	}
	/**
	 * 设置：身份编号
	 */
	public void setIdentityNo(String identityNo) {
		this.identityNo = identityNo;
	}
	/**
	 * 获取：身份编号
	 */
    public String isIdentityNo() {
		return identityNo;
	}
	/**
	 * 设置：工作单位
	 */
	public void setCompany(String company) {
		this.company = company;
	}
	/**
	 * 获取：工作单位
	 */
    public String isCompany() {
		return company;
	}
	/**
	 * 设置：联系电话
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}
	/**
	 * 获取：联系电话
	 */
    public String isPhone() {
		return phone;
	}
	/**
	 * 设置：承租人姓名
	 */
	public void setLesseeName(String lesseeName) {
		this.lesseeName = lesseeName;
	}
	/**
	 * 获取：承租人姓名
	 */
    public String isLesseeName() {
		return lesseeName;
	}
	/**
	 * 设置：入住人数
	 */
	public void setLiveCount(Integer liveCount) {
		this.liveCount = liveCount;
	}
	/**
	 * 获取：入住人数
	 */
    public Integer isLiveCount() {
		return liveCount;
	}
	/**
	 * 设置：紧急联系人姓名
	 */
	public void setEmergencyName(String emergencyName) {
		this.emergencyName = emergencyName;
	}
	/**
	 * 获取：紧急联系人姓名
	 */
    public String isEmergencyName() {
		return emergencyName;
	}
	/**
	 * 设置：紧急联系人电话
	 */
	public void setEmergencyPhone(String emergencyPhone) {
		this.emergencyPhone = emergencyPhone;
	}
	/**
	 * 获取：紧急联系人电话
	 */
    public String isEmergencyPhone() {
		return emergencyPhone;
	}
	/**
	 * 设置：承租房屋
	 */
	public void setEstate(String estate) {
		this.estate = estate;
	}
	/**
	 * 获取：承租房屋
	 */
    public String isEstate() {
		return estate;
	}
	/**
	 * 设置：入住日期
	 */
	public void setCheckInDate(java.util.Date checkInDate) {
		this.checkInDate = checkInDate;
	}
	/**
	 * 获取：入住日期
	 */
    public java.util.Date isCheckInDate() {
		return checkInDate;
	}
	/**
	 * 设置：居住时长
	 */
	public void setLiveDays(Integer liveDays) {
		this.liveDays = liveDays;
	}
	/**
	 * 获取：居住时长
	 */
    public Integer isLiveDays() {
		return liveDays;
	}
	/**
	 * 设置：退租日期
	 */
	public void setLiveDate(java.util.Date liveDate) {
		this.liveDate = liveDate;
	}
	/**
	 * 获取：退租日期
	 */
    public java.util.Date isLiveDate() {
		return liveDate;
	}
	/**
	 * 设置：租金
	 */
	public void setRent(java.math.BigDecimal rent) {
		this.rent = rent;
	}
	/**
	 * 获取：租金
	 */
    public java.math.BigDecimal isRent() {
		return rent;
	}
	/**
	 * 设置：押金
	 */
	public void setDeposit(java.math.BigDecimal deposit) {
		this.deposit = deposit;
	}
	/**
	 * 获取：押金
	 */
    public java.math.BigDecimal isDeposit() {
		return deposit;
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
	 * 设置：水收费类型 0按吨 1包月
	 */
	public void setWaterType(Short waterType) {
		this.waterType = waterType;
	}
	/**
	 * 获取：水收费类型 0按吨 1包月
	 */
    public Short isWaterType() {
		return waterType;
	}
	/**
	 * 设置：状态
	 */
	public void setState(Short state) {
		this.state = state;
	}
	/**
	 * 获取：状态
	 */
    public Short isState() {
		return state;
	}
	/**
	 * 设置：入住时电表
	 */
	public void setElectricity(java.math.BigDecimal electricity) {
		this.electricity = electricity;
	}
	/**
	 * 获取：入住时电表
	 */
    public java.math.BigDecimal isElectricity() {
		return electricity;
	}
	/**
	 * 设置：入住时水表
	 */
	public void setWater(java.math.BigDecimal water) {
		this.water = water;
	}
	/**
	 * 获取：入住时水表
	 */
    public java.math.BigDecimal isWater() {
		return water;
	}
	/**
	 * 设置：创建人
	 */
	public void setCreateUserId(Long createUserId) {
		this.createUserId = createUserId;
	}
	/**
	 * 获取：创建人
	 */
    public Long isCreateUserId() {
		return createUserId;
	}
	/**
	 * 设置：催收日期(号)
	 */
	public void setCollectionDay(Short collectionDay) {
		this.collectionDay = collectionDay;
	}
	/**
	 * 获取：催收日期(号)
	 */
    public Short isCollectionDay() {
		return collectionDay;
	}
	/**
	 * 设置：电收费类型 0按吨 2预付费
	 */
	public void setElectricityType(Short electricityType) {
		this.electricityType = electricityType;
	}
	/**
	 * 获取：电收费类型 0按吨 2预付费
	 */
    public Short isElectricityType() {
		return electricityType;
	}
	/**
	 * 设置：最后退租日期
	 */
	public void setLastDate(java.util.Date lastDate) {
		this.lastDate = lastDate;
	}
	/**
	 * 获取：最后退租日期
	 */
    public java.util.Date isLastDate() {
		return lastDate;
	}
	/**
	 * 设置：
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}
	/**
	 * 获取：
	 */
    public String isRemark() {
		return remark;
	}

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
  }
}