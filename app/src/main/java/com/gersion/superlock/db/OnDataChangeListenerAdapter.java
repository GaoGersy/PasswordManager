package com.gersion.superlock.db;

import java.util.List;

public class OnDataChangeListenerAdapter<T> implements BaseDbManager.OnDataChangeCallback<T> {

    /**
     * {@inheritDoc}
     */
    @Override
    public void onUpdate(T bean) {

    }
    /**
     * {@inheritDoc}
     */
    @Override
    public void onAdd(T bean) {

    }
    /**
     * {@inheritDoc}
     */
    @Override
    public void onDelete(T bean) {

    }
    /**
     * {@inheritDoc}
     */
    @Override
    public void onDeleteAll() {

    }
    /**
     * {@inheritDoc}
     */
    @Override
    public void onDbSwitch(List<T> list) {

    }
}
