package io.mycat.housesys.domain;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.data.relational.core.mapping.Column;

/**
 * mk_user
 * 
 * @author leader
 * @email leader@mycat.io
 * @date Fri Mar 27 23:53:48 CST 2020
 */

@Table("mk_user")
public class  MkUser {

    /**
	 * 主键，主键ID
	 */
    @Id
    
	@Column("id")
	private Long id;
    /**
	 * 密码
	 */
    
	@Column("pwd")
	private String pwd;
    /**
	 * 日期
	 */
    
	@Column("create_date")
	private java.util.Date createDate;
    /**
	 * 手机号码
	 */
    
	@Column("phone")
	private String phone;
    /**
	 * 邮箱
	 */
    
	@Column("email")
	private String email;
    /**
	 * 姓名
	 */
    
	@Column("nick_name")
	private String nickName;
    /**
	 * 微信open_id
	 */
    
	@Column("open_id")
	private String openId;
    /**
	 * 性别
	 */
    
	@Column("sex")
	private Short sex;
    /**
	 * 1、普通用户 2、超级管理员 3、管家 4、维修师傅
	 */
    
	@Column("role")
	private Short role;
    /**
	 * 微信号
	 */
    
	@Column("wx")
	private String wx;
    /**
	 * 紧急联系人
	 */
    
	@Column("emergency")
	private String emergency;
    /**
	 * 紧急联系人电话
	 */
    
	@Column("emergency_phone")
	private String emergencyPhone;
    /**
	 * 积分
	 */
    
	@Column("points")
	private Integer points;
    /**
	 * 
	 */
    
	@Column("job")
	private String job;
    /**
	 * 
	 */
    
	@Column("owner_manger")
	private Long ownerManger;
    /**
	 * 
	 */
    
	@Column("discover_manager")
	private Long discoverManager;
    /**
	 * 
	 */
    
	@Column("cfytype")
	private Integer cfytype;
    /**
	 * 
	 */
    
	@Column("custom_type")
	private Integer customType;
    /**
	 * 
	 */
    
	@Column("sales_states")
	private Integer salesStates;


	/**
	 * 设置：主键，主键ID
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * 获取：主键，主键ID
	 */
    public Long isId() {
		return id;
	}
	/**
	 * 设置：密码
	 */
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	/**
	 * 获取：密码
	 */
    public String isPwd() {
		return pwd;
	}
	/**
	 * 设置：日期
	 */
	public void setCreateDate(java.util.Date createDate) {
		this.createDate = createDate;
	}
	/**
	 * 获取：日期
	 */
    public java.util.Date isCreateDate() {
		return createDate;
	}
	/**
	 * 设置：手机号码
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}
	/**
	 * 获取：手机号码
	 */
    public String isPhone() {
		return phone;
	}
	/**
	 * 设置：邮箱
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 * 获取：邮箱
	 */
    public String isEmail() {
		return email;
	}
	/**
	 * 设置：姓名
	 */
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	/**
	 * 获取：姓名
	 */
    public String isNickName() {
		return nickName;
	}
	/**
	 * 设置：微信open_id
	 */
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	/**
	 * 获取：微信open_id
	 */
    public String isOpenId() {
		return openId;
	}
	/**
	 * 设置：性别
	 */
	public void setSex(Short sex) {
		this.sex = sex;
	}
	/**
	 * 获取：性别
	 */
    public Short isSex() {
		return sex;
	}
	/**
	 * 设置：1、普通用户 2、超级管理员 3、管家 4、维修师傅
	 */
	public void setRole(Short role) {
		this.role = role;
	}
	/**
	 * 获取：1、普通用户 2、超级管理员 3、管家 4、维修师傅
	 */
    public Short isRole() {
		return role;
	}
	/**
	 * 设置：微信号
	 */
	public void setWx(String wx) {
		this.wx = wx;
	}
	/**
	 * 获取：微信号
	 */
    public String isWx() {
		return wx;
	}
	/**
	 * 设置：紧急联系人
	 */
	public void setEmergency(String emergency) {
		this.emergency = emergency;
	}
	/**
	 * 获取：紧急联系人
	 */
    public String isEmergency() {
		return emergency;
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
	 * 设置：积分
	 */
	public void setPoints(Integer points) {
		this.points = points;
	}
	/**
	 * 获取：积分
	 */
    public Integer isPoints() {
		return points;
	}
	/**
	 * 设置：
	 */
	public void setJob(String job) {
		this.job = job;
	}
	/**
	 * 获取：
	 */
    public String isJob() {
		return job;
	}
	/**
	 * 设置：
	 */
	public void setOwnerManger(Long ownerManger) {
		this.ownerManger = ownerManger;
	}
	/**
	 * 获取：
	 */
    public Long isOwnerManger() {
		return ownerManger;
	}
	/**
	 * 设置：
	 */
	public void setDiscoverManager(Long discoverManager) {
		this.discoverManager = discoverManager;
	}
	/**
	 * 获取：
	 */
    public Long isDiscoverManager() {
		return discoverManager;
	}
	/**
	 * 设置：
	 */
	public void setCfytype(Integer cfytype) {
		this.cfytype = cfytype;
	}
	/**
	 * 获取：
	 */
    public Integer isCfytype() {
		return cfytype;
	}
	/**
	 * 设置：
	 */
	public void setCustomType(Integer customType) {
		this.customType = customType;
	}
	/**
	 * 获取：
	 */
    public Integer isCustomType() {
		return customType;
	}
	/**
	 * 设置：
	 */
	public void setSalesStates(Integer salesStates) {
		this.salesStates = salesStates;
	}
	/**
	 * 获取：
	 */
    public Integer isSalesStates() {
		return salesStates;
	}

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
  }
}