package com.hquach.Validator;

import com.hquach.Utils.DateUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Date;

/**
 * Effective Date Validator
 */
public class EffectiveDateValidator implements ConstraintValidator<EffectiveDate, Date> {
    public final void initialize(final EffectiveDate annotation) {}

    @Override
    public boolean isValid(Date value, ConstraintValidatorContext context) {
        if (value.before(DateUtils.getBeginThisYear()) || value.after(DateUtils.getBeginNextYear())) {
            return false;
        }
        return true;
    }

}
