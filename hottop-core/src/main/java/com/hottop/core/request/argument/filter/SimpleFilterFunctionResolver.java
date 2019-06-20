package com.hottop.core.request.argument.filter;

import com.hottop.core.BaseConstant;
import com.hottop.core.model.zpoj.EntityBase;
import com.hottop.core.request.argument.field.FieldExporter;
import com.hottop.core.request.argument.flag.FlagPageSizeRequest;
import com.hottop.core.request.argument.flag.FlagPageSizeResolver;
import com.hottop.core.request.argument.flag.FlagPageable;
import com.hottop.core.request.argument.sort.SortStringResolver;
import com.hottop.core.request.argument.view.DataViewRegistration;
import com.hottop.core.request.argument.view.ViewExporter;

import java.util.HashMap;

public class SimpleFilterFunctionResolver implements IFilterFunctionResolver {
    private FlagPageable flagPageable;
    private FieldExporter fieldExporter;
    private ViewExporter viewExporter;

    private Class<?> clazz;
    private HashMap<EFilterFunction, HashMap<String, String>> funcParametersMapper;
    private DataViewRegistration dataViewRegistration;

    public <T extends EntityBase> SimpleFilterFunctionResolver(Class<T> clazz,
                                                               HashMap<EFilterFunction, HashMap<String, String>> funcParametersMapper,
                                                               DataViewRegistration dataViewRegistration) {
        this.clazz = clazz;
        this.funcParametersMapper = funcParametersMapper;
        this.dataViewRegistration = dataViewRegistration;
    }

    @Override
    public FlagPageable flagPageable() {
        if (this.flagPageable == null) {
<<<<<<< HEAD
            if (funcParametersMapper.containsKey(EFilterFunction.flag)) {
                HashMap<String, String> parameterMapper = funcParametersMapper.get(EFilterFunction.flag);
                FlagPageable flagPageable = new FlagPageSizeRequest(parameterMapper.getOrDefault(EFilterFunction.flag.primaryParameter(),
                        new FlagPageSizeResolver(0, BaseConstant.Response.PAGE_SIZE).toFlag()));
                flagPageable.setSortResolver(new SortStringResolver(parameterMapper.getOrDefault("sort", "")));
=======
            if (funcParametersMapper != null && funcParametersMapper.containsKey(EFilterFunction.flag)) {
                HashMap<String, String> parameterMapper = funcParametersMapper.get(EFilterFunction.flag);
                FlagPageable flagPageable = new FlagPageSizeRequest(parameterMapper.getOrDefault(EFilterFunction.flag.primaryParameter(),
                        new FlagPageSizeResolver(0, BaseConstant.Response.PAGE_SIZE).toFlag()));
                flagPageable.setSortResolver(new SortStringResolver(parameterMapper.getOrDefault(BaseConstant.Request.Argument.SORT, "")));
>>>>>>> b99db5c79492b574b2ca3021b6e903a9c00b3c37
                this.flagPageable = flagPageable;
            } else {
                this.flagPageable = FlagPageSizeRequest.DEFAULT;
            }
        }
        return this.flagPageable;
    }

    @Override
    public FieldExporter fieldExporter() throws Exception {
        if (this.fieldExporter == null) {
<<<<<<< HEAD
            if (funcParametersMapper.containsKey(EFilterFunction.fields)) {
=======
            if (funcParametersMapper != null && funcParametersMapper.containsKey(EFilterFunction.fields)) {
>>>>>>> b99db5c79492b574b2ca3021b6e903a9c00b3c37
                HashMap<String, String> parameterMapper = funcParametersMapper.get(EFilterFunction.fields);
                String fieldNamesString = parameterMapper.get(EFilterFunction.fields.primaryParameter());
                this.fieldExporter = new FieldExporter().init(clazz, fieldNamesString);
            } else {
                this.fieldExporter = FieldExporter.NO_FIELD_EXPORT;
            }
        }
        return this.fieldExporter;
    }

    @Override
    public ViewExporter viewExporter() throws Exception {
        if (this.viewExporter == null) {
<<<<<<< HEAD
            if (funcParametersMapper.containsKey(EFilterFunction.view)) {
=======
            if (funcParametersMapper != null && funcParametersMapper.containsKey(EFilterFunction.view)) {
>>>>>>> b99db5c79492b574b2ca3021b6e903a9c00b3c37
                HashMap<String, String> parameterMapper = funcParametersMapper.get(EFilterFunction.view);
                String viewName = parameterMapper.get(EFilterFunction.view.primaryParameter());
                this.viewExporter = new ViewExporter(clazz, viewName, dataViewRegistration);
            } else {
                this.viewExporter = ViewExporter.NO_VIEW_EXPORT;
            }
        }
        return this.viewExporter;
    }
}
