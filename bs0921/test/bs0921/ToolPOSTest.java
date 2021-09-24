package bs0921;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.time.LocalDate;

import org.junit.Test;

import bs0921.Tool.Brand;
import bs0921.Tool.ToolCode;
import bs0921.Tool.ToolType;

/**
 * Test suite for the {@link ToolPOS} class
 */
public class ToolPOSTest {

	private Tool.ToolCode code;

	private int rentalDays;

	private int discountPercent;

	private LocalDate checkoutDate;

	private RentalAgreement agreement;

	@Test
	public void test1() {
		code = ToolCode.JAKR;
		checkoutDate = LocalDate.of(2015, 9, 3);
		rentalDays = 5;
		discountPercent = 101;

		boolean errorThrown = false;
		Exception exception = null;
		try {
			agreement = ToolPOS.rentTool(code, rentalDays, discountPercent, checkoutDate);
		} catch (Exception e) {
			errorThrown = true;
			exception = e;
		}

		if (errorThrown) {
			assertEquals("Whoops! Discount percent is not in the range 0-100. "
					+ "Please try again.", exception.getMessage());
		} else {
			fail("Invalid discount percent not detected.");
		}

	}

	@Test
	public void test2() throws Exception {
		code = ToolCode.LADW;
		checkoutDate = LocalDate.of(2020, 7, 2);
		rentalDays = 3;
		discountPercent = 10;

		agreement = ToolPOS.rentTool(code, rentalDays, discountPercent, checkoutDate);
		assertNotNull(agreement);
		assertEquals(ToolCode.LADW, agreement.getTool().getCode());
		assertEquals(ToolType.LADDER, agreement.getTool().getType());
		assertEquals(Brand.WERNER, agreement.getTool().getBrand());
		assertEquals(3, agreement.getRentalDays());
		assertEquals(LocalDate.of(2020, 7, 2), agreement.getCheckoutDate());
		assertEquals(LocalDate.of(2020, 7, 5), agreement.getDueDate());
		assertEquals(1.99, agreement.getTool().getDailyCharge(), 0);
		assertEquals(2, agreement.getChargeableDays());
		assertEquals(3.98, agreement.getTotalCostNoDiscount(), 0);
		assertEquals(10, agreement.getDiscountPercentage());
		assertEquals(.4, agreement.getDiscountAmount(), 0);
		assertEquals(3.58, agreement.getFinalCharge(), 0);
	}

	@Test
	public void test3() throws Exception {
		code = ToolCode.CHNS;
		checkoutDate = LocalDate.of(2015, 7, 2);
		rentalDays = 5;
		discountPercent = 25;

		agreement = ToolPOS.rentTool(code, rentalDays, discountPercent, checkoutDate);
		assertNotNull(agreement);
		assertEquals(ToolCode.CHNS, agreement.getTool().getCode());
		assertEquals(ToolType.CHAINSAW, agreement.getTool().getType());
		assertEquals(Brand.STIHL, agreement.getTool().getBrand());
		assertEquals(5, agreement.getRentalDays());
		assertEquals(LocalDate.of(2015, 7, 2), agreement.getCheckoutDate());
		assertEquals(LocalDate.of(2015, 7, 7), agreement.getDueDate());
		assertEquals(1.49, agreement.getTool().getDailyCharge(), 0);
		assertEquals(3, agreement.getChargeableDays());
		assertEquals(4.47, agreement.getTotalCostNoDiscount(), 0);
		assertEquals(25, agreement.getDiscountPercentage());
		assertEquals(1.12, agreement.getDiscountAmount(), 0);
		assertEquals(3.35, agreement.getFinalCharge(), 0);
	}

	@Test
	public void test4() throws Exception {
		code = ToolCode.JAKD;
		checkoutDate = LocalDate.of(2015, 9, 3);
		rentalDays = 6;
		discountPercent = 0;

		agreement = ToolPOS.rentTool(code, rentalDays, discountPercent, checkoutDate);
		assertNotNull(agreement);
		assertEquals(ToolCode.JAKD, agreement.getTool().getCode());
		assertEquals(ToolType.JACKHAMMER, agreement.getTool().getType());
		assertEquals(Brand.DEWALT, agreement.getTool().getBrand());
		assertEquals(6, agreement.getRentalDays());
		assertEquals(LocalDate.of(2015, 9, 3), agreement.getCheckoutDate());
		assertEquals(LocalDate.of(2015, 9, 9), agreement.getDueDate());
		assertEquals(2.99, agreement.getTool().getDailyCharge(), 0);
		assertEquals(3, agreement.getChargeableDays());
		assertEquals(8.97, agreement.getTotalCostNoDiscount(), 0);
		assertEquals(0, agreement.getDiscountPercentage());
		assertEquals(0, agreement.getDiscountAmount(), 0);
		assertEquals(8.97, agreement.getFinalCharge(), 0);
	}

	@Test
	public void test5() throws Exception {
		code = ToolCode.JAKR;
		checkoutDate = LocalDate.of(2015, 7, 2);
		rentalDays = 9;
		discountPercent = 0;

		agreement = ToolPOS.rentTool(code, rentalDays, discountPercent, checkoutDate);
		assertNotNull(agreement);
		assertEquals(ToolCode.JAKR, agreement.getTool().getCode());
		assertEquals(ToolType.JACKHAMMER, agreement.getTool().getType());
		assertEquals(Brand.RIDGID, agreement.getTool().getBrand());
		assertEquals(9, agreement.getRentalDays());
		assertEquals(LocalDate.of(2015, 7, 2), agreement.getCheckoutDate());
		assertEquals(LocalDate.of(2015, 7, 11), agreement.getDueDate());
		assertEquals(2.99, agreement.getTool().getDailyCharge(), 0);
		assertEquals(5, agreement.getChargeableDays());
		assertEquals(14.95, agreement.getTotalCostNoDiscount(), 0);
		assertEquals(0, agreement.getDiscountPercentage());
		assertEquals(0, agreement.getDiscountAmount(), 0);
		assertEquals(14.95, agreement.getFinalCharge(), 0);
	}

	@Test
	public void test6() throws Exception {
		code = ToolCode.JAKR;
		checkoutDate = LocalDate.of(2020, 7, 2);
		rentalDays = 4;
		discountPercent = 50;

		agreement = ToolPOS.rentTool(code, rentalDays, discountPercent, checkoutDate);
		assertNotNull(agreement);
		assertEquals(ToolCode.JAKR, agreement.getTool().getCode());
		assertEquals(ToolType.JACKHAMMER, agreement.getTool().getType());
		assertEquals(Brand.RIDGID, agreement.getTool().getBrand());
		assertEquals(4, agreement.getRentalDays());
		assertEquals(LocalDate.of(2020, 7, 2), agreement.getCheckoutDate());
		assertEquals(LocalDate.of(2020, 7, 6), agreement.getDueDate());
		assertEquals(2.99, agreement.getTool().getDailyCharge(), 0);
		assertEquals(1, agreement.getChargeableDays());
		assertEquals(2.99, agreement.getTotalCostNoDiscount(), 0);
		assertEquals(50, agreement.getDiscountPercentage());
		assertEquals(1.50, agreement.getDiscountAmount(), 0);
		assertEquals(1.49, agreement.getFinalCharge(), 0);
	}

}
