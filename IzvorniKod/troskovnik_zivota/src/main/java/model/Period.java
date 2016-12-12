package model;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * This class models accounting period for
 * 
 * @author Filip
 *
 */
@Entity
@Table(name = "periods")
public class Period {

	private static Map<String, Boolean> constantPeriods;

	public static final String weekly = "Jednom tjedno";

	public static final String monthly = "Jednom mjesečno";

	public static final String anualy = "Jednom godinšnje";

	public static final String oneTime = "Jedno ročno";

	static {
		constantPeriods = new HashMap<>();
		constantPeriods.put(anualy, true);
		constantPeriods.put(weekly, true);
		constantPeriods.put(monthly, true);
	}


	/**
	 * id
	 */
	@Id
	@GeneratedValue
	private Long id;
	
	@Column
	private String period;

	@Column
	private Date date;

	@OneToMany(mappedBy = "period", fetch = FetchType.LAZY, cascade=CascadeType.ALL, orphanRemoval = false)
	private List<ExpenseItem> expenseItemsOwners;
	
	@OneToMany(mappedBy = "period", fetch = FetchType.LAZY, cascade=CascadeType.ALL, orphanRemoval = false)
	private List<IncomeItem> incomeItemsOwners;
	
	public Period() {
		super();
	}

	public Period(String period) {
		super();
		if (period.equals(oneTime)) {
			throw new IllegalArgumentException("If period is one time, date must be defined!");
		} else if (!constantPeriods.containsKey(period)) {
			throw new IllegalArgumentException("Invalid period was provided");
		}
		this.period = period;
	}

	public Period(String period, Date date) {
		super();
		if (!period.equals(oneTime)) {
			throw new IllegalArgumentException("If date argument can only be provided if period is one time");
		} 
		
		this.period = period;
		this.date = date;
	}

	public String getPeriod() {
		return period;
	}

	public void setPeriod(String period) {
		this.period = period;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	
}
