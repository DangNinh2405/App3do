package com.example.app3do.utils.direction;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public class Direction {
    private static Direction instance;
    private Direction(){}

    public static Direction getInstance() {
        if (instance == null) {
            synchronized (Direction.class) {
                if (instance == null) {
                    instance = new Direction();
                }
            }
        }

        return instance;
    }

    public int directionToFragment(FragmentManager fragmentManager, int id, Fragment fragment, String tag, String backStack) {
        return fragmentManager.beginTransaction()
                .add(id, fragment, tag)
                .addToBackStack(backStack)
                .commit();
    }
}
