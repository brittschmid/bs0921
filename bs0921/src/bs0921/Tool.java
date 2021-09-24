package bs0921;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * This class represents a rental tool
 *
 */
public class Tool {
	
	public enum ToolType {
		LADDER("Ladder"),
		CHAINSAW("Chainsaw"),
		JACKHAMMER("Jackhammer");
		
		private String value;
		
		ToolType(String value) {
			this.value = value;
		}
		
		@Override
		public String toString() {
			return value;
		}
	};
	
	public enum Brand {
		WERNER("Werner"),
		STIHL("Stihl"),
		RIDGID("Ridgid"),
		DEWALT("DeWalt");
		
		private String value;
		
		Brand(String value) {
			this.value = value;
		}
		
		@Override
		public String toString() {
			return value;
		}
	};
	
	public enum ToolCode {
		LADW,
		CHNS,
		JAKR,
		JAKD;
		
		public static List<String> getToolCodes() {
			return Stream.of(ToolCode.values())
					.map(ToolCode::name)
					.collect(Collectors.toList());
		}
	};
	
	private ToolType type;
	
	private Brand brand;
	
	private ToolCode code;
	
	private double dailyCharge;
	
	private boolean chargeWeekends;
	
	private boolean chargeHolidays;

	public Tool(ToolCode code) {
		this.code = code;
		
		switch (code) {
		case CHNS:
			this.type = ToolType.CHAINSAW;
			this.brand = Brand.STIHL;
			this.dailyCharge = 1.49;
			this.chargeWeekends = false;
			this.chargeHolidays = true;
			break;
		case JAKD:
			this.type = ToolType.JACKHAMMER;
			this.brand = Brand.DEWALT;
			this.dailyCharge = 2.99;
			this.chargeWeekends = false;
			this.chargeHolidays = false;
			break;
		case JAKR:
			this.type = ToolType.JACKHAMMER;
			this.brand = Brand.RIDGID;
			this.dailyCharge = 2.99;
			this.chargeWeekends = false;
			this.chargeHolidays = false;
			break;
		case LADW:
			this.type = ToolType.LADDER;
			this.brand = Brand.WERNER;
			this.dailyCharge = 1.99;
			this.chargeWeekends = true;
			this.chargeHolidays = false;
			break;		
		}
	}

	/**
	 * @return the type of the tool
	 */
	public ToolType getType() {
		return type;
	}

	/**
	 * @param type the type of the tool to set
	 */
	public void setType(ToolType type) {
		this.type = type;
	}

	/**
	 * @return the brand
	 */
	public Brand getBrand() {
		return brand;
	}

	/**
	 * @param brand the brand to set
	 */
	public void setBrand(Brand brand) {
		this.brand = brand;
	}

	/**
	 * @return the code
	 */
	public ToolCode getCode() {
		return code;
	}

	/**
	 * @param code the code to set
	 */
	public void setCode(ToolCode code) {
		this.code = code;
	}
	
	/**
	 * @return the dailyCharge
	 */
	public double getDailyCharge() {
		return dailyCharge;
	}

	/**
	 * @param dailyCharge the dailyCharge to set
	 */
	public void setDailyCharge(double dailyCharge) {
		this.dailyCharge = dailyCharge;
	}

	/**
	 * @return the chargeWeekends
	 */
	public boolean chargeWeekends() {
		return chargeWeekends;
	}

	/**
	 * @param chargeWeekends the chargeWeekends to set
	 */
	public void setChargeWeekends(boolean chargeWeekends) {
		this.chargeWeekends = chargeWeekends;
	}

	/**
	 * @return the chargeHolidays
	 */
	public boolean chargeHolidays() {
		return chargeHolidays;
	}

	/**
	 * @param chargeHolidays the chargeHolidays to set
	 */
	public void setChargeHolidays(boolean chargeHolidays) {
		this.chargeHolidays = chargeHolidays;
	}

}
