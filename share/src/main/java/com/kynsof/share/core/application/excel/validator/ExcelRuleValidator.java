package com.kynsof.share.core.application.excel.validator;

import com.kynsof.share.core.domain.response.ErrorField;

import java.util.List;

public interface ExcelRuleValidator<T> {
    List<ErrorField> validate(T obj);
}

