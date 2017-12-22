package com.fumushan.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class NumberUtil {
	
	public static BigDecimal double2StringTail2(BigDecimal val,BigDecimal divisor){
		if(val == null)
			return new BigDecimal(0);
		BigDecimal d = val.divide(divisor);
		return d.setScale(2,RoundingMode.HALF_EVEN);
	}

}
