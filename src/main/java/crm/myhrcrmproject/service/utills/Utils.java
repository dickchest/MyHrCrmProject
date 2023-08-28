package crm.myhrcrmproject.service.utills;
@FunctionalInterface
interface FieldSetter<T, R> {
    void setField(T target, R value);
}

