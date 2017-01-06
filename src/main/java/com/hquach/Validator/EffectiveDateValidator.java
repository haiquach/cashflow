package com.hquach.Validator;

import com.hquach.Utils.DateUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;
import java.util.Date;

/**
 * Effective Date Validator
 */
public class EffectiveDateValidator implements ConstraintValidator<EffectiveDate, Date> {
    public final void initialize(final EffectiveDate annotation) {}

    @Override
    public boolean isValid(Date value, ConstraintValidatorContext context) {
        if (value == null) {
            return false;
        }
        LocalDate date = DateUtils.asLocalDate(value);
        if (date.isBefore(DateUtils.beginningThisYear()) ||
                date.isEqual(DateUtils.beginningNextYear()) ||
                date.isAfter(DateUtils.beginningNextYear())) {
            return false;
        }
        return true;
    }

}
