package bs0921;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;

/**
 * This class represents the rental agreement between 
 * the rental company and its tool renter.
 */
public class RentalAgreement {

	public static DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("MM/dd/yy");

	private Tool tool;

	private int rentalDays;

	private int discountPercentage;

	private LocalDate checkoutDate;

	public RentalAgreement(Tool tool, int rentalDays, int discountPercentage, LocalDate checkoutDate) {
		this.tool = tool;
		this.rentalDays = rentalDays;
		this.discountPercentage = discountPercentage;
		this.checkoutDate = checkoutDate;
	}

	/**
	 * @return the tool
	 */
	public Tool getTool() {
		return tool;
	}

	/**
	 * @param tool the tool to set
	 */
	public void setTool(Tool tool) {
		this.tool = tool;
	}

	/**
	 * @return the rentalDays
	 */
	public int getRentalDays() {
		return rentalDays;
	}

	/**
	 * @param rentalDays the rentalDays to set
	 */
	public void setRentalDays(int rentalDays) {
		this.rentalDays = rentalDays;
	}

	/**
	 * @return the discountPercentage
	 */
	public int getDiscountPercentage() {
		return discountPercentage;
	}

	/**
	 * @param discountPercentage the discountPercentage to set
	 */
	public void setDiscountPercentage(int discountPercentage) {
		this.discountPercentage = discountPercentage;
	}

	/**
	 * @return the checkoutDate
	 */
	public LocalDate getCheckoutDate() {
		return checkoutDate;
	}

	/**
	 * @param checkoutDate the checkoutDate to set
	 */
	public void setCheckoutDate(LocalDate checkoutDate) {
		this.checkoutDate = checkoutDate;
	}

	@Override
	public String toString() {
		NumberFormat formatter = NumberFormat.getCurrencyInstance();
		
		StringBuilder agreement = new StringBuilder();
		agreement.append("Tool code: ");
		agreement.append(tool.getCode());
		agreement.append('\n');

		agreement.append("Tool type: ");
		agreement.append(tool.getType());
		agreement.append('\n');

		agreement.append("Tool brand: ");
		agreement.append(tool.getBrand());
		agreement.append('\n');

		agreement.append("Rental days: ");
		agreement.append(rentalDays);
		agreement.append('\n');

		agreement.append("Check out date: ");
		agreement.append(dateFormat.format(checkoutDate));
		agreement.append('\n');

		agreement.append("Due date: ");
		agreement.append(dateFormat.format(getDueDate()));
		agreement.append('\n');

		agreement.append("Daily rental charge: ");
		agreement.append(formatter.format(tool.getDailyCharge()));
		agreement.append('\n');

		agreement.append("Charge days: ");
		agreement.append(getChargeableDays());
		agreement.append('\n');

		agreement.append("Pre-discount charge: ");
		agreement.append(formatter.format(getTotalCostNoDiscount()));
		agreement.append('\n');

		agreement.append("Discount percentage: ");
		agreement.append(discountPercentage);
		agreement.append("%\n");

		agreement.append("Discount amount: ");
		agreement.append(formatter.format(getDiscountAmount()));
		agreement.append('\n');

		agreement.append("Final Charge: ");
		agreement.append(formatter.format(getFinalCharge()));
		agreement.append('\n');

		return agreement.toString();
	}

	/**
	 * @return Due date calculated from checkout date and rental days
	 */
	public LocalDate getDueDate() {
		return LocalDate.from(checkoutDate).plusDays(rentalDays);
	}

	/**
	 * @return Amount calculated from discount % and pre-discount charge NOTE:
	 *         Resulting amount rounded half up to cents
	 */
	public double getDiscountAmount() {
		double discountDouble = getTotalCostNoDiscount() * ((double) discountPercentage / 100.0);

		return new BigDecimal(discountDouble).setScale(2, RoundingMode.HALF_UP).doubleValue();
	}

	/**
	 * @return Calculated as pre-discount charge - discount amount
	 */
	public double getFinalCharge() {
		double finalCharge = getTotalCostNoDiscount() - getDiscountAmount();

		return new BigDecimal(finalCharge).setScale(2, RoundingMode.HALF_UP).doubleValue();
	}

	/**
	 * @return Count of chargeable days, from day after checkout through and
	 *         including due date, excluding “no charge” days as specified by the
	 *         tool type
	 */
	public int getChargeableDays() {
		int chargeableDays = 0;
		for (LocalDate date = checkoutDate.plusDays(1); date
				.isBefore(getDueDate().plusDays(1)); date = date.plusDays(1)) {
			boolean isWeekend = isWeekendDay(date);
			boolean isHoliday = isHoliday(date);

			if ((!isWeekend && !isHoliday) || (isWeekend && tool.chargeWeekends())
					|| (isHoliday && tool.chargeHolidays())) {
				chargeableDays++;
			}
		}

		return chargeableDays;
	}

	/**
	 * @return Cost calculated as charge days X daily charge. 
	 * 		   NOTE: Resulting total rounded half up to cents
	 */
	public double getTotalCostNoDiscount() {
		return new BigDecimal(getChargeableDays() * tool.getDailyCharge())
				.setScale(2, RoundingMode.HALF_UP).doubleValue();
	}

	/**
	 * @return true if date is a weekend day, false if not
	 */
	private boolean isWeekendDay(LocalDate date) {
		return (date.getDayOfWeek() == DayOfWeek.SATURDAY || 
				date.getDayOfWeek() == DayOfWeek.SUNDAY) ? true : false;
	}

	/**
	 * @return true if date is an observed holiday, false if not
	 */
	private boolean isHoliday(LocalDate date) {
		LocalDate firstMondayInSept = LocalDate.of(date.getYear(), 9, 1);
		firstMondayInSept = firstMondayInSept.with(TemporalAdjusters.firstInMonth(DayOfWeek.MONDAY));

		return (date.equals(getObservedJuly4th(date.getYear())) || 
				date.equals(firstMondayInSept)) ? true : false;
	}

	/**
	 * @return the date of the observed Fourth of July for given year
	 */
	private LocalDate getObservedJuly4th(int year) {
		LocalDate date = LocalDate.of(year, 7, 4);

		if (date.getDayOfWeek() == DayOfWeek.SATURDAY) {
			return date.minusDays(1);
		}
		if (date.getDayOfWeek() == DayOfWeek.SUNDAY) {
			return date.plusDays(1);
		}
		return date;
	}

}
