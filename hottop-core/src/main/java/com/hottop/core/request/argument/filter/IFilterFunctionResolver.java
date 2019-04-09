package com.hottop.core.request.argument.filter;

import com.hottop.core.model.zpoj.EntityBase;
import com.hottop.core.request.argument.field.FieldExporter;
import com.hottop.core.request.argument.flag.FlagPageable;
import com.hottop.core.request.argument.view.ViewExporter;
import com.hottop.core.request.argument.view.exception.ViewNotFoundException;

public interface IFilterFunctionResolver {
    FlagPageable flagPageable();

    FieldExporter fieldExporter() throws Exception;

    ViewExporter viewExporter() throws Exception;
}
