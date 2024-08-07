package com.mygdx.game.ui;

import java.util.ArrayList;
import java.util.List;

public class CallbackList<E> extends ArrayList<E> {

    private List<CallbackInterface> registered_add;

    public CallbackList() {
        registered_add = new ArrayList<>(1);
    }

    @Override
    public boolean add(E e) {
        boolean r = super.add(e);

        for (CallbackInterface i : registered_add)
            i.callback();

        return r;
    }

    public void registerAddCallback(CallbackInterface ir) {
        registered_add.add(ir);
    }

    public interface CallbackInterface {
        void callback();
    }

}
